package com.codexdemo.orderplatform.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestFailedEventReplayApprovalRequest(@NotBlank @Size(max = 500) String reason) {}
