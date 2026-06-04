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
class OpsShardReadinessPrototypeHandoffCatalogIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessPrototypeHandoffCatalogReturnsReadOnlyConsumerInputs()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-handoff-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value(matchesPattern("Java v\\d+")))
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
                .andExpect(jsonPath("$.entryCount").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.entries[0].javaVersion").value(429))
                .andExpect(jsonPath("$.entries[0].key").value("handoff-catalog"))
                .andExpect(jsonPath("$.entries[0].nodePlanVersion").value("Node v368"))
                .andExpect(jsonPath("$.entries[0].endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-catalog"))
                .andExpect(jsonPath("$.forbiddenOperations[6]")
                        .value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void opsShardReadinessPrototypeHandoffEndpointInventoryReturnsRouteEvidence()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/prototype-handoff-endpoint-inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v431"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/prototype-handoff-endpoint-inventory"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-prototype-handoff-endpoint-inventory.v1"))
                .andExpect(jsonPath("$.entryKey").value("handoff-endpoint-inventory"))
                .andExpect(jsonPath("$.sourceCatalogVersion").value("Java v427"))
                .andExpect(jsonPath("$.sourceCloseoutVersion").value("Java v427"))
                .andExpect(jsonPath("$.evidenceRefs.length()").value(3))
                .andExpect(jsonPath("$.checks[2]").value("inventory-handoff-catalog-route-present"))
                .andExpect(jsonPath("$.digestValue").isString())
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
