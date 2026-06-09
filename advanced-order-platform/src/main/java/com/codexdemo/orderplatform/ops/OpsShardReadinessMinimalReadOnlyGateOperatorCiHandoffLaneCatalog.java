package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .OperatorLane> lanes(
                    OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse archive
    ) {
        return List.of(
                lane("focused", 1, "ci-operator", archive.version(),
                        "Run focused registry and catalog tests first."),
                lane("grouped", 2, "ci-operator", archive.version(),
                        "Run grouped controller and route evidence tests after focused success."),
                lane("build", 3, "build-operator", archive.version(),
                        "Run Maven compile and non-Docker regression before smoke."),
                lane("smoke", 4, "release-operator", archive.version(),
                        "Run read-only smoke only after build validation passes.")
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .OperatorLane lane(
                    String lane,
                    int order,
                    String owner,
                    String sourceEvidence,
                    String instruction
            ) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                .OperatorLane(
                        lane,
                        order,
                        owner,
                        sourceEvidence,
                        true,
                        instruction
                );
    }
}
