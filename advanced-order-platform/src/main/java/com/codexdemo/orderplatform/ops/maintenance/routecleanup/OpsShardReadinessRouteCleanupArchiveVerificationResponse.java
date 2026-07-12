package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupArchiveVerificationResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String verificationEndpoint,
    String verificationProfile,
    String archivePlanEndpoint,
    String suiteCloseoutEndpoint,
    int checkCount,
    List<VerificationCheck> checks,
    String status) {

  public record VerificationCheck(String name, boolean passed, String evidence, String status) {}
}
