package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessManualEvidenceWorksheetAssuranceController {

    private final OpsShardReadinessManualEvidenceWorksheetImporterPreflightService importerPreflightService;
    private final OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService routeProfileSummaryService;

    public OpsShardReadinessManualEvidenceWorksheetAssuranceController(
            OpsShardReadinessManualEvidenceWorksheetImporterPreflightService importerPreflightService,
            OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService routeProfileSummaryService
    ) {
        this.importerPreflightService = importerPreflightService;
        this.routeProfileSummaryService = routeProfileSummaryService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_IMPORTER_PREFLIGHT)
    public OpsShardReadinessManualEvidenceWorksheetResponse importerPreflight() {
        return importerPreflightService.preflight();
    }

    @GetMapping(OpsShardReadinessRoutePaths.MANUAL_EVIDENCE_WORKSHEET_ROUTE_PROFILE_SUMMARY)
    public OpsShardReadinessManualEvidenceWorksheetResponse routeProfileSummary() {
        return routeProfileSummaryService.summary();
    }
}
