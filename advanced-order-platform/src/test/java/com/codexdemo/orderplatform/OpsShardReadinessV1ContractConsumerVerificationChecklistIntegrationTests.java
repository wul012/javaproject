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
class OpsShardReadinessV1ContractConsumerVerificationChecklistIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractConsumerVerificationChecklistReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v215"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.verificationChecklistEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist"))
                .andExpect(jsonPath("$.handoffBundleEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-handoff-bundle"))
                .andExpect(jsonPath("$.catalogedArtifactCount").value(6))
                .andExpect(jsonPath("$.verificationItems[6]")
                        .value("archive-v215-checklist-receipt-before-any-node-consumption"))
                .andExpect(jsonPath("$.requiredEvidence[4]")
                        .value("e/214/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-integrity-v214.json"))
                .andExpect(jsonPath("$.verificationChecks[2]").value("required-evidence-count:9"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-verification-checklist-receipt-v215"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractConsumerVerificationChecklistFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v215"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.verificationChecklistFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json"))
                .andExpect(jsonPath("$.handoffBundleReceiptId")
                        .value("java-shard-readiness-v1-contract-consumer-handoff-bundle-receipt-v211"))
                .andExpect(jsonPath("$.catalogedArtifactCount").value(6))
                .andExpect(jsonPath("$.verificationChecks[6]")
                        .value("node-may-start-or-stop-java-or-mini-kv:false"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-verification-checklist-receipt-v215"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
