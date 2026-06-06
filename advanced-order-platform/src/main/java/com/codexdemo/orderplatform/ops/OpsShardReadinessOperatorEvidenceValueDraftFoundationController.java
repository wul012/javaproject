package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService slotTemplateService;

    public OpsShardReadinessOperatorEvidenceValueDraftFoundationController(
            OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService slotTemplateService
    ) {
        this.catalogService = catalogService;
        this.slotTemplateService = slotTemplateService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse slotTemplate() {
        return slotTemplateService.template();
    }
}
