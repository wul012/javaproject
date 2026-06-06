package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueDraftSupportTests {

    @Test
    void buildsReadOnlyValueDraftResponseWithActualValuesNotSupplied() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse response =
                OpsShardReadinessOperatorEvidenceValueDraftSupport.response(
                        "Java v609",
                        "/api/v1/ops/shard-readiness/operator-evidence-value-draft-example",
                        "java-operator-evidence-value-draft-example.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueDraftSupport.slot(
                                "VALUE_DRAFT_EXAMPLE",
                                "IMPORT_PREFLIGHT_EXAMPLE",
                                "Prepare a draft slot without entering a value.",
                                "actual value is not supplied",
                                OpsShardReadinessOperatorEvidenceImportPreflightCloseoutService.ENDPOINT
                        )),
                        List.of("example-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v609");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(response.actualValueState()).isEqualTo("not-supplied");
        assertThat(response.draftValueState()).isEqualTo("awaiting-operator-value");
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForManualEvidenceEntry()).isFalse();
        assertThat(response.readyForLiveExecution()).isFalse();
        assertThat(response.readyForProductionExecution()).isFalse();
        assertThat(response.sourcePlan()).isEqualTo("Node v911");
        assertThat(response.slotCount()).isOne();
        assertThat(response.passedSlotCount()).isOne();
        assertThat(response.slots()).singleElement().satisfies(slot -> {
            assertThat(slot.code()).isEqualTo("VALUE_DRAFT_EXAMPLE");
            assertThat(slot.sourceSlot()).isEqualTo("IMPORT_PREFLIGHT_EXAMPLE");
            assertThat(slot.importValueState()).isEqualTo("blocked");
            assertThat(slot.status()).isEqualTo("passed");
        });
        assertThat(response.checks()).containsExactly(
                "slot-count-1",
                "passed-slot-count-1",
                "source-plan-Node v911",
                "operator-evidence-value-draft-ready",
                "actual-value-state-not-supplied",
                "evidence-import-locked",
                "manual-evidence-entry-locked",
                "live-execution-locked",
                "production-execution-locked",
                "example-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
