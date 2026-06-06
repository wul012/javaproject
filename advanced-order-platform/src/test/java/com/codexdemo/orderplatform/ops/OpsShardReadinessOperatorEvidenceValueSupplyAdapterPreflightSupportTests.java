package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupportTests {

    @Test
    void buildsDisabledAdapterPreflightResponseWithoutAcceptingValues() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse response =
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                        "Java v659",
                        "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-example",
                        "java-operator-evidence-value-supply-adapter-preflight-example.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.slot(
                                "ADAPTER_PREFLIGHT_EXAMPLE",
                                "VALUE_SUPPLY_EXAMPLE",
                                "compatibility",
                                "Adapter metadata is reviewed before implementation.",
                                "adapter implementation remains locked",
                                OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService.ENDPOINT
                        )),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.rule(
                                "RULE_EXAMPLE",
                                "submission",
                                "Operator values are not accepted by the disabled adapter preflight.",
                                "fail-closed"
                        )),
                        List.of("example-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v659");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForDisabledAdapterPreflight()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v936");
        assertThat(response.sourceSupplyVersion()).isEqualTo("Java v658");
        assertThat(response.adapterState()).isEqualTo("disabled-preflight");
        assertThat(response.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(response.compatibilityState()).isEqualTo("metadata-only");
        assertThat(response.redactionState()).isEqualTo("required-before-adapter");
        assertThat(response.provenanceState()).isEqualTo("required-before-adapter");
        assertThat(response.submissionState()).isEqualTo("locked");
        assertThat(response.readyForAdapterImplementation()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.readyForLiveExecution()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.slotCount()).isEqualTo(1);
        assertThat(response.passedSlotCount()).isEqualTo(1);
        assertThat(response.ruleCount()).isEqualTo(1);
        assertThat(response.slots().get(0).blockedReason())
                .isEqualTo("adapter implementation remains locked");
        assertThat(response.rules().get(0).enforcement()).isEqualTo("fail-closed");
        assertThat(response.checks()).contains(
                "value-supply-adapter-preflight-source-plan-Node v936",
                "value-supply-adapter-preflight-source-supply-Java v658",
                "value-supply-adapter-preflight-disabled",
                "value-supply-adapter-preflight-values-not-accepted",
                "value-supply-adapter-preflight-production-locked",
                "example-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
