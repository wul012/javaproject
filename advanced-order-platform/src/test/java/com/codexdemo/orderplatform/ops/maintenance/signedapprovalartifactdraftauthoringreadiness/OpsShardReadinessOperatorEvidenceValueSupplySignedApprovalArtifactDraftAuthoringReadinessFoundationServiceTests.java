package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationServiceTests {

  @Test
  void exposesCatalogWithoutAuthoringArtifactCreation() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService()
            .catalog();

    assertThat(response.version()).isEqualTo("Java v871");
    assertThat(response.sourcePlan()).isEqualTo("Node v1186");
    assertThat(response.sourceNodeReviewPackagePreflightVersion()).isEqualTo("Node v1161");
    assertThat(response.sourceJavaReviewPackagePreflightVersion()).isEqualTo("Java v859");
    assertThat(response.readyForAuthoringReadiness()).isTrue();
    assertThat(response.readyForHumanDraftAuthoring()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.requirementCount()).isEqualTo(25);
    assertThat(response.blockerCount()).isEqualTo(25);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void exposesDigestPinsWithoutInstructionGeneration() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService()
            .digestPins();

    assertThat(response.version()).isEqualTo("Java v872");
    assertThat(response.authoringReadinessState()).isEqualTo("requirement-map-only");
    assertThat(response.authoringArtifactState()).isEqualTo("not-created");
    assertThat(response.requirementCount()).isEqualTo(4);
    assertThat(response.blockerCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesOperatorRequirementsWithoutCredentialCapture() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService()
            .operatorRequirements();

    assertThat(response.version()).isEqualTo("Java v873");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.requirementCount()).isEqualTo(4);
    assertThat(response.blockerCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesSignatureRequirementsWithoutSignatureOrStatementText() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService()
            .signatureRequirements();

    assertThat(response.version()).isEqualTo("Java v874");
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.requirementCount()).isEqualTo(5);
    assertThat(response.blockerCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(3);
  }
}
