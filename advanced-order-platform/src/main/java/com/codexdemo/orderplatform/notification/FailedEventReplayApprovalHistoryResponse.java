package com.codexdemo.orderplatform.notification;

import java.time.Instant;

public record FailedEventReplayApprovalHistoryResponse(
        Long id,
        Long failedEventMessageId,
        FailedEventReplayApprovalHistoryAction action,
        String operatorId,
        String operatorRole,
        String note,
        Instant changedAt
) {

    static FailedEventReplayApprovalHistoryResponse from(FailedEventReplayApprovalHistory history) {
        return new FailedEventReplayApprovalHistoryResponse(
                history.getId(),
                history.getFailedEventMessage().getId(),
                history.getAction(),
                history.getOperatorId(),
                history.getOperatorRole(),
                history.getNote(),
                history.getChangedAt()
        );
    }
}
