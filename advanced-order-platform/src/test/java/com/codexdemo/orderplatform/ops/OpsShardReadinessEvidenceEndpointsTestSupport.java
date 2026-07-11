package com.codexdemo.orderplatform.ops;

import java.util.List;

public final class OpsShardReadinessEvidenceEndpointsTestSupport {

  private OpsShardReadinessEvidenceEndpointsTestSupport() {}

  public static List<String> liveEndpoints() {
    return OpsShardReadinessEvidenceEndpoints.liveEndpoints();
  }

  public static List<String> fixtureEndpoints() {
    return OpsShardReadinessEvidenceEndpoints.fixtureEndpoints();
  }

  public static List<String> liveProbeEndpoints() {
    return OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints();
  }

  public static List<String> fixtureProbeEndpoints() {
    return OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints();
  }
}
