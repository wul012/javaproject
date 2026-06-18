package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService;

final class OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport {

  private OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport() {}

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
