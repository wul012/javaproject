package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1851Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "runtimeexecution"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "runtimeexecution"));
  private static final Path DOC =
      Path.of("docs", "ops", "runtime-execution-evidence-extraction-v1851.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1848-v1852",
          "version-1851-production-excellence-runtime-execution-evidence-extraction.md");
  private static final String PREFIX = "OpsShardReadinessRuntimeExecution";
  private static final String PACKAGE_IMPORT = "ops.maintenance.runtimeexecution";
  private static final List<String> FAMILIES =
      List.of(
          "ApprovalGateInput",
          "ApprovalInputContractHandoff",
          "ApprovalInputTemplateCompatibility",
          "ApprovalInputTemplateCompatibilityIntake",
          "ApprovalInputValueValidation",
          "ArtifactCandidate",
          "LiveReadGate",
          "PacketContribution",
          "PassEvidenceCloseout");
  private static final List<String> SPOTBUG_RESPONSES =
      List.of(
          "ApprovalGateInput",
          "ApprovalInputContractHandoff",
          "ApprovalInputTemplateCompatibility",
          "ApprovalInputValueValidation",
          "ArtifactCandidate",
          "LiveReadGate",
          "PacketContribution",
          "PassEvidenceCloseout");

  @Test
  void runtimeExecutionClosureMovesWhileControllerStaysRootVisible() throws IOException {
    for (String family : FAMILIES) {
      for (String suffix : List.of("Service.java", "Response.java")) {
        String file = PREFIX + family + suffix;
        assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
        assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
      }
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(18);
    }

    Path controller = OPS_ROOT.resolve(PREFIX + "Controller.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller))
        .contains(PACKAGE_IMPORT)
        .doesNotContain("package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution");
  }

  @Test
  void packageTestsFollowImplementationAndShareOneGraphBuilder() throws IOException {
    for (String family : FAMILIES) {
      String file = PREFIX + family + "ServiceTests.java";
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    Path support = PACKAGE_TEST_ROOT.resolve(PREFIX + "TestSupport.java");
    assertThat(Files.isRegularFile(support)).isTrue();
    assertThat(read(support))
        .contains("public final class " + PREFIX + "TestSupport", "passEvidenceCloseoutService()")
        .doesNotContain("OpsShardReadinessServiceGraphTestFactory");
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(10);
    }

    String rootFactory = read(TEST_ROOT.resolve("OpsShardReadinessServiceGraphTestFactory.java"));
    assertThat(rootFactory)
        .contains(PREFIX + "TestSupport.passEvidenceCloseoutService()")
        .doesNotContain("private static " + PREFIX + "LiveReadGateService");
  }

  @Test
  void immutableEvidenceReferencesAreTheOnlyNewProductionSurface() throws IOException {
    for (String family : FAMILIES) {
      String service = read(PACKAGE_ROOT.resolve(PREFIX + family + "Service.java"));
      assertThat(service)
          .contains(
              "public static final String ENDPOINT",
              "public static final String FIXTURE_ENDPOINT",
              "public static final String EVIDENCE_PATH")
          .doesNotContain("OpsShardReadinessRoutePaths");
    }

    String snapshot =
        read(
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "readonlyevidence",
                    "OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.java")));
    assertThat(snapshot)
        .contains(PACKAGE_IMPORT, PREFIX + "ArtifactCandidateService.FIXTURE_ENDPOINT")
        .doesNotContain(
            "import com.codexdemo.orderplatform.ops." + PREFIX + "ArtifactCandidateService;");
  }

  @Test
  void spotbugsMirrorsAndShrinkOnlyRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String family : SPOTBUG_RESPONSES) {
      String response = PREFIX + family + "Response";
      assertThat(spotbugs)
          .contains(PACKAGE_IMPORT + "." + response)
          .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    }
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(187);
    }
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
            "Direct root 500 -> 482",
            "movable 395 -> 377",
            "RuntimeExecution buckets 18 -> 0",
            "runtimeexecution",
            "ReadOnlyEvidence");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1851",
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

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
