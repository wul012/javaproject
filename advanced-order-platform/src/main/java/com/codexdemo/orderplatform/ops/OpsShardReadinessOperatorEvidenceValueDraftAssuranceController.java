package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftResponse;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft.OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessService.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueDraftAssuranceController {

  private final OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService
      blockedReasonLedgerService;
  private final OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService
      digestBlueprintService;
  private final OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService
      routeProfileSummaryService;
  private final OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService archivePlanService;
  private final OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService
      operatorHandoffService;
  private final OpsShardReadinessOperatorEvidenceValueDraftCloseoutService closeoutService;

  public OpsShardReadinessOperatorEvidenceValueDraftAssuranceController(
      OpsShardReadinessOperatorEvidenceValueDraftBlockedReasonLedgerService
          blockedReasonLedgerService,
      OpsShardReadinessOperatorEvidenceValueDraftDigestBlueprintService digestBlueprintService,
      OpsShardReadinessOperatorEvidenceValueDraftRouteProfileSummaryService
          routeProfileSummaryService,
      OpsShardReadinessOperatorEvidenceValueDraftArchivePlanService archivePlanService,
      OpsShardReadinessOperatorEvidenceValueDraftOperatorHandoffService operatorHandoffService,
      OpsShardReadinessOperatorEvidenceValueDraftCloseoutService closeoutService) {
    this.blockedReasonLedgerService = blockedReasonLedgerService;
    this.digestBlueprintService = digestBlueprintService;
    this.routeProfileSummaryService = routeProfileSummaryService;
    this.archivePlanService = archivePlanService;
    this.operatorHandoffService = operatorHandoffService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
          .OPERATOR_EVIDENCE_VALUE_DRAFT_BLOCKED_REASON_LEDGER)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse blockedReasonLedger() {
    return blockedReasonLedgerService.ledger();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
          .OPERATOR_EVIDENCE_VALUE_DRAFT_DIGEST_BLUEPRINT)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse digestBlueprint() {
    return digestBlueprintService.blueprint();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
          .OPERATOR_EVIDENCE_VALUE_DRAFT_ROUTE_PROFILE_SUMMARY)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse routeProfileSummary() {
    return routeProfileSummaryService.summary();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
          .OPERATOR_EVIDENCE_VALUE_DRAFT_ARCHIVE_PLAN)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse archivePlan() {
    return archivePlanService.plan();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths
          .OPERATOR_EVIDENCE_VALUE_DRAFT_OPERATOR_HANDOFF)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse operatorHandoff() {
    return operatorHandoffService.handoff();
  }

  @GetMapping(
      OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.OPERATOR_EVIDENCE_VALUE_DRAFT_CLOSEOUT)
  public OpsShardReadinessOperatorEvidenceValueDraftResponse closeout() {
    return closeoutService.closeout();
  }
}
