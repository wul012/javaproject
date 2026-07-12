package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int ledgerEntryCount,
    String algorithm,
    int digestLength,
    List<LedgerEntry> entries,
    List<String> checks,
    String status) {

  public record LedgerEntry(
      String itemName, String sourceEndpoint, String evidencePath, String digest, String status) {}
}
