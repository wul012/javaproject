package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupContinuityReportResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupContinuityReportService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEndpointManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEndpointManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupOperationalSnapshotService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_OPERATIONAL_SNAPSHOT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-operational-snapshot.v1";

  private final OpsShardReadinessRouteCleanupContinuityReportService continuityReportService;

  private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

  private final OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService;

  public OpsShardReadinessRouteCleanupOperationalSnapshotService(
      OpsShardReadinessRouteCleanupContinuityReportService continuityReportService,
      OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService,
      OpsShardReadinessRouteCleanupAcceptanceReceiptService acceptanceReceiptService) {
    this.continuityReportService = continuityReportService;
    this.endpointManifestService = endpointManifestService;
    this.acceptanceReceiptService = acceptanceReceiptService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupOperationalSnapshotResponse snapshot() {
    OpsShardReadinessRouteCleanupContinuityReportResponse continuity =
        continuityReportService.report();
    OpsShardReadinessRouteCleanupEndpointManifestResponse manifest =
        endpointManifestService.manifest();
    OpsShardReadinessRouteCleanupAcceptanceReceiptResponse receipt =
        acceptanceReceiptService.receipt();
    List<OpsShardReadinessRouteCleanupOperationalSnapshotResponse.BoundarySignal> signals =
        List.of(
            signal("versions-continuous", String.valueOf(continuity.versionsContinuous())),
            signal("read-only-boundary-held", String.valueOf(continuity.readOnlyBoundaryHeld())),
            signal("receipt-status", receipt.status()),
            signal("manifest-status", manifest.status()),
            signal("execution-allowed", String.valueOf(receipt.executionAllowed())));
    boolean passed =
        continuity.status().equals("passed")
            && manifest.status().equals("passed")
            && receipt.status().equals("passed")
            && !receipt.executionAllowed();
    return new OpsShardReadinessRouteCleanupOperationalSnapshotResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        continuity.latestVersion(),
        manifest.endpointCount(),
        continuity.phaseCount(),
        receipt.receipt(),
        signals.size(),
        signals,
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupOperationalSnapshotResponse.BoundarySignal signal(
      String name, String value) {
    return new OpsShardReadinessRouteCleanupOperationalSnapshotResponse.BoundarySignal(
        name, value, "passed");
  }
}
