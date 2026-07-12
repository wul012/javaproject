package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupArchivePlanService {

  static final String ENDPOINT = RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.ARCHIVE_PLAN;

  static final String PROFILE = "java-shard-readiness-route-cleanup-archive-plan.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupArchivePlanResponse plan() {
    int version = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();
    String root = "e/" + version + "/evidence";
    List<OpsShardReadinessRouteCleanupArchivePlanResponse.ArchiveArtifact> artifacts =
        List.of(
            artifact(
                "catalog-json",
                "json",
                OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT,
                root + "/route-cleanup-evidence-catalog-v" + version + ".json"),
            artifact(
                "phase-summary-json",
                "json",
                OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT,
                root + "/route-cleanup-phase-summary-v" + version + ".json"),
            artifact(
                "boundary-matrix-json",
                "json",
                OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT,
                root + "/route-cleanup-boundary-matrix-v" + version + ".json"),
            artifact(
                "handoff-checklist-json",
                "json",
                OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT,
                root + "/route-cleanup-handoff-checklist-v" + version + ".json"));
    return new OpsShardReadinessRouteCleanupArchivePlanResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupHandoffChecklistService.ENDPOINT,
        root,
        artifacts.size(),
        artifacts,
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus());
  }

  private OpsShardReadinessRouteCleanupArchivePlanResponse.ArchiveArtifact artifact(
      String name, String kind, String source, String targetPath) {
    return new OpsShardReadinessRouteCleanupArchivePlanResponse.ArchiveArtifact(
        name, kind, source, targetPath, true, "planned");
  }
}
