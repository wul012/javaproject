package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalogTests {

  @Test
  void listsTwentyArtifactGatesWithClosedExecutionBoundary() {
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
            .allGates();

    assertThat(gates)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                .GATE_COUNT);
    assertThat(gates.stream().map(gate -> gate.code()).collect(Collectors.toSet())).hasSize(20);
    assertThat(gates)
        .allSatisfy(
            gate -> {
              assertThat(gate.gate()).isNotBlank();
              assertThat(gate.enforcement()).isNotBlank();
            });
    assertThat(gates).anySatisfy(gate -> assertThat(gate.code()).contains("NO_WRITE_ROUTE"));
    assertThat(gates).anySatisfy(gate -> assertThat(gate.code()).contains("NO_SIBLING_MUTATION"));
  }
}
