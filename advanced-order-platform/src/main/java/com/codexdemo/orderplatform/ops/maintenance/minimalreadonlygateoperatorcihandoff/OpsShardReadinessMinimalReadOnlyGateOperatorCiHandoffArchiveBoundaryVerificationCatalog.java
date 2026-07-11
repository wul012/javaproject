package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryVerificationCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryVerificationCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
              .BoundaryVerification>
      boundaryVerifications(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse source) {
    return source.boundaryLocks().stream()
        .map(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryVerificationCatalog
                ::boundary)
        .toList();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .BoundaryVerification
      boundary(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.BoundaryLock
              source) {
    boolean archived = source.locked();
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
        .BoundaryVerification(
        source.code(),
        source.lockedBehavior(),
        source.locked(),
        archived,
        archived ? "passed" : "blocked");
  }
}
