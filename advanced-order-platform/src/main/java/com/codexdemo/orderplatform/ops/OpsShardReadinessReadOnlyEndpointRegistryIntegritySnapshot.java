package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot {

    private OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot() {
    }

    static List<OpsShardReadinessEvidenceEndpoints.EndpointPair> v184EndpointPairs() {
        return List.of(
                endpointPair(OpsShardReadinessService.ENDPOINT, OpsShardReadinessService.FIXTURE_ENDPOINT),
                endpointPair(
                        OpsShardReadinessHardeningService.ENDPOINT,
                        OpsShardReadinessHardeningService.FIXTURE_ENDPOINT
                ),
                endpointPair(OpsShardReadinessEchoService.ENDPOINT, OpsShardReadinessEchoService.FIXTURE_ENDPOINT),
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

    static List<String> v184LiveEndpoints() {
        return v184EndpointPairs().stream()
                .map(OpsShardReadinessEvidenceEndpoints.EndpointPair::liveEndpoint)
                .toList();
    }

    static List<String> v184FixtureEndpoints() {
        return v184EndpointPairs().stream()
                .map(OpsShardReadinessEvidenceEndpoints.EndpointPair::fixtureEndpoint)
                .toList();
    }

    private static OpsShardReadinessEvidenceEndpoints.EndpointPair endpointPair(
            String liveEndpoint,
            String fixtureEndpoint
    ) {
        return new OpsShardReadinessEvidenceEndpoints.EndpointPair(liveEndpoint, fixtureEndpoint);
    }
}
