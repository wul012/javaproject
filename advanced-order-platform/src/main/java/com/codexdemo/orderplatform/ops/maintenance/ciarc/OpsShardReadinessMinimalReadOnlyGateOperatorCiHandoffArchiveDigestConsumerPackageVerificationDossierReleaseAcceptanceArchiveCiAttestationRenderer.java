package com.codexdemo.orderplatform.ops.maintenance.ciarc;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveCiAttestationRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveCiAttestationRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                      .CiAttestationEntry>
              attestations) {
    List<String> lines = new ArrayList<>();
    lines.add("ci-attestation-count=" + attestations.size());
    attestations.forEach(
        attestation ->
            lines.add(
                attestation.order()
                    + ". "
                    + attestation.batch()
                    + " | "
                    + attestation.commandFamily()
                    + " | "
                    + OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                        .flag("readOnly", attestation.readOnly())
                    + " | "
                    + OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                        .flag("sourcePassed", attestation.sourcePassed())
                    + " | "
                    + OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
                        .statusLine(attestation.status())));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRendererSupport
        .section("CI Attestations", lines);
  }
}
