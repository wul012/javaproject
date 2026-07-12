package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int sectionCount,
    int evidenceItemCount,
    int matrixEntryCount,
    int ciExpectationCount,
    int policyCount,
    int digestLedgerEntryCount,
    List<ReviewSection> sections,
    List<String> checks,
    String status) {

  public record ReviewSection(
      String name, String sourceProfile, String sourceEndpoint, int itemCount, String status) {}
}
