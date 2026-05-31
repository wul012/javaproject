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
class OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeReturnsJavaReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v167"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.compatibilityIntakeReceiptPresent").value(true))
                .andExpect(jsonPath("$.compatibilityIntakeReceiptComplete").value(true))
                .andExpect(jsonPath("$.nodeCompatibilityIntakePresent").value(true))
                .andExpect(jsonPath("$.nodeCompatibilityIntakeComplete").value(true))
                .andExpect(jsonPath("$.sourceTemplateCompatibilityReceiptPresent").value(true))
                .andExpect(jsonPath("$.sourceTemplateCompatibilityReceiptComplete").value(true))
                .andExpect(jsonPath("$.sourceJavaInputCanonical").value(true))
                .andExpect(jsonPath("$.nodeTemplateValidatorPresent").value(true))
                .andExpect(jsonPath("$.templatesAreApprovalInputs").value(false))
                .andExpect(jsonPath("$.canonicalApprovalInputsCreatedByJava").value(false))
                .andExpect(jsonPath("$.canonicalApprovalInputsCreatedByNodeV403").value(false))
                .andExpect(jsonPath("$.nodeApprovedRuntimeWindowPresent").value(false))
                .andExpect(jsonPath("$.correlatedOperatorApprovalRecordPresent").value(false))
                .andExpect(jsonPath("$.completeCrossProjectRuntimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.runtimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(false))
                .andExpect(jsonPath("$.readyForRuntimeLiveReadGate").value(false))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.connectsManagedAudit").value(false))
                .andExpect(jsonPath("$.credentialValueRead").value(false))
                .andExpect(jsonPath("$.rawEndpointUrlParsed").value(false))
                .andExpect(jsonPath("$.sourceTemplateCompatibilityVersion").value("Java v166"))
                .andExpect(jsonPath("$.sourceContractHandoffVersion").value("Java v165"))
                .andExpect(jsonPath("$.sourceCanonicalJavaInputVersion").value("Java v164"))
                .andExpect(jsonPath("$.sourceNodeTemplateValidatorVersion").value("Node v402"))
                .andExpect(jsonPath("$.nodeCompatibilityIntakeVersion").value("Node v403"))
                .andExpect(jsonPath("$.miniKvTemplateEchoVersion").value("mini-kv v157"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v404"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-runtime-execution-approval-input-template-compatibility-intake-receipt-v167"))
                .andExpect(jsonPath("$.templateMatrix.length()").value(3))
                .andExpect(jsonPath("$.canonicalTargetPaths.length()").value(3))
                .andExpect(jsonPath("$.templateArchivePaths.length()").value(3))
                .andExpect(jsonPath("$.nodeV403IntakeFields[8]").value("presentCanonicalInputCount:0"))
                .andExpect(jsonPath("$.blockedCanonicalInputs[2]")
                        .value("e/398/input/cross-project-runtime-execution-packet-v398.json:missing-real-approval-input"))
                .andExpect(jsonPath("$.productionBlockers[2]")
                        .value("CROSS_PROJECT_RUNTIME_EXECUTION_PACKET_INPUT_NOT_PRESENT"))
                .andExpect(jsonPath("$.failClosedRules[6]")
                        .value("node-v404-may-run-only-after-real-canonical-approval-inputs-exist"))
                .andExpect(jsonPath("$.stopConditions[0]")
                        .value("request-would-treat-node-v403-intake-as-runtime-approval"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/167/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v167"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.compatibilityIntakeReceiptPresent").value(true))
                .andExpect(jsonPath("$.nodeCompatibilityIntakeVersion").value("Node v403"))
                .andExpect(jsonPath("$.sourceTemplateCompatibilityVersion").value("Java v166"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v404"))
                .andExpect(jsonPath("$.canonicalTargetPaths.length()").value(3))
                .andExpect(jsonPath("$.productionBlockers.length()").value(3))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
