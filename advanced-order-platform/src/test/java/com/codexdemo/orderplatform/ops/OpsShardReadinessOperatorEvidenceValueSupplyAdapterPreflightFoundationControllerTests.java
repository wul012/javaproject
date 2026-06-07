package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationControllerWithoutOpeningAdapter() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse catalog = controller.catalog();

        assertThat(catalog.version()).isEqualTo("Java v662");
        assertThat(catalog.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService.ENDPOINT);
        assertThat(catalog.readyForDisabledAdapterPreflight()).isTrue();
        assertThat(catalog.readyForAdapterImplementation()).isFalse();
        assertThat(catalog.ruleCount()).isEqualTo(18);
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void exposesCompatibilityMatrixThroughFoundationControllerWithoutAcceptingValues() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse matrix =
                controller.compatibilityMatrix();

        assertThat(matrix.version()).isEqualTo("Java v664");
        assertThat(matrix.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService.ENDPOINT);
        assertThat(matrix.compatibilityState()).isEqualTo("metadata-only");
        assertThat(matrix.readyForOperatorValueSubmission()).isFalse();
        assertThat(matrix.slotCount()).isEqualTo(4);
        assertThat(matrix.ruleCount()).isEqualTo(4);
        assertThat(matrix.status()).isEqualTo("passed");
    }

    @Test
    void exposesRedactionBoundaryThroughFoundationControllerWithoutSecretMaterial() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse boundary =
                controller.redactionBoundary();

        assertThat(boundary.version()).isEqualTo("Java v666");
        assertThat(boundary.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService.ENDPOINT);
        assertThat(boundary.redactionState()).isEqualTo("required-before-adapter");
        assertThat(boundary.readyForOperatorValueSubmission()).isFalse();
        assertThat(boundary.slotCount()).isEqualTo(4);
        assertThat(boundary.ruleCount()).isEqualTo(3);
        assertThat(boundary.status()).isEqualTo("passed");
    }

    @Test
    void exposesProvenanceBindingThroughFoundationControllerWithoutImportReadiness() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse binding =
                controller.provenanceBinding();

        assertThat(binding.version()).isEqualTo("Java v668");
        assertThat(binding.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService.ENDPOINT);
        assertThat(binding.provenanceState()).isEqualTo("required-before-adapter");
        assertThat(binding.readyForEvidenceImport()).isFalse();
        assertThat(binding.slotCount()).isEqualTo(4);
        assertThat(binding.ruleCount()).isEqualTo(3);
        assertThat(binding.status()).isEqualTo("passed");
    }

    @Test
    void exposesMissingValueRejectionThroughFoundationControllerWithoutDefaults() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse rejection =
                controller.missingValueRejection();

        assertThat(rejection.version()).isEqualTo("Java v670");
        assertThat(rejection.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService.ENDPOINT);
        assertThat(rejection.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(rejection.readyForOperatorValueSubmission()).isFalse();
        assertThat(rejection.slotCount()).isEqualTo(4);
        assertThat(rejection.ruleCount()).isEqualTo(2);
        assertThat(rejection.status()).isEqualTo("passed");
    }

    @Test
    void exposesSourceEvidenceSnapshotThroughFoundationControllerWithoutSiblingImport() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse snapshot =
                controller.sourceEvidenceSnapshot();

        assertThat(snapshot.version()).isEqualTo("Java v672");
        assertThat(snapshot.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService.ENDPOINT);
        assertThat(snapshot.readyForEvidenceImport()).isFalse();
        assertThat(snapshot.readyForRuntimePayload()).isFalse();
        assertThat(snapshot.slotCount()).isEqualTo(4);
        assertThat(snapshot.ruleCount()).isEqualTo(3);
        assertThat(snapshot.status()).isEqualTo("passed");
    }

    private OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService()
        );
    }
}
