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
import org.junit.jupiter.api.Test;

class OpsExtractionV1859Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC =
      Path.of("docs", "ops", "route-cleanup-upkeep-core-extraction-v1859.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段8",
          "v1858-v1862",
          "version-1859-production-excellence-route-cleanup-upkeep-core-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesUpkeepClosure() throws IOException {
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
  void keepsControllerAsRootAdapter() throws IOException {
    String controller =
        read(OPS_ROOT.resolve("OpsShardReadinessRouteCleanupMaintenanceUpkeepController.java"));
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
  void ownsUpkeepRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.of(
            "MAINTENANCE_UPKEEP_CATALOG",
            "/route-cleanup-maintenance-upkeep-catalog",
            "MAINTENANCE_CONSUMER_HANDOFF_MATRIX",
            "/route-cleanup-maintenance-consumer-handoff-matrix",
            "MAINTENANCE_CI_EXPECTATION_MANIFEST",
            "/route-cleanup-maintenance-ci-expectation-manifest",
            "MAINTENANCE_ROUTE_TOPOLOGY_INDEX",
            "/route-cleanup-maintenance-route-topology-index",
            "MAINTENANCE_FAIL_CLOSED_POLICY",
            "/route-cleanup-maintenance-fail-closed-policy");

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
        .hasSizeLessThan(1031);
  }

  @Test
  void exposesMeasuredCatalogBoundary() throws ReflectiveOperationException, IOException {
    Class<?> catalog =
        Class.forName(PACKAGE_NAME + ".OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog");
    assertThat(Modifier.isPublic(catalog.getModifiers())).isFalse();
    assertThat(Modifier.isFinal(catalog.getModifiers())).isTrue();
    assertThat(catalog.getDeclaredMethods())
        .filteredOn(method -> !method.isSynthetic())
        .extracting(method -> method.getName())
        .containsExactlyInAnyOrder("items", "firstServiceVersion", "latestRouteVersion");
    assertThat(catalog.getDeclaredMethods())
        .filteredOn(method -> !method.isSynthetic())
        .allSatisfy(
            method -> {
              assertThat(Modifier.isPublic(method.getModifiers())).isFalse();
              assertThat(Modifier.isStatic(method.getModifiers())).isTrue();
            });
    assertThat(catalog.getDeclaredClasses())
        .singleElement()
        .satisfies(
            item -> {
              assertThat(item.isRecord()).isTrue();
              assertThat(Modifier.isPublic(item.getModifiers())).isFalse();
            });

    Class<?> seeds =
        Class.forName(PACKAGE_NAME + ".OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds");
    assertThat(Modifier.isPublic(seeds.getModifiers())).isFalse();

    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, mainFiles());
    assertThat(census.sourceCount()).isEqualTo(1);
    assertThat(census.edgeCount()).isEqualTo(10);
    assertThat(census.targetNames()).hasSize(10);
  }

  @Test
  void narrowsEndpointVisibility() throws ReflectiveOperationException, IOException {
    for (String service : serviceNames()) {
      Class<?> type = Class.forName(PACKAGE_NAME + "." + service);
      var endpoint = type.getDeclaredField("ENDPOINT");
      var profile = type.getDeclaredField("PROFILE");
      assertThat(Modifier.isPublic(endpoint.getModifiers())).as(service).isFalse();
      assertThat(Modifier.isStatic(endpoint.getModifiers())).as(service).isTrue();
      assertThat(Modifier.isFinal(endpoint.getModifiers())).as(service).isTrue();
      assertThat(Modifier.isPublic(profile.getModifiers())).as(service).isFalse();

      assertThat(externalReaders(OPS_ROOT, PACKAGE_ROOT, service + ".ENDPOINT"))
          .as(service)
          .isEmpty();
    }

    for (String service : v1858ServiceNames()) {
      Class<?> type = Class.forName(PACKAGE_NAME + "." + service);
      assertThat(Modifier.isPublic(type.getDeclaredField("ENDPOINT").getModifiers()))
          .as(service)
          .isFalse();
      assertThat(externalReaders(OPS_ROOT, PACKAGE_ROOT, service + ".ENDPOINT"))
          .as(service)
          .isEmpty();
    }
  }

  @Test
  void relocatesSpotbugsMirrors() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String response : responseNames()) {
      assertThat(count(spotbugs, PACKAGE_NAME + "." + response)).as(response).isEqualTo(2);
      assertThat(spotbugs).doesNotContain(ROOT_PACKAGE + "." + response);
    }
  }

  @Test
  void tightensLiveCensus() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(174);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);

    String census = read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md"));
    assertThat(census)
        .contains(
            "Current direct-root Java files: **174**",
            "Remaining direct-root non-controller files to move or collapse: **70**",
            "RouteCleanup web | 66",
            "231 to 219",
            "127 to 115",
            "## v1859 progress");
  }

  @Test
  void walkthroughPassesGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(RouteCleanupRoutes.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 231 -> 219",
            "movable 127 -> 115",
            "RouteCleanup 123 -> 111",
            "Repay temporary visibility");

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
        "OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService.java",
        "OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService.java",
        "OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService.java",
        "OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService.java",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.java",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds.java",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService.java");
  }

  private List<String> testFiles() {
    return serviceNames().stream().map(name -> name + "Tests.java").toList();
  }

  private List<String> serviceNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestService",
        "OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService",
        "OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService",
        "OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexService",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService");
  }

  private List<String> responseNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceCiExpectationManifestResponse",
        "OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse",
        "OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse",
        "OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse",
        "OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse");
  }

  private List<String> v1858ServiceNames() {
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
}
