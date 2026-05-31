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
class OpsShardReadinessReadOnlyEvidenceCatalogHandoffIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessReadOnlyEvidenceCatalogHandoffReturnsVersionedReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v177"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceCatalogVersion").value("Java v175"))
                .andExpect(jsonPath("$.sourceCatalogReceiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-receipt-v175"))
                .andExpect(jsonPath("$.sourceCatalogLiveEndpointCount").value(20))
                .andExpect(jsonPath("$.sourceCatalogFixtureEndpointCount").value(20))
                .andExpect(jsonPath("$.sourceCatalogFrozen").value(true))
                .andExpect(jsonPath("$.readyForBatchNodeConsumption").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.writeRoutingAllowed").value(false))
                .andExpect(jsonPath("$.activeShardRouterAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-handoff-receipt-v177"))
                .andExpect(jsonPath("$.handoffArtifacts[3]")
                        .value("e/176/evidence/"
                                + "java-shard-readiness-read-only-evidence-catalog-snapshot-freeze-v176.json"))
                .andExpect(jsonPath("$.consumerRules[5]")
                        .value("node-must-fail-closed-if-catalog-status-is-not-passed"))
                .andExpect(jsonPath("$.blockedOperations[6]").value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/177/evidence/"
                                + "java-shard-readiness-read-only-evidence-catalog-handoff-v177.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessReadOnlyEvidenceCatalogHandoffFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v177"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceCatalogVersion").value("Java v175"))
                .andExpect(jsonPath("$.sourceCatalogFrozen").value(true))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-read-only-evidence-catalog-handoff-receipt-v177"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
