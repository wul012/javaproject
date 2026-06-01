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
class OpsShardReadinessV1ContractConsumerEvidenceDigestIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractConsumerEvidenceDigestReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-consumer-evidence-digest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v220"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.evidenceDigestEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-evidence-digest"))
                .andExpect(jsonPath("$.verificationChecklistEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist"))
                .andExpect(jsonPath("$.checklistItemCount").value(7))
                .andExpect(jsonPath("$.requiredEvidenceCount").value(5))
                .andExpect(jsonPath("$.verificationCheckCount").value(7))
                .andExpect(jsonPath("$.digestEvidence[4]")
                        .value("e/219/evidence/java-shard-readiness-v1-contract-consumer-route-inventory-v219.json"))
                .andExpect(jsonPath("$.digestChecks[4]").value("digest-evidence-count:5"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-evidence-digest-receipt-v220"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractConsumerEvidenceDigestFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v220"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.evidenceDigestFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.fixture.json"))
                .andExpect(jsonPath("$.verificationChecklistReceiptId")
                        .value("java-shard-readiness-v1-contract-consumer-verification-checklist-receipt-v215"))
                .andExpect(jsonPath("$.checklistItemCount").value(7))
                .andExpect(jsonPath("$.digestChecks[0]").value("checklist-version:Java v215"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-evidence-digest-receipt-v220"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
