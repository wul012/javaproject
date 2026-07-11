package com.codexdemo.orderplatform.ops.maintenance.readinesscore;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessEvidenceIndexServiceTests {

  @Test
  void buildsFrozenShardReadinessEvidenceIndex() {
    OpsShardReadinessEvidenceIndexResponse index =
        new OpsShardReadinessEvidenceIndexService().evidenceIndex();

    assertThat(index.project()).isEqualTo("advanced-order-platform");
    assertThat(index.version()).isEqualTo("Java v155");
    assertThat(index.readOnly()).isTrue();
    assertThat(index.executionAllowed()).isFalse();
    assertThat(index.lastConsumedByNodeVersion()).isEqualTo("Node v376");
    assertThat(index.requiredContractFields())
        .containsExactly(
            "project",
            "version",
            "readOnly",
            "executionAllowed",
            "shardEnabled",
            "shardCount",
            "slotCount",
            "routingMode",
            "status");
    assertThat(index.evidenceEntries())
        .extracting(OpsShardReadinessEvidenceIndexResponse.EvidenceEntry::evidenceVersion)
        .containsExactly("Java v153", "Java v154");
    assertThat(index.evidenceEntries())
        .allSatisfy(
            entry -> {
              assertThat(entry.frozen()).isTrue();
              assertThat(entry.rollingCurrentPointer()).isFalse();
            });
    assertThat(index.fallbackPolicy())
        .contains(
            "use-versioned-fixture-endpoints-only",
            "do-not-read-rolling-current-files-for-historical-baselines");
    assertThat(index.compatibilityGuarantees())
        .contains(
            "v153-core-contract-remains-frozen",
            "v154-hardening-remains-additive",
            "no-node-v370-v376-archive-mutation");
    assertThat(index.evidencePath())
        .isEqualTo("e/155/evidence/java-shard-readiness-evidence-index-v155.json");
    assertThat(index.status()).isEqualTo("passed");
  }
}
