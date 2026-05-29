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
class OpsShardReadinessEvidenceHandoffIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessEvidenceHandoffReturnsCompletedEvidenceSet() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/evidence-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v157"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceIndexVersion").value("Java v155"))
                .andExpect(jsonPath("$.sourceVerificationVersion").value("Java v156"))
                .andExpect(jsonPath("$.lastConsumedByNodeVersion").value("Node v378"))
                .andExpect(jsonPath("$.completedEvidenceVersions.length()").value(2))
                .andExpect(jsonPath("$.handoffArtifacts.length()").value(6))
                .andExpect(jsonPath("$.consumerRules[2]")
                        .value("do-not-read-rolling-current-files-for-historical-baselines"))
                .andExpect(jsonPath("$.stopConditions[3]")
                        .value("node-requests-live-read-without-explicit-service-plan"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/157/evidence/java-shard-readiness-evidence-handoff-v157.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessEvidenceHandoffFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v157"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceIndexVersion").value("Java v155"))
                .andExpect(jsonPath("$.sourceVerificationVersion").value("Java v156"))
                .andExpect(jsonPath("$.completedEvidenceVersions[1]").value("Java v156"))
                .andExpect(jsonPath("$.consumerRules[4]").value("do-not-start-or-stop-java-from-node-consumption"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
