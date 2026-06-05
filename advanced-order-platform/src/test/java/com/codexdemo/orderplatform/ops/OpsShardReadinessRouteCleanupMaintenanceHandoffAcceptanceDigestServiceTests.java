package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestServiceTests {

    @Test
    void buildsAcceptanceDigestFromSustainmentEvidence() {
        OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse digest =
                new OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService(
                        new OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService(),
                        new OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService(),
                        new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService()
                ).digest();

        assertThat(digest.version()).isEqualTo("Java v522");
        assertThat(digest.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-handoff-acceptance-digest");
        assertThat(digest.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-handoff-acceptance-digest.v1");
        assertThat(digest.sectionCount()).isEqualTo(5);
        assertThat(digest.acceptedSectionCount()).isEqualTo(5);
        assertThat(digest.blockedSectionCount()).isZero();
        assertThat(digest.sections())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse
                        .AcceptanceSection::name)
                .containsExactly(
                        "owner-coverage",
                        "risk-closure",
                        "evidence-freshness",
                        "runtime-boundary",
                        "handoff-readiness"
                );
        assertThat(digest.sections()).allSatisfy(section -> assertThat(section.status()).isEqualTo("passed"));
        assertThat(digest.checks()).contains("handoff-acceptance-digest-remains-read-only");
        assertThat(digest.status()).isEqualTo("passed");
    }
}
