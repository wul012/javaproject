package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1838Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "comparedpackagereview"));
  private static final Path DOC =
      Path.of("docs", "ops", "compared-package-review-extraction-v1838.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1838-v1842",
          "version-1838-production-excellence-compared-package-review-extraction.md");
  private static final List<String> MOVED_FILES =
      List.of(
          "OpsShardReadinessComparedPackageReviewCatalogService.java",
          "OpsShardReadinessComparedPackageReviewComparisonOutcomeService.java",
          "OpsShardReadinessComparedPackageReviewComparisonOutcomeSlotCatalog.java",
          "OpsShardReadinessComparedPackageReviewGuardCatalog.java",
          "OpsShardReadinessComparedPackageReviewHandoffCloseoutService.java",
          "OpsShardReadinessComparedPackageReviewIdentityDigestService.java",
          "OpsShardReadinessComparedPackageReviewIdentityDigestSlotCatalog.java",
          "OpsShardReadinessComparedPackageReviewPolicyArchiveService.java",
          "OpsShardReadinessComparedPackageReviewPolicyArchiveSlotCatalog.java",
          "OpsShardReadinessComparedPackageReviewResponse.java",
          "OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.java",
          "OpsShardReadinessComparedPackageReviewRoutePaths.java",
          "OpsShardReadinessComparedPackageReviewSlotCatalog.java",
          "OpsShardReadinessComparedPackageReviewSourceEvidenceService.java",
          "OpsShardReadinessComparedPackageReviewSourceEvidenceSlotCatalog.java",
          "OpsShardReadinessComparedPackageReviewSupport.java");

  @Test
  void implementationMovesWhileTheHttpControllerStaysAtRoot() {
    assertThat(Files.isDirectory(PACKAGE_ROOT)).isTrue();
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve("OpsShardReadinessComparedPackageReviewController.java")))
        .isTrue();
  }

  @Test
  void leafOwnsRouteBytesAndRootAggregationDelegates() throws IOException {
    String leaf =
        read(PACKAGE_ROOT.resolve("OpsShardReadinessComparedPackageReviewRoutePaths.java"));
    String root = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));

    assertThat(leaf)
        .contains(
            "public final class OpsShardReadinessComparedPackageReviewRoutePaths",
            "BASE_PATH = \"/api/v1/ops/shard-readiness\"",
            "COMPARED_PACKAGE_REVIEW_CATALOG",
            "COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT",
            "public static final String CATALOG",
            "public static final String HANDOFF_CLOSEOUT");
    assertThat(root)
        .contains(
            "OpsShardReadinessComparedPackageReviewRoutePaths.*",
            "COMPARED_PACKAGE_REVIEW_CATALOG",
            "COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT");
  }

  @Test
  void evaluationPreflightReadsTheMovedPublicBoundary() throws IOException {
    Path evaluationPackageRoot =
        OPS_ROOT.resolve(Path.of("maintenance", "comparedevidenceevaluationpreflight"));
    for (String suffix :
        List.of(
            "SourceArtifactRuleCatalog.java",
            "IdentityDigestRuleCatalog.java",
            "PolicyRuntimeRuleCatalog.java",
            "ExclusionTraceRuleCatalog.java")) {
      String source =
          read(
              evaluationPackageRoot.resolve(
                  "OpsShardReadinessComparedEvidenceEvaluationPreflight" + suffix));
      assertThat(source)
          .contains(
              "ops.maintenance.comparedpackagereview."
                  + "OpsShardReadinessComparedPackageReviewRoutePaths")
          .doesNotContain("OpsShardReadinessComparedPackageReviewEndpointRefs");
    }
  }

  @Test
  void responseExclusionsAndStructuralRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview."
                + "OpsShardReadinessComparedPackageReviewResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops." + "OpsShardReadinessComparedPackageReviewResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(789);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(1352);
    }
  }

  @Test
  void documentationAndChineseWalkthroughAreCommittedBeforeVerify() throws IOException {
    assertThat(Files.isRegularFile(DOC)).isTrue();
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "direct root 805 -> 789",
            "805 -> 789",
            "700 -> 684",
            "zero-count `ComparedPackageReview` bucket");
    String walkthrough = read(WALKTHROUGH);
    assertThat(walkthrough)
        .contains(
            "version-1838",
            "禁止硬凑",
            "本项目",
            "## 实际工作量说明",
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

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
