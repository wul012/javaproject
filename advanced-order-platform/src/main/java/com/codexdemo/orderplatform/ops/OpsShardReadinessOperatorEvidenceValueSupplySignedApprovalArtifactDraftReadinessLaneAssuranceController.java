package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService
            evidenceReviewService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
            valueRedactionService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
            embargoLockService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService
            manualPackageGateService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService
            closeoutService;

    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService
                    evidenceReviewService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService
                    valueRedactionService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService
                    embargoLockService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService
                    manualPackageGateService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService
                    closeoutService
    ) {
        this.evidenceReviewService = evidenceReviewService;
        this.valueRedactionService = valueRedactionService;
        this.embargoLockService = embargoLockService;
        this.manualPackageGateService = manualPackageGateService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EVIDENCE_REVIEW)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse evidenceReview() {
        return evidenceReviewService.evidenceReview();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_VALUE_REDACTION)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
    valueRedaction() {
        return valueRedactionService.valueRedaction();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EMBARGO_LOCKS)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse embargoLocks() {
        return embargoLockService.embargoLocks();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_MANUAL_PACKAGE_GATE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
    manualPackageGate() {
        return manualPackageGateService.manualPackageGate();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse closeout() {
        return closeoutService.closeout();
    }
}
