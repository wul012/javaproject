package com.codexdemo.orderplatform.ops.maintenance.readonlyevidence;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessActiveShardPlanHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessDeclaredOperatorLifecycleService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEchoService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessHardeningService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessLiveReadGatePlanService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessOperatorServiceLifecycleService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalGateInputService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionArtifactCandidateService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPacketContributionService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import java.util.List;

final class OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot {

  private OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot() {}

  static List<EndpointPair> v184EndpointPairs() {
    return List.of(
        endpointPair(OpsShardReadinessService.ENDPOINT, OpsShardReadinessService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessHardeningService.ENDPOINT,
            OpsShardReadinessHardeningService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessEchoService.ENDPOINT, OpsShardReadinessEchoService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT,
            OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessEvidenceIndexService.ENDPOINT,
            OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessEvidenceVerificationService.ENDPOINT,
            OpsShardReadinessEvidenceVerificationService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessEvidenceHandoffService.ENDPOINT,
            OpsShardReadinessEvidenceHandoffService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessActiveShardPlanHandoffService.ENDPOINT,
            OpsShardReadinessActiveShardPlanHandoffService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessLiveReadGatePlanService.ENDPOINT,
            OpsShardReadinessLiveReadGatePlanService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessOperatorServiceLifecycleService.ENDPOINT,
            OpsShardReadinessOperatorServiceLifecycleService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessDeclaredOperatorLifecycleService.ENDPOINT,
            OpsShardReadinessDeclaredOperatorLifecycleService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionArtifactCandidateService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionArtifactCandidateService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionPacketContributionService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionPacketContributionService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
                .FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
                .ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
                .FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionLiveReadGateService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT));
  }

  static List<String> v184LiveEndpoints() {
    return v184EndpointPairs().stream().map(EndpointPair::liveEndpoint).toList();
  }

  static List<String> v184FixtureEndpoints() {
    return v184EndpointPairs().stream().map(EndpointPair::fixtureEndpoint).toList();
  }

  private static EndpointPair endpointPair(String liveEndpoint, String fixtureEndpoint) {
    return new EndpointPair(liveEndpoint, fixtureEndpoint);
  }

  record EndpointPair(String liveEndpoint, String fixtureEndpoint) {}
}
