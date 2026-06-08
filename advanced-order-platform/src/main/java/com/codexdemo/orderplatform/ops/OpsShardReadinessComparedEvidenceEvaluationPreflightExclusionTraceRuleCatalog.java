package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionTraceRuleCatalog {

    private OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionTraceRuleCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule> exclusionTraceRules() {
        return List.of(
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("synthetic-evidence-exclusion",
                        "Node v1347", "exclusion trace",
                        "Reject synthetic compared evidence in this evaluation preflight.",
                        "reject-evaluation-synthetic-evidence-present",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.HANDOFF_CLOSEOUT),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("runtime-payload-exclusion",
                        "Node v1348", "exclusion trace",
                        "Reject any candidate requiring runtime payload import.",
                        "reject-evaluation-runtime-payload-present",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.POLICY_ARCHIVE),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("write-sibling-mutation-exclusion",
                        "Node v1349", "exclusion trace",
                        "Reject candidate paths that write Java, mini-kv, Node, or sibling state.",
                        "reject-evaluation-write-sibling-mutation-present",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.HANDOFF_CLOSEOUT),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("reviewer-traceability",
                        "Node v1350", "exclusion trace",
                        "Require reviewer traceability before evaluation closeout can be displayed.",
                        "reject-evaluation-reviewer-traceability-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.HANDOFF_CLOSEOUT),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("evaluation-closeout",
                        "Node v1351", "exclusion trace",
                        "Render the evaluation preflight closeout without accepting or executing evidence.",
                        "reject-evaluation-closeout-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.HANDOFF_CLOSEOUT)
        );
    }
}
