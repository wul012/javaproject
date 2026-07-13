package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetRedactionRulesService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetResponse;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetSlotTemplateService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService;
import com.codexdemo.orderplatform.ops.maintenance.manualevidenceworksheet.OpsShardReadinessManualEvidenceWorksheetValidationRulesService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessManualEvidenceWorksheetFoundationController {

  private final OpsShardReadinessManualEvidenceWorksheetCatalogService catalogService;
  private final OpsShardReadinessManualEvidenceWorksheetSlotTemplateService slotTemplateService;
  private final OpsShardReadinessManualEvidenceWorksheetValidationRulesService
      validationRulesService;
  private final OpsShardReadinessManualEvidenceWorksheetRedactionRulesService redactionRulesService;
  private final OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService
      missingValuePolicyService;
  private final OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService
      targetScopeRegistryService;

  public OpsShardReadinessManualEvidenceWorksheetFoundationController(
      OpsShardReadinessManualEvidenceWorksheetCatalogService catalogService,
      OpsShardReadinessManualEvidenceWorksheetSlotTemplateService slotTemplateService,
      OpsShardReadinessManualEvidenceWorksheetValidationRulesService validationRulesService,
      OpsShardReadinessManualEvidenceWorksheetRedactionRulesService redactionRulesService,
      OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService missingValuePolicyService,
      OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService
          targetScopeRegistryService) {
    this.catalogService = catalogService;
    this.slotTemplateService = slotTemplateService;
    this.validationRulesService = validationRulesService;
    this.redactionRulesService = redactionRulesService;
    this.missingValuePolicyService = missingValuePolicyService;
    this.targetScopeRegistryService = targetScopeRegistryService;
  }

  @GetMapping(OpsShardReadinessManualEvidenceWorksheetRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CATALOG)
  public OpsShardReadinessManualEvidenceWorksheetResponse catalog() {
    return catalogService.catalog();
  }

  @GetMapping(
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.MANUAL_EVIDENCE_WORKSHEET_SLOT_TEMPLATE)
  public OpsShardReadinessManualEvidenceWorksheetResponse slotTemplate() {
    return slotTemplateService.template();
  }

  @GetMapping(
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.MANUAL_EVIDENCE_WORKSHEET_VALIDATION_RULES)
  public OpsShardReadinessManualEvidenceWorksheetResponse validationRules() {
    return validationRulesService.rules();
  }

  @GetMapping(
      OpsShardReadinessManualEvidenceWorksheetRoutePaths.MANUAL_EVIDENCE_WORKSHEET_REDACTION_RULES)
  public OpsShardReadinessManualEvidenceWorksheetResponse redactionRules() {
    return redactionRulesService.rules();
  }

  @GetMapping(
      OpsShardReadinessManualEvidenceWorksheetRoutePaths
          .MANUAL_EVIDENCE_WORKSHEET_MISSING_VALUE_POLICY)
  public OpsShardReadinessManualEvidenceWorksheetResponse missingValuePolicy() {
    return missingValuePolicyService.policy();
  }

  @GetMapping(
      OpsShardReadinessManualEvidenceWorksheetRoutePaths
          .MANUAL_EVIDENCE_WORKSHEET_TARGET_SCOPE_REGISTRY)
  public OpsShardReadinessManualEvidenceWorksheetResponse targetScopeRegistry() {
    return targetScopeRegistryService.registry();
  }
}
