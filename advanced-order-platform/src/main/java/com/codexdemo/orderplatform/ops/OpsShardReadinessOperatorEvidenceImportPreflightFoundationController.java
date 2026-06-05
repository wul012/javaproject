package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightFoundationController {

    private final OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService slotNormalizationService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService importBlockerMatrixService;

    public OpsShardReadinessOperatorEvidenceImportPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService slotNormalizationService,
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService importBlockerMatrixService
    ) {
        this.catalogService = catalogService;
        this.slotNormalizationService = slotNormalizationService;
        this.importBlockerMatrixService = importBlockerMatrixService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CATALOG)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_SLOT_NORMALIZATION)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse slotNormalization() {
        return slotNormalizationService.normalization();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_IMPORT_BLOCKER_MATRIX)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse importBlockerMatrix() {
        return importBlockerMatrixService.matrix();
    }
}
