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
class OpsShardReadinessLiveReadGatePlanIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessLiveReadGatePlanReturnsServiceLifecyclePlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/live-read-gate-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v159"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.liveReadGateAllowed").value(false))
                .andExpect(jsonPath("$.serviceStartAllowedByNode").value(false))
                .andExpect(jsonPath("$.serviceStopAllowedByNode").value(false))
                .andExpect(jsonPath("$.failClosedRequired").value(true))
                .andExpect(jsonPath("$.sourceBoundaryHandoffVersion").value("Java v158"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v383"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v384"))
                .andExpect(jsonPath("$.requiredServiceOwnershipFields.length()").value(6))
                .andExpect(jsonPath("$.javaServiceLifecyclePlan[0]")
                        .value("node-may-not-start-java-from-this-plan"))
                .andExpect(jsonPath("$.smokeTargets[1]")
                        .value("GET /api/v1/ops/shard-readiness/live-read-gate-plan"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/159/evidence/java-shard-readiness-live-read-gate-plan-v159.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessLiveReadGatePlanFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v159"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.serviceStartAllowedByNode").value(false))
                .andExpect(jsonPath("$.serviceStopAllowedByNode").value(false))
                .andExpect(jsonPath("$.sourceBoundaryHandoffVersion").value("Java v158"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v383"))
                .andExpect(jsonPath("$.failClosedRules[3]").value("failed-smoke-blocks-node-consumption"))
                .andExpect(jsonPath("$.cleanupResponsibilities[1]")
                        .value("node-must-not-stop-pre-existing-java-service"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
