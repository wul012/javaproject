package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReadinessRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReadinessRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .ReleaseReadinessGate>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRendererSupport
        .section(
            "Readiness Gates",
            entries.stream()
                .map(
                    entry ->
                        entry.code()
                            + " | expected="
                            + entry.expected()
                            + " | actual="
                            + entry.actual()
                            + " | evidence="
                            + entry.evidence()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
