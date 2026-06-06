package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyFoundationController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService envelopeTemplateService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService redactionPolicyService;

    public OpsShardReadinessOperatorEvidenceValueSupplyFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService envelopeTemplateService,
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService redactionPolicyService
    ) {
        this.catalogService = catalogService;
        this.envelopeTemplateService = envelopeTemplateService;
        this.redactionPolicyService = redactionPolicyService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse catalog() {
        return catalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ENVELOPE_TEMPLATE)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse envelopeTemplate() {
        return envelopeTemplateService.template();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_REDACTION_POLICY)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse redactionPolicy() {
        return redactionPolicyService.policy();
    }
}
