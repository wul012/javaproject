package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService sideEffectGateService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService operatorReviewChecklistService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService digestBlueprintService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService,
            OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService sideEffectGateService,
            OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService operatorReviewChecklistService,
            OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService digestBlueprintService
    ) {
        this.validationMatrixService = validationMatrixService;
        this.sideEffectGateService = sideEffectGateService;
        this.operatorReviewChecklistService = operatorReviewChecklistService;
        this.digestBlueprintService = digestBlueprintService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse validationMatrix() {
        return validationMatrixService.matrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_SIDE_EFFECT_GATE)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse sideEffectGate() {
        return sideEffectGateService.gate();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_OPERATOR_REVIEW_CHECKLIST)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse operatorReviewChecklist() {
        return operatorReviewChecklistService.checklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_DIGEST_BLUEPRINT)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse digestBlueprint() {
        return digestBlueprintService.blueprint();
    }
}
