package com.codexdemo.orderplatform.ops.maintenance.readonlyevidence;

import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReadOnlyEndpointRegistryIntegrityService {

  public static final String ENDPOINT =
      OpsShardReadinessService.BASE_PATH
          + OpsShardReadinessRoutePaths.READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY;
  public static final String FIXTURE_ENDPOINT =
      "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json";
  public static final String EVIDENCE_PATH =
      "e/184/evidence/java-shard-readiness-read-only-endpoint-registry-integrity-v184.json";

  @Transactional(readOnly = true)
  public OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse integrity() {
    List<OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.EndpointPair> pairs =
        OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184EndpointPairs();
    List<String> liveEndpoints =
        OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints();
    List<String> fixtureEndpoints =
        OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints();

    boolean pairCountsAligned =
        pairs.size() == liveEndpoints.size() && pairs.size() == fixtureEndpoints.size();
    boolean liveEndpointsDistinct = new HashSet<>(liveEndpoints).size() == liveEndpoints.size();
    boolean fixtureEndpointsDistinct =
        new HashSet<>(fixtureEndpoints).size() == fixtureEndpoints.size();
    boolean pairsHaveLiveAndFixture =
        pairs.stream()
            .allMatch(pair -> !pair.liveEndpoint().isBlank() && !pair.fixtureEndpoint().isBlank());
    boolean endpointRegistryIncludesIntegrity = liveEndpoints.contains(ENDPOINT);
    boolean fixtureRegistryIncludesIntegrity = fixtureEndpoints.contains(FIXTURE_ENDPOINT);
    String status =
        pairCountsAligned
                && liveEndpointsDistinct
                && fixtureEndpointsDistinct
                && pairsHaveLiveAndFixture
                && endpointRegistryIncludesIntegrity
                && fixtureRegistryIncludesIntegrity
            ? "passed"
            : "blocked";

    return new OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse(
        "advanced-order-platform",
        "Java v184",
        true,
        false,
        false,
        pairs.size(),
        liveEndpoints.size(),
        fixtureEndpoints.size(),
        pairCountsAligned,
        liveEndpointsDistinct,
        fixtureEndpointsDistinct,
        pairsHaveLiveAndFixture,
        endpointRegistryIncludesIntegrity,
        fixtureRegistryIncludesIntegrity,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        "java-shard-readiness-read-only-endpoint-registry-integrity-receipt-v184",
        verificationChecks(
            pairs,
            liveEndpoints,
            fixtureEndpoints,
            pairCountsAligned,
            liveEndpointsDistinct,
            fixtureEndpointsDistinct,
            pairsHaveLiveAndFixture,
            endpointRegistryIncludesIntegrity,
            fixtureRegistryIncludesIntegrity),
        blockedOperations(),
        EVIDENCE_PATH,
        status);
  }

  private List<String> verificationChecks(
      List<OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.EndpointPair> pairs,
      List<String> liveEndpoints,
      List<String> fixtureEndpoints,
      boolean pairCountsAligned,
      boolean liveEndpointsDistinct,
      boolean fixtureEndpointsDistinct,
      boolean pairsHaveLiveAndFixture,
      boolean endpointRegistryIncludesIntegrity,
      boolean fixtureRegistryIncludesIntegrity) {
    return List.of(
        "endpoint-pairs-count:" + pairs.size(),
        "live-endpoints-count:" + liveEndpoints.size(),
        "fixture-endpoints-count:" + fixtureEndpoints.size(),
        "pair-counts-aligned:" + pairCountsAligned,
        "live-endpoints-distinct:" + liveEndpointsDistinct,
        "fixture-endpoints-distinct:" + fixtureEndpointsDistinct,
        "pairs-have-live-and-fixture:" + pairsHaveLiveAndFixture,
        "endpoint-registry-includes-integrity:" + endpointRegistryIncludesIntegrity,
        "fixture-registry-includes-integrity:" + fixtureRegistryIncludesIntegrity);
  }

  private List<String> blockedOperations() {
    return List.of(
        "write-routing",
        "active-shard-router",
        "credential-value-read",
        "raw-endpoint-parse",
        "managed-audit-connection",
        "deployment-or-rollback",
        "node-start-or-stop-java-or-mini-kv");
  }
}
