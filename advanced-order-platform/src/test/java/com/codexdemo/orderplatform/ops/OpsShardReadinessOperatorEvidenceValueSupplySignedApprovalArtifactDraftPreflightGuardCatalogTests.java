package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalogTests {

    @Test
    void listsDraftGuardsWithRejectionCodes() {
        var guards = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                .allGuards();

        assertThat(guards).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                        .GUARD_COUNT);
        assertThat(guards.stream().map(guard -> guard.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(guards).allSatisfy(guard -> {
            assertThat(guard.status()).isEqualTo("passed");
            assertThat(guard.guardRequirement()).isNotBlank();
            assertThat(guard.rejectionCode()).startsWith("reject-");
            assertThat(guard.enforcement()).isNotBlank();
        });
        assertThat(guards).filteredOn(guard -> "lock".equals(guard.category())).hasSize(5);
    }
}
