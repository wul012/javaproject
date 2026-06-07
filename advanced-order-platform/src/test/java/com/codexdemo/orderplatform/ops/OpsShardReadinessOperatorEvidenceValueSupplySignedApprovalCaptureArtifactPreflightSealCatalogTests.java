package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalogTests {

    @Test
    void listsTwentyFiveArtifactSealsWithRejectionCodes() {
        var seals = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                .allSeals();

        assertThat(seals).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                        .SEAL_COUNT);
        assertThat(seals.stream().map(seal -> seal.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(seals).allSatisfy(seal -> {
            assertThat(seal.status()).isEqualTo("passed");
            assertThat(seal.sealRequirement()).isNotBlank();
            assertThat(seal.rejectionCode()).startsWith("reject-");
            assertThat(seal.enforcement()).isNotBlank();
        });
        assertThat(seals).filteredOn(seal -> "lock".equals(seal.category())).hasSize(5);
    }
}
