package com.codexdemo.orderplatform.notification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewFailedEventReplayApprovalRequest(
    @NotNull FailedEventReplayApprovalStatus status, @Size(max = 500) String note) {}
