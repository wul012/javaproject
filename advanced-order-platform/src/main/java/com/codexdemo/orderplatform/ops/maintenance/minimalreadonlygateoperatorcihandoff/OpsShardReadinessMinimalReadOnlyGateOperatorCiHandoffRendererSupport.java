package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }
}
