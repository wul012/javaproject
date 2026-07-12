package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_REPORT;

  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-boundary-report.v1";

  private final OpsShardReadinessRouteCleanupArchiveHandoffReceiptService
      archiveHandoffReceiptService;

  private final OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService;

  public OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService(
      OpsShardReadinessRouteCleanupArchiveHandoffReceiptService archiveHandoffReceiptService,
      OpsShardReadinessRouteCleanupPolicyGuardService policyGuardService) {
    this.archiveHandoffReceiptService = archiveHandoffReceiptService;
    this.policyGuardService = policyGuardService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse report() {
    OpsShardReadinessRouteCleanupArchiveHandoffReceiptResponse receipt =
        archiveHandoffReceiptService.receipt();
    OpsShardReadinessRouteCleanupPolicyGuardResponse guard = policyGuardService.guard();
    List<OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse.BoundaryRule> rules =
        List.of(
            rule("no-write-routing", "write routing"),
            rule("no-active-shard-router", "active shard router"),
            rule("no-credential-values", "credential value exposure"),
            rule("no-raw-endpoint-parse", "raw endpoint parsing"),
            rule("no-managed-audit-connection", "managed audit connection"),
            rule("no-deployment-rollback", "deployment or rollback"),
            rule("no-runtime-autostart", "Node, Java, or mini-kv automatic start/stop"));
    boolean passed =
        receipt.status().equals("passed")
            && guard.status().equals("passed")
            && rules.stream()
                .noneMatch(
                    OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse.BoundaryRule
                        ::allowed);
    return new OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        OpsShardReadinessRouteCleanupArchiveHandoffReceiptService.ENDPOINT,
        rules.size(),
        rules,
        passed ? "maintenance-boundary-held" : "blocked",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse.BoundaryRule rule(
      String name, String blockedCapability) {
    return new OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse.BoundaryRule(
        name, blockedCapability, false, "blocked");
  }
}
