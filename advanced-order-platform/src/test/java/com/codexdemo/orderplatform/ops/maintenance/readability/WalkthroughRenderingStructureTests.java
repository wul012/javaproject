package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class WalkthroughRenderingStructureTests {

  private static final Path MAINTENANCE =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops", "maintenance");
  private static final Path TEST_MAINTENANCE =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops", "maintenance");
  private static final List<String> FAMILIES =
      List.of("compliance", "depth", "qualityaudit", "qualitygate");
  private static final Path DOC = Path.of("docs", "ops", "code-walkthrough-renderers-v1885.md");

  @Test
  void familiesKeepOneShortRenderer() throws IOException {
    for (String family : FAMILIES) {
      Path root = familyRoot(MAINTENANCE, family);
      assertThat(OpsExtractionTestSupport.javaFiles(root).stream().filter(this::isRenderer))
          .extracting(path -> path.getFileName().toString())
          .containsExactly("ReportRenderer.java");
    }
  }

  @Test
  void servicesUseLocalRenderer() throws IOException {
    for (String family : FAMILIES) {
      Path service =
          OpsExtractionTestSupport.javaFiles(familyRoot(MAINTENANCE, family)).stream()
              .filter(path -> path.getFileName().toString().endsWith("RegistryService.java"))
              .findFirst()
              .orElseThrow();
      assertThat(OpsExtractionTestSupport.read(service))
          .contains("ReportRenderer.render(")
          .doesNotContain("RegistryRenderer.render(");
    }
  }

  @Test
  void renderersReuseSharedEngine() throws IOException {
    for (String family : FAMILIES) {
      String renderer =
          OpsExtractionTestSupport.read(
              familyRoot(MAINTENANCE, family).resolve("ReportRenderer.java"));
      assertThat(renderer)
          .contains("maintenance.rendering.MarkdownSections")
          .doesNotContain("new ArrayList", "new MarkdownSection");
    }
  }

  @Test
  void testDataOwnersStayShort() throws IOException {
    for (String family : FAMILIES) {
      Path root = familyRoot(TEST_MAINTENANCE, family);
      assertThat(root.resolve("WalkthroughTestData.java")).isRegularFile();
      assertThat(OpsExtractionTestSupport.javaFiles(root))
          .noneMatch(path -> path.getFileName().toString().endsWith("RegistryTestSupport.java"));
    }
  }

  @Test
  void docsRecordFrozenReports() throws IOException {
    assertThat(OpsExtractionTestSupport.read(DOC))
        .contains(
            "## Family design",
            "Requirement Evidence Matrix",
            "22 sections",
            "168 lines",
            "30 -> 30",
            "9 -> 5",
            "Failure Conditions");
  }

  private Path familyRoot(Path root, String family) {
    return root.resolve(Path.of("walkthrough", family));
  }

  private boolean isRenderer(Path path) {
    return path.getFileName().toString().endsWith("Renderer.java");
  }
}
