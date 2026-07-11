package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              .AcceptanceCriterion>
      criteria(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              source) {
    return List.of(
        criterion(
            "source-digest-passed", "status=" + source.status(), "passed".equals(source.status())),
        criterion(
            "digest-sections-passed",
            "digest-sections="
                + source.passedDigestSectionCount()
                + "/"
                + source.digestSectionCount(),
            source.passedDigestSectionCount() == source.digestSectionCount()),
        criterion(
            "consumer-packets-ready",
            "consumer-packets="
                + source.readyConsumerPacketCount()
                + "/"
                + source.consumerPacketCount(),
            source.readyConsumerPacketCount() == source.consumerPacketCount()),
        criterion(
            "replay-instructions-read-only",
            "replay="
                + source.readOnlyReplayInstructionCount()
                + "/"
                + source.replayInstructionCount(),
            source.readOnlyReplayInstructionCount() == source.replayInstructionCount()),
        criterion(
            "boundaries-locked",
            "boundaries=" + source.lockedBoundaryCount() + "/" + source.boundaryLockCount(),
            source.lockedBoundaryCount() == source.boundaryLockCount()));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .AcceptanceCriterion
      criterion(String code, String evidence, boolean passed) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
        .AcceptanceCriterion(code, evidence, passed, passed ? "passed" : "blocked");
  }
}
