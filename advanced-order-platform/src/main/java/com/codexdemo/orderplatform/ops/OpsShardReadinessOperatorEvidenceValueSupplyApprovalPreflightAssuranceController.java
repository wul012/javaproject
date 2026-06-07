package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService
            valueRejectionService;

    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService valueRejectionService
    ) {
        this.valueRejectionService = valueRejectionService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_VALUE_REJECTION)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse valueRejection() {
        return valueRejectionService.rejection();
    }
}
