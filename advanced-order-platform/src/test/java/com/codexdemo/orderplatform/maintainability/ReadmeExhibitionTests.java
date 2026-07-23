package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadmeExhibitionTests {

  private static final Path PROJECT = Path.of("").toAbsolutePath();
  private static final Path REPO = PROJECT.getParent();
  private static final String MATURITY =
      "single-project validation + verified read-only cross-project integration "
          + "(env-gated, single machine, no execution authority)";

  @Test
  void rootPageLinksEveryClaim() throws IOException {
    String landing = read(REPO.resolve("README.md"));

    assertThat(landing)
        .contains(
            "actions/workflows/maven-ci.yml/badge.svg",
            "tests-1915%2B",
            "SpotBugs-0",
            "max_file-658_lines",
            MATURITY,
            "approval-gated failed-event replay",
            "flowchart LR",
            "advanced-order-platform/scripts/ops-root-census.ps1",
            "advanced-order-platform/scripts/java-maintainability-census.ps1",
            "advanced-order-platform/docs/java-track-final-evidence.md",
            "advanced-order-platform/docs/production-excellence-progress.md")
        .doesNotContain("production authorized");
  }

  @Test
  void deepDiveListsReproCommands() throws IOException {
    String readme = read(PROJECT.resolve("README.md"));

    assertThat(readme)
        .contains(
            "actions/workflows/maven-ci.yml/badge.svg",
            MATURITY,
            ".\\scripts\\ops-root-census.ps1 -Json",
            ".\\scripts\\java-maintainability-census.ps1 -Json",
            ".\\scripts\\archive-retention-census.ps1 -Json",
            ".\\mvnw.cmd -B verify",
            "docs/java-track-final-evidence.md",
            "docs/production-excellence-progress.md");
  }

  @Test
  void headlineNumbersMatchTree() throws IOException {
    String landing = read(REPO.resolve("README.md"));
    String evidence = read(PROJECT.resolve(Path.of("docs", "java-track-final-evidence.md")));
    String ledger = read(PROJECT.resolve(Path.of("docs", "production-excellence-progress.md")));
    SourceStats stats = sourceStats();

    assertThat(directRootFiles()).isEqualTo(104);
    assertThat(stats).isEqualTo(new SourceStats(658, 0, 0));
    assertThat(landing).contains("805 → 104", "658 lines", ">750 / >1000 = 0 / 0");
    assertThat(evidence)
        .contains("1,915 tests", "maximum 658 lines", "SpotBugs reported `BugInstance=0`");
    assertThat(ledger)
        .contains(
            "819 -> 805",
            "| J72 | v1862 | completed; remote CI passed |",
            "| J73 | v1863 | completed; remote CI passed |",
            "| J74 | v1864 | completed; remote CI passed |",
            "| J75 | v1865 | completed; remote CI passed |",
            "| J76 | v1866 | completed; implementation CI passed | Closes Phase 1 at root 104");
  }

  private static long directRootFiles() throws IOException {
    Path root =
        PROJECT.resolve(Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops"));
    try (Stream<Path> paths = Files.list(root)) {
      return paths.filter(Files::isRegularFile).filter(ReadmeExhibitionTests::isJava).count();
    }
  }

  private static SourceStats sourceStats() throws IOException {
    List<Long> lines;
    try (Stream<Path> paths = Files.walk(PROJECT.resolve(Path.of("src", "main", "java")))) {
      lines =
          paths
              .filter(Files::isRegularFile)
              .filter(ReadmeExhibitionTests::isJava)
              .map(ReadmeExhibitionTests::lineCount)
              .toList();
    }
    return new SourceStats(
        lines.stream().mapToLong(Long::longValue).max().orElseThrow(),
        lines.stream().filter(line -> line > 750).count(),
        lines.stream().filter(line -> line > 1000).count());
  }

  private static boolean isJava(Path path) {
    return path.getFileName().toString().endsWith(".java");
  }

  private static long lineCount(Path path) {
    try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
      return lines.count();
    } catch (IOException exception) {
      throw new IllegalStateException("Cannot count lines in " + path, exception);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }

  private record SourceStats(long maxLines, long over750, long over1000) {}
}
