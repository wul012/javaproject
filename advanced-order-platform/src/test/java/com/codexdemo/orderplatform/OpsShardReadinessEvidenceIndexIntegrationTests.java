package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsShardReadinessEvidenceIndexIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessEvidenceIndexReturnsFrozenSources() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/evidence-index"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v155"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.lastConsumedByNodeVersion").value("Node v376"))
                .andExpect(jsonPath("$.requiredContractFields.length()").value(9))
                .andExpect(jsonPath("$.requiredContractFields[4]").value("shardEnabled"))
                .andExpect(jsonPath("$.evidenceEntries.length()").value(2))
                .andExpect(jsonPath("$.evidenceEntries[0].evidenceVersion").value("Java v153"))
                .andExpect(jsonPath("$.evidenceEntries[0].rollingCurrentPointer").value(false))
                .andExpect(jsonPath("$.evidenceEntries[1].evidenceVersion").value("Java v154"))
                .andExpect(jsonPath("$.fallbackPolicy[2]")
                        .value("do-not-read-rolling-current-files-for-historical-baselines"))
                .andExpect(jsonPath("$.compatibilityGuarantees[2]")
                        .value("v155-index-does-not-enable-sharding"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/155/evidence/java-shard-readiness-evidence-index-v155.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessEvidenceIndexFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-evidence-index-v155.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v155"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.lastConsumedByNodeVersion").value("Node v376"))
                .andExpect(jsonPath("$.requiredContractFields.length()").value(9))
                .andExpect(jsonPath("$.evidenceEntries.length()").value(2))
                .andExpect(jsonPath("$.evidenceEntries[0].fixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v153.fixture.json"))
                .andExpect(jsonPath("$.evidenceEntries[1].fixtureEndpoint")
                        .value("/contracts/java-shard-readiness-hardening-v154.fixture.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
