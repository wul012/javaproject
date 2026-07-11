package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchVerificationCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchVerificationCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .CiBatchVerification>
      ciBatchVerifications(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return source.ciBatches().stream()
        .map(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchVerificationCatalog
                ::ciBatch)
        .toList();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .CiBatchVerification
      ciBatch(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.CiBatchPlan
              source) {
    boolean archived = source.passed();
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
        .CiBatchVerification(
        source.batch(),
        source.order(),
        source.commandFamily(),
        source.passed(),
        archived,
        archived ? "passed" : "blocked");
  }
}
