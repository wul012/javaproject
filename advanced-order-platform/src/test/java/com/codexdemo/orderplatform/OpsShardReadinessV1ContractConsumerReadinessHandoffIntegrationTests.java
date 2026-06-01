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
class OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractConsumerReadinessHandoffReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-consumer-readiness-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v225"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readinessHandoffEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-readiness-handoff"))
                .andExpect(jsonPath("$.evidenceDigestEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-evidence-digest"))
                .andExpect(jsonPath("$.digestEvidenceCount").value(5))
                .andExpect(jsonPath("$.digestCheckCount").value(7))
                .andExpect(jsonPath("$.handoffGuardEvidence[3]")
                        .value("e/224/evidence/java-shard-readiness-v1-contract-consumer-readiness-completion-v224.json"))
                .andExpect(jsonPath("$.handoffChecks[3]").value("handoff-guard-evidence-count:4"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-readiness-handoff-receipt-v225"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractConsumerReadinessHandoffFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v225"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readinessHandoffFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json"))
                .andExpect(jsonPath("$.evidenceDigestReceiptId")
                        .value("java-shard-readiness-v1-contract-consumer-evidence-digest-receipt-v220"))
                .andExpect(jsonPath("$.digestEvidenceCount").value(5))
                .andExpect(jsonPath("$.handoffChecks[0]").value("digest-version:Java v220"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-readiness-handoff-receipt-v225"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
