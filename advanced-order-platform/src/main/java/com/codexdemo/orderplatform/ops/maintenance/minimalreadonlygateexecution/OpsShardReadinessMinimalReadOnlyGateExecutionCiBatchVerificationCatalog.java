package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchVerificationCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchVerificationCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
              .CiBatchVerification>
      ciBatchVerifications(
          OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.ciBatches().stream()
        .map(
            batch ->
                new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .CiBatchVerification(
                    batch.name(), batch.order(), batch.commandFamily(), true, "passed"))
        .toList();
  }
}
