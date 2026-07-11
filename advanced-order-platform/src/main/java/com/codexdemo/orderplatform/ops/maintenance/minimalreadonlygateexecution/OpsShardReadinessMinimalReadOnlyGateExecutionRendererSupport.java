package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport {

  private OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport() {}

  static OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection section(
      String heading, List<String> lines) {
    return new OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.MarkdownSection(
        heading, List.copyOf(lines));
  }

  static String flag(String name, boolean value) {
    return name + "=" + value;
  }
}
