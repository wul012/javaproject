package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyFoundationServiceTests {

    @Test
    void buildsValueSupplyCatalogWithTwentyFiveDisabledEnvelopeSlots() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse catalog =
                new OpsShardReadinessOperatorEvidenceValueSupplyCatalogService().catalog();

        assertThat(catalog.version()).isEqualTo("Java v636");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-catalog.v1");
        assertThat(catalog.sourcePlan()).isEqualTo("Node v936");
        assertThat(catalog.sourceDraftVersion()).isEqualTo("Java v633");
        assertThat(catalog.readyForOperatorValueSupplyEnvelope()).isTrue();
        assertThat(catalog.envelopeState()).isEqualTo("disabled-design");
        assertThat(catalog.suppliedValueState()).isEqualTo("not-accepted");
        assertThat(catalog.readyForOperatorValueSubmission()).isFalse();
        assertThat(catalog.readyForEvidenceImport()).isFalse();
        assertThat(catalog.readyForRuntimePayload()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.slotCount()).isEqualTo(25);
        assertThat(catalog.passedSlotCount()).isEqualTo(25);
        assertThat(catalog.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .startsWith(
                        "VALUE_SUPPLY_01_ENVELOPE_ID",
                        "VALUE_SUPPLY_02_OPERATOR_REFERENCE"
                )
                .endsWith("VALUE_SUPPLY_25_CLOSEOUT_LOCKS_HELD");
        assertThat(catalog.checks()).contains(
                "value-supply-catalog-slot-count-25",
                "value-supply-catalog-source-draft-v633",
                "value-supply-catalog-node-v936-disabled-envelope"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
