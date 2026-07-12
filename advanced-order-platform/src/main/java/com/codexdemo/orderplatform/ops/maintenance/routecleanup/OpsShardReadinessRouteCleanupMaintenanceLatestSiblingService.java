package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_LATEST_SIBLING_REPORT;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-latest-sibling-report.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse report() {
    List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
        OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog.entries();
    List<String> sourcePlans =
        entries.stream()
            .map(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::sourceNodePlan)
            .distinct()
            .toList();
    List<String> evidencePaths =
        entries.stream()
            .map(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::evidencePath)
            .toList();
    int liveSmokeCount =
        (int) entries.stream().filter(entry -> entry.phase().contains("live-smoke")).count();
    List<String> checks =
        List.of(
            "latest-sibling-segment-entry-count-" + entries.size(),
            "latest-sibling-live-smoke-entry-count-" + liveSmokeCount,
            "latest-sibling-source-node-v549-present",
            "latest-sibling-evidence-paths-versioned",
            "latest-sibling-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse(
        "advanced-order-platform",
        "Java v475",
        true,
        false,
        ENDPOINT,
        PROFILE,
        entries.getFirst().javaVersion(),
        entries.getLast().javaVersion(),
        entries.size(),
        liveSmokeCount,
        sourcePlans,
        evidencePaths,
        checks,
        status(entries, sourcePlans, liveSmokeCount));
  }

  private String status(
      List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries,
      List<String> sourcePlans,
      int liveSmokeCount) {
    boolean passed =
        entries.size() == 12
            && entries.getFirst().javaVersion() == 306
            && entries.getLast().javaVersion() == 317
            && liveSmokeCount == 6
            && sourcePlans.contains("Node v549")
            && entries.stream()
                .allMatch(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::readOnly)
            && entries.stream()
                .noneMatch(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::executionAllowed);
    return passed ? "passed" : "blocked";
  }
}
