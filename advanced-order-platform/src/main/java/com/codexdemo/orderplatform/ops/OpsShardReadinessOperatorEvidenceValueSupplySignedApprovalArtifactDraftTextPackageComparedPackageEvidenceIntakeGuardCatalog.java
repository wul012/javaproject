package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeGuardCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeGuardCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .IntakeGuard> allGuards() {
        return List.of(
                guard("compared-package-evidence-guard-source-acceptance-precheck", "source",
                        "Reject when source acceptance precheck evidence is missing",
                        "reject-missing-source-acceptance-precheck-evidence"),
                guard("compared-package-evidence-guard-manual-submission-reference", "submission",
                        "Reject when manual submission reference evidence is missing",
                        "reject-missing-manual-submission-reference-evidence"),
                guard("compared-package-evidence-guard-offline-comparison-result", "comparison",
                        "Reject when offline comparison result evidence is missing or synthetic",
                        "reject-missing-offline-comparison-result-evidence"),
                guard("compared-package-evidence-guard-identity-binding", "identity",
                        "Reject when identity binding evidence is missing",
                        "reject-missing-identity-binding-evidence"),
                guard("compared-package-evidence-guard-digest-match-summary", "digest",
                        "Reject when digest match summary evidence is missing",
                        "reject-missing-digest-match-summary-evidence"),
                guard("compared-package-evidence-guard-detached-signature-observation", "signature",
                        "Reject when detached signature observation evidence is missing or parsed",
                        "reject-missing-detached-signature-observation-evidence"),
                guard("compared-package-evidence-guard-source-value-handles", "value",
                        "Reject when source evidence or operator value handle evidence is missing",
                        "reject-missing-source-value-handle-evidence"),
                guard("compared-package-evidence-guard-policy-execution-lock", "execution",
                        "Reject when policy or execution lock evidence is missing",
                        "reject-missing-policy-execution-lock-evidence"),
                guard("compared-package-evidence-guard-approval-grant-separation", "approval",
                        "Reject when approval grant separation evidence is missing",
                        "reject-missing-approval-grant-separation-evidence"),
                guard("compared-package-evidence-guard-archive-closeout", "archive",
                        "Reject when archive closeout evidence is missing or writes files",
                        "reject-missing-archive-closeout-evidence")
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .IntakeGuard guard(String code, String category, String guard, String rejectionCode) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
                .guard(code, category, guard, rejectionCode);
    }
}

