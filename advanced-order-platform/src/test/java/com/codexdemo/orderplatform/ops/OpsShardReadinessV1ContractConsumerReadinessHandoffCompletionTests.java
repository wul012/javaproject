package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCompletionTests {

  @Test
  void keepsReadinessHandoffAsCurrentV1ConsumerCompletionPoint() {
    assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
        .hasSize(11)
        .last()
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
  }

  @Test
  void keepsPostHandoffGuardReceiptsOutsideFrozenV225Handoff() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.digestEvidence()).doesNotContainAnyElementsOf(postHandoffEvidencePaths());
    assertThat(handoff.handoffGuardEvidence())
        .doesNotContainAnyElementsOf(postHandoffEvidencePaths());
    assertThat(handoff.handoffChecks()).noneSatisfy(check -> assertThat(check).contains("v239"));
  }

  @Test
  void keepsCompletionEvidencePathVersionedToV239() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_COMPLETION_EVIDENCE_PATH)
        .isEqualTo(
            "e/239/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-completion-v239.json");
  }

  private static List<String> postHandoffEvidencePaths() {
    return List.of(
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_SNAPSHOT_FREEZE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_INTEGRITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_ROUTE_INVENTORY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_EVIDENCE_CHAIN_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_OPS_EVIDENCE_ALIGNMENT_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_CONTROLLER_MAPPING_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_FIXTURE_PARITY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_BOUNDARY_MATRIX_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_ENDPOINT_ADJACENCY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_RECEIPT_UNIQUENESS_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_NODE_CONSUMER_BOUNDARY_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_ARTIFACT_PRESENCE_EVIDENCE_PATH,
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_COMPLETION_EVIDENCE_PATH);
  }
}
