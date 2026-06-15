package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalogTests {

  @Test
  void modulesRemainShortOrderedAndEvidenceOnly() {
    assertThat(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.modules())
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry::order)
        .containsExactly(209, 210, 211, 212, 213);
    assertThat(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.modules())
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.ModuleEntry
                ::status)
        .containsOnly("passed");
  }

  @Test
  void checkpointsPartitionAllSourceRequestsAndAcceptanceChecks() {
    var sourceRequest = materialRequestService().materialRequest();
    var checkpoints =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.checkpoints(
            sourceRequest);

    assertThat(checkpoints).hasSize(10);
    assertThat(checkpoints)
        .extracting(checkpoint -> checkpoint.sourceRequestCodes().size())
        .containsExactly(3, 2, 3, 2, 3, 2, 3, 2, 3, 2);
    assertThat(checkpoints)
        .extracting(checkpoint -> checkpoint.sourceAcceptanceCheckCodes().size())
        .containsExactly(3, 2, 3, 2, 3, 2, 3, 2, 3, 2);
    assertThat(flattenRequestCodes(checkpoints))
        .containsExactlyElementsOf(
            sourceRequest.requestItems().stream()
                .map(OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem::code)
                .toList());
    assertThat(flattenAcceptanceCodes(checkpoints))
        .containsExactlyElementsOf(
            sourceRequest.acceptanceChecks().stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialRequestResponse.AcceptanceCheck::code)
                .toList());
  }

  @Test
  void validatorsMapOneToOneToCheckpoints() {
    var checkpoints =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.checkpoints(
            materialRequestService().materialRequest());
    var validators =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckCatalog.validators(checkpoints);

    assertThat(validators).hasSize(10);
    assertThat(validators)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator
                ::checkpointCode)
        .containsExactlyElementsOf(
            checkpoints.stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                            .SubmissionCheckpoint
                        ::code)
                .toList());
    assertThat(validators)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator
                ::enforcement)
        .containsOnly("fail-closed");
  }

  private List<String> flattenRequestCodes(
      List<
              OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                  .SubmissionCheckpoint>
          checkpoints) {
    return checkpoints.stream()
        .flatMap(checkpoint -> checkpoint.sourceRequestCodes().stream())
        .toList();
  }

  private List<String> flattenAcceptanceCodes(
      List<
              OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                  .SubmissionCheckpoint>
          checkpoints) {
    return checkpoints.stream()
        .flatMap(checkpoint -> checkpoint.sourceAcceptanceCheckCodes().stream())
        .toList();
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
