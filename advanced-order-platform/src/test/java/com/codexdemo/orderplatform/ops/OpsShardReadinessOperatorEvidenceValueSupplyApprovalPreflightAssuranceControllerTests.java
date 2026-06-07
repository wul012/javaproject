package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceControllerTests {

    @Test
    void exposesValueRejectionThroughAssuranceControllerWithoutImportReadiness() {
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController controller =
                new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController(
                        new OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService()
                );

        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse rejection =
                controller.valueRejection();

        assertThat(rejection.version()).isEqualTo("Java v698");
        assertThat(rejection.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService.ENDPOINT);
        assertThat(rejection.acceptedValueState()).isEqualTo("not-accepted");
        assertThat(rejection.malformedValueState()).isEqualTo("rejected");
        assertThat(rejection.readyForOperatorValueSubmission()).isFalse();
        assertThat(rejection.readyForEvidenceImport()).isFalse();
        assertThat(rejection.itemCount()).isEqualTo(3);
        assertThat(rejection.policyCount()).isEqualTo(3);
        assertThat(rejection.status()).isEqualTo("passed");
    }
}
