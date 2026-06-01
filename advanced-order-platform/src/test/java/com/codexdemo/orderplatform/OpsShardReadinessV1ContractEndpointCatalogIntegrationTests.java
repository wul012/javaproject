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
class OpsShardReadinessV1ContractEndpointCatalogIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractEndpointCatalogReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v208"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpointCatalogEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog"))
                .andExpect(jsonPath("$.contractEndpointCount").value(6))
                .andExpect(jsonPath("$.endpoints[5].liveEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan"))
                .andExpect(jsonPath("$.fixtureProbeEndpoints[5]")
                        .value("GET /contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"))
                .andExpect(jsonPath("$.evidencePaths[5]")
                        .value("e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/208/evidence/java-shard-readiness-v1-contract-endpoint-catalog-v208.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractEndpointCatalogFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v208"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpointCatalogFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-endpoint-catalog-v208.fixture.json"))
                .andExpect(jsonPath("$.contractEndpointCount").value(6))
                .andExpect(jsonPath("$.endpoints[0].name").value("alignment"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
