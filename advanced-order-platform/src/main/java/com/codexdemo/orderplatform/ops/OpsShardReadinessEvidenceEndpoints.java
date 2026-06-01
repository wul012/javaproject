package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessEvidenceEndpoints {

    private OpsShardReadinessEvidenceEndpoints() {
    }

    static List<String> liveEndpoints() {
        return endpointPairs().stream()
                .map(EndpointPair::liveEndpoint)
                .toList();
    }

    static List<String> fixtureEndpoints() {
        return endpointPairs().stream()
                .map(EndpointPair::fixtureEndpoint)
                .toList();
    }

    static List<EndpointPair> endpointPairs() {
        return List.of(
                endpointPair(OpsShardReadinessService.ENDPOINT, OpsShardReadinessService.FIXTURE_ENDPOINT),
                endpointPair(
                        OpsShardReadinessHardeningService.ENDPOINT,
                        OpsShardReadinessHardeningService.FIXTURE_ENDPOINT
                ),
                endpointPair(OpsShardReadinessEchoService.ENDPOINT, OpsShardReadinessEchoService.FIXTURE_ENDPOINT),
                endpointPair(
                        OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
                        OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT,
                        OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT,
                        OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessEvidenceIndexService.ENDPOINT,
                        OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessEvidenceVerificationService.ENDPOINT,
                        OpsShardReadinessEvidenceVerificationService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessEvidenceHandoffService.ENDPOINT,
                        OpsShardReadinessEvidenceHandoffService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessActiveShardPlanHandoffService.ENDPOINT,
                        OpsShardReadinessActiveShardPlanHandoffService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessLiveReadGatePlanService.ENDPOINT,
                        OpsShardReadinessLiveReadGatePlanService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessOperatorServiceLifecycleService.ENDPOINT,
                        OpsShardReadinessOperatorServiceLifecycleService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessDeclaredOperatorLifecycleService.ENDPOINT,
                        OpsShardReadinessDeclaredOperatorLifecycleService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionArtifactCandidateService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionArtifactCandidateService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionPacketContributionService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPacketContributionService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionLiveReadGateService.FIXTURE_ENDPOINT
                ),
                endpointPair(
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT,
                        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT
                )
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

    private static EndpointPair endpointPair(String liveEndpoint, String fixtureEndpoint) {
        return new EndpointPair(liveEndpoint, fixtureEndpoint);
    }

    record EndpointPair(String liveEndpoint, String fixtureEndpoint) {
    }
}
