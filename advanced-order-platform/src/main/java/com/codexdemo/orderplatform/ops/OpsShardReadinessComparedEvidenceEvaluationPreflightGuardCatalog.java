package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog {

    private OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> allGuards() {
        return OpsShardReadinessComparedEvidenceEvaluationPreflightRuleCatalog.allRules().stream()
                .map(rule -> OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.guard(
                        rule.code() + "-guard",
                        rule.evaluationArea(),
                        "Fail closed when " + rule.rule(),
                        rule.missingCandidateGuard()))
                .toList();
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> sourceArtifactGuards() {
        return allGuards().subList(0, 5);
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> identityDigestGuards() {
        return allGuards().subList(5, 10);
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> policyRuntimeGuards() {
        return allGuards().subList(10, 15);
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationGuard> exclusionTraceGuards() {
        return allGuards().subList(15, 20);
    }
}
