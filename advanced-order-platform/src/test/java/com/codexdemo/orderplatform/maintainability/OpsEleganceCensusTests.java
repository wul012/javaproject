package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class OpsEleganceCensusTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path DIGEST_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorcihandoffarchivedigest"));
  private static final Path CONSUMER_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorciconsumerpackage"));
  private static final Path DOSSIER_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "operatorcidossier"));
  private static final Path RELEASE_ACCEPTANCE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "ciaccept"));
  private static final Path ARCHIVE_REGISTRY_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "ciarc"));
  private static final Path ARCHIVE_HANDOFF_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releasearchivehandoff"));
  private static final Path ACCEPTANCE_PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptancepackage"));
  private static final Path HANDOFF_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorcihandoff"));
  private static final Path EXECUTION_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateexecution"));
  private static final Path SUSTAINMENT_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit", "sustainment"));

  @Test
  void rendererDebtCanOnlyShrink() throws IOException {
    List<Path> renderers = javaFiles(OPS_ROOT).stream().filter(this::isRenderer).toList();

    assertThat(renderers).hasSizeLessThanOrEqualTo(38);
    assertThat(renderers.stream().mapToLong(this::lineCountUnchecked).sum())
        .isLessThanOrEqualTo(3_521);
    assertThat(renderers.stream().filter(this::hasLongStem)).hasSizeLessThanOrEqualTo(22);
  }

  @Test
  void archiveDigestKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(DIGEST_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(10);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
    assertThat(
            Path.of(
                "src",
                "main",
                "java",
                "com",
                "codexdemo",
                "orderplatform",
                "ops",
                "maintenance",
                "rendering",
                "MarkdownSections.java"))
        .isRegularFile();
  }

  @Test
  void consumerPackageKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(CONSUMER_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(13);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
  }

  @Test
  void dossierKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(DOSSIER_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(14);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
  }

  @Test
  void releaseAcceptanceKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(RELEASE_ACCEPTANCE_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(14);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
  }

  @Test
  void archiveRegistryKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(ARCHIVE_REGISTRY_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(13);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
  }

  @Test
  void archiveHandoffKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(ARCHIVE_HANDOFF_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(14);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
  }

  @Test
  void acceptancePackageKeepsThreeRenderers() throws IOException {
    List<Path> files = javaFiles(ACCEPTANCE_PACKAGE_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(26);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrder(
            "ArchiveIndexRenderer.java", "ReceiptRenderer.java", "ReportRenderer.java");
  }

  @Test
  void handoffKeepsTwoOutputRenderers() throws IOException {
    List<Path> files = javaFiles(HANDOFF_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(18);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrder("ArchiveRenderer.java", "HandoffRenderer.java");
  }

  @Test
  void executionKeepsTwoOutputRenderers() throws IOException {
    List<Path> files = javaFiles(EXECUTION_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(23);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactlyInAnyOrder("ArchiveRenderer.java", "ExecutionRenderer.java");
  }

  @Test
  void sustainmentKeepsOneShortRenderer() throws IOException {
    List<Path> files = javaFiles(SUSTAINMENT_ROOT);
    List<Path> renderers = files.stream().filter(this::isRenderer).toList();

    assertThat(files).hasSizeLessThanOrEqualTo(11);
    assertThat(renderers)
        .extracting(path -> path.getFileName().toString())
        .containsExactly("ReportRenderer.java");
  }

  @Test
  void censusAndRoadmapStayReproducible() throws IOException {
    Path script = Path.of("scripts", "ops-elegance-census.ps1");
    Path roadmap = Path.of("docs", "ops", "elegance-three-point-roadmap.md");
    Path version = Path.of("docs", "ops", "release-sustainment-renderer-v1882.md");

    assertThat(script).isRegularFile();
    assertThat(read(script))
        .contains(
            "RendererFiles",
            "RendererLines",
            "LongRendererFileNames",
            "ArchiveDigestJavaFiles",
            "ConsumerPackageJavaFiles",
            "DossierJavaFiles",
            "ReleaseAcceptanceJavaFiles",
            "ArchiveRegistryJavaFiles",
            "ArchiveHandoffJavaFiles",
            "AcceptancePackageJavaFiles",
            "HandoffJavaFiles",
            "ExecutionJavaFiles",
            "SustainmentJavaFiles");
    assertThat(read(roadmap)).contains("ops-elegance-census.ps1", "DONE 与失败条件", "<= 650", "<= 30");
    assertThat(read(version)).contains("需求证据矩阵", "失败条件", "19 -> 11", "45 -> 38", "3616 -> 3521");
  }

  private List<Path> javaFiles(Path root) throws IOException {
    try (Stream<Path> paths = Files.walk(root)) {
      return paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(".java"))
          .toList();
    }
  }

  private boolean isRenderer(Path path) {
    return stem(path).endsWith("Renderer");
  }

  private boolean hasLongStem(Path path) {
    return stem(path).length() > JavaSourceNames.NAME_BUDGET;
  }

  private String stem(Path path) {
    String name = path.getFileName().toString();
    return name.substring(0, name.length() - ".java".length());
  }

  private long lineCountUnchecked(Path path) {
    try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
      return lines.count();
    } catch (IOException exception) {
      throw new IllegalStateException("Cannot count lines in " + path, exception);
    }
  }

  private String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
