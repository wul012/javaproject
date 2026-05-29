package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessOperatorServiceLifecycleResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean operatorOwned,
        boolean runtimeProbeAllowed,
        boolean nodeMayStartService,
        boolean nodeMayStopService,
        String sourceGatePlanVersion,
        String lastVerifiedByNodeVersion,
        String nextNodeConsumerHint,
        String javaServiceOwner,
        String javaStartOwner,
        String javaStopOwner,
        String javaPortDeclaration,
        String javaBaseUrlTemplate,
        List<String> operatorPrerequisites,
        List<String> getOnlySmokeTargets,
        List<String> failClosedRules,
        List<String> cleanupResponsibilities,
        List<String> stopConditions,
        String evidencePath,
        String status
) {
}
