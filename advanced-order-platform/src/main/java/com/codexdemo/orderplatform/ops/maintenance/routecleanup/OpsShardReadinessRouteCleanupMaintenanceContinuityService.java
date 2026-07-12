package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceContinuityService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_CONTINUITY;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-continuity.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceContinuityResponse continuity() {
    List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries();
    List<OpsShardReadinessRouteCleanupEvidenceAnalyzer.Segment> segments =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments();
    int first = entries.getFirst().javaVersion();
    int latest = entries.getLast().javaVersion();
    int expected = latest - first + 1;
    int gapCount = expected - entries.size();
    List<String> checks =
        List.of(
            "entries-cover-java-v" + first + "-through-v" + latest,
            "entry-count-" + entries.size() + "-matches-expected-" + expected,
            "segment-count-" + segments.size(),
            "segment-boundaries-are-contiguous",
            "all-entries-remain-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceContinuityResponse(
        "advanced-order-platform",
        "Java v473",
        true,
        false,
        ENDPOINT,
        PROFILE,
        first,
        latest,
        expected,
        entries.size(),
        segments.size(),
        gapCount,
        checks,
        status(gapCount, segments));
  }

  private String status(
      int gapCount, List<OpsShardReadinessRouteCleanupEvidenceAnalyzer.Segment> segments) {
    boolean segmentsContinuous = true;
    for (int index = 1; index < segments.size(); index++) {
      segmentsContinuous =
          segmentsContinuous
              && segments.get(index).firstJavaVersion()
                  == segments.get(index - 1).lastJavaVersion() + 1;
    }
    boolean passed =
        gapCount == 0
            && segmentsContinuous
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous()
            && OpsShardReadinessRouteCleanupEvidenceAnalyzer.allEntriesKeepReadOnlyBoundary();
    return passed ? "passed" : "blocked";
  }
}
