package com.codexdemo.orderplatform.notification;

public record ReviewFailedEventReplayApprovalRequest(
        FailedEventReplayApprovalStatus status,
        String note
) {
}
