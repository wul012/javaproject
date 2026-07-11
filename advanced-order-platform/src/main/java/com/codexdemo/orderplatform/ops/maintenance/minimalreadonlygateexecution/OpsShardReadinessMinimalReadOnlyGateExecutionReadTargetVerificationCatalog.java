package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetVerificationCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetVerificationCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
              .ReadTargetVerification>
      readTargetVerifications(
          OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.readTargets().stream()
        .map(
            target ->
                new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .ReadTargetVerification(
                    target.target(),
                    target.commandOrRoute(),
                    target.status(),
                    "passed".equals(target.status()),
                    "passed".equals(target.status()) ? "passed" : "blocked"))
        .toList();
  }
}
