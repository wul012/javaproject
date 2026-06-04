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
class OpsShardReadinessPrototypeHandoffCatalogIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeHandoffCatalogReturnsReadOnlyConsumerInputs()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-handoff-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v429"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-handoff-catalog.v1"))
                .andExpect(jsonPath("$.sourcePrototypeVersion").value("Java v427"))
                .andExpect(jsonPath("$.sourcePrototypeEndpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-closeout"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.entryCount").value(1))
                .andExpect(jsonPath("$.entries[0].javaVersion").value(429))
                .andExpect(jsonPath("$.entries[0].key").value("handoff-catalog"))
                .andExpect(jsonPath("$.entries[0].nodePlanVersion").value("Node v368"))
                .andExpect(jsonPath("$.entries[0].endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-catalog"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
