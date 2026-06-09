package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport {

    private OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport() {
    }

    static OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService service() {
        return new OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService());
    }

    static OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse registry() {
        return service().registry();
    }
}
