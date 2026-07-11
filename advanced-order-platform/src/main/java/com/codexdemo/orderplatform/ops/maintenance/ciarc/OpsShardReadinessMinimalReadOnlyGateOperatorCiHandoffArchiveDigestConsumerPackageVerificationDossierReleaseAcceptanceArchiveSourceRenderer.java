package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveSourceRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveSourceRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .SourceArchiveSnapshot>
              snapshots) {
    List<String> lines = new ArrayList<>();
    lines.add("source-archive-snapshot-count=" + snapshots.size());
    snapshots.forEach(
        snapshot ->
            lines.add(
                String.join(
                    " | ",
                    snapshot.version(),
                    snapshot.endpoint(),
                    snapshot.profile(),
                    snapshot.releaseAcceptanceState(),
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                        .statusLine(snapshot.status()))));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
        .section("Source Archive", lines);
  }
}
