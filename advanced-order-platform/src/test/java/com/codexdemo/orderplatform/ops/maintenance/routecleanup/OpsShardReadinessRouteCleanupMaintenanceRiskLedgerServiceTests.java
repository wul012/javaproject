package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceRiskLedgerServiceTests {

  @Test
  void buildsMitigatedRiskLedgerForMaintenanceSustainment() {
    OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse ledger =
        new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService().ledger();

    assertThat(ledger.version()).isEqualTo("Java v520");
    assertThat(ledger.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-risk-ledger");
    assertThat(ledger.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-risk-ledger.v1");
    assertThat(ledger.riskCount()).isEqualTo(5);
    assertThat(ledger.highRiskCount()).isZero();
    assertThat(ledger.mitigatedRiskCount()).isEqualTo(5);
    assertThat(ledger.risks())
        .extracting(OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse.RiskEntry::name)
        .containsExactly(
            "route-drift",
            "evidence-staleness",
            "boundary-drift",
            "handoff-owner-gap",
            "ci-regression-gap");
    assertThat(ledger.risks()).allSatisfy(risk -> assertThat(risk.status()).isEqualTo("mitigated"));
    assertThat(ledger.checks()).contains("risk-ledger-remains-read-only");
    assertThat(ledger.status()).isEqualTo("passed");
  }
}
