package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceServiceTests {

  @Test
  void exposesEvidencePackageWithoutImport() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService()
            .evidencePackage();

    assertThat(response.version()).isEqualTo("Java v850");
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.slotCount()).isEqualTo(3);
    assertThat(response.guardCount()).isEqualTo(3);
    assertThat(response.gateCount()).isEqualTo(1);
    assertThat(response.checks())
        .contains("signed-approval-artifact-draft-review-package-preflight-no-evidence-import");
  }

  @Test
  void exposesValuePolicyPackageWithoutValueBody() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService()
            .valuePolicyPackage();

    assertThat(response.version()).isEqualTo("Java v851");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.slotCount()).isEqualTo(4);
    assertThat(response.guardCount()).isEqualTo(4);
    assertThat(response.gateCount()).isEqualTo(2);
    assertThat(response.checks())
        .contains("signed-approval-artifact-draft-review-package-preflight-no-value-body");
  }

  @Test
  void exposesEmbargoPackageWithoutRuntimeOrSiblingMutation() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService()
            .embargoPackage();

    assertThat(response.version()).isEqualTo("Java v852");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.slotCount()).isEqualTo(5);
    assertThat(response.guardCount()).isEqualTo(5);
    assertThat(response.gateCount()).isEqualTo(10);
  }

  @Test
  void exposesDraftAuthoringGateWithoutDraftText() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService()
            .draftAuthoringGate();

    assertThat(response.version()).isEqualTo("Java v853");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForHumanDraftAuthoring()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.checks())
        .contains(
            "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-no-signed-draft-text",
            "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-no-file-write",
            "signed-approval-artifact-draft-review-package-preflight-draft-authoring-gate-no-service-start");
  }

  @Test
  void closesOutReviewPackagePreflightWithHumanDraftBoundary() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService()
            .closeout();

    assertThat(response.version()).isEqualTo("Java v854");
    assertThat(response.readyForHumanDraftAuthoring()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.slotCount()).isEqualTo(25);
    assertThat(response.guardCount()).isEqualTo(25);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.checks())
        .contains(
            "signed-approval-artifact-draft-review-package-preflight-closeout-versions-v835-v859",
            "signed-approval-artifact-draft-review-package-preflight-closeout-source-node-v1161",
            "signed-approval-artifact-draft-review-package-preflight-closeout-source-node-readiness-v1136",
            "signed-approval-artifact-draft-review-package-preflight-closeout-source-java-readiness-v834",
            "signed-approval-artifact-draft-review-package-preflight-closeout-no-review-artifact-creation",
            "signed-approval-artifact-draft-review-package-preflight-closeout-next-step-human-draft-artifact-plan");
  }
}
