package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureContractBoundaryTests {

    @Test
    void keepsReadinessHandoffFixtureFrozenToV225() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.readinessHandoffFixtureEndpoint())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT)
                .contains("v225")
                .doesNotContain("v255");
        assertThat(Files.exists(Path.of("src", "main", "resources", "static", "contracts",
                "java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json")))
                .isTrue();
    }

    @Test
    void keepsFixtureContractBoundaryEvidencePathVersionedToV255() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_FIXTURE_CONTRACT_BOUNDARY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/255/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "fixture-contract-boundary-v255.json"
                );
    }
}
