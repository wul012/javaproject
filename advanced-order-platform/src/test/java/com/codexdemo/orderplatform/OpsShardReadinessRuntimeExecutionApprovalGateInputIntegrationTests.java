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
class OpsShardReadinessRuntimeExecutionApprovalGateInputIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionApprovalGateInputReturnsJavaSideInput() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v164"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaApprovalGateInputPresent").value(true))
                .andExpect(jsonPath("$.javaApprovalGateInputComplete").value(true))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(false))
                .andExpect(jsonPath("$.nodeApprovedRuntimeWindowPresent").value(false))
                .andExpect(jsonPath("$.correlatedOperatorApprovalRecordPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(false))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.sourcePacketContributionVersion").value("Java v163"))
                .andExpect(jsonPath("$.lastReviewedByNodeVersion").value("Node v397"))
                .andExpect(jsonPath("$.lastArchiveVerifiedByNodeVersion").value("Node v399"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v400"))
                .andExpect(jsonPath("$.approvalGateInputId").value("java-runtime-execution-approval-gate-input-v164"))
                .andExpect(jsonPath("$.javaOperatorApprovalRecordId")
                        .value("java-runtime-packet-contribution-approval-record-v163"))
                .andExpect(jsonPath("$.approvalCorrelationRequirement")
                        .value("node-v400-must-correlate-java-mini-kv-and-node-approved-runtime-window"))
                .andExpect(jsonPath("$.javaLoopbackPort").value("8080"))
                .andExpect(jsonPath("$.javaApprovalInputArtifacts.length()").value(6))
                .andExpect(jsonPath("$.javaPacketRowsForCorrelation.length()").value(6))
                .andExpect(jsonPath("$.requiredSiblingInputs[0]").value("mini-kv-approval-gate-input"))
                .andExpect(jsonPath("$.nodeApprovalGateInputPaths[0]")
                        .value("e/398/input/node-approved-runtime-window-v398.json"))
                .andExpect(jsonPath("$.failClosedRules[5]")
                        .value("java-approval-gate-input-alone-is-not-runtime-approval"))
                .andExpect(jsonPath("$.stopConditions[3]")
                        .value("request-would-treat-java-only-input-as-correlated-approval"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/164/evidence/java-shard-readiness-runtime-execution-approval-gate-input-v164.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionApprovalGateInputFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v164"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaApprovalGateInputPresent").value(true))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(false))
                .andExpect(jsonPath("$.nodeApprovedRuntimeWindowPresent").value(false))
                .andExpect(jsonPath("$.correlatedOperatorApprovalRecordPresent").value(false))
                .andExpect(jsonPath("$.sourcePacketContributionVersion").value("Java v163"))
                .andExpect(jsonPath("$.lastArchiveVerifiedByNodeVersion").value("Node v399"))
                .andExpect(jsonPath("$.requiredSiblingInputs[3]")
                        .value("complete-cross-project-runtime-execution-packet"))
                .andExpect(jsonPath("$.failClosedRules[1]")
                        .value("missing-node-approved-runtime-window-blocks-runtime-execution"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
