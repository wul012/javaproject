package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ProfileRenderingStructureTests {

  private static final Path OPS =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path MAINTENANCE = OPS.resolve("maintenance");
  private static final Path ENGINE = MAINTENANCE.resolve("rendering/ProfileSections.java");
  private static final List<Path> FAMILIES =
      List.of(
          MAINTENANCE.resolve("candidatedocument"),
          MAINTENANCE.resolve("signedapprovaldraftprofilesection"),
          MAINTENANCE.resolve("signedapprovaldrafttextpackageprofilesection"));
  private static final Path DOC =
      Path.of("docs", "ops", "profile-section-rendering-engine-v1884.md");

  @Test
  void familiesKeepOneShortAdapter() throws IOException {
    for (Path family : FAMILIES) {
      assertThat(OpsExtractionTestSupport.javaFiles(family).stream().filter(this::isRenderer))
          .extracting(path -> path.getFileName().toString())
          .containsExactly("ProfileRenderer.java");
    }
  }

  @Test
  void engineStaysDomainNeutral() throws IOException {
    assertThat(ENGINE).isRegularFile();
    assertThat(OpsExtractionTestSupport.read(ENGINE))
        .contains("record Section", "record Field", "record Rendered")
        .doesNotContain(
            "candidatedocument",
            "signedapproval",
            "RegistryResponse",
            "submission",
            "compared-evidence");
  }

  @Test
  void publicModelsIgnoreTheEngine() throws IOException {
    for (Path family : FAMILIES) {
      Path response =
          OpsExtractionTestSupport.javaFiles(family).stream()
              .filter(path -> path.getFileName().toString().endsWith("RegistryResponse.java"))
              .findFirst()
              .orElseThrow();
      assertThat(OpsExtractionTestSupport.read(response)).doesNotContain("ProfileSections");
    }
  }

  @Test
  void servicesUseLocalAdapters() throws IOException {
    for (Path family : FAMILIES) {
      Path service =
          OpsExtractionTestSupport.javaFiles(family).stream()
              .filter(path -> path.getFileName().toString().endsWith("RegistryService.java"))
              .findFirst()
              .orElseThrow();
      assertThat(OpsExtractionTestSupport.read(service))
          .contains("ProfileRenderer.render(")
          .doesNotContain("ProfileSections");
    }
  }

  @Test
  void docsRecordFrozenOutput() throws IOException {
    assertThat(OpsExtractionTestSupport.read(DOC))
        .contains(
            "## Family design",
            "Requirement Evidence Matrix",
            "5 + 5 + 9",
            "32 -> 30",
            "Failure Conditions");
  }

  private boolean isRenderer(Path path) {
    return path.getFileName().toString().endsWith("Renderer.java");
  }
}
