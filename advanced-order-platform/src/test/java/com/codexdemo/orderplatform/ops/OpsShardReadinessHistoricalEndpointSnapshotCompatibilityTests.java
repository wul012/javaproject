package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests {

    @Test
    void rollingRegistryKeepsHistoricalLiveSnapshotsReachable() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .hasSizeGreaterThanOrEqualTo(23)
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints());
    }

    @Test
    void rollingRegistryKeepsHistoricalFixtureSnapshotsReachable() {
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .hasSizeGreaterThanOrEqualTo(23)
                .containsAll(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .containsAll(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints());
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
}
