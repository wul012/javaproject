package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEndpointPairsTests {

    @Test
    void groupsV1ContractEndpointPairsInContractOrder() {
        assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(8);
        assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
                .containsExactly(
                        "/api/v1/ops/shard-readiness/v1-contract-alignment",
                        "/api/v1/ops/shard-readiness/v1-contract-alignment-handoff",
                        "/api/v1/ops/shard-readiness/v1-contract-evidence-packet",
                        "/api/v1/ops/shard-readiness/v1-contract-operator-checklist",
                        "/api/v1/ops/shard-readiness/v1-contract-handoff-manifest",
                        "/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan",
                        "/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog",
                        "/api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle"
                );
        assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
                .containsExactly(
                        "/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json"
                );
    }
}
