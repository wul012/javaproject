package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffSourceArchiveCatalog {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffSourceArchiveCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
              .SourceArchiveSnapshot>
      snapshots(
          OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
              archive) {
    return List.of(
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .SourceArchiveSnapshot(
            archive.version(),
            archive.endpoint(),
            archive.sourcePlan(),
            archive.archiveState(),
            archive.artifactVerificationCount(),
            archive.readTargetVerificationCount(),
            archive.gateCheckVerificationCount(),
            archive.boundaryVerificationCount(),
            archive.status()));
  }
}
