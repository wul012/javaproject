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
class OpsShardReadinessReadOnlyEvidenceCatalogIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessReadOnlyEvidenceCatalogReturnsVersionedCatalog()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/read-only-evidence-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v175"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.shardEnabled").value(false))
                .andExpect(jsonPath("$.sourceEchoVersion").value("Java v174"))
                .andExpect(jsonPath("$.sourceRuntimePassEvidenceCloseoutVersion").value("Java v170"))
                .andExpect(jsonPath("$.sourceEchoReceiptId").value("java-shard-readiness-echo-receipt-v174"))
                .andExpect(jsonPath("$.sourceRuntimePassEvidenceCloseoutReceiptId")
                        .value("java-runtime-execution-pass-evidence-closeout-receipt-v170"))
                .andExpect(jsonPath("$.schemaCompatibilityMode")
                        .value("append-only-read-only-evidence-catalog-preserves-v153-root-schema"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-receipt-v175"))
                .andExpect(jsonPath("$.liveEndpointCount").value(20))
                .andExpect(jsonPath("$.fixtureEndpointCount").value(20))
                .andExpect(jsonPath("$.liveEndpoints[3]")
                        .value("/api/v1/ops/shard-readiness/read-only-evidence-catalog"))
                .andExpect(jsonPath("$.fixtureEndpoints[3]")
                        .value("/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json"))
                .andExpect(jsonPath("$.consumerBatches[4]")
                        .value("java-v175:read-only-evidence-catalog-for-batch-node-consumption"))
                .andExpect(jsonPath("$.forbiddenOperations[6]").value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/175/evidence/java-shard-readiness-read-only-evidence-catalog-v175.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessReadOnlyEvidenceCatalogFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v175"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.liveEndpointCount").value(20))
                .andExpect(jsonPath("$.fixtureEndpointCount").value(20))
                .andExpect(jsonPath("$.sourceEchoVersion").value("Java v174"))
                .andExpect(jsonPath("$.sourceRuntimePassEvidenceCloseoutVersion").value("Java v170"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-receipt-v175"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
