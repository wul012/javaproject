package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEndpointManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupFinalDigestService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupEvidenceRegisterService {

  static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_REGISTER;

  static final String PROFILE = "java-shard-readiness-route-cleanup-evidence-register.v1";

  private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

  private final OpsShardReadinessRouteCleanupFinalDigestService finalDigestService;

  public OpsShardReadinessRouteCleanupEvidenceRegisterService(
      OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService,
      OpsShardReadinessRouteCleanupFinalDigestService finalDigestService) {
    this.endpointManifestService = endpointManifestService;
    this.finalDigestService = finalDigestService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupEvidenceRegisterResponse register() {
    List<OpsShardReadinessRouteCleanupEvidenceRegisterResponse.RegisteredEvidence>
        registeredEvidence =
            endpointManifestService.manifest().endpoints().stream()
                .map(
                    endpoint ->
                        new OpsShardReadinessRouteCleanupEvidenceRegisterResponse
                            .RegisteredEvidence(
                            endpoint.constantName(),
                            endpoint.endpoint(),
                            category(endpoint.route()),
                            endpoint.readOnly(),
                            endpoint.executionAllowed(),
                            endpoint.status()))
                .toList();
    return new OpsShardReadinessRouteCleanupEvidenceRegisterResponse(
        "advanced-order-platform",
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
        true,
        false,
        ENDPOINT,
        PROFILE,
        registeredEvidence.size(),
        registeredEvidence,
        finalDigestService.digest().digestValue(),
        OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus());
  }

  private String category(String route) {
    if (route.contains("handoff") || route.contains("receipt")) {
      return "handoff";
    }
    if (route.contains("guard") || route.contains("gate") || route.contains("boundary")) {
      return "governance";
    }
    if (route.contains("manifest") || route.contains("digest") || route.contains("catalog")) {
      return "summary";
    }
    return "evidence";
  }
}
