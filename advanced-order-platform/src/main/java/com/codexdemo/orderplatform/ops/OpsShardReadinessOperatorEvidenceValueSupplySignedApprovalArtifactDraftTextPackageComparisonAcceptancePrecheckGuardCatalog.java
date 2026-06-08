package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckGuardCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckGuardCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
            .MissingEvidenceGuard> allGuards() {
        return List.of(
                guard("comparison-acceptance-guard-source-readiness", "source",
                        "Reject when Java v1004 comparison preflight evidence is absent",
                        "reject-missing-comparison-preflight-readiness"),
                guard("comparison-acceptance-guard-identity-request", "identity",
                        "Reject when identity or request comparison evidence is missing",
                        "reject-missing-identity-request-comparison"),
                guard("comparison-acceptance-guard-digest-binding", "digest",
                        "Reject when digest binding or digest recheck comparison evidence is missing",
                        "reject-missing-digest-binding-comparison"),
                guard("comparison-acceptance-guard-detached-signature", "signature",
                        "Reject when detached signature envelope comparison evidence is missing or parsed",
                        "reject-missing-detached-signature-comparison"),
                guard("comparison-acceptance-guard-source-evidence", "source-evidence",
                        "Reject when source evidence handle comparison evidence is missing",
                        "reject-missing-source-evidence-comparison"),
                guard("comparison-acceptance-guard-operator-value", "value",
                        "Reject when operator value handle comparison evidence is missing",
                        "reject-missing-operator-value-comparison"),
                guard("comparison-acceptance-guard-policy-review", "policy",
                        "Reject when policy or review-state comparison evidence is missing",
                        "reject-missing-policy-review-comparison"),
                guard("comparison-acceptance-guard-execution-lock", "execution",
                        "Reject when execution lock, no-runtime, or no-write evidence is missing",
                        "reject-missing-execution-lock-comparison"),
                guard("comparison-acceptance-guard-approval-separation", "approval",
                        "Reject when comparison evidence implies approval grant review",
                        "reject-missing-approval-separation"),
                guard("comparison-acceptance-guard-archive-closeout", "archive",
                        "Reject when archive closeout comparison evidence is missing",
                        "reject-missing-archive-closeout-comparison")
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse
            .MissingEvidenceGuard guard(String code, String category, String guard, String rejectionCode) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport
                .guard(code, category, guard, rejectionCode);
    }
}

