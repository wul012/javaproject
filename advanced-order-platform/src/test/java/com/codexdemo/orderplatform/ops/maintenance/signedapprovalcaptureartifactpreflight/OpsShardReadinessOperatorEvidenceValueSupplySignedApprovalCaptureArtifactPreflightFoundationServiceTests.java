package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationServiceTests {

  @Test
  void exposesCatalogWithoutArtifactDraftReadiness() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService()
            .catalog();

    assertThat(response.version()).isEqualTo("Java v744");
    assertThat(response.sourcePlan()).isEqualTo("Node v1086");
    assertThat(response.sourceJavaCapturePreflightVersion()).isEqualTo("Java v734");
    assertThat(response.readyForArtifactPreflight()).isTrue();
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(25);
    assertThat(response.sealCount()).isEqualTo(25);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void bindsCaptureDigestWithoutMaterialization() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService()
            .binding();

    assertThat(response.version()).isEqualTo("Java v745");
    assertThat(response.endpoint())
        .isEqualTo(
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService
                .ENDPOINT);
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
    assertThat(response.sealCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(2);
    assertThat(response.checks())
        .contains(
            "signed-approval-capture-artifact-preflight-capture-digest-no-artifact-materialization");
  }

  @Test
  void bindsTemplateAndReviewDigestsWithoutApprovalGrant() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightTemplateReviewDigestService()
            .binding();

    assertThat(response.version()).isEqualTo("Java v746");
    assertThat(response.sourceTemplateVersion()).isEqualTo("Node v1036");
    assertThat(response.sourceApprovalPacketReviewVersion()).isEqualTo("Node v1011");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
    assertThat(response.sealCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesOperatorFragmentsWithoutCredentials() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService()
            .fragments();

    assertThat(response.version()).isEqualTo("Java v747");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
    assertThat(response.sealCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(1);
  }

  @Test
  void exposesCapturePolicyFragmentsWithoutWriteRoute() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService()
            .policy();

    assertThat(response.version()).isEqualTo("Java v748");
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.readyForProductionExecution()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(2);
    assertThat(response.sealCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(2);
  }
}
