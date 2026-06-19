package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalogTests {

  @Test
  void exposesOneFailClosedGuardPerInstructionSlot() {
    var guards =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog
            .allGuards();

    assertThat(guards)
        .hasSize(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog
                .GUARD_COUNT);
    assertThat(guards.stream().map(guard -> guard.code()).collect(Collectors.toSet())).hasSize(25);
    assertThat(guards)
        .allSatisfy(
            guard -> {
              assertThat(guard.code()).endsWith("_SLOT_GUARD");
              assertThat(guard.rejectionCode()).startsWith("REJECT_DRAFT_INSTRUCTION_PREFLIGHT_");
              assertThat(guard.enforcement()).isEqualTo("fail-closed");
              assertThat(guard.status()).isEqualTo("passed");
            });
  }
}
