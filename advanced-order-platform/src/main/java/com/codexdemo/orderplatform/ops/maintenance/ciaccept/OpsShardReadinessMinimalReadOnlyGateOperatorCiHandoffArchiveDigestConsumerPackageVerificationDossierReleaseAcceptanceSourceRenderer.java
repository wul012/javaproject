package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSourceRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSourceRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .SourceDossierSnapshot>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRendererSupport
        .section(
            "Source Dossier",
            entries.stream()
                .map(
                    entry ->
                        entry.version()
                            + " | state="
                            + entry.dossierState()
                            + " | sections="
                            + entry.sectionDigestCount()
                            + " | ci="
                            + entry.ciLaneCount()
                            + " | boundaries="
                            + entry.boundaryAuditCount()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
