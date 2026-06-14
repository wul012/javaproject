package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditBoundaryCatalog {

  private OpsShardReadinessCodeWalkthroughQualityAuditBoundaryCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit>
      boundaryAudits() {
    return List.of(
        boundary("no-write-routing", "write routing", "readOnly=true and executionAllowed=false"),
        boundary(
            "no-active-shard-router",
            "active shard router",
            "Node v367 says Java current work is not required"),
        boundary(
            "no-credential-value", "credential value read", "only handles and policies are named"),
        boundary(
            "no-raw-endpoint-url",
            "raw endpoint URL resolution",
            "endpoints remain route constants or symbolic anchors"),
        boundary(
            "no-managed-audit-connection",
            "managed audit HTTP or TCP connection",
            "registry is static Java evidence"),
        boundary(
            "no-deployment-rollback",
            "deployment or rollback",
            "batch produces source, tests, docs, and tags only"),
        boundary(
            "no-java-autostart", "Java service autostart", "tests instantiate services directly"),
        boundary(
            "no-minikv-autostart",
            "mini-kv process autostart",
            "mini-kv remains an untouched boundary"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit
      boundary(String code, String forbiddenAction, String evidence) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.BoundaryAudit(
        code, forbiddenAction, false, evidence);
  }
}
