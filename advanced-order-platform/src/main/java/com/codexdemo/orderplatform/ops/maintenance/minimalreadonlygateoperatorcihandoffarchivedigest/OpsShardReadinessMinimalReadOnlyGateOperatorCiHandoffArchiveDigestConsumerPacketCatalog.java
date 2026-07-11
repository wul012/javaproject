package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              .ConsumerPacket>
      packets(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              source) {
    boolean sourcePassed = "passed".equals(source.status());
    return List.of(
        packet("operator-runbook-extract", "operator", sourcePassed),
        packet("ci-batch-matrix", "ci", sourcePassed),
        packet("boundary-lock-manifest", "operator-ci", sourcePassed),
        packet("archive-scorecard-summary", "release-review", sourcePassed));
  }

  private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
          .ConsumerPacket
      packet(String packet, String owner, boolean ready) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
        .ConsumerPacket(packet, owner, true, true, ready, ready ? "passed" : "blocked");
  }
}
