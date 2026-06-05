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

    @Test
    void buildsRedactionRulesThatExcludeSecretsAndRawEndpoints() {
        OpsShardReadinessManualEvidenceWorksheetResponse rules =
                new OpsShardReadinessManualEvidenceWorksheetRedactionRulesService().rules();

        assertThat(rules.version()).isEqualTo("Java v566");
        assertThat(rules.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-redaction-rules");
        assertThat(rules.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-redaction-rules.v1");
        assertThat(rules.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(rules.readyForManualEvidenceEntry()).isFalse();
        assertThat(rules.itemCount()).isEqualTo(4);
        assertThat(rules.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "credential-value-ban",
                        "raw-endpoint-ban",
                        "placeholder-policy",
                        "review-text-boundary"
                );
        assertThat(rules.checks()).contains(
                "redaction-rules-ban-credential-values",
                "redaction-rules-ban-raw-endpoints",
                "redaction-rules-placeholders-are-absence-markers"
        );
        assertThat(rules.status()).isEqualTo("passed");
    }

    @Test
    void buildsMissingValuePolicyThatBlocksImporterReadiness() {
        OpsShardReadinessManualEvidenceWorksheetResponse policy =
                new OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService().policy();

        assertThat(policy.version()).isEqualTo("Java v568");
        assertThat(policy.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-missing-value-policy");
        assertThat(policy.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-missing-value-policy.v1");
        assertThat(policy.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(policy.readyForManualEvidenceEntry()).isFalse();
        assertThat(policy.itemCount()).isEqualTo(4);
        assertThat(policy.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "missing-manual-value",
                        "missing-owner-review",
                        "missing-target-scope",
                        "missing-import-source"
                );
        assertThat(policy.checks()).contains(
                "missing-value-policy-keeps-worksheet-ready",
                "missing-value-policy-blocks-manual-entry",
                "missing-value-policy-blocks-importer"
        );
        assertThat(policy.status()).isEqualTo("passed");
    }

    @Test
    void buildsTargetScopeRegistryWithoutActivatingRouting() {
        OpsShardReadinessManualEvidenceWorksheetResponse registry =
                new OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService().registry();

        assertThat(registry.version()).isEqualTo("Java v570");
        assertThat(registry.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/manual-evidence-worksheet-target-scope-registry");
        assertThat(registry.profile()).isEqualTo(
                "java-shard-readiness-manual-evidence-worksheet-target-scope-registry.v1");
        assertThat(registry.readyForOperatorEntryWorksheet()).isTrue();
        assertThat(registry.readyForProductionExecution()).isFalse();
        assertThat(registry.itemCount()).isEqualTo(4);
        assertThat(registry.items())
                .extracting(OpsShardReadinessManualEvidenceWorksheetResponse.WorksheetItem::name)
                .containsExactly(
                        "order-read-model",
                        "shard-preview-window",
                        "evidence-review-package",
                        "operator-entry-slot"
                );
        assertThat(registry.checks()).contains(
                "target-scope-registry-scope-count-4",
                "target-scope-registry-no-write-routing",
                "target-scope-registry-no-active-shard-router"
        );
        assertThat(registry.status()).isEqualTo("passed");
    }
}
