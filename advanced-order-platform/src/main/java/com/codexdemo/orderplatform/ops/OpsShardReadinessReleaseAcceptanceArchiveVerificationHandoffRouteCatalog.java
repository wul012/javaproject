package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.ciarc.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRouteCatalog {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRouteCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff>
      handoffs(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
              source) {
    return source.routePackages().stream()
        .map(
            route ->
                new OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                    .RouteHandoff(
                    route.receiver(), route.owner(), route.packet(), route.ready(), route.status()))
        .toList();
  }
}
