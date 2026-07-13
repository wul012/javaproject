package com.codexdemo.orderplatform.ops.maintenance.readability;

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
import org.junit.jupiter.api.Test;

class OpsExtractionV1858Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC =
      Path.of("docs", "ops", "route-cleanup-maintenance-core-extraction-v1858.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段8",
          "v1858-v1862",
          "version-1858-production-excellence-route-cleanup-maintenance-core-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesMaintenanceClosure() throws IOException {
    assertThat(javaFiles(PACKAGE_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsAll(mainFiles());
    assertThat(javaFiles(PACKAGE_TEST_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsAll(testFiles());

    for (String file : mainFiles()) {
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : testFiles()) {
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
  }

  @Test
  void keepsRootAdaptersNarrow() throws IOException {
    String controller =
        read(OPS_ROOT.resolve("OpsShardReadinessRouteCleanupMaintenanceController.java"));
    assertThat(controller)
        .contains(PACKAGE_NAME, "RouteCleanupRoutes.BASE_PATH")
        .doesNotContain(
            "OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_",
            "OpsShardReadinessRoutePaths.BASE_PATH");

    String seeds =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds.java"));
    assertThat(seeds).contains(PACKAGE_NAME);

    for (String file : mainFiles()) {
      assertThat(read(PACKAGE_ROOT.resolve(file)))
          .as(file)
          .doesNotContain(
              "import " + ROOT_PACKAGE + ".OpsShardReadinessRouteCleanup",
              "OpsShardReadinessRoutePaths.");
    }
  }

  @Test
  void ownsMaintenanceRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.ofEntries(
            Map.entry("MAINTENANCE_SEGMENT_CATALOG", "/route-cleanup-maintenance-segment-catalog"),
            Map.entry("MAINTENANCE_CONTINUITY", "/route-cleanup-maintenance-continuity"),
            Map.entry(
                "MAINTENANCE_LATEST_SIBLING_REPORT",
                "/route-cleanup-maintenance-latest-sibling-report"),
            Map.entry(
                "MAINTENANCE_HANDOFF_PAIR_AUDIT", "/route-cleanup-maintenance-handoff-pair-audit"),
            Map.entry("MAINTENANCE_BOUNDARY_DRIFT", "/route-cleanup-maintenance-boundary-drift"),
            Map.entry(
                "MAINTENANCE_SOURCE_PLAN_ALIGNMENT",
                "/route-cleanup-maintenance-source-plan-alignment"),
            Map.entry(
                "MAINTENANCE_TEST_BUDGET_PLAN", "/route-cleanup-maintenance-test-budget-plan"),
            Map.entry(
                "MAINTENANCE_ARCHIVE_MANIFEST", "/route-cleanup-maintenance-archive-manifest"),
            Map.entry("MAINTENANCE_CLOSEOUT", "/route-cleanup-maintenance-closeout"));

    for (Map.Entry<String, String> route : routes.entrySet()) {
      var field = RouteCleanupRoutes.class.getDeclaredField(route.getKey());
      assertThat(field.get(null)).isEqualTo(route.getValue());
      assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
      assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
      assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
      assertThat(field.getName()).hasSizeLessThanOrEqualTo(40);
    }

    String globalRoutes = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    for (String route : routes.keySet()) {
      assertThat(globalRoutes).doesNotContain("ROUTE_CLEANUP_" + route);
    }
    assertThat(Files.readAllLines(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .hasSizeLessThan(1048);
  }

  @Test
  void limitsPublicBoundary() throws ReflectiveOperationException, IOException {
    for (String service : serviceNames()) {
      Class<?> type = Class.forName(PACKAGE_NAME + "." + service);
      var endpoint = type.getDeclaredField("ENDPOINT");
      var profile = type.getDeclaredField("PROFILE");
      assertThat(Modifier.isPublic(endpoint.getModifiers())).as(service).isFalse();
      assertThat(Modifier.isStatic(endpoint.getModifiers())).as(service).isTrue();
      assertThat(Modifier.isFinal(endpoint.getModifiers())).as(service).isTrue();
      assertThat(Modifier.isPublic(profile.getModifiers())).as(service).isFalse();

      assertThat(productionReaders(service + ".ENDPOINT")).as(service).isEmpty();
    }
  }

  @Test
  void relocatesSpotbugsMirrors() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    int moved = 0;
    for (String response : responseNames()) {
      int expected = response.endsWith("SegmentCatalogResponse") ? 4 : 2;
      moved += expected;
      assertThat(count(spotbugs, PACKAGE_NAME + "." + response)).as(response).isEqualTo(expected);
      assertThat(spotbugs).doesNotContain(ROOT_PACKAGE + "." + response);
    }
    assertThat(moved).isEqualTo(20);
  }

  @Test
  void tightensLiveCensus() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(104);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);

    String census = read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md"));
    assertThat(census)
        .contains(
            "Current direct-root Java files: **104**",
            "Remaining direct-root non-controller files to move or collapse: **0**",
            "RouteCleanup web | 0",
            "249 to 231",
            "145 to 127",
            "## v1858 progress");
  }

  @Test
  void usesSharedTestEngine() throws IOException {
    Path support =
        TEST_ROOT.resolve(Path.of("maintenance", "readability", "OpsExtractionTestSupport.java"));
    Path prior =
        TEST_ROOT.resolve(Path.of("maintenance", "readability", "OpsExtractionV1857Tests.java"));
    assertThat(OpsExtractionTestSupport.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(Files.readAllLines(support)).hasSizeLessThanOrEqualTo(80);
    assertThat(Files.readAllLines(prior)).hasSizeLessThanOrEqualTo(380);
    assertThat(read(prior))
        .contains("OpsExtractionTestSupport")
        .doesNotContain(
            "private String read(",
            "private List<Path> javaFiles(",
            "private int hanCount(",
            "private List<String> requiredHeadings(");
  }

  @Test
  void walkthroughPassesGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(RouteCleanupRoutes.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 249 -> 231",
            "movable 145 -> 127",
            "RouteCleanup 141 -> 123",
            "No new production type");

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

  private List<Path> productionReaders(String needle) throws IOException {
    return allJavaFiles(OPS_ROOT).stream()
        .filter(path -> !path.startsWith(PACKAGE_ROOT))
        .filter(path -> contains(path, needle))
        .sorted()
        .toList();
  }

  private boolean contains(Path path, String needle) {
    try {
      return read(path).contains(needle);
    } catch (IOException exception) {
      throw new IllegalStateException(exception);
    }
  }

  private List<String> mainFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService.java",
        "OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService.java",
        "OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceCloseoutService.java",
        "OpsShardReadinessRouteCleanupMaintenanceContinuityResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceContinuityService.java",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService.java",
        "OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService.java",
        "OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService.java",
        "OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService.java",
        "OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService.java");
  }

  private List<String> testFiles() {
    return serviceNames().stream().map(name -> name + "Tests.java").toList();
  }

  private List<String> serviceNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService",
        "OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService",
        "OpsShardReadinessRouteCleanupMaintenanceCloseoutService",
        "OpsShardReadinessRouteCleanupMaintenanceContinuityService",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService",
        "OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService",
        "OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService",
        "OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService",
        "OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService");
  }

  private List<String> responseNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse",
        "OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse",
        "OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse",
        "OpsShardReadinessRouteCleanupMaintenanceContinuityResponse",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse",
        "OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse",
        "OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse",
        "OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse",
        "OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse");
  }
}
