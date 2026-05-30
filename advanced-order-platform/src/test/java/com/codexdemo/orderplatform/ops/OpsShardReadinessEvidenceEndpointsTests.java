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
                        "/api/v1/ops/shard-readiness/evidence-handoff",
                        "/api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "/api/v1/ops/shard-readiness/live-read-gate-plan",
                        "/api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "/api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "/api/v1/ops/shard-readiness/runtime-execution-packet-contribution"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints())
                .containsExactly(
                        "GET /api/v1/ops/shard-readiness",
                        "GET /api/v1/ops/shard-readiness/hardening",
                        "GET /api/v1/ops/shard-readiness/evidence-index",
                        "GET /api/v1/ops/shard-readiness/evidence-verification",
                        "GET /api/v1/ops/shard-readiness/evidence-handoff",
                        "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
                        "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "GET /api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-packet-contribution"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints())
                .containsExactly(
                        "GET /contracts/java-shard-readiness-v153.fixture.json",
                        "GET /contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "GET /contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "GET /contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "GET /contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "GET /contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json"
                );
    }
}
