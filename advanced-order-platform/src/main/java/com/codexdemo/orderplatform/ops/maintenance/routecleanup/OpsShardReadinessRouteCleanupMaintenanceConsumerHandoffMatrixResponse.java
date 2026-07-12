package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int matrixEntryCount,
    int consumerCount,
    int forbiddenOperationCount,
    List<MatrixEntry> matrix,
    List<String> forbiddenOperations,
    List<String> checks,
    String status) {

  public record MatrixEntry(
      String itemName,
      String consumer,
      String boundary,
      String sourceEndpoint,
      String requiredAction,
      String handoffStatus) {}
}
