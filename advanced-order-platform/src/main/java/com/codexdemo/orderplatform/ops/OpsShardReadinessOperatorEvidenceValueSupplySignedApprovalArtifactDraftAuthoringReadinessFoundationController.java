package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
      catalogService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService
      digestPinService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService
      operatorRequirementService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService
      signatureRequirementService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
          catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService
          digestPinService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService
          operatorRequirementService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService
          signatureRequirementService) {
    this.catalogService = catalogService;
    this.digestPinService = digestPinService;
    this.operatorRequirementService = operatorRequirementService;
    this.signatureRequirementService = signatureRequirementService;
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_CATALOG)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_DIGEST_PINS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      digestPins() {
    return digestPinService.digestPins();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_OPERATOR_REQUIREMENTS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      operatorRequirements() {
    return operatorRequirementService.operatorRequirements();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_SIGNATURE_REQUIREMENTS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      signatureRequirements() {
    return signatureRequirementService.signatureRequirements();
  }
}
