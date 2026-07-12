package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsExtractionV1855Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "readinesscore"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "readinesscore"));
  private static final Path DOC = Path.of("docs", "ops", "readiness-core-extraction-v1855.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段7",
          "v1853-v1857",
          "version-1855-production-excellence-readiness-core-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_IMPORT = "ops.maintenance.readinesscore";
  private static final List<String> PREFIXES =
      List.of(
          "",
          "ActiveShardPlanHandoff",
          "DeclaredOperatorLifecycle",
          "Echo",
          "EvidenceHandoff",
          "EvidenceIndex",
          "EvidenceVerification",
          "Hardening",
          "LiveReadGatePlan",
          "OperatorServiceLifecycle");

  @Test
  void completeCoreClosureMovesWhileAdaptersStayRoot() throws IOException {
    assertThat(javaFiles(PACKAGE_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrderElementsOf(expectedMainFiles());

    assertThat(javaFiles(OPS_ROOT).stream().filter(this::isCoreFile).toList()).isEmpty();
    for (String controller :
        List.of(
            "OpsShardReadinessController.java",
            "OpsShardReadinessEvidenceController.java",
            "OpsShardReadinessLifecyclePlanController.java")) {
      assertThat(read(OPS_ROOT.resolve(controller))).as(controller).contains(PACKAGE_IMPORT);
    }
  }

  @Test
  void behaviorTestsMoveButCrossFamilyTestsStayRoot() throws IOException {
    assertThat(javaFiles(PACKAGE_TEST_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrderElementsOf(expectedTestFiles());

    for (String rootTest :
        List.of(
            "OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests.java",
            "OpsShardReadinessRoutePathsTests.java")) {
      assertThat(read(TEST_ROOT.resolve(rootTest))).as(rootTest).contains(PACKAGE_IMPORT);
    }
    for (String packageTest :
        List.of(
            "OpsShardReadinessPrototypeConsumerGateServiceTests.java",
            "OpsShardReadinessPrototypeEvidenceServiceTests.java",
            "OpsShardReadinessPrototypeHandoffServiceTests.java")) {
      Path path = TEST_ROOT.resolve(Path.of("maintenance", "prototype", packageTest));
      assertThat(read(path)).as(packageTest).contains(PACKAGE_IMPORT);
    }
  }

  @Test
  void coreOwnsRoutesWithoutBorrowingTheRootAggregator() throws IOException {
    String service = read(PACKAGE_ROOT.resolve("OpsShardReadinessService.java"));
    assertThat(service)
        .contains(
            "public static final String BASE_PATH = \"/api/v1/ops/shard-readiness\"",
            "public static final String EVIDENCE_INDEX_PATH = \"/evidence-index\"",
            "public static final String EVIDENCE_VERIFICATION_PATH =",
            "public static final String EVIDENCE_HANDOFF_PATH = \"/evidence-handoff\"",
            "public static final String ENDPOINT = BASE_PATH");

    for (String prefix : List.of("EvidenceIndex", "EvidenceVerification", "EvidenceHandoff")) {
      assertThat(read(PACKAGE_ROOT.resolve("OpsShardReadiness" + prefix + "Service.java")))
          .as(prefix)
          .contains("OpsShardReadinessService.")
          .doesNotContain("OpsShardReadinessRoutePaths");
    }

    assertThat(read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .contains(
            "import " + ROOT_PACKAGE + ".maintenance.readinesscore.OpsShardReadinessService;",
            "BASE_PATH = OpsShardReadinessService.BASE_PATH",
            "EVIDENCE_INDEX = OpsShardReadinessService.EVIDENCE_INDEX_PATH",
            "OpsShardReadinessService.EVIDENCE_VERIFICATION_PATH",
            "EVIDENCE_HANDOFF = OpsShardReadinessService.EVIDENCE_HANDOFF_PATH");
  }

  @Test
  void allProductionConsumersPointAtTheExtractedBoundary() throws IOException {
    for (Path consumer : productionConsumers()) {
      assertThat(read(consumer)).as(consumer.toString()).contains(PACKAGE_IMPORT);
    }

    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(
              files
                  .filter(Files::isRegularFile)
                  .filter(this::isJava)
                  .filter(this::containsMovedTypeImport))
          .isEmpty();
    }
  }

  @Test
  void spotbugsFqnsMoveWithoutGrowingTheWaiverSet() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String prefix : PREFIXES.subList(1, PREFIXES.size())) {
      String type = "OpsShardReadiness" + prefix + "Response";
      assertThat(count(spotbugs, ROOT_PACKAGE + ".maintenance.readinesscore." + type))
          .as(type)
          .isEqualTo(2);
      assertThat(spotbugs).doesNotContain(ROOT_PACKAGE + "." + type);
    }
  }

  @Test
  void censusAndTotalFileRatchetsOnlyTighten() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(187);
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1352);
    }

    String census = read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md"));
    assertThat(census)
        .contains(
            "Current direct-root Java files: **187**",
            "Remaining direct-root non-controller files to move or collapse: **83**",
            "310 to 290",
            "206 to 186");
  }

  @Test
  void explanationAndNewGuardObeyTheEleganceGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 310 -> 290",
            "movable 206 -> 186",
            "No route string");

    String walkthrough = read(WALKTHROUGH);
    assertThat(requiredHeadings(walkthrough))
        .containsExactly(
            "实际工作量说明",
            "入口路由",
            "响应模型",
            "上游证据配置",
            "服务层核心流程",
            "Java 证据检查",
            "mini-kv 证据检查",
            "阻断与安全边界",
            "测试覆盖",
            "一句话总结");
    assertThat(hanCount(walkthrough)).isGreaterThanOrEqualTo(3000);
    assertThat(walkthrough).contains("设计说明先于实现", "本版不改 mini-kv");
  }

  private List<String> expectedMainFiles() {
    List<String> files = new ArrayList<>();
    for (String prefix : PREFIXES) {
      files.add("OpsShardReadiness" + prefix + "Service.java");
      files.add("OpsShardReadiness" + prefix + "Response.java");
    }
    return files;
  }

  private List<String> expectedTestFiles() {
    return PREFIXES.stream()
        .map(prefix -> "OpsShardReadiness" + prefix + "ServiceTests.java")
        .toList();
  }

  private List<Path> productionConsumers() {
    return List.of(
        OPS_ROOT.resolve("OpsShardReadinessController.java"),
        OPS_ROOT.resolve("OpsShardReadinessEvidenceController.java"),
        OPS_ROOT.resolve("OpsShardReadinessEvidenceEndpoints.java"),
        OPS_ROOT.resolve("OpsShardReadinessLifecyclePlanController.java"),
        OPS_ROOT.resolve(
            Path.of("maintenance", "prototype", "OpsShardReadinessPrototypeEvidenceService.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "readonlyevidence",
                "OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "readonlyevidence",
                "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "readonlyevidence",
                "OpsShardReadinessReadOnlyEvidenceCatalogService.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "readonlyevidence",
                "OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "runtimeexecution",
                "OpsShardReadinessRuntimeExecutionArtifactCandidateService.java")),
        OPS_ROOT.resolve(Path.of("maintenance", "v1contract", "OpsShardReadinessV1Contract.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance", "v1contract", "OpsShardReadinessV1ContractAlignmentService.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance", "v1contract", "OpsShardReadinessV1ContractAlignmentSnapshot.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "v1contract",
                "OpsShardReadinessV1ContractConsumerProbePlanSnapshot.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "v1contract",
                "OpsShardReadinessV1ContractEvidencePacketSnapshot.java")),
        OPS_ROOT.resolve(
            Path.of(
                "maintenance",
                "v1contract",
                "OpsShardReadinessV1ContractHandoffManifestSnapshot.java")));
  }

  private boolean isCoreFile(Path path) {
    return expectedMainFiles().contains(path.getFileName().toString());
  }

  private boolean containsMovedTypeImport(Path path) {
    try {
      String source = read(path);
      return PREFIXES.stream()
          .flatMap(prefix -> Stream.of(prefix + "Service", prefix + "Response"))
          .anyMatch(
              suffix ->
                  source.contains("import " + ROOT_PACKAGE + ".OpsShardReadiness" + suffix + ";"));
    } catch (IOException exception) {
      throw new IllegalStateException(exception);
    }
  }

  private List<String> requiredHeadings(String source) {
    return source
        .lines()
        .filter(line -> line.startsWith("## "))
        .map(line -> line.substring(3).trim())
        .toList();
  }

  private int hanCount(String source) {
    return (int)
        source.codePoints().filter(codePoint -> codePoint >= 0x4E00 && codePoint <= 0x9FFF).count();
  }

  private int count(String source, String needle) {
    int matches = 0;
    int index = 0;
    while ((index = source.indexOf(needle, index)) >= 0) {
      matches++;
      index += needle.length();
    }
    return matches;
  }

  private List<Path> javaFiles(Path directory) throws IOException {
    try (Stream<Path> paths = Files.list(directory)) {
      return paths.filter(Files::isRegularFile).filter(this::isJava).toList();
    }
  }

  private boolean isJava(Path path) {
    return path.getFileName().toString().endsWith(".java");
  }

  private String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
