package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight.OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController {

  private final OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService
      digestBlueprintService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService
      routeProfileSummaryService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService
      archivePlanService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService
      operatorHandoffService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService ciBudgetService;
  private final OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService closeoutService;

  public OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController(
      OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService digestBlueprintService,
      OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService
          routeProfileSummaryService,
      OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService archivePlanService,
      OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService operatorHandoffService,
      OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService ciBudgetService,
      OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService closeoutService) {
    this.digestBlueprintService = digestBlueprintService;
    this.routeProfileSummaryService = routeProfileSummaryService;
    this.archivePlanService = archivePlanService;
    this.operatorHandoffService = operatorHandoffService;
    this.ciBudgetService = ciBudgetService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
          .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse digestBlueprint() {
    return digestBlueprintService.blueprint();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
          .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ROUTE_PROFILE_SUMMARY)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse routeProfileSummary() {
    return routeProfileSummaryService.summary();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
          .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ARCHIVE_PLAN)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse archivePlan() {
    return archivePlanService.plan();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
          .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_OPERATOR_HANDOFF)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse operatorHandoff() {
    return operatorHandoffService.handoff();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
          .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CI_BUDGET)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse ciBudget() {
    return ciBudgetService.budget();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths
          .OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CLOSEOUT)
  public OpsShardReadinessOperatorEvidenceImportPreflightResponse closeout() {
    return closeoutService.closeout();
  }
}
