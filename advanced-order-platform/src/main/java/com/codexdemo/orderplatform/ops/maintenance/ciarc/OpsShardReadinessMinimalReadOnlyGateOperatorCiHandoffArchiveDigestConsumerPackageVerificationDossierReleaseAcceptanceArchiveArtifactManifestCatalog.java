package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveArtifactManifestCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveArtifactManifestCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              .ArtifactManifestEntry>
      manifest(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
              source) {
    return List.of(
        entry("source-release-acceptance-version", source.version(), true),
        entry("source-release-acceptance-state", source.releaseAcceptanceState(), true),
        entry("readiness-gates-passed", String.valueOf(source.passedReadinessGateCount()), true),
        entry(
            "evidence-chain-passed", String.valueOf(source.passedEvidenceChainEntryCount()), true),
        entry("signoff-lanes-ready", String.valueOf(source.readySignoffLaneCount()), true),
        entry(
            "ci-replay-lanes-read-only", String.valueOf(source.readOnlyCiReplayLaneCount()), true),
        entry(
            "closeout-checkpoints-ready",
            String.valueOf(source.readyCloseoutCheckpointCount()),
            true));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
          .ArtifactManifestEntry
      entry(String name, String value, boolean required) {
    boolean passed = required && value != null && !value.isBlank();
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
        .ArtifactManifestEntry(name, value, required, passed ? "passed" : "blocked");
  }
}
