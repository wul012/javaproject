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
class OpsShardReadinessRuntimeExecutionArtifactCandidateIntegrationTests extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionArtifactCandidateReturnsJavaSideCandidate() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v162"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaRuntimeArtifactCandidatePresent").value(true))
                .andExpect(jsonPath("$.javaRuntimeArtifactsComplete").value(true))
                .andExpect(jsonPath("$.crossProjectRuntimeArtifactsComplete").value(false))
                .andExpect(jsonPath("$.runtimeExecutionPacketPresent").value(false))
                .andExpect(jsonPath("$.runtimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(false))
                .andExpect(jsonPath("$.readyForRuntimeLiveReadGate").value(false))
                .andExpect(jsonPath("$.executionAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.sourceDeclaredLifecycleVersion").value("Java v161"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v395"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v396"))
                .andExpect(jsonPath("$.operatorApprovalScope").value("java-side-artifact-candidate-only"))
                .andExpect(jsonPath("$.javaLoopbackPort").value("8080"))
                .andExpect(jsonPath("$.miniKvLoopbackPort").value("requires-mini-kv-runtime-artifact"))
                .andExpect(jsonPath("$.getOnlySmokeCommands[1]")
                        .value("GET java-loopback-port-8080 /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate"))
                .andExpect(jsonPath("$.missingCrossProjectArtifacts[0]")
                        .value("mini-kv-v153-runtime-artifact-candidate"))
                .andExpect(jsonPath("$.stopConditions[4]")
                        .value("request-would-use-candidate-as-cross-project-runtime-approval"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/162/evidence/java-shard-readiness-runtime-execution-artifact-candidate-v162.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionArtifactCandidateFixtureMatchesContractFields() throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v162"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.javaRuntimeArtifactCandidatePresent").value(true))
                .andExpect(jsonPath("$.crossProjectRuntimeArtifactsComplete").value(false))
                .andExpect(jsonPath("$.runtimeExecutionPacketExecutable").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.sourceDeclaredLifecycleVersion").value("Java v161"))
                .andExpect(jsonPath("$.lastVerifiedByNodeVersion").value("Node v395"))
                .andExpect(jsonPath("$.processCleanupRules[2]").value("do-not-stop-pre-existing-java-service"))
                .andExpect(jsonPath("$.failClosedRules[1]")
                        .value("missing-mini-kv-runtime-artifact-blocks-runtime-execution-packet"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
