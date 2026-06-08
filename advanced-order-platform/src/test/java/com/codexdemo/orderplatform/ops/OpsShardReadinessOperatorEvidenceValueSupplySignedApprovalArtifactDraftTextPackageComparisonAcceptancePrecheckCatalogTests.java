package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogTests {

    @Test
    void exposesTenCheckpointsAndTenMissingEvidenceGuards() {
        var checkpoints = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog
                .allCheckpoints();
        var guards = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckGuardCatalog
                .allGuards();

        assertThat(checkpoints).hasSize(10);
        assertThat(guards).hasSize(10);
        assertThat(checkpoints).extracting(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
                        .AcceptanceCheckpoint::status
        ).containsOnly("passed");
        assertThat(guards).allSatisfy(guard -> {
            assertThat(guard.enforcement()).isEqualTo("fail-closed");
            assertThat(guard.rejectionCode()).startsWith("reject-missing-");
        });
    }
}

