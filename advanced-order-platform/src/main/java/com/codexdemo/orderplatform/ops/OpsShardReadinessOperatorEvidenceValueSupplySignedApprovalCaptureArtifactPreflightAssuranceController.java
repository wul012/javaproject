package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
            signatureSealService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
            statementEvidenceService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
            redactionValueService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
            failClosedLockService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService
            archivePlanService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService
            closeoutService;

    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSignatureSealService
                    signatureSealService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService
                    statementEvidenceService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRedactionValueService
                    redactionValueService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFailClosedLockService
                    failClosedLockService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightArchivePlanService
                    archivePlanService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService
                    closeoutService
    ) {
        this.signatureSealService = signatureSealService;
        this.statementEvidenceService = statementEvidenceService;
        this.redactionValueService = redactionValueService;
        this.failClosedLockService = failClosedLockService;
        this.archivePlanService = archivePlanService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_SIGNATURE_SEAL)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse signatureSeal() {
        return signatureSealService.seal();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_STATEMENT_EVIDENCE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse statementEvidence() {
        return statementEvidenceService.evidence();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REDACTION_VALUE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse redactionValue() {
        return redactionValueService.binding();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_FAIL_CLOSED_LOCKS)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse failClosedLocks() {
        return failClosedLockService.locks();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_ARCHIVE_PLAN)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse archivePlan() {
        return archivePlanService.plan();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse closeout() {
        return closeoutService.closeout();
    }
}
