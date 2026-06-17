package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationControllerTests {

  @Test
  void exposesCatalogThroughFoundationController() {
    var response = controller().catalog();

    assertThat(response.version()).isEqualTo("Java v714");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.inputCount()).isEqualTo(25);
    assertThat(response.attestationCount()).isEqualTo(25);
    assertThat(response.policyCount()).isEqualTo(20);
  }

  @Test
  void exposesTemplateDigestThroughFoundationController() {
    var response = controller().templateDigest();

    assertThat(response.version()).isEqualTo("Java v716");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService
                .ENDPOINT);
    assertThat(response.sourceTemplateVersion()).isEqualTo("Node v1036");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
  }

  @Test
  void exposesReviewDigestThroughFoundationController() {
    var response = controller().reviewDigest();

    assertThat(response.version()).isEqualTo("Java v718");
    assertThat(response.sourceApprovalPacketReviewVersion()).isEqualTo("Node v1011");
    assertThat(response.readyForApprovalGrant()).isFalse();
  }

  @Test
  void exposesOperatorInputThroughFoundationController() {
    var response = controller().operatorInput();

    assertThat(response.version()).isEqualTo("Java v720");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.inputCount()).isEqualTo(2);
  }

  @Test
  void exposesTimingWindowThroughFoundationController() {
    var response = controller().timingWindow();

    assertThat(response.version()).isEqualTo("Java v722");
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.inputCount()).isEqualTo(2);
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTemplateDigestBindingService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService());
  }
}
