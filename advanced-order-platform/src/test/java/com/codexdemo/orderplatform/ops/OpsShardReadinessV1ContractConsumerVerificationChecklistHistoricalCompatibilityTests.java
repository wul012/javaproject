package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerVerificationChecklistHistoricalCompatibilityTests {

    @Test
    void keepsV215ChecklistOutOfOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT);
    }

    @Test
    void keepsV215ChecklistEvidenceIndependentFromFutureFreezeReceipts() {
        OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();

        assertThat(checklist.requiredEvidence())
                .contains(
                        OpsShardReadinessV1ContractEndpointCatalogService.EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .HANDOFF_BUNDLE_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .HANDOFF_BUNDLE_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .HANDOFF_BUNDLE_INTEGRITY_EVIDENCE_PATH
                )
                .doesNotContain(
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .CONSUMER_VERIFICATION_CHECKLIST_SNAPSHOT_FREEZE_EVIDENCE_PATH,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService
                                .CONSUMER_VERIFICATION_CHECKLIST_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH
                );
        assertThat(checklist.evidencePath())
                .isEqualTo(OpsShardReadinessV1ContractConsumerVerificationChecklistService.EVIDENCE_PATH);
    }

    @Test
    void currentRollingRegistryIncludesV215ChecklistAfterBundleBeforeReadOnlyCatalog() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsSubsequence(
                        OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
                        OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT
                );
    }
}
