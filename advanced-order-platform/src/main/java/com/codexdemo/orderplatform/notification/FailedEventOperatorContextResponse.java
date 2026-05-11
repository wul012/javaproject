package com.codexdemo.orderplatform.notification;

import java.util.List;
import java.util.Map;

public record FailedEventOperatorContextResponse(
        String operatorId,
        String operatorRole,
        List<String> allowedRoles,
        Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
        Map<FailedEventOperatorAction, FailedEventOperatorActionDecision> actionDecisions,
        List<FailedEventOperatorAction> allowedActions,
        List<FailedEventOperatorAction> deniedActions
) {

    public static FailedEventOperatorContextResponse from(
            FailedEventOperatorContext operatorContext,
            List<String> allowedRoles,
            Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
            Map<FailedEventOperatorAction, FailedEventOperatorActionDecision> actionDecisions,
            List<FailedEventOperatorAction> allowedActions,
            List<FailedEventOperatorAction> deniedActions
    ) {
        return new FailedEventOperatorContextResponse(
                operatorContext.operatorId(),
                operatorContext.operatorRole(),
                allowedRoles,
                allowedRolesByAction,
                actionDecisions,
                allowedActions,
                deniedActions
        );
    }
}
