package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessManualEvidenceWorksheetFoundationServiceTests {

    @Test
    void buildsWorksheetCatalogWithEntryLocks() {
        OpsShardReadinessManualEvidenceWorksheetResponse catalog =
                new OpsShardReadinessManualEvidenceWorksheetCatalogService().catalog();

        assertThat(catalog.version()).isEqualTo("Java v560");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-catalog.v1");
        assertThat(catalog.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(catalog.readyForManualEvidenceEntry()).isFalse();
        assertThat(catalog.readyForLiveExecution()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.itemCount()).isEqualTo(4);
        assertThat(catalog.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "source-review-package",
                        "blank-slot-count",
                        "gate-count",
                        "fail-closed-flags"
                );
        assertThat(catalog.checks()).contains(
                "worksheet-catalog-slot-count-25",
                "worksheet-catalog-gate-count-21",
                "worksheet-catalog-ready-for-operator-entry-only"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void buildsSlotTemplateWithoutManualValues() {
        OpsShardReadinessManualEvidenceWorksheetResponse template =
                new OpsShardReadinessManualEvidenceWorksheetSlotTemplateService().template();

        assertThat(template.version()).isEqualTo("Java v562");
        assertThat(template.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-slot-template");
        assertThat(template.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-slot-template.v1");
        assertThat(template.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(template.readyForManualEvidenceEntry()).isFalse();
        assertThat(template.itemCount()).isEqualTo(4);
        assertThat(template.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "slot-identity",
                        "blank-value-state",
                        "operator-note",
                        "validation-hint"
                );
        assertThat(template.checks()).contains(
                "slot-template-blank-values-only",
                "slot-template-no-secret-placeholder",
                "slot-template-target-scope-required"
        );
        assertThat(template.status()).isEqualTo("passed");
    }

    @Test
    void buildsValidationRulesThatRejectRuntimePayloads() {
        OpsShardReadinessManualEvidenceWorksheetResponse rules =
                new OpsShardReadinessManualEvidenceWorksheetValidationRulesService().rules();

        assertThat(rules.version()).isEqualTo("Java v564");
        assertThat(rules.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-validation-rules");
        assertThat(rules.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-validation-rules.v1");
        assertThat(rules.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(rules.readyForLiveExecution()).isFalse();
        assertThat(rules.itemCount()).isEqualTo(4);
        assertThat(rules.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "required-slot-id",
                        "accepted-empty-state",
                        "rejected-runtime-value",
                        "reviewer-note-length"
                );
        assertThat(rules.checks()).contains(
                "validation-rules-allow-empty-manual-value",
                "validation-rules-reject-runtime-payload",
                "validation-rules-do-not-import-values"
        );
        assertThat(rules.status()).isEqualTo("passed");
    }
}
