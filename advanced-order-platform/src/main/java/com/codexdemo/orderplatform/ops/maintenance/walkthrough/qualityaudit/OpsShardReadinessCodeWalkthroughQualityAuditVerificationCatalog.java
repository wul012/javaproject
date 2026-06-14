package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import java.util.List;

final class OpsShardReadinessCodeWalkthroughQualityAuditVerificationCatalog {

  private OpsShardReadinessCodeWalkthroughQualityAuditVerificationCatalog() {}

  static List<OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep>
      verificationSteps() {
    return List.of(
        step(
            "quality-audit-targeted-tests",
            "OpsShardReadinessCodeWalkthroughQualityAudit*Tests",
            "route, service, renderer, boundary, controller, immutability"),
        step(
            "walkthrough-archive-compliance",
            "OpsCodeWalkthroughArchiveComplianceTests",
            "future walkthroughs keep the required structure and avoid legacy markers"),
        step(
            "quality-gate-regression",
            "OpsShardReadinessCodeWalkthroughQualityGate*Tests",
            "audit registry remains aligned with the active quality gate"),
        step(
            "full-maven-regression",
            "mvn -q test",
            "full Java regression without Docker-only assumptions"),
        step(
            "remote-ci",
            "GitHub Actions Java Maven CI",
            "remote compile, non-Docker regression, and package verification"));
  }

  private static OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep step(
      String name, String commandOrClass, String scope) {
    return new OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.VerificationStep(
        name, commandOrClass, scope, true);
  }
}
