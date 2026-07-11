package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorciconsumerpackage;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistRenderer() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                      .HandoffChecklistItem>
              checklist) {
    List<String> lines = new ArrayList<>();
    lines.add("handoff-checklist-count=" + checklist.size());
    checklist.forEach(
        item ->
            lines.add(
                item.order()
                    + ". "
                    + item.item()
                    + " | "
                    + item.owner()
                    + " | ready="
                    + item.ready()
                    + " | status="
                    + item.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
        .section("Handoff Checklist", lines);
  }
}
