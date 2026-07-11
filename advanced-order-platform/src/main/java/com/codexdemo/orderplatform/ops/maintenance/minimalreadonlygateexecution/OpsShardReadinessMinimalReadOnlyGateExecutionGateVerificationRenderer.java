package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

final class OpsShardReadinessMinimalReadOnlyGateExecutionGateVerificationRenderer {

  private OpsShardReadinessMinimalReadOnlyGateExecutionGateVerificationRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .GateCheckVerification>
              gateChecks) {
    Map<String, List<String>> grouped = new LinkedHashMap<>();
    gateChecks.forEach(
        check ->
            grouped
                .computeIfAbsent(check.group(), ignored -> new ArrayList<>())
                .add(check.code() + "=" + check.status()));
    List<String> lines = new ArrayList<>();
    lines.add("gate-check-verification-count=" + gateChecks.size());
    grouped.forEach((group, checks) -> lines.add(group + ": " + String.join("; ", checks)));
    return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.section(
        "Gate Check Verification", lines);
  }
}
