package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport {

  private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport() {}

  static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
          .MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
        .MarkdownSection(heading, List.copyOf(lines));
  }

  static String statusLine(String name, String status) {
    return name + "=" + status;
  }
}
