package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationCatalog {

  private OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
              .BoundaryVerification>
      boundaryVerifications(
          OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry) {
    return sourceRegistry.boundaryRules().stream()
        .map(
            rule ->
                new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .BoundaryVerification(
                    rule.code(),
                    rule.forbiddenAction(),
                    rule.allowed(),
                    !rule.allowed(),
                    rule.allowed() ? "blocked" : "passed"))
        .toList();
  }
}
