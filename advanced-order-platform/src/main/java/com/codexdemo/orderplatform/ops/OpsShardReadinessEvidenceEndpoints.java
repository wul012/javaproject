package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessEvidenceEndpoints {

    private OpsShardReadinessEvidenceEndpoints() {
    }

    static List<String> liveEndpoints() {
        return List.of(
                OpsShardReadinessService.ENDPOINT,
                OpsShardReadinessHardeningService.ENDPOINT,
                OpsShardReadinessEchoService.ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT,
                OpsShardReadinessEvidenceIndexService.ENDPOINT,
                OpsShardReadinessEvidenceVerificationService.ENDPOINT,
                OpsShardReadinessEvidenceHandoffService.ENDPOINT,
                OpsShardReadinessActiveShardPlanHandoffService.ENDPOINT,
                OpsShardReadinessLiveReadGatePlanService.ENDPOINT,
                OpsShardReadinessOperatorServiceLifecycleService.ENDPOINT,
                OpsShardReadinessDeclaredOperatorLifecycleService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionArtifactCandidateService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionPacketContributionService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT,
                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT
        );
    }

    static List<String> fixtureEndpoints() {
        return List.of(
                OpsShardReadinessService.FIXTURE_ENDPOINT,
                OpsShardReadinessHardeningService.FIXTURE_ENDPOINT,
                OpsShardReadinessEchoService.FIXTURE_ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.FIXTURE_ENDPOINT,
                OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.FIXTURE_ENDPOINT,
                OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT,
                OpsShardReadinessEvidenceVerificationService.FIXTURE_ENDPOINT,
                OpsShardReadinessEvidenceHandoffService.FIXTURE_ENDPOINT,
                OpsShardReadinessActiveShardPlanHandoffService.FIXTURE_ENDPOINT,
                OpsShardReadinessLiveReadGatePlanService.FIXTURE_ENDPOINT,
                OpsShardReadinessOperatorServiceLifecycleService.FIXTURE_ENDPOINT,
                OpsShardReadinessDeclaredOperatorLifecycleService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionArtifactCandidateService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionPacketContributionService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionLiveReadGateService.FIXTURE_ENDPOINT,
                OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT
        );
    }

    static List<String> liveProbeEndpoints() {
        return liveEndpoints().stream()
                .map(endpoint -> "GET " + endpoint)
                .toList();
    }

    static List<String> fixtureProbeEndpoints() {
        return fixtureEndpoints().stream()
                .map(endpoint -> "GET " + endpoint)
                .toList();
    }
}
