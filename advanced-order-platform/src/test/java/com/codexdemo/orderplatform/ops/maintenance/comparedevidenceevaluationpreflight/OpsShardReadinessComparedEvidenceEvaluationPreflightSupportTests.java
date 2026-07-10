package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceEvaluationPreflightSupportTests {

  @Test
  void buildsReadOnlyEvaluationPreflightWithoutCandidateOrRuntime() {
    var response =
        OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.response(
            "Java v1045",
            "/ops/shard-readiness/test",
            "test-profile",
            OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog
                .sourceArtifactRules(),
            OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.sourceArtifactGuards(),
            List.of("unit-extra-check"));

    assertThat(response.sourcePlan()).isEqualTo("Node v1351");
    assertThat(response.candidateEvidenceState()).isEqualTo("candidate-absent");
    assertThat(response.readyForCandidateEvaluation()).isFalse();
    assertThat(response.readyForEvidenceAcceptance()).isFalse();
    assertThat(response.readyForApprovalCapture()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.status()).isEqualTo("passed");
    assertThat(response.checks())
        .contains(
            "unit-extra-check", "compared-evidence-evaluation-preflight-source-java-Java v1044");
  }
}
