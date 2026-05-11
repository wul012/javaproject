package com.codexdemo.orderplatform.notification;

import java.util.List;

public record FailedEventOperatorContextResponse(
        String operatorId,
        String operatorRole,
        List<String> allowedRoles
) {

    public static FailedEventOperatorContextResponse from(
            FailedEventOperatorContext operatorContext,
            List<String> allowedRoles
    ) {
        return new FailedEventOperatorContextResponse(
                operatorContext.operatorId(),
                operatorContext.operatorRole(),
                allowedRoles
        );
    }
}
