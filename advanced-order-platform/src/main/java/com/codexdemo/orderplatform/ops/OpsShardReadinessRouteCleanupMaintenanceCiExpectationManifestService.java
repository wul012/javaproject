package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CI_EXPECTATION_MANIFEST;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-ci-expectation-manifest.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse manifest() {
        List<OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse.CiExpectation> expectations =
                OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
                        .map(this::expectation)
                        .toList();
        List<String> checks = List.of(
                "focused-service-tests-are-named",
                "route-regression-suite-is-named",
                "full-regression-command-is-maven-test",
                "github-actions-job-is-non-docker-regression",
                "ci-manifest-does-not-start-upstreams"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse(
                "advanced-order-platform",
                "Java v493",
                true,
                false,
                ENDPOINT,
                PROFILE,
                expectations.size(),
                4,
                false,
                false,
                expectations,
                checks,
                status(expectations)
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse.CiExpectation expectation(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse.CiExpectation(
                item.name(),
                focusedTestClass(item.name()),
                "OpsShardReadinessRouteCleanupMaintenanceIntegrationTests",
                "mvn -q test",
                "Java Maven CI / Maven compile and non-Docker regression",
                item.status()
        );
    }

    private String focusedTestClass(String itemName) {
        return switch (itemName) {
            case "segment-catalog" -> "OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogServiceTests";
            case "continuity" -> "OpsShardReadinessRouteCleanupMaintenanceContinuityServiceTests";
            case "latest-sibling-report" -> "OpsShardReadinessRouteCleanupMaintenanceLatestSiblingServiceTests";
            case "handoff-pair-audit" -> "OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditServiceTests";
            case "boundary-drift" -> "OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftServiceTests";
            case "source-plan-alignment" -> "OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentServiceTests";
            case "test-budget-plan" -> "OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanServiceTests";
            case "archive-manifest" -> "OpsShardReadinessRouteCleanupMaintenanceArchiveManifestServiceTests";
            case "closeout" -> "OpsShardReadinessRouteCleanupMaintenanceCloseoutServiceTests";
            default -> throw new IllegalArgumentException("Unknown upkeep item: " + itemName);
        };
    }

    private String status(
            List<OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse.CiExpectation> expectations
    ) {
        boolean passed = expectations.size() == 9
                && expectations.stream().allMatch(expectation -> expectation.focusedTestClass().endsWith("Tests"))
                && expectations.stream().allMatch(expectation -> expectation.fullRegressionCommand().equals("mvn -q test"))
                && expectations.stream().allMatch(expectation -> "passed".equals(expectation.status()));
        return passed ? "passed" : "blocked";
    }
}
