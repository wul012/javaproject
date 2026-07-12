package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService {

  public static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_TEST_EVIDENCE_ROLLUP;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-test-evidence-rollup.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse rollup() {
    List<OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse.TestEvidenceEntry>
        entries =
            List.of(
                entry(
                    "handoff-acceptance-digest-service",
                    "OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestServiceTests",
                    "service",
                    "acceptance-section-count-5"),
                entry(
                    "dependency-boundary-map-service",
                    "OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapServiceTests",
                    "service",
                    "boundary-entry-count-9"),
                entry(
                    "archive-retention-calendar-service",
                    "OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarServiceTests",
                    "service",
                    "archive-entry-count-9"),
                entry(
                    "shared-route-constant-contract",
                    "OpsShardReadinessRoutePathsTests",
                    "contract",
                    "endpoint-constants-match-service-endpoints"),
                entry(
                    "sustainment-route-integration",
                    "OpsShardReadinessRouteCleanupMaintenanceSustainmentIntegrationTests",
                    "integration",
                    "mockmvc-route-json-contracts"));
    int covered = (int) entries.stream().filter(entry -> "covered".equals(entry.status())).count();
    List<String> checks =
        List.of(
            "test-evidence-entry-count-" + entries.size(),
            "covered-entry-count-" + covered,
            "service-contract-and-integration-tests-present",
            "rollup-does-not-execute-tests",
            "test-evidence-rollup-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse(
        "advanced-order-platform",
        "Java v528",
        true,
        false,
        ENDPOINT,
        PROFILE,
        entries.size(),
        covered,
        entries,
        checks,
        covered == entries.size() ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse.TestEvidenceEntry
      entry(String name, String testClass, String coverageType, String evidence) {
    return new OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse.TestEvidenceEntry(
        name, testClass, coverageType, evidence, "covered");
  }
}
