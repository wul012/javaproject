package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupFinalDigestResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String digestEndpoint,
    String digestProfile,
    String digestAlgorithm,
    String digestInput,
    String digestValue,
    int sourceCount,
    List<String> sources,
    String status) {}
