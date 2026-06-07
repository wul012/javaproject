package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalogTests {

    @Test
    void listsTwentyFiveCaptureInputsWithoutOpeningCapture() {
        var inputs = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .allInputs();

        assertThat(inputs).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog.INPUT_COUNT);
        assertThat(inputs.stream().map(input -> input.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(inputs).allSatisfy(input -> {
            assertThat(input.status()).isEqualTo("passed");
            assertThat(input.inputRequirement()).isNotBlank();
            assertThat(input.blockedReason()).isNotBlank();
            assertThat(input.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
        assertThat(inputs.get(0).code()).contains("REQUEST_ID");
        assertThat(inputs.get(24).code()).contains("CLOSEOUT_BOUNDARY");
    }
}
