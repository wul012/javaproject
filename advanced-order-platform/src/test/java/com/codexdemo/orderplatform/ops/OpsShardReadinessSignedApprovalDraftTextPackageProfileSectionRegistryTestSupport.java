package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport {

    private OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport() {
    }

    static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService service() {
        return new OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService(),
                new OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService(),
                new OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService(),
                new OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService());
    }

    static OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse registry() {
        return service().registry();
    }
}
