package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupPostPushCloseoutResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String postPushCloseoutEndpoint,
    String postPushCloseoutProfile,
    String completionCertificateEndpoint,
    String ciEvidenceEndpoint,
    int closeoutSignalCount,
    List<CloseoutSignal> closeoutSignals,
    String decision,
    String status) {

  public record CloseoutSignal(String name, String evidence, String status) {}
}
