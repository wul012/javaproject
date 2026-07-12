package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.CloseoutSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.CloseoutSource;
import com.codexdemo.orderplatform.ops.maintenance.prototype.OpsShardReadinessPrototypeEvidenceService.PrototypeRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsExtractionV1856Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "prototype"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "prototype"));
  private static final Path DOC = Path.of("docs", "ops", "prototype-closure-extraction-v1856.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段7",
          "v1853-v1857",
          "version-1856-production-excellence-prototype-closure-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_IMPORT = "ops.maintenance.prototype";

  @Test
  void movesCompletePrototypeClosure() throws IOException {
    assertThat(javaFiles(PACKAGE_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrderElementsOf(mainFiles());

    assertThat(
            javaFiles(OPS_ROOT).stream()
                .map(path -> path.getFileName().toString())
                .filter(name -> name.startsWith("OpsShardReadinessPrototype")))
        .containsExactlyInAnyOrder(
            "OpsShardReadinessPrototypeController.java",
            "OpsShardReadinessPrototypeConsumerGateController.java",
            "OpsShardReadinessPrototypeHandoffController.java");
  }

  @Test
  void keepsAdaptersAndMovesBehaviorTests() throws IOException {
    assertThat(javaFiles(PACKAGE_TEST_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrder(
            "OpsShardReadinessPrototypeEvidenceServiceTests.java",
            "OpsShardReadinessPrototypeHandoffServiceTests.java",
            "OpsShardReadinessPrototypeConsumerGateServiceTests.java");

    for (String controller :
        List.of(
            "OpsShardReadinessPrototypeController.java",
            "OpsShardReadinessPrototypeHandoffController.java",
            "OpsShardReadinessPrototypeConsumerGateController.java")) {
      assertThat(read(OPS_ROOT.resolve(controller)))
          .as(controller)
          .contains(PACKAGE_IMPORT, "PrototypeRoutes")
          .doesNotContain("SHARD_READINESS_PROTOTYPE_");
    }

    for (String test :
        List.of(
            "OpsShardReadinessPrototypeControllerSplitTests.java",
            "OpsShardReadinessPrototypeHandoffControllerSplitTests.java",
            "OpsShardReadinessPrototypeConsumerGateControllerSplitTests.java")) {
      assertThat(read(TEST_ROOT.resolve(test))).as(test).contains("PrototypeRoutes");
    }
  }

  @Test
  void ownsRouteBytesOutsideGlobalTable() throws IOException {
    assertThat(PrototypeRoutes.class.getDeclaredFields()).hasSize(31);
    assertThat(PrototypeRoutes.class.getDeclaredFields())
        .allSatisfy(
            field -> {
              assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
              assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
              assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
              assertThat(field.getName()).hasSizeLessThanOrEqualTo(40);
            });

    String routePaths = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    assertThat(routePaths).doesNotContain("SHARD_READINESS_PROTOTYPE_", "\"/prototype-");
    assertThat(Files.readAllLines(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .hasSizeLessThan(1111);
  }

  @Test
  void narrowsRouteCleanupToSnapshotPort() throws IOException {
    assertThat(CloseoutSource.class.getDeclaredMethods())
        .singleElement()
        .satisfies(method -> assertThat(method.getName()).isEqualTo("snapshot"));
    assertThat(CloseoutSnapshot.class.getRecordComponents())
        .extracting(component -> component.getName())
        .containsExactly("version", "executionAllowed", "postCompletionCloseoutEndpoint", "status");

    String prototype = read(PACKAGE_ROOT.resolve("OpsShardReadinessPrototypeEvidenceService.java"));
    assertThat(prototype)
        .contains("CloseoutSource closeoutSource", "CloseoutSnapshot closeout")
        .doesNotContain(
            "OpsShardReadinessRouteCleanupPostCompletionCloseoutService",
            "OpsShardReadinessRouteCleanupPostCompletionCloseoutResponse");

    String adapter =
        read(OPS_ROOT.resolve("OpsShardReadinessRouteCleanupPostCompletionCloseoutService.java"));
    assertThat(adapter)
        .contains(
            "implements CloseoutSource",
            "public CloseoutSnapshot snapshot()",
            "closeout.version()",
            "closeout.executionAllowed()",
            "closeout.postCompletionCloseoutEndpoint()",
            "closeout.status()");
  }

  @Test
  void limitsPublicEndpointsToTwoReaders() throws IOException {
    String service = read(PACKAGE_ROOT.resolve("OpsShardReadinessPrototypeEvidenceService.java"));
    assertThat(service)
        .contains(
            "public static final String CATALOG_ENDPOINT",
            "public static final String FIELD_ALIGNMENT_ENDPOINT")
        .doesNotContain(
            "public static final String FIXTURE_ECHO_ENDPOINT",
            "public static final String CLOSEOUT_ENDPOINT");

    assertThat(productionReaders("OpsShardReadinessPrototypeEvidenceService.CATALOG_ENDPOINT"))
        .extracting(path -> path.getFileName().toString())
        .containsExactly("OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.java");
    assertThat(
            productionReaders("OpsShardReadinessPrototypeEvidenceService.FIELD_ALIGNMENT_ENDPOINT"))
        .extracting(path -> path.getFileName().toString())
        .containsExactly("OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.java");
  }

  @Test
  void movesSpotbugsFqnsWithoutWaivers() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String type : responseFiles()) {
      assertThat(count(spotbugs, ROOT_PACKAGE + ".maintenance.prototype." + type))
          .as(type)
          .isEqualTo(2);
      assertThat(spotbugs).doesNotContain(ROOT_PACKAGE + "." + type);
    }
  }

  @Test
  void tightensCensusAndRouteBudget() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(231);
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1352);
    }

    String census = read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md"));
    assertThat(census)
        .contains(
            "Current direct-root Java files: **231**",
            "Remaining direct-root non-controller files to move or collapse: **127**",
            "290 to 278",
            "186 to 174",
            "PrototypeConsumerGate | 0");
  }

  @Test
  void walkthroughPassesEleganceGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(CloseoutSource.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(CloseoutSnapshot.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(PrototypeRoutes.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 290 -> 278",
            "movable 186 -> 174",
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
    assertThat(hanCount(walkthrough) * 2).isGreaterThanOrEqualTo(letterCount(walkthrough));
    assertThat(walkthrough).contains("禁止硬凑", "本项目");
  }

  private List<String> mainFiles() {
    return List.of(
        "OpsShardReadinessPrototypeCatalogResponse.java",
        "OpsShardReadinessPrototypeEvidenceCatalog.java",
        "OpsShardReadinessPrototypeEvidenceResponse.java",
        "OpsShardReadinessPrototypeEvidenceService.java",
        "OpsShardReadinessPrototypeConsumerGateCatalogResponse.java",
        "OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.java",
        "OpsShardReadinessPrototypeConsumerGateEvidenceResponse.java",
        "OpsShardReadinessPrototypeConsumerGateService.java",
        "OpsShardReadinessPrototypeHandoffCatalogResponse.java",
        "OpsShardReadinessPrototypeHandoffEvidenceCatalog.java",
        "OpsShardReadinessPrototypeHandoffEvidenceResponse.java",
        "OpsShardReadinessPrototypeHandoffService.java");
  }

  private List<String> responseFiles() {
    return List.of(
        "OpsShardReadinessPrototypeCatalogResponse",
        "OpsShardReadinessPrototypeEvidenceResponse",
        "OpsShardReadinessPrototypeConsumerGateCatalogResponse",
        "OpsShardReadinessPrototypeConsumerGateEvidenceResponse",
        "OpsShardReadinessPrototypeHandoffCatalogResponse",
        "OpsShardReadinessPrototypeHandoffEvidenceResponse");
  }

  private List<Path> productionReaders(String needle) throws IOException {
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      return files
          .filter(Files::isRegularFile)
          .filter(this::isJava)
          .filter(path -> !path.startsWith(PACKAGE_ROOT))
          .filter(path -> contains(path, needle))
          .sorted()
          .toList();
    }
  }

  private boolean contains(Path path, String needle) {
    try {
      return read(path).contains(needle);
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

  private int letterCount(String source) {
    return (int) source.codePoints().filter(Character::isLetter).count();
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
    try (Stream<Path> files = Files.list(directory)) {
      return files.filter(Files::isRegularFile).filter(this::isJava).toList();
    }
  }

  private boolean isJava(Path path) {
    return path.getFileName().toString().endsWith(".java");
  }

  private String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
