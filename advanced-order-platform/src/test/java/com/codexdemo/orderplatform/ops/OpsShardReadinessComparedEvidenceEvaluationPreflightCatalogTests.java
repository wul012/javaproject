package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogTests {

    @Test
    void catalogListsTwentyRulesAndTwentyFailClosedGuards() {
        var response = new OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService().catalog();

        assertThat(response.version()).isEqualTo("Java v1050");
        assertThat(response.evaluationRuleCount()).isEqualTo(20);
        assertThat(response.passedEvaluationRuleCount()).isEqualTo(20);
        assertThat(response.guardCount()).isEqualTo(20);
        assertThat(response.passedGuardCount()).isEqualTo(20);
        assertThat(response.guards()).allSatisfy(guard -> {
            assertThat(guard.enforcement()).isEqualTo("fail-closed");
            assertThat(guard.rejectionCode()).startsWith("reject-evaluation");
        });
    }

    @Test
    void evaluationRulesKeepNodeVersionContinuityAndUniqueCodes() {
        var rules = OpsShardReadinessComparedEvidenceEvaluationPreflightRuleCatalog.allRules();

        assertThat(rules).hasSize(20);
        assertThat(rules)
                .extracting(OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule::code)
                .doesNotHaveDuplicates()
                .contains("source-intake-readiness", "evaluation-closeout");
        assertThat(rules)
                .extracting(OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule::sourceNodeVersion)
                .contains("Node v1332", "Node v1351");
    }
}
