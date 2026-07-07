package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogTests {

  @Test
  void catalogCarriesTenSlotsTenGuardsTwentyFieldsAndThirtySixGates() {
    var response =
        new OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService().catalog();

    assertThat(response.version()).isEqualTo("Java v1075");
    assertThat(response.intakeSlotCount()).isEqualTo(10);
    assertThat(response.guardCount()).isEqualTo(10);
    assertThat(response.requiredFieldCount()).isEqualTo(20);
    assertThat(response.passedGateCount()).isEqualTo(36);
    assertThat(response.guards())
        .allSatisfy(
            guard -> {
              assertThat(guard.enforcement()).isEqualTo("fail-closed");
              assertThat(guard.rejectionCode()).startsWith("reject-missing");
            });
  }

  @Test
  void intakeSlotCodesStayUniqueAndCloseoutAware() {
    assertThat(OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog.allSlots())
        .extracting(
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot::code)
        .doesNotHaveDuplicates()
        .contains("source-intake-readiness-document", "candidate-closeout-document");
  }
}
