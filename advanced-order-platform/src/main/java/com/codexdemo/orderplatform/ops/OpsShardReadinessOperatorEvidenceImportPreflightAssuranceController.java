package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService digestBlueprintService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService routeProfileSummaryService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService archivePlanService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService operatorHandoffService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService ciBudgetService;
    private final OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService closeoutService;

    public OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService digestBlueprintService,
            OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService routeProfileSummaryService,
            OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService archivePlanService,
            OpsShardReadinessOperatorEvidenceImportPreflightOperatorHandoffService operatorHandoffService,
            OpsShardReadinessOperatorEvidenceImportPreflightCiBudgetService ciBudgetService,
            OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService closeoutService
    ) {
        this.digestBlueprintService = digestBlueprintService;
        this.routeProfileSummaryService = routeProfileSummaryService;
        this.archivePlanService = archivePlanService;
        this.operatorHandoffService = operatorHandoffService;
        this.ciBudgetService = ciBudgetService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_DIGEST_BLUEPRINT)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse digestBlueprint() {
        return digestBlueprintService.blueprint();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ROUTE_PROFILE_SUMMARY)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse routeProfileSummary() {
        return routeProfileSummaryService.summary();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_ARCHIVE_PLAN)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse archivePlan() {
        return archivePlanService.plan();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_OPERATOR_HANDOFF)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse operatorHandoff() {
        return operatorHandoffService.handoff();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CI_BUDGET)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse ciBudget() {
        return ciBudgetService.budget();
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_IMPORT_PREFLIGHT_CLOSEOUT)
    public OpsShardReadinessOperatorEvidenceImportPreflightResponse closeout() {
        return closeoutService.closeout();
    }
}
