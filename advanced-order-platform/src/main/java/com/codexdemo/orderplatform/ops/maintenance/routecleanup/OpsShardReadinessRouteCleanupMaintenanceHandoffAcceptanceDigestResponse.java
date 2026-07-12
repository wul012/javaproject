package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int sectionCount,
    int acceptedSectionCount,
    int blockedSectionCount,
    List<AcceptanceSection> sections,
    List<String> checks,
    String status) {

  public record AcceptanceSection(
      String name, String sourceEndpoint, String owner, String evidence, String status) {}
}
