package com.codexdemo.orderplatform.ops.maintenance.readability;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseCatalog;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsExtractionV1866Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path OVERVIEW_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "overview"));
  private static final Path EVIDENCE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "evidencecore"));
  private static final Path DESIGN = Path.of("docs", "ops", "ops-root-endgame-v1866.md");
  private static final Path WAIVERS = Path.of("docs", "ops", "extraction-waivers.md");
  private static final Path CENSUS = Path.of("docs", "ops", "extraction-endgame-census-v1828.md");
  private static final Path WALKTHROUGH =
      Path.of("代码讲解记录_生产雏形阶段8", "v1863-v1867", "v1866-ops-root-endgame.md");

  @Test
  void movesFinalRootFamilies() throws IOException {
    assertThat(OpsExtractionTestSupport.javaFiles(OPS_ROOT)).hasSize(104);
    assertThat(OpsExtractionTestSupport.allJavaFiles(OPS_ROOT)).hasSizeLessThanOrEqualTo(1336);

    assertThat(rootNames())
        .doesNotContain(
            "OpsOverviewService.java",
            "OpsOverviewResponse.java",
            "OpsEvidenceStaticReleaseArtifact.java",
            "OpsEvidenceStaticReleaseDispatchTable.java");
    assertThat(fileNames(OVERVIEW_ROOT))
        .containsExactlyInAnyOrder("OpsOverviewResponse.java", "OpsOverviewService.java");
    assertThat(fileNames(EVIDENCE_ROOT))
        .containsExactlyInAnyOrder("StaticReleaseCatalog.java", "StaticReleaseSections.java");
  }

  @Test
  void keepsRetainedRootExact() throws IOException {
    Set<String> nonControllers =
        rootNames().stream()
            .filter(name -> !name.endsWith("Controller.java"))
            .collect(Collectors.toSet());

    assertThat(rootNames().stream().filter(name -> name.endsWith("Controller.java"))).hasSize(100);
    assertThat(nonControllers)
        .containsExactlyInAnyOrder(
            "OpsEvidenceResponse.java",
            "OpsEvidenceService.java",
            "OpsShardReadinessEvidenceEndpoints.java",
            "OpsShardReadinessRoutePaths.java");
  }

  @Test
  void keepsStaticCatalogExact() {
    Map<String, String> actual =
        Arrays.stream(StaticReleaseCatalog.Artifact.values())
            .collect(
                Collectors.toMap(
                    Enum::name, artifact -> artifact.version() + "|" + artifact.endpoint()));

    assertThat(actual)
        .containsExactlyInAnyOrderEntriesOf(
            Map.ofEntries(
                entry(
                    "RELEASE_VERIFICATION_MANIFEST",
                    "java-release-verification-manifest.v1|/contracts/release-verification-manifest.sample.json"),
                entry(
                    "DEPLOYMENT_ROLLBACK_EVIDENCE",
                    "java-deployment-rollback-evidence.v1|/contracts/deployment-rollback-evidence.sample.json"),
                entry(
                    "RELEASE_BUNDLE_MANIFEST",
                    "java-release-bundle-manifest.v1|/contracts/release-bundle-manifest.sample.json"),
                entry(
                    "RELEASE_HANDOFF_CHECKLIST_FIXTURE",
                    "java-release-handoff-checklist-fixture.v1|/contracts/release-handoff-checklist.fixture.json"),
                entry(
                    "RELEASE_AUDIT_RETENTION_FIXTURE",
                    "java-release-audit-retention-fixture.v1|/contracts/release-audit-retention.fixture.json"),
                entry(
                    "RELEASE_OPERATOR_SIGNOFF_FIXTURE",
                    "java-release-operator-signoff-fixture.v1|/contracts/release-operator-signoff.fixture.json"),
                entry(
                    "ROLLBACK_APPROVER_EVIDENCE_FIXTURE",
                    "java-rollback-approver-evidence-fixture.v1|/contracts/rollback-approver-evidence.fixture.json"),
                entry(
                    "ROLLBACK_APPROVAL_HANDOFF",
                    "java-rollback-approval-handoff.v1|/contracts/rollback-approval-handoff.sample.json"),
                entry(
                    "ROLLBACK_APPROVAL_RECORD_FIXTURE",
                    "java-rollback-approval-record-fixture.v1|/contracts/rollback-approval-record.fixture.json"),
                entry(
                    "ROLLBACK_SQL_REVIEW_GATE",
                    "java-rollback-sql-review-gate.v1|/contracts/rollback-sql-review-gate.sample.json"),
                entry(
                    "PRODUCTION_SECRET_SOURCE_CONTRACT",
                    "java-production-secret-source-contract.v1|/contracts/production-secret-source-contract.sample.json"),
                entry(
                    "PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT",
                    "java-production-deployment-runbook-contract.v1|/contracts/production-deployment-runbook-contract.sample.json")));
  }

  @Test
  void keepsSplitSmallAndPrivate() throws Exception {
    Path catalog = EVIDENCE_ROOT.resolve("StaticReleaseCatalog.java");
    Path sections = EVIDENCE_ROOT.resolve("StaticReleaseSections.java");
    Class<?> sectionType =
        Class.forName(
            "com.codexdemo.orderplatform.ops.maintenance.evidencecore.StaticReleaseSections");

    assertThat(OpsExtractionTestSupport.read(catalog).lines()).hasSizeLessThanOrEqualTo(250);
    assertThat(OpsExtractionTestSupport.read(sections).lines()).hasSizeLessThanOrEqualTo(500);
    assertThat(Modifier.isPublic(StaticReleaseCatalog.class.getModifiers())).isTrue();
    assertThat(Modifier.isFinal(StaticReleaseCatalog.class.getModifiers())).isTrue();
    assertThat(Modifier.isPublic(sectionType.getModifiers())).isFalse();
    assertThat(fileNames(EVIDENCE_ROOT))
        .allSatisfy(name -> assertThat(stem(name)).hasSizeLessThanOrEqualTo(40));
  }

  @Test
  void pointsRootCompositionInward() throws IOException {
    String controller =
        OpsExtractionTestSupport.read(OPS_ROOT.resolve("OpsOverviewController.java"));
    String evidence = OpsExtractionTestSupport.read(OPS_ROOT.resolve("OpsEvidenceService.java"));
    String releaseBuilder =
        OpsExtractionTestSupport.read(
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "releaseapproval",
                    "ReleaseApprovalRehearsalResponseBuilder.java")));

    assertThat(controller)
        .contains(
            "ops.maintenance.overview.OpsOverviewResponse",
            "ops.maintenance.overview.OpsOverviewService");
    assertThat(evidence).contains("ops.maintenance.evidencecore.StaticReleaseCatalog");
    assertThat(releaseBuilder).contains("StaticReleaseCatalog.Artifact");
    assertThat(OpsExtractionTestSupport.read(EVIDENCE_ROOT.resolve("StaticReleaseSections.java")))
        .doesNotContain("OpsOverviewController", "OpsShardReadinessRoutePaths");
  }

  @Test
  void movesSpotbugsMirrors() throws IOException {
    String exclusions = OpsExtractionTestSupport.read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(
            OpsExtractionTestSupport.count(
                exclusions,
                "com.codexdemo.orderplatform.ops.maintenance.overview.OpsOverviewResponse$Application"))
        .isEqualTo(2);
    assertThat(
            OpsExtractionTestSupport.count(
                exclusions, "com.codexdemo.orderplatform.ops.OpsOverviewResponse$Application"))
        .isZero();
  }

  @Test
  void closesCensusAndWaivers() throws IOException {
    assertThat(OpsExtractionTestSupport.read(CENSUS))
        .contains(
            "live after v1866",
            "Current direct-root Java files: **104**",
            "Remaining direct-root non-controller files to move or collapse: **0**",
            "counted buckets sum to **104**",
            "## v1866 progress");
    assertThat(OpsExtractionTestSupport.read(WAIVERS))
        .contains("StaticReleaseCatalog.java", "StaticReleaseSections.java")
        .doesNotContain("`OpsEvidenceStaticReleaseArtifact.java` and");
  }

  @Test
  void freezesChineseWalkthrough() throws IOException {
    String source = OpsExtractionTestSupport.read(WALKTHROUGH);
    assertThat(OpsExtractionTestSupport.requiredHeadings(source))
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
    assertThat(OpsExtractionTestSupport.hanCount(source)).isGreaterThanOrEqualTo(3000);
    assertThat(OpsExtractionTestSupport.hanCount(source) * 2)
        .isGreaterThanOrEqualTo(OpsExtractionTestSupport.letterCount(source));
    assertThat(source).contains("禁止硬凑", "本项目");
  }

  private static Set<String> rootNames() throws IOException {
    return fileNames(OPS_ROOT);
  }

  private static Set<String> fileNames(Path root) throws IOException {
    return OpsExtractionTestSupport.javaFiles(root).stream()
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toSet());
  }

  private static String stem(String fileName) {
    return fileName.substring(0, fileName.length() - ".java".length());
  }
}
