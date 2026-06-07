package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalogTests {

    @Test
    void listsPackageGuardsWithRejectCodes() {
        var guards = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .allGuards();

        assertThat(guards).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                        .GUARD_COUNT);
        assertThat(guards.stream().map(guard -> guard.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(guards).allSatisfy(guard -> {
            assertThat(guard.status()).isEqualTo("passed");
            assertThat(guard.guard()).isNotBlank();
            assertThat(guard.rejectionCode()).startsWith("REJECT_REVIEW_PACKAGE_PREFLIGHT_");
            assertThat(guard.enforcement()).isNotBlank();
        });
        assertThat(guards).filteredOn(guard -> "embargo".equals(guard.category())).hasSize(5);
    }
}
