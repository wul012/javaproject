package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSignaturePackageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService
      digestPinService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService
      operatorPackageService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSignaturePackageService
      signaturePackageService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService
          catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService
          digestPinService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService
          operatorPackageService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSignaturePackageService
          signaturePackageService) {
    this.catalogService = catalogService;
    this.digestPinService = digestPinService;
    this.operatorPackageService = operatorPackageService;
    this.signaturePackageService = signaturePackageService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CATALOG)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_DIGEST_PINS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      digestPins() {
    return digestPinService.digestPins();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_OPERATOR_PACKAGE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      operatorPackage() {
    return operatorPackageService.operatorPackage();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_SIGNATURE_PACKAGE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      signaturePackage() {
    return signaturePackageService.signaturePackage();
  }
}
