package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceController {

    private final OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffDigestService;
    private final OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService dependencyBoundaryMapService;
    private final OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archiveRetentionService;
    private final OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService;
    private final OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService operationsScorecardService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceController(
            OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffDigestService,
            OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService dependencyBoundaryMapService,
            OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archiveRetentionService,
            OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService,
            OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService operationsScorecardService
    ) {
        this.handoffDigestService = handoffDigestService;
        this.dependencyBoundaryMapService = dependencyBoundaryMapService;
        this.archiveRetentionService = archiveRetentionService;
        this.testEvidenceRollupService = testEvidenceRollupService;
        this.operationsScorecardService = operationsScorecardService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST)
    public OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse handoffAcceptanceDigest() {
        return handoffDigestService.digest();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_DEPENDENCY_BOUNDARY_MAP)
    public OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse dependencyBoundaryMap() {
        return dependencyBoundaryMapService.map();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_RETENTION_CALENDAR)
    public OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse archiveRetentionCalendar() {
        return archiveRetentionService.calendar();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_TEST_EVIDENCE_ROLLUP)
    public OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse testEvidenceRollup() {
        return testEvidenceRollupService.rollup();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OPERATIONS_SCORECARD)
    public OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse operationsScorecard() {
        return operationsScorecardService.scorecard();
    }
}
