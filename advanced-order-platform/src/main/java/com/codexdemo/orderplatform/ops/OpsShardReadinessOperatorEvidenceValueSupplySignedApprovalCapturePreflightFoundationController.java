package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService
            catalogService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
            templateDigestBindingService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService
            reviewDigestBindingService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
            operatorInputMirrorService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService
            timingWindowService;

    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
                    templateDigestBindingService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService
                    reviewDigestBindingService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService
                    operatorInputMirrorService,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService
                    timingWindowService
    ) {
        this.catalogService = catalogService;
        this.templateDigestBindingService = templateDigestBindingService;
        this.reviewDigestBindingService = reviewDigestBindingService;
        this.operatorInputMirrorService = operatorInputMirrorService;
        this.timingWindowService = timingWindowService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TEMPLATE_DIGEST)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse templateDigest() {
        return templateDigestBindingService.binding();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REVIEW_DIGEST)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse reviewDigest() {
        return reviewDigestBindingService.binding();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_OPERATOR_INPUT)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse operatorInput() {
        return operatorInputMirrorService.mirror();
    }

    @GetMapping(OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TIMING_WINDOW)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse timingWindow() {
        return timingWindowService.window();
    }
}
