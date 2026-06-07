package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalogTests {

    @Test
    void listsControlBlockersWithNodeRejectCodes() {
        var blockers = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                .allBlockers();

        assertThat(blockers).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                        .BLOCKER_COUNT);
        assertThat(blockers.stream().map(blocker -> blocker.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(blockers).allSatisfy(blocker -> {
            assertThat(blocker.status()).isEqualTo("passed");
            assertThat(blocker.blocker()).isNotBlank();
            assertThat(blocker.rejectionCode()).startsWith("REJECT_DRAFT_READINESS_");
            assertThat(blocker.enforcement()).isNotBlank();
        });
        assertThat(blockers).filteredOn(blocker -> "embargo".equals(blocker.category())).hasSize(5);
    }
}
