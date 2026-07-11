package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffEvidenceChainTests {

  @Test
  void connectsDigestGuardAndHandoffEvidenceWithoutFutureBackfill() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    List<String> handoffEvidenceChain = new ArrayList<>();
    handoffEvidenceChain.addAll(handoff.digestEvidence());
    handoffEvidenceChain.addAll(handoff.handoffGuardEvidence());
    handoffEvidenceChain.add(handoff.evidencePath());

    assertThat(handoffEvidenceChain)
        .containsExactly(
            "e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json",
            "e/216/evidence/java-shard-readiness-v215-consumer-verification-checklist-snapshot-freeze-v216.json",
            "e/217/evidence/java-shard-readiness-v215-consumer-verification-checklist-historical-compatibility-v217.json",
            "e/218/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-integrity-v218.json",
            "e/219/evidence/java-shard-readiness-v1-contract-consumer-route-inventory-v219.json",
            "e/221/evidence/java-shard-readiness-v220-consumer-evidence-digest-snapshot-freeze-v221.json",
            "e/222/evidence/java-shard-readiness-v220-consumer-evidence-digest-historical-compatibility-v222.json",
            "e/223/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-integrity-v223.json",
            "e/224/evidence/java-shard-readiness-v1-contract-consumer-readiness-completion-v224.json",
            "e/225/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json")
        .doesNotContain(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_SNAPSHOT_FREEZE_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_HISTORICAL_COMPATIBILITY_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_INTEGRITY_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_ROUTE_INVENTORY_EVIDENCE_PATH,
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_EVIDENCE_CHAIN_EVIDENCE_PATH);
  }

  @Test
  void keepsEvidenceChainCountsAlignedWithHandoffChecks() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.digestEvidence()).hasSize(handoff.digestEvidenceCount());
    assertThat(handoff.handoffGuardEvidence()).hasSize(4);
    assertThat(handoff.handoffChecks())
        .contains(
            "digest-evidence-count:" + handoff.digestEvidence().size(),
            "digest-check-count:" + handoff.digestCheckCount(),
            "handoff-guard-evidence-count:" + handoff.handoffGuardEvidence().size());
  }
}
