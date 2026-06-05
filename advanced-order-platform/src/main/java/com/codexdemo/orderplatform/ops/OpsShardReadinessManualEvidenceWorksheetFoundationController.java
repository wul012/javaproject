package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessManualEvidenceWorksheetFoundationController {

    private final OpsShardReadinessManualEvidenceWorksheetCatalogService catalogService;
    private final OpsShardReadinessManualEvidenceWorksheetSlotTemplateService slotTemplateService;
    private final OpsShardReadinessManualEvidenceWorksheetValidationRulesService validationRulesService;

    public OpsShardReadinessManualEvidenceWorksheetFoundationController(
            OpsShardReadinessManualEvidenceWorksheetCatalogService catalogService,
            OpsShardReadinessManualEvidenceWorksheetSlotTemplateService slotTemplateService,
            OpsShardReadinessManualEvidenceWorksheetValidationRulesService validationRulesService
    ) {
        this.catalogService = catalogService;
        this.slotTemplateService = slotTemplateService;
        this.validationRulesService = validationRulesService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG)
    public OpsShardReadinessManualEvidenceWorksheetResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE)
    public OpsShardReadinessManualEvidenceWorksheetResponse slotTemplate() {
        return slotTemplateService.template();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES)
    public OpsShardReadinessManualEvidenceWorksheetResponse validationRules() {
        return validationRulesService.rules();
    }
}
