package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseChecklistRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseChecklistRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                      .ReleaseChecklistItem>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport
        .section(
            "Release Checklist",
            entries.stream()
                .map(
                    entry ->
                        entry.order()
                            + ". "
                            + entry.item()
                            + " | owner="
                            + entry.owner()
                            + " | evidence="
                            + entry.releaseEvidence()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
