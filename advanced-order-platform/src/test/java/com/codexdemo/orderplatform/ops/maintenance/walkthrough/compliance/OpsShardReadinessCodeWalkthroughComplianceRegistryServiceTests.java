package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughComplianceRegistryServiceTests {

  @Test
  void buildsCodeWalkthroughComplianceRegistryForNewStandardBatch() {
    var response = WalkthroughTestData.registry();

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v1747");
    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-code-walkthrough-compliance-registry.v1");
    assertThat(response.sourcePlan()).isEqualTo("Node v367 / Node v368");
    assertThat(response.archiveDirectory()).isEqualTo("代码讲解记录_生产雏形阶段4/v1728-v1747");
    assertThat(response.registryState())
        .isEqualTo("future-walkthrough-structure-enforced-with-read-only-runtime-boundaries");
    assertThat(response.versionCount()).isEqualTo(20);
    assertThat(response.requiredHeadingCount()).isEqualTo(9);
    assertThat(response.archiveRangeCount()).isEqualTo(5);
    assertThat(response.documentationRuleCount()).isEqualTo(7);
    assertThat(response.testCoverageCount()).isEqualTo(8);
    assertThat(response.status()).isEqualTo("passed");
  }

  @Test
  void keepsVersionRangeAndRequiredHeadingsStable() {
    var response = WalkthroughTestData.registry();

    assertThat(response.versions())
        .extracting(
            OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.VersionEntry::javaVersion)
        .first()
        .isEqualTo("Java v1728");
    assertThat(response.versions())
        .extracting(
            OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.VersionEntry::javaVersion)
        .last()
        .isEqualTo("Java v1747");
    assertThat(response.requiredHeadings())
        .extracting(
            OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.RequiredHeading::heading)
        .containsExactly(
            "## 入口路由",
            "## 响应模型",
            "## 上游证据配置",
            "## 服务层核心流程",
            "## Java 证据检查",
            "## mini-kv 证据检查",
            "## 阻断与安全边界",
            "## 测试覆盖",
            "## 一句话总结");
  }
}
