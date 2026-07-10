package com.codexdemo.orderplatform.notification;

import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.requireOperatorContext;
import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.truncate;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
final class FailedEventManagementService {

  private final FailedEventMessageRepository failedEventMessageRepository;
  private final FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;

  FailedEventManagementService(
      FailedEventMessageRepository failedEventMessageRepository,
      FailedEventManagementHistoryRepository failedEventManagementHistoryRepository) {
    this.failedEventMessageRepository = failedEventMessageRepository;
    this.failedEventManagementHistoryRepository = failedEventManagementHistoryRepository;
  }

  FailedEventManagementBatchResponse markManagementStatus(
      MarkFailedEventManagementRequest request, FailedEventOperatorContext operatorContext) {
    if (request == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "request body is required");
    }
    List<Long> ids = normalizeIds(request.ids());
    FailedEventManagementStatus managementStatus = requireStatus(request.status());
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String note = resolveNote(request.note());
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
              managementStatus, note, operator.operatorId(), managedAt);
          failedEventManagementHistoryRepository.save(
              FailedEventManagementHistory.record(
                  failedMessage,
                  previousStatus,
                  managementStatus,
                  operator.operatorId(),
                  operator.operatorRole(),
                  note,
                  managedAt));
        });
    return new FailedEventManagementBatchResponse(
        managementStatus,
        failedMessages.size(),
        failedMessages.stream().map(FailedEventMessageResponse::from).toList());
  }

  private List<Long> normalizeIds(List<Long> ids) {
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

  private FailedEventManagementStatus requireStatus(FailedEventManagementStatus managementStatus) {
    if (managementStatus == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "management status is required");
    }
    return managementStatus;
  }

  private String resolveNote(String note) {
    if (note == null || note.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "management note is required");
    }
    return truncate(note.strip(), 500);
  }
}
