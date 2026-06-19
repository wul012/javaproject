package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness.*;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationControllerTests {

  @Test
  void exposesCatalogThroughFoundationController() {
    var response = controller().catalog();

    assertThat(response.version()).isEqualTo("Java v871");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
                .ENDPOINT);
    assertThat(response.requirementCount()).isEqualTo(25);
    assertThat(response.blockerCount()).isEqualTo(25);
  }

  @Test
  void exposesDigestPinsThroughFoundationController() {
    var response = controller().digestPins();

    assertThat(response.version()).isEqualTo("Java v872");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService
                .ENDPOINT);
    assertThat(response.readyForSignedDraftText()).isFalse();
  }

  @Test
  void exposesOperatorRequirementsThroughFoundationController() {
    var response = controller().operatorRequirements();

    assertThat(response.version()).isEqualTo("Java v873");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService
                .ENDPOINT);
    assertThat(response.readyForApprovalGrant()).isFalse();
  }

  @Test
  void exposesSignatureRequirementsThroughFoundationController() {
    var response = controller().signatureRequirements();

    assertThat(response.version()).isEqualTo("Java v874");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService
                .ENDPOINT);
    assertThat(response.readyForSignatureCapture()).isFalse();
  }

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationController
      controller() {
    return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationController(
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService(),
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService());
  }
}
