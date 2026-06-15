package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchivePolicyTests {

  @Test
  void archiveHandlesMapOneToOneToSourceCheckpoints() {
    var sourcePrecheck = sourcePrecheckService().materialSubmissionPrecheck();
    var archiveHandles =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchiveCatalog
            .archiveHandles(sourcePrecheck);

    assertThat(archiveHandles).hasSize(10);
    assertThat(archiveHandles)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ArchiveHandle
                ::checkpointCode)
        .containsExactlyElementsOf(
            sourcePrecheck.checkpoints().stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
                            .SubmissionCheckpoint
                        ::code)
                .toList());
    assertThat(archiveHandles)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ArchiveHandle
                ::reference)
        .allSatisfy(reference -> assertThat(reference).startsWith("e/1187/archive/"));
  }

  @Test
  void policyLocksMapOneToOneToSourceValidators() {
    var sourcePrecheck = sourcePrecheckService().materialSubmissionPrecheck();
    var policyLocks =
        OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffPolicyCatalog
            .policyLocks(sourcePrecheck);

    assertThat(policyLocks).hasSize(10);
    assertThat(policyLocks)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock
                ::validatorCode)
        .containsExactlyElementsOf(
            sourcePrecheck.validators().stream()
                .map(
                    OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Validator
                        ::code)
                .toList());
    assertThat(policyLocks)
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock
                ::enforcement)
        .containsOnly("fail-closed");
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
