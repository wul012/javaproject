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
    private final OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService missingValuePolicyService;
    private final OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService provenanceRequirementService;

    public OpsShardReadinessOperatorEvidenceValueSupplyFoundationController(
            OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService,
            OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService envelopeTemplateService,
            OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService redactionPolicyService,
            OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService missingValuePolicyService,
            OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService provenanceRequirementService
    ) {
        this.catalogService = catalogService;
        this.envelopeTemplateService = envelopeTemplateService;
        this.redactionPolicyService = redactionPolicyService;
        this.missingValuePolicyService = missingValuePolicyService;
        this.provenanceRequirementService = provenanceRequirementService;
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

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_MISSING_VALUE_POLICY)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse missingValuePolicy() {
        return missingValuePolicyService.policy();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_PROVENANCE_REQUIREMENT)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse provenanceRequirement() {
        return provenanceRequirementService.requirement();
    }
}
