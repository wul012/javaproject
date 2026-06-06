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
class OpsShardReadinessOperatorEvidenceValueSupplyFoundationIntegrationTests
        extends OpsOverviewIntegrationTestSupport {

    @Test
    void operatorEvidenceValueSupplyCatalogReturnsDisabledEnvelopeSlots() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v636"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorValueSupplyEnvelope").value(true))
                .andExpect(jsonPath("$.sourcePlan").value("Node v936"))
                .andExpect(jsonPath("$.sourceDraftVersion").value("Java v633"))
                .andExpect(jsonPath("$.envelopeState").value("disabled-design"))
                .andExpect(jsonPath("$.suppliedValueState").value("not-accepted"))
                .andExpect(jsonPath("$.readyForOperatorValueSubmission").value(false))
                .andExpect(jsonPath("$.readyForEvidenceImport").value(false))
                .andExpect(jsonPath("$.readyForRuntimePayload").value(false))
                .andExpect(jsonPath("$.readyForProductionExecution").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-catalog"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-catalog.v1"))
                .andExpect(jsonPath("$.slotCount").value(25))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_01_ENVELOPE_ID"))
                .andExpect(jsonPath("$.slots[24].code").value("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD"))
                .andExpect(jsonPath("$.checks[10]").value("value-supply-catalog-slot-count-25"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueSupplyEnvelopeTemplateReturnsMetadataOnlySlots() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-envelope-template"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v638"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.readyForOperatorValueSupplyEnvelope").value(true))
                .andExpect(jsonPath("$.suppliedValueState").value("not-accepted"))
                .andExpect(jsonPath("$.readyForOperatorValueSubmission").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-envelope-template"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-envelope-template.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_01_ENVELOPE_ID"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_SUPPLY_04_VALUE_KIND"))
                .andExpect(jsonPath("$.checks[10]").value("value-supply-template-foundation-slice-1-4"))
                .andExpect(jsonPath("$.checks[12]").value("value-supply-template-no-value-field"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueSupplyRedactionPolicyReturnsSecretBlocks() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-redaction-policy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v640"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.redactionState").value("redact-before-storage"))
                .andExpect(jsonPath("$.readyForOperatorValueSubmission").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-redaction-policy"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-redaction-policy.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_05_REDACTION_CLASSIFICATION"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_SUPPLY_08_SECRET_MATERIAL_BLOCK"))
                .andExpect(jsonPath("$.checks[11]").value("value-supply-redaction-credential-values-blocked"))
                .andExpect(jsonPath("$.checks[13]").value("value-supply-redaction-secret-material-blocked"))
                .andExpect(jsonPath("$.status").value("passed"));
    }

    @Test
    void operatorEvidenceValueSupplyMissingValuePolicyReturnsLockedManualEntry() throws Exception {
        mockMvc.perform(get("/api/v1/ops/shard-readiness/operator-evidence-value-supply-missing-value-policy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.version").value("Java v642"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.suppliedValueState").value("not-accepted"))
                .andExpect(jsonPath("$.readyForManualEvidenceEntry").value(false))
                .andExpect(jsonPath("$.endpoint")
                        .value("/api/v1/ops/shard-readiness/operator-evidence-value-supply-missing-value-policy"))
                .andExpect(jsonPath("$.profile")
                        .value("java-shard-readiness-operator-evidence-value-supply-missing-value-policy.v1"))
                .andExpect(jsonPath("$.slotCount").value(4))
                .andExpect(jsonPath("$.slots[0].code").value("VALUE_SUPPLY_09_MISSING_VALUE_POLICY"))
                .andExpect(jsonPath("$.slots[3].code").value("VALUE_SUPPLY_12_REVIEWER_REQUIRED"))
                .andExpect(jsonPath("$.checks[11]").value("value-supply-missing-values-not-defaulted"))
                .andExpect(jsonPath("$.checks[13]").value("value-supply-manual-entry-still-locked"))
                .andExpect(jsonPath("$.status").value("passed"));
    }
}
