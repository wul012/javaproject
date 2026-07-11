package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestCatalog {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestCatalog() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
              .ManifestEntry>
      manifest(
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
              source) {
    return List.of(
        entry("source-digest-version", source.version(), true),
        entry("source-archive-version", source.sourceArchiveVersion(), true),
        entry("source-digest-state", source.digestState(), true),
        entry("source-endpoint", source.endpoint(), true),
        entry("source-profile", source.profile(), true));
  }

  private static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .ManifestEntry
      entry(String name, String value, boolean required) {
    boolean passed = required && value != null && !value.isBlank();
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
        .ManifestEntry(name, value, required, passed ? "passed" : "blocked");
  }
}
