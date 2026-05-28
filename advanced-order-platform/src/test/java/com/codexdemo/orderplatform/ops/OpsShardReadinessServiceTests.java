package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessServiceTests {

    @Test
    void buildsFrozenShardReadinessV1Echo() {
        OpsShardReadinessResponse readiness = new OpsShardReadinessService().readiness();

        assertThat(readiness.project()).isEqualTo("advanced-order-platform");
        assertThat(readiness.version()).isEqualTo("Java v153");
        assertThat(readiness.readOnly()).isTrue();
        assertThat(readiness.executionAllowed()).isFalse();
        assertThat(readiness.shardEnabled()).isFalse();
        assertThat(readiness.shardCount()).isZero();
        assertThat(readiness.slotCount()).isZero();
        assertThat(readiness.routingMode()).isEqualTo("fixture");
        assertThat(readiness.evidencePath()).isEqualTo("e/153/evidence/java-shard-readiness-v153.json");
        assertThat(readiness.status()).isEqualTo("passed");
    }
}
