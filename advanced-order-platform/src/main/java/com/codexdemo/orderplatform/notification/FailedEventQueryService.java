package com.codexdemo.orderplatform.notification;

import static com.codexdemo.orderplatform.notification.FailedEventSearchPageSupport.normalizeExportPageRequest;
import static com.codexdemo.orderplatform.notification.FailedEventSearchPageSupport.normalizePageRequest;
import static com.codexdemo.orderplatform.notification.FailedEventSearchPageSupport.validateSearchId;
import static com.codexdemo.orderplatform.notification.FailedEventSearchPageSupport.validateTimeRange;
import static com.codexdemo.orderplatform.notification.FailedEventSearchSpecifications.failedMessagesMatching;
import static com.codexdemo.orderplatform.notification.FailedEventSearchSpecifications.managementHistoryMatching;
import static com.codexdemo.orderplatform.notification.FailedEventSearchSpecifications.replayApprovalHistoryMatching;
import static com.codexdemo.orderplatform.notification.FailedEventSearchSpecifications.replayAttemptsMatching;
import static java.util.Map.entry;

import com.codexdemo.orderplatform.common.PagedResponse;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
final class FailedEventQueryService {

  private static final Map<String, String> FAILED_MESSAGE_SORT_FIELDS =
      Map.ofEntries(
          entry("id", "id"),
          entry("failedAt", "failedAt"),
          entry("status", "status"),
          entry("eventType", "eventType"),
          entry("aggregateId", "aggregateId"),
          entry("replayCount", "replayCount"),
          entry("managementStatus", "managementStatus"),
          entry("managedAt", "managedAt"),
          entry("replayApprovalStatus", "replayApprovalStatus"),
          entry("replayApprovalRequestedAt", "replayApprovalRequestedAt"),
          entry("replayApprovalReviewedAt", "replayApprovalReviewedAt"));

  private static final Map<String, String> REPLAY_ATTEMPT_SORT_FIELDS =
      Map.of(
          "id", "id",
          "attemptedAt", "attemptedAt",
          "status", "status",
          "operatorId", "operatorId",
          "operatorRole", "operatorRole");

  private static final Map<String, String> MANAGEMENT_HISTORY_SORT_FIELDS =
      Map.of(
          "id", "id",
          "changedAt", "changedAt",
          "previousStatus", "previousStatus",
          "newStatus", "newStatus",
          "operatorId", "operatorId",
          "operatorRole", "operatorRole");

  private static final Map<String, String> REPLAY_APPROVAL_HISTORY_SORT_FIELDS =
      Map.of(
          "id", "id",
          "changedAt", "changedAt",
          "action", "action",
          "operatorId", "operatorId",
          "operatorRole", "operatorRole");

  private final FailedEventMessageRepository failedEventMessageRepository;
  private final FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;
  private final FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;
  private final FailedEventReplayApprovalHistoryRepository
      failedEventReplayApprovalHistoryRepository;
  private final FailedEventReplayProperties failedEventReplayProperties;

  FailedEventQueryService(
      FailedEventMessageRepository failedEventMessageRepository,
      FailedEventReplayAttemptRepository failedEventReplayAttemptRepository,
      FailedEventManagementHistoryRepository failedEventManagementHistoryRepository,
      FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository,
      FailedEventReplayProperties failedEventReplayProperties) {
    this.failedEventMessageRepository = failedEventMessageRepository;
    this.failedEventReplayAttemptRepository = failedEventReplayAttemptRepository;
    this.failedEventManagementHistoryRepository = failedEventManagementHistoryRepository;
    this.failedEventReplayApprovalHistoryRepository = failedEventReplayApprovalHistoryRepository;
    this.failedEventReplayProperties = failedEventReplayProperties;
  }

  List<FailedEventMessageResponse> listRecentFailedMessages() {
    return searchFailedMessages(
            new FailedEventMessageSearchCriteria(null, null, null, null, null, null, null))
        .content();
  }

  PagedResponse<FailedEventMessageResponse> searchFailedMessages(
      FailedEventMessageSearchCriteria criteria) {
    FailedEventMessageSearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventMessageSearchCriteria(null, null, null, null, null, null, null)
            : criteria;
    validateTimeRange(
        normalizedCriteria.failedFrom(), normalizedCriteria.failedTo(), "failedFrom", "failedTo");
    FailedEventSearchPageSupport.NormalizedPageRequest pageRequest =
        normalizePageRequest(
            normalizedCriteria.page(),
            normalizedCriteria.size(),
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            FAILED_MESSAGE_SORT_FIELDS,
            "failedAt,desc");
    Page<FailedEventMessage> page =
        failedEventMessageRepository.findAll(
            failedMessagesMatching(normalizedCriteria), pageRequest.pageRequest());
    return PagedResponse.from(page, FailedEventMessageResponse::from, pageRequest.sort());
  }

  String exportFailedMessagesCsv(FailedEventMessageSearchCriteria criteria) {
    FailedEventMessageSearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventMessageSearchCriteria(null, null, null, null, null, null, null)
            : criteria;
    validateTimeRange(
        normalizedCriteria.failedFrom(), normalizedCriteria.failedTo(), "failedFrom", "failedTo");
    PageRequest pageRequest =
        normalizeExportPageRequest(
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            FAILED_MESSAGE_SORT_FIELDS,
            "failedAt,desc");
    List<FailedEventMessageResponse> messages =
        failedEventMessageRepository
            .findAll(failedMessagesMatching(normalizedCriteria), pageRequest)
            .stream()
            .map(FailedEventMessageResponse::from)
            .toList();
    return FailedEventCsvExporter.failedMessages(messages);
  }

  List<FailedEventManagementHistoryResponse> listManagementHistory(Long failedEventMessageId) {
    validateSearchId(failedEventMessageId, "failedEventMessageId");
    requireFailedEventMessage(failedEventMessageId);
    return failedEventManagementHistoryRepository
        .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedEventMessageId)
        .stream()
        .map(FailedEventManagementHistoryResponse::from)
        .toList();
  }

  PagedResponse<FailedEventManagementHistoryResponse> searchManagementHistory(
      FailedEventManagementHistorySearchCriteria criteria) {
    FailedEventManagementHistorySearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventManagementHistorySearchCriteria(
                null, null, null, null, null, null, null, null)
            : criteria;
    validateSearchId(normalizedCriteria.failedEventMessageId(), "failedEventMessageId");
    validateTimeRange(
        normalizedCriteria.changedFrom(),
        normalizedCriteria.changedTo(),
        "changedFrom",
        "changedTo");
    FailedEventSearchPageSupport.NormalizedPageRequest pageRequest =
        normalizePageRequest(
            normalizedCriteria.page(),
            normalizedCriteria.size(),
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            MANAGEMENT_HISTORY_SORT_FIELDS,
            "changedAt,desc");
    Page<FailedEventManagementHistory> page =
        failedEventManagementHistoryRepository.findAll(
            managementHistoryMatching(normalizedCriteria, failedEventReplayProperties),
            pageRequest.pageRequest());
    return PagedResponse.from(page, FailedEventManagementHistoryResponse::from, pageRequest.sort());
  }

  String exportManagementHistoryCsv(FailedEventManagementHistorySearchCriteria criteria) {
    FailedEventManagementHistorySearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventManagementHistorySearchCriteria(
                null, null, null, null, null, null, null, null)
            : criteria;
    validateSearchId(normalizedCriteria.failedEventMessageId(), "failedEventMessageId");
    validateTimeRange(
        normalizedCriteria.changedFrom(),
        normalizedCriteria.changedTo(),
        "changedFrom",
        "changedTo");
    PageRequest pageRequest =
        normalizeExportPageRequest(
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            MANAGEMENT_HISTORY_SORT_FIELDS,
            "changedAt,desc");
    List<FailedEventManagementHistoryResponse> history =
        failedEventManagementHistoryRepository
            .findAll(
                managementHistoryMatching(normalizedCriteria, failedEventReplayProperties),
                pageRequest)
            .stream()
            .map(FailedEventManagementHistoryResponse::from)
            .toList();
    return FailedEventCsvExporter.managementHistory(history);
  }

  List<FailedEventReplayApprovalHistoryResponse> listReplayApprovalHistory(
      Long failedEventMessageId) {
    validateSearchId(failedEventMessageId, "failedEventMessageId");
    requireFailedEventMessage(failedEventMessageId);
    return failedEventReplayApprovalHistoryRepository
        .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedEventMessageId)
        .stream()
        .map(FailedEventReplayApprovalHistoryResponse::from)
        .toList();
  }

  PagedResponse<FailedEventReplayApprovalHistoryResponse> searchReplayApprovalHistory(
      FailedEventReplayApprovalHistorySearchCriteria criteria) {
    FailedEventReplayApprovalHistorySearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventReplayApprovalHistorySearchCriteria(
                null, null, null, null, null, null, null, null, null, null)
            : criteria;
    validateSearchId(normalizedCriteria.failedEventMessageId(), "failedEventMessageId");
    validateTimeRange(
        normalizedCriteria.changedFrom(),
        normalizedCriteria.changedTo(),
        "changedFrom",
        "changedTo");
    FailedEventSearchPageSupport.NormalizedPageRequest pageRequest =
        normalizePageRequest(
            normalizedCriteria.page(),
            normalizedCriteria.size(),
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            REPLAY_APPROVAL_HISTORY_SORT_FIELDS,
            "changedAt,desc");
    Page<FailedEventReplayApprovalHistory> page =
        failedEventReplayApprovalHistoryRepository.findAll(
            replayApprovalHistoryMatching(normalizedCriteria, failedEventReplayProperties),
            pageRequest.pageRequest());
    return PagedResponse.from(
        page, FailedEventReplayApprovalHistoryResponse::from, pageRequest.sort());
  }

  String exportReplayApprovalHistoryCsv(FailedEventReplayApprovalHistorySearchCriteria criteria) {
    FailedEventReplayApprovalHistorySearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventReplayApprovalHistorySearchCriteria(
                null, null, null, null, null, null, null, null, null, null)
            : criteria;
    validateSearchId(normalizedCriteria.failedEventMessageId(), "failedEventMessageId");
    validateTimeRange(
        normalizedCriteria.changedFrom(),
        normalizedCriteria.changedTo(),
        "changedFrom",
        "changedTo");
    PageRequest pageRequest =
        normalizeExportPageRequest(
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            REPLAY_APPROVAL_HISTORY_SORT_FIELDS,
            "changedAt,desc");
    List<FailedEventReplayApprovalHistoryResponse> history =
        failedEventReplayApprovalHistoryRepository
            .findAll(
                replayApprovalHistoryMatching(normalizedCriteria, failedEventReplayProperties),
                pageRequest)
            .stream()
            .map(FailedEventReplayApprovalHistoryResponse::from)
            .toList();
    return FailedEventCsvExporter.replayApprovalHistory(history);
  }

  List<FailedEventReplayAttemptResponse> listReplayAttempts(Long failedEventMessageId) {
    requireFailedEventMessage(failedEventMessageId);
    return failedEventReplayAttemptRepository
        .findByFailedEventMessageIdOrderByAttemptedAtDescIdDesc(failedEventMessageId)
        .stream()
        .map(FailedEventReplayAttemptResponse::from)
        .toList();
  }

  PagedResponse<FailedEventReplayAttemptResponse> searchReplayAttempts(
      FailedEventReplayAttemptSearchCriteria criteria) {
    FailedEventReplayAttemptSearchCriteria normalizedCriteria =
        criteria == null
            ? new FailedEventReplayAttemptSearchCriteria(null, null, null, null, null, null, null)
            : criteria;
    validateTimeRange(
        normalizedCriteria.attemptedFrom(),
        normalizedCriteria.attemptedTo(),
        "attemptedFrom",
        "attemptedTo");
    FailedEventSearchPageSupport.NormalizedPageRequest pageRequest =
        normalizePageRequest(
            normalizedCriteria.page(),
            normalizedCriteria.size(),
            normalizedCriteria.limit(),
            normalizedCriteria.sort(),
            REPLAY_ATTEMPT_SORT_FIELDS,
            "attemptedAt,desc");
    Page<FailedEventReplayAttempt> page =
        failedEventReplayAttemptRepository.findAll(
            replayAttemptsMatching(normalizedCriteria, failedEventReplayProperties),
            pageRequest.pageRequest());
    return PagedResponse.from(page, FailedEventReplayAttemptResponse::from, pageRequest.sort());
  }

  private void requireFailedEventMessage(Long failedEventMessageId) {
    if (!failedEventMessageRepository.existsById(failedEventMessageId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "failed event message not found");
    }
  }
}
