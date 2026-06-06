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

    @Test
    void buildsProvenanceRequirementForSourceEvidenceReferences() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse requirement =
                new OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService().requirement();

        assertThat(requirement.version()).isEqualTo("Java v644");
        assertThat(requirement.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-provenance-requirement");
        assertThat(requirement.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-provenance-requirement.v1");
        assertThat(requirement.provenanceState()).isEqualTo("required-before-import");
        assertThat(requirement.readyForEvidenceImport()).isFalse();
        assertThat(requirement.slotCount()).isEqualTo(4);
        assertThat(requirement.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_13_PROVENANCE_SOURCE_ID",
                        "VALUE_SUPPLY_14_PROVENANCE_EVIDENCE_FILE",
                        "VALUE_SUPPLY_15_PROVENANCE_SNIPPET_ID",
                        "VALUE_SUPPLY_16_SOURCE_ENDPOINT_ALIAS"
                );
        assertThat(requirement.checks()).contains(
                "value-supply-provenance-requirement-slice-13-16",
                "value-supply-provenance-source-id-required",
                "value-supply-provenance-evidence-file-required",
                "value-supply-provenance-raw-endpoint-alias-only"
        );
        assertThat(requirement.status()).isEqualTo("passed");
    }

    @Test
    void buildsSourceEvidenceGuardForFreshSiblingAndFallbackReferences() {
        OpsShardReadinessOperatorEvidenceValueSupplyResponse guard =
                new OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService().guard();

        assertThat(guard.version()).isEqualTo("Java v646");
        assertThat(guard.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-source-evidence-guard");
        assertThat(guard.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-source-evidence-guard.v1");
        assertThat(guard.readyForRuntimePayload()).isFalse();
        assertThat(guard.slotCount()).isEqualTo(4);
        assertThat(guard.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyResponse.SupplySlot::code)
                .containsExactly(
                        "VALUE_SUPPLY_17_FRESH_SIBLING_REFERENCE",
                        "VALUE_SUPPLY_18_HISTORICAL_FALLBACK_MARKER",
                        "VALUE_SUPPLY_19_SYNTHETIC_EVIDENCE_BLOCK",
                        "VALUE_SUPPLY_20_RUNTIME_PAYLOAD_BLOCK"
                );
        assertThat(guard.checks()).contains(
                "value-supply-source-evidence-guard-slice-17-20",
                "value-supply-source-evidence-fresh-sibling-read-only",
                "value-supply-source-evidence-fallback-explicit",
                "value-supply-source-evidence-synthetic-blocked"
        );
        assertThat(guard.status()).isEqualTo("passed");
    }
}
