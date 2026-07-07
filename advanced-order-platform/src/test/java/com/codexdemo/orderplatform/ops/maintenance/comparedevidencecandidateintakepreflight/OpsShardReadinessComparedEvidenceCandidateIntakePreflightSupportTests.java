package com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupportTests {

  @Test
  void buildsWaitingForRealDocumentStateWithoutImportsOrEvaluation() {
    var response =
        OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.response(
            "Java v1065",
            "/ops/shard-readiness/test",
            "test-profile",
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog
                .sourceSlots(),
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.sourceGuards(),
            OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGates(),
            List.of("unit-extra-check"));

    assertThat(response.sourcePlan()).isEqualTo("Node v1371");
    assertThat(response.intakeState())
        .isEqualTo("waiting-for-real-compared-package-evidence-candidate-document");
    assertThat(response.realDocumentCount()).isZero();
    assertThat(response.requiredFieldCount()).isEqualTo(20);
    assertThat(response.passedGateCount()).isEqualTo(36);
    assertThat(response.payloadImportAllowed()).isFalse();
    assertThat(response.candidateEvaluationAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.status()).isEqualTo("passed");
  }
}
