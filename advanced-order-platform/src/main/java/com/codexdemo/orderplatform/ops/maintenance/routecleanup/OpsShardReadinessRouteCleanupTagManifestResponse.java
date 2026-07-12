package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupTagManifestResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String tagManifestEndpoint,
    String tagManifestProfile,
    String ciRunAttestationEndpoint,
    int tagCount,
    List<TagEntry> tags,
    String policy,
    String status) {

  public record TagEntry(int javaVersion, String tagName, String evidenceType, String status) {}
}
