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
class OpsShardReadinessRuntimeExecutionPacketContributionIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionPacketContributionReturnsJavaSideContribution() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-packet-contribution"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v163"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaPacketContributionPresent").value(true))
                .andExpect(jsonPath("$.javaPacketContributionComplete").value(true))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(false))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.sourceRuntimeArtifactCandidateVersion").value("Java v162"))
                .andExpect(jsonPath("$.lastClarifiedByNodeVersion").value("Node v396"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v397"))
                .andExpect(jsonPath("$.operatorApprovalRecordId")
                        .value("java-runtime-packet-contribution-approval-record-v163"))
                .andExpect(jsonPath("$.operatorApprovalCorrelationRequirement")
                        .value("must-be-correlated-by-node-approved-cross-project-runtime-window"))
                .andExpect(jsonPath("$.javaLoopbackPort").value("8080"))
                .andExpect(jsonPath("$.miniKvLoopbackPortRequirement")
                        .value("requires-mini-kv-runtime-packet-contribution"))
                .andExpect(jsonPath("$.acceptedRequirementRows.length()").value(6))
                .andExpect(jsonPath("$.getOnlySmokeCommands[1]")
                        .value("GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-packet-contribution"))
                .andExpect(jsonPath("$.crossProjectMissingArtifacts[0]")
                        .value("mini-kv-runtime-execution-packet-contribution"))
                .andExpect(jsonPath("$.stopConditions[4]")
                        .value("request-would-treat-java-only-contribution-as-cross-project-packet"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/163/evidence/java-shard-readiness-runtime-execution-packet-contribution-v163.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionPacketContributionFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v163"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaPacketContributionPresent").value(true))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.crossProjectRuntimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.sourceRuntimeArtifactCandidateVersion").value("Java v162"))
                .andExpect(jsonPath("$.lastClarifiedByNodeVersion").value("Node v396"))
                .andExpect(jsonPath("$.acceptedRequirementRows[5]")
                        .value("process-cleanup-rules:java-stop-only-owned-process-rules-present-mini-kv-required"))
                .andExpect(jsonPath("$.processCleanupRules[2]").value("never-stop-pre-existing-java-service"))
                .andExpect(jsonPath("$.failClosedRules[2]")
                        .value("uncorrelated-operator-approval-record-blocks-runtime-execution"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
