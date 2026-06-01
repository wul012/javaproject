package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessEvidenceEndpointsTests {

    @Test
    void groupsShardReadinessLiveAndFixtureEndpointsInEvidenceOrder() {
        assertThat(OpsShardReadinessEvidenceEndpoints.endpointPairs())
                .extracting(OpsShardReadinessEvidenceEndpoints.EndpointPair::liveEndpoint)
                .containsExactly(
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/hardening",
                        "/api/v1/ops/shard-readiness/echo",
                        "/api/v1/ops/shard-readiness/v1-contract-alignment",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
                        "/api/v1/ops/shard-readiness/evidence-index",
                        "/api/v1/ops/shard-readiness/evidence-verification",
                        "/api/v1/ops/shard-readiness/evidence-handoff",
                        "/api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "/api/v1/ops/shard-readiness/live-read-gate-plan",
                        "/api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "/api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "/api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
                        "/api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
                        "/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.endpointPairs())
                .extracting(OpsShardReadinessEvidenceEndpoints.EndpointPair::fixtureEndpoint)
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-echo-v174.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.liveEndpoints())
                .containsExactly(
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/hardening",
                        "/api/v1/ops/shard-readiness/echo",
                        "/api/v1/ops/shard-readiness/v1-contract-alignment",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
                        "/api/v1/ops/shard-readiness/evidence-index",
                        "/api/v1/ops/shard-readiness/evidence-verification",
                        "/api/v1/ops/shard-readiness/evidence-handoff",
                        "/api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "/api/v1/ops/shard-readiness/live-read-gate-plan",
                        "/api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "/api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "/api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
                        "/api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
                        "/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureEndpoints())
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-echo-v174.fixture.json",
                        "/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.liveProbeEndpoints())
                .containsExactly(
                        "GET /api/v1/ops/shard-readiness",
                        "GET /api/v1/ops/shard-readiness/hardening",
                        "GET /api/v1/ops/shard-readiness/echo",
                        "GET /api/v1/ops/shard-readiness/v1-contract-alignment",
                        "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "GET /api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
                        "GET /api/v1/ops/shard-readiness/evidence-index",
                        "GET /api/v1/ops/shard-readiness/evidence-verification",
                        "GET /api/v1/ops/shard-readiness/evidence-handoff",
                        "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
                        "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "GET /api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout"
                );
        assertThat(OpsShardReadinessEvidenceEndpoints.fixtureProbeEndpoints())
                .containsExactly(
                        "GET /contracts/java-shard-readiness-v153.fixture.json",
                        "GET /contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "GET /contracts/java-shard-readiness-echo-v174.fixture.json",
                        "GET /contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "GET /contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "GET /contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "GET /contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "GET /contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json"
                );
    }
}
