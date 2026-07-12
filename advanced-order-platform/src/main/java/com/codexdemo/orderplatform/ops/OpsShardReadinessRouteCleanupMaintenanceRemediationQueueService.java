package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReadinessGateService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_REMEDIATION_QUEUE;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-remediation-queue.v1";

  private final OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService
      failClosedPolicyService;
  private final OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService;

  public OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService(
      OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService failClosedPolicyService,
      OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService) {
    this.failClosedPolicyService = failClosedPolicyService;
    this.readinessGateService = readinessGateService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse queue() {
    OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse policy =
        failClosedPolicyService.report();
    OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse gate =
        readinessGateService.gate();
    List<OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse.QueueItem> items =
        List.of(
            item(
                "fail-closed-policy-drift",
                "policy-zero-violations",
                policy.endpoint(),
                "inspect-policy-report-before-any-code-change",
                policy.status()),
            item(
                "readiness-gate-blocked",
                "blocked-gate-check-count-" + gate.blockedCheckCount(),
                gate.endpoint(),
                "review-gate-check-reason",
                gate.status()),
            item(
                "execution-boundary-drift",
                "executionAllowed-" + gate.executionAllowed(),
                gate.endpoint(),
                "keep-execution-disabled",
                gate.status()),
            item(
                "upstream-startup-drift",
                "starts-upstream-false",
                gate.endpoint(),
                "do-not-start-java-or-mini-kv",
                gate.status()));
    int blocked = (int) items.stream().filter(item -> !"standby".equals(item.status())).count();
    List<String> checks =
        List.of(
            "remediation-queue-item-count-" + items.size(),
            "queue-is-preview-only",
            "blocked-remediation-count-" + blocked,
            "remediation-does-not-execute-actions",
            "remediation-queue-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse(
        "advanced-order-platform",
        "Java v514",
        true,
        false,
        ENDPOINT,
        PROFILE,
        items.size(),
        items.size() - blocked,
        blocked,
        items,
        checks,
        blocked == 0 ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse.QueueItem item(
      String name, String trigger, String sourceEndpoint, String action, String sourceStatus) {
    return new OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse.QueueItem(
        name,
        trigger,
        sourceEndpoint,
        action,
        "passed".equals(sourceStatus) ? "standby" : "blocked");
  }
}
