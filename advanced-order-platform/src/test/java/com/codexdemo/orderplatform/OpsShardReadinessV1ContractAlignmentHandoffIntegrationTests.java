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
class OpsShardReadinessV1ContractAlignmentHandoffIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractAlignmentHandoffReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-alignment-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v190"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.alignmentVersion").value("Java v187"))
                .andExpect(jsonPath("$.snapshotFreezeVersion").value("Java v188"))
                .andExpect(jsonPath("$.historicalCompatibilityVersion").value("Java v189"))
                .andExpect(jsonPath("$.historicalSnapshotsProtected").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-alignment-handoff-receipt-v190"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractAlignmentHandoffFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v190"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.historicalSnapshotsProtected").value(true))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-alignment-handoff-receipt-v190"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
