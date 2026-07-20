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

  @Test
  void rendererDebtCanOnlyShrink() throws IOException {
    List<Path> renderers = javaFiles(OPS_ROOT).stream().filter(this::isRenderer).toList();

    assertThat(renderers).hasSizeLessThanOrEqualTo(77);
    assertThat(renderers.stream().mapToLong(this::lineCountUnchecked).sum())
        .isLessThanOrEqualTo(4_376);
    assertThat(renderers.stream().filter(this::hasLongStem)).hasSizeLessThanOrEqualTo(70);
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
  void censusAndRoadmapStayReproducible() throws IOException {
    Path script = Path.of("scripts", "ops-elegance-census.ps1");
    Path roadmap = Path.of("docs", "ops", "elegance-three-point-roadmap.md");
    Path version = Path.of("docs", "ops", "archive-registry-renderer-engine-v1877.md");

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
            "ArchiveRegistryJavaFiles");
    assertThat(read(roadmap)).contains("ops-elegance-census.ps1", "DONE 与失败条件", "<= 650", "<= 30");
    assertThat(read(version)).contains("需求证据矩阵", "失败条件", "86 -> 77", "4586 -> 4376");
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
