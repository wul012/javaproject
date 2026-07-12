package com.codexdemo.orderplatform.ops.maintenance.readability;

import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.count;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.hanCount;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.javaFiles;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.letterCount;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.read;
import static com.codexdemo.orderplatform.ops.maintenance.readability.OpsExtractionTestSupport.requiredHeadings;
import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsExtractionV1857Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC =
      Path.of("docs", "ops", "route-cleanup-read-only-gate-extraction-v1857.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段7",
          "v1853-v1857",
          "version-1857-production-excellence-route-cleanup-read-only-gate-extraction.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactClosureAndOwnedTests() throws IOException {
    assertThat(javaFiles(PACKAGE_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsAll(mainFiles());
    assertThat(javaFiles(PACKAGE_TEST_ROOT))
        .extracting(path -> path.getFileName().toString())
        .containsAll(testFiles());

    for (String name : mainFiles()) {
      assertThat(Files.exists(OPS_ROOT.resolve(name))).as(name).isFalse();
    }
    assertThat(
            Files.exists(
                OPS_ROOT.resolve("OpsShardReadinessRouteCleanupEvidenceEntryFactory.java")))
        .isFalse();
    assertThat(read(PACKAGE_ROOT.resolve("OpsShardReadinessRouteCleanupEvidenceCatalog.java")))
        .contains("static Entry entry(");
  }

  @Test
  void keepsAdaptersAndNoReverseEdge() throws IOException {
    assertThat(javaFiles(OPS_ROOT))
        .extracting(path -> path.getFileName().toString())
        .filteredOn(name -> name.startsWith("OpsShardReadinessRouteCleanup"))
        .filteredOn(name -> name.endsWith("Controller.java"))
        .contains(
            "OpsShardReadinessRouteCleanupEvidenceController.java",
            "OpsShardReadinessRouteCleanupGovernanceController.java",
            "OpsShardReadinessRouteCleanupHandoffController.java",
            "OpsShardReadinessRouteCleanupSummaryController.java");

    for (String controller : controllerFiles()) {
      assertThat(read(OPS_ROOT.resolve(controller)))
          .as(controller)
          .contains("ops.maintenance.routecleanup", "RouteCleanupRoutes");
    }
    for (Path path : javaFiles(PACKAGE_ROOT)) {
      assertThat(read(path))
          .as(path.getFileName().toString())
          .doesNotContain("import " + ROOT_PACKAGE + ".OpsShardReadinessRouteCleanup");
    }
  }

  @Test
  void ownsExactRouteBytes() throws ReflectiveOperationException, IOException {
    Map<String, String> routes =
        Map.ofEntries(
            Map.entry("BASE_PATH", "/api/v1/ops/shard-readiness"),
            Map.entry("EVIDENCE_CATALOG", "/route-cleanup-evidence-catalog"),
            Map.entry("PHASE_SUMMARY", "/route-cleanup-phase-summary"),
            Map.entry("BOUNDARY_MATRIX", "/route-cleanup-boundary-matrix"),
            Map.entry("HANDOFF_CHECKLIST", "/route-cleanup-handoff-checklist"),
            Map.entry("ARCHIVE_PLAN", "/route-cleanup-archive-plan"),
            Map.entry("DIGEST", "/route-cleanup-digest"),
            Map.entry("SOURCE_PLAN_ALIGNMENT", "/route-cleanup-source-plan-alignment"),
            Map.entry("RELEASE_HANDOFF", "/route-cleanup-release-handoff"),
            Map.entry("OPERATOR_RUNBOOK", "/route-cleanup-operator-runbook"),
            Map.entry("READ_ONLY_GATE", "/route-cleanup-read-only-gate"));

    assertThat(RouteCleanupRoutes.class.getDeclaredFields())
        .hasSizeGreaterThanOrEqualTo(routes.size());
    for (Map.Entry<String, String> route : routes.entrySet()) {
      var field = RouteCleanupRoutes.class.getDeclaredField(route.getKey());
      assertThat(field.get(null)).isEqualTo(route.getValue());
      assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
      assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
      assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
      assertThat(field.getName()).hasSizeLessThanOrEqualTo(40);
    }
    assertThat(RouteCleanupRoutes.class.getDeclaredMethods())
        .filteredOn(method -> !method.isSynthetic())
        .isEmpty();

    String globalRoutes = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    for (String route : routes.keySet()) {
      if (!route.equals("BASE_PATH")) {
        assertThat(globalRoutes).doesNotContain("ROUTE_CLEANUP_" + route);
      }
    }
    assertThat(Files.readAllLines(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .hasSizeLessThan(1058);
  }

  @Test
  void keepsBoundaryApiMeasured() throws ReflectiveOperationException, IOException {
    Class<?> analyzer = OpsShardReadinessRouteCleanupEvidenceAnalyzer.class;
    assertThat(Modifier.isPublic(analyzer.getModifiers())).isTrue();
    assertThat(Modifier.isFinal(analyzer.getModifiers())).isTrue();
    assertThat(analyzer.getDeclaredMethods())
        .filteredOn(method -> Modifier.isPublic(method.getModifiers()))
        .extracting(method -> method.getName())
        .containsExactlyInAnyOrder(
            "entries",
            "segments",
            "latestJavaVersion",
            "latestJavaVersionLabel",
            "versionsAreContinuous",
            "allEntriesKeepReadOnlyBoundary",
            "forbiddenOperations",
            "segmentFor",
            "boundaryStatus");
    assertThat(analyzer.getDeclaredClasses())
        .singleElement()
        .satisfies(
            type -> {
              assertThat(type.isRecord()).isTrue();
              assertThat(Modifier.isPublic(type.getModifiers())).isTrue();
            });

    for (String service : serviceNames()) {
      if (service.endsWith("EvidenceAnalyzer")) {
        continue;
      }
      var endpoint = Class.forName(PACKAGE_NAME + "." + service).getDeclaredField("ENDPOINT");
      assertThat(Modifier.isPublic(endpoint.getModifiers())).as(service).isTrue();
      assertThat(Modifier.isStatic(endpoint.getModifiers())).as(service).isTrue();
      assertThat(Modifier.isFinal(endpoint.getModifiers())).as(service).isTrue();
    }
    assertThat(publicCatalogs())
        .containsExactly("OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog");

    BoundaryCensus census = boundaryCensus();
    assertThat(census.sourceCount()).isEqualTo(38);
    assertThat(census.edgeCount()).isEqualTo(76);
    assertThat(census.targetNames()).hasSize(21);
    assertThat(census.analyzerReaders()).isEqualTo(34);
  }

  @Test
  void relocatesSpotbugsMirrors() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    int moved = 0;
    for (String response : responseNames()) {
      int expected = response.endsWith("PhaseSummaryResponse") ? 4 : 2;
      moved += expected;
      assertThat(count(spotbugs, PACKAGE_NAME + "." + response)).as(response).isEqualTo(expected);
      assertThat(spotbugs).doesNotContain(ROOT_PACKAGE + "." + response);
    }
    assertThat(moved).isEqualTo(22);
  }

  @Test
  void tightensCensus() throws IOException {
    assertThat(javaFiles(OPS_ROOT)).hasSize(219);
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(OpsExtractionTestSupport::isJava))
          .hasSizeLessThanOrEqualTo(1352);
    }

    String census = read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md"));
    assertThat(census)
        .contains(
            "Current direct-root Java files: **219**",
            "Remaining direct-root non-controller files to move or collapse: **115**",
            "RouteCleanup web | 111",
            "278 to 249",
            "174 to 145",
            "## v1857 progress");
  }

  @Test
  void walkthroughPassesGate() throws IOException {
    assertThat(getClass().getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(RouteCleanupRoutes.class.getSimpleName()).hasSizeLessThanOrEqualTo(40);
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 278 -> 249",
            "movable 174 -> 145",
            "RouteCleanup 170 -> 141",
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
        "OpsShardReadinessRouteCleanupArchivePlanResponse.java",
        "OpsShardReadinessRouteCleanupArchivePlanService.java",
        "OpsShardReadinessRouteCleanupBoundaryMatrixResponse.java",
        "OpsShardReadinessRouteCleanupBoundaryMatrixService.java",
        "OpsShardReadinessRouteCleanupDigestResponse.java",
        "OpsShardReadinessRouteCleanupDigestService.java",
        "OpsShardReadinessRouteCleanupEvidenceAnalyzer.java",
        "OpsShardReadinessRouteCleanupEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupEvidenceResponse.java",
        "OpsShardReadinessRouteCleanupEvidenceService.java",
        "OpsShardReadinessRouteCleanupHandoffAssuranceEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupHandoffChecklistResponse.java",
        "OpsShardReadinessRouteCleanupHandoffChecklistService.java",
        "OpsShardReadinessRouteCleanupHandoffCoreEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupHandoffGovernanceEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupLatestSiblingEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupOperatorRunbookResponse.java",
        "OpsShardReadinessRouteCleanupOperatorRunbookService.java",
        "OpsShardReadinessRouteCleanupPhaseSummaryResponse.java",
        "OpsShardReadinessRouteCleanupPhaseSummaryService.java",
        "OpsShardReadinessRouteCleanupPostCompletionEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupReadinessSeedEvidenceCatalog.java",
        "OpsShardReadinessRouteCleanupReadOnlyGateResponse.java",
        "OpsShardReadinessRouteCleanupReadOnlyGateService.java",
        "OpsShardReadinessRouteCleanupReleaseHandoffResponse.java",
        "OpsShardReadinessRouteCleanupReleaseHandoffService.java",
        "OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse.java",
        "OpsShardReadinessRouteCleanupSourcePlanAlignmentService.java",
        "RouteCleanupRoutes.java");
  }

  private List<String> testFiles() {
    return serviceNames().stream().map(name -> name + "Tests.java").toList();
  }

  private List<String> serviceNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupArchivePlanService",
        "OpsShardReadinessRouteCleanupBoundaryMatrixService",
        "OpsShardReadinessRouteCleanupDigestService",
        "OpsShardReadinessRouteCleanupEvidenceAnalyzer",
        "OpsShardReadinessRouteCleanupEvidenceService",
        "OpsShardReadinessRouteCleanupHandoffChecklistService",
        "OpsShardReadinessRouteCleanupOperatorRunbookService",
        "OpsShardReadinessRouteCleanupPhaseSummaryService",
        "OpsShardReadinessRouteCleanupReadOnlyGateService",
        "OpsShardReadinessRouteCleanupReleaseHandoffService",
        "OpsShardReadinessRouteCleanupSourcePlanAlignmentService");
  }

  private List<String> controllerFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupEvidenceController.java",
        "OpsShardReadinessRouteCleanupGovernanceController.java",
        "OpsShardReadinessRouteCleanupHandoffController.java",
        "OpsShardReadinessRouteCleanupSummaryController.java");
  }

  private List<String> responseNames() {
    return List.of(
        "OpsShardReadinessRouteCleanupArchivePlanResponse",
        "OpsShardReadinessRouteCleanupBoundaryMatrixResponse",
        "OpsShardReadinessRouteCleanupDigestResponse",
        "OpsShardReadinessRouteCleanupEvidenceResponse",
        "OpsShardReadinessRouteCleanupHandoffChecklistResponse",
        "OpsShardReadinessRouteCleanupOperatorRunbookResponse",
        "OpsShardReadinessRouteCleanupPhaseSummaryResponse",
        "OpsShardReadinessRouteCleanupReadOnlyGateResponse",
        "OpsShardReadinessRouteCleanupReleaseHandoffResponse",
        "OpsShardReadinessRouteCleanupSourcePlanAlignmentResponse");
  }

  private Set<String> publicCatalogs() throws ClassNotFoundException {
    Set<String> catalogs = new TreeSet<>();
    for (String file : mainFiles()) {
      if (!file.endsWith("Catalog.java")) {
        continue;
      }
      String typeName = file.substring(0, file.length() - ".java".length());
      if (Modifier.isPublic(Class.forName(PACKAGE_NAME + "." + typeName).getModifiers())) {
        catalogs.add(typeName);
      }
    }
    return catalogs;
  }

  private BoundaryCensus boundaryCensus() throws IOException {
    Set<String> candidateTypes = new TreeSet<>();
    for (String file : mainFiles()) {
      if (!file.equals("RouteCleanupRoutes.java")) {
        candidateTypes.add(file.substring(0, file.length() - ".java".length()));
      }
    }

    Set<Path> sources = new HashSet<>();
    Set<String> targets = new TreeSet<>();
    int edges = 0;
    int analyzerReaders = 0;
    List<Path> productionFiles;
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      productionFiles =
          files
              .filter(Files::isRegularFile)
              .filter(OpsExtractionTestSupport::isJava)
              .filter(path -> !path.startsWith(PACKAGE_ROOT))
              .toList();
    }
    for (Path path : productionFiles) {
      String source = read(path);
      for (String target : candidateTypes) {
        if (containsType(source, target)) {
          sources.add(path);
          targets.add(target);
          edges++;
          if (target.equals("OpsShardReadinessRouteCleanupEvidenceAnalyzer")) {
            analyzerReaders++;
          }
        }
      }
    }
    return new BoundaryCensus(sources.size(), edges, targets, analyzerReaders);
  }

  private boolean containsType(String source, String typeName) {
    return Pattern.compile("\\b" + Pattern.quote(typeName) + "\\b").matcher(source).find();
  }

  private record BoundaryCensus(
      int sourceCount, int edgeCount, Set<String> targetNames, int analyzerReaders) {}
}
