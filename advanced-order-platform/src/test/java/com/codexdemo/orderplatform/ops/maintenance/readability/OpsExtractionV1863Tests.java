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
import org.junit.jupiter.api.Test;

class OpsExtractionV1863Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC = Path.of("docs", "ops", "routecleanup-residue-v1863.md");
  private static final Path WALKTHROUGH =
      Path.of("代码讲解记录_生产雏形阶段8", "v1863-v1867", "v1863-maintenance-residue.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactMaintenanceResidue() throws ReflectiveOperationException {
    for (String file : mainFiles()) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : testFiles()) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }

    Class<?> support =
        Class.forName(
            PACKAGE_NAME + ".OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport");
    assertThat(Modifier.isPublic(support.getModifiers())).isFalse();
    assertThat(Modifier.isFinal(support.getModifiers())).isTrue();
    assertThat(support.getDeclaredMethods())
        .filteredOn(method -> !method.isSynthetic())
        .allSatisfy(method -> assertThat(Modifier.isPublic(method.getModifiers())).isFalse());
    assertThat(
            Files.exists(
                OPS_ROOT.resolve(
                    "OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService.java")))
        .isTrue();
    assertThat(
            Files.exists(
                PACKAGE_ROOT.resolve(
                    "OpsShardReadinessRouteCleanupMaintenanceBoundaryReportService.java")))
        .isFalse();
  }

  @Test
  void keepsRootAdaptersNarrow() throws IOException {
    for (String controller : controllerFiles()) {
      assertThat(read(OPS_ROOT.resolve(controller)))
          .as(controller)
          .contains(PACKAGE_NAME, "RouteCleanupRoutes")
          .doesNotContain("OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_");
    }
  }

  @Test
  void ownsExactRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.ofEntries(
            Map.entry("MAINTENANCE_CONTRACT_FREEZE", "/route-cleanup-maintenance-contract-freeze"),
            Map.entry("MAINTENANCE_GATE_HANDOFF", "/route-cleanup-maintenance-gate-handoff"),
            Map.entry("MAINTENANCE_SHARD_FIELD_MAP", "/route-cleanup-maintenance-shard-field-map"),
            Map.entry(
                "MAINTENANCE_READ_WINDOW_EVIDENCE",
                "/route-cleanup-maintenance-read-window-evidence"),
            Map.entry(
                "MAINTENANCE_RUNTIME_BOUNDARY_CHECKLIST",
                "/route-cleanup-maintenance-runtime-boundary-checklist"),
            Map.entry(
                "MAINTENANCE_CONSUMER_GATE_PACKET",
                "/route-cleanup-maintenance-consumer-gate-packet"),
            Map.entry(
                "MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY",
                "/route-cleanup-maintenance-archive-verifier-summary"),
            Map.entry(
                "MAINTENANCE_CI_BUDGET_LEDGER", "/route-cleanup-maintenance-ci-budget-ledger"),
            Map.entry(
                "MAINTENANCE_ROUTE_INVENTORY_DIGEST",
                "/route-cleanup-maintenance-route-inventory-digest"),
            Map.entry(
                "MAINTENANCE_OPERATOR_SIGNOFF", "/route-cleanup-maintenance-operator-signoff"),
            Map.entry(
                "MAINTENANCE_EXTENDED_CLOSEOUT", "/route-cleanup-maintenance-extended-closeout"));

    for (Map.Entry<String, String> route : routes.entrySet()) {
      var field = RouteCleanupRoutes.class.getField(route.getKey());
      assertThat(field.get(null)).as(route.getKey()).isEqualTo(route.getValue());
      assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
      assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
      assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
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
    assertThat(census.sourceCount()).isEqualTo(2);
    assertThat(census.edgeCount()).isEqualTo(13);
    assertThat(census.targetNames()).containsExactlyInAnyOrderElementsOf(externalTargets());
  }

  @Test
  void repaysTemporaryEndpointVisibility() throws ReflectiveOperationException, IOException {
    for (String service : repaidEndpointServices()) {
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
    assertThat(javaFiles(OPS_ROOT)).hasSize(174);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);
    assertThat(read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md")))
        .contains(
            "Current direct-root Java files: **174**",
            "Remaining direct-root non-controller files to move or collapse: **70**",
            "RouteCleanup web | 66",
            "187 to 174",
            "83 to 70",
            "## v1863 progress");
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 187 -> 174",
            "2-source, 13-edge, 12-target",
            "Package-private support");
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

  private Set<String> externalTargets() {
    return mainFiles().stream()
        .map(this::typeName)
        .filter(name -> !name.endsWith("SustainmentReviewSupport"))
        .collect(java.util.stream.Collectors.toSet());
  }

  private String typeName(String fileName) {
    return fileName.substring(0, fileName.length() - ".java".length());
  }

  private List<String> controllerFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController.java");
  }

  private List<String> mainFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService.java",
        "OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService.java",
        "OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService.java",
        "OpsShardReadinessRouteCleanupMaintenanceContractFreezeService.java",
        "OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService.java",
        "OpsShardReadinessRouteCleanupMaintenanceGateHandoffService.java",
        "OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService.java",
        "OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService.java",
        "OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService.java",
        "OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService.java",
        "OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport.java");
  }

  private List<String> testFiles() {
    return List.of(
        "OpsShardReadinessMaintenanceRoutePathsTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchServiceTests.java",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupportTests.java");
  }

  private List<String> responseNames() {
    return List.of("OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse");
  }

  private List<String> repaidEndpointServices() {
    return List.of(
        "OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService",
        "OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService",
        "OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService",
        "OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService",
        "OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService",
        "OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService",
        "OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService",
        "OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService",
        "OpsShardReadinessRouteCleanupMaintenanceVersionLineageService");
  }
}
