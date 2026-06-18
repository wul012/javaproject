package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService {

  static final String RESPONSE_VERSION = "Java v1237";
  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-signed-approval-draft-profile-section-registry.v1";

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
      preflightService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService
      readinessService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService
      reviewPackagePreflightService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
      authoringReadinessService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
      instructionPreflightService;

  public OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
          preflightService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService
          readinessService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService
          reviewPackagePreflightService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
          authoringReadinessService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
          instructionPreflightService) {
    this.preflightService = preflightService;
    this.readinessService = readinessService;
    this.reviewPackagePreflightService = reviewPackagePreflightService;
    this.authoringReadinessService = authoringReadinessService;
    this.instructionPreflightService = instructionPreflightService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse registry() {
    var sources =
        OpsShardReadinessSignedApprovalDraftProfileSectionSourceCatalog.sources(
            preflightService.catalog(),
            readinessService.catalog(),
            reviewPackagePreflightService.catalog(),
            authoringReadinessService.catalog(),
            instructionPreflightService.catalog());
    var sections = OpsShardReadinessSignedApprovalDraftProfileSectionCatalog.sections(sources);
    var fieldEntries =
        OpsShardReadinessSignedApprovalDraftProfileSectionFieldCatalog.fieldEntries(sections);
    return OpsShardReadinessSignedApprovalDraftProfileSectionRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessSignedApprovalDraftProfileSectionModuleCatalog.modules(),
        sources,
        sections,
        fieldEntries,
        OpsShardReadinessSignedApprovalDraftProfileSectionRenderer.render(sections, fieldEntries),
        OpsShardReadinessSignedApprovalDraftProfileSectionRouteLockCatalog.routeFieldLocks(
            sections),
        OpsShardReadinessSignedApprovalDraftProfileSectionGateCatalog.gates(),
        List.of(
            "signed-approval-draft-profile-section-registry-service-assembled-from-five-read-only-routes"));
  }
}
