package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalogTests {

  @Test
  void listsDraftPreflightGatesWithManualDraftLocked() {
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
            .allGates();

    assertThat(gates)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                .GATE_COUNT);
    assertThat(gates.stream().map(gate -> gate.code()).collect(Collectors.toSet())).hasSize(20);
    assertThat(gates)
        .allSatisfy(
            gate -> {
              assertThat(gate.gate()).isNotBlank();
              assertThat(gate.enforcement()).isNotBlank();
            });
    assertThat(gates).anySatisfy(gate -> assertThat(gate.code()).contains("NO_REAL_MANUAL_DRAFT"));
    assertThat(gates)
        .anySatisfy(gate -> assertThat(gate.code()).contains("NO_DRAFT_MATERIALIZATION"));
  }
}
