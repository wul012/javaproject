package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughDepthBoundaryCatalog {

  private OpsShardReadinessCodeWalkthroughDepthBoundaryCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule> boundaryRules() {
    return List.of(
        denied("no-write-routing", "enable order write routing from a walkthrough-quality batch"),
        denied(
            "no-active-shard-router", "turn a documentation registry into an active shard router"),
        denied("no-credential-value", "read or print credential values while documenting code"),
        denied("no-raw-endpoint-url", "resolve or output raw managed-audit endpoint URLs"),
        denied("no-managed-audit-http", "open managed audit HTTP/TCP calls"),
        denied("no-deployment-or-rollback", "deploy or roll back production systems"),
        denied("no-java-autostart", "start the Java service automatically"),
        denied("no-minikv-autostart", "start mini-kv automatically"));
  }

  private static OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule denied(
      String code, String forbiddenAction) {
    return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse.BoundaryRule(
        code, forbiddenAction, false, "walkthrough depth enforcement is repository evidence only");
  }
}
