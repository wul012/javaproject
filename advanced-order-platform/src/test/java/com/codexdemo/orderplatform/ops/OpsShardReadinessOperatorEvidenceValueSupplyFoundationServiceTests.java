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

    @Test
    void buildsEnvelopeTemplateWithMetadataOnlyFoundationSlots() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse template =
                new OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService().template();

        assertThat(template.version()).isEqualTo("Java v638");
        assertThat(template.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-envelope-template");
        assertThat(template.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-envelope-template.v1");
        assertThat(template.readyForOperatorValueSupplyEnvelope()).isTrue();
        assertThat(template.suppliedValueState()).isEqualTo("not-accepted");
        assertThat(template.readyForOperatorValueSubmission()).isFalse();
        assertThat(template.slotCount()).isEqualTo(4);
        assertThat(template.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_01_ENVELOPE_ID",
                        "VALUE_SUPPLY_02_OPERATOR_REFERENCE",
                        "VALUE_SUPPLY_03_SOURCE_DRAFT_SLOT",
                        "VALUE_SUPPLY_04_VALUE_KIND"
                );
        assertThat(template.checks()).contains(
                "value-supply-template-foundation-slice-1-4",
                "value-supply-template-metadata-only",
                "value-supply-template-no-value-field"
        );
        assertThat(template.status()).isEqualTo("passed");
    }

    @Test
    void buildsRedactionPolicyThatBlocksSecretCarryingFields() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse policy =
                new OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService().policy();

        assertThat(policy.version()).isEqualTo("Java v640");
        assertThat(policy.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-redaction-policy");
        assertThat(policy.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-redaction-policy.v1");
        assertThat(policy.redactionState()).isEqualTo("redact-before-storage");
        assertThat(policy.readyForOperatorValueSubmission()).isFalse();
        assertThat(policy.slotCount()).isEqualTo(4);
        assertThat(policy.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_05_REDACTION_CLASSIFICATION",
                        "VALUE_SUPPLY_06_CREDENTIAL_VALUE_BLOCK",
                        "VALUE_SUPPLY_07_RAW_ENDPOINT_BLOCK",
                        "VALUE_SUPPLY_08_SECRET_MATERIAL_BLOCK"
                );
        assertThat(policy.checks()).contains(
                "value-supply-redaction-policy-slice-5-8",
                "value-supply-redaction-credential-values-blocked",
                "value-supply-redaction-raw-endpoints-blocked",
                "value-supply-redaction-secret-material-blocked"
        );
        assertThat(policy.status()).isEqualTo("passed");
    }

    @Test
    void buildsMissingValuePolicyThatDoesNotDefaultBlankOrManualValues() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse policy =
                new OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService().policy();

        assertThat(policy.version()).isEqualTo("Java v642");
        assertThat(policy.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-missing-value-policy");
        assertThat(policy.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-missing-value-policy.v1");
        assertThat(policy.suppliedValueState()).isEqualTo("not-accepted");
        assertThat(policy.readyForManualEvidenceEntry()).isFalse();
        assertThat(policy.slotCount()).isEqualTo(4);
        assertThat(policy.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_09_MISSING_VALUE_POLICY",
                        "VALUE_SUPPLY_10_BLANK_VALUE_POLICY",
                        "VALUE_SUPPLY_11_MANUAL_ENTRY_LOCK",
                        "VALUE_SUPPLY_12_REVIEWER_REQUIRED"
                );
        assertThat(policy.checks()).contains(
                "value-supply-missing-policy-slice-9-12",
                "value-supply-missing-values-not-defaulted",
                "value-supply-blank-values-rejected",
                "value-supply-manual-entry-still-locked"
        );
        assertThat(policy.status()).isEqualTo("passed");
    }
}
