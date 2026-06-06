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
class OpsShardReadinessOperatorEvidenceValueSupplyAssuranceIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void operatorEvidenceValueSupplyValidationMatrixReturnsLockedExecutionGates() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-validation-matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v648"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorValueSubmission").value(false))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-validation-matrix"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-validation-matrix.v1"))
                .andExpect(jsonPath("$.slotCount").value(5))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK"))
                .andExpect(jsonPath("$.slots[4].code").value("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[11]").value("value-supply-validation-operator-submission-locked"))
                .andExpect(jsonPath("$.checks[13]").value("value-supply-validation-execution-locks-held"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
