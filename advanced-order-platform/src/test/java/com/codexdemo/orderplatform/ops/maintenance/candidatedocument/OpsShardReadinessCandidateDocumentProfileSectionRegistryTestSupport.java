package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

public final class OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport {

  private OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport() {}

  public static OpsShardReadinessCandidateDocumentProfileSectionRegistryService service() {
    var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoffService =
        new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
    var submissionPrecheckService =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            requestPackageService, handoffService);
    var intakePacketService =
        new OpsShardReadinessCandidateDocumentIntakePacketService(submissionPrecheckService);
    var materialRequestService =
        new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
    var materialSubmissionPrecheckService =
        new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(
            materialRequestService);
    return new OpsShardReadinessCandidateDocumentProfileSectionRegistryService(
        requestPackageService,
        submissionPrecheckService,
        intakePacketService,
        materialRequestService,
        materialSubmissionPrecheckService);
  }

  public static OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse registry() {
    return service().registry();
  }
}
