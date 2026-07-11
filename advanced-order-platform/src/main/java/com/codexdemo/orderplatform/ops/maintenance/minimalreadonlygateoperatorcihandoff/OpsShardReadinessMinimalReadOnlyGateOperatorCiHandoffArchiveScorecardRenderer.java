package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                      .ScorecardEntry>
              scorecard) {
    List<String> lines = new ArrayList<>();
    lines.add("scorecard-entry-count=" + scorecard.size());
    scorecard.forEach(
        score ->
            lines.add(
                score.name()
                    + "="
                    + score.actual()
                    + "/"
                    + score.expected()
                    + " | status="
                    + score.status()));
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.section(
        "Scorecard", lines);
  }
}
