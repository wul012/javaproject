package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffVerificationCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffVerificationCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .OperatorHandoffVerification> operatorHandoffVerifications(
                    OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry
    ) {
        return sourceRegistry.operatorHandoffs().stream()
                .map(handoff -> new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                        .OperatorHandoffVerification(
                                handoff.step(),
                                handoff.owner(),
                                handoff.manual(),
                                handoff.manual() ? "passed" : "blocked"
                        ))
                .toList();
    }
}
