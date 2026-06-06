package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService envelopeTemplateService;

    public OpsShardReadinessOperatorEvidenceValueSupplyFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService envelopeTemplateService
    ) {
        this.catalogService = catalogService;
        this.envelopeTemplateService = envelopeTemplateService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ENVELOPE_TEMPLATE)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse envelopeTemplate() {
        return envelopeTemplateService.template();
    }
}
