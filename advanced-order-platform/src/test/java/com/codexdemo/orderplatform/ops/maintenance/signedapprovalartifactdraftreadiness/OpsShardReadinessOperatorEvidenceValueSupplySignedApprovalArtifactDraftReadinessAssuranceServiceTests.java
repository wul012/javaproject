package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceServiceTests {

  @Test
  void exposesEvidenceSourceWithoutImport() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService()
            .evidenceSource();

    assertThat(response.version()).isEqualTo("Java v775");
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(3);
    assertThat(response.ownershipRuleCount()).isEqualTo(1);
    assertThat(response.gateCount()).isEqualTo(1);
  }

  @Test
  void exposesRedactionProvenanceWithoutValueBody() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService()
            .redactionProvenance();

    assertThat(response.version()).isEqualTo("Java v776");
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(4);
    assertThat(response.ownershipRuleCount()).isEqualTo(1);
    assertThat(response.gateCount()).isEqualTo(2);
  }

  @Test
  void exposesFailClosedLocksWithoutRuntimeOrSiblingMutation() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService()
            .locks();

    assertThat(response.version()).isEqualTo("Java v777");
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForProductionExecution()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(5);
    assertThat(response.ownershipRuleCount()).isEqualTo(11);
    assertThat(response.gateCount()).isEqualTo(10);
  }

  @Test
  void exposesArchivePlanWithoutFileWrites() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessArchivePlanService()
            .plan();

    assertThat(response.version()).isEqualTo("Java v778");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForManualArtifactDraft()).isFalse();
    assertThat(response.gateCount()).isEqualTo(20);
  }

  @Test
  void closesOutReadinessWithNextStepExplicitPlanBoundary() {
    var response =
        new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService()
            .closeout();

    assertThat(response.version()).isEqualTo("Java v779");
    assertThat(response.readyForManualArtifactDraft()).isFalse();
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.readinessItemCount()).isEqualTo(25);
    assertThat(response.ownershipRuleCount()).isEqualTo(20);
    assertThat(response.gateCount()).isEqualTo(20);
    assertThat(response.checks())
        .contains(
            "signed-approval-artifact-draft-readiness-closeout-versions-v760-v784",
            "signed-approval-artifact-draft-readiness-closeout-no-manual-artifact-draft",
            "signed-approval-artifact-draft-readiness-closeout-next-step-needs-explicit-plan");
  }
}
