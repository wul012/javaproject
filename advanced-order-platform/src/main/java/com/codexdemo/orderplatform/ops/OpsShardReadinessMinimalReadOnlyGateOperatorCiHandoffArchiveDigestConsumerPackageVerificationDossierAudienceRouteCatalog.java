package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              .AudienceRoute>
      routes(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source) {
    return source.consumerAudiences().stream()
        .map(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteCatalog
                ::route)
        .toList();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .AudienceRoute
      route(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .ConsumerAudience
              source) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
        .AudienceRoute(
        source.audience(),
        source.owner(),
        source.packet(),
        reviewerLane(source.packet()),
        source.ready(),
        source.status());
  }

  private static String reviewerLane(String packet) {
    if (packet.contains("ci")) {
      return "ci-non-docker-regression";
    }
    if (packet.contains("operator")) {
      return "operator-review";
    }
    if (packet.contains("archive")) {
      return "archive-verification";
    }
    return "read-only-consumer-review";
  }
}
