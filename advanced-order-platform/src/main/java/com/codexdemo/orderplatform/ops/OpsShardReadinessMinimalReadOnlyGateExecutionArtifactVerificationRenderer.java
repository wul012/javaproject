package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationRenderer {

    private OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationRenderer() {
    }

    static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .MarkdownSection render(
                    List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                            .ArtifactVerification> artifacts
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("artifact-verification-count=" + artifacts.size());
        artifacts.forEach(artifact -> lines.add(String.join(
                " | ",
                artifact.artifact(),
                artifact.producer(),
                artifact.evidence(),
                OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport
                        .statusLine("status", artifact.status())
        )));
        return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.section(
                "Archive Artifacts",
                lines
        );
    }
}
