package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceController {

  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService
      evidenceSourceService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
      redactionProvenanceService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
      failClosedLockService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService
      archivePlanService;
  private final
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService
      closeoutService;

  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService
          evidenceSourceService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService
          redactionProvenanceService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService
          failClosedLockService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService
          archivePlanService,
      OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService
          closeoutService) {
    this.evidenceSourceService = evidenceSourceService;
    this.redactionProvenanceService = redactionProvenanceService;
    this.failClosedLockService = failClosedLockService;
    this.archivePlanService = archivePlanService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_EVIDENCE_SOURCE)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      evidenceSource() {
    return evidenceSourceService.evidenceSource();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_REDACTION_PROVENANCE)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      redactionProvenance() {
    return redactionProvenanceService.redactionProvenance();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_FAIL_CLOSED_LOCKS)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      failClosedLocks() {
    return failClosedLockService.locks();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_ARCHIVE_PLAN)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      archivePlan() {
    return archivePlanService.plan();
  }

  @GetMapping(
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      closeout() {
    return closeoutService.closeout();
  }
}
