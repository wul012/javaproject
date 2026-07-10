package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService;
import com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight.OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceEvaluationPreflightControllerTests {

  @Test
  void exposesEvaluationPreflightRoutesWithoutAcceptingCandidateEvidence() {
    var controller = controller();

    assertThat(controller.catalog().evaluationRuleCount()).isEqualTo(20);
    assertThat(controller.sourceArtifact().version()).isEqualTo("Java v1051");
    assertThat(controller.identityDigest().readyForEvidenceAcceptance()).isFalse();
    assertThat(controller.policyRuntime().readyForRuntimePayload()).isFalse();
    assertThat(controller.exclusionCloseout().siblingMutationAllowed()).isFalse();
  }

  private OpsShardReadinessComparedEvidenceEvaluationPreflightController controller() {
    return new OpsShardReadinessComparedEvidenceEvaluationPreflightController(
        new OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService(),
        new OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService(),
        new OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService(),
        new OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService(),
        new OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService());
  }
}
