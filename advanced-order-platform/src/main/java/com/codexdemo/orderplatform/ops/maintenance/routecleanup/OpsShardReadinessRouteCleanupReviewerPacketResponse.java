package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupReviewerPacketResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String reviewerPacketEndpoint,
    String reviewerPacketProfile,
    int sourceCount,
    List<String> sources,
    int reviewerCheckCount,
    List<ReviewerCheck> reviewerChecks,
    String summary,
    String status) {

  public record ReviewerCheck(String name, String expected, String evidence, String status) {}
}
