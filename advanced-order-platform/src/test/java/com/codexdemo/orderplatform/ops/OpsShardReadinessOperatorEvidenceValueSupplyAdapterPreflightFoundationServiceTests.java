package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationServiceTests {

    @Test
    void buildsCatalogWithAllSlotsAndRulesButNoAdapterImplementation() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse catalog =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService().catalog();

        assertThat(catalog.version()).isEqualTo("Java v662");
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-catalog.v1");
        assertThat(catalog.sourcePlan()).isEqualTo("Node v986");
        assertThat(catalog.sourceSupplyVersion()).isEqualTo("Java v658");
        assertThat(catalog.readyForDisabledAdapterPreflight()).isTrue();
        assertThat(catalog.readyForAdapterImplementation()).isFalse();
        assertThat(catalog.readyForOperatorValueSubmission()).isFalse();
        assertThat(catalog.readyForEvidenceImport()).isFalse();
        assertThat(catalog.readyForRuntimePayload()).isFalse();
        assertThat(catalog.readyForLiveExecution()).isFalse();
        assertThat(catalog.readyForProductionExecution()).isFalse();
        assertThat(catalog.slotCount()).isEqualTo(25);
        assertThat(catalog.passedSlotCount()).isEqualTo(25);
        assertThat(catalog.ruleCount()).isEqualTo(18);
        assertThat(catalog.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
                .startsWith("ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY")
                .endsWith("ADAPTER_PREFLIGHT_25_CLOSEOUT_LOCKS_HELD");
        assertThat(catalog.rules())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
                .startsWith("ADAPTER_RULE_01_DISABLED_IMPLEMENTATION")
                .endsWith("ADAPTER_RULE_18_CLOSEOUT_LOCK_SUMMARY_REQUIRED");
        assertThat(catalog.checks()).contains(
                "value-supply-adapter-preflight-catalog-slot-count-25",
                "value-supply-adapter-preflight-catalog-rule-count-18",
                "value-supply-adapter-preflight-catalog-node-v986-approval-draft-boundary"
        );
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void buildsCompatibilityMatrixWithMetadataOnlyAdapterChecks() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse matrix =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService()
                        .matrix();

        assertThat(matrix.version()).isEqualTo("Java v664");
        assertThat(matrix.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-compatibility-matrix");
        assertThat(matrix.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-compatibility-matrix.v1");
        assertThat(matrix.compatibilityState()).isEqualTo("metadata-only");
        assertThat(matrix.readyForAdapterImplementation()).isFalse();
        assertThat(matrix.readyForOperatorValueSubmission()).isFalse();
        assertThat(matrix.slotCount()).isEqualTo(4);
        assertThat(matrix.ruleCount()).isEqualTo(4);
        assertThat(matrix.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
                .containsExactly(
                        "ADAPTER_PREFLIGHT_01_ENVELOPE_ID_COMPATIBILITY",
                        "ADAPTER_PREFLIGHT_02_OPERATOR_REFERENCE_COMPATIBILITY",
                        "ADAPTER_PREFLIGHT_03_SOURCE_DRAFT_COMPATIBILITY",
                        "ADAPTER_PREFLIGHT_04_VALUE_KIND_COMPATIBILITY"
                );
        assertThat(matrix.rules())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
                .containsExactly(
                        "ADAPTER_RULE_01_DISABLED_IMPLEMENTATION",
                        "ADAPTER_RULE_02_METADATA_ONLY_COMPATIBILITY",
                        "ADAPTER_RULE_03_NO_OPERATOR_VALUE_BODY",
                        "ADAPTER_RULE_04_NO_APPROVAL_CAPTURE"
                );
        assertThat(matrix.checks()).contains(
                "value-supply-adapter-preflight-compatibility-metadata-only",
                "value-supply-adapter-preflight-compatibility-no-value-body",
                "value-supply-adapter-preflight-compatibility-no-approval-capture"
        );
        assertThat(matrix.status()).isEqualTo("passed");
    }

    @Test
    void buildsRedactionBoundaryThatBlocksSecretCarryingAdapterInputs() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse boundary =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService()
                        .boundary();

        assertThat(boundary.version()).isEqualTo("Java v666");
        assertThat(boundary.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-redaction-boundary");
        assertThat(boundary.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-redaction-boundary.v1");
        assertThat(boundary.redactionState()).isEqualTo("required-before-adapter");
        assertThat(boundary.readyForOperatorValueSubmission()).isFalse();
        assertThat(boundary.slotCount()).isEqualTo(4);
        assertThat(boundary.ruleCount()).isEqualTo(3);
        assertThat(boundary.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
                .containsExactly(
                        "ADAPTER_PREFLIGHT_05_REDACTION_CLASSIFICATION",
                        "ADAPTER_PREFLIGHT_06_CREDENTIAL_VALUE_BLOCK",
                        "ADAPTER_PREFLIGHT_07_RAW_ENDPOINT_BLOCK",
                        "ADAPTER_PREFLIGHT_08_SECRET_MATERIAL_BLOCK"
                );
        assertThat(boundary.rules())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
                .containsExactly(
                        "ADAPTER_RULE_05_NO_CREDENTIAL_VALUE",
                        "ADAPTER_RULE_06_NO_RAW_ENDPOINT",
                        "ADAPTER_RULE_07_NO_SECRET_MATERIAL"
                );
        assertThat(boundary.checks()).contains(
                "value-supply-adapter-preflight-redaction-credential-values-blocked",
                "value-supply-adapter-preflight-redaction-raw-endpoints-blocked",
                "value-supply-adapter-preflight-redaction-secret-material-blocked"
        );
        assertThat(boundary.status()).isEqualTo("passed");
    }
}
