package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveGateBoundaryTests {

    @Test
    void verifiesTwentyGateChecksFromSourceRegistry() {
        var sourceRegistry =
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport
                        .sourceRegistry();
        var gateChecks =
                OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckVerificationCatalog
                        .gateCheckVerifications(sourceRegistry);

        assertThat(gateChecks).hasSize(
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                        .EXPECTED_GATE_CHECK_VERIFICATION_COUNT);
        assertThat(gateChecks).allSatisfy(check -> {
            assertThat(check.sourcePassed()).isTrue();
            assertThat(check.archived()).isTrue();
            assertThat(check.status()).isEqualTo("passed");
        });
        assertThat(gateChecks)
                .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                        .GateCheckVerification::code)
                .contains("read-targets-five-of-five", "gate-checks-twenty-of-twenty");
    }

    @Test
    void verifiesBoundaryDenialsRemainClosed() {
        var sourceRegistry =
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport
                        .sourceRegistry();
        var boundaries =
                OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationCatalog
                        .boundaryVerifications(sourceRegistry);

        assertThat(boundaries).hasSize(
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                        .EXPECTED_BOUNDARY_VERIFICATION_COUNT);
        assertThat(boundaries).allSatisfy(boundary -> {
            assertThat(boundary.allowed()).isFalse();
            assertThat(boundary.denied()).isTrue();
            assertThat(boundary.status()).isEqualTo("passed");
        });
        assertThat(boundaries)
                .extracting(OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                        .BoundaryVerification::code)
                .contains("no-write-routing", "no-managed-audit-connection", "no-java-autostart");
    }
}
