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
                .andExpect(jsonPath("$.evidenceVersion").value("failed-event-replay-evidence-index.v2"))
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
                .andExpect(jsonPath("$.operatorAuthBoundary.identitySource")
                        .value("HEADER_DERIVED_OPERATOR_CONTEXT"))
                .andExpect(jsonPath("$.operatorAuthBoundary.requiredHeaders", hasItem("X-Operator-Id")))
                .andExpect(jsonPath("$.operatorAuthBoundary.requiredHeaders", hasItem("X-Operator-Role")))
                .andExpect(jsonPath("$.operatorAuthBoundary.anonymousAllowed").value(false))
                .andExpect(jsonPath("$.operatorAuthBoundary.javaAuthenticatesCredentials").value(false))
                .andExpect(jsonPath("$.operatorAuthBoundary.enforcementMode")
                        .value("ROLE_POLICY_PRECHECK_AND_SERVICE_GATE"))
                .andExpect(jsonPath("$.operatorAuthBoundary.globalAllowedRoles", hasItem("ORDER_SUPPORT")))
                .andExpect(jsonPath("$.operatorAuthBoundary.allowedRolesByAction.REVIEW_REPLAY_APPROVAL",
                        hasItem("SRE")))
                .andExpect(jsonPath("$.operatorAuthBoundary.allowedRolesByAction.REPLAY_FAILED_EVENT",
                        hasItem("SYSTEM")))
                .andExpect(jsonPath("$.operatorAuthBoundary.normalizationRules",
                        hasItem("operator role must be present, stripped, upper-cased, allow-listed, and truncated to 80 characters")))
                .andExpect(jsonPath("$.operatorAuthBoundary.productionAuthGaps",
                        hasItem("Java does not validate JWT, session cookies, or external identity-provider signatures yet.")))
                .andExpect(jsonPath("$.auditIdentityFields", hasItem("operator.operatorId")))
                .andExpect(jsonPath("$.auditIdentityFields", hasItem("decisionId")))
                .andExpect(jsonPath("$.executionSafetyRules", hasItem("REAL_REPLAY_REQUIRES_APPROVED_STATUS")))
                .andExpect(jsonPath("$.executionSafetyRules",
                        hasItem("OPERATOR_HEADERS_ARE_REQUIRED_BUT_NOT_CREDENTIAL_AUTHENTICATION")))
                .andExpect(jsonPath("$.executionSafetyRules", hasItem("BLOCKED_PRECHECK_MUST_NOT_CREATE_REPLAY_ATTEMPT")))
                .andExpect(jsonPath("$.productionReadinessNotes",
                        hasItem("Operator/auth boundary data explains Java's current header-derived identity rehearsal model.")));
    }
}
