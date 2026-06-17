package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationControllerTests {

  @Test
  void exposesCatalogThroughFoundationController() {
    var response = controller().catalog();

    assertThat(response.version()).isEqualTo("Java v744");
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(25);
  }

  @Test
  void exposesCaptureDigestThroughFoundationController() {
    var response = controller().captureDigest();

    assertThat(response.version()).isEqualTo("Java v745");
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
  }

  @Test
  void exposesTemplateReviewThroughFoundationController() {
    var response = controller().templateReview();

    assertThat(response.version()).isEqualTo("Java v746");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
  }

  @Test
  void exposesOperatorFragmentThroughFoundationController() {
    var response = controller().operatorFragment();

    assertThat(response.version()).isEqualTo("Java v747");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
  }

  @Test
  void exposesCapturePolicyThroughFoundationController() {
    var response = controller().capturePolicy();

    assertThat(response.version()).isEqualTo("Java v748");
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService());
  }
}
