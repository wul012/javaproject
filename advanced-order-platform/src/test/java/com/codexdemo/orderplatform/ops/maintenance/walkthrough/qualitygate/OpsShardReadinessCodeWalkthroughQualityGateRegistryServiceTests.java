package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityGateRegistryServiceTests {

  @Test
  void buildsQualityGateRegistryForLargerVersionGranularity() {
    var response = OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1753");
    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-code-walkthrough-quality-gate-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367 / Java v1748-v1753");
    assertThat(response.priorComplianceRegistry())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry");
    assertThat(response.registryState())
        .isEqualTo("larger-version-granularity-enforced-with-standout-walkthrough-rubric");
    assertThat(response.versionRuleCount()).isEqualTo(6);
    assertThat(response.explanationRubricCount()).isEqualTo(8);
    assertThat(response.evidenceAnchorCount()).isEqualTo(6);
    assertThat(response.reviewChecklistCount()).isEqualTo(6);
    assertThat(response.boundaryRuleCount()).isEqualTo(8);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void namesTheNewGranularityRulesAndRubricSections() {
    var response = OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.registry();

    assertThat(response.versionRules())
        .extracting(OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.VersionRule::code)
        .containsExactly(
            "no-micro-version-by-default",
            "standout-explanation-required",
            "evidence-and-tests-travel-together",
            "refactor-with-purpose",
            "batch-size-guard",
            "read-only-boundary-first");
    assertThat(response.explanationRubrics())
        .extracting(
            OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.ExplanationRubric::section)
        .contains("入口路由", "响应模型", "服务层核心流程", "阻断与安全边界");
  }
}
