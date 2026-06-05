package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditServiceTests {

    @Test
    void auditsHandoffSuiteServiceRoutePairsAndDocumentedRouteOnlyExceptions() {
        OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse audit =
                new OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService().audit();

        assertThat(audit.version()).isEqualTo("Java v477");
        assertThat(audit.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-handoff-pair-audit");
        assertThat(audit.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-handoff-pair-audit.v1");
        assertThat(audit.handoffEntryCount()).isEqualTo(83);
        assertThat(audit.serviceEntryCount()).isGreaterThan(30);
        assertThat(audit.routeEntryCount()).isGreaterThan(30);
        assertThat(audit.pairedRouteCount()).isGreaterThan(30);
        assertThat(audit.documentedRouteOnlyEntries())
                .containsExactly(
                        "handoff-suite-closeout-route",
                        "handoff-suite-completion-certificate-route"
                );
        assertThat(audit.unpairedServiceEntries()).isEmpty();
        assertThat(audit.checks()).contains("pair-audit-remains-read-only");
        assertThat(audit.status()).isEqualTo("passed");
    }
}
