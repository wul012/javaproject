package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }
}
