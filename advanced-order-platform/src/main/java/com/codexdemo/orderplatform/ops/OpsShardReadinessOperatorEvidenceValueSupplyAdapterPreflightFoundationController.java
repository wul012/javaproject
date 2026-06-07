package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService
            compatibilityMatrixService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService
            redactionBoundaryService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService
                    compatibilityMatrixService,
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService
                    redactionBoundaryService
    ) {
        this.catalogService = catalogService;
        this.compatibilityMatrixService = compatibilityMatrixService;
        this.redactionBoundaryService = redactionBoundaryService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_COMPATIBILITY_MATRIX)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse compatibilityMatrix() {
        return compatibilityMatrixService.matrix();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_REDACTION_BOUNDARY)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse redactionBoundary() {
        return redactionBoundaryService.boundary();
    }
}
