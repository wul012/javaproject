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

    @Test
    void operatorEvidenceValueSupplySideEffectGateReturnsNoWriteBoundary() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-side-effect-gate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v650"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForRuntimePayload").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-side-effect-gate"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-side-effect-gate.v1"))
                .andExpect(jsonPath("$.slotCount").value(25))
                .andExpect(jsonPath("$.checks[10]").value("value-supply-side-effect-gate-no-sibling-service-start"))
                .andExpect(jsonPath("$.checks[13]").value("value-supply-side-effect-gate-no-production-path"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueSupplyOperatorReviewChecklistReturnsNoApprovalGrant() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-operator-review-checklist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v652"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorValueSubmission").value(false))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-operator-review-checklist"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-operator-review-checklist.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_01_ENVELOPE_ID"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_SUPPLY_04_VALUE_KIND"))
                .andExpect(jsonPath("$.checks[10]").value("value-supply-operator-review-checklist-envelope-id"))
                .andExpect(jsonPath("$.checks[13]").value("value-supply-operator-review-checklist-no-approval-grant"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueSupplyDigestBlueprintReturnsValueFreeDigestPlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-digest-blueprint"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v654"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-digest-blueprint"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-digest-blueprint.v1"))
                .andExpect(jsonPath("$.slotCount").value(25))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_01_ENVELOPE_ID"))
                .andExpect(jsonPath("$.slots[24].code").value("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[10]").value("value-supply-digest-blueprint-slot-count-25"))
                .andExpect(jsonPath("$.checks[11]").value("value-supply-digest-blueprint-no-value-hash"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueSupplyArchivePlanReturnsExternalCapturePlan() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-archive-plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v656"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-archive-plan"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-archive-plan.v1"))
                .andExpect(jsonPath("$.slotCount").value(5))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_21_IMPORT_PREVIEW_BLOCK"))
                .andExpect(jsonPath("$.slots[4].code").value("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[10]").value("value-supply-archive-plan-external-capture"))
                .andExpect(jsonPath("$.checks[12]").value("value-supply-archive-plan-no-runtime-process"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
