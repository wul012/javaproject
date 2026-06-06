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
}
