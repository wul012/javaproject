package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBatchCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBatchCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .CiBatchPlan> batches() {
        return List.of(
                batch("archive-verification-registry", 1, "focused",
                        "OpsShardReadinessMinimalReadOnlyGateExecutionArchive*Tests", true),
                batch("operator-ci-handoff-registry", 2, "focused",
                        "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoff*Tests", true),
                batch("route-evidence", 3, "grouped",
                        "controller route evidence and shared route path tests", true),
                batch("non-docker-regression", 4, "build",
                        "mvn -q test with Docker-dependent tests remaining guarded", true),
                batch("read-only-smoke", 5, "smoke",
                        "no upstream autostart and no write routing smoke", false)
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .CiBatchPlan batch(
                    String batch,
                    int order,
                    String commandFamily,
                    String scope,
                    boolean blocksNextBatch
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                .CiBatchPlan(
                        batch,
                        order,
                        commandFamily,
                        scope,
                        true,
                        blocksNextBatch
                );
    }
}
