package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftinstructionpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
      evidenceInstructionService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
      valuePolicyInstructionService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
      embargoInstructionService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService
      draftTextLockService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService
      closeoutService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
          evidenceInstructionService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
          valuePolicyInstructionService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
          embargoInstructionService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService
          draftTextLockService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService
          closeoutService) {
    this.evidenceInstructionService = evidenceInstructionService;
    this.valuePolicyInstructionService = valuePolicyInstructionService;
    this.embargoInstructionService = embargoInstructionService;
    this.draftTextLockService = draftTextLockService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EVIDENCE_INSTRUCTIONS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
      evidenceInstructions() {
    return evidenceInstructionService.evidenceInstructions();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_VALUE_POLICY_INSTRUCTIONS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
      valuePolicyInstructions() {
    return valuePolicyInstructionService.valuePolicyInstructions();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EMBARGO_INSTRUCTIONS)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
      embargoInstructions() {
    return embargoInstructionService.embargoInstructions();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_DRAFT_TEXT_LOCK)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
      draftTextLock() {
    return draftTextLockService.draftTextLock();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftInstructionPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_CLOSEOUT)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
      closeout() {
    return closeoutService.closeout();
  }
}
