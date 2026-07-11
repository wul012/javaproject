package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceEvidenceChainRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceEvidenceChainRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .EvidenceChainEntry>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRendererSupport
        .section(
            "Evidence Chain",
            entries.stream()
                .map(
                    entry ->
                        entry.order()
                            + ". "
                            + entry.artifact()
                            + " | target="
                            + entry.releaseTarget()
                            + " | source="
                            + entry.sourceEvidence()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
