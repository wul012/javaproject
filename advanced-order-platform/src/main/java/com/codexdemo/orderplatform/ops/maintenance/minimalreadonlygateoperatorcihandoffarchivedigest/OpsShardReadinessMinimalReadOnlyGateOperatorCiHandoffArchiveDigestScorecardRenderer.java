package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardRenderer {

  private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardRenderer() {}

  static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
          .MarkdownSection
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
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
    return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport
        .section("Scorecard", lines);
  }
}
