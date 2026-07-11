package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCiReplayRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCiReplayRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .CiReplayLane>
              entries) {
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRendererSupport
        .section(
            "CI Replay Lanes",
            entries.stream()
                .map(
                    entry ->
                        entry.order()
                            + ". "
                            + entry.batch()
                            + " | command="
                            + entry.commandFamily()
                            + " | replay="
                            + entry.replayGroup()
                            + " | readOnly="
                            + entry.readOnly()
                            + " | status="
                            + entry.status())
                .toList());
  }
}
