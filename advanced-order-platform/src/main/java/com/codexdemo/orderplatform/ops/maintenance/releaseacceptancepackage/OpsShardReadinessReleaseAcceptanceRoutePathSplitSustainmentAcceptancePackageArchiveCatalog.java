package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
              .ArchiveItem>
      items(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean passed = "passed".equals(source.status());
    return List.of(
        item("sustainment-response", "release-evidence-bundle", source.endpoint(), passed),
        item(
            "version-tags-v1580-v1604",
            "version-lineage",
            source.version(),
            "Java v1604".equals(source.version())),
        item(
            "boundary-lock-matrix",
            "runtime-boundary-archive",
            "boundary-count=" + source.boundaryGuardCount(),
            allBoundariesLocked(source)),
        item(
            "ci-gate-ledger",
            "ci-run-archive",
            "ci-gates=" + source.ciGateCount(),
            allCiGatesRequired(source)),
        item(
            "consumer-handoff-rules",
            "handoff-archive",
            "handoffs=" + source.consumerHandoffCount(),
            allConsumersReady(source)));
  }

  private static boolean allBoundariesLocked(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.boundaryGuards().stream()
        .allMatch(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard
                ::locked);
  }

  private static boolean allCiGatesRequired(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.ciGates().stream()
        .allMatch(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate::required);
  }

  private static boolean allConsumersReady(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return source.consumerHandoffs().stream()
        .allMatch(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff
                ::ready);
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ArchiveItem
      item(String artifact, String retention, String evidence, boolean ready) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
        .ArchiveItem(artifact, retention, evidence, ready);
  }
}
