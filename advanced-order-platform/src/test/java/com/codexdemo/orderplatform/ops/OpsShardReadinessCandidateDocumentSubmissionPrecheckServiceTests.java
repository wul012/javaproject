package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentSubmissionPrecheckServiceTests {

    @Test
    void buildsReadOnlySubmissionPrecheckFromRequestPackageAndHandoff() {
        var response = service().precheck();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1113");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForSubmissionPrecheck()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1411");
        assertThat(response.sourceNodeRequestPackageVersion()).isEqualTo("Node v1386");
        assertThat(response.sourceJavaRequestPackageVersion()).isEqualTo("Java v1081");
        assertThat(response.sourceJavaHandoffVersion()).isEqualTo("Java v1107");
        assertThat(response.checkpointCount()).isEqualTo(25);
        assertThat(response.passedCheckpointCount()).isEqualTo(25);
        assertThat(response.validatorCount()).isEqualTo(25);
        assertThat(response.passedValidatorCount()).isEqualTo(25);
        assertThat(response.requestedCandidateFieldCount()).isEqualTo(20);
        assertThat(response.artifactCount()).isEqualTo(8);
        assertThat(response.gateCount()).isEqualTo(40);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsDocumentPayloadAndMutationPathsClosed() {
        var response = service().precheck();

        assertThat(response.realDocumentCount()).isZero();
        assertThat(response.syntheticDocumentCount()).isZero();
        assertThat(response.stagedDocumentCount()).isZero();
        assertThat(response.importedDocumentCount()).isZero();
        assertThat(response.evaluatedDocumentCount()).isZero();
        assertThat(response.acceptedDocumentCount()).isZero();
        assertThat(response.rejectedDocumentCount()).isZero();
        assertThat(response.payloadCount()).isZero();
        assertThat(response.importAllowed()).isFalse();
        assertThat(response.evaluationAllowed()).isFalse();
        assertThat(response.approvalGrantAllowed()).isFalse();
        assertThat(response.signedApprovalCaptureAllowed()).isFalse();
        assertThat(response.runtimePayloadAllowed()).isFalse();
        assertThat(response.writeAllowed()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
    }

    @Test
    void checkpointsAndValidatorsPreserveSourceBoundaries() {
        var response = service().precheck();

        assertThat(response.checkpoints())
                .extracting(OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint::category)
                .contains("request-item", "consumer-rule");
        assertThat(response.checkpoints())
                .extracting(OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Checkpoint::code)
                .contains(
                        "request-source-readiness-request",
                        "request-approval-runtime-write-freeze-request",
                        "consumer-do-not-import-payload",
                        "consumer-do-not-mutate-siblings");
        assertThat(response.validators())
                .allSatisfy(validator -> {
                    assertThat(validator.code()).endsWith("-validator");
                    assertThat(validator.rejectionCode()).startsWith("reject-submission-precheck-");
                    assertThat(validator.enforcement()).isEqualTo("fail-closed");
                    assertThat(validator.status()).isEqualTo("passed");
                });
    }

    private OpsShardReadinessCandidateDocumentSubmissionPrecheckService service() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        return new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(requestPackageService, handoffService);
    }
}
