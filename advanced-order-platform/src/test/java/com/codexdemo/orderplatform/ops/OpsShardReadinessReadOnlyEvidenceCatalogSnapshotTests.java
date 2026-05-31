package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEvidenceCatalogSnapshotTests {

    @Test
    void freezesV175ReadOnlyEvidenceCatalogEndpoints() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.v175LiveEndpoints())
                .containsExactly(
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/hardening",
                        "/api/v1/ops/shard-readiness/echo",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
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
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.v175FixtureEndpoints())
                .containsExactly(
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-echo-v174.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
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
    }
}
