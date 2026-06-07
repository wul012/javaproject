package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationControllerWithoutOpeningAdapter() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
                        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService(),
                        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService()
                );

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
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
                        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService(),
                        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService()
                );

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
}
