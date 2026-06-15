package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactConsumerGateTests {

  @Test
  void artifactReferencesMirrorSourceArtifactsIntoHandoffArchive() {
    var sourcePrecheck = sourcePrecheckService().materialSubmissionPrecheck();
    var references =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArtifactCatalog
            .artifactReferences(sourcePrecheck);

    assertThat(references).hasSize(8);
    assertThat(references)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ArtifactReference
                ::sourceReference)
        .containsExactlyElementsOf(
            sourcePrecheck.artifacts().stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact
                        ::reference)
                .toList());
    assertThat(references)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ArtifactReference
                ::archiveReference)
        .allSatisfy(reference -> assertThat(reference).startsWith("e/1187/artifacts/"));
  }

  @Test
  void consumerRulesAllowOnlyArchiveReads() {
    var rules =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffConsumerCatalog
            .consumerRules(sourcePrecheckService().materialSubmissionPrecheck());

    assertThat(rules).hasSize(10);
    assertThat(rules)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule
                ::allowedAction)
        .containsOnly("read archive handle and policy lock");
    assertThat(rules)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule
                ::blockedAction)
        .allSatisfy(action -> assertThat(action).contains("submit", "import", "write"));
  }

  @Test
  void handoffGatesAddOneCloseoutGateAfterSourcePrecheck() {
    assertThat(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffGateCatalog.gates())
        .hasSize(42)
        .first()
        .isEqualTo("candidate-document-material-submission-precheck-handoff-no-material-gate-1");
    assertThat(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffGateCatalog.gates())
        .last()
        .isEqualTo("candidate-document-material-submission-precheck-handoff-no-material-gate-42");
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
