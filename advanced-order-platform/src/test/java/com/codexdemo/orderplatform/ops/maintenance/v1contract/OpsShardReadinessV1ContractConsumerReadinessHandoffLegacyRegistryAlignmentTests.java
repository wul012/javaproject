package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEchoService;
import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceEndpointsTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffLegacyRegistryAlignmentTests {

  @Test
  void keepsLegacyV1ConsumerRegistryAlignedAfterReadinessHandoff() {
    assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(11);
    assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
        .doesNotHaveDuplicates()
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT)
        .doesNotContain(
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT);
  }

  @Test
  void keepsFixtureRegistryAlignedWithReadinessHandoffPair() {
    assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
        .doesNotHaveDuplicates()
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT)
        .doesNotContain(
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT);
  }

  @Test
  void keepsRollingEvidenceEndpointsOrderedAcrossConsumerHandoffAndReadOnlyCatalog() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessEchoService.ENDPOINT,
            OpsShardReadinessV1ContractAlignmentService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
  }

  @Test
  void keepsLegacyRegistryAlignmentEvidencePathVersionedToV240() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_LEGACY_REGISTRY_ALIGNMENT_EVIDENCE_PATH)
        .isEqualTo(
            "e/240/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "legacy-registry-alignment-v240.json");
  }
}
