package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessManualEvidenceWorksheetAssuranceController {

    private final OpsShardReadinessManualEvidenceWorksheetImporterPreflightService importerPreflightService;
    private final OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService routeProfileSummaryService;
    private final OpsShardReadinessManualEvidenceWorksheetArchivePlanService archivePlanService;
    private final OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService operatorHandoffService;
    private final OpsShardReadinessManualEvidenceWorksheetCiBudgetService ciBudgetService;
    private final OpsShardReadinessManualEvidenceWorksheetCloseoutService closeoutService;

    public OpsShardReadinessManualEvidenceWorksheetAssuranceController(
            OpsShardReadinessManualEvidenceWorksheetImporterPreflightService importerPreflightService,
            OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService routeProfileSummaryService,
            OpsShardReadinessManualEvidenceWorksheetArchivePlanService archivePlanService,
            OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService operatorHandoffService,
            OpsShardReadinessManualEvidenceWorksheetCiBudgetService ciBudgetService,
            OpsShardReadinessManualEvidenceWorksheetCloseoutService closeoutService
    ) {
        this.importerPreflightService = importerPreflightService;
        this.routeProfileSummaryService = routeProfileSummaryService;
        this.archivePlanService = archivePlanService;
        this.operatorHandoffService = operatorHandoffService;
        this.ciBudgetService = ciBudgetService;
        this.closeoutService = closeoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT)
    public OpsShardReadinessManualEvidenceWorksheetResponse importerPreflight() {
        return importerPreflightService.preflight();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ROUTE_PROFILE_SUMMARY)
    public OpsShardReadinessManualEvidenceWorksheetResponse routeProfileSummary() {
        return routeProfileSummaryService.summary();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ARCHIVE_PLAN)
    public OpsShardReadinessManualEvidenceWorksheetResponse archivePlan() {
        return archivePlanService.plan();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_OPERATOR_HANDOFF)
    public OpsShardReadinessManualEvidenceWorksheetResponse operatorHandoff() {
        return operatorHandoffService.handoff();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CI_BUDGET)
    public OpsShardReadinessManualEvidenceWorksheetResponse ciBudget() {
        return ciBudgetService.budget();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_CLOSEOUT)
    public OpsShardReadinessManualEvidenceWorksheetResponse closeout() {
        return closeoutService.closeout();
    }
}
