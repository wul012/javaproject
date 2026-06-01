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
class OpsShardReadinessV1ContractConsumerHandoffBundleIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractConsumerHandoffBundleReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v211"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.handoffBundleEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle"))
                .andExpect(jsonPath("$.endpointCatalogEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-endpoint-catalog"))
                .andExpect(jsonPath("$.catalogedArtifactCount").value(6))
                .andExpect(jsonPath("$.requiredEvidence[8]")
                        .value("e/210/evidence/java-shard-readiness-v208-endpoint-catalog-historical-compatibility-v210.json"))
                .andExpect(jsonPath("$.handoffEvidence[3]")
                        .value("e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractConsumerHandoffBundleFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v211"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.handoffBundleFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.fixture.json"))
                .andExpect(jsonPath("$.catalogedArtifactCount").value(6))
                .andExpect(jsonPath("$.endpointCatalogReceiptId")
                        .value("java-shard-readiness-v1-contract-endpoint-catalog-receipt-v208"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
