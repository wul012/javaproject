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
class OpsShardReadinessV1ContractOperatorChecklistIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractOperatorChecklistReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-operator-checklist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v196"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.checklistEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-operator-checklist"))
                .andExpect(jsonPath("$.packetEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-evidence-packet"))
                .andExpect(jsonPath("$.requiredReadOnlyEvidence[3]")
                        .value("e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json"))
                .andExpect(jsonPath("$.operatorChecklistItems[6]")
                        .value("confirm-no-java-or-mini-kv-process-control-is-delegated-to-node"))
                .andExpect(jsonPath("$.packetFrozen").value(true))
                .andExpect(jsonPath("$.historicalSnapshotsProtected").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-operator-checklist-receipt-v196"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/196/evidence/java-shard-readiness-v1-contract-operator-checklist-v196.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractOperatorChecklistFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v196"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.checklistFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json"))
                .andExpect(jsonPath("$.verificationChecks[3]").value("required-read-only-evidence-count:4"))
                .andExpect(jsonPath("$.packetFrozen").value(true))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-operator-checklist-receipt-v196"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
