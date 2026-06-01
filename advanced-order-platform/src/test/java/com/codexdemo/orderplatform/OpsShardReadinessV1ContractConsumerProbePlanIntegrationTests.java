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
class OpsShardReadinessV1ContractConsumerProbePlanIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessV1ContractConsumerProbePlanReturnsReadOnlyReceipt() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v202"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.probePlanEndpoint")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan"))
                .andExpect(jsonPath("$.readTargets[4]")
                        .value("/api/v1/ops/shard-readiness/v1-contract-consumer-probe-plan"))
                .andExpect(jsonPath("$.probeSequence[5]")
                        .value("GET /contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"))
                .andExpect(jsonPath("$.requiredEvidence[9]")
                        .value("e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.nodeMayStartOrStopJavaOrMiniKv").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-probe-plan-receipt-v202"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/202/evidence/java-shard-readiness-v1-contract-consumer-probe-plan-v202.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessV1ContractConsumerProbePlanFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v202"))
                .andExpect(jsonPath("$.contractName").value("shard-readiness.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.probePlanFixtureEndpoint")
                        .value("/contracts/java-shard-readiness-v1-contract-consumer-probe-plan-v202.fixture.json"))
                .andExpect(jsonPath("$.verificationChecks[2]").value("read-target-count:5"))
                .andExpect(jsonPath("$.probesAreGetOnly").value(true))
                .andExpect(jsonPath("$.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-shard-readiness-v1-contract-consumer-probe-plan-receipt-v202"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
