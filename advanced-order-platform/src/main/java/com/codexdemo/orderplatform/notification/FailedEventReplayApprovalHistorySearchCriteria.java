package com.codexdemo.orderplatform.notification;

import java.time.Instant;

public record FailedEventReplayApprovalHistorySearchCriteria(
        Long failedEventMessageId,
        FailedEventReplayApprovalHistoryAction action,
        String operatorId,
        String operatorRole,
        Instant changedFrom,
        Instant changedTo,
        Integer page,
        Integer size,
        String sort,
        Integer limit
) {

    public FailedEventReplayApprovalHistorySearchCriteria(
            Long failedEventMessageId,
            FailedEventReplayApprovalHistoryAction action,
            String operatorId,
            String operatorRole,
            Instant changedFrom,
            Instant changedTo,
            Integer limit
    ) {
        this(
                failedEventMessageId,
                action,
                operatorId,
                operatorRole,
                changedFrom,
                changedTo,
                null,
                null,
                null,
                limit
        );
    }
}
