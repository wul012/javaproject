package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionRenderer {

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                      .ReplayInstruction>
              instructions) {
    List<String> lines = new ArrayList<>();
    lines.add("replay-instruction-count=" + instructions.size());
    instructions.forEach(
        instruction ->
            lines.add(
                instruction.order()
                    + ". "
                    + instruction.batch()
                    + " | "
                    + instruction.commandFamily()
                    + " | read-only="
                    + instruction.readOnly()
                    + " | status="
                    + instruction.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport
        .section("Replay Instructions", lines);
  }
}
