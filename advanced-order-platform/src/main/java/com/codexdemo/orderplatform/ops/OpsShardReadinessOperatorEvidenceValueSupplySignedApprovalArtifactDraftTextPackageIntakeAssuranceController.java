package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService
      sourceEvidenceService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService
      operatorValueHandleService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService
      policyReviewStateService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
      executionLockService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService
      archiveCloseoutService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService
          sourceEvidenceService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService
          operatorValueHandleService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService
          policyReviewStateService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService
          executionLockService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService
          archiveCloseoutService) {
    this.sourceEvidenceService = sourceEvidenceService;
    this.operatorValueHandleService = operatorValueHandleService;
    this.policyReviewStateService = policyReviewStateService;
    this.executionLockService = executionLockService;
    this.archiveCloseoutService = archiveCloseoutService;
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_SOURCE_EVIDENCE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
      sourceEvidence() {
    return sourceEvidenceService.sourceEvidence();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_OPERATOR_VALUE_HANDLE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
      operatorValueHandle() {
    return operatorValueHandleService.operatorValueHandle();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_POLICY_REVIEW_STATE)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
      policyReviewState() {
    return policyReviewStateService.policyReviewState();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_EXECUTION_LOCK)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
      executionLock() {
    return executionLockService.executionLock();
  }

  @GetMapping(
      OpsShardReadinessRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_INTAKE_ARCHIVE_CLOSEOUT)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
      archiveCloseout() {
    return archiveCloseoutService.archiveCloseout();
  }
}
