package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import java.util.List;

public final class OpsShardReadinessV1ContractEndpointPairs {

  private OpsShardReadinessV1ContractEndpointPairs() {}

  public static List<EndpointPair> endpointPairs() {
    return List.of(
        endpointPair(
            OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
            OpsShardReadinessV1ContractAlignmentService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractAlignmentHandoffService.ENDPOINT,
            OpsShardReadinessV1ContractAlignmentHandoffService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractEvidencePacketService.ENDPOINT,
            OpsShardReadinessV1ContractEvidencePacketService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractOperatorChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractOperatorChecklistService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractHandoffManifestService.ENDPOINT,
            OpsShardReadinessV1ContractHandoffManifestService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractConsumerProbePlanService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerProbePlanService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT),
        endpointPair(
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT));
  }

  public static List<String> liveEndpoints() {
    return endpointPairs().stream().map(EndpointPair::liveEndpoint).toList();
  }

  public static List<String> fixtureEndpoints() {
    return endpointPairs().stream().map(EndpointPair::fixtureEndpoint).toList();
  }

  private static EndpointPair endpointPair(String liveEndpoint, String fixtureEndpoint) {
    return new EndpointPair(liveEndpoint, fixtureEndpoint);
  }

  public record EndpointPair(String liveEndpoint, String fixtureEndpoint) {}
}
