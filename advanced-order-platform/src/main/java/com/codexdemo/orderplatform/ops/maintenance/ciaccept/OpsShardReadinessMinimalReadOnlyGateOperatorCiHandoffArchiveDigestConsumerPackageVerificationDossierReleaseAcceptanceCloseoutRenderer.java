package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCloseoutRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCloseoutRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .CloseoutCheckpoint>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRendererSupport
        .section(
            "Closeout Checkpoints",
            entries.stream()
                .map(
                    entry ->
                        entry.order()
                            + ". "
                            + entry.item()
                            + " | owner="
                            + entry.owner()
                            + " | evidence="
                            + entry.evidence()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
