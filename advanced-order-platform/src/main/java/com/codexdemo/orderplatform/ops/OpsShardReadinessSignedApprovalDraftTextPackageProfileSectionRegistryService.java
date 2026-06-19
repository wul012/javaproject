package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService {

  static final String RESPONSE_VERSION = "Java v1287";
  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.SIGNED_APPROVAL_DRAFT_TEXT_PACKAGE_PROFILE_SECTION_REGISTRY;
  static final String PROFILE =
      "java-shard-readiness-signed-approval-draft-text-package-profile-section-registry.v1";

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService
      intakeService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService
      reviewPreflightService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
      submissionPreflightService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService
      comparisonPreflightService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService
      acceptancePrecheckService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService
      comparedPackageEvidenceIntakeService;
  private final OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService
      evaluationPreflightService;
  private final OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService
      candidateBlueprintService;
  private final OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService
      candidateIntakePreflightService;

  public OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService
          intakeService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService
          reviewPreflightService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
          submissionPreflightService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService
          comparisonPreflightService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService
          acceptancePrecheckService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService
          comparedPackageEvidenceIntakeService,
      OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService evaluationPreflightService,
      OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService candidateBlueprintService,
      OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService
          candidateIntakePreflightService) {
    this.intakeService = intakeService;
    this.reviewPreflightService = reviewPreflightService;
    this.submissionPreflightService = submissionPreflightService;
    this.comparisonPreflightService = comparisonPreflightService;
    this.acceptancePrecheckService = acceptancePrecheckService;
    this.comparedPackageEvidenceIntakeService = comparedPackageEvidenceIntakeService;
    this.evaluationPreflightService = evaluationPreflightService;
    this.candidateBlueprintService = candidateBlueprintService;
    this.candidateIntakePreflightService = candidateIntakePreflightService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse registry() {
    var sources =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalog.sources(
            intakeService.catalog(),
            reviewPreflightService.catalog(),
            submissionPreflightService.catalog(),
            comparisonPreflightService.catalog(),
            acceptancePrecheckService.catalog(),
            comparedPackageEvidenceIntakeService.catalog(),
            evaluationPreflightService.catalog(),
            candidateBlueprintService.catalog(),
            candidateIntakePreflightService.catalog());
    var sections =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionCatalog.sections(sources);
    var fieldEntries =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionFieldCatalog.fieldEntries(
            sections);
    return OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistrySupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionModuleCatalog.modules(),
        sources,
        sections,
        fieldEntries,
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRenderer.render(
            sections, fieldEntries),
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRouteLockCatalog
            .routeFieldLocks(sections),
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionGateCatalog.gates(),
        List.of(
            "signed-approval-draft-text-package-profile-section-registry-service-assembled-from-nine-read-only-routes",
            "signed-approval-draft-text-package-profile-section-registry-node-v1531-renderer-split-aligned"));
  }
}
