package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupPolicyGuardService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POLICY_GUARD;

  static final String PROFILE = "java-shard-readiness-route-cleanup-policy-guard.v1";

  private final OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService;

  private final OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService;

  public OpsShardReadinessRouteCleanupPolicyGuardService(
      OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService,
      OpsShardReadinessRouteCleanupEvidenceRegisterService evidenceRegisterService) {
    this.operationalSnapshotService = operationalSnapshotService;
    this.evidenceRegisterService = evidenceRegisterService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupPolicyGuardResponse guard() {
    OpsShardReadinessRouteCleanupOperationalSnapshotResponse snapshot =
        operationalSnapshotService.snapshot();
    OpsShardReadinessRouteCleanupEvidenceRegisterResponse register =
        evidenceRegisterService.register();
    List<OpsShardReadinessRouteCleanupPolicyGuardResponse.GuardRule> rules =
        List.of(
            rule("write-routing", "opening write routing"),
            rule("active-shard-router", "activating shard router"),
            rule("credential-value", "exposing credential values"),
            rule("raw-endpoint", "parsing raw endpoint values"),
            rule("managed-audit-connection", "opening managed audit connection"),
            rule("deployment-rollback", "deployment or rollback operations"),
            rule("node-autostart", "auto-starting or stopping Node, Java, or mini-kv"));
    boolean passed =
        snapshot.status().equals("passed")
            && register.status().equals("passed")
            && rules.stream()
                .noneMatch(OpsShardReadinessRouteCleanupPolicyGuardResponse.GuardRule::allowed);
    return new OpsShardReadinessRouteCleanupPolicyGuardResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        rules.size(),
        rules,
        passed ? "read-only-policy-guard-held" : "blocked",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupPolicyGuardResponse.GuardRule rule(
      String name, String blockedCapability) {
    return new OpsShardReadinessRouteCleanupPolicyGuardResponse.GuardRule(
        name, blockedCapability, false, "blocked-by-policy");
  }
}
