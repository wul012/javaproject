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
class OpsShardReadinessOperatorEvidenceValueDraftFoundationIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void operatorEvidenceValueDraftCatalogReturnsTwentyFiveBlockedDraftSlots() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v610"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.draftValueState").value("awaiting-operator-value"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.readyForLiveExecution").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-catalog.v1"))
                .andExpect(jsonPath("$.sourcePlan").value("Node v911"))
                .andExpect(jsonPath("$.slotCount").value(25))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT"))
                .andExpect(jsonPath("$.slots[24].code").value("VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.slots[0].importValueState").value("blocked"))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-catalog-slot-count-25"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftSlotTemplateReturnsNoValueTemplate() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-slot-template"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v612"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-slot-template"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-slot-template.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_DRAFT_04_NO_VALUE_INGESTION"))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-slot-template-catalog-slice-1-4"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-slot-template-no-operator-values"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftValueBoundaryReturnsActualValueBoundary() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-value-boundary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v614"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-value-boundary"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-value-boundary.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_05_SLOT_ID_NORMALIZATION"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_DRAFT_08_NOTE_TEXT_NORMALIZATION"))
                .andExpect(jsonPath("$.slots[0].importValueState").value("blocked"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-boundary-actual-values-not-supplied"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftInstructionSetReturnsBlockedImportInstructions() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-instruction-set"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v616"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-instruction-set"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-instruction-set.v1"))
                .andExpect(jsonPath("$.slotCount").value(5))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_09_MISSING_MANUAL_VALUE_BLOCKER"))
                .andExpect(jsonPath("$.slots[4].code").value("VALUE_DRAFT_13_MANUAL_ENTRY_LOCK_BLOCKER"))
                .andExpect(jsonPath("$.checks[9]").value("value-draft-instruction-set-blocker-slice-9-13"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-instruction-set-no-submitted-values"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueDraftSafetyGateMatrixReturnsNoSecretGates() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-draft-safety-gate-matrix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v618"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorEvidenceValueDraft").value(true))
                .andExpect(jsonPath("$.actualValueState").value("not-supplied"))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-draft-safety-gate-matrix"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-draft-safety-gate-matrix.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_DRAFT_14_CREDENTIAL_REDACTION"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_DRAFT_17_BLANK_SLOT_GUARD"))
                .andExpect(jsonPath("$.checks[10]").value("value-draft-safety-gate-no-secret-values"))
                .andExpect(jsonPath("$.checks[11]").value("value-draft-safety-gate-no-synthetic-evidence"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
