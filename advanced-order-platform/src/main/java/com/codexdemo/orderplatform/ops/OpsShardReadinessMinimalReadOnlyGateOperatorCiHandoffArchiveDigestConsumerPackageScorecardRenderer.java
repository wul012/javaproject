package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse
                            .ScorecardEntry> scorecard
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("scorecard-entry-count=" + scorecard.size());
        scorecard.forEach(score -> lines.add(score.name()
                + "="
                + score.actual()
                + "/"
                + score.expected()
                + " | status="
                + score.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport
                .section("Scorecard", lines);
    }
}
