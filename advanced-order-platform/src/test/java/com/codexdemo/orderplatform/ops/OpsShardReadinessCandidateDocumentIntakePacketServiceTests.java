package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentIntakePacketServiceTests {

    @Test
    void buildsReadOnlyIntakePacketFromSubmissionPrecheck() {
        var response = service().intakePacket();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1125");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForIntakePacket()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1421");
        assertThat(response.sourceNodeSubmissionPrecheckVersion()).isEqualTo("Node v1411");
        assertThat(response.sourceJavaSubmissionPrecheckVersion()).isEqualTo("Java v1117");
        assertThat(response.sourcePrecheckEndpoint()).endsWith("candidate-document-submission-precheck");
        assertThat(response.intakeSlotCount()).isEqualTo(10);
        assertThat(response.passedIntakeSlotCount()).isEqualTo(10);
        assertThat(response.intakeGuardCount()).isEqualTo(10);
        assertThat(response.passedIntakeGuardCount()).isEqualTo(10);
        assertThat(response.coveredSourceCheckpointCount()).isEqualTo(25);
        assertThat(response.coveredSourceValidatorCount()).isEqualTo(25);
        assertThat(response.carriedCandidateFieldCount()).isEqualTo(20);
        assertThat(response.artifactCount()).isEqualTo(8);
        assertThat(response.gateCount()).isEqualTo(35);
        assertThat(response.status()).isEqualTo("passed");
    }

    private OpsShardReadinessCandidateDocumentIntakePacketService service() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        var precheckService = new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
                requestPackageService,
                handoffService);
        return new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
    }
}
