package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupArchivePlanResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String archivePlanEndpoint,
    String archiveProfile,
    String sourceEndpoint,
    String targetEvidenceRoot,
    int artifactCount,
    List<ArchiveArtifact> artifacts,
    String status) {

  public record ArchiveArtifact(
      String name,
      String kind,
      String source,
      String targetPath,
      boolean required,
      String status) {}
}
