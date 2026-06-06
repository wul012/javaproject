package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService slotTemplateService;
    private final OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService valueBoundaryService;
    private final OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService instructionSetService;

    public OpsShardReadinessOperatorEvidenceValueDraftFoundationController(
            OpsShardReadinessOperatorEvidenceValueDraftCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService slotTemplateService,
            OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService valueBoundaryService,
            OpsShardReadinessOperatorEvidenceValueDraftInstructionSetService instructionSetService
    ) {
        this.catalogService = catalogService;
        this.slotTemplateService = slotTemplateService;
        this.valueBoundaryService = valueBoundaryService;
        this.instructionSetService = instructionSetService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_SLOT_TEMPLATE)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse slotTemplate() {
        return slotTemplateService.template();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_VALUE_BOUNDARY)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse valueBoundary() {
        return valueBoundaryService.boundary();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_INSTRUCTION_SET)
    public OpsShardReadinessOperatorEvidenceValueDraftResponse instructionSet() {
        return instructionSetService.instructions();
    }
}
