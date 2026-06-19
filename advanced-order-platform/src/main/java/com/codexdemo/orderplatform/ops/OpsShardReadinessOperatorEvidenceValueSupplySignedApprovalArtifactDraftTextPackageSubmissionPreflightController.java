package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightDigestSignatureService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightPolicyExecutionCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService
      identityService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightDigestSignatureService
      digestSignatureService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService
      evidenceValueService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightPolicyExecutionCloseoutService
      policyExecutionCloseoutService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
          catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightIdentityService
          identityService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightDigestSignatureService
          digestSignatureService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService
          evidenceValueService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightPolicyExecutionCloseoutService
          policyExecutionCloseoutService) {
    this.catalogService = catalogService;
    this.identityService = identityService;
    this.digestSignatureService = digestSignatureService;
    this.evidenceValueService = evidenceValueService;
    this.policyExecutionCloseoutService = policyExecutionCloseoutService;
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_CATALOG)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_IDENTITY)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      identity() {
    return identityService.identity();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_DIGEST_SIGNATURE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      digestSignature() {
    return digestSignatureService.digestSignature();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_EVIDENCE_VALUE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      evidenceValue() {
    return evidenceValueService.evidenceValue();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_POLICY_EXECUTION_CLOSEOUT)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      policyExecutionCloseout() {
    return policyExecutionCloseoutService.policyExecutionCloseout();
  }
}
