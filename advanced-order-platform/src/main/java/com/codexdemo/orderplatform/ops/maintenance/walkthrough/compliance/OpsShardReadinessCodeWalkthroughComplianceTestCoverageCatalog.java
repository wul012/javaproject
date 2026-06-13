package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughComplianceTestCoverageCatalog {

  private OpsShardReadinessCodeWalkthroughComplianceTestCoverageCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage>
      testCoverages() {
    return List.of(
        coverage(
            "OpsCodeWalkthroughArchiveComplianceTests",
            "historical walkthrough cleanup",
            "non-standard historical walkthroughs are either standard or legacy-marked"),
        coverage(
            "OpsCodeWalkthroughArchiveComplianceTests",
            "future walkthrough standard",
            "walkthroughs after v289 include all required headings"),
        coverage(
            "OpsCodeWalkthroughArchiveComplianceTests",
            "future legacy marker guard",
            "walkthroughs after v289 cannot use the legacy marker"),
        coverage(
            "OpsShardReadinessCodeWalkthroughComplianceRegistryServiceTests",
            "registry identity and counts",
            "version, endpoint, source plan, headings, and counts are stable"),
        coverage(
            "OpsShardReadinessCodeWalkthroughComplianceRegistryBoundaryTests",
            "runtime boundary lock",
            "forbidden actions stay denied"),
        coverage(
            "OpsShardReadinessCodeWalkthroughComplianceRegistryRendererTests",
            "operator markdown",
            "rendered sections remain stable and scan-friendly"),
        coverage(
            "OpsShardReadinessCodeWalkthroughComplianceRegistryControllerTests",
            "HTTP entry point",
            "controller exposes the shared route constant"),
        coverage(
            "OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests",
            "response immutability",
            "response lists cannot be mutated by callers"));
  }

  private static OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage coverage(
      String testClass, String scope, String assertion) {
    return new OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.TestCoverage(
        testClass, scope, assertion, true);
  }
}
