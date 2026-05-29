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
class OpsShardReadinessEvidenceVerificationIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessEvidenceVerificationReturnsPassedChecks() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/evidence-verification"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v156"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceIndexVersion").value("Java v155"))
                .andExpect(jsonPath("$.verifiedEntryCount").value(2))
                .andExpect(jsonPath("$.verifiedEvidenceVersions[0]").value("Java v153"))
                .andExpect(jsonPath("$.verifiedEvidenceVersions[1]").value("Java v154"))
                .andExpect(jsonPath("$.checks.length()").value(8))
                .andExpect(jsonPath("$.checks[4].checkId").value("no-rolling-current-pointer"))
                .andExpect(jsonPath("$.checks[4].passed").value(true))
                .andExpect(jsonPath("$.fallbackPolicy[2]")
                        .value("do-not-read-rolling-current-files-for-historical-baselines"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/156/evidence/java-shard-readiness-evidence-verification-v156.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessEvidenceVerificationFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-evidence-verification-v156.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v156"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceIndexVersion").value("Java v155"))
                .andExpect(jsonPath("$.verifiedEntryCount").value(2))
                .andExpect(jsonPath("$.checks.length()").value(8))
                .andExpect(jsonPath("$.checks[7].checkId").value("node-archive-mutation-forbidden"))
                .andExpect(jsonPath("$.checks[7].passed").value(true))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
