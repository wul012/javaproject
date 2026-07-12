package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String postCompletionCloseoutEndpoint,
    String postCompletionCloseoutProfile,
    int firstVersion,
    int latestVersion,
    int versionCount,
    String completionAuditDigestEndpoint,
    String maintenanceBoundaryReportEndpoint,
    int closeoutEvidenceCount,
    List<String> closeoutEvidence,
    String decision,
    String status) {}
