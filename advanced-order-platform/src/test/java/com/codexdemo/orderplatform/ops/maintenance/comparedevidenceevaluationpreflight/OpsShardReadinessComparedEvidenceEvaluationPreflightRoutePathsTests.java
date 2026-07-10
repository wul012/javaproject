package com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePathsTests {

  @Test
  void evaluationPreflightRoutesRemainReadOnlyPreflightSurfaces() {
    assertThat(OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.CATALOG)
        .endsWith("compared-evidence-evaluation-preflight-catalog");
    assertThat(OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.SOURCE_ARTIFACT)
        .endsWith("compared-evidence-evaluation-preflight-source-artifact");
    assertThat(OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.IDENTITY_DIGEST)
        .endsWith("compared-evidence-evaluation-preflight-identity-digest");
    assertThat(OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.POLICY_RUNTIME)
        .endsWith("compared-evidence-evaluation-preflight-policy-runtime");
    assertThat(OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.EXCLUSION_CLOSEOUT)
        .endsWith("compared-evidence-evaluation-preflight-exclusion-closeout");
  }
}
