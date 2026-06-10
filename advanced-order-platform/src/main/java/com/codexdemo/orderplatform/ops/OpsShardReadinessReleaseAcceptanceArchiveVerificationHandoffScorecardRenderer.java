package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffScorecardRenderer {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffScorecardRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry> scorecard
    ) {
        List<String> lines = new ArrayList<>();
        lines.add("scorecard-entry-count=" + scorecard.size());
        scorecard.forEach(score -> lines.add(score.name()
                + "="
                + score.actual()
                + "/"
                + score.expected()
                + " | "
                + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .statusLine(score.status())));
        return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .section("Scorecard", lines);
    }
}
