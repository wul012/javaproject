package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupCiEvidenceServiceTests {

    @Test
    void buildsCiEvidenceRequirementsWithoutClaimingRuntimeExecution() {
        OpsShardReadinessRouteCleanupCiEvidenceResponse evidence =
                new OpsShardReadinessRouteCleanupCiEvidenceService().evidence();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(350);
        assertThat(evidence.project()).isEqualTo("advanced-order-platform");
        assertThat(evidence.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(evidence.readOnly()).isTrue();
        assertThat(evidence.executionAllowed()).isFalse();
        assertThat(evidence.ciEvidenceEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-ci-evidence");
        assertThat(evidence.ciProfile()).isEqualTo("java-shard-readiness-route-cleanup-ci-evidence.v1");
        assertThat(evidence.validationStepCount()).isEqualTo(4);
        assertThat(evidence.validationSteps())
                .extracting(OpsShardReadinessRouteCleanupCiEvidenceResponse.ValidationStep::name)
                .containsExactly(
                        "focused-route-cleanup-tests",
                        "full-java-suite",
                        "github-actions-master",
                        "cleanup-gate"
                );
        assertThat(evidence.validationSteps())
                .allSatisfy(step -> {
                    assertThat(step.required()).isTrue();
                    assertThat(step.status()).isEqualTo("required");
                });
        assertThat(evidence.releaseRequirement()).contains("GitHub Actions");
        assertThat(evidence.status()).isEqualTo("passed");
    }
}
