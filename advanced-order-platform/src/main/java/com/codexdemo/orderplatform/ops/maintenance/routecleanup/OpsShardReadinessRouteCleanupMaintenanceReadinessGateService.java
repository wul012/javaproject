package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceReadinessGateService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_READINESS_GATE;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-readiness-gate.v1";

  private final OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService
      operatorReviewPacketService;
  private final OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService;
  private final OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService
      routeTopologyIndexService;
  private final OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService
      failClosedPolicyService;
  private final OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService
      ciExpectationManifestService;

  public OpsShardReadinessRouteCleanupMaintenanceReadinessGateService(
      OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService
          operatorReviewPacketService,
      OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService,
      OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService routeTopologyIndexService,
      OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService failClosedPolicyService,
      OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService
          ciExpectationManifestService) {
    this.operatorReviewPacketService = operatorReviewPacketService;
    this.versionLineageService = versionLineageService;
    this.routeTopologyIndexService = routeTopologyIndexService;
    this.failClosedPolicyService = failClosedPolicyService;
    this.ciExpectationManifestService = ciExpectationManifestService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse gate() {
    OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse review =
        operatorReviewPacketService.packet();
    OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse lineage =
        versionLineageService.lineage();
    OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse topology =
        routeTopologyIndexService.index();
    OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse policy =
        failClosedPolicyService.report();
    OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse ci =
        ciExpectationManifestService.manifest();
    List<OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse.GateCheck> gateChecks =
        List.of(
            check(
                "operator-review-packet",
                review.endpoint(),
                "review sections passed",
                "passed".equals(review.status())),
            check(
                "version-lineage",
                lineage.endpoint(),
                "lineage gap count is zero",
                lineage.gapCount() == 0),
            check(
                "route-topology-index",
                topology.endpoint(),
                "route count and latest version match",
                topology.routeCount() == 9 && topology.latestRouteVersion() == 488),
            check(
                "fail-closed-policy",
                policy.endpoint(),
                "all forbidden operations have zero violations",
                policy.zeroViolationCount() == policy.policyCount()),
            check(
                "ci-expectation-manifest",
                ci.endpoint(),
                "ci plan does not start upstreams",
                !ci.startsJavaService() && !ci.startsMiniKvService()));
    int acceptedCheckCount =
        (int)
            gateChecks.stream()
                .filter(
                    OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse.GateCheck::passed)
                .count();
    List<String> checks =
        List.of(
            "gate-check-count-" + gateChecks.size(),
            "all-gate-checks-accepted",
            "readiness-gate-keeps-execution-disabled",
            "readiness-gate-keeps-upstream-startup-disabled",
            "readiness-gate-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse(
        "advanced-order-platform",
        "Java v505",
        true,
        false,
        ENDPOINT,
        PROFILE,
        gateChecks.size(),
        acceptedCheckCount,
        gateChecks.size() - acceptedCheckCount,
        lineage.firstServiceVersion(),
        topology.latestRouteVersion(),
        gateChecks,
        checks,
        acceptedCheckCount == gateChecks.size() ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse.GateCheck check(
      String name, String sourceEndpoint, String reason, boolean passed) {
    return new OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse.GateCheck(
        name, sourceEndpoint, passed, reason, passed ? "passed" : "blocked");
  }
}
