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
class OpsShardReadinessEchoIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessEchoReturnsReadOnlyVersionedReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/echo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v174"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.shardEnabled").value(false))
                .andExpect(jsonPath("$.sourceReadinessVersion").value("Java v153"))
                .andExpect(jsonPath("$.sourceHardeningVersion").value("Java v154"))
                .andExpect(jsonPath("$.sourceEvidenceIndexVersion").value("Java v155"))
                .andExpect(jsonPath("$.sourceEvidenceHandoffVersion").value("Java v157"))
                .andExpect(jsonPath("$.receiptId").value("java-shard-readiness-echo-receipt-v174"))
                .andExpect(jsonPath("$.controllerSplitReceipts[0]")
                        .value("Java v171:runtime-execution-controller-split"))
                .andExpect(jsonPath("$.forbiddenOperations[0]").value("write-routing"))
                .andExpect(jsonPath("$.evidencePath").value("e/174/evidence/java-shard-readiness-echo-v174.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessEchoFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-echo-v174.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v174"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.schemaCompatibilityMode")
                        .value("append-only-new-echo-endpoint-preserves-v153-root-schema"))
                .andExpect(jsonPath("$.receiptId").value("java-shard-readiness-echo-receipt-v174"))
                .andExpect(jsonPath("$.forbiddenOperations[6]").value("node-start-or-stop-java-or-mini-kv"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
