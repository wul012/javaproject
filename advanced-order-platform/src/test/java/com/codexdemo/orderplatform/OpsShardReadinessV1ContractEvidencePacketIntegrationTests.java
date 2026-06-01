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
class OpsShardReadinessV1ContractEvidencePacketIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractEvidencePacketReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-evidence-packet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v193"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.packetEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-evidence-packet"))
                .andExpect(jsonPath("$.nodeConsumableEndpoints[3]")
                        .value("/api/v1/ops/shard-readiness/v1-contract-evidence-packet"))
                .andExpect(jsonPath("$.evidenceChain[5]")
                        .value("e/192/evidence/java-shard-readiness-v190-handoff-historical-snapshot-compatibility-v192.json"))
                .andExpect(jsonPath("$.historicalSnapshotsProtected").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-evidence-packet-receipt-v193"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractEvidencePacketFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v193"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.nodeConsumableFixtureEndpoints[3]")
                        .value("/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-evidence-packet-receipt-v193"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
