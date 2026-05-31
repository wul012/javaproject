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
class OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityReturnsJavaReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v166"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.templateCompatibilityReceiptPresent").value(true))
                .andExpect(jsonPath("$.templateCompatibilityReceiptComplete").value(true))
                .andExpect(jsonPath("$.sourceContractHandoffPresent").value(true))
                .andExpect(jsonPath("$.sourceJavaInputCanonical").value(true))
                .andExpect(jsonPath("$.nodeTemplateValidatorPresent").value(true))
                .andExpect(jsonPath("$.templatesAreApprovalInputs").value(false))
                .andExpect(jsonPath("$.canonicalApprovalInputsCreatedByJava").value(false))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(false))
                .andExpect(jsonPath("$.nodeApprovedRuntimeWindowPresent").value(false))
                .andExpect(jsonPath("$.correlatedOperatorApprovalRecordPresent").value(false))
                .andExpect(jsonPath("$.completeCrossProjectRuntimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(false))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.sourceContractHandoffVersion").value("Java v165"))
                .andExpect(jsonPath("$.sourceCanonicalJavaInputVersion").value("Java v164"))
                .andExpect(jsonPath("$.lastTemplateValidatorNodeVersion").value("Node v402"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v403"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-runtime-execution-approval-input-template-compatibility-receipt-v166"))
                .andExpect(jsonPath("$.canonicalJavaApprovalInputPath")
                        .value("e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json"))
                .andExpect(jsonPath("$.templateMatrix.length()").value(3))
                .andExpect(jsonPath("$.canonicalTargetPaths[0]")
                        .value("e/398/input/node-approved-runtime-window-v398.json"))
                .andExpect(jsonPath("$.templateArchivePaths[2]")
                        .value("e/402/input-templates/cross-project-runtime-execution-packet-v402.template.json"))
                .andExpect(jsonPath("$.javaTemplateBindingFields[0]").value("java-input-version:Java v164"))
                .andExpect(jsonPath("$.compatibilityChecks[5]")
                        .value("template-archives-are-not-canonical-approval-inputs"))
                .andExpect(jsonPath("$.blockedCanonicalInputs[2]")
                        .value("complete-cross-project-runtime-execution-packet:canonical-file-missing-or-not-owned-by-java"))
                .andExpect(jsonPath("$.failClosedRules[6]")
                        .value("template-compatibility-receipt-alone-is-not-runtime-approval"))
                .andExpect(jsonPath("$.stopConditions[0]")
                        .value("request-would-copy-template-to-canonical-input-path"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/166/evidence/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v166"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.templateCompatibilityReceiptPresent").value(true))
                .andExpect(jsonPath("$.templatesAreApprovalInputs").value(false))
                .andExpect(jsonPath("$.canonicalApprovalInputsCreatedByJava").value(false))
                .andExpect(jsonPath("$.sourceContractHandoffVersion").value("Java v165"))
                .andExpect(jsonPath("$.lastTemplateValidatorNodeVersion").value("Node v402"))
                .andExpect(jsonPath("$.canonicalTargetPaths.length()").value(3))
                .andExpect(jsonPath("$.failClosedRules[1]")
                        .value("node-v402-templates-are-template-only-not-approval-inputs"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
