package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckVerificationCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckVerificationCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .GateCheckVerification> gateCheckVerifications(
                    OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry
    ) {
        return sourceRegistry.gateChecks().stream()
                .map(check -> new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                        .GateCheckVerification(
                                check.code(),
                                check.group(),
                                check.passed(),
                                check.passed(),
                                check.passed() ? "passed" : "blocked"
                        ))
                .toList();
    }
}
