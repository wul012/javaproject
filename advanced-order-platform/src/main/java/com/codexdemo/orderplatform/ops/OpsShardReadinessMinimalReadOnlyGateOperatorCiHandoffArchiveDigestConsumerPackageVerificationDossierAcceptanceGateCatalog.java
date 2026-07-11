package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAcceptanceGateCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAcceptanceGateCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              .AcceptanceGate>
      gates(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source) {
    return source.acceptanceCriteria().stream()
        .map(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAcceptanceGateCatalog
                ::gate)
        .toList();
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .AcceptanceGate
      gate(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                  .AcceptanceCriterion
              source) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
        .AcceptanceGate(
        source.code(),
        source.evidence(),
        source.code() + "-verification-dossier",
        source.passed(),
        source.status());
  }
}
