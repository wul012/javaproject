package com.codexdemo.orderplatform;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
class FailedEventReplayAuditEvidenceSampleTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void approvedReplayAuditSampleExposesExecutionAuditEvidence() throws Exception {
        mockMvc.perform(get("/contracts/failed-event-replay-audit-approved.sample.json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.auditEvidenceVersion").value("failed-event-replay-audit-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("APPROVED_REPLAY_AUDIT"))
                .andExpect(jsonPath("$.requestId").value("req-replay-audit-20260512-0001"))
                .andExpect(jsonPath("$.decisionId").value("decision-replay-audit-approved-20260512-0001"))
                .andExpect(jsonPath("$.dryRun").value(false))
                .andExpect(jsonPath("$.executionAllowed").value(true))
                .andExpect(jsonPath("$.operator.operatorId").value("sre-user"))
                .andExpect(jsonPath("$.operator.operatorRole").value("SRE"))
                .andExpect(jsonPath("$.approval.approvalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.execution.attemptAuditType").value("FAILED_EVENT_REPLAY_ATTEMPT"))
                .andExpect(jsonPath("$.execution.attemptStatus").value("SUCCEEDED"))
                .andExpect(jsonPath("$.execution.expectedSideEffects[0]").value("PUBLISH_RABBITMQ_REPLAY_MESSAGE"))
                .andExpect(jsonPath("$.execution.expectedSideEffects[1]").value("SAVE_REPLAY_ATTEMPT_AUDIT"))
                .andExpect(jsonPath("$.auditTrail[2].auditType").value("FAILED_EVENT_REPLAY_ATTEMPT"))
                .andExpect(jsonPath("$.auditTrail[2].result").value("SUCCEEDED"))
                .andExpect(jsonPath("$.blockedBy").isEmpty());
    }

    @Test
    void blockedReplayAuditSampleExposesPrecheckAuditEvidence() throws Exception {
        mockMvc.perform(get("/contracts/failed-event-replay-audit-blocked.sample.json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.auditEvidenceVersion").value("failed-event-replay-audit-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("BLOCKED_REPLAY_AUDIT"))
                .andExpect(jsonPath("$.requestId").value("req-replay-audit-20260512-0002"))
                .andExpect(jsonPath("$.decisionId").value("decision-replay-audit-blocked-20260512-0002"))
                .andExpect(jsonPath("$.dryRun").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.operator.operatorId").value("ops-user"))
                .andExpect(jsonPath("$.operator.operatorRole").value("ORDER_SUPPORT"))
                .andExpect(jsonPath("$.approval.approvalStatus").value("PENDING"))
                .andExpect(jsonPath("$.execution.attemptStatus").value("NOT_ATTEMPTED"))
                .andExpect(jsonPath("$.execution.expectedSideEffects").isEmpty())
                .andExpect(jsonPath("$.auditTrail[1].auditType").value("READ_ONLY_EVIDENCE"))
                .andExpect(jsonPath("$.auditTrail[1].result").value("BLOCKED"))
                .andExpect(jsonPath("$.blockedBy[0]").value("REPLAY_APPROVAL_NOT_APPROVED"))
                .andExpect(jsonPath("$.warnings[1]").value("blocked precheck does not create FailedEventReplayAttempt"));
    }
}
