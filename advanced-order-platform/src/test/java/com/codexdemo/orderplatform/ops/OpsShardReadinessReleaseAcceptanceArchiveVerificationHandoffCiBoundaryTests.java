package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCiBoundaryTests {

    @Test
    void preservesReadOnlyCiProofOrder() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.ciProofCount()).isEqualTo(5);
        assertThat(response.passedCiProofCount()).isEqualTo(5);
        assertThat(response.ciProofs())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .CiProof::commandFamily)
                .containsExactly("focused", "focused", "grouped", "build", "smoke");
        assertThat(response.ciProofs())
                .allSatisfy(ci -> {
                    assertThat(ci.readOnly()).isTrue();
                    assertThat(ci.sourcePassed()).isTrue();
                    assertThat(ci.status()).isEqualTo("passed");
                });
    }

    @Test
    void keepsBoundaryGuardsLockedForArchiveVerification() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.boundaryGuardCount()).isEqualTo(8);
        assertThat(response.lockedBoundaryGuardCount()).isEqualTo(8);
        assertThat(response.boundaryGuards())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .BoundaryGuard::code)
                .contains("no-java-autostart", "no-mini-kv-autostart", "no-write-routing");
        assertThat(response.boundaryGuards())
                .allSatisfy(guard -> {
                    assertThat(guard.locked()).isTrue();
                    assertThat(guard.status()).isEqualTo("passed");
                });
    }
}
