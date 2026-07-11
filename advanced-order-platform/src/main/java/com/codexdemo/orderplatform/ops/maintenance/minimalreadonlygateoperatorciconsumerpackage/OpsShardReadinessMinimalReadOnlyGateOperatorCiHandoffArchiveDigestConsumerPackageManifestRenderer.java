package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .ManifestEntry>
              manifest) {
    List<String> lines = new ArrayList<>();
    lines.add("manifest-entry-count=" + manifest.size());
    manifest.forEach(
        entry ->
            lines.add(
                entry.name()
                    + "="
                    + entry.value()
                    + " | required="
                    + entry.required()
                    + " | status="
                    + entry.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
        .section("Manifest", lines);
  }
}
