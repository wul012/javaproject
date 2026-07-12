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

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsExtractionV1862Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC =
      Path.of("docs", "ops", "route-cleanup-sustainment-evidence-extraction-v1862.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段8",
          "v1858-v1862",
          "version-1862-production-excellence-route-cleanup-sustainment-evidence-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactEvidenceClosure() throws ReflectiveOperationException {
    for (String file : mainFiles()) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : testFiles()) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    Class<?> fixture =
        Class.forName(
            PACKAGE_NAME + ".OpsShardReadinessRouteCleanupMaintenanceSustainmentServiceFixture");
    assertThat(Modifier.isPublic(fixture.getModifiers())).isFalse();
  }

  @Test
  void keepsRootAdapterNarrow() throws IOException {
    String controller =
        read(
            OPS_ROOT.resolve(
                "OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceController.java"));
    assertThat(controller)
        .contains(PACKAGE_NAME, "RouteCleanupRoutes.BASE_PATH")
        .doesNotContain(
            "OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_",
            "OpsShardReadinessRoutePaths.BASE_PATH");

    for (String file : mainFiles()) {
      assertThat(read(PACKAGE_ROOT.resolve(file)))
          .as(file)
          .doesNotContain(
              "import " + ROOT_PACKAGE + ".OpsShardReadinessRouteCleanup",
              "OpsShardReadinessRoutePaths.");
    }
  }

  @Test
  void ownsEvidenceRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.of(
            "MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST",
                "/route-cleanup-maintenance-handoff-acceptance-digest",
            "MAINTENANCE_DEPENDENCY_BOUNDARY_MAP",
                "/route-cleanup-maintenance-dependency-boundary-map",
            "MAINTENANCE_ARCHIVE_RETENTION_CALENDAR",
                "/route-cleanup-maintenance-archive-retention-calendar",
            "MAINTENANCE_TEST_EVIDENCE_ROLLUP", "/route-cleanup-maintenance-test-evidence-rollup",
            "MAINTENANCE_OPERATIONS_SCORECARD", "/route-cleanup-maintenance-operations-scorecard",
            "MAINTENANCE_SUSTAINMENT_CLOSEOUT", "/route-cleanup-maintenance-sustainment-closeout");

    for (Map.Entry<String, String> route : routes.entrySet()) {
      var field = RouteCleanupRoutes.class.getField(route.getKey());
      assertThat(field.get(null)).as(route.getKey()).isEqualTo(route.getValue());
      assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
      assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
      assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
      assertThat(route.getKey()).hasSizeLessThanOrEqualTo(40);
    }

    String globalRoutes = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    for (String route : routes.keySet()) {
      assertThat(globalRoutes).doesNotContain("ROUTE_CLEANUP_" + route);
    }
    assertThat(globalRoutes.lines().count()).isLessThan(1001);
  }

  @Test
  void keepsMeasuredProductionBoundary() throws IOException {
    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, mainFiles());
    assertThat(census.sourceCount()).isEqualTo(6);
    assertThat(census.edgeCount()).isEqualTo(20);
    assertThat(census.targetNames())
        .containsExactlyInAnyOrderElementsOf(mainFiles().stream().map(this::typeName).toList());
  }

  @Test
  void exposesOnlyMeasuredEndpoints() throws ReflectiveOperationException, IOException {
    Map<Class<?>, List<String>> publicEndpoints =
        Map.of(
            OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService.class,
                List.of(
                    "OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.java"),
            OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService.class,
                List.of("OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.java"),
            OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService.class,
                List.of(
                    "OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.java",
                    "OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.java"),
            OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService.class,
                List.of("OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.java"),
            OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.class,
                List.of(
                    "OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.java",
                    "OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.java",
                    "OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.java"));

    for (Map.Entry<Class<?>, List<String>> endpoint : publicEndpoints.entrySet()) {
      var field = endpoint.getKey().getDeclaredField("ENDPOINT");
      assertThat(Modifier.isPublic(field.getModifiers())).as(endpoint.getKey().getName()).isTrue();
      assertThat(
              externalReaders(
                  OPS_ROOT, PACKAGE_ROOT, endpoint.getKey().getSimpleName() + ".ENDPOINT"))
          .extracting(path -> path.getFileName().toString())
          .containsExactlyInAnyOrderElementsOf(endpoint.getValue());
    }

    var privateEndpoint =
        OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService.class
            .getDeclaredField("ENDPOINT");
    assertThat(Modifier.isPublic(privateEndpoint.getModifiers())).isFalse();
    assertThat(
            externalReaders(
                OPS_ROOT,
                PACKAGE_ROOT,
                "OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService.ENDPOINT"))
        .isEmpty();
    for (Class<?> service : serviceTypes()) {
      assertThat(Modifier.isPublic(service.getDeclaredField("PROFILE").getModifiers()))
          .as(service.getName())
          .isFalse();
    }
  }

  @Test
  void repaysCatalogVisibility() throws ReflectiveOperationException, IOException {
    String catalogName = "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog";
    Class<?> catalog = Class.forName(PACKAGE_NAME + "." + catalogName);
    assertThat(Modifier.isPublic(catalog.getModifiers())).isFalse();
    assertThat(Modifier.isFinal(catalog.getModifiers())).isTrue();
    assertThat(catalog.getDeclaredMethods())
        .filteredOn(method -> !method.isSynthetic())
        .allSatisfy(
            method -> {
              assertThat(Modifier.isPublic(method.getModifiers())).isFalse();
              assertThat(Modifier.isStatic(method.getModifiers())).isTrue();
            });
    assertThat(catalog.getDeclaredClasses())
        .singleElement()
        .satisfies(item -> assertThat(Modifier.isPublic(item.getModifiers())).isFalse());
    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, List.of(catalogName + ".java"));
    assertThat(census.sourceCount()).isZero();
    assertThat(census.edgeCount()).isZero();
    assertThat(census.targetNames()).isEmpty();
  }

  @Test
  void relocatesSpotBugsMirrors() throws IOException {
    String exclusions = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String response : responseNames()) {
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
    assertThat(javaFiles(OPS_ROOT)).hasSize(187);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);
    assertThat(read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md")))
        .contains(
            "Current direct-root Java files: **187**",
            "Remaining direct-root non-controller files to move or collapse: **83**",
            "RouteCleanup web | 79",
            "199 to 187",
            "95 to 83",
            "## v1862 progress");
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 199 -> 187",
            "6-source, 20-edge, 12-target",
            "Repay catalog visibility");
  }

  @Test
  void walkthroughPassesGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(RouteCleanupRoutes.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
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

  private String typeName(String fileName) {
    return fileName.substring(0, fileName.length() - ".java".length());
  }

  private List<String> mainFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService.java",
        "OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService.java",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService.java",
        "OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.java",
        "OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService.java");
  }

  private List<String> testFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentServiceFixture.java",
        "OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupServiceTests.java");
  }

  private List<Class<?>> serviceTypes() {
    return List.of(
        OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService.class,
        OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService.class,
        OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService.class,
        OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService.class,
        OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService.class,
        OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService.class);
  }

  private List<String> responseNames() {
    return serviceTypes().stream()
        .map(Class::getSimpleName)
        .map(name -> name.replace("Service", "Response"))
        .toList();
  }
}
