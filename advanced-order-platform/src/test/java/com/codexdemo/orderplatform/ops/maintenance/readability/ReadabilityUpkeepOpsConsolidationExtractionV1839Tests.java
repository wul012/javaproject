package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1839Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "comparedevidenceevaluationpreflight"));
  private static final Path DOC =
      Path.of("docs", "ops", "compared-evidence-evaluation-preflight-extraction-v1839.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1838-v1842",
          "version-1839-production-excellence-compared-evidence-evaluation-preflight-extraction.md");
  private static final List<String> MOVED_FILES =
      List.of(
          "OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionCloseoutService.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightExclusionTraceRuleCatalog.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightGuardCatalog.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestRuleCatalog.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightIdentityDigestService.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeRuleCatalog.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightPolicyRuntimeService.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightResponse.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightRuleCatalog.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactService.java",
          "OpsShardReadinessComparedEvidenceEvaluationPreflightSupport.java");

  @Test
  void implementationMovesAndOnlyTheControllerRemainsAtRoot() {
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceEvaluationPreflightController.java")))
        .isTrue();
  }

  @Test
  void routeOwnerKeepsFiveEndpointsAndRootDelegation() throws IOException {
    String routes =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.java"));
    String root = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    assertThat(routes)
        .contains(
            "public final class OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths",
            "COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_CATALOG",
            "COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_EXCLUSION_CLOSEOUT",
            "public static final String CATALOG",
            "public static final String EXCLUSION_CLOSEOUT");
    assertThat(root)
        .doesNotContain(
            "OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths.*",
            "COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_SOURCE_ARTIFACT",
            "COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_POLICY_RUNTIME");
  }

  @Test
  void upstreamAndDownstreamReadersUseNarrowMovedBoundaries() throws IOException {
    String sourceRules =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceEvaluationPreflightSourceArtifactRuleCatalog.java"));
    String blueprint =
        read(
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "comparedevidencecandidateblueprint",
                    "OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog.java")));
    String profileRegistry =
        read(
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "signedapprovaldrafttextpackageprofilesection",
                    "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService.java")));
    assertThat(sourceRules)
        .contains(
            "ops.maintenance.comparedpackagereview."
                + "OpsShardReadinessComparedPackageReviewRoutePaths");
    assertThat(blueprint)
        .contains(
            "ops.maintenance.comparedevidenceevaluationpreflight."
                + "OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePaths");
    assertThat(profileRegistry)
        .contains(
            "ops.maintenance.comparedevidenceevaluationpreflight."
                + "OpsShardReadinessComparedEvidenceEvaluationPreflightCatalogService");
  }

  @Test
  void analysisAndCountRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.comparedevidenceevaluationpreflight."
                + "OpsShardReadinessComparedEvidenceEvaluationPreflightResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessComparedEvidenceEvaluationPreflightResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(775);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(1352);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix", "789 -> 775", "684 -> 670", "zero unassigned files");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1839",
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
