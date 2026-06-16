package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepGovernanceConsolidationPlanTests {

  private static final Path PLANS_ROOT = Path.of("docs", "plans");
  private static final Path OPS_DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final int MAX_OPS_MAIN_JAVA_FILES = 1352;
  private static final int MAX_ROOT_OPS_MAIN_JAVA_FILES = 1167;
  private static final int MAX_READINESS_MAIN_JAVA_FILES = 1210;

  @Test
  void keepsJavaOpsConsolidationPlanDiscoverable() throws IOException {
    Path roadmap = PLANS_ROOT.resolve("v1789-java-ops-governance-consolidation-roadmap.md");
    Path playbook = PLANS_ROOT.resolve("v1789-codex-ops-migration-playbook.md");
    String readme = read(OPS_DOCS_ROOT.resolve("README.md"));
    String roadmapText = read(roadmap);
    String playbookText = read(playbook);

    assertThat(Files.isRegularFile(roadmap)).isTrue();
    assertThat(Files.isRegularFile(playbook)).isTrue();
    assertThat(readme)
        .contains(
            "../plans/v1789-java-ops-governance-consolidation-roadmap.md",
            "../plans/v1789-codex-ops-migration-playbook.md",
            "Do not rename or move");
    assertThat(roadmapText)
        .contains(
            "Measured on 2026-06-12",
            "1,352 Java files",
            "Root `ops` package direct Java files: 1,330",
            "Do not rename or move archive folders",
            "Node v2114",
            "Java may proceed in parallel with Node v2114");
    assertThat(playbookText)
        .contains(
            "Do not rename or move archive folders",
            "git fetch javaproject --tags --prune",
            "ReadabilityUpkeepGovernanceConsolidationPlanTests",
            "CI is green on the pushed head");
  }

  @Test
  void keepsOpsMainSourceFileCountRatchetFromGrowing() throws IOException {
    try (Stream<Path> paths = Files.walk(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(MAX_OPS_MAIN_JAVA_FILES);
    }

    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(MAX_ROOT_OPS_MAIN_JAVA_FILES);
    }

    try (Stream<Path> paths = Files.walk(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .filter(path -> path.getFileName().toString().contains("Readiness"))
                  .count())
          .isLessThanOrEqualTo(MAX_READINESS_MAIN_JAVA_FILES);
    }
  }

  @Test
  void keepsLoadBearingArchiveRootsInPlace() throws IOException {
    for (String root : "abcdef".split("")) {
      assertThat(Files.isDirectory(Path.of(root))).isTrue();
    }

    String roadmapText =
        read(PLANS_ROOT.resolve("v1789-java-ops-governance-consolidation-roadmap.md"));
    String playbookText = read(PLANS_ROOT.resolve("v1789-codex-ops-migration-playbook.md"));

    assertThat(roadmapText).contains("`a/` through `f/`", "`e/<version>/`", "evidence JSON");
    assertThat(playbookText).contains("`a/` through `f/`", "`e/<version>/`", "evidence JSON");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
