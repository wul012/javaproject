package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1829Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PROFILE_SECTION_HANDOFF_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapprovaldraftprofilesectionhandoff"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-draft-profile-section-handoff-extraction-v1829.md");
  private static final Path CENSUS_SCRIPT = Path.of("scripts", "ops-root-census.ps1");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1829-v1833",
          "version-1829-production-excellence-signed-approval-draft-profile-section-handoff-extraction.md");
  private static final List<String> RELOCATED_FILES =
      List.of(
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffBoundaryCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffGateCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffModuleCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRenderer.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRouteContractCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSectionCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSourceCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffSupport.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndexAndCensus() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);
    String census = read(DOCS_ROOT.resolve("extraction-endgame-census-v1828.md"));

    assertThat(readme)
        .contains(
            "signed-approval-draft-profile-section-handoff-extraction-v1829.md",
            "ops.maintenance.signedapprovaldraftprofilesectionhandoff",
            "874 to 864");
    assertThat(note)
        .contains(
            "v1829",
            "third of three",
            "Direct Java files in the root `ops` package fall from 874 to 864",
            "total `ops` Java files stay at 1,352",
            "Do not rename or move archive roots",
            "census script",
            "759");
    assertThat(census)
        .contains("v1829 progress", "874 to 864", "769 to 759", "scripts/ops-root-census.ps1");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(PROFILE_SECTION_HANDOFF_SOURCE_ROOT)).isTrue();
    for (String fileName : RELOCATED_FILES) {
      assertThat(Files.isRegularFile(PROFILE_SECTION_HANDOFF_SOURCE_ROOT.resolve(fileName)))
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
  }

  @Test
  void rootStillOwnsControllersAndRouteAggregationOnly() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftProfileSectionRegistryController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void routeOwnershipMovesToSignedApprovalLeafWithoutChangingSuffix() throws IOException {
    String leaf =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "signedapproval",
                    "OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths.java")));
    String candidateDocument =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "candidatedocument",
                    "OpsShardReadinessCandidateDocumentRoutePaths.java")));
    String rootAggregator = read(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    String service =
        read(
            PROFILE_SECTION_HANDOFF_SOURCE_ROOT.resolve(
                "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.java"));

    assertThat(
            OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths.BASE_PATH
                + OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths
                    .SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF)
        .isEqualTo("/api/v1/ops/shard-readiness/signed-approval-draft-profile-section-handoff");
    assertThat(leaf)
        .contains(
            "SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF",
            "\"/signed-approval-draft-profile-section-handoff\"");
    assertThat(candidateDocument)
        .contains(
            "OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths",
            ".SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF");
    assertThat(rootAggregator)
        .doesNotContain(
            "OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths",
            "SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF");
    assertThat(service)
        .contains(
            "OpsShardReadinessSignedApprovalDraftProfileSectionRoutePaths.BASE_PATH",
            ".SIGNED_APPROVAL_DRAFT_PROFILE_SECTION_HANDOFF");
  }

  @Test
  void spotbugsMirrorBlocksFollowMovedResponseFqn() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff."
                + "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse");
    assertThat(spotbugs)
        .doesNotContain(
            "com.codexdemo.orderplatform.ops.OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse");
  }

  @Test
  void rootPackageAndTotalOpsRatchetsStayTight() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(864);
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
  void censusScriptMakesReviewerCountReproducible() throws IOException {
    assertThat(Files.isRegularFile(CENSUS_SCRIPT)).isTrue();
    String script = read(CENSUS_SCRIPT);

    assertThat(script)
        .contains(
            "DirectRootJavaFiles",
            "TargetFinalDirectRootJavaFiles",
            "RemainingDirectRootNonControllers",
            "SignedApprovalDraftProfileSection",
            "RouteCleanup web",
            "UnassignedFiles");
  }

  @Test
  void chineseWalkthroughForThisVersionIsCommittedBeforeVerify() throws IOException {
    assertThat(Files.isRegularFile(WALKTHROUGH)).isTrue();
    assertThat(read(WALKTHROUGH))
        .contains("version-1829", "禁止硬凑", "本项目", "## 实际工作量说明", "## 入口路由", "## 一句话总结");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
