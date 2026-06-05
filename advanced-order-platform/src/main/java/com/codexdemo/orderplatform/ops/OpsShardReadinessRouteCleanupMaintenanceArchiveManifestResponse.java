package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int artifactCount,
        List<ArchiveArtifact> artifacts,
        List<String> checks,
        String status
) {

    public record ArchiveArtifact(
            String name,
            String sourceEndpoint,
            String evidencePath,
            String status
    ) {
    }
}
