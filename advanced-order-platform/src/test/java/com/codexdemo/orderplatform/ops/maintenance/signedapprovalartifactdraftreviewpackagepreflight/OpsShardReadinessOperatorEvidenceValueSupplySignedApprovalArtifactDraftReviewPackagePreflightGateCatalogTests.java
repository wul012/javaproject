package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGateCatalogTests {

  @Test
  void listsReviewPackageGatesWithDraftAuthoringLocked() {
    var gates =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
            .allGates();

    assertThat(gates)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .GATE_COUNT);
    assertThat(gates.stream().map(gate -> gate.code()).collect(Collectors.toSet())).hasSize(20);
    assertThat(gates)
        .allSatisfy(
            gate -> {
              assertThat(gate.gate()).isNotBlank();
              assertThat(gate.enforcement()).isNotBlank();
            });
    assertThat(gates).anySatisfy(gate -> assertThat(gate.code()).contains("NO_SIGNED_DRAFT"));
    assertThat(gates)
        .anySatisfy(gate -> assertThat(gate.code()).contains("NEXT_STEP_HUMAN_DRAFT_PLAN"));
  }
}
