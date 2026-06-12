package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughDepthRegistryServiceTests {

    @Test
    void buildsChineseLongformDepthRegistry() {
        var response = OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport.registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1774");
        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-depth-registry");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-code-walkthrough-depth-registry.v1");
        assertThat(response.sourcePlan()).isEqualTo("Node v367 / Java v1774-v1778");
        assertThat(response.priorQualityGate())
                .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry");
        assertThat(response.registryState())
                .isEqualTo("chinese-longform-walkthrough-depth-enforced-from-v1774");
        assertThat(response.effectiveFromVersion()).isEqualTo(1774);
        assertThat(response.minimumChineseCharacterCount()).isEqualTo(3000);
        assertThat(response.depthRuleCount()).isEqualTo(5);
        assertThat(response.languageRuleCount()).isEqualTo(4);
        assertThat(response.evidenceRuleCount()).isEqualTo(5);
        assertThat(response.boundaryRuleCount()).isEqualTo(8);
        assertThat(response.deniedBoundaryRuleCount()).isEqualTo(8);
        assertThat(response.verificationStepCount()).isEqualTo(5);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void emitsDepthChecksForFutureWalkthroughs() {
        var response = OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport.registry();

        assertThat(response.checks())
                .contains(
                        "code-walkthrough-depth-effective-from-v1774",
                        "code-walkthrough-depth-minimum-chinese-characters-3000",
                        "code-walkthrough-depth-chinese-default",
                        "code-walkthrough-depth-one-version-one-walkthrough",
                        "code-walkthrough-depth-no-short-receipts",
                        "code-walkthrough-depth-no-padding-workload-evidence",
                        "code-walkthrough-depth-project-local-workload-proof"
                );
        assertThat(response.depthRules())
                .extracting(OpsShardReadinessCodeWalkthroughDepthRegistryResponse.DepthRule::code)
                .contains(
                        "minimum-3000-chinese-characters",
                        "no-padding-workload-evidence",
                        "implementation-surface-required",
                        "boundary-proof-required"
                );
    }
}
