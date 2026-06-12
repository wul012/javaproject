package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepRegistryServiceTests {

    @Test
    void buildsReadabilityUpkeepRegistry() {
        var response = ReadabilityUpkeepRegistryTestSupport.registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1783");
        assertThat(response.endpoint()).isEqualTo("/api/v1/ops/readability/upkeep-registry");
        assertThat(response.profile()).isEqualTo("java-ops-readability-upkeep-registry.v1");
        assertThat(response.docsRoot()).isEqualTo("docs/ops");
        assertThat(response.packageRoot())
                .isEqualTo("com.codexdemo.orderplatform.ops.maintenance.readability");
        assertThat(response.registryState())
                .isEqualTo("readability-upkeep-subpackage-registry-active-v1783");
        assertThat(response.topicCount()).isEqualTo(5);
        assertThat(response.packageRuleCount()).isEqualTo(4);
        assertThat(response.templateRuleCount()).isEqualTo(10);
        assertThat(response.classNameTrialCount()).isEqualTo(3);
        assertThat(response.boundaryRuleCount()).isEqualTo(8);
        assertThat(response.deniedBoundaryRuleCount()).isEqualTo(8);
        assertThat(response.verificationStepCount()).isEqualTo(6);
        assertThat(response.markdownSectionCount()).isEqualTo(6);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void emitsReadabilityChecks() {
        var response = ReadabilityUpkeepRegistryTestSupport.registry();

        assertThat(response.checks())
                .contains(
                        "readability-upkeep-source-advice-java-only",
                        "readability-upkeep-docs-root-docs/ops",
                        "readability-upkeep-new-code-subpackage-first",
                        "readability-upkeep-registry-template-required",
                        "readability-upkeep-short-class-name-trial-active",
                        "readability-upkeep-no-write-routing",
                        "readability-upkeep-no-credential-value",
                        "readability-upkeep-no-upstream-autostart"
                );
        assertThat(response.topics())
                .extracting(ReadabilityUpkeepRegistryResponse.TopicMap::code)
                .contains("shard-readiness", "walkthrough-quality", "archive-layout");
    }
}
