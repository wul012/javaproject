package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueDraftFoundationServiceTests {

    @Test
    void buildsValueDraftCatalogWithTwentyFiveBlockedDraftSlots() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse catalog =
                new OpsShardReadinessOperatorEvidenceValueDraftCatalogService().catalog();

        assertThat(catalog.version()).isEqualTo("Java v610");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-catalog.v1");
        assertThat(catalog.sourcePlan()).isEqualTo("Node v911");
        assertThat(catalog.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(catalog.actualValueState()).isEqualTo("not-supplied");
        assertThat(catalog.readyForEvidenceImport()).isFalse();
        assertThat(catalog.readyForManualEvidenceEntry()).isFalse();
        assertThat(catalog.readyForLiveExecution()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.slotCount()).isEqualTo(25);
        assertThat(catalog.passedSlotCount()).isEqualTo(25);
        assertThat(catalog.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot::code)
                .startsWith(
                        "VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT",
                        "VALUE_DRAFT_02_PREFLIGHT_SLOT_COUNT"
                )
                .endsWith("VALUE_DRAFT_25_CLOSEOUT_LOCKS_HELD");
        assertThat(catalog.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot::importValueState)
                .containsOnly("blocked");
        assertThat(catalog.checks()).contains(
                "value-draft-catalog-slot-count-25",
                "value-draft-catalog-actual-values-not-supplied",
                "value-draft-catalog-source-import-preflight-v608"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void buildsSlotTemplateWithoutOperatorValues() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse template =
                new OpsShardReadinessOperatorEvidenceValueDraftSlotTemplateService().template();

        assertThat(template.version()).isEqualTo("Java v612");
        assertThat(template.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-slot-template");
        assertThat(template.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-slot-template.v1");
        assertThat(template.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(template.actualValueState()).isEqualTo("not-supplied");
        assertThat(template.readyForEvidenceImport()).isFalse();
        assertThat(template.slotCount()).isEqualTo(4);
        assertThat(template.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot::code)
                .containsExactly(
                        "VALUE_DRAFT_01_SOURCE_WORKSHEET_CLOSEOUT",
                        "VALUE_DRAFT_02_PREFLIGHT_SLOT_COUNT",
                        "VALUE_DRAFT_03_PREFLIGHT_GATE_COUNT",
                        "VALUE_DRAFT_04_NO_VALUE_INGESTION"
                );
        assertThat(template.checks()).contains(
                "value-draft-slot-template-catalog-slice-1-4",
                "value-draft-slot-template-fields-present",
                "value-draft-slot-template-no-operator-values"
        );
        assertThat(template.status()).isEqualTo("passed");
    }

    @Test
    void buildsValueBoundaryWithActualValuesNotSupplied() {
        OpsShardReadinessOperatorEvidenceValueDraftResponse boundary =
                new OpsShardReadinessOperatorEvidenceValueDraftValueBoundaryService().boundary();

        assertThat(boundary.version()).isEqualTo("Java v614");
        assertThat(boundary.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-draft-value-boundary");
        assertThat(boundary.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-draft-value-boundary.v1");
        assertThat(boundary.readyForOperatorEvidenceValueDraft()).isTrue();
        assertThat(boundary.actualValueState()).isEqualTo("not-supplied");
        assertThat(boundary.readyForEvidenceImport()).isFalse();
        assertThat(boundary.slotCount()).isEqualTo(4);
        assertThat(boundary.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot::code)
                .containsExactly(
                        "VALUE_DRAFT_05_SLOT_ID_NORMALIZATION",
                        "VALUE_DRAFT_06_BLANK_VALUE_NORMALIZATION",
                        "VALUE_DRAFT_07_SCOPE_NAME_NORMALIZATION",
                        "VALUE_DRAFT_08_NOTE_TEXT_NORMALIZATION"
                );
        assertThat(boundary.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueDraftResponse.DraftSlot::importValueState)
                .containsOnly("blocked");
        assertThat(boundary.checks()).contains(
                "value-draft-boundary-source-slice-5-8",
                "value-draft-boundary-actual-values-not-supplied",
                "value-draft-boundary-import-value-state-blocked"
        );
        assertThat(boundary.status()).isEqualTo("passed");
    }
}
