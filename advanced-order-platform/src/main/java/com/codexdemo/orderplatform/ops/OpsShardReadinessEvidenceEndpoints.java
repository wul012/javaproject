package com.codexdemo.orderplatform.ops;

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
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEndpointRegistryIntegrityService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalGateInputService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionArtifactCandidateService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPacketContributionService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractEndpointPairs;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessEvidenceEndpoints {

  private OpsShardReadinessEvidenceEndpoints() {}

  static List<String> liveEndpoints() {
    return endpointPairs().stream().map(EndpointPair::liveEndpoint).toList();
  }

  static List<String> fixtureEndpoints() {
    return endpointPairs().stream().map(EndpointPair::fixtureEndpoint).toList();
  }

  static List<EndpointPair> endpointPairs() {
    List<EndpointPair> endpointPairs = new ArrayList<>();
    endpointPairs.add(
        endpointPair(OpsShardReadinessService.ENDPOINT, OpsShardReadinessService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessHardeningService.ENDPOINT,
            OpsShardReadinessHardeningService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessEchoService.ENDPOINT, OpsShardReadinessEchoService.FIXTURE_ENDPOINT));
    OpsShardReadinessV1ContractEndpointPairs.endpointPairs().stream()
        .map(pair -> endpointPair(pair.liveEndpoint(), pair.fixtureEndpoint()))
        .forEach(endpointPairs::add);
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.ENDPOINT,
            OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessEvidenceIndexService.ENDPOINT,
            OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessEvidenceVerificationService.ENDPOINT,
            OpsShardReadinessEvidenceVerificationService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessEvidenceHandoffService.ENDPOINT,
            OpsShardReadinessEvidenceHandoffService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessActiveShardPlanHandoffService.ENDPOINT,
            OpsShardReadinessActiveShardPlanHandoffService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessLiveReadGatePlanService.ENDPOINT,
            OpsShardReadinessLiveReadGatePlanService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessOperatorServiceLifecycleService.ENDPOINT,
            OpsShardReadinessOperatorServiceLifecycleService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessDeclaredOperatorLifecycleService.ENDPOINT,
            OpsShardReadinessDeclaredOperatorLifecycleService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionArtifactCandidateService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionArtifactCandidateService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionPacketContributionService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionPacketContributionService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
                .FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
                .ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
                .FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionLiveReadGateService.FIXTURE_ENDPOINT));
    endpointPairs.add(
        endpointPair(
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT));
    return List.copyOf(endpointPairs);
  }

  static List<String> liveProbeEndpoints() {
    return liveEndpoints().stream().map(endpoint -> "GET " + endpoint).toList();
  }

  static List<String> fixtureProbeEndpoints() {
    return fixtureEndpoints().stream().map(endpoint -> "GET " + endpoint).toList();
  }

  private static EndpointPair endpointPair(String liveEndpoint, String fixtureEndpoint) {
    return new EndpointPair(liveEndpoint, fixtureEndpoint);
  }

  record EndpointPair(String liveEndpoint, String fixtureEndpoint) {}
}
