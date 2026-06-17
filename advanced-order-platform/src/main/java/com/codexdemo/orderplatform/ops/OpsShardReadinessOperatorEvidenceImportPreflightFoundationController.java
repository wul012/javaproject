package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightFoundationController {

  private final OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService
      slotNormalizationService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService
      importBlockerMatrixService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService
      redactionPreservationService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService
      missingValueGuardService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService
      targetScopeMappingService;

  public OpsShardReadinessOperatorEvidenceImportPreflightFoundationController(
      OpsShardReadinessOperatorEvidenceImportPreflightCatalogService catalogService,
      OpsShardReadinessOperatorEvidenceImportPreflightSlotNormalizationService
          slotNormalizationService,
      OpsShardReadinessOperatorEvidenceImportPreflightImportBlockerMatrixService
          importBlockerMatrixService,
      OpsShardReadinessOperatorEvidenceImportPreflightRedactionPreservationService
          redactionPreservationService,
      OpsShardReadinessOperatorEvidenceImportPreflightMissingValueGuardService
          missingValueGuardService,
      OpsShardReadinessOperatorEvidenceImportPreflightTargetScopeMappingService
          targetScopeMappingService) {
    this.catalogService = catalogService;
    this.slotNormalizationService = slotNormalizationService;
    this.importBlockerMatrixService = importBlockerMatrixService;
    this.redactionPreservationService = redactionPreservationService;
    this.missingValueGuardService = missingValueGuardService;
    this.targetScopeMappingService = targetScopeMappingService;
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

  @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_TARGET_SCOPE_MAPPING)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse targetScopeMapping() {
    return targetScopeMappingService.mapping();
  }
}
