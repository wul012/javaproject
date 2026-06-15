package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

public final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport {

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport() {}

  public static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService
      handoffService() {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
        sourcePrecheckService());
  }

  public static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService
      sourcePrecheckService() {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(
        materialRequestService());
  }

  public static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
      handoff() {
    return handoffService().handoff();
  }

  public static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse
      sourcePrecheck() {
    return sourcePrecheckService().materialSubmissionPrecheck();
  }

  private static OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService() {
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
