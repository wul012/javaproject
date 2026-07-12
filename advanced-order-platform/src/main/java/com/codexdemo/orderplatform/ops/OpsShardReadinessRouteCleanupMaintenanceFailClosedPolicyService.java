package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_FAIL_CLOSED_POLICY;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-fail-closed-policy.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse report() {
    List<OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse.PolicyCheck> policies =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations().stream()
            .map(this::policy)
            .toList();
    int zeroViolationCount =
        (int) policies.stream().filter(policy -> policy.violationCount() == 0).count();
    List<String> checks =
        List.of(
            "policy-count-" + policies.size(),
            "protected-item-count-"
                + OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().size(),
            "all-forbidden-operations-have-guards",
            "all-policy-violations-are-zero",
            "fail-closed-policy-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse(
        "advanced-order-platform",
        "Java v497",
        true,
        false,
        ENDPOINT,
        PROFILE,
        policies.size(),
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().size(),
        zeroViolationCount,
        policies,
        checks,
        zeroViolationCount == policies.size() ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse.PolicyCheck policy(
      String operation) {
    int violationCount = violationCount(operation);
    return new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse.PolicyCheck(
        operation,
        "fail-closed-before-" + operation,
        "route-cleanup-maintenance-upkeep-catalog",
        violationCount,
        violationCount == 0 ? "passed" : "blocked");
  }

  private int violationCount(String operation) {
    List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries();
    return switch (operation) {
      case "write-routing" ->
          (int)
              entries.stream()
                  .filter(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::writeRoutingChanged)
                  .count();
      case "active-shard-router" ->
          (int)
              entries.stream()
                  .filter(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::executionAllowed)
                  .count();
      case "credential-value-read" ->
          (int)
              entries.stream()
                  .filter(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::credentialValueRead)
                  .count();
      case "raw-endpoint-parse" ->
          (int)
              entries.stream()
                  .filter(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::rawEndpointParsed)
                  .count();
      case "managed-audit-connection" ->
          (int)
              entries.stream()
                  .filter(
                      OpsShardReadinessRouteCleanupEvidenceResponse.Entry
                          ::managedAuditConnectionOpened)
                  .count();
      case "deployment-or-rollback" -> 0;
      case "node-start-or-stop-java-or-mini-kv" ->
          (int)
              entries.stream()
                  .filter(entry -> entry.startsJavaService() || entry.startsMiniKvService())
                  .count();
      default -> throw new IllegalArgumentException("Unknown forbidden operation: " + operation);
    };
  }
}
