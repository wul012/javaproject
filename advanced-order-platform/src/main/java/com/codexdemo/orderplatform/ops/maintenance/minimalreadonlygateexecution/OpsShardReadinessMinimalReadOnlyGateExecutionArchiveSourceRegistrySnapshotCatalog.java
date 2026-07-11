package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveSourceRegistrySnapshotCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveSourceRegistrySnapshotCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
              .SourceRegistrySnapshot>
      snapshots(OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return List.of(
        new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .SourceRegistrySnapshot(
            sourceRegistry.version(),
            sourceRegistry.endpoint(),
            sourceRegistry.profile(),
            sourceRegistry.sourcePlan(),
            sourceRegistry.readTargetCount(),
            sourceRegistry.gateCheckCount(),
            sourceRegistry.boundaryRuleCount(),
            sourceRegistry.status()));
  }
}
