package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SUSTAINMENT_CLOSEOUT;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-sustainment-closeout.v1";
    static final String SOURCE_PLAN = "Node v549";

    private final OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService operationsScorecardService;
    private final OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archiveRetentionService;
    private final OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService(
            OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService operationsScorecardService,
            OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService archiveRetentionService,
            OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService
    ) {
        this.operationsScorecardService = operationsScorecardService;
        this.archiveRetentionService = archiveRetentionService;
        this.testEvidenceRollupService = testEvidenceRollupService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse closeout() {
        OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse scorecard =
                operationsScorecardService.scorecard();
        OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse archive =
                archiveRetentionService.calendar();
        OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse tests =
                testEvidenceRollupService.rollup();
        List<OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse.CloseoutItem> items =
                List.of(
                        item("operations-scorecard", scorecard.endpoint(),
                                "score-" + scorecard.score(), scorecard.status()),
                        item("sustainment-route-split", scorecard.endpoint(),
                                "base-and-evidence-controllers", scorecard.status()),
                        item("archive-retention", archive.endpoint(),
                                "next-review-version-" + archive.nextReviewVersion(), archive.status()),
                        item("test-evidence-rollup", tests.endpoint(),
                                "covered-entry-count-" + tests.coveredEntryCount(), tests.status()),
                        item("read-only-boundary", scorecard.endpoint(),
                                "executionAllowed-" + scorecard.executionAllowed(),
                                scorecard.executionAllowed() ? "blocked" : "passed")
                );
        int passed = (int) items.stream().filter(item -> "passed".equals(item.status())).count();
        List<String> checks = List.of(
                "closeout-item-count-" + items.size(),
                "passed-item-count-" + passed,
                "final-score-" + scorecard.score(),
                "source-plan-" + SOURCE_PLAN,
                "sustainment-closeout-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse(
                "advanced-order-platform",
                "Java v532",
                true,
                false,
                ENDPOINT,
                PROFILE,
                items.size(),
                passed,
                scorecard.score(),
                SOURCE_PLAN,
                items,
                checks,
                passed == items.size() && scorecard.score() == 100 ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse.CloseoutItem item(
            String name,
            String sourceEndpoint,
            String evidence,
            String status
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse.CloseoutItem(
                name,
                sourceEndpoint,
                evidence,
                status
        );
    }
}
