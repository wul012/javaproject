package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffOperatorRenderer {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffOperatorRenderer() {}

  static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection
      render(
          List<
                  OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                      .OperatorInstruction>
              operators) {
    List<String> lines = new ArrayList<>();
    lines.add("operator-instruction-count=" + operators.size());
    operators.forEach(
        operator ->
            lines.add(
                operator.order()
                    + ". "
                    + operator.owner()
                    + " | "
                    + operator.sourceEvidence()
                    + " | "
                    + operator.instruction()
                    + " | "
                    + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .flag("ready", operator.ready())
                    + " | "
                    + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(operator.status())));
    return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport.section(
        "Operator Instructions", lines);
  }
}
