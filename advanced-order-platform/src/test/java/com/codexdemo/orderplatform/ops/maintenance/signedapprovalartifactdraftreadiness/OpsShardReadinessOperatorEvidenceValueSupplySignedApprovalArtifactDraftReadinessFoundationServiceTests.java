package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationServiceTests {

  @Test
  void exposesCatalogWithoutManualArtifactDraftReadiness() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService()
            .catalog();

    assertThat(response.version()).isEqualTo("Java v771");
    assertThat(response.sourceArtifactPreflightVersion()).isEqualTo("Java v759");
    assertThat(response.readyForDraftReadiness()).isTrue();
    assertThat(response.readyForManualArtifactDraft()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(25);
    assertThat(response.ownershipRuleCount()).isEqualTo(20);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void exposesDigestChainWithoutMaterialization() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService()
            .digestChain();

    assertThat(response.version()).isEqualTo("Java v772");
    assertThat(response.readyForManualArtifactDraft()).isFalse();
    assertThat(response.artifactMaterializationState()).isEqualTo("not-materialized");
    assertThat(response.readinessItemCount()).isEqualTo(4);
    assertThat(response.ownershipRuleCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesOperatorWindowWithoutGrantOrWriteRoute() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService()
            .operatorWindow();

    assertThat(response.version()).isEqualTo("Java v773");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(4);
    assertThat(response.ownershipRuleCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesSignatureStatementWithoutSignatureMaterial() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService()
            .signatureStatement();

    assertThat(response.version()).isEqualTo("Java v774");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(5);
    assertThat(response.ownershipRuleCount()).isEqualTo(2);
    assertThat(response.gateCount()).isEqualTo(3);
  }
}
