package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightRuleCatalog {

    private OpsShardReadinessComparedEvidenceEvaluationPreflightRuleCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule> allRules() {
        List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule> rules =
                new ArrayList<>();
        rules.addAll(OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog
                .sourceArtifactRules());
        rules.addAll(OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestRuleCatalog
                .identityDigestRules());
        rules.addAll(OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeRuleCatalog
                .policyRuntimeRules());
        rules.addAll(OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionTraceRuleCatalog
                .exclusionTraceRules());
        return List.copyOf(rules);
    }
}
