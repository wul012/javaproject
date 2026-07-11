package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFifteenVersionCompletionTests {

  @Test
  void keepsV260ThroughV274CatalogedAsTheCurrentFifteenVersionRun() {
    var versions =
        OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions();

    assertThat(versions)
        .containsSubsequence(IntStream.rangeClosed(260, 274).boxed().toArray(Integer[]::new));
    assertThat(versions)
        .containsExactlyElementsOf(IntStream.rangeClosed(226, versions.getLast()).boxed().toList());
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .receipts())
        .hasSizeGreaterThanOrEqualTo(49);
  }

  @Test
  void keepsFrozenV225HandoffSeparateAfterFifteenVersionCompletion() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.version()).isEqualTo("Java v225");
    assertThat(handoff.evidencePath())
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.EVIDENCE_PATH);
    assertThat(handoff.handoffGuardEvidence())
        .doesNotContainAnyElementsOf(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .evidencePaths());
    assertThat(handoff.digestEvidence())
        .doesNotContainAnyElementsOf(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .evidencePaths());
  }

  @Test
  void keepsFifteenVersionCompletionPathVersionedToV274() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_FIFTEEN_VERSION_COMPLETION_EVIDENCE_PATH)
        .isEqualTo(
            "e/274/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "fifteen-version-completion-v274.json");
  }
}
