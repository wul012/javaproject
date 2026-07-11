package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourceRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourceRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                      .SourcePackageSnapshot>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport
        .section(
            "Source Consumer Package",
            entries.stream()
                .map(
                    entry ->
                        entry.version()
                            + " | state="
                            + entry.consumerPackageState()
                            + " | manifest="
                            + entry.manifestEntryCount()
                            + " | sections="
                            + entry.packageSectionCount()
                            + " | ci="
                            + entry.ciMatrixEntryCount()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
