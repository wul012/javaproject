package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCloseoutRenderer {

  private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffCloseoutRenderer() {}

  static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection
      render(
          List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff>
              closeouts) {
    List<String> lines = new ArrayList<>();
    lines.add("closeout-handoff-count=" + closeouts.size());
    closeouts.forEach(
        closeout ->
            lines.add(
                closeout.order()
                    + ". "
                    + closeout.item()
                    + " | "
                    + closeout.owner()
                    + " | "
                    + closeout.evidence()
                    + " | "
                    + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .flag("ready", closeout.ready())
                    + " | "
                    + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                        .statusLine(closeout.status())));
    return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport.section(
        "Closeout Handoffs", lines);
  }
}
