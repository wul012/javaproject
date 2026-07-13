package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyFoundationController {

  private final OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService
      envelopeTemplateService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService
      redactionPolicyService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService
      missingValuePolicyService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService
      provenanceRequirementService;
  private final OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService
      sourceEvidenceGuardService;

  public OpsShardReadinessOperatorEvidenceValueSupplyFoundationController(
      OpsShardReadinessOperatorEvidenceValueSupplyCatalogService catalogService,
      OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService envelopeTemplateService,
      OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService redactionPolicyService,
      OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService
          missingValuePolicyService,
      OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService
          provenanceRequirementService,
      OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService
          sourceEvidenceGuardService) {
    this.catalogService = catalogService;
    this.envelopeTemplateService = envelopeTemplateService;
    this.redactionPolicyService = redactionPolicyService;
    this.missingValuePolicyService = missingValuePolicyService;
    this.provenanceRequirementService = provenanceRequirementService;
    this.sourceEvidenceGuardService = sourceEvidenceGuardService;
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ENVELOPE_TEMPLATE)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse envelopeTemplate() {
    return envelopeTemplateService.template();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_REDACTION_POLICY)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse redactionPolicy() {
    return redactionPolicyService.policy();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_MISSING_VALUE_POLICY)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse missingValuePolicy() {
    return missingValuePolicyService.policy();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_PROVENANCE_REQUIREMENT)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse provenanceRequirement() {
    return provenanceRequirementService.requirement();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SOURCE_EVIDENCE_GUARD)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse sourceEvidenceGuard() {
    return sourceEvidenceGuardService.guard();
  }
}
