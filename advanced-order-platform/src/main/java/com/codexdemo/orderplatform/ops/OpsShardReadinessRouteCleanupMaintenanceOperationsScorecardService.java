package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OPERATIONS_SCORECARD;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-operations-scorecard.v1";

    private final OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffDigestService;
    private final OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService dependencyBoundaryMapService;
    private final OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archiveRetentionService;
    private final OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService;

    public OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService(
            OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffDigestService,
            OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService dependencyBoundaryMapService,
            OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archiveRetentionService,
            OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService
    ) {
        this.handoffDigestService = handoffDigestService;
        this.dependencyBoundaryMapService = dependencyBoundaryMapService;
        this.archiveRetentionService = archiveRetentionService;
        this.testEvidenceRollupService = testEvidenceRollupService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse scorecard() {
        OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse handoff =
                handoffDigestService.digest();
        OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse boundary =
                dependencyBoundaryMapService.map();
        OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse archive =
                archiveRetentionService.calendar();
        OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse tests =
                testEvidenceRollupService.rollup();
        List<OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse.ScorecardDimension>
                dimensions = List.of(
                dimension("handoff-acceptance", handoff.endpoint(), 25,
                        "accepted-section-count-" + handoff.acceptedSectionCount(), handoff.status()),
                dimension("dependency-boundary", boundary.endpoint(), 25,
                        "boundary-entry-count-" + boundary.boundaryEntryCount(), boundary.status()),
                dimension("archive-retention", archive.endpoint(), 25,
                        "next-review-version-" + archive.nextReviewVersion(), archive.status()),
                dimension("test-evidence", tests.endpoint(), 25,
                        "covered-entry-count-" + tests.coveredEntryCount(), tests.status())
        );
        int passed = (int) dimensions.stream().filter(dimension -> "passed".equals(dimension.status())).count();
        int score = dimensions.stream()
                .filter(dimension -> "passed".equals(dimension.status()))
                .mapToInt(OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse
                        .ScorecardDimension::weight)
                .sum();
        List<String> checks = List.of(
                "scorecard-dimension-count-" + dimensions.size(),
                "passed-dimension-count-" + passed,
                "score-" + score,
                "scorecard-derived-from-read-only-services",
                "operations-scorecard-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse(
                "advanced-order-platform",
                "Java v530",
                true,
                false,
                ENDPOINT,
                PROFILE,
                score,
                dimensions.size(),
                passed,
                dimensions,
                checks,
                passed == dimensions.size() && score == 100 ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse.ScorecardDimension dimension(
            String name,
            String sourceEndpoint,
            int weight,
            String evidence,
            String status
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse.ScorecardDimension(
                name,
                sourceEndpoint,
                weight,
                evidence,
                status
        );
    }
}
