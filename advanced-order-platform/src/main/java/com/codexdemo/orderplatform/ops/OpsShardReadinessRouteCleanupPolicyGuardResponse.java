package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupPolicyGuardResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String policyGuardEndpoint,
        String policyGuardProfile,
        int guardRuleCount,
        List<GuardRule> guardRules,
        String decision,
        String status
) {

    public record GuardRule(
            String name,
            String blockedCapability,
            boolean allowed,
            String status
    ) {
    }
}
