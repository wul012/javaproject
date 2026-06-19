package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightFoundationServiceTests {

  @Test
  void exposesCatalogWithoutReviewArtifactCreation() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService()
            .catalog();

    assertThat(response.version()).isEqualTo("Java v846");
    assertThat(response.sourcePlan()).isEqualTo("Node v1161");
    assertThat(response.sourceNodeReadinessLaneVersion()).isEqualTo("Node v1136");
    assertThat(response.sourceJavaReadinessLaneVersion()).isEqualTo("Java v834");
    assertThat(response.readyForReviewPackagePreflight()).isTrue();
    assertThat(response.readyForHumanDraftAuthoring()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.slotCount()).isEqualTo(25);
    assertThat(response.guardCount()).isEqualTo(25);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void exposesDigestPinsWithoutPackageMaterialization() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService()
            .digestPins();

    assertThat(response.version()).isEqualTo("Java v847");
    assertThat(response.reviewPackageState()).isEqualTo("slot-map-only");
    assertThat(response.reviewArtifactState()).isEqualTo("not-created");
    assertThat(response.slotCount()).isEqualTo(4);
    assertThat(response.guardCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesOperatorPackageWithoutGrantOrAdapterEnablement() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService()
            .operatorPackage();

    assertThat(response.version()).isEqualTo("Java v848");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.slotCount()).isEqualTo(4);
    assertThat(response.guardCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesSignaturePackageWithoutSignatureOrStatementText() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSignaturePackageService()
            .signaturePackage();

    assertThat(response.version()).isEqualTo("Java v849");
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.slotCount()).isEqualTo(5);
    assertThat(response.guardCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(3);
  }
}
