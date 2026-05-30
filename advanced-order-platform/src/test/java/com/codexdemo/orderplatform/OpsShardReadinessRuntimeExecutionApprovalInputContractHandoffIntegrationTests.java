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
class OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionApprovalInputContractHandoffReturnsJavaSideHandoff()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v165"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaApprovalInputContractHandoffPresent").value(true))
                .andExpect(jsonPath("$.javaApprovalInputContractHandoffComplete").value(true))
                .andExpect(jsonPath("$.sourceJavaApprovalGateInputPresent").value(true))
                .andExpect(jsonPath("$.sourceJavaApprovalGateInputComplete").value(true))
                .andExpect(jsonPath("$.javaInputRemainsCanonical").value(true))
                .andExpect(jsonPath("$.javaInputChangedByThisVersion").value(false))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(false))
                .andExpect(jsonPath("$.nodeApprovedRuntimeWindowPresent").value(false))
                .andExpect(jsonPath("$.correlatedOperatorApprovalRecordPresent").value(false))
                .andExpect(jsonPath("$.completeCrossProjectRuntimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(false))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.sourceApprovalGateInputVersion").value("Java v164"))
                .andExpect(jsonPath("$.lastContractedByNodeVersion").value("Node v400"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v401"))
                .andExpect(jsonPath("$.handoffId")
                        .value("java-runtime-execution-approval-input-contract-handoff-v165"))
                .andExpect(jsonPath("$.canonicalJavaApprovalInputEndpoint")
                        .value("/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input"))
                .andExpect(jsonPath("$.javaOwnedArtifacts.length()").value(7))
                .andExpect(jsonPath("$.ownerByOwnerHandoff[1]")
                        .value("mini-kv:final-approval-gate-input-required-not-owned-by-java"))
                .andExpect(jsonPath("$.nonJavaMissingInputs[0]").value("final-mini-kv-approval-gate-input"))
                .andExpect(jsonPath("$.finalPacketBindingRequirements[0]")
                        .value("bind-java-v164-approval-gate-input"))
                .andExpect(jsonPath("$.failClosedRules[6]")
                        .value("contract-handoff-alone-is-not-runtime-approval"))
                .andExpect(jsonPath("$.stopConditions[5]")
                        .value("request-would-treat-contract-handoff-as-complete-cross-project-packet"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/165/evidence/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionApprovalInputContractHandoffFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v165"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaApprovalInputContractHandoffPresent").value(true))
                .andExpect(jsonPath("$.javaInputRemainsCanonical").value(true))
                .andExpect(jsonPath("$.runtimeGateApprovalPresent").value(false))
                .andExpect(jsonPath("$.nodeApprovedRuntimeWindowPresent").value(false))
                .andExpect(jsonPath("$.correlatedOperatorApprovalRecordPresent").value(false))
                .andExpect(jsonPath("$.sourceApprovalGateInputVersion").value("Java v164"))
                .andExpect(jsonPath("$.lastContractedByNodeVersion").value("Node v400"))
                .andExpect(jsonPath("$.nonJavaMissingInputs[3]")
                        .value("complete-cross-project-runtime-execution-packet"))
                .andExpect(jsonPath("$.failClosedRules[2]")
                        .value("missing-final-mini-kv-approval-gate-input-blocks-runtime-execution"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
