package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentHandoffServiceTests {

    @Test
    void buildsReadOnlyCandidateDocumentHandoffFromRequestPackage() {
        var response = service().handoff();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1092");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForCandidateDocumentHandoff()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1386");
        assertThat(response.sourceNodeCandidateIntakeVersion()).isEqualTo("Node v1371");
        assertThat(response.sourceJavaCandidateIntakeVersion()).isEqualTo("Java v1079");
        assertThat(response.sourceRequestPackageVersion()).isEqualTo("Java v1081");
        assertThat(response.sourceRequestPackageEndpoint()).endsWith("candidate-document-request-package");
        assertThat(response.sourceLineageCount()).isEqualTo(6);
        assertThat(response.moduleCount()).isEqualTo(5);
        assertThat(response.artifactHandleCount()).isEqualTo(15);
        assertThat(response.policyLockCount()).isEqualTo(15);
        assertThat(response.archiveEntryCount()).isEqualTo(8);
        assertThat(response.consumerRuleCount()).isEqualTo(10);
        assertThat(response.gateCount()).isEqualTo(25);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsDocumentPayloadAndMutationPathsClosed() {
        var response = service().handoff();

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

    private OpsShardReadinessCandidateDocumentHandoffService service() {
        return new OpsShardReadinessCandidateDocumentHandoffService(
                new OpsShardReadinessCandidateDocumentRequestPackageService());
    }
}
