package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerServiceTests {

  @Test
  void buildsStableArchiveDigestLedgerFromUpkeepCatalogFields() {
    OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse ledger =
        new OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService().ledger();

    assertThat(ledger.version()).isEqualTo("Java v499");
    assertThat(ledger.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-digest-ledger");
    assertThat(ledger.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-archive-digest-ledger.v1");
    assertThat(ledger.ledgerEntryCount()).isEqualTo(9);
    assertThat(ledger.algorithm()).isEqualTo("SHA-256");
    assertThat(ledger.digestLength()).isEqualTo(16);
    assertThat(ledger.entries().getFirst().itemName()).isEqualTo("segment-catalog");
    assertThat(ledger.entries().getFirst().digest()).hasSize(16);
    assertThat(ledger.entries())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse.LedgerEntry::digest)
        .doesNotHaveDuplicates();
    assertThat(ledger.entries())
        .allSatisfy(
            entry -> {
              assertThat(entry.evidencePath()).endsWith(".json");
              assertThat(entry.status()).isEqualTo("passed");
            });
    assertThat(ledger.checks()).contains("ledger-does-not-read-archive-files");
    assertThat(ledger.status()).isEqualTo("passed");
  }
}
