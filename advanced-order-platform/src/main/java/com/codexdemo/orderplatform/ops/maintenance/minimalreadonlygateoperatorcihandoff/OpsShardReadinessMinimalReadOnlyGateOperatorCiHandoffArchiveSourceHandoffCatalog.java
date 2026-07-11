package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceHandoffCatalog {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceHandoffCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .SourceHandoffSnapshot>
      snapshots(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return List.of(
        new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
            .SourceHandoffSnapshot(
            source.version(),
            source.endpoint(),
            source.profile(),
            source.sourcePlan(),
            source.requiredArchiveVerificationPlan(),
            source.recommendedOperatorPlan(),
            source.handoffState(),
            source.operatorLaneCount(),
            source.ciBatchCount(),
            source.boundaryLockCount(),
            source.status()));
  }
}
