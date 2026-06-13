package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceBoundaryRuleCatalog {

  private OpsShardReadinessCodeWalkthroughComplianceBoundaryRuleCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule>
      boundaryRules() {
    return List.of(
        boundary(
            "no-write-routing",
            "java-ops",
            "write routing",
            "walkthrough compliance is a read-only evidence registry"),
        boundary(
            "no-active-shard-router",
            "java-ops",
            "active shard router",
            "Node v367 only validated minimal read-only gates"),
        boundary(
            "no-credential-value",
            "java-security",
            "credential value read",
            "only credential handles or policy names may be documented"),
        boundary(
            "no-raw-endpoint-url",
            "java-security",
            "raw endpoint URL resolution",
            "endpoint handles stay symbolic"),
        boundary(
            "no-managed-audit-connection",
            "java-ops",
            "managed audit HTTP or TCP connection",
            "this registry does not connect to upstream systems"),
        boundary(
            "no-deployment-rollback",
            "java-release",
            "deployment or rollback action",
            "the batch only archives source evidence"),
        boundary(
            "no-java-autostart",
            "java-runtime",
            "Java service autostart",
            "tests instantiate services directly"),
        boundary(
            "no-minikv-autostart",
            "mini-kv-runtime",
            "mini-kv process autostart",
            "mini-kv is only named as an untouched read-only boundary"));
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule boundary(
      String code, String owner, String forbiddenAction, String rationale) {
    return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.BoundaryRule(
        code, owner, forbiddenAction, false, rationale);
  }
}
