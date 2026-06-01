package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests {

    @Test
    void rollingRegistryKeepsHistoricalLiveSnapshotsReachable() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .hasSizeGreaterThanOrEqualTo(24)
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .contains(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);
    }

    @Test
    void rollingRegistryKeepsHistoricalFixtureSnapshotsReachable() {
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .hasSizeGreaterThanOrEqualTo(24)
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .contains(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);
    }

    @Test
    void historicalSnapshotsLayerForwardWithoutMutatingOlderReceipts() {
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .contains(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .contains(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT);
    }

    @Test
    void v187ContractAlignmentDoesNotBackfillOlderEndpointSnapshots() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.ENDPOINT);

        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .doesNotContain(OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT);

        assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint())
                .isEqualTo(OpsShardReadinessService.ENDPOINT);
        assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187MinimalFields())
                .hasSize(10);
    }
}
