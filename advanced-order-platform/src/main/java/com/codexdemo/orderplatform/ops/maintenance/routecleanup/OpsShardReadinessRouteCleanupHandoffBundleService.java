package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupHandoffBundleService {

  static final String ENDPOINT = RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.HANDOFF_BUNDLE;

  static final String PROFILE = "java-shard-readiness-route-cleanup-handoff-bundle.v1";

  private final OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService;

  private final OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService;

  private final OpsShardReadinessRouteCleanupRegressionGuardService regressionGuardService;

  public OpsShardReadinessRouteCleanupHandoffBundleService(
      OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService,
      OpsShardReadinessRouteCleanupCiEvidenceService ciEvidenceService,
      OpsShardReadinessRouteCleanupRegressionGuardService regressionGuardService) {
    this.consumerPacketService = consumerPacketService;
    this.ciEvidenceService = ciEvidenceService;
    this.regressionGuardService = regressionGuardService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupHandoffBundleResponse bundle() {
    OpsShardReadinessRouteCleanupConsumerPacketResponse packet = consumerPacketService.packet();
    OpsShardReadinessRouteCleanupCiEvidenceResponse ciEvidence = ciEvidenceService.evidence();
    OpsShardReadinessRouteCleanupRegressionGuardResponse guard = regressionGuardService.guard();
    List<OpsShardReadinessRouteCleanupHandoffBundleResponse.BundleComponent> components =
        List.of(
            component(
                "consumer-packet",
                OpsShardReadinessRouteCleanupConsumerPacketService.ENDPOINT,
                packet.status()),
            component(
                "ci-evidence",
                OpsShardReadinessRouteCleanupCiEvidenceService.ENDPOINT,
                ciEvidence.status()),
            component(
                "regression-guard",
                OpsShardReadinessRouteCleanupRegressionGuardService.ENDPOINT,
                guard.status()));
    boolean passed = components.stream().allMatch(component -> component.status().equals("passed"));
    return new OpsShardReadinessRouteCleanupHandoffBundleResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        components.size(),
        components,
        passed ? "bundle-ready-for-read-only-consumer" : "blocked",
        passed ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupHandoffBundleResponse.BundleComponent component(
      String name, String endpoint, String status) {
    return new OpsShardReadinessRouteCleanupHandoffBundleResponse.BundleComponent(
        name, endpoint, status);
  }
}
