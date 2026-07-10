package com.codexdemo.orderplatform.notification;

import com.codexdemo.orderplatform.common.PagedResponse;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FailedEventMessageService {

  private final FailedEventMessageRepository failedEventMessageRepository;

  private final FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;

  private final FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;

  private final FailedEventReplayApprovalHistoryRepository
      failedEventReplayApprovalHistoryRepository;

  private final FailedEventOperatorContextResolver operatorContextResolver;

  private final FailedEventReplayProperties failedEventReplayProperties;

  private final RabbitTemplate rabbitTemplate;

  private final OutboxRabbitMqProperties outboxRabbitMqProperties;

  private final FailedEventQueryService failedEventQueryService;

  public FailedEventMessageService(
      FailedEventMessageRepository failedEventMessageRepository,
      FailedEventReplayAttemptRepository failedEventReplayAttemptRepository,
      FailedEventManagementHistoryRepository failedEventManagementHistoryRepository,
      FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository,
      FailedEventOperatorContextResolver operatorContextResolver,
      FailedEventReplayProperties failedEventReplayProperties,
      RabbitTemplate rabbitTemplate,
      OutboxRabbitMqProperties outboxRabbitMqProperties,
      FailedEventQueryService failedEventQueryService) {
    this.failedEventMessageRepository = failedEventMessageRepository;
    this.failedEventReplayAttemptRepository = failedEventReplayAttemptRepository;
    this.failedEventManagementHistoryRepository = failedEventManagementHistoryRepository;
    this.failedEventReplayApprovalHistoryRepository = failedEventReplayApprovalHistoryRepository;
    this.operatorContextResolver = operatorContextResolver;
    this.failedEventReplayProperties = failedEventReplayProperties;
    this.rabbitTemplate = rabbitTemplate;
    this.outboxRabbitMqProperties = outboxRabbitMqProperties;
    this.failedEventQueryService = failedEventQueryService;
  }

  @Transactional
  public FailedEventMessage record(Message message, String deadLetterQueue) {
    String payload = new String(message.getBody(), StandardCharsets.UTF_8);
    String messageId = resolveMessageId(message, payload);
    return failedEventMessageRepository
        .findByMessageId(messageId)
        .orElseGet(() -> saveFailedMessage(message, deadLetterQueue, payload, messageId));
  }

  @Transactional(readOnly = true)
  public List<FailedEventMessageResponse> listRecentFailedMessages() {
    return failedEventQueryService.listRecentFailedMessages();
  }

  @Transactional(readOnly = true)
  public PagedResponse<FailedEventMessageResponse> searchFailedMessages(
      FailedEventMessageSearchCriteria criteria) {
    return failedEventQueryService.searchFailedMessages(criteria);
  }

  @Transactional(readOnly = true)
  public String exportFailedMessagesCsv(FailedEventMessageSearchCriteria criteria) {
    return failedEventQueryService.exportFailedMessagesCsv(criteria);
  }

  @Transactional
  public FailedEventManagementBatchResponse markManagementStatus(
      MarkFailedEventManagementRequest request, String operatorId, String operatorRole) {
    return markManagementStatus(
        request,
        operatorContextResolver.resolve(
            operatorId, operatorRole, FailedEventOperatorAction.MANAGE_FAILED_EVENT));
  }

  @Transactional
  public FailedEventManagementBatchResponse markManagementStatus(
      MarkFailedEventManagementRequest request, FailedEventOperatorContext operatorContext) {
    if (request == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request body is required");
    }
    List<Long> ids = normalizeManagementIds(request.ids());
    FailedEventManagementStatus managementStatus = requireManagementStatus(request.status());
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String normalizedOperatorId = operator.operatorId();
    String normalizedOperatorRole = operator.operatorRole();
    String note = resolveManagementNote(request.note());
    Instant managedAt = Instant.now();
    List<FailedEventMessage> failedMessages = failedEventMessageRepository.findAllById(ids);
    if (failedMessages.size() != ids.size()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "one or more failed event messages were not found");
    }
    failedMessages.forEach(
        failedMessage -> {
          FailedEventManagementStatus previousStatus = failedMessage.getManagementStatus();
          failedMessage.markManagementStatus(
              managementStatus, note, normalizedOperatorId, managedAt);
          failedEventManagementHistoryRepository.save(
              FailedEventManagementHistory.record(
                  failedMessage,
                  previousStatus,
                  managementStatus,
                  normalizedOperatorId,
                  normalizedOperatorRole,
                  note,
                  managedAt));
        });
    return new FailedEventManagementBatchResponse(
        managementStatus,
        failedMessages.size(),
        failedMessages.stream().map(FailedEventMessageResponse::from).toList());
  }

  @Transactional
  public FailedEventMessageResponse requestReplayApproval(
      Long id,
      RequestFailedEventReplayApprovalRequest request,
      String operatorId,
      String operatorRole) {
    return requestReplayApproval(
        id,
        request,
        operatorContextResolver.resolve(
            operatorId, operatorRole, FailedEventOperatorAction.REQUEST_REPLAY_APPROVAL));
  }

  @Transactional
  public FailedEventMessageResponse requestReplayApproval(
      Long id,
      RequestFailedEventReplayApprovalRequest request,
      FailedEventOperatorContext operatorContext) {
    FailedEventMessage failedMessage =
        failedEventMessageRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "failed event message not found"));
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String normalizedOperatorId = operator.operatorId();
    String normalizedOperatorRole = operator.operatorRole();
    String reason = resolveReplayApprovalReason(request);
    if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "failed event message has already been replayed");
    }
    if (failedMessage.getReplayApprovalStatus() == FailedEventReplayApprovalStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "replay approval is already pending");
    }
    if (failedMessage.getReplayApprovalStatus() == FailedEventReplayApprovalStatus.APPROVED) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "replay approval has already been approved");
    }
    Instant requestedAt = Instant.now();
    failedMessage.requestReplayApproval(reason, normalizedOperatorId, requestedAt);
    failedEventReplayApprovalHistoryRepository.save(
        FailedEventReplayApprovalHistory.record(
            failedMessage,
            FailedEventReplayApprovalHistoryAction.REQUESTED,
            normalizedOperatorId,
            normalizedOperatorRole,
            reason,
            requestedAt));
    return FailedEventMessageResponse.from(failedMessage);
  }

  @Transactional
  public FailedEventMessageResponse reviewReplayApproval(
      Long id,
      ReviewFailedEventReplayApprovalRequest request,
      String operatorId,
      String operatorRole) {
    return reviewReplayApproval(
        id,
        request,
        operatorContextResolver.resolve(
            operatorId, operatorRole, FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL));
  }

  @Transactional
  public FailedEventMessageResponse reviewReplayApproval(
      Long id,
      ReviewFailedEventReplayApprovalRequest request,
      FailedEventOperatorContext operatorContext) {
    FailedEventMessage failedMessage =
        failedEventMessageRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "failed event message not found"));
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String normalizedOperatorId = operator.operatorId();
    String normalizedOperatorRole = operator.operatorRole();
    FailedEventReplayApprovalStatus reviewStatus = requireReplayApprovalReviewStatus(request);
    String note =
        resolveReplayApprovalReviewNote(reviewStatus, request == null ? null : request.note());
    if (failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "replay approval is not pending");
    }
    ensureReplayApprovalReviewerIsDifferent(failedMessage, normalizedOperatorId);
    Instant reviewedAt = Instant.now();
    if (reviewStatus == FailedEventReplayApprovalStatus.APPROVED) {
      failedMessage.approveReplay(normalizedOperatorId, note, reviewedAt);
    } else {
      failedMessage.rejectReplay(normalizedOperatorId, note, reviewedAt);
    }
    failedEventReplayApprovalHistoryRepository.save(
        FailedEventReplayApprovalHistory.record(
            failedMessage,
            FailedEventReplayApprovalHistoryAction.valueOf(reviewStatus.name()),
            normalizedOperatorId,
            normalizedOperatorRole,
            note,
            reviewedAt));
    return FailedEventMessageResponse.from(failedMessage);
  }

  @Transactional(readOnly = true)
  public List<FailedEventManagementHistoryResponse> listManagementHistory(
      Long failedEventMessageId) {
    return failedEventQueryService.listManagementHistory(failedEventMessageId);
  }

  @Transactional(readOnly = true)
  public PagedResponse<FailedEventManagementHistoryResponse> searchManagementHistory(
      FailedEventManagementHistorySearchCriteria criteria) {
    return failedEventQueryService.searchManagementHistory(criteria);
  }

  @Transactional(readOnly = true)
  public String exportManagementHistoryCsv(FailedEventManagementHistorySearchCriteria criteria) {
    return failedEventQueryService.exportManagementHistoryCsv(criteria);
  }

  @Transactional(readOnly = true)
  public List<FailedEventReplayApprovalHistoryResponse> listReplayApprovalHistory(
      Long failedEventMessageId) {
    return failedEventQueryService.listReplayApprovalHistory(failedEventMessageId);
  }

  @Transactional(readOnly = true)
  public PagedResponse<FailedEventReplayApprovalHistoryResponse> searchReplayApprovalHistory(
      FailedEventReplayApprovalHistorySearchCriteria criteria) {
    return failedEventQueryService.searchReplayApprovalHistory(criteria);
  }

  @Transactional(readOnly = true)
  public String exportReplayApprovalHistoryCsv(
      FailedEventReplayApprovalHistorySearchCriteria criteria) {
    return failedEventQueryService.exportReplayApprovalHistoryCsv(criteria);
  }

  @Transactional(readOnly = true)
  public List<FailedEventReplayAttemptResponse> listReplayAttempts(Long failedEventMessageId) {
    return failedEventQueryService.listReplayAttempts(failedEventMessageId);
  }

  @Transactional(readOnly = true)
  public PagedResponse<FailedEventReplayAttemptResponse> searchReplayAttempts(
      FailedEventReplayAttemptSearchCriteria criteria) {
    return failedEventQueryService.searchReplayAttempts(criteria);
  }

  @Transactional
  public FailedEventMessageResponse replay(Long id, ReplayFailedEventRequest request) {
    return replay(
        id,
        request,
        operatorContextResolver.resolve(
            "system",
            failedEventReplayProperties.getSystemRole(),
            FailedEventOperatorAction.REPLAY_FAILED_EVENT));
  }

  @Transactional
  public FailedEventMessageResponse replay(
      Long id, ReplayFailedEventRequest request, String operatorId) {
    return replay(
        id,
        request,
        operatorContextResolver.resolve(
            operatorId,
            failedEventReplayProperties.getSystemRole(),
            FailedEventOperatorAction.REPLAY_FAILED_EVENT));
  }

  @Transactional
  public FailedEventMessageResponse replay(
      Long id, ReplayFailedEventRequest request, String operatorId, String operatorRole) {
    return replay(
        id,
        request,
        operatorContextResolver.resolve(
            operatorId, operatorRole, FailedEventOperatorAction.REPLAY_FAILED_EVENT));
  }

  @Transactional
  public FailedEventMessageResponse replay(
      Long id, ReplayFailedEventRequest request, FailedEventOperatorContext operatorContext) {
    FailedEventMessage failedMessage =
        failedEventMessageRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "failed event message not found"));
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String normalizedOperatorId = operator.operatorId();
    String normalizedOperatorRole = operator.operatorRole();
    String reason = resolveReplayReason(request);
    if (!failedMessage.isReplayApproved()) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "failed event replay must be approved before replay");
    }
    if (!outboxRabbitMqProperties.isEnabled()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "RabbitMQ outbox is disabled");
    }

    String eventId = resolveReplayEventId(failedMessage, request);
    String eventType =
        requiredReplayField(
            "eventType", firstNonBlank(requestEventType(request), failedMessage.getEventType()));
    String aggregateType =
        requiredReplayField(
            "aggregateType",
            firstNonBlank(requestAggregateType(request), failedMessage.getAggregateType()));
    String aggregateId =
        requiredReplayField(
            "aggregateId",
            firstNonBlank(requestAggregateId(request), failedMessage.getAggregateId()));
    String payload =
        requiredReplayField(
            "payload", firstNonBlank(requestPayload(request), failedMessage.getPayload()));
    Instant replayedAt = Instant.now();
    if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
      saveReplayAttempt(
          failedMessage,
          request,
          normalizedOperatorId,
          normalizedOperatorRole,
          reason,
          eventId,
          eventType,
          aggregateType,
          aggregateId,
          payload,
          FailedEventReplayAttemptStatus.SKIPPED_ALREADY_REPLAYED,
          null,
          replayedAt);
      return FailedEventMessageResponse.from(failedMessage);
    }

    try {
      publishReplay(failedMessage, eventId, eventType, aggregateType, aggregateId, payload);
      failedMessage.markReplayed(eventId, replayedAt);
      saveReplayAttempt(
          failedMessage,
          request,
          normalizedOperatorId,
          normalizedOperatorRole,
          reason,
          eventId,
          eventType,
          aggregateType,
          aggregateId,
          payload,
          FailedEventReplayAttemptStatus.SUCCEEDED,
          null,
          replayedAt);
    } catch (AmqpException ex) {
      String errorMessage = truncate(errorMessage(ex), 500);
      failedMessage.markReplayFailed(eventId, errorMessage, replayedAt);
      saveReplayAttempt(
          failedMessage,
          request,
          normalizedOperatorId,
          normalizedOperatorRole,
          reason,
          eventId,
          eventType,
          aggregateType,
          aggregateId,
          payload,
          FailedEventReplayAttemptStatus.FAILED,
          errorMessage,
          replayedAt);
    }
    return FailedEventMessageResponse.from(failedMessage);
  }

  private FailedEventMessage saveFailedMessage(
      Message message, String deadLetterQueue, String payload, String messageId) {
    try {
      return failedEventMessageRepository.save(
          FailedEventMessage.record(
              messageId,
              header(message, "eventId"),
              header(message, "eventType"),
              header(message, "aggregateType"),
              header(message, "aggregateId"),
              header(message, "x-first-death-queue"),
              deadLetterQueue,
              truncate(
                  firstNonBlank(header(message, "x-first-death-reason"), "dead-lettered"), 500),
              payload));
    } catch (DataIntegrityViolationException ex) {
      return failedEventMessageRepository.findByMessageId(messageId).orElseThrow(() -> ex);
    }
  }

  private void publishReplay(
      FailedEventMessage failedMessage,
      String eventId,
      String eventType,
      String aggregateType,
      String aggregateId,
      String payload) {
    rabbitTemplate.convertAndSend(
        outboxRabbitMqProperties.getExchange(),
        outboxRabbitMqProperties.routingKeyForEventType(eventType),
        payload,
        message -> {
          message.getMessageProperties().setContentType("application/json");
          message.getMessageProperties().setMessageId(eventId);
          message.getMessageProperties().setHeader("eventId", eventId);
          message.getMessageProperties().setHeader("aggregateType", aggregateType);
          message.getMessageProperties().setHeader("aggregateId", aggregateId);
          message.getMessageProperties().setHeader("eventType", eventType);
          message
              .getMessageProperties()
              .setHeader("replayedFromFailedEventId", failedMessage.getId());
          message
              .getMessageProperties()
              .setHeader("replayedFromMessageId", failedMessage.getMessageId());
          return message;
        });
  }

  private void saveReplayAttempt(
      FailedEventMessage failedMessage,
      ReplayFailedEventRequest request,
      String operatorId,
      String operatorRole,
      String reason,
      String eventId,
      String eventType,
      String aggregateType,
      String aggregateId,
      String payload,
      FailedEventReplayAttemptStatus status,
      String errorMessage,
      Instant attemptedAt) {
    failedEventReplayAttemptRepository.save(
        FailedEventReplayAttempt.record(
            failedMessage,
            operatorId,
            operatorRole,
            reason,
            request,
            eventId,
            eventType,
            aggregateType,
            aggregateId,
            payload,
            status,
            errorMessage,
            attemptedAt));
  }

  private List<Long> normalizeManagementIds(List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ids are required");
    }
    List<Long> normalizedIds = ids.stream().distinct().toList();
    if (normalizedIds.size() > 100) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "ids size must be between 1 and 100");
    }
    if (normalizedIds.stream().anyMatch(id -> id == null || id < 1)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ids must contain positive ids");
    }
    return normalizedIds;
  }

  private FailedEventManagementStatus requireManagementStatus(
      FailedEventManagementStatus managementStatus) {
    if (managementStatus == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "management status is required");
    }
    return managementStatus;
  }

  private String resolveManagementNote(String note) {
    if (note == null || note.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "management note is required");
    }
    return truncate(note.strip(), 500);
  }

  private String resolveReplayApprovalReason(RequestFailedEventReplayApprovalRequest request) {
    String reason = request == null ? null : request.reason();
    if (reason == null || reason.isBlank()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "replay approval reason is required");
    }
    return truncate(reason.strip(), 500);
  }

  private FailedEventReplayApprovalStatus requireReplayApprovalReviewStatus(
      ReviewFailedEventReplayApprovalRequest request) {
    FailedEventReplayApprovalStatus status = request == null ? null : request.status();
    if (status != FailedEventReplayApprovalStatus.APPROVED
        && status != FailedEventReplayApprovalStatus.REJECTED) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "replay approval review status must be APPROVED or REJECTED");
    }
    return status;
  }

  private String resolveReplayApprovalReviewNote(
      FailedEventReplayApprovalStatus status, String note) {
    if (status == FailedEventReplayApprovalStatus.REJECTED && (note == null || note.isBlank())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "replay approval rejection note is required");
    }
    return note == null || note.isBlank() ? null : truncate(note.strip(), 500);
  }

  private void ensureReplayApprovalReviewerIsDifferent(
      FailedEventMessage failedMessage, String reviewerId) {
    String requesterId = failedMessage.getReplayApprovalRequestedBy();
    if (requesterId != null && requesterId.equals(reviewerId)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "replay approval requester cannot review own request");
    }
  }

  private String resolveReplayEventId(
      FailedEventMessage failedMessage, ReplayFailedEventRequest request) {
    String eventId =
        firstNonBlank(
            requestEventId(request), failedMessage.getEventId(), UUID.randomUUID().toString());
    try {
      UUID.fromString(eventId);
      return eventId;
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "eventId must be a valid UUID", ex);
    }
  }

  private String requiredReplayField(String fieldName, String value) {
    if (value == null || value.isBlank()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, fieldName + " is required for replay");
    }
    return value;
  }

  private FailedEventOperatorContext requireOperatorContext(
      FailedEventOperatorContext operatorContext) {
    if (operatorContext == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "operator context is required");
    }
    return operatorContext;
  }

  private String resolveReplayReason(ReplayFailedEventRequest request) {
    String reason = request == null ? null : request.reason();
    if (reason == null || reason.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reason is required for replay");
    }
    return truncate(reason.strip(), 500);
  }

  private String resolveMessageId(Message message, String payload) {
    return firstNonBlank(
        message.getMessageProperties().getMessageId(),
        header(message, "eventId"),
        "sha256-" + sha256(payload + message.getMessageProperties().getHeaders()));
  }

  private String header(Message message, String name) {
    Object value = message.getMessageProperties().getHeaders().get(name);
    return value == null ? null : value.toString();
  }

  private String firstNonBlank(String... values) {
    for (String value : values) {
      if (value != null && !value.isBlank()) {
        return value;
      }
    }
    return null;
  }

  private String truncate(String value, int maxLength) {
    if (value == null || value.length() <= maxLength) {
      return value;
    }
    return value.substring(0, maxLength);
  }

  private String sha256(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalStateException("SHA-256 is not available", ex);
    }
  }

  private String requestEventId(ReplayFailedEventRequest request) {
    return request == null ? null : request.eventId();
  }

  private String requestEventType(ReplayFailedEventRequest request) {
    return request == null ? null : request.eventType();
  }

  private String requestAggregateType(ReplayFailedEventRequest request) {
    return request == null ? null : request.aggregateType();
  }

  private String requestAggregateId(ReplayFailedEventRequest request) {
    return request == null ? null : request.aggregateId();
  }

  private String requestPayload(ReplayFailedEventRequest request) {
    return request == null ? null : request.payload();
  }

  private String errorMessage(Exception ex) {
    if (ex.getMessage() == null || ex.getMessage().isBlank()) {
      return ex.getClass().getSimpleName();
    }
    return ex.getMessage();
  }
}
