package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService
      captureDigestBindingService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService
      templateReviewDigestService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService
      operatorFragmentService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService
      capturePolicyFragmentService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService
          catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService
          captureDigestBindingService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService
          templateReviewDigestService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService
          operatorFragmentService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService
          capturePolicyFragmentService) {
    this.catalogService = catalogService;
    this.captureDigestBindingService = captureDigestBindingService;
    this.templateReviewDigestService = templateReviewDigestService;
    this.operatorFragmentService = operatorFragmentService;
    this.capturePolicyFragmentService = capturePolicyFragmentService;
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CATALOG)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_DIGEST)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      captureDigest() {
    return captureDigestBindingService.binding();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_TEMPLATE_REVIEW)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      templateReview() {
    return templateReviewDigestService.binding();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_FRAGMENT)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      operatorFragment() {
    return operatorFragmentService.fragments();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_POLICY)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      capturePolicy() {
    return capturePolicyFragmentService.policy();
  }
}
