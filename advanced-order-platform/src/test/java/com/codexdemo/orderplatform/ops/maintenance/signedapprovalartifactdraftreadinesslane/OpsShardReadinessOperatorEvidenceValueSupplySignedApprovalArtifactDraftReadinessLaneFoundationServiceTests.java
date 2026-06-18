package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationServiceTests {

  @Test
  void exposesCatalogWithoutManualPackageAuthoring() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogService()
            .catalog();

    assertThat(response.version()).isEqualTo("Java v821");
    assertThat(response.sourcePlan()).isEqualTo("Node v1136");
    assertThat(response.sourceNodeDraftPreflightVersion()).isEqualTo("Node v1111");
    assertThat(response.sourceJavaDraftPreflightVersion()).isEqualTo("Java v809");
    assertThat(response.readyForReadinessLaneCloseout()).isTrue();
    assertThat(response.readyForManualDraft()).isFalse();
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.laneCount()).isEqualTo(25);
    assertThat(response.blockerCount()).isEqualTo(25);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void exposesDigestPinsWithoutDraftMaterialization() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneDigestPinService()
            .digestPins();

    assertThat(response.version()).isEqualTo("Java v822");
    assertThat(response.manualPackageState()).isEqualTo("not-authored");
    assertThat(response.draftMaterializationState()).isEqualTo("not-materialized");
    assertThat(response.laneCount()).isEqualTo(4);
    assertThat(response.blockerCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesOperatorReviewWithoutGrantOrRuntime() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneOperatorReviewService()
            .operatorReview();

    assertThat(response.version()).isEqualTo("Java v823");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.laneCount()).isEqualTo(4);
    assertThat(response.blockerCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesSignatureReviewWithoutSignatureCapture() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSignatureReviewService()
            .signatureReview();

    assertThat(response.version()).isEqualTo("Java v824");
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.laneCount()).isEqualTo(5);
    assertThat(response.blockerCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(3);
  }
}
