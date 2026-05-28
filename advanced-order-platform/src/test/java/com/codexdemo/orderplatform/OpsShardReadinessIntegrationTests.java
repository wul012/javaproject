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
class OpsShardReadinessIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessReturnsFrozenContractFields() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v153"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.shardEnabled").value(false))
                .andExpect(jsonPath("$.shardCount").value(0))
                .andExpect(jsonPath("$.slotCount").value(0))
                .andExpect(jsonPath("$.routingMode").value("fixture"))
                .andExpect(jsonPath("$.evidencePath").value("e/153/evidence/java-shard-readiness-v153.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v153.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v153"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.routingMode").value("fixture"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
