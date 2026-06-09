package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveScorecardCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveScorecardCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .ScorecardEntry> scorecard(
                    OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry
    ) {
        return List.of(
                score("source-registry", 1, sourceRegistry.status().equals("passed") ? 1 : 0),
                score("archive-artifacts",
                        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                                .EXPECTED_ARTIFACT_VERIFICATION_COUNT,
                        sourceRegistry.archiveRequirementCount()),
                score("read-targets",
                        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                                .EXPECTED_READ_TARGET_VERIFICATION_COUNT,
                        sourceRegistry.passedReadTargetCount()),
                score("gate-checks",
                        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                                .EXPECTED_GATE_CHECK_VERIFICATION_COUNT,
                        sourceRegistry.passedGateCheckCount()),
                score("boundary-denials",
                        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                                .EXPECTED_BOUNDARY_VERIFICATION_COUNT,
                        sourceRegistry.deniedBoundaryRuleCount()),
                score("ci-batches",
                        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                                .EXPECTED_CI_BATCH_VERIFICATION_COUNT,
                        sourceRegistry.ciBatchCount()),
                score("operator-handoffs",
                        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                                .EXPECTED_OPERATOR_HANDOFF_VERIFICATION_COUNT,
                        sourceRegistry.operatorHandoffCount())
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .ScorecardEntry score(String name, int expected, int actual) {
        return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .ScorecardEntry(
                        name,
                        expected,
                        actual,
                        expected == actual ? "passed" : "blocked"
                );
    }
}
