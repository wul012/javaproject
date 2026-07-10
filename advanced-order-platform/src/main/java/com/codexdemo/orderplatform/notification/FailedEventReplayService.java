package com.codexdemo.orderplatform.notification;

import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.firstNonBlank;
import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.requireOperatorContext;
import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.truncate;

import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import java.time.Instant;
import java.util.UUID;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
final class FailedEventReplayService {

  private final FailedEventMessageRepository failedEventMessageRepository;
  private final FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;
  private final RabbitTemplate rabbitTemplate;
  private final OutboxRabbitMqProperties outboxRabbitMqProperties;

  FailedEventReplayService(
      FailedEventMessageRepository failedEventMessageRepository,
      FailedEventReplayAttemptRepository failedEventReplayAttemptRepository,
      RabbitTemplate rabbitTemplate,
      OutboxRabbitMqProperties outboxRabbitMqProperties) {
    this.failedEventMessageRepository = failedEventMessageRepository;
    this.failedEventReplayAttemptRepository = failedEventReplayAttemptRepository;
    this.rabbitTemplate = rabbitTemplate;
    this.outboxRabbitMqProperties = outboxRabbitMqProperties;
  }

  FailedEventMessageResponse replay(
      Long id, ReplayFailedEventRequest request, FailedEventOperatorContext operatorContext) {
    FailedEventMessage failedMessage = findFailedMessage(id);
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String reason = resolveReason(request);
    if (!failedMessage.isReplayApproved()) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "failed event replay must be approved before replay");
    }
    if (!outboxRabbitMqProperties.isEnabled()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "RabbitMQ outbox is disabled");
    }

    EffectiveReplayEvent event = resolveEffectiveEvent(failedMessage, request);
    Instant replayedAt = Instant.now();
    if (failedMessage.getStatus() == FailedEventMessageStatus.REPLAYED) {
      saveReplayAttempt(
          failedMessage,
          request,
          operator,
          reason,
          event,
          FailedEventReplayAttemptStatus.SKIPPED_ALREADY_REPLAYED,
          null,
          replayedAt);
      return FailedEventMessageResponse.from(failedMessage);
    }

    try {
      publishReplay(failedMessage, event);
      failedMessage.markReplayed(event.eventId(), replayedAt);
      saveReplayAttempt(
          failedMessage,
          request,
          operator,
          reason,
          event,
          FailedEventReplayAttemptStatus.SUCCEEDED,
          null,
          replayedAt);
    } catch (AmqpException ex) {
      String errorMessage = truncate(errorMessage(ex), 500);
      failedMessage.markReplayFailed(event.eventId(), errorMessage, replayedAt);
      saveReplayAttempt(
          failedMessage,
          request,
          operator,
          reason,
          event,
          FailedEventReplayAttemptStatus.FAILED,
          errorMessage,
          replayedAt);
    }
    return FailedEventMessageResponse.from(failedMessage);
  }

  private FailedEventMessage findFailedMessage(Long id) {
    return failedEventMessageRepository
        .findById(id)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "failed event message not found"));
  }

  private EffectiveReplayEvent resolveEffectiveEvent(
      FailedEventMessage failedMessage, ReplayFailedEventRequest request) {
    return new EffectiveReplayEvent(
        resolveEventId(failedMessage, request),
        requiredField(
            "eventType", firstNonBlank(requestEventType(request), failedMessage.getEventType())),
        requiredField(
            "aggregateType",
            firstNonBlank(requestAggregateType(request), failedMessage.getAggregateType())),
        requiredField(
            "aggregateId",
            firstNonBlank(requestAggregateId(request), failedMessage.getAggregateId())),
        requiredField(
            "payload", firstNonBlank(requestPayload(request), failedMessage.getPayload())));
  }

  private String resolveEventId(
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

  private String requiredField(String fieldName, String value) {
    if (value == null || value.isBlank()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, fieldName + " is required for replay");
    }
    return value;
  }

  private String resolveReason(ReplayFailedEventRequest request) {
    String reason = request == null ? null : request.reason();
    if (reason == null || reason.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reason is required for replay");
    }
    return truncate(reason.strip(), 500);
  }

  private void publishReplay(FailedEventMessage failedMessage, EffectiveReplayEvent event) {
    rabbitTemplate.convertAndSend(
        outboxRabbitMqProperties.getExchange(),
        outboxRabbitMqProperties.routingKeyForEventType(event.eventType()),
        event.payload(),
        message -> {
          message.getMessageProperties().setContentType("application/json");
          message.getMessageProperties().setMessageId(event.eventId());
          message.getMessageProperties().setHeader("eventId", event.eventId());
          message.getMessageProperties().setHeader("aggregateType", event.aggregateType());
          message.getMessageProperties().setHeader("aggregateId", event.aggregateId());
          message.getMessageProperties().setHeader("eventType", event.eventType());
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
      FailedEventOperatorContext operator,
      String reason,
      EffectiveReplayEvent event,
      FailedEventReplayAttemptStatus status,
      String errorMessage,
      Instant attemptedAt) {
    failedEventReplayAttemptRepository.save(
        FailedEventReplayAttempt.record(
            failedMessage,
            operator.operatorId(),
            operator.operatorRole(),
            reason,
            request,
            event.eventId(),
            event.eventType(),
            event.aggregateType(),
            event.aggregateId(),
            event.payload(),
            status,
            errorMessage,
            attemptedAt));
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

  private record EffectiveReplayEvent(
      String eventId, String eventType, String aggregateType, String aggregateId, String payload) {}
}
