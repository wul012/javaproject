package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService slotNormalizationService;

    public OpsShardReadinessOperatorEvidenceImportPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService slotNormalizationService
    ) {
        this.catalogService = catalogService;
        this.slotNormalizationService = slotNormalizationService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_SLOT_NORMALIZATION)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse slotNormalization() {
        return slotNormalizationService.normalization();
    }
}
