package com.codexdemo.orderplatform.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReplayFailedEventRequest(
    @Size(max = 36) String eventId,
    @Size(max = 100) String eventType,
    @Size(max = 100) String aggregateType,
    @Size(max = 100) String aggregateId,
    String payload,
    @NotBlank @Size(max = 500) String reason) {}
