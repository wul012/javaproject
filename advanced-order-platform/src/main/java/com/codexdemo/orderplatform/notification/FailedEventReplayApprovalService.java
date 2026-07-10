package com.codexdemo.orderplatform.notification;

import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.requireOperatorContext;
import static com.codexdemo.orderplatform.notification.FailedEventCommandSupport.truncate;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
final class FailedEventReplayApprovalService {

  private final FailedEventMessageRepository failedEventMessageRepository;
  private final FailedEventReplayApprovalHistoryRepository
      failedEventReplayApprovalHistoryRepository;

  FailedEventReplayApprovalService(
      FailedEventMessageRepository failedEventMessageRepository,
      FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository) {
    this.failedEventMessageRepository = failedEventMessageRepository;
    this.failedEventReplayApprovalHistoryRepository = failedEventReplayApprovalHistoryRepository;
  }

  FailedEventMessageResponse requestReplayApproval(
      Long id,
      RequestFailedEventReplayApprovalRequest request,
      FailedEventOperatorContext operatorContext) {
    FailedEventMessage failedMessage = findFailedMessage(id);
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    String reason = resolveRequestReason(request);
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
    failedMessage.requestReplayApproval(reason, operator.operatorId(), requestedAt);
    failedEventReplayApprovalHistoryRepository.save(
        FailedEventReplayApprovalHistory.record(
            failedMessage,
            FailedEventReplayApprovalHistoryAction.REQUESTED,
            operator.operatorId(),
            operator.operatorRole(),
            reason,
            requestedAt));
    return FailedEventMessageResponse.from(failedMessage);
  }

  FailedEventMessageResponse reviewReplayApproval(
      Long id,
      ReviewFailedEventReplayApprovalRequest request,
      FailedEventOperatorContext operatorContext) {
    FailedEventMessage failedMessage = findFailedMessage(id);
    FailedEventOperatorContext operator = requireOperatorContext(operatorContext);
    FailedEventReplayApprovalStatus reviewStatus = requireReviewStatus(request);
    String note = resolveReviewNote(reviewStatus, request == null ? null : request.note());
    if (failedMessage.getReplayApprovalStatus() != FailedEventReplayApprovalStatus.PENDING) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "replay approval is not pending");
    }
    ensureReviewerIsDifferent(failedMessage, operator.operatorId());
    Instant reviewedAt = Instant.now();
    if (reviewStatus == FailedEventReplayApprovalStatus.APPROVED) {
      failedMessage.approveReplay(operator.operatorId(), note, reviewedAt);
    } else {
      failedMessage.rejectReplay(operator.operatorId(), note, reviewedAt);
    }
    failedEventReplayApprovalHistoryRepository.save(
        FailedEventReplayApprovalHistory.record(
            failedMessage,
            FailedEventReplayApprovalHistoryAction.valueOf(reviewStatus.name()),
            operator.operatorId(),
            operator.operatorRole(),
            note,
            reviewedAt));
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

  private String resolveRequestReason(RequestFailedEventReplayApprovalRequest request) {
    String reason = request == null ? null : request.reason();
    if (reason == null || reason.isBlank()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "replay approval reason is required");
    }
    return truncate(reason.strip(), 500);
  }

  private FailedEventReplayApprovalStatus requireReviewStatus(
      ReviewFailedEventReplayApprovalRequest request) {
    FailedEventReplayApprovalStatus status = request == null ? null : request.status();
    if (status != FailedEventReplayApprovalStatus.APPROVED
        && status != FailedEventReplayApprovalStatus.REJECTED) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "replay approval review status must be APPROVED or REJECTED");
    }
    return status;
  }

  private String resolveReviewNote(FailedEventReplayApprovalStatus status, String note) {
    if (status == FailedEventReplayApprovalStatus.REJECTED && (note == null || note.isBlank())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "replay approval rejection note is required");
    }
    return note == null || note.isBlank() ? null : truncate(note.strip(), 500);
  }

  private void ensureReviewerIsDifferent(FailedEventMessage failedMessage, String reviewerId) {
    String requesterId = failedMessage.getReplayApprovalRequestedBy();
    if (requesterId != null && requesterId.equals(reviewerId)) {
      throw new ResponseStatusException(
          HttpStatus.CONFLICT, "replay approval requester cannot review own request");
    }
  }
}
