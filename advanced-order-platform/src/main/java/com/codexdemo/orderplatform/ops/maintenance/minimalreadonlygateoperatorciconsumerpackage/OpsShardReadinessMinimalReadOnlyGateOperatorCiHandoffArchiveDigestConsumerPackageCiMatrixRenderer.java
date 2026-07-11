package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .CiMatrixEntry>
              entries) {
    List<String> lines = new ArrayList<>();
    lines.add("ci-matrix-count=" + entries.size());
    entries.forEach(
        entry ->
            lines.add(
                entry.order()
                    + ". "
                    + entry.batch()
                    + " | "
                    + entry.commandFamily()
                    + " | read-only="
                    + entry.readOnly()
                    + " | status="
                    + entry.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
        .section("CI Matrix", lines);
  }
}
