package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService;
    private final OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService sideEffectGateService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService,
            OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService sideEffectGateService
    ) {
        this.validationMatrixService = validationMatrixService;
        this.sideEffectGateService = sideEffectGateService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse validationMatrix() {
        return validationMatrixService.matrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_SIDE_EFFECT_GATE)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse sideEffectGate() {
        return sideEffectGateService.gate();
    }
}
