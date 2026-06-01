package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEndpointRegistryIntegrityServiceTests {

    @Test
    void verifiesCurrentEndpointPairRegistryWithoutOpeningExecution() {
        OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse integrity =
                new OpsShardReadinessReadOnlyEndpointRegistryIntegrityService().integrity();

        assertThat(integrity.project()).isEqualTo("advanced-order-platform");
        assertThat(integrity.version()).isEqualTo("Java v184");
        assertThat(integrity.readOnly()).isTrue();
        assertThat(integrity.executionAllowed()).isFalse();
        assertThat(integrity.shardEnabled()).isFalse();
        assertThat(integrity.pairCount()).isEqualTo(23);
        assertThat(integrity.liveEndpointCount()).isEqualTo(23);
        assertThat(integrity.fixtureEndpointCount()).isEqualTo(23);
        assertThat(integrity.pairCountsAligned()).isTrue();
        assertThat(integrity.liveEndpointsDistinct()).isTrue();
        assertThat(integrity.fixtureEndpointsDistinct()).isTrue();
        assertThat(integrity.pairsHaveLiveAndFixture()).isTrue();
        assertThat(integrity.endpointRegistryIncludesIntegrity()).isTrue();
        assertThat(integrity.fixtureRegistryIncludesIntegrity()).isTrue();
        assertThat(integrity.writeRoutingAllowed()).isFalse();
        assertThat(integrity.activeShardRouterAllowed()).isFalse();
        assertThat(integrity.credentialValueRead()).isFalse();
        assertThat(integrity.rawEndpointParsed()).isFalse();
        assertThat(integrity.managedAuditConnectionAllowed()).isFalse();
        assertThat(integrity.deploymentOrRollbackAllowed()).isFalse();
        assertThat(integrity.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
        assertThat(integrity.receiptId())
                .isEqualTo("java-shard-readiness-read-only-endpoint-registry-integrity-receipt-v184");
        assertThat(integrity.verificationChecks())
                .contains(
                        "endpoint-pairs-count:23",
                        "live-endpoints-count:23",
                        "fixture-endpoints-count:23",
                        "pair-counts-aligned:true",
                        "endpoint-registry-includes-integrity:true"
                );
        assertThat(integrity.blockedOperations())
                .contains(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(integrity.evidencePath())
                .isEqualTo("e/184/evidence/"
                        + "java-shard-readiness-read-only-endpoint-registry-integrity-v184.json");
        assertThat(integrity.status()).isEqualTo("passed");
    }
}
