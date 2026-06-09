package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch>
            ciBatches() {
        return List.of(
                batch("focused-registry-tests", 1, "focused", "new registry service and catalog tests", true),
                batch("grouped-route-tests", 2, "grouped", "controller and route evidence tests", true),
                batch("build-validation", 3, "build", "Maven compile and non-Docker regression", true),
                batch("read-only-smoke", 4, "smoke", "read-only gate output smoke", false)
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch
            batch(
                    String name,
                    int order,
                    String commandFamily,
                    String scope,
                    boolean blocksNextBatch
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.CiBatch(
                name,
                order,
                commandFamily,
                scope,
                blocksNextBatch
        );
    }
}
