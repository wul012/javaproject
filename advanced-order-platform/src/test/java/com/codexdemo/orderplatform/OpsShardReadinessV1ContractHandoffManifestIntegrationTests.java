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
class OpsShardReadinessV1ContractHandoffManifestIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractHandoffManifestReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-handoff-manifest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v199"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.manifestEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-handoff-manifest"))
                .andExpect(jsonPath("$.consumerReadTargets[3]")
                        .value("/api/v1/ops/shard-readiness/v1-contract-handoff-manifest"))
                .andExpect(jsonPath("$.prerequisiteEvidence[6]")
                        .value("e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json"))
                .andExpect(jsonPath("$.checklistFrozen").value(true))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-handoff-manifest-receipt-v199"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/199/evidence/java-shard-readiness-v1-contract-handoff-manifest-v199.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractHandoffManifestFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v199"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.manifestFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json"))
                .andExpect(jsonPath("$.verificationChecks[3]").value("manifest-section-count:6"))
                .andExpect(jsonPath("$.checklistFrozen").value(true))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-handoff-manifest-receipt-v199"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
