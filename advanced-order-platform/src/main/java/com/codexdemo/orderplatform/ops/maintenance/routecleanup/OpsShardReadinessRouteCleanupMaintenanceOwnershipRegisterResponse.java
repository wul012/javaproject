package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int ownerEntryCount,
    int distinctOwnerCount,
    List<OwnerEntry> owners,
    List<String> checks,
    String status) {

  public record OwnerEntry(
      String itemName, String owner, String boundary, String sourceEndpoint, String status) {}
}
