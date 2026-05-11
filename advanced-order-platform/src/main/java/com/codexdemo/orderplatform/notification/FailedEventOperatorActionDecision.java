package com.codexdemo.orderplatform.notification;

import java.util.List;

public record FailedEventOperatorActionDecision(
        FailedEventOperatorAction action,
        boolean allowed,
        List<String> allowedRoles
) {
}
