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
class OpsShardReadinessRuntimeExecutionLiveReadGateIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionLiveReadGateReturnsJavaReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-live-read-gate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v169"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.liveReadGateReceiptPresent").value(true))
                .andExpect(jsonPath("$.liveReadGateReceiptComplete").value(true))
                .andExpect(jsonPath("$.sourceValueValidationPresent").value(true))
                .andExpect(jsonPath("$.sourceValueValidationComplete").value(true))
                .andExpect(jsonPath("$.nodeLiveReadGatePresent").value(true))
                .andExpect(jsonPath("$.nodeLiveReadGateAccepted").value(true))
                .andExpect(jsonPath("$.readyForRuntimeExecutionPacket").value(true))
                .andExpect(jsonPath("$.readyForRuntimeLiveReadGate").value(true))
                .andExpect(jsonPath("$.readyForApprovedLocalLoopbackReadOnlySmoke").value(true))
                .andExpect(jsonPath("$.runtimeSmokeAttempted").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.cleanupProofRequired").value(true))
                .andExpect(jsonPath("$.sourceValueValidationVersion").value("Java v168"))
                .andExpect(jsonPath("$.sourceNodeValueValidationVersion").value("Node v405"))
                .andExpect(jsonPath("$.nodeLiveReadGateVersion").value("Node v406"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v407"))
                .andExpect(jsonPath("$.receiptId").value("java-runtime-execution-live-read-gate-receipt-v169"))
                .andExpect(jsonPath("$.targetCount").value(2))
                .andExpect(jsonPath("$.readyTargetCount").value(2))
                .andExpect(jsonPath("$.nodeCheckCount").value(33))
                .andExpect(jsonPath("$.nodeProductionBlockerCount").value(0))
                .andExpect(jsonPath("$.runtimeTargets[0]")
                        .value("java:owner=java-platform-operator:GET:http://127.0.0.1:8080/actuator/health"))
                .andExpect(jsonPath("$.acceptedNodeGateFields[4]").value("runtimeSmokeAttempted:false"))
                .andExpect(jsonPath("$.cleanupProofRequirements[3]").value("stop-only-owned-processes"))
                .andExpect(jsonPath("$.failClosedRules[6]")
                        .value("node-v407-must-capture-approved-local-loopback-read-only-smoke"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/169/evidence/java-shard-readiness-runtime-execution-live-read-gate-v169.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionLiveReadGateFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get("/contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v169"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.nodeLiveReadGateVersion").value("Node v406"))
                .andExpect(jsonPath("$.readyForApprovedLocalLoopbackReadOnlySmoke").value(true))
                .andExpect(jsonPath("$.runtimeSmokeAttempted").value(false))
                .andExpect(jsonPath("$.targetCount").value(2))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
