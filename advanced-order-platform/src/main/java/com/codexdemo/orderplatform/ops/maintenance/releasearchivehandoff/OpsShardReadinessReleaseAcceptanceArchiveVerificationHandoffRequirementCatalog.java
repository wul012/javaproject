package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRequirementCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRequirementCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
              .VerificationRequirement>
      requirements(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              source) {
    return List.of(
        requirement(
            "source-archive-status",
            "source archive registry passed",
            1,
            "passed".equals(source.status()) ? 1 : 0),
        requirement(
            "artifact-manifest-passed",
            "all manifest entries passed",
            source.artifactManifestCount(),
            source.passedArtifactManifestCount()),
        requirement(
            "route-packages-ready",
            "all route packages ready",
            source.routePackageCount(),
            source.readyRoutePackageCount()),
        requirement(
            "operator-packs-ready",
            "all operator packs ready",
            source.operatorPackCount(),
            source.readyOperatorPackCount()),
        requirement(
            "ci-attestations-passed",
            "all CI attestations passed",
            source.ciAttestationCount(),
            source.passedCiAttestationCount()),
        requirement(
            "boundary-seals-locked",
            "all boundary seals locked",
            source.boundarySealCount(),
            source.lockedBoundarySealCount()),
        requirement(
            "retention-windows-ready",
            "all retention windows ready",
            source.retentionWindowCount(),
            source.readyRetentionWindowCount()),
        requirement(
            "closeout-ledger-ready",
            "all closeout ledger entries ready",
            source.closeoutLedgerCount(),
            source.readyCloseoutLedgerCount()));
  }

  private static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
          .VerificationRequirement
      requirement(String code, String evidence, int expected, int actual) {
    boolean passed = expected == actual;
    return new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
        .VerificationRequirement(
        code, evidence, expected, actual, passed, passed ? "passed" : "blocked");
  }
}
