package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PrecheckHandoffCatalogTests {

  @Test
  void sourceLineagePinsNodePlanAndJavaPrecheck() {
    var sourcePrecheck = sourcePrecheckService().materialSubmissionPrecheck();
    var evidence = PrecheckHandoffCatalog.from(sourcePrecheck);
    var lineage = evidence.sourceLineage();

    assertThat(lineage)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .SourceLineage
                ::order)
        .containsExactly(1, 2, 3, 4, 5, 6);
    assertThat(lineage)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .SourceLineage
                ::code)
        .containsExactly(
            "node-material-submission-precheck-plan",
            "java-material-submission-precheck-route",
            "java-material-submission-precheck-profile",
            "java-material-submission-precheck-checkpoints",
            "java-material-submission-precheck-validators",
            "java-material-submission-precheck-artifacts-and-gates");
    assertThat(lineage)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .SourceLineage
                ::status)
        .containsOnly("passed");
  }

  @Test
  void moduleCatalogStaysShortAndOrdered() {
    var sourcePrecheck = sourcePrecheckService().materialSubmissionPrecheck();
    var modules = PrecheckHandoffCatalog.from(sourcePrecheck).modules();

    assertThat(modules)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry
                ::order)
        .containsExactly(214, 215, 216, 217, 218);
    assertThat(modules)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry
                ::code)
        .contains(
            "material-submission-precheck-handoff-types",
            "material-submission-precheck-handoff-source",
            "material-submission-precheck-handoff-route");
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
