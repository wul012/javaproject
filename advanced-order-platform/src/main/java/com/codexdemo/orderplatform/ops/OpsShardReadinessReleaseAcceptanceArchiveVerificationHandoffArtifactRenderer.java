package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactRenderer {

    private OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffArtifactRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck> artifacts
    ) {
        List<String> lines = new ArrayList<>();
        lines.add("artifact-cross-check-count=" + artifacts.size());
        artifacts.forEach(artifact -> lines.add(artifact.name()
                + "="
                + artifact.sourceValue()
                + " | "
                + artifact.expectedEvidence()
                + " | "
                + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .flag("matched", artifact.matched())
                + " | "
                + OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .statusLine(artifact.status())));
        return OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRendererSupport
                .section("Artifact Cross Checks", lines);
    }
}
