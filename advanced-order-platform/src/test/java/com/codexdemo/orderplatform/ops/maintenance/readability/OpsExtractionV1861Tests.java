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

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpsExtractionV1861Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC =
      Path.of("docs", "ops", "route-cleanup-sustainment-core-extraction-v1861.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段8",
          "v1858-v1862",
          "version-1861-production-excellence-route-cleanup-sustainment-core-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactSustainmentCore() {
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
            OPS_ROOT.resolve("OpsShardReadinessRouteCleanupMaintenanceSustainmentController.java"));
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
  void ownsSustainmentRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.of(
            "MAINTENANCE_RELEASE_CHECKLIST", "/route-cleanup-maintenance-release-checklist",
            "MAINTENANCE_REMEDIATION_QUEUE", "/route-cleanup-maintenance-remediation-queue",
            "MAINTENANCE_FRESHNESS_WINDOW", "/route-cleanup-maintenance-freshness-window",
            "MAINTENANCE_OWNERSHIP_REGISTER", "/route-cleanup-maintenance-ownership-register",
            "MAINTENANCE_RISK_LEDGER", "/route-cleanup-maintenance-risk-ledger");

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
    assertThat(globalRoutes.lines().count()).isLessThan(1011);
  }

  @Test
  void keepsMeasuredProductionBoundary() throws IOException {
    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, mainFiles());
    assertThat(census.sourceCount()).isEqualTo(5);
    assertThat(census.edgeCount()).isEqualTo(19);
    assertThat(census.targetNames())
        .containsExactlyInAnyOrderElementsOf(mainFiles().stream().map(this::typeName).toList());
  }

  @Test
  void exposesOnlyRiskEndpoint() throws ReflectiveOperationException, IOException {
    var riskEndpoint =
        OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.class.getDeclaredField(
            "ENDPOINT");
    assertThat(Modifier.isPublic(riskEndpoint.getModifiers())).isTrue();
    assertThat(
            externalReaders(
                OPS_ROOT,
                PACKAGE_ROOT,
                "OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.ENDPOINT"))
        .extracting(path -> path.getFileName().toString())
        .containsExactly(
            "OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.java",
            "OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService.java",
            "OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.java");

    for (Class<?> service : privateEndpointServices()) {
      assertThat(Modifier.isPublic(service.getDeclaredField("ENDPOINT").getModifiers()))
          .as(service.getName())
          .isFalse();
      assertThat(externalReaders(OPS_ROOT, PACKAGE_ROOT, service.getSimpleName() + ".ENDPOINT"))
          .as(service.getName())
          .isEmpty();
    }
    for (Class<?> service : serviceTypes()) {
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
  void tightensLiveCensus() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(199);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);
    assertThat(read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md")))
        .contains(
            "Current direct-root Java files: **199**",
            "Remaining direct-root non-controller files to move or collapse: **95**",
            "RouteCleanup web | 91",
            "209 to 199",
            "105 to 95",
            "## v1861 progress");
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 209 -> 199",
            "5-source, 19-edge, 10-target",
            "RiskLedger");
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
        "OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService.java",
        "OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService.java",
        "OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService.java",
        "OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService.java",
        "OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.java");
  }

  private List<String> testFiles() {
    return serviceNames().stream()
        .map(name -> name.replace("Service", "ServiceTests") + ".java")
        .toList();
  }

  private List<String> responseNames() {
    return serviceNames().stream().map(name -> name.replace("Service", "Response")).toList();
  }

  private List<Class<?>> serviceTypes() {
    return List.of(
        OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService.class,
        OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService.class,
        OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService.class,
        OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService.class,
        OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService.class);
  }

  private List<Class<?>> privateEndpointServices() {
    return List.of(
        OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService.class,
        OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService.class,
        OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService.class,
        OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService.class);
  }

  private List<String> serviceNames() {
    return serviceTypes().stream().map(Class::getSimpleName).toList();
  }
}
