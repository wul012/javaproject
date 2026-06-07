package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationControllerWithoutOpeningAdapter() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController controller =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController(
                        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService()
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
}
