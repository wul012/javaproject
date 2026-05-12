package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class FailedEventReplayEvidenceIndexIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void replayEvidenceIndexDescribesLiveEndpointsSamplesAndSafetyRules() throws Exception {
        mockMvc.perform(get("/api/v1/failed-events/replay-evidence-index"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.evidenceVersion").value("failed-event-replay-evidence-index.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.liveEvidenceEndpoints[0].name").value("failed-event-summary"))
                .andExpect(jsonPath("$.liveEvidenceEndpoints[0].readOnly").value(true))
                .andExpect(jsonPath("$.liveEvidenceEndpoints[0].changesReplayState").value(false))
                .andExpect(jsonPath("$.liveEvidenceEndpoints[3].name").value("replay-execution-contract"))
                .andExpect(jsonPath("$.liveEvidenceEndpoints[4].path")
                        .value("/api/v1/failed-events/{id}/replay-attempts"))
                .andExpect(jsonPath("$.staticEvidenceSamples[0].name").value("execution-contract-approved"))
                .andExpect(jsonPath("$.staticEvidenceSamples[2].name").value("replay-audit-approved"))
                .andExpect(jsonPath("$.staticEvidenceSamples[2].requiredFields", hasItem("auditTrail")))
                .andExpect(jsonPath("$.staticEvidenceSamples[3].scenario").value("BLOCKED_REPLAY_AUDIT"))
                .andExpect(jsonPath("$.auditIdentityFields", hasItem("operator.operatorId")))
                .andExpect(jsonPath("$.auditIdentityFields", hasItem("decisionId")))
                .andExpect(jsonPath("$.executionSafetyRules", hasItem("REAL_REPLAY_REQUIRES_APPROVED_STATUS")))
                .andExpect(jsonPath("$.executionSafetyRules", hasItem("BLOCKED_PRECHECK_MUST_NOT_CREATE_REPLAY_ATTEMPT")))
                .andExpect(jsonPath("$.productionReadinessNotes[0]")
                        .value("This index is read-only and does not execute replay."));
    }
}
