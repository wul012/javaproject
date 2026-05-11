package com.codexdemo.orderplatform.notification;

import com.codexdemo.orderplatform.common.PagedResponse;
import java.time.Instant;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failed-events")
public class FailedEventMessageController {

    private static final MediaType TEXT_CSV = MediaType.parseMediaType("text/csv; charset=UTF-8");

    private final FailedEventMessageService failedEventMessageService;

    private final FailedEventOperatorContextResolver operatorContextResolver;

    public FailedEventMessageController(
            FailedEventMessageService failedEventMessageService,
            FailedEventOperatorContextResolver operatorContextResolver
    ) {
        this.failedEventMessageService = failedEventMessageService;
        this.operatorContextResolver = operatorContextResolver;
    }

    @GetMapping
    public PagedResponse<FailedEventMessageResponse> searchFailedMessages(
            @RequestParam(required = false) FailedEventMessageStatus status,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String aggregateType,
            @RequestParam(required = false) String aggregateId,
            @RequestParam(required = false) FailedEventManagementStatus managementStatus,
            @RequestParam(required = false) FailedEventReplayApprovalStatus replayApprovalStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant failedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant failedTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        return failedEventMessageService.searchFailedMessages(new FailedEventMessageSearchCriteria(
                status,
                eventType,
                aggregateType,
                aggregateId,
                managementStatus,
                replayApprovalStatus,
                failedFrom,
                failedTo,
                page,
                size,
                sort,
                limit
        ));
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public ResponseEntity<String> exportFailedMessages(
            @RequestParam(required = false) FailedEventMessageStatus status,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) String aggregateType,
            @RequestParam(required = false) String aggregateId,
            @RequestParam(required = false) FailedEventManagementStatus managementStatus,
            @RequestParam(required = false) FailedEventReplayApprovalStatus replayApprovalStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant failedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant failedTo,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        String csv = failedEventMessageService.exportFailedMessagesCsv(new FailedEventMessageSearchCriteria(
                status,
                eventType,
                aggregateType,
                aggregateId,
                managementStatus,
                replayApprovalStatus,
                failedFrom,
                failedTo,
                null,
                null,
                sort,
                limit
        ));
        return csvResponse("failed-events.csv", csv);
    }

    @GetMapping("/replay-attempts")
    public PagedResponse<FailedEventReplayAttemptResponse> searchReplayAttempts(
            @RequestParam(required = false) Long failedEventMessageId,
            @RequestParam(required = false) FailedEventReplayAttemptStatus status,
            @RequestParam(required = false) String operatorId,
            @RequestParam(required = false) String operatorRole,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant attemptedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant attemptedTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        return failedEventMessageService.searchReplayAttempts(new FailedEventReplayAttemptSearchCriteria(
                failedEventMessageId,
                status,
                operatorId,
                operatorRole,
                attemptedFrom,
                attemptedTo,
                page,
                size,
                sort,
                limit
        ));
    }

    @GetMapping("/management-history")
    public PagedResponse<FailedEventManagementHistoryResponse> searchManagementHistory(
            @RequestParam(required = false) Long failedEventMessageId,
            @RequestParam(required = false) FailedEventManagementStatus previousStatus,
            @RequestParam(required = false) FailedEventManagementStatus newStatus,
            @RequestParam(required = false) String operatorId,
            @RequestParam(required = false) String operatorRole,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        return failedEventMessageService.searchManagementHistory(new FailedEventManagementHistorySearchCriteria(
                failedEventMessageId,
                previousStatus,
                newStatus,
                operatorId,
                operatorRole,
                changedFrom,
                changedTo,
                page,
                size,
                sort,
                limit
        ));
    }

    @GetMapping(value = "/management-history/export", produces = "text/csv")
    public ResponseEntity<String> exportManagementHistory(
            @RequestParam(required = false) Long failedEventMessageId,
            @RequestParam(required = false) FailedEventManagementStatus previousStatus,
            @RequestParam(required = false) FailedEventManagementStatus newStatus,
            @RequestParam(required = false) String operatorId,
            @RequestParam(required = false) String operatorRole,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedTo,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        String csv = failedEventMessageService.exportManagementHistoryCsv(new FailedEventManagementHistorySearchCriteria(
                failedEventMessageId,
                previousStatus,
                newStatus,
                operatorId,
                operatorRole,
                changedFrom,
                changedTo,
                null,
                null,
                sort,
                limit
        ));
        return csvResponse("failed-event-management-history.csv", csv);
    }

    @GetMapping("/replay-approval-history")
    public PagedResponse<FailedEventReplayApprovalHistoryResponse> searchReplayApprovalHistory(
            @RequestParam(required = false) Long failedEventMessageId,
            @RequestParam(required = false) FailedEventReplayApprovalHistoryAction action,
            @RequestParam(required = false) String operatorId,
            @RequestParam(required = false) String operatorRole,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        return failedEventMessageService.searchReplayApprovalHistory(new FailedEventReplayApprovalHistorySearchCriteria(
                failedEventMessageId,
                action,
                operatorId,
                operatorRole,
                changedFrom,
                changedTo,
                page,
                size,
                sort,
                limit
        ));
    }

    @GetMapping(value = "/replay-approval-history/export", produces = "text/csv")
    public ResponseEntity<String> exportReplayApprovalHistory(
            @RequestParam(required = false) Long failedEventMessageId,
            @RequestParam(required = false) FailedEventReplayApprovalHistoryAction action,
            @RequestParam(required = false) String operatorId,
            @RequestParam(required = false) String operatorRole,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant changedTo,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit
    ) {
        String csv = failedEventMessageService.exportReplayApprovalHistoryCsv(new FailedEventReplayApprovalHistorySearchCriteria(
                failedEventMessageId,
                action,
                operatorId,
                operatorRole,
                changedFrom,
                changedTo,
                null,
                null,
                sort,
                limit
        ));
        return csvResponse("failed-event-replay-approval-history.csv", csv);
    }

    @GetMapping("/{id}/replay-attempts")
    public List<FailedEventReplayAttemptResponse> listReplayAttempts(@PathVariable Long id) {
        return failedEventMessageService.listReplayAttempts(id);
    }

    @GetMapping("/{id}/management-history")
    public List<FailedEventManagementHistoryResponse> listManagementHistory(@PathVariable Long id) {
        return failedEventMessageService.listManagementHistory(id);
    }

    @GetMapping("/{id}/replay-approval-history")
    public List<FailedEventReplayApprovalHistoryResponse> listReplayApprovalHistory(@PathVariable Long id) {
        return failedEventMessageService.listReplayApprovalHistory(id);
    }

    @GetMapping("/operator-context")
    public FailedEventOperatorContextResponse resolveOperatorContext(@RequestHeader HttpHeaders headers) {
        return FailedEventOperatorContextResponse.from(
                operatorContextResolver.resolve(headers),
                operatorContextResolver.allowedRoles(),
                operatorContextResolver.allowedRolesByAction()
        );
    }

    @PostMapping("/management-status")
    public FailedEventManagementBatchResponse markManagementStatus(
            @RequestHeader HttpHeaders headers,
            @RequestBody MarkFailedEventManagementRequest request
    ) {
        return failedEventMessageService.markManagementStatus(
                request,
                operatorContextResolver.resolve(headers, FailedEventOperatorAction.MANAGE_FAILED_EVENT)
        );
    }

    @PostMapping("/{id}/replay")
    public FailedEventMessageResponse replayFailedMessage(
            @PathVariable Long id,
            @RequestHeader HttpHeaders headers,
            @RequestBody(required = false) ReplayFailedEventRequest request
    ) {
        return failedEventMessageService.replay(
                id,
                request,
                operatorContextResolver.resolve(headers, FailedEventOperatorAction.REPLAY_FAILED_EVENT)
        );
    }

    @PostMapping("/{id}/replay-approval")
    public FailedEventMessageResponse requestReplayApproval(
            @PathVariable Long id,
            @RequestHeader HttpHeaders headers,
            @RequestBody(required = false) RequestFailedEventReplayApprovalRequest request
    ) {
        return failedEventMessageService.requestReplayApproval(
                id,
                request,
                operatorContextResolver.resolve(headers, FailedEventOperatorAction.REQUEST_REPLAY_APPROVAL)
        );
    }

    @PostMapping("/{id}/replay-approval/review")
    public FailedEventMessageResponse reviewReplayApproval(
            @PathVariable Long id,
            @RequestHeader HttpHeaders headers,
            @RequestBody(required = false) ReviewFailedEventReplayApprovalRequest request
    ) {
        return failedEventMessageService.reviewReplayApproval(
                id,
                request,
                operatorContextResolver.resolve(headers, FailedEventOperatorAction.REVIEW_REPLAY_APPROVAL)
        );
    }

    private ResponseEntity<String> csvResponse(String filename, String csv) {
        return ResponseEntity.ok()
                .contentType(TEXT_CSV)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(csv);
    }
}
