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
class OpsShardReadinessV1ContractAlignmentIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractAlignmentReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-alignment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v187"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceReadinessVersion").value("Java v153"))
                .andExpect(jsonPath("$.minimalFields[0]").value("project"))
                .andExpect(jsonPath("$.minimalFields[9]").value("status"))
                .andExpect(jsonPath("$.executionBlocked").value(true))
                .andExpect(jsonPath("$.shardRoutingDisabled").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-alignment-receipt-v187"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractAlignmentFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v187"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.minimalFieldsFrozen").value(true))
                .andExpect(jsonPath("$.readOnlyMatches").value(true))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-alignment-receipt-v187"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
