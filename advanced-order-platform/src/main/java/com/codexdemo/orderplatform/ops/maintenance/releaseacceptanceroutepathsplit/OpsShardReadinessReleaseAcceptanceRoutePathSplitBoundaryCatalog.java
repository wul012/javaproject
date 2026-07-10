package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard> guards() {
    return List.of(
        guard("write-routing", "route constants only; no command path is created"),
        guard(
            "active-shard-router",
            "split records readiness route names but does not route traffic"),
        guard("credential-value-read", "no credential handle or credential value is dereferenced"),
        guard("raw-endpoint-resolution", "only stable relative ops paths are compared"),
        guard("managed-audit-connection", "no HTTP/TCP client is built by this registry"),
        guard(
            "deployment-rollback", "route-path split does not emit deployment or rollback actions"),
        guard(
            "sibling-autostart",
            "Node v1846 parallel work does not authorize Java or mini-kv startup"));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard guard(
      String boundary, String evidence) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard(
        boundary, true, evidence);
  }
}
