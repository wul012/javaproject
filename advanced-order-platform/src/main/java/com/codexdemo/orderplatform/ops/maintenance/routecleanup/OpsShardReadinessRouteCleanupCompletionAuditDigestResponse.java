package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupCompletionAuditDigestResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String completionAuditDigestEndpoint,
    String completionAuditDigestProfile,
    String digestAlgorithm,
    String digestValue,
    int sourceCount,
    List<String> sources,
    String status) {}
