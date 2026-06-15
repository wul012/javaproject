package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffServiceTests {

  @Test
  void buildsReadOnlyArchiveHandoffFromMaterialSubmissionPrecheck() {
    var response = service().handoff();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1187");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForArchiveHandoff()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v1456");
    assertThat(response.sourceNodeMaterialSubmissionPrecheckVersion()).isEqualTo("Node v1456");
    assertThat(response.sourceJavaMaterialSubmissionPrecheckVersion()).isEqualTo("Java v1162");
    assertThat(response.sourceMaterialSubmissionPrecheckEndpoint())
        .endsWith("candidate-document-material-submission-precheck");
    assertThat(response.sourceLineageCount()).isEqualTo(6);
    assertThat(response.moduleCount()).isEqualTo(5);
    assertThat(response.archiveHandleCount()).isEqualTo(10);
    assertThat(response.policyLockCount()).isEqualTo(10);
    assertThat(response.artifactReferenceCount()).isEqualTo(8);
    assertThat(response.consumerRuleCount()).isEqualTo(10);
    assertThat(response.sourceCheckpointCount()).isEqualTo(10);
    assertThat(response.sourceValidatorCount()).isEqualTo(10);
    assertThat(response.sourceArtifactCount()).isEqualTo(8);
    assertThat(response.sourceGateCount()).isEqualTo(41);
    assertThat(response.gateCount()).isEqualTo(42);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsMaterialSubmissionPayloadAndMutationPathsClosed() {
    var response = service().handoff();

    assertThat(response.realDocumentCount()).isZero();
    assertThat(response.syntheticDocumentCount()).isZero();
    assertThat(response.stagedDocumentCount()).isZero();
    assertThat(response.importedDocumentCount()).isZero();
    assertThat(response.evaluatedDocumentCount()).isZero();
    assertThat(response.acceptedDocumentCount()).isZero();
    assertThat(response.rejectedDocumentCount()).isZero();
    assertThat(response.payloadCount()).isZero();
    assertThat(response.materialSubmissionAccepted()).isFalse();
    assertThat(response.importAllowed()).isFalse();
    assertThat(response.evaluationAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.signedApprovalCaptureAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void checksCarrySourceCountsAndClosedBoundaries() {
    var response = service().handoff();

    assertThat(response.checks())
        .contains(
            "candidate-document-material-submission-precheck-handoff-source-plan-Node v1456",
            "candidate-document-material-submission-precheck-handoff-source-java-precheck-Java v1162",
            "candidate-document-material-submission-precheck-handoff-lineage-count-6",
            "candidate-document-material-submission-precheck-handoff-archive-handle-count-10",
            "candidate-document-material-submission-precheck-handoff-policy-lock-count-10",
            "candidate-document-material-submission-precheck-handoff-source-checkpoint-count-10",
            "candidate-document-material-submission-precheck-handoff-source-validator-count-10",
            "candidate-document-material-submission-precheck-handoff-gate-count-42",
            "candidate-document-material-submission-precheck-handoff-material-submission-disabled",
            "candidate-document-material-submission-precheck-handoff-sibling-mutation-disabled",
            "candidate-document-material-submission-precheck-handoff-service-assembled-from-java-v1162");
  }

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService service() {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
        sourcePrecheckService());
  }

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService
      sourcePrecheckService() {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(
        materialRequestService());
  }

  private OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService() {
    var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoffService =
        new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
    var precheckService =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            requestPackageService, handoffService);
    var intakePacketService =
        new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
    return new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
  }
}
