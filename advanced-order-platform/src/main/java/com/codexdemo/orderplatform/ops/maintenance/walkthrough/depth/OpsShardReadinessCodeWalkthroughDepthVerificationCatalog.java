package com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughDepthVerificationCatalog {

  private OpsShardReadinessCodeWalkthroughDepthVerificationCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep>
      verificationSteps() {
    return List.of(
        step(
            "depth-registry-route-tests",
            "OpsShardReadinessCodeWalkthroughDepthRoutePathsTests",
            "route delegation and endpoint stability"),
        step(
            "depth-registry-service-tests",
            "OpsShardReadinessCodeWalkthroughDepthRegistryServiceTests",
            "response counts, state, and checks"),
        step(
            "depth-registry-boundary-tests",
            "OpsShardReadinessCodeWalkthroughDepthBoundaryTests",
            "runtime-free and denied boundary catalog"),
        step(
            "walkthrough-archive-compliance-tests",
            "OpsCodeWalkthroughArchiveComplianceTests",
            "Chinese length, standard sections, and legacy marker rejection"),
        step("full-maven-regression", "mvn -q test", "full Java regression before push"));
  }

  private static OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep step(
      String name, String commandOrClass, String scope) {
    return new OpsShardReadinessCodeWalkthroughDepthRegistryResponse.VerificationStep(
        name, commandOrClass, scope, true);
  }
}
