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
class OpsShardReadinessPrototypeReadOnlyIntegrationBridgeIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeReadOnlyIntegrationBridgeReturnsGateEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-read-only-integration-bridge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("Java v415"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.entryKey").value("prototype-read-only-integration-bridge"))
                .andExpect(jsonPath("$.nodePlanVersion").value("Node v368"))
                .andExpect(jsonPath("$.checks[0]").value("bridge-node-v367-read-targets-passed"))
                .andExpect(jsonPath("$.checks[4]").value("bridge-upstream-actions-disabled"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
