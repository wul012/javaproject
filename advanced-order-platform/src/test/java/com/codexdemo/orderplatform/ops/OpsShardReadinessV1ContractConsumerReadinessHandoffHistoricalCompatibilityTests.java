package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffHistoricalCompatibilityTests {

    @Test
    void keepsV225HandoffOutOfOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
    }

    @Test
    void keepsV225HandoffEvidenceIndependentFromFutureFreezeReceipts() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.handoffGuardEvidence())
                .contains(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService
                                .CONSUMER_EVIDENCE_DIGEST_READINESS_COMPLETION_EVIDENCE_PATH
                )
                .doesNotContain(
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                                .CONSUMER_READINESS_HANDOFF_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH
                );
        assertThat(handoff.evidencePath())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.EVIDENCE_PATH);
    }

    @Test
    void currentRollingRegistryIncludesV225HandoffAfterDigestBeforeReadOnlyCatalog() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT
                );
    }
}
