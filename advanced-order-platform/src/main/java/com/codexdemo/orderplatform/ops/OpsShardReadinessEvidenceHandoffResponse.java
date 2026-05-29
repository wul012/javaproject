package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessEvidenceHandoffResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourceIndexVersion,
        String sourceVerificationVersion,
        String lastConsumedByNodeVersion,
        List<String> completedEvidenceVersions,
        List<String> handoffArtifacts,
        List<String> consumerRules,
        List<String> stopConditions,
        String evidencePath,
        String status
) {
}
