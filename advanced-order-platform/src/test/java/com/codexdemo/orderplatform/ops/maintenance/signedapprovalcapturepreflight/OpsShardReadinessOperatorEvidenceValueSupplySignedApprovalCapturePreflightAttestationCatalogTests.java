package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalogTests {

    @Test
    void listsTwentyFiveAttestationsWithFailClosedLocks() {
        var attestations = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .allAttestations();

        assertThat(attestations).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                        .ATTESTATION_COUNT);
        assertThat(attestations.stream().map(attestation -> attestation.code()).collect(Collectors.toSet()))
                .hasSize(25);
        assertThat(attestations).allSatisfy(attestation -> {
            assertThat(attestation.status()).isEqualTo("passed");
            assertThat(attestation.attestation()).isNotBlank();
            assertThat(attestation.enforcement()).isNotBlank();
        });
        assertThat(attestations)
                .filteredOn(attestation -> "lock".equals(attestation.category()))
                .hasSize(5);
    }
}
