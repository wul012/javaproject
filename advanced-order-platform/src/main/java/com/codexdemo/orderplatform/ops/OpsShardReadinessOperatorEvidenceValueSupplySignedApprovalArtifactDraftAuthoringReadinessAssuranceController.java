package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEvidenceRequirementService
      evidenceRequirementService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService
      valuePolicyRequirementService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEmbargoRequirementService
      embargoRequirementService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDraftTextAbsenceService
      draftTextAbsenceService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService
      closeoutService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEvidenceRequirementService
          evidenceRequirementService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService
          valuePolicyRequirementService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEmbargoRequirementService
          embargoRequirementService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDraftTextAbsenceService
          draftTextAbsenceService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService
          closeoutService) {
    this.evidenceRequirementService = evidenceRequirementService;
    this.valuePolicyRequirementService = valuePolicyRequirementService;
    this.embargoRequirementService = embargoRequirementService;
    this.draftTextAbsenceService = draftTextAbsenceService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_EVIDENCE_REQUIREMENTS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      evidenceRequirements() {
    return evidenceRequirementService.evidenceRequirements();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_VALUE_POLICY_REQUIREMENTS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      valuePolicyRequirements() {
    return valuePolicyRequirementService.valuePolicyRequirements();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_EMBARGO_REQUIREMENTS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      embargoRequirements() {
    return embargoRequirementService.embargoRequirements();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_DRAFT_TEXT_ABSENCE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      draftTextAbsence() {
    return draftTextAbsenceService.draftTextAbsence();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_CLOSEOUT)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      closeout() {
    return closeoutService.closeout();
  }
}
