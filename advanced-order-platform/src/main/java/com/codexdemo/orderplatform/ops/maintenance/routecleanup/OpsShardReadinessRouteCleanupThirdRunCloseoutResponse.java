package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupThirdRunCloseoutResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String thirdRunCloseoutEndpoint,
    String thirdRunCloseoutProfile,
    int firstVersion,
    int latestVersion,
    int versionCount,
    String finalVerificationEndpoint,
    String finalArchivePlanEndpoint,
    int readinessEvidenceCount,
    List<String> readinessEvidence,
    String decision,
    String status) {}
