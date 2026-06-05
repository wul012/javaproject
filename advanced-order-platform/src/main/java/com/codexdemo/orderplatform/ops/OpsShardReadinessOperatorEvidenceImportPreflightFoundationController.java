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
    private final OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService
            redactionPreservationService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService missingValueGuardService;

    public OpsShardReadinessOperatorEvidenceImportPreflightFoundationController(
            OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService slotNormalizationService,
            OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService importBlockerMatrixService,
            OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService redactionPreservationService,
            OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService missingValueGuardService
    ) {
        this.catalogService = catalogService;
        this.slotNormalizationService = slotNormalizationService;
        this.importBlockerMatrixService = importBlockerMatrixService;
        this.redactionPreservationService = redactionPreservationService;
        this.missingValueGuardService = missingValueGuardService;
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

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_REDACTION_PRESERVATION)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse redactionPreservation() {
        return redactionPreservationService.preservation();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_MISSING_VALUE_GUARD)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse missingValueGuard() {
        return missingValueGuardService.guard();
    }
}
