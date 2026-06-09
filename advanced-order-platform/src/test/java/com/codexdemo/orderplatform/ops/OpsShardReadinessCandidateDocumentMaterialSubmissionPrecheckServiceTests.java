package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckServiceTests {

    @Test
    void buildsReadOnlySubmissionPrecheckFromMaterialRequest() {
        var response = service().materialSubmissionPrecheck();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1162");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForMaterialSubmissionPrecheck()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1456");
        assertThat(response.sourceNodeMaterialRequestVersion()).isEqualTo("Node v1446");
        assertThat(response.sourceJavaMaterialRequestVersion()).isEqualTo("Java v1152");
        assertThat(response.sourceMaterialRequestEndpoint()).endsWith("candidate-document-material-request");
        assertThat(response.moduleCount()).isEqualTo(5);
        assertThat(response.checkpointCount()).isEqualTo(10);
        assertThat(response.passedCheckpointCount()).isEqualTo(10);
        assertThat(response.validatorCount()).isEqualTo(10);
        assertThat(response.passedValidatorCount()).isEqualTo(10);
        assertThat(response.sourceRequestItemCount()).isEqualTo(25);
        assertThat(response.sourceAcceptanceCheckCount()).isEqualTo(25);
        assertThat(response.requiredMaterialFieldCount()).isEqualTo(20);
        assertThat(response.submissionMaterialFieldCount()).isEqualTo(20);
        assertThat(response.artifactCount()).isEqualTo(8);
        assertThat(response.gateCount()).isEqualTo(41);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsMaterialSubmissionPayloadAndMutationPathsClosed() {
        var response = service().materialSubmissionPrecheck();

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
    void checkpointsAndValidatorsGroupMaterialRequestItems() {
        var response = service().materialSubmissionPrecheck();

        assertThat(response.checkpoints())
                .extracting(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint::code)
                .contains(
                        "material-source-package-submission-checkpoint",
                        "reviewer-identity-submission-checkpoint",
                        "runtime-import-freeze-submission-checkpoint",
                        "closeout-archive-submission-checkpoint");
        assertThat(response.checkpoints())
                .flatExtracting(
                        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint::sourceRequestCodes)
                .hasSize(25)
                .contains(
                        "material-slot-candidate-intake-slot-1",
                        "reviewer-identity-request",
                        "redaction-archive-closeout-request");
        assertThat(response.checkpoints())
                .flatExtracting(
                        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.SubmissionCheckpoint::sourceAcceptanceCheckCodes)
                .hasSize(25)
                .contains("reviewer-identity-request-acceptance-check");
        assertThat(response.validators())
                .allSatisfy(validator -> {
                    assertThat(validator.code()).endsWith("-validator");
                    assertThat(validator.rejectionCode()).startsWith("reject-material-submission-precheck-");
                    assertThat(validator.enforcement()).isEqualTo("fail-closed");
                    assertThat(validator.status()).isEqualTo("passed");
                });
    }

    @Test
    void artifactsGatesAndChecksRemainEvidenceOnly() {
        var response = service().materialSubmissionPrecheck();

        assertThat(response.artifacts())
                .extracting(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact::reference)
                .allSatisfy(reference -> assertThat(reference).startsWith("e/1162/"));
        assertThat(response.gates())
                .hasSize(41)
                .first()
                .isEqualTo("candidate-document-material-submission-precheck-no-material-gate-1");
        assertThat(response.checks())
                .contains(
                        "candidate-document-material-submission-precheck-source-plan-Node v1456",
                        "candidate-document-material-submission-precheck-source-java-material-request-Java v1152",
                        "candidate-document-material-submission-precheck-checkpoint-count-10",
                        "candidate-document-material-submission-precheck-validator-count-10",
                        "candidate-document-material-submission-precheck-source-request-item-count-25",
                        "candidate-document-material-submission-precheck-material-submission-disabled",
                        "candidate-document-material-submission-precheck-import-disabled",
                        "candidate-document-material-submission-precheck-sibling-mutation-disabled",
                        "candidate-document-material-submission-precheck-service-assembled-from-material-request");
    }

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService service() {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(materialRequestService());
    }

    private OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        var precheckService = new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
                requestPackageService,
                handoffService);
        var intakePacketService = new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
        return new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
    }
}
