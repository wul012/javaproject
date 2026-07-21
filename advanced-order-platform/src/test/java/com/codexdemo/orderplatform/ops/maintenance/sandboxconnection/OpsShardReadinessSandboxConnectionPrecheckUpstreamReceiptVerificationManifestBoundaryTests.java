package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryTests {

  @Test
  void precheckFieldsEchoTheSevenValueFreeInputs() {
    var response = ManifestTestData.manifest();

    assertThat(response.precheckFields())
        .extracting(
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                    .PrecheckField
                ::id)
        .containsExactly(
            "owner-approval-artifact",
            "credential-handle-review",
            "schema-migration-rehearsal",
            "operator-window",
            "rollback-path",
            "abort-marker",
            "timeout-policy");
    assertThat(response.precheckFields())
        .allSatisfy(
            field -> {
              assertThat(field.echoed()).isTrue();
              assertThat(field.carriesCredentialValue()).isFalse();
            });
  }

  @Test
  void boundaryGuardsKeepAllRuntimeActionsClosed() {
    var response = ManifestTestData.manifest();

    assertThat(response.boundaryGuards())
        .extracting(
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
                    .BoundaryGuard
                ::name)
        .contains(
            "credential-value-read",
            "actual-connection-attempted",
            "schema-migration-sql-executed",
            "deployment-triggered",
            "mini-kv-write-permission-requested",
            "production-window-opened");
    assertThat(response.boundaryGuards())
        .allSatisfy(
            guard -> {
              assertThat(guard.expectedValue()).isFalse();
              assertThat(guard.actualValue()).isFalse();
              assertThat(guard.passed()).isTrue();
            });
  }
}
