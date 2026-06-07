package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService catalogService;

    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService catalogService
    ) {
        this.catalogService = catalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse catalog() {
        return catalogService.catalog();
    }
}
