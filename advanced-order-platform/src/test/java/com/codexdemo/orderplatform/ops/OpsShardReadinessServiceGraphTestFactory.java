package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessServiceGraphTestFactory {

    private OpsShardReadinessServiceGraphTestFactory() {
    }

    static OpsShardReadinessReadOnlyEvidenceCatalogService readOnlyEvidenceCatalogService() {
        OpsShardReadinessEvidenceIndexService indexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService verificationService =
                new OpsShardReadinessEvidenceVerificationService(indexService);
        OpsShardReadinessEvidenceHandoffService handoffService =
                new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
        OpsShardReadinessEchoService echoService = new OpsShardReadinessEchoService(
                new OpsShardReadinessService(),
                new OpsShardReadinessHardeningService(),
                indexService,
                handoffService
        );
        return new OpsShardReadinessReadOnlyEvidenceCatalogService(echoService, passEvidenceCloseoutService(
                handoffService
        ));
    }

    static OpsShardReadinessReadOnlyEvidenceCatalogHandoffService readOnlyEvidenceCatalogHandoffService() {
        return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(readOnlyEvidenceCatalogService());
    }

    static OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService
            readOnlyEvidenceCatalogHandoffVerificationService() {
        OpsShardReadinessReadOnlyEvidenceCatalogService catalogService = readOnlyEvidenceCatalogService();
        return new OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService(
                catalogService,
                new OpsShardReadinessReadOnlyEvidenceCatalogHandoffService(catalogService)
        );
    }

    static OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService passEvidenceCloseoutService() {
        OpsShardReadinessEvidenceIndexService indexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService verificationService =
                new OpsShardReadinessEvidenceVerificationService(indexService);
        OpsShardReadinessEvidenceHandoffService handoffService =
                new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
        return passEvidenceCloseoutService(handoffService);
    }

    private static OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService passEvidenceCloseoutService(
            OpsShardReadinessEvidenceHandoffService handoffService
    ) {
        return new OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService(liveReadGateService(handoffService));
    }

    private static OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService(
            OpsShardReadinessEvidenceHandoffService handoffService
    ) {
        OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService =
                new OpsShardReadinessActiveShardPlanHandoffService(handoffService);
        OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService =
                new OpsShardReadinessLiveReadGatePlanService(activeShardPlanHandoffService);
        OpsShardReadinessOperatorServiceLifecycleService operatorLifecycleService =
                new OpsShardReadinessOperatorServiceLifecycleService(liveReadGatePlanService);
        OpsShardReadinessDeclaredOperatorLifecycleService declaredLifecycleService =
                new OpsShardReadinessDeclaredOperatorLifecycleService(operatorLifecycleService);
        OpsShardReadinessRuntimeExecutionArtifactCandidateService artifactCandidateService =
                new OpsShardReadinessRuntimeExecutionArtifactCandidateService(declaredLifecycleService);
        OpsShardReadinessRuntimeExecutionPacketContributionService packetContributionService =
                new OpsShardReadinessRuntimeExecutionPacketContributionService(artifactCandidateService);
        OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService =
                new OpsShardReadinessRuntimeExecutionApprovalGateInputService(packetContributionService);
        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService contractHandoffService =
                new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(approvalGateInputService);
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService templateCompatibilityService =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(contractHandoffService);
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService compatibilityIntakeService =
                new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
                        templateCompatibilityService
                );
        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService valueValidationService =
                new OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService(compatibilityIntakeService);
        return new OpsShardReadinessRuntimeExecutionLiveReadGateService(valueValidationService);
    }
}
