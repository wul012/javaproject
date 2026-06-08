package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceSubmissionSlotCatalog {

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceSubmissionSlotCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .EvidenceSlot> sourceSubmissionSlots() {
        return List.of(
                slot("compared-package-evidence-slot-source-acceptance-precheck", "v1322",
                        "source acceptance precheck evidence contract slot",
                        "Does the future artifact reference Java v1014 acceptance precheck evidence?",
                        "reject-missing-source-acceptance-precheck-evidence",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService
                                .ENDPOINT),
                slot("compared-package-evidence-slot-manual-submission-reference", "v1323",
                        "manual submission reference evidence slot",
                        "Does the future artifact reference the manual submission preflight without accepting it?",
                        "reject-missing-manual-submission-reference-evidence",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
                                .ENDPOINT)
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
            .EvidenceSlot slot(
                    String code,
                    String sourceVersion,
                    String evidenceSlot,
                    String evidenceQuestion,
                    String missingEvidenceGuard,
                    String sourceEndpoint
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
                .slot(code, sourceVersion, evidenceSlot, evidenceQuestion, missingEvidenceGuard, sourceEndpoint);
    }
}

