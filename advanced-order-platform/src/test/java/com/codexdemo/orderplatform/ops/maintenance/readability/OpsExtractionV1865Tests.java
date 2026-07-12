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

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEndpointManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsExtractionV1865Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "routecleanup"));
  private static final Path DOC = Path.of("docs", "ops", "routecleanup-closeout-v1865.md");
  private static final Path WALKTHROUGH =
      Path.of("代码讲解记录_生产雏形阶段8", "v1863-v1867", "v1865-routecleanup-closeout.md");
  private static final String ROOT_PACKAGE = "com.codexdemo.orderplatform.ops";
  private static final String PACKAGE_NAME = ROOT_PACKAGE + ".maintenance.routecleanup";

  @Test
  void movesExactPostCompletionClosure() throws ReflectiveOperationException {
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
    for (String fixture : fixtureFiles()) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(fixture))).as(fixture).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(fixture))).as(fixture).isFalse();
    }
  }

  @Test
  void keepsDependencyDirection() throws IOException {
    for (Path file : javaFiles(PACKAGE_ROOT)) {
      assertThat(read(file))
          .as(file.getFileName().toString())
          .doesNotContain(
              "import com.codexdemo.orderplatform.ops.OpsShardReadinessRouteCleanup",
              "import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths");
    }
    for (String controller : controllerFiles()) {
      assertThat(read(OPS_ROOT.resolve(controller)))
          .as(controller)
          .contains(PACKAGE_NAME, "RouteCleanupRoutes")
          .doesNotContain("OpsShardReadinessRoutePaths.ROUTE_CLEANUP_");
    }
  }

  @Test
  void ownsCompleteRouteManifest() throws ReflectiveOperationException, IOException {
    for (Map.Entry<String, String> route : routes().entrySet()) {
      var field = RouteCleanupRoutes.class.getField(route.getKey());
      assertThat(field.get(null)).as(route.getKey()).isEqualTo(route.getValue());
      assertThat(Modifier.isPublic(field.getModifiers())).isTrue();
      assertThat(Modifier.isStatic(field.getModifiers())).isTrue();
      assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
    }
    long ownedRoutes =
        Arrays.stream(RouteCleanupRoutes.class.getDeclaredFields())
            .filter(field -> Modifier.isStatic(field.getModifiers()))
            .filter(field -> field.getType().equals(String.class))
            .filter(field -> !field.getName().equals("BASE_PATH"))
            .count();
    assertThat(ownedRoutes).isEqualTo(84);
    assertThat(
            new OpsShardReadinessRouteCleanupEndpointManifestService().manifest().endpointCount())
        .isEqualTo(84);
    assertThat(read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .doesNotContain("ROUTE_CLEANUP_");
  }

  @Test
  void keepsMeasuredBoundary() throws IOException {
    OpsBoundaryTestSupport.BoundaryCensus census =
        boundaryCensus(OPS_ROOT, PACKAGE_ROOT, mainFiles());
    assertThat(census.sourceCount()).isEqualTo(4);
    assertThat(census.edgeCount()).isEqualTo(44);
    assertThat(census.targetNames()).containsExactlyInAnyOrderElementsOf(targetNames());
  }

  @Test
  void repaysAllEndpointVisibility() throws ReflectiveOperationException, IOException {
    for (Path file : javaFiles(PACKAGE_ROOT)) {
      String typeName = typeName(file.getFileName().toString());
      Class<?> type = Class.forName(PACKAGE_NAME + "." + typeName);
      var endpoint = declaredField(type, "ENDPOINT");
      if (endpoint != null) {
        assertThat(Modifier.isPublic(endpoint.getModifiers())).as(typeName).isFalse();
        assertThat(externalReaders(OPS_ROOT, PACKAGE_ROOT, typeName + ".ENDPOINT"))
            .as(typeName)
            .isEmpty();
      }
      var profile = declaredField(type, "PROFILE");
      if (profile != null) {
        assertThat(Modifier.isPublic(profile.getModifiers())).as(typeName).isFalse();
      }
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
    assertThat(javaFiles(OPS_ROOT)).hasSize(108);
    assertThat(allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1352);
    assertThat(read(Path.of("docs", "ops", "extraction-endgame-census-v1828.md")))
        .contains(
            "Current direct-root Java files: **108**",
            "Remaining direct-root non-controller files to move or collapse: **4**",
            "RouteCleanup web | 0",
            "152 to 108",
            "48 to 4",
            "## v1865 progress");
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Root 152 -> 108",
            "4-source / 44-edge / 44-target",
            "all 84 suffixes");
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

  private java.lang.reflect.Field declaredField(Class<?> type, String name) {
    try {
      return type.getDeclaredField(name);
    } catch (NoSuchFieldException exception) {
      return null;
    }
  }

  private Set<String> targetNames() {
    return mainFiles().stream().map(this::typeName).collect(Collectors.toSet());
  }

  private String typeName(String fileName) {
    return fileName.substring(0, fileName.length() - ".java".length());
  }

  private List<String> controllerFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupAssuranceController.java",
        "OpsShardReadinessRouteCleanupCompletionController.java",
        "OpsShardReadinessRouteCleanupGovernanceController.java",
        "OpsShardReadinessRouteCleanupPostCompletionController.java");
  }

  private List<String> fixtureFiles() {
    return List.of(
        "OpsShardReadinessRouteCleanupServiceFixtures.java",
        "OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.java");
  }

  private List<String> responseNames() {
    return families().stream()
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
        "AcceptanceReceipt",
        "ArchiveHandoffReceipt",
        "AuditTrail",
        "CiRunAttestation",
        "CompletionAuditDigest",
        "CompletionCertificate",
        "CompletionIndex",
        "ConsumerSignoffPacket",
        "EvidenceRegister",
        "FinalArchivePlan",
        "FinalVerification",
        "FixtureCoverageIndex",
        "MaintenanceBoundaryReport",
        "OperationalSnapshot",
        "PolicyGuard",
        "PostCompletionCloseout",
        "PostPushCloseout",
        "ReleaseEvidenceBundle",
        "ReviewerPacket",
        "TagManifest",
        "ThirdRunCloseout",
        "TransitionBrief");
  }

  private Map<String, String> routes() {
    return Map.ofEntries(
        Map.entry("AUDIT_TRAIL", "/route-cleanup-audit-trail"),
        Map.entry("ACCEPTANCE_RECEIPT", "/route-cleanup-acceptance-receipt"),
        Map.entry("EVIDENCE_REGISTER", "/route-cleanup-evidence-register"),
        Map.entry("OPERATIONAL_SNAPSHOT", "/route-cleanup-operational-snapshot"),
        Map.entry("POLICY_GUARD", "/route-cleanup-policy-guard"),
        Map.entry("REVIEWER_PACKET", "/route-cleanup-reviewer-packet"),
        Map.entry("TRANSITION_BRIEF", "/route-cleanup-transition-brief"),
        Map.entry("FINAL_VERIFICATION", "/route-cleanup-final-verification"),
        Map.entry("FINAL_ARCHIVE_PLAN", "/route-cleanup-final-archive-plan"),
        Map.entry("THIRD_RUN_CLOSEOUT", "/route-cleanup-third-run-closeout"),
        Map.entry("COMPLETION_INDEX", "/route-cleanup-completion-index"),
        Map.entry("COMPLETION_CERTIFICATE", "/route-cleanup-completion-certificate"),
        Map.entry("POST_PUSH_CLOSEOUT", "/route-cleanup-post-push-closeout"),
        Map.entry("CI_RUN_ATTESTATION", "/route-cleanup-ci-run-attestation"),
        Map.entry("TAG_MANIFEST", "/route-cleanup-tag-manifest"),
        Map.entry("RELEASE_EVIDENCE_BUNDLE", "/route-cleanup-release-evidence-bundle"),
        Map.entry("CONSUMER_SIGNOFF_PACKET", "/route-cleanup-consumer-signoff-packet"),
        Map.entry("ARCHIVE_HANDOFF_RECEIPT", "/route-cleanup-archive-handoff-receipt"),
        Map.entry("MAINTENANCE_BOUNDARY_REPORT", "/route-cleanup-maintenance-boundary-report"),
        Map.entry("FIXTURE_COVERAGE_INDEX", "/route-cleanup-fixture-coverage-index"),
        Map.entry("COMPLETION_AUDIT_DIGEST", "/route-cleanup-completion-audit-digest"),
        Map.entry("POST_COMPLETION_CLOSEOUT", "/route-cleanup-post-completion-closeout"));
  }
}
