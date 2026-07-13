package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply.OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController {

  private final OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService
      validationMatrixService;
  private final OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService
      sideEffectGateService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService
      operatorReviewChecklistService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService
      digestBlueprintService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService archivePlanService;
  private final OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService closeoutService;

  public OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController(
      OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService validationMatrixService,
      OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService sideEffectGateService,
      OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService
          operatorReviewChecklistService,
      OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService digestBlueprintService,
      OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService archivePlanService,
      OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService closeoutService) {
    this.validationMatrixService = validationMatrixService;
    this.sideEffectGateService = sideEffectGateService;
    this.operatorReviewChecklistService = operatorReviewChecklistService;
    this.digestBlueprintService = digestBlueprintService;
    this.archivePlanService = archivePlanService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_VALIDATION_MATRIX)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse validationMatrix() {
    return validationMatrixService.matrix();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIDE_EFFECT_GATE)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse sideEffectGate() {
    return sideEffectGateService.gate();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_OPERATOR_REVIEW_CHECKLIST)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse operatorReviewChecklist() {
    return operatorReviewChecklistService.checklist();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_DIGEST_BLUEPRINT)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse digestBlueprint() {
    return digestBlueprintService.blueprint();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_ARCHIVE_PLAN)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse archivePlan() {
    return archivePlanService.plan();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths
          .OPERATOR_EVIDENCE_VALUE_SUPPLY_CLOSEOUT)
  public OpsShardReadinessOperatorEvidenceValueSupplyResponse closeout() {
    return closeoutService.closeout();
  }
}
