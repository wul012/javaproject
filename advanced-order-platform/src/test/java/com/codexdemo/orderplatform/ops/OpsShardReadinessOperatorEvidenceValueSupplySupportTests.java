package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySupportTests {

    @Test
    void buildsDisabledValueSupplyEnvelopeWithoutAcceptingValues() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse response =
                OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
                        "Java v634",
                        "/api/v1/ops/shard-readiness/operator-evidence-value-supply-example",
                        "java-operator-evidence-value-supply-example.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySupport.slot(
                                "VALUE_SUPPLY_EXAMPLE",
                                "VALUE_DRAFT_EXAMPLE",
                                OpsShardReadinessOperatorEvidenceValueDraftCloseoutService.ENDPOINT,
                                "Define the disabled envelope without storing a value.",
                                "no supplied value is accepted",
                                "source evidence provenance is required before import"
                        )),
                        List.of("example-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v634");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForOperatorValueSupplyEnvelope()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v936");
        assertThat(response.sourceDraftVersion()).isEqualTo("Java v633");
        assertThat(response.envelopeState()).isEqualTo("disabled-design");
        assertThat(response.suppliedValueState()).isEqualTo("not-accepted");
        assertThat(response.redactionState()).isEqualTo("redact-before-storage");
        assertThat(response.provenanceState()).isEqualTo("required-before-import");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForManualEvidenceEntry()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.readyForLiveExecution()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.slotCount()).isEqualTo(1);
        assertThat(response.passedSlotCount()).isEqualTo(1);
        assertThat(response.slots().get(0).valuePolicy()).isEqualTo("no supplied value is accepted");
        assertThat(response.checks()).contains(
                "value-supply-source-plan-Node v936",
                "value-supply-source-draft-Java v633",
                "value-supply-supplied-value-state-not-accepted",
                "value-supply-production-execution-locked",
                "example-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
