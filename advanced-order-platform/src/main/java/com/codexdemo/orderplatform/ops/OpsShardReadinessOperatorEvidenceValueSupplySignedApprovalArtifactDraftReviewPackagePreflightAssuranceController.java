package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService
            evidencePackageService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
            valuePolicyPackageService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
            embargoPackageService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService
            draftAuthoringGateService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService
            closeoutService;

    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService
                    evidencePackageService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService
                    valuePolicyPackageService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService
                    embargoPackageService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService
                    draftAuthoringGateService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService
                    closeoutService
    ) {
        this.evidencePackageService = evidencePackageService;
        this.valuePolicyPackageService = valuePolicyPackageService;
        this.embargoPackageService = embargoPackageService;
        this.draftAuthoringGateService = draftAuthoringGateService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EVIDENCE_PACKAGE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    evidencePackage() {
        return evidencePackageService.evidencePackage();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_VALUE_POLICY_PACKAGE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    valuePolicyPackage() {
        return valuePolicyPackageService.valuePolicyPackage();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EMBARGO_PACKAGE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    embargoPackage() {
        return embargoPackageService.embargoPackage();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_DRAFT_AUTHORING_GATE)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    draftAuthoringGate() {
        return draftAuthoringGateService.draftAuthoringGate();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    closeout() {
        return closeoutService.closeout();
    }
}
