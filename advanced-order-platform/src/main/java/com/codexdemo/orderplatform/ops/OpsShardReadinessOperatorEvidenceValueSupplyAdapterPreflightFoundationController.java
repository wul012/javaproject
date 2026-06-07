package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService catalogService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService catalogService
    ) {
        this.catalogService = catalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse catalog() {
        return catalogService.catalog();
    }
}
