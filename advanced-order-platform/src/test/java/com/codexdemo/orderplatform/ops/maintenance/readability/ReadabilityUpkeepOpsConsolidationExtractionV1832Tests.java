package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1832Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path BLUEPRINT_PACKAGE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "comparedevidencecandidateblueprint"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("compared-evidence-candidate-blueprint-extraction-v1832.md");
  private static final Path WALKTHROUGH =
      findWalkthrough(
          "version-1832-production-excellence-compared-evidence-candidate-blueprint-extraction.md");
  private static final List<String> RELOCATED_FILES =
      List.of(
          "OpsShardReadinessComparedEvidenceCandidateBlueprintBlockerCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutSectionCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonSectionCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonService.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintPolicySectionCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintPolicyService.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintSectionCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintSourceSectionCatalog.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService.java",
          "OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndexAndCensus() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);
    String census = read(DOCS_ROOT.resolve("extraction-endgame-census-v1828.md"));

    assertThat(readme)
        .contains(
            "compared-evidence-candidate-blueprint-extraction-v1832.md",
            "ops.maintenance.comparedevidencecandidateblueprint",
            "833 to 819");
    assertThat(note)
        .contains(
            "v1832",
            "Direct Java files in the root `ops` package fall from 833 to 819",
            "direct-root non-controller backlog falls from 728 to 714",
            "files stay at 1,352",
            "Requirement Evidence Matrix",
            "EndpointRefs",
            "Do not rename or move archive roots");
    assertThat(census)
        .contains(
            "v1832 progress", "833 to 819", "728 to 714", "ComparedEvidenceCandidateBlueprint | 0");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(BLUEPRINT_PACKAGE_ROOT)).isTrue();
    for (String fileName : RELOCATED_FILES) {
      assertThat(Files.isRegularFile(BLUEPRINT_PACKAGE_ROOT.resolve(fileName))).isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.isRegularFile(
                BLUEPRINT_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.java")))
        .isTrue();
  }

  @Test
  void rootKeepsOnlyCandidateBlueprintControllerForHttpEntryPoint() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateBlueprintController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void routeOwnershipMovesToCandidateBlueprintLeafWithoutChangingSuffixes() throws IOException {
    String leaf =
        read(
            BLUEPRINT_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.java"));
    String rootAggregator = read(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    String sourceService =
        read(
            BLUEPRINT_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateBlueprintSourceService.java"));
    String closeoutService =
        read(
            BLUEPRINT_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutService.java"));

    assertThat(leaf)
        .contains(
            "BASE_PATH = \"/api/v1/ops/shard-readiness\"",
            "COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG",
            "\"/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-catalog\"",
            "COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT",
            "\"/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-closeout\"",
            "public static final String CATALOG",
            "public static final String CLOSEOUT");
    assertThat(rootAggregator)
        .contains(
            "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths",
            ".COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CATALOG",
            ".COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT");
    assertThat(sourceService)
        .contains(
            "public static final String ENDPOINT",
            "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.BASE_PATH",
            ".COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_SOURCE");
    assertThat(closeoutService)
        .contains(
            "public static final String ENDPOINT",
            "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths.BASE_PATH",
            ".COMPARED_EVIDENCE_CANDIDATE_BLUEPRINT_CLOSEOUT");
  }

  @Test
  void endpointRefsHelperIsCollapsedIntoRouteOwnerToAvoidFileGrowth() throws IOException {
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs.java")))
        .isFalse();
    assertThat(
            Files.exists(
                BLUEPRINT_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs.java")))
        .isFalse();

    try (Stream<Path> paths = Files.walk(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .map(ReadabilityUpkeepOpsConsolidationExtractionV1832Tests::readUnchecked)
                  .filter(
                      content -> content.contains("ComparedEvidenceCandidateBlueprintEndpointRefs"))
                  .toList())
          .isEmpty();
    }
  }

  @Test
  void inboundReadersImportMovedCandidateBlueprintBoundary() throws IOException {
    String sourceSlot =
        read(
            OPS_SOURCE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceSlotCatalog.java"));
    String comparisonSlot =
        read(
            OPS_SOURCE_ROOT.resolve(
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
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths")
        .doesNotContain("ComparedEvidenceCandidateBlueprintEndpointRefs");
    assertThat(comparisonSlot)
        .contains(
            "ops.maintenance.comparedevidencecandidateblueprint."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths");
    assertThat(registry)
        .contains(
            "ops.maintenance.comparedevidencecandidateblueprint."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintCatalogService");
    assertThat(profileSource)
        .contains(
            "ops.maintenance.comparedevidencecandidateblueprint."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintResponse");
  }

  @Test
  void outboundEvaluationPreflightEndpointRefsStayReadOnlyAndPublic() throws IOException {
    String endpointRefs =
        read(
            OPS_SOURCE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs.java"));
    String comparisonSection =
        read(
            BLUEPRINT_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateBlueprintComparisonSectionCatalog.java"));
    String closeoutSection =
        read(
            BLUEPRINT_PACKAGE_ROOT.resolve(
                "OpsShardReadinessComparedEvidenceCandidateBlueprintCloseoutSectionCatalog.java"));

    assertThat(endpointRefs)
        .contains(
            "public final class OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs",
            "public static final String CATALOG",
            "public static final String SOURCE_ARTIFACT",
            "public static final String IDENTITY_DIGEST",
            "public static final String POLICY_RUNTIME",
            "public static final String EXCLUSION_CLOSEOUT");
    assertThat(comparisonSection)
        .contains(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs");
    assertThat(closeoutSection)
        .contains(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs");
  }

  @Test
  void spotbugsMirrorBlocksFollowMovedCandidateBlueprintResponseFqn() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintResponse");
    assertThat(spotbugs)
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessComparedEvidenceCandidateBlueprintResponse");
  }

  @Test
  void rootPackageAndTotalOpsRatchetsStayTight() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isEqualTo(819);
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
            "version-1832",
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

  private static String readUnchecked(Path path) {
    try {
      return read(path);
    } catch (IOException ex) {
      throw new IllegalStateException("Unable to read " + path, ex);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
