package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1833Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path INTAKE_PACKAGE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "comparedevidencecandidateintakepreflight"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("compared-evidence-candidate-intake-preflight-extraction-v1833.md");
  private static final Path WALKTHROUGH =
      findWalkthrough(
          "version-1833-production-excellence-compared-evidence-candidate-intake-preflight-extraction.md");
  private static final List<String> RELOCATED_FILES =
      List.of(
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutSlotCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonSlotCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicySlotCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSlotCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndexAndCensus() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);
    String census = read(DOCS_ROOT.resolve("extraction-endgame-census-v1828.md"));

    assertThat(readme)
        .contains(
            "compared-evidence-candidate-intake-preflight-extraction-v1833.md",
            "ops.maintenance.comparedevidencecandidateintakepreflight",
            "819 to 805");
    assertThat(note)
        .contains(
            "v1833",
            "Direct Java files in the root `ops` package fall from 819 to 805",
            "direct-root non-controller backlog falls from 714 to 700",
            "files stay at 1,352",
            "Requirement Evidence Matrix",
            "GateCatalog",
            "Do not rename or move archive roots",
            "fifth extraction batch");
    assertThat(census)
        .contains(
            "v1833 progress",
            "819 to 805",
            "714 to 700",
            "ComparedEvidenceCandidateIntakePreflight | 0");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(INTAKE_PACKAGE_ROOT)).isTrue();
    for (String fileName : RELOCATED_FILES) {
      assertThat(Files.isRegularFile(INTAKE_PACKAGE_ROOT.resolve(fileName))).isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.isRegularFile(
                INTAKE_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.java")))
        .isTrue();
  }

  @Test
  void rootKeepsOnlyCandidateIntakePreflightControllerForHttpEntryPoint() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateIntakePreflightController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void routeOwnershipMovesToCandidateIntakeLeafWithoutChangingSuffixes() throws IOException {
    String leaf =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.java"));
    String rootAggregator = read(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    String sourceService =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService.java"));
    String closeoutService =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService.java"));

    assertThat(leaf)
        .contains(
            "BASE_PATH = \"/api/v1/ops/shard-readiness\"",
            "COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG",
            "\"/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake-preflight-catalog\"",
            "COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT",
            "\"/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake-preflight-closeout\"",
            "public static final String CATALOG",
            "public static final String CLOSEOUT");
    assertThat(rootAggregator)
        .contains(
            "OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths",
            ".COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG",
            ".COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT");
    assertThat(sourceService)
        .contains(
            "public static final String ENDPOINT",
            "OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.BASE_PATH",
            ".COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_SOURCE");
    assertThat(closeoutService)
        .contains(
            "public static final String ENDPOINT",
            "OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths.BASE_PATH",
            ".COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CLOSEOUT");
  }

  @Test
  void candidateDocumentHistoricalRouteExposureDelegatesToIntakeOwner() throws IOException {
    String candidateDocumentRoutes =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "candidatedocument",
                    "OpsShardReadinessCandidateDocumentRoutePaths.java")));

    assertThat(candidateDocumentRoutes)
        .contains(
            "OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths",
            ".COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG")
        .doesNotContain(
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG =\n          \"/operator-evidence");
  }

  @Test
  void gateCatalogIsCollapsedIntoGuardCatalogToAvoidFileGrowth() throws IOException {
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateIntakePreflightGateCatalog.java")))
        .isFalse();
    assertThat(
            Files.exists(
                INTAKE_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateIntakePreflightGateCatalog.java")))
        .isFalse();

    String guardCatalog =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.java"));
    String catalogService =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService.java"));
    assertThat(guardCatalog)
        .contains(
            "private static final List<String> GATES",
            "candidate-intake-preflight-closeout-rendered",
            "static List<String> allGates()");
    assertThat(catalogService)
        .contains(
            "OpsShardReadinessComparedEvidenceCandidateIntakePreflightGuardCatalog.allGates()");
  }

  @Test
  void upstreamAndDownstreamReadersImportMovedBoundaries() throws IOException {
    String sourceSlot =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog.java"));
    String comparisonSlot =
        read(
            INTAKE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonSlotCatalog.java"));
    String registry =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "signedapprovaldrafttextpackageprofilesection",
                    "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService.java")));
    String profileSource =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "signedapprovaldrafttextpackageprofilesection",
                    "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalog.java")));

    assertThat(sourceSlot)
        .contains(
            "ops.maintenance.comparedevidencecandidateblueprint."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths");
    assertThat(comparisonSlot)
        .contains(
            "ops.maintenance.comparedevidencecandidateblueprint."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths");
    assertThat(registry)
        .contains(
            "ops.maintenance.comparedevidencecandidateintakepreflight."
                + "OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService")
        .doesNotContain(
            "ops.OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService");
    assertThat(profileSource)
        .contains(
            "ops.maintenance.comparedevidencecandidateintakepreflight."
                + "OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse")
        .doesNotContain("ops.OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse");
  }

  @Test
  void spotbugsMirrorBlocksFollowMovedCandidateIntakeResponseFqn() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight."
                + "OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse");
    assertThat(spotbugs)
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse");
  }

  @Test
  void rootPackageAndTotalOpsRatchetsStayTight() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isEqualTo(805);
    }
    try (Stream<Path> paths = Files.walk(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(1352);
    }
  }

  @Test
  void chineseWalkthroughForThisVersionIsCommittedBeforeVerify() throws IOException {
    assertThat(Files.isRegularFile(WALKTHROUGH)).isTrue();
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1833",
            "\u7981\u6b62\u786c\u51d1",
            "\u672c\u9879\u76ee",
            "## \u5b9e\u9645\u5de5\u4f5c\u91cf\u8bf4\u660e",
            "## \u5165\u53e3\u8def\u7531",
            "## \u54cd\u5e94\u6a21\u578b",
            "## \u4e0a\u6e38\u8bc1\u636e\u914d\u7f6e",
            "## \u670d\u52a1\u5c42\u6838\u5fc3\u6d41\u7a0b",
            "## Java \u8bc1\u636e\u68c0\u67e5",
            "## mini-kv \u8bc1\u636e\u68c0\u67e5",
            "## \u963b\u65ad\u4e0e\u5b89\u5168\u8fb9\u754c",
            "## \u6d4b\u8bd5\u8986\u76d6",
            "## \u4e00\u53e5\u8bdd\u603b\u7ed3");
  }

  private static Path findWalkthrough(String fileName) {
    try (Stream<Path> paths = Files.walk(Path.of("."))) {
      return paths
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().equals(fileName))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Missing walkthrough " + fileName));
    } catch (IOException ex) {
      throw new IllegalStateException("Unable to locate walkthrough " + fileName, ex);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
