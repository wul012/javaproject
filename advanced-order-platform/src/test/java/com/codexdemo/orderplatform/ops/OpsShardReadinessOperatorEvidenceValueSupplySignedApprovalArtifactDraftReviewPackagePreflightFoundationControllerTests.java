package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSignaturePackageService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationControllerTests {

  @Test
  void exposesFoundationRoutesWithoutDraftArtifactCreation() {
    var controller = controller();

    assertThat(controller.catalog().version()).isEqualTo("Java v846");
    assertThat(controller.digestPins().version()).isEqualTo("Java v847");
    assertThat(controller.operatorPackage().version()).isEqualTo("Java v848");
    assertThat(controller.signaturePackage().version()).isEqualTo("Java v849");
    assertThat(controller.catalog().readyForHumanDraftAuthoring()).isFalse();
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSignaturePackageService());
  }
}
