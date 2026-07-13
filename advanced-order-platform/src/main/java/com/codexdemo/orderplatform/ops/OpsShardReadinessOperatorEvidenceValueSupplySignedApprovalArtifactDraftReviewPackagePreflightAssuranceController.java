package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService
      evidencePackageService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
      valuePolicyPackageService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
      embargoPackageService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService
      draftAuthoringGateService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService
      closeoutService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService
          evidencePackageService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
          valuePolicyPackageService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
          embargoPackageService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService
          draftAuthoringGateService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService
          closeoutService) {
    this.evidencePackageService = evidencePackageService;
    this.valuePolicyPackageService = valuePolicyPackageService;
    this.embargoPackageService = embargoPackageService;
    this.draftAuthoringGateService = draftAuthoringGateService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EVIDENCE_PACKAGE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      evidencePackage() {
    return evidencePackageService.evidencePackage();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_VALUE_POLICY_PACKAGE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      valuePolicyPackage() {
    return valuePolicyPackageService.valuePolicyPackage();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EMBARGO_PACKAGE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      embargoPackage() {
    return embargoPackageService.embargoPackage();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_DRAFT_AUTHORING_GATE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      draftAuthoringGate() {
    return draftAuthoringGateService.draftAuthoringGate();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      closeout() {
    return closeoutService.closeout();
  }
}
