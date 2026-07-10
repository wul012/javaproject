package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexVerificationCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexVerificationCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
              .VerificationGate>
      gates(
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
              source) {
    boolean passed = "passed".equals(source.status());
    return List.of(
        gate(
            "focused-archive-index-tests",
            "mvn -q \"-Dtest=*ReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndex*\" test",
            passed),
        gate(
            "related-route-path-split-tests",
            "mvn -q \"-Dtest=*ReleaseAcceptanceRoutePathSplit*\" test",
            passed),
        gate("remote-ci-confirmation", "GitHub Actions Java Maven CI after push", passed),
        gate("runtime-execution-closed", "executionAllowed=false", !source.executionAllowed()),
        gate("sibling-startup-closed", "Node and mini-kv remain parallel evidence only", passed));
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
          .VerificationGate
      gate(String gate, String evidence, boolean passed) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
        .VerificationGate(gate, evidence, passed);
  }
}
