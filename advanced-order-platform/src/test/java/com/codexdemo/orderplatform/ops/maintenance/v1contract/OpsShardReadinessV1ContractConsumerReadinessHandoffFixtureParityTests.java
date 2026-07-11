package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureParityTests {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void keepsStaticFixtureIdenticalToFrozenV225Handoff() throws IOException {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    JsonNode fixtureJson;
    try (InputStream inputStream =
        getClass()
            .getClassLoader()
            .getResourceAsStream(
                "static/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json")) {
      assertThat(inputStream).isNotNull();
      fixtureJson = objectMapper.readTree(inputStream);
    }

    assertThat(fixtureJson).isEqualTo(objectMapper.valueToTree(handoff));
  }

  @Test
  void keepsFixtureEndpointAndEvidencePathConsistentWithFrozenHandoff() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.readinessHandoffFixtureEndpoint())
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
    assertThat(handoff.evidencePath())
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.EVIDENCE_PATH);
  }
}
