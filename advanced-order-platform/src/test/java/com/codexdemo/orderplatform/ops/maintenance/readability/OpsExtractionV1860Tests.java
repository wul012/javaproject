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

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReadinessGateService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceVersionLineageService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OpsExtractionV1860Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC =
      Path.of("docs", "ops", "route-cleanup-upkeep-assurance-extraction-v1860.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段8",
          "v1858-v1862",
          "version-1860-production-excellence-route-cleanup-upkeep-assurance-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactAssuranceClosure() {
    for (String file : mainFiles()) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : testFiles()) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
  }

  @Test
  void keepsRootAdapterNarrow() throws IOException {
    String controller =
        read(
            OPS_ROOT.resolve(
                "OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController.java"));
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
  void ownsAssuranceRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.of(
            "MAINTENANCE_ARCHIVE_DIGEST_LEDGER", "/route-cleanup-maintenance-archive-digest-ledger",
            "MAINTENANCE_OPERATOR_REVIEW_PACKET",
                "/route-cleanup-maintenance-operator-review-packet",
            "MAINTENANCE_VERSION_LINEAGE", "/route-cleanup-maintenance-version-lineage",
            "MAINTENANCE_READINESS_GATE", "/route-cleanup-maintenance-readiness-gate",
            "MAINTENANCE_UPKEEP_CLOSEOUT", "/route-cleanup-maintenance-upkeep-closeout");

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
    assertThat(globalRoutes.lines().count()).isLessThan(1021);
  }

  @Test
  void keepsMeasuredProductionBoundary() throws IOException {
    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, mainFiles());
    assertThat(census.sourceCount()).isEqualTo(1);
    assertThat(census.edgeCount()).isEqualTo(10);
    assertThat(census.targetNames())
        .containsExactlyInAnyOrderElementsOf(mainFiles().stream().map(this::typeName).toList());
  }

  @Test
  void exposesOnlyMeasuredEndpoints() throws ReflectiveOperationException, IOException {
    for (Class<?> service : serviceTypes()) {
      assertThat(Modifier.isPublic(service.getDeclaredField("ENDPOINT").getModifiers()))
          .as(service.getName())
          .isFalse();
      assertThat(externalReaders(OPS_ROOT, PACKAGE_ROOT, service.getSimpleName() + ".ENDPOINT"))
          .as(service.getName())
          .isEmpty();
      assertThat(Modifier.isPublic(service.getDeclaredField("PROFILE").getModifiers()))
          .as(service.getName())
          .isFalse();
    }
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
  void keepsTestEnginesSmall() throws IOException {
    Path supportRoot = TEST_ROOT.resolve(Path.of("maintenance", "readability"));
    assertThat(read(supportRoot.resolve("OpsExtractionTestSupport.java")).lines())
        .hasSizeLessThanOrEqualTo(80);
    assertThat(read(supportRoot.resolve("OpsBoundaryTestSupport.java")).lines())
        .hasSizeLessThanOrEqualTo(80);
    assertThat(OpsBoundaryTestSupport.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
  }

  @Test
  void tightensLiveCensus() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(152);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);
    assertThat(read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md")))
        .contains(
            "Current direct-root Java files: **152**",
            "Remaining direct-root non-controller files to move or collapse: **48**",
            "RouteCleanup web | 44",
            "219 to 209",
            "115 to 105",
            "## v1860 progress");
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 219 -> 209",
            "5-source, 18-edge, 10-target",
            "OpsExtractionTestSupport");
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
    return serviceNames().stream()
        .flatMap(name -> Set.of(name + "Response.java", name + "Service.java").stream())
        .sorted()
        .toList();
  }

  private List<String> testFiles() {
    return serviceNames().stream().map(name -> name + "ServiceTests.java").toList();
  }

  private List<String> serviceNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedger",
        "OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacket",
        "OpsShardReadinessRouteCleanupMaintenanceVersionLineage",
        "OpsShardReadinessRouteCleanupMaintenanceReadinessGate",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseout");
  }

  private List<String> responseNames() {
    return serviceNames().stream().map(name -> name + "Response").toList();
  }

  private List<Class<?>> serviceTypes() {
    return List.of(
        OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService.class,
        OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService.class,
        OpsShardReadinessRouteCleanupMaintenanceVersionLineageService.class,
        OpsShardReadinessRouteCleanupMaintenanceReadinessGateService.class,
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService.class);
  }
}
