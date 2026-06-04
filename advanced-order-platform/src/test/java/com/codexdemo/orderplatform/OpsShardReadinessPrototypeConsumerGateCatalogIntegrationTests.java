package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.matchesPattern;

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
                .andExpect(jsonPath("$.version").value(matchesPattern("Java v\\d+")))
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
                .andExpect(jsonPath("$.entryCount").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.entries[0].javaVersion").value(449))
                .andExpect(jsonPath("$.entries[0].key").value("consumer-gate-catalog"))
                .andExpect(jsonPath("$.entries[0].nodePlanVersion").value("Node v370"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeConsumerGateSourceInventoryReturnsSourceEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-consumer-gate-source-inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v451"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-consumer-gate-source-inventory"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-consumer-gate-source-inventory.v1"))
                .andExpect(jsonPath("$.entryKey").value("consumer-gate-source-inventory"))
                .andExpect(jsonPath("$.nodePlanVersion").value("Node v370"))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v447"))
                .andExpect(jsonPath("$.sourceHandoffEndpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-closeout"))
                .andExpect(jsonPath("$.evidenceRefs.length()").value(3))
                .andExpect(jsonPath("$.checks[3]").value("verify-source-entry-count-10"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
