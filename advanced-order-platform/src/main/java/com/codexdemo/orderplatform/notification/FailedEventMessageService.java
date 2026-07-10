package com.codexdemo.orderplatform.notification;

import com.codexdemo.orderplatform.common.PagedResponse;
import java.util.List;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FailedEventMessageService {

  private final FailedEventOperatorContextResolver operatorContextResolver;
  private final FailedEventReplayProperties failedEventReplayProperties;
  private final FailedEventRecorder failedEventRecorder;
  private final FailedEventQueryService failedEventQueryService;
  private final FailedEventManagementService failedEventManagementService;
  private final FailedEventReplayApprovalService failedEventReplayApprovalService;
  private final FailedEventReplayService failedEventReplayService;

  public FailedEventMessageService(
      FailedEventOperatorContextResolver operatorContextResolver,
      FailedEventReplayProperties failedEventReplayProperties,
      FailedEventRecorder failedEventRecorder,
      FailedEventQueryService failedEventQueryService,
      FailedEventManagementService failedEventManagementService,
      FailedEventReplayApprovalService failedEventReplayApprovalService,
      FailedEventReplayService failedEventReplayService) {
    this.operatorContextResolver = operatorContextResolver;
    this.failedEventReplayProperties = failedEventReplayProperties;
    this.failedEventRecorder = failedEventRecorder;
    this.failedEventQueryService = failedEventQueryService;
    this.failedEventManagementService = failedEventManagementService;
    this.failedEventReplayApprovalService = failedEventReplayApprovalService;
    this.failedEventReplayService = failedEventReplayService;
  }

  @Transactional
  public FailedEventMessage record(Message message, String deadLetterQueue) {
    return failedEventRecorder.record(message, deadLetterQueue);
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
    return failedEventManagementService.markManagementStatus(request, operatorContext);
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
    return failedEventReplayApprovalService.requestReplayApproval(id, request, operatorContext);
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
    return failedEventReplayApprovalService.reviewReplayApproval(id, request, operatorContext);
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
    return failedEventReplayService.replay(id, request, operatorContext);
  }
}
