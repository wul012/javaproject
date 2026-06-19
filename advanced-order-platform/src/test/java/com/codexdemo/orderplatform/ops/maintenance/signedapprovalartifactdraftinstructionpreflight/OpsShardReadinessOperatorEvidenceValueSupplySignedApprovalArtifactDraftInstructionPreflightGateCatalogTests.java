package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGateCatalogTests {

  @Test
  void exposesInstructionPreflightGatesBeforeAnyDraftTextPath() {
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog
            .allGates();

    assertThat(gates)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog
                .GATE_COUNT);
    assertThat(gates.stream().map(gate -> gate.code()).collect(Collectors.toSet())).hasSize(20);
    assertThat(gates)
        .allSatisfy(
            gate -> {
              assertThat(gate.code()).startsWith("DRAFT_INSTRUCTION_PREFLIGHT_GATE_");
              assertThat(gate.gate()).isNotBlank();
              assertThat(gate.enforcement()).isEqualTo("fail-closed");
            });
    assertThat(gates.stream().map(gate -> gate.gate()).collect(Collectors.joining(" ")))
        .contains("Signed draft text remains absent", "Closeout stops before real draft text");
  }
}
