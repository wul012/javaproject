package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport {

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport() {
    }

    static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService handoffService() {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
                sourcePrecheckService());
    }

    static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService sourcePrecheckService() {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(materialRequestService());
    }

    static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse handoff() {
        return handoffService().handoff();
    }

    static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck() {
        return sourcePrecheckService().materialSubmissionPrecheck();
    }

    private static OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        var precheckService = new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
                requestPackageService,
                handoffService);
        var intakePacketService = new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
        return new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
    }
}
