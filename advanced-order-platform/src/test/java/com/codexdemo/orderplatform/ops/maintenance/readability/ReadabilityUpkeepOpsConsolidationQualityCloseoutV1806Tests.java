package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path PROGRESS = Path.of("docs", "production-excellence-progress.md");
  private static final Path CHANGELOG = Path.of("CHANGELOG.md");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path CLOSEOUT_NOTE =
      DOCS_ROOT.resolve("java-extraction-quality-closeout-v1806.md");
  private static final int EXPECTED_ROOT_OPS_MAIN_JAVA_FILES = 429;

  @Test
  void closeoutNoteRecordsCurrentGreenBaselineAndHistoricalException() throws IOException {
    assertThat(Files.isRegularFile(CLOSEOUT_NOTE)).isTrue();

    String note = read(CLOSEOUT_NOTE);

    assertThat(note)
        .contains(
            "v1806",
            "v1805-order-platform-production-excellence-ops-candidate-document-extraction",
            "1,183",
            "MAX_ROOT_OPS_MAIN_JAVA_FILES = 1183",
            "41 tests, 0 failures",
            "spotless:check",
            "27524935139",
            "v1798 tag is a historical exception",
            "a8d8f93881407b0bc15bd25b61e463215fcac069",
            "Do not rewrite or force-move historical tags",
            "Treat v1799 and newer tags as the remediated extraction line");
  }

  @Test
  void progressAndChangelogExposeV1806Closeout() throws IOException {
    String progress = read(PROGRESS);
    String changelog = read(CHANGELOG);
    String readme = read(DOCS_ROOT.resolve("README.md"));

    assertThat(progress)
        .contains(
            "J16",
            "v1806",
            "historical v1798 tag exception",
            "v1805 current green baseline",
            "Do not rewrite or force-move historical tags");
    assertThat(changelog)
        .contains("v1806", "Java extraction quality closeout", "v1798", "v1799", "v1805");
    assertThat(readme)
        .contains(
            "java-extraction-quality-closeout-v1806.md",
            "quality closeout",
            "historical v1798 tag exception");
  }

  @Test
  void rootPackageRatchetStillMatchesCurrentMeasuredCount() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isEqualTo(EXPECTED_ROOT_OPS_MAIN_JAVA_FILES);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
