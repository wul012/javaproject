package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceEvaluationPreflightServiceTests {

    @Test
    void exposesScopedEvaluationRuleSlicesWithoutOpeningExecution() {
        assertSlice(new OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService().sourceArtifact(),
                "Java v1051");
        assertSlice(new OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService().identityDigest(),
                "Java v1052");
        assertSlice(new OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService().policyRuntime(),
                "Java v1053");
        assertSlice(new OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService().exclusionCloseout(),
                "Java v1054");
    }

    private void assertSlice(
            OpsShardReadinessComparedEvidenceEvaluationPreflightResponse response,
            String version
    ) {
        assertThat(response.version()).isEqualTo(version);
        assertThat(response.evaluationRuleCount()).isEqualTo(5);
        assertThat(response.guardCount()).isEqualTo(5);
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForCandidateEvaluation()).isFalse();
        assertThat(response.readyForApprovalCapture()).isFalse();
    }
}
