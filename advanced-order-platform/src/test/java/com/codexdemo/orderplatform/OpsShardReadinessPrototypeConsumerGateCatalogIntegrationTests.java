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
class OpsShardReadinessPrototypeConsumerGateCatalogIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeConsumerGateCatalogReturnsReadOnlyHandoffInputs()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v449"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-catalog.v1"))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v447"))
                .andExpect(jsonPath("$.sourceHandoffEndpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-closeout"))
                .andExpect(jsonPath("$.sourceHandoffEntryCount").value(10))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.entryCount").value(1))
                .andExpect(jsonPath("$.entries[0].javaVersion").value(449))
                .andExpect(jsonPath("$.entries[0].key").value("consumer-gate-catalog"))
                .andExpect(jsonPath("$.entries[0].nodePlanVersion").value("Node v370"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
