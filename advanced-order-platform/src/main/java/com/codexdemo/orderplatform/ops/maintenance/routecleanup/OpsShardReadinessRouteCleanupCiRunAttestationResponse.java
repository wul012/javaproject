package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupCiRunAttestationResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String ciRunAttestationEndpoint,
    String ciRunAttestationProfile,
    String postPushCloseoutEndpoint,
    int attestationItemCount,
    List<AttestationItem> attestationItems,
    String requirement,
    String status) {

  public record AttestationItem(String name, String evidence, boolean required, String status) {}
}
