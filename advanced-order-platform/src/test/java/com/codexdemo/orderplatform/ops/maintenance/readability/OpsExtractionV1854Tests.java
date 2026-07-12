package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsExtractionV1854Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseapproval"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "releaseapproval"));
  private static final Path DOC =
      Path.of("docs", "ops", "release-approval-closure-extraction-v1854.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段7",
          "v1853-v1857",
          "version-1854-production-excellence-release-approval-closure-extraction.md");
  private static final String FAMILY_PREFIX = "ReleaseApproval";
  private static final String PACKAGE_IMPORT = "ops.maintenance.releaseapproval";

  @Test
  void completeReleaseApprovalClosureMovesOutOfDirectRoot() throws IOException {
    assertThat(javaFiles(PACKAGE_ROOT)).hasSize(119);
    assertThat(javaFiles(PACKAGE_ROOT))
        .allMatch(path -> path.getFileName().toString().startsWith(FAMILY_PREFIX));
    assertThat(javaFiles(OPS_ROOT).stream().filter(this::isReleaseApprovalFile)).isEmpty();

    assertThat(javaFiles(PACKAGE_TEST_ROOT)).hasSize(6);
    assertThat(javaFiles(PACKAGE_TEST_ROOT))
        .allMatch(path -> path.getFileName().toString().startsWith(FAMILY_PREFIX));
    assertThat(javaFiles(TEST_ROOT).stream().filter(this::isReleaseApprovalFile)).isEmpty();
  }

  @Test
  void compositionBoundaryIsPublicWhileInternalBuildersStayPrivate() throws IOException {
    assertThat(read(PACKAGE_ROOT.resolve("ReleaseApprovalRehearsalRequest.java")))
        .contains("public record ReleaseApprovalRehearsalRequest(");
    assertThat(read(PACKAGE_ROOT.resolve("ReleaseApprovalRehearsalResponse.java")))
        .contains("public record ReleaseApprovalRehearsalResponse(");
    assertThat(read(PACKAGE_ROOT.resolve("ReleaseApprovalRehearsalResponseBuilder.java")))
        .contains(
            "public final class ReleaseApprovalRehearsalResponseBuilder",
            "public ReleaseApprovalRehearsalResponse build(");

    for (String internalBuilder :
        List.of(
            "ReleaseApprovalContextHeaderField.java",
            "ReleaseApprovalDigestSupport.java",
            "ReleaseApprovalVerificationHintBuilder.java",
            "ReleaseApprovalVerificationWarningDigestBuilder.java",
            "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder.java")) {
      assertThat(read(PACKAGE_ROOT.resolve(internalBuilder)))
          .as(internalBuilder)
          .doesNotContain("public final class", "public record");
    }
  }

  @Test
  void constantsAndHeaderNormalizationBelongToTheExtractedFamily() throws IOException {
    String contractConstants = read(PACKAGE_ROOT.resolve("ReleaseApprovalContractConstants.java"));
    String upstreamConstants =
        read(PACKAGE_ROOT.resolve("ReleaseApprovalUpstreamContractConstants.java"));

    assertThat(contractConstants)
        .contains(
            "public final class ReleaseApprovalContractConstants",
            "public static final String RELEASE_APPROVAL_REHEARSAL_ENDPOINT");
    assertThat(upstreamConstants)
        .contains(
            "public final class ReleaseApprovalUpstreamContractConstants",
            "public static final String NODE_V210_APPROVAL_BINDING_CONTRACT_VERSION");
    assertThat(contractConstants.lines().count()).isLessThanOrEqualTo(400);
    assertThat(upstreamConstants.lines().count()).isLessThanOrEqualTo(800);

    for (Path familyFile : javaFiles(PACKAGE_ROOT)) {
      assertThat(read(familyFile))
          .as(familyFile.toString())
          .doesNotContain(
              "import com.codexdemo.orderplatform.ops.OpsEvidenceService;", "OpsEvidenceService.");
    }
    assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve("ReleaseApprovalContextHeaderField.java")))
        .isTrue();
    assertThat(Files.exists(OPS_ROOT.resolve("ContextHeaderField.java"))).isFalse();
  }

  @Test
  void forwardingBuilderAndSharedTestFixtureStayNarrow() throws IOException {
    String executionDeniedSupport =
        read(
            PACKAGE_ROOT.resolve(
                "ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport.java"));
    assertThat(executionDeniedSupport).contains("static final class ReceiptBuilder");
    assertThat(
            Files.exists(
                PACKAGE_ROOT.resolve(
                    "ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder.java")))
        .isFalse();

    assertThat(read(PACKAGE_TEST_ROOT.resolve("ReleaseApprovalRehearsalTestSupport.java")))
        .contains(
            "public abstract class ReleaseApprovalRehearsalTestSupport",
            "OpsEvidenceServiceTestFixtures");
  }

  @Test
  void externalCompositionAndRecordConsumersImportTheNewPackage() throws IOException {
    for (String consumer : List.of("OpsEvidenceService.java", "OpsOverviewController.java")) {
      assertThat(read(OPS_ROOT.resolve(consumer))).as(consumer).contains(PACKAGE_IMPORT);
    }
    assertThat(
            read(
                OPS_ROOT.resolve(
                    Path.of(
                        "maintenance",
                        "credentialresolver",
                        "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveBoundaryCatalog.java"))))
        .contains(PACKAGE_IMPORT);
    assertThat(
            read(
                OPS_ROOT.resolve(
                    Path.of(
                        "maintenance",
                        "sandboxconnection",
                        "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog.java"))))
        .contains(PACKAGE_IMPORT);
  }

  @Test
  void staticReleaseFixtureDependencyRemainsAnImmutablePublicBoundary() throws IOException {
    String artifact = read(OPS_ROOT.resolve("OpsEvidenceStaticReleaseArtifact.java"));
    assertThat(artifact)
        .contains(
            "public enum OpsEvidenceStaticReleaseArtifact",
            "public String version()",
            "public String endpoint()");
    assertThat(read(PACKAGE_ROOT.resolve("ReleaseApprovalRehearsalResponseBuilder.java")))
        .contains("OpsEvidenceStaticReleaseArtifact");
  }

  @Test
  void namedHotspotBudgetsMovePathsWithoutChangingCaps() throws IOException {
    String budgets =
        read(
            Path.of(
                "src",
                "test",
                "java",
                "com",
                "codexdemo",
                "orderplatform",
                "maintainability",
                "JavaMaintainabilityBudgetTests.java"));
    assertThat(budgets)
        .contains(
            "ops/maintenance/releaseapproval/ReleaseApprovalVerificationHintBuilder.java\",",
            "70L",
            "ops/maintenance/releaseapproval/ReleaseApprovalVerificationWarningDigestBuilder.java\",",
            "421L",
            "ops/maintenance/releaseapproval/ReleaseApprovalRehearsalResponseBuilder.java\",",
            "ops/maintenance/releaseapproval/ReleaseApprovalVerificationSupport.java\",",
            "412L",
            "ops/maintenance/releaseapproval/ReleaseApprovalVerificationHintContributionCatalog.java\",",
            "382L",
            "ops/maintenance/releaseapproval/ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder.java\",",
            "793L")
        .doesNotContain("ops/ReleaseApprovalVerificationHintBuilder.java\"");
  }

  @Test
  void spotbugsAndShrinkOnlyCensusFollowTheWholeFamily() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains("com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApproval")
        .doesNotContain("com.codexdemo.orderplatform.ops.ReleaseApproval");

    assertThat(javaFiles(OPS_ROOT)).hasSize(174);
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1352);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 429 -> 310",
            "movable 324 -> 206",
            "ReleaseApproval buckets 118 -> 0",
            "ContextHeaderField waiver 1 -> 0",
            "releaseapproval");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1854",
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

  private List<Path> javaFiles(Path root) throws IOException {
    try (Stream<Path> files = Files.list(root)) {
      return files.filter(Files::isRegularFile).filter(this::isJava).sorted().toList();
    }
  }

  private boolean isReleaseApprovalFile(Path path) {
    return path.getFileName().toString().startsWith(FAMILY_PREFIX);
  }

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
