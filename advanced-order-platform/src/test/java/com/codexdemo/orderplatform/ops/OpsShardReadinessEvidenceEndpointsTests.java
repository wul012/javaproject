package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessEvidenceEndpointsTests {

    @Test
    void groupsShardReadinessLiveAndFixtureEndpointsInEvidenceOrder() {
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsExactly(
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/hardening",
                        "/api/v1/ops/shard-readiness/evidence-index",
                        "/api/v1/ops/shard-readiness/evidence-verification",
                        "/api/v1/ops/shard-readiness/evidence-handoff"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints())
                .containsExactly(
                        "GET /api/v1/ops/shard-readiness",
                        "GET /api/v1/ops/shard-readiness/hardening",
                        "GET /api/v1/ops/shard-readiness/evidence-index",
                        "GET /api/v1/ops/shard-readiness/evidence-verification",
                        "GET /api/v1/ops/shard-readiness/evidence-handoff"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints())
                .containsExactly(
                        "GET /contracts/java-shard-readiness-v153.fixture.json",
                        "GET /contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-handoff-v157.fixture.json"
                );
    }
}
