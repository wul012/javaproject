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
class OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void opsShardReadinessRuntimeExecutionPassEvidenceCloseoutReturnsJavaReceipt()
            throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v170"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.passEvidenceCloseoutReceiptPresent").value(true))
                .andExpect(jsonPath("$.passEvidenceCloseoutReceiptComplete").value(true))
                .andExpect(jsonPath("$.nodeApprovedSmokePassed").value(true))
                .andExpect(jsonPath("$.nodeArchiveVerificationPassed").value(true))
                .andExpect(jsonPath("$.nodePassEvidenceCloseoutReady").value(true))
                .andExpect(jsonPath("$.readyForRuntimeExecutionChainHandoff").value(true))
                .andExpect(jsonPath("$.approvedLocalLoopbackReadOnlySmokePassed").value(true))
                .andExpect(jsonPath("$.cleanupProofPassed").value(true))
                .andExpect(jsonPath("$.runtimeSmokeRerunByJava").value(false))
                .andExpect(jsonPath("$.startsJavaService").value(false))
                .andExpect(jsonPath("$.startsMiniKvService").value(false))
                .andExpect(jsonPath("$.sourceLiveReadGateVersion").value("Java v169"))
                .andExpect(jsonPath("$.nodeApprovedSmokeVersion").value("Node v407"))
                .andExpect(jsonPath("$.nodeArchiveVerificationVersion").value("Node v408"))
                .andExpect(jsonPath("$.nodePassEvidenceCloseoutVersion").value("Node v409"))
                .andExpect(jsonPath("$.nextNodeConsumerHint").value("Node v410"))
                .andExpect(jsonPath("$.receiptId")
                        .value("java-runtime-execution-pass-evidence-closeout-receipt-v170"))
                .andExpect(jsonPath("$.sourceSummaryCount").value(4))
                .andExpect(jsonPath("$.readyStageCount").value(4))
                .andExpect(jsonPath("$.totalSourceCheckCount").value(114))
                .andExpect(jsonPath("$.totalSourceProductionBlockerCount").value(0))
                .andExpect(jsonPath("$.archiveReferenceCount").value(7))
                .andExpect(jsonPath("$.presentArchiveReferenceCount").value(7))
                .andExpect(jsonPath("$.cleanupProofFields[1]").value("cleanupPassed:true"))
                .andExpect(jsonPath("$.closeoutHandoffChecks[5]")
                        .value("java-v170-does-not-rerun-runtime-smoke"))
                .andExpect(jsonPath("$.failClosedRules[6]")
                        .value("future-route-group-refactors-must-not-change-api-paths"))
                .andExpect(jsonPath("$.evidencePath")
                        .value("e/170/evidence/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.json"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void staticShardReadinessRuntimeExecutionPassEvidenceCloseoutFixtureMatchesContractFields()
            throws Exception {
        mockMvc.perform(get(
                        "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json"
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v170"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.nodePassEvidenceCloseoutVersion").value("Node v409"))
                .andExpect(jsonPath("$.approvedLocalLoopbackReadOnlySmokePassed").value(true))
                .andExpect(jsonPath("$.cleanupProofPassed").value(true))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
