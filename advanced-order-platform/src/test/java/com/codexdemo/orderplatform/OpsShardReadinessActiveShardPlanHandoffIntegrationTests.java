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
class OpsShardReadinessActiveShardPlanHandoffIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessActiveShardPlanHandoffReturnsReadOnlyBoundary() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/active-shard-plan-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v158"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.activeShardPrototypeEnabled").value(false))
                .andExpect(jsonPath("$.liveReadAllowed").value(false))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v157"))
                .andExpect(jsonPath("$.lastConsumedByNodeVersion").value("Node v380"))
                .andExpect(jsonPath("$.nodeArchiveVerificationVersion").value("Node v381"))
                .andExpect(jsonPath("$.activePrototypeAuthority").value("mini-kv-active-prototype-plan"))
                .andExpect(jsonPath("$.frozenJavaEvidence.length()").value(4))
                .andExpect(jsonPath("$.javaBoundaryRules[2]")
                        .value("do-not-enable-java-shard-router-or-write-routing"))
                .andExpect(jsonPath("$.stopConditions[3]")
                        .value("node-requests-live-read-without-service-responsibility-plan"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/158/evidence/java-shard-readiness-active-shard-plan-handoff-v158.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessActiveShardPlanHandoffFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v158"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.activeShardPrototypeEnabled").value(false))
                .andExpect(jsonPath("$.liveReadAllowed").value(false))
                .andExpect(jsonPath("$.sourceHandoffVersion").value("Java v157"))
                .andExpect(jsonPath("$.lastConsumedByNodeVersion").value("Node v380"))
                .andExpect(jsonPath("$.nodeArchiveVerificationVersion").value("Node v381"))
                .andExpect(jsonPath("$.javaBoundaryRules[1]")
                        .value("active-shard-prototype-authority-stays-with-mini-kv-plan"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
