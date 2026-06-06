package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService
    ) {
        this.validationMatrixService = validationMatrixService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse validationMatrix() {
        return validationMatrixService.matrix();
    }
}
