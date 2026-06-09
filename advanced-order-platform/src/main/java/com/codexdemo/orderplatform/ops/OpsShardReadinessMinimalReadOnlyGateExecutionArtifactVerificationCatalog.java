package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationCatalog {

    private OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
            .ArtifactVerification> artifactVerifications(
                    OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse sourceRegistry
    ) {
        return sourceRegistry.archiveRequirements().stream()
                .map(requirement -> new OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                        .ArtifactVerification(
                                requirement.artifact(),
                                requirement.producer(),
                                requirement.evidence(),
                                requirement.required(),
                                requirement.required() ? "passed" : "blocked"
                        ))
                .toList();
    }
}
