package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualityaudit;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle.sha256;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityAuditRegistryRendererTests {

  @Test
  void rendersStableMarkdownSectionsForQualityAudit() {
    var response = WalkthroughTestData.registry();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection::heading)
        .containsExactly(
            "Batch Assessments",
            "Version Audits",
            "Rubric Scores",
            "Review Findings",
            "Boundary Audits",
            "Verification Steps");
    assertThat(response.markdownSections())
        .extracting(section -> section.lines().size())
        .containsExactly(3, 7, 9, 5, 9, 6);
    assertThat(
            sha256(
                response.markdownSections(),
                OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
                    ::heading,
                OpsShardReadinessCodeWalkthroughQualityAuditRegistryResponse.MarkdownSection
                    ::lines))
        .isEqualTo("4358541d814b1b22095e049b9e1b2a314341b08e5e5ada8487eccf1271c3720d");
    assertThat(response.markdownSections().get(0).lines().get(0))
        .isEqualTo("batch-assessment-count=2");
    assertThat(response.markdownSections().get(1).lines())
        .anySatisfy(line -> assertThat(line).contains("Java v1748", "medium=true"));
    assertThat(response.markdownSections().get(2).lines())
        .anySatisfy(line -> assertThat(line).contains("服务层核心流程", "passed=true"));
    assertThat(response.markdownSections().get(3).lines())
        .anySatisfy(line -> assertThat(line).contains("no-shallow-version-found"));
    assertThat(response.markdownSections().get(4).lines())
        .anySatisfy(line -> assertThat(line).contains("no-write-routing", "allowed=false"));
    assertThat(response.markdownSections().get(5).lines())
        .anySatisfy(line -> assertThat(line).contains("full-maven-regression", "mvn -q test"));
  }
}
