package com.codexdemo.orderplatform.ops.maintenance.operatorcidossier;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSectionDigestRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSectionDigestRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
                      .SectionDigest>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRendererSupport
        .section(
            "Section Digests",
            entries.stream()
                .map(
                    entry ->
                        entry.heading()
                            + " | lines="
                            + entry.lineCount()
                            + " | required="
                            + entry.required()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
