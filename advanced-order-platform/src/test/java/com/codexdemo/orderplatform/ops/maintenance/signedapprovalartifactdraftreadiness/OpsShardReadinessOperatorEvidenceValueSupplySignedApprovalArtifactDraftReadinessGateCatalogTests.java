package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalogTests {

  @Test
  void listsDraftGatesWithManualDraftAndMaterializationLocked() {
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog
            .allGates();

    assertThat(gates)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog
                .GATE_COUNT);
    assertThat(gates.stream().map(gate -> gate.code()).collect(Collectors.toSet())).hasSize(20);
    assertThat(gates)
        .allSatisfy(
            gate -> {
              assertThat(gate.gate()).isNotBlank();
              assertThat(gate.enforcement()).isNotBlank();
            });
    assertThat(gates)
        .anySatisfy(gate -> assertThat(gate.code()).contains("NO_MANUAL_ARTIFACT_DRAFT"));
    assertThat(gates).anySatisfy(gate -> assertThat(gate.code()).contains("NO_MATERIALIZATION"));
  }
}
