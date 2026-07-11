package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck>
      crossChecks(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              source) {
    return source.artifactManifest().stream()
        .map(
            entry ->
                new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                    .ArtifactCrossCheck(
                    entry.name(),
                    entry.value(),
                    entry.required() ? "required-present" : "optional",
                    "passed".equals(entry.status()),
                    entry.status()))
        .toList();
  }
}
