package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class RendererCloseoutStructureTests {

  private static final Path MAIN_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops", "maintenance");
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops", "maintenance");
  private static final Path DOC = Path.of("docs", "ops", "renderer-closeout-v1886.md");
  private static final List<RendererCase> CASES =
      List.of(
          new RendererCase(
              "credentialresolver",
              "ArchiveRenderer.java",
              "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService.java",
              "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRenderer.java",
              "ArchiveTestData.java",
              "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveTestSupport.java",
              true),
          new RendererCase(
              "sandboxconnection",
              "DossierRenderer.java",
              "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService.java",
              "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer.java",
              "DossierTestData.java",
              "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierTestSupport.java",
              true),
          new RendererCase(
              "sandboxconnection",
              "ManifestRenderer.java",
              "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService.java",
              "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestRenderer.java",
              "ManifestTestData.java",
              "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestTestSupport.java",
              true),
          new RendererCase(
              "screenshotexplanationarchive",
              "ReportRenderer.java",
              "OpsShardReadinessScreenshotExplanationArchiveRegistryService.java",
              "OpsShardReadinessScreenshotExplanationArchiveRegistryRenderer.java",
              "ScreenshotTestData.java",
              "OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.java",
              true),
          new RendererCase(
              "signedapprovaldraftprofilesectionhandoff",
              "HandoffRenderer.java",
              "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.java",
              "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffRenderer.java",
              "HandoffTestData.java",
              "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.java",
              false));

  @Test
  void rendererNamesStayWithinBudget() throws IOException {
    assertThat(OpsExtractionTestSupport.allJavaFiles(MAIN_ROOT))
        .filteredOn(path -> path.getFileName().toString().endsWith("Renderer.java"))
        .allSatisfy(
            path ->
                assertThat(stem(path)).as("renderer stem %s", path).hasSizeLessThanOrEqualTo(40));
  }

  @Test
  void shortOwnersReplaceRetiredOwners() {
    for (RendererCase rendererCase : CASES) {
      Path family = MAIN_ROOT.resolve(rendererCase.family());
      assertThat(family.resolve(rendererCase.owner())).isRegularFile();
      assertThat(family.resolve(rendererCase.retiredOwner())).doesNotExist();
    }
  }

  @Test
  void sectionReportsReuseSharedEngine() throws IOException {
    for (RendererCase rendererCase : CASES) {
      String source =
          OpsExtractionTestSupport.read(
              MAIN_ROOT.resolve(rendererCase.family()).resolve(rendererCase.owner()));
      if (rendererCase.usesEngine()) {
        assertThat(source)
            .contains("maintenance.rendering.MarkdownSections")
            .doesNotContain("new ArrayList", "new MarkdownSection");
      } else {
        assertThat(source).doesNotContain("MarkdownSections");
      }
    }
  }

  @Test
  void servicesDelegateToShortOwners() throws IOException {
    for (RendererCase rendererCase : CASES) {
      String service =
          OpsExtractionTestSupport.read(
              MAIN_ROOT.resolve(rendererCase.family()).resolve(rendererCase.service()));
      assertThat(service)
          .contains(ownerType(rendererCase.owner()) + ".render(")
          .doesNotContain(ownerType(rendererCase.retiredOwner()) + ".render(");
    }
  }

  @Test
  void testDataOwnersStayShort() {
    for (RendererCase rendererCase : CASES) {
      Path family = TEST_ROOT.resolve(rendererCase.family());
      assertThat(family.resolve(rendererCase.testData())).isRegularFile();
      assertThat(family.resolve(rendererCase.retiredTestData())).doesNotExist();
    }
  }

  @Test
  void designRecordsFrozenOutputAndFailures() throws IOException {
    assertThat(OpsExtractionTestSupport.read(DOC))
        .contains(
            "## Family design",
            "Requirement Evidence Matrix",
            "33",
            "202",
            "ArchiveRenderer",
            "DossierRenderer",
            "ManifestRenderer",
            "ReportRenderer",
            "HandoffRenderer",
            "Failure Conditions");
  }

  private static String stem(Path path) {
    return path.getFileName().toString().replaceFirst("\\.java$", "");
  }

  private static String ownerType(String fileName) {
    return fileName.replaceFirst("\\.java$", "");
  }

  private record RendererCase(
      String family,
      String owner,
      String service,
      String retiredOwner,
      String testData,
      String retiredTestData,
      boolean usesEngine) {}
}
