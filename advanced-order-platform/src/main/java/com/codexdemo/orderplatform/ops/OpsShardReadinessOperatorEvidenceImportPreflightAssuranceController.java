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

    public OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceImportPreflightDigestBlueprintService digestBlueprintService,
            OpsShardReadinessOperatorEvidenceImportPreflightRouteProfileSummaryService routeProfileSummaryService,
            OpsShardReadinessOperatorEvidenceImportPreflightArchivePlanService archivePlanService
    ) {
        this.digestBlueprintService = digestBlueprintService;
        this.routeProfileSummaryService = routeProfileSummaryService;
        this.archivePlanService = archivePlanService;
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
}
