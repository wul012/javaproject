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
class OpsShardReadinessRuntimeExecutionApprovalInputValueValidationIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionApprovalInputValueValidationReturnsJavaReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v168"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.valueValidationReceiptPresent").value(true))
                .andExpect(jsonPath("$.valueValidationReceiptComplete").value(true))
                .andExpect(jsonPath("$.sourceCompatibilityIntakePresent").value(true))
                .andExpect(jsonPath("$.sourceCompatibilityIntakeComplete").value(true))
                .andExpect(jsonPath("$.nodeCanonicalApprovalInputValueValidationPresent").value(true))
                .andExpect(jsonPath("$.nodeCanonicalApprovalInputValueValidationAccepted").value(true))
                .andExpect(jsonPath("$.canonicalApprovalInputsPresent").value(true))
                .andExpect(jsonPath("$.canonicalApprovalInputsValueValid").value(true))
                .andExpect(jsonPath("$.sharedApprovalCorrelationIdValidated").value(true))
                .andExpect(jsonPath("$.runtimeExecutionPacketPresent").value(true))
                .andExpect(jsonPath("$.runtimeExecutionPacketExecutable").value(true))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(true))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(true))
                .andExpect(jsonPath("$.readyForRuntimeLiveReadGate").value(true))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.connectsManagedAudit").value(false))
                .andExpect(jsonPath("$.credentialValueRead").value(false))
                .andExpect(jsonPath("$.rawEndpointUrlParsed").value(false))
                .andExpect(jsonPath("$.writeOperationsAllowed").value(false))
                .andExpect(jsonPath("$.sourceCompatibilityIntakeVersion").value("Java v167"))
                .andExpect(jsonPath("$.sourceNodePrecheckVersion").value("Node v404"))
                .andExpect(jsonPath("$.nodeValueValidationVersion").value("Node v405"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v406"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-runtime-execution-approval-input-value-validation-receipt-v168"))
                .andExpect(jsonPath("$.presentTargetInputCount").value(3))
                .andExpect(jsonPath("$.validTargetInputCount").value(3))
                .andExpect(jsonPath("$.nodeProductionBlockerCount").value(0))
                .andExpect(jsonPath("$.canonicalInputPaths.length()").value(3))
                .andExpect(jsonPath("$.acceptedNodeValidationFields[5]")
                        .value("sharedApprovalCorrelationIdValidated:true"))
                .andExpect(jsonPath("$.allowedRuntimeSmokeCommands[0]")
                        .value("java:GET:http://127.0.0.1:8080/actuator/health"))
                .andExpect(jsonPath("$.failClosedRules[5]")
                        .value("node-v406-live-read-gate-required-before-any-approved-smoke"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/168/evidence/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionApprovalInputValueValidationFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v168"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.nodeValueValidationVersion").value("Node v405"))
                .andExpect(jsonPath("$.canonicalApprovalInputsPresent").value(true))
                .andExpect(jsonPath("$.presentTargetInputCount").value(3))
                .andExpect(jsonPath("$.validTargetInputCount").value(3))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
