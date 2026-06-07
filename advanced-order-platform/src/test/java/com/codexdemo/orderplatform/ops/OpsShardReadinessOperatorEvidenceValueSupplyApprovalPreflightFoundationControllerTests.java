package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationControllerWithoutCapturingApproval() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController controller =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController(
                        new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService()
                );

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse catalog =
                controller.catalog();

        assertThat(catalog.version()).isEqualTo("Java v688");
        assertThat(catalog.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService.ENDPOINT);
        assertThat(catalog.readyForApprovalPreflight()).isTrue();
        assertThat(catalog.readyForSignedApprovalCapture()).isFalse();
        assertThat(catalog.readyForApprovalGrant()).isFalse();
        assertThat(catalog.itemCount()).isEqualTo(25);
        assertThat(catalog.policyCount()).isEqualTo(20);
        assertThat(catalog.status()).isEqualTo("passed");
    }
}
