package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupConsumerChecklistService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_CHECKLIST;

  static final String PROFILE = "java-shard-readiness-route-cleanup-consumer-checklist.v1";

  private final OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService;

  private final OpsShardReadinessRouteCleanupContinuityReportService continuityReportService;

  public OpsShardReadinessRouteCleanupConsumerChecklistService(
      OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService,
      OpsShardReadinessRouteCleanupContinuityReportService continuityReportService) {
    this.consumerPacketService = consumerPacketService;
    this.continuityReportService = continuityReportService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupConsumerChecklistResponse checklist() {
    OpsShardReadinessRouteCleanupConsumerPacketResponse packet = consumerPacketService.packet();
    OpsShardReadinessRouteCleanupContinuityReportResponse continuity =
        continuityReportService.report();
    List<OpsShardReadinessRouteCleanupConsumerChecklistResponse.ChecklistItem> items =
        List.of(
            item(
                "packet-passed",
                packet.status().equals("passed"),
                OpsShardReadinessRouteCleanupConsumerPacketService.ENDPOINT),
            item(
                "continuity-passed",
                continuity.status().equals("passed"),
                OpsShardReadinessRouteCleanupContinuityReportService.ENDPOINT),
            item(
                "read-only-boundary-held",
                continuity.readOnlyBoundaryHeld(),
                "readOnlyBoundaryHeld=true"),
            item(
                "blocked-operations-present",
                packet.blockedOperations().size() >= 7,
                "blockedOperations=" + packet.blockedOperations().size()));
    boolean passed =
        items.stream()
            .allMatch(OpsShardReadinessRouteCleanupConsumerChecklistResponse.ChecklistItem::passed);
    return new OpsShardReadinessRouteCleanupConsumerChecklistResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        items.size(),
        items,
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupConsumerChecklistResponse.ChecklistItem item(
      String name, boolean passed, String evidence) {
    return new OpsShardReadinessRouteCleanupConsumerChecklistResponse.ChecklistItem(
        name, passed, evidence, passed ? "passed" : "blocked");
  }
}
