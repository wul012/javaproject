package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse;
import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRouteContractCatalog {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRouteContractCatalog() {}

  static List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RouteContract>
      routeContracts(
          List<OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RouteFieldLock>
              locks) {
    return locks.stream()
        .map(
            lock ->
                new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.RouteContract(
                    lock.sectionCode(),
                    lock.endpoint(),
                    lock.profile(),
                    lock.javaVersion(),
                    lock.nodeVersionMarker(),
                    lock.lockedFieldCount(),
                    "downstream-may-read-route-contract-only",
                    "passed"))
        .toList();
  }
}
