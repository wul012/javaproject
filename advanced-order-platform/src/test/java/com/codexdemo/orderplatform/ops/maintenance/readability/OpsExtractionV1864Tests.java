package com.codexdemo.orderplatform.ops.maintenance.readability;

import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsBoundaryTestSupport.boundaryCensus;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsBoundaryTestSupport.externalReaders;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.allJavaFiles;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.count;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.hanCount;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.javaFiles;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.letterCount;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.read;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.requiredHeadings;
import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsExtractionV1864Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC = Path.of("docs", "ops", "routecleanup-handoff-v1864.md");
  private static final Path WALKTHROUGH =
      Path.of("代码讲解记录_生产雏形阶段8", "v1863-v1867", "v1864-routecleanup-handoff.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactHandoffClosure() throws ReflectiveOperationException {
    for (String file : mainFiles()) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
      assertThat(
              Modifier.isPublic(Class.forName(PACKAGE_NAME + "." + typeName(file)).getModifiers()))
          .as(file)
          .isTrue();
    }
    for (String file : testFiles()) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
  }

  @Test
  void keepsDependencyDirection() throws IOException {
    for (String file : mainFiles()) {
      assertThat(read(PACKAGE_ROOT.resolve(file)))
          .as(file)
          .doesNotContain("import com.codexdemo.orderplatform.ops.OpsShardReadinessRouteCleanup");
    }
    for (String controller : controllerFiles()) {
      String source = read(OPS_ROOT.resolve(controller));
      assertThat(source).as(controller).contains(PACKAGE_NAME, "RouteCleanupRoutes");
      for (String route : routes().keySet()) {
        assertThat(source)
            .as(controller + ": " + route)
            .doesNotContain("OpsShardReadinessRoutePaths.ROUTE_CLEANUP_" + route);
      }
    }
  }

  @Test
  void ownsExactHandoffRoutes() throws ReflectiveOperationException, IOException {
    for (Map.Entry<String, String> route : routes().entrySet()) {
      var field = RouteCleanupRoutes.class.getField(route.getKey());
      assertThat(field.get(null)).as(route.getKey()).isEqualTo(route.getValue());
      assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
      assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
      assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
    }

    String globalRoutes = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    for (String route : routes().keySet()) {
      assertThat(globalRoutes).doesNotContain("ROUTE_CLEANUP_" + route);
    }
  }

  @Test
  void keepsMeasuredBoundary() throws IOException {
    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, mainFiles());
    assertThat(census.sourceCount()).isEqualTo(3);
    assertThat(census.edgeCount()).isEqualTo(22);
    assertThat(census.targetNames()).containsExactlyInAnyOrderElementsOf(targetNames());
  }

  @Test
  void limitsEndpointVisibility() throws ReflectiveOperationException, IOException {
    Map<String, String> publicReaders = publicEndpointReaders();
    for (String service : serviceNames()) {
      Class<?> type = Class.forName(PACKAGE_NAME + "." + service);
      boolean expectedPublic = publicReaders.containsKey(service);
      assertThat(Modifier.isPublic(type.getDeclaredField("ENDPOINT").getModifiers()))
          .as(service)
          .isEqualTo(expectedPublic);
      assertThat(Modifier.isPublic(type.getDeclaredField("PROFILE").getModifiers()))
          .as(service)
          .isFalse();

      List<Path> readers = externalReaders(OPS_ROOT, PACKAGE_ROOT, service + ".ENDPOINT");
      if (expectedPublic) {
        assertThat(readers)
            .extracting(path -> path.getFileName().toString())
            .containsExactly(publicReaders.get(service));
      } else {
        assertThat(readers).as(service).isEmpty();
      }
    }
  }

  @Test
  void relocatesSpotBugsMirrors() throws IOException {
    String exclusions = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String response : spotBugsResponses()) {
      assertThat(count(exclusions, "name=\"" + PACKAGE_NAME + "." + response + "\""))
          .as(response)
          .isEqualTo(2);
      assertThat(count(exclusions, "name=\"" + ROOT_PACKAGE + "." + response + "\""))
          .as(response)
          .isZero();
    }
  }

  @Test
  void tightensLiveCensus() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(108);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);
    assertThat(read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md")))
        .contains(
            "Current direct-root Java files: **108**",
            "Remaining direct-root non-controller files to move or collapse: **4**",
            "RouteCleanup web | 0",
            "174 to 152",
            "70 to 48",
            "## v1864 progress");
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Root 174 -> 152",
            "10 sources, 38 edges, and 22 targets",
            "three measured ENDPOINT fields");
  }

  @Test
  void walkthroughPassesGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(DOC.getFileName().toString()).hasSizeLessThanOrEqualTo(40);
    assertThat(WALKTHROUGH.getFileName().toString()).hasSizeLessThanOrEqualTo(40);
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

  private Map<String, String> routes() {
    return Map.ofEntries(
        Map.entry("SUITE_CLOSEOUT", "/route-cleanup-suite-closeout"),
        Map.entry("ARCHIVE_VERIFICATION", "/route-cleanup-archive-verification"),
        Map.entry("CONSUMER_PACKET", "/route-cleanup-consumer-packet"),
        Map.entry("CI_EVIDENCE", "/route-cleanup-ci-evidence"),
        Map.entry("ENDPOINT_MANIFEST", "/route-cleanup-endpoint-manifest"),
        Map.entry("REGRESSION_GUARD", "/route-cleanup-regression-guard"),
        Map.entry("HANDOFF_BUNDLE", "/route-cleanup-handoff-bundle"),
        Map.entry("CONTINUITY_REPORT", "/route-cleanup-continuity-report"),
        Map.entry("CONSUMER_CHECKLIST", "/route-cleanup-consumer-checklist"),
        Map.entry("FINAL_DIGEST", "/route-cleanup-final-digest"),
        Map.entry("EXTENDED_CLOSEOUT", "/route-cleanup-extended-closeout"));
  }

  private Map<String, String> publicEndpointReaders() {
    return Map.of();
  }

  private Set<String> targetNames() {
    return mainFiles().stream().map(this::typeName).collect(Collectors.toSet());
  }

  private String typeName(String fileName) {
    return fileName.substring(0, fileName.length() - ".java".length());
  }

  private List<String> controllerFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupGovernanceController.java",
        "OpsShardReadinessRouteCleanupHandoffController.java",
        "OpsShardReadinessRouteCleanupSummaryController.java");
  }

  private List<String> serviceNames() {
    return families().stream()
        .map(name -> "OpsShardReadinessRouteCleanup" + name + "Service")
        .toList();
  }

  private List<String> spotBugsResponses() {
    return families().stream()
        .filter(name -> !name.equals("ContinuityReport"))
        .map(name -> "OpsShardReadinessRouteCleanup" + name + "Response")
        .toList();
  }

  private List<String> mainFiles() {
    return families().stream()
        .flatMap(
            name ->
                java.util.stream.Stream.of(
                    "OpsShardReadinessRouteCleanup" + name + "Response.java",
                    "OpsShardReadinessRouteCleanup" + name + "Service.java"))
        .toList();
  }

  private List<String> testFiles() {
    return families().stream()
        .map(name -> "OpsShardReadinessRouteCleanup" + name + "ServiceTests.java")
        .toList();
  }

  private List<String> families() {
    return List.of(
        "ArchiveVerification",
        "CiEvidence",
        "ConsumerChecklist",
        "ConsumerPacket",
        "ContinuityReport",
        "EndpointManifest",
        "ExtendedCloseout",
        "FinalDigest",
        "HandoffBundle",
        "RegressionGuard",
        "SuiteCloseout");
  }
}
