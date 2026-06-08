package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestRuleCatalog {

    private OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestRuleCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.EvaluationRule> identityDigestRules() {
        return List.of(
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("identity-binding",
                        "Node v1337", "identity digest",
                        "Require identity binding without changing runtime identity.",
                        "reject-evaluation-identity-binding-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.IDENTITY_DIGEST),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("digest-lineage",
                        "Node v1338", "identity digest",
                        "Require digest lineage that does not store candidate body content.",
                        "reject-evaluation-digest-lineage-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.IDENTITY_DIGEST),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("signature-envelope-metadata",
                        "Node v1339", "identity digest",
                        "Require signature envelope metadata while signature parsing remains disabled.",
                        "reject-evaluation-signature-envelope-metadata-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.IDENTITY_DIGEST),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("source-evidence-handle",
                        "Node v1340", "identity digest",
                        "Require source evidence handle that points back to read-only intake surfaces.",
                        "reject-evaluation-source-evidence-handle-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.SOURCE_EVIDENCE),
                OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.rule("operator-value-handle",
                        "Node v1341", "identity digest",
                        "Require operator value handle without accepting a credential or value body.",
                        "reject-evaluation-operator-value-handle-missing",
                        OpsShardReadinessComparedPackageReviewEndpointRefs.IDENTITY_DIGEST)
        );
    }
}
