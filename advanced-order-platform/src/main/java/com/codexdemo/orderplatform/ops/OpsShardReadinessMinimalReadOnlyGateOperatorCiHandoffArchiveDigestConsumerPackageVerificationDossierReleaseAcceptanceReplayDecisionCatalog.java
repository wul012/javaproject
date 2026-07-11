package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReplayDecisionCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReplayDecisionCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
              .ReplayDecision>
      decisions(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              source) {
    boolean passed = "passed".equals(source.status()) && !source.executionAllowed();
    return List.of(
        decision(
            "focused-first",
            "run-focused-lanes-before-grouped",
            "ci-lanes=" + source.ciLaneCount(),
            passed),
        decision(
            "grouped-second",
            "run-grouped-after-focused",
            "read-only-ci=" + source.readOnlyCiLaneCount(),
            passed),
        decision(
            "build-third",
            "package-after-regression",
            "markdown=" + source.markdownSectionCount(),
            passed),
        decision("smoke-last", "smoke-after-build", "source=" + source.version(), passed),
        decision(
            "runtime-closed",
            "keep-runtime-execution-disabled",
            "executionAllowed=" + source.executionAllowed(),
            passed));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .ReplayDecision
      decision(String code, String decision, String evidence, boolean passed) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
        .ReplayDecision(code, decision, evidence, passed, passed ? "passed" : "blocked");
  }
}
