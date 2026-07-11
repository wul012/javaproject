package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierProvenanceCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierProvenanceCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              .ProvenanceEntry>
      provenance(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              source) {
    return List.of(
        entry("source-consumer-package-version", source.version(), true),
        entry("source-consumer-package-endpoint", source.endpoint(), true),
        entry("source-consumer-package-profile", source.profile(), true),
        entry("source-digest-version", source.sourceDigestVersion(), true),
        entry("source-digest-state", source.sourceDigestState(), true),
        entry("source-consumer-package-state", source.consumerPackageState(), true));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
          .ProvenanceEntry
      entry(String name, String value, boolean required) {
    boolean passed = required && value != null && !value.isBlank();
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
        .ProvenanceEntry(name, value, required, passed ? "passed" : "blocked");
  }
}
