package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
            channelSignaturePolicyService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService
            statementJustificationService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
            sourceEvidenceMirrorService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
            redactionProvenanceBindingService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
            failClosedLockService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
            closeoutService;

    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService
                    channelSignaturePolicyService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService
                    statementJustificationService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService
                    sourceEvidenceMirrorService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService
                    redactionProvenanceBindingService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService
                    failClosedLockService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService closeoutService
    ) {
        this.channelSignaturePolicyService = channelSignaturePolicyService;
        this.statementJustificationService = statementJustificationService;
        this.sourceEvidenceMirrorService = sourceEvidenceMirrorService;
        this.redactionProvenanceBindingService = redactionProvenanceBindingService;
        this.failClosedLockService = failClosedLockService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CHANNEL_SIGNATURE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse channelSignature() {
        return channelSignaturePolicyService.policy();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_STATEMENT_JUSTIFICATION)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse statementJustification() {
        return statementJustificationService.statement();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_SOURCE_EVIDENCE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse sourceEvidence() {
        return sourceEvidenceMirrorService.mirror();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REDACTION_PROVENANCE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse redactionProvenance() {
        return redactionProvenanceBindingService.binding();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_FAIL_CLOSED_LOCKS)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse failClosedLocks() {
        return failClosedLockService.locks();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse closeout() {
        return closeoutService.closeout();
    }
}
