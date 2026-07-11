package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessResponse;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractAlignmentSnapshotTests {

  @Test
  void freezesV187AlignmentSourceContract() {
    OpsShardReadinessResponse source =
        OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceReadiness();

    assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187ContractName())
        .isEqualTo("shard-readiness.v1");
    assertThat(source.project()).isEqualTo("advanced-order-platform");
    assertThat(source.version()).isEqualTo("Java v153");
    assertThat(source.readOnly()).isTrue();
    assertThat(source.executionAllowed()).isFalse();
    assertThat(source.shardEnabled()).isFalse();
    assertThat(source.shardCount()).isZero();
    assertThat(source.slotCount()).isZero();
    assertThat(source.routingMode()).isEqualTo("fixture");
    assertThat(source.evidencePath()).isEqualTo("e/153/evidence/java-shard-readiness-v153.json");
    assertThat(source.status()).isEqualTo("passed");
    assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness");
    assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187SourceFixtureEndpoint())
        .isEqualTo("/contracts/java-shard-readiness-v153.fixture.json");
    assertThat(OpsShardReadinessV1ContractAlignmentSnapshot.v187MinimalFields())
        .containsExactly(
            "project",
            "version",
            "readOnly",
            "executionAllowed",
            "shardEnabled",
            "shardCount",
            "slotCount",
            "routingMode",
            "evidencePath",
            "status");
  }

  @Test
  void alignmentServiceReadsFrozenV187Snapshot() {
    OpsShardReadinessV1ContractAlignmentResponse alignment =
        new OpsShardReadinessV1ContractAlignmentService().alignment();

    assertThat(alignment.sourceReadinessVersion()).isEqualTo("Java v153");
    assertThat(alignment.minimalFields()).hasSize(10);
    assertThat(alignment.verificationChecks())
        .contains(
            "contract-name:shard-readiness.v1",
            "source-readiness-version:Java v153",
            "minimal-field-count:10");
    assertThat(alignment.status()).isEqualTo("passed");
  }
}
