package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class JavaMaintainabilityBudgetTests {

  private static final Path MAIN_ROOT = Path.of("src", "main", "java");
  private static final Path TEST_ROOT = Path.of("src", "test", "java");
  private static final Path CENSUS_SCRIPT = Path.of("scripts", "java-maintainability-census.ps1");
  private static final Path PROGRAM_DOC =
      Path.of("docs", "maintainability", "java-maintainability-program-v1834.md");

  private static final Map<String, Long> PRIORITY_HOTSPOT_LINE_CAPS = priorityHotspotLineCaps();

  @Test
  void productionSourceHotspotBudgetCanOnlyTighten() throws IOException {
    SourceMetrics metrics = measure(MAIN_ROOT);

    assertThat(metrics.maxLines()).isLessThanOrEqualTo(1530);
    assertThat(metrics.filesOver500Lines()).isLessThanOrEqualTo(39);
    assertThat(metrics.filesOver750Lines()).isLessThanOrEqualTo(4);
    assertThat(metrics.filesOver1000Lines()).isLessThanOrEqualTo(2);
  }

  @Test
  void testSourceHotspotBudgetCanOnlyTighten() throws IOException {
    SourceMetrics metrics = measure(TEST_ROOT);

    assertThat(metrics.maxLines()).isLessThanOrEqualTo(854);
    assertThat(metrics.filesOver500Lines()).isLessThanOrEqualTo(8);
    assertThat(metrics.filesOver750Lines()).isLessThanOrEqualTo(2);
    assertThat(metrics.filesOver1000Lines()).isZero();
  }

  @Test
  void namedPriorityHotspotsCannotGrowBehindAggregateCounts() throws IOException {
    for (Map.Entry<String, Long> budget : PRIORITY_HOTSPOT_LINE_CAPS.entrySet()) {
      Path source = Path.of(budget.getKey());
      assertThat(source).as(budget.getKey()).isRegularFile();
      assertThat(lineCount(source)).as(budget.getKey()).isLessThanOrEqualTo(budget.getValue());
    }
  }

  @Test
  void censusAndProgramKeepTheBudgetReproducible() throws IOException {
    assertThat(CENSUS_SCRIPT).isRegularFile();
    assertThat(PROGRAM_DOC).isRegularFile();

    String script = Files.readString(CENSUS_SCRIPT, StandardCharsets.UTF_8);
    String program = Files.readString(PROGRAM_DOC, StandardCharsets.UTF_8);
    assertThat(script)
        .contains("FilesOver500Lines", "FilesOver750Lines", "FilesOver1000Lines", "Hotspots");
    assertThat(program)
        .contains(
            ".\\scripts\\java-maintainability-census.ps1 -Json", "1530", "39", "1126", "维护预算只能收紧");
  }

  private static SourceMetrics measure(Path root) throws IOException {
    List<Long> lineCounts;
    try (Stream<Path> paths = Files.walk(root)) {
      lineCounts =
          paths
              .filter(Files::isRegularFile)
              .filter(path -> path.getFileName().toString().endsWith(".java"))
              .map(JavaMaintainabilityBudgetTests::lineCountUnchecked)
              .sorted(Comparator.reverseOrder())
              .toList();
    }
    return new SourceMetrics(
        lineCounts.get(0),
        lineCounts.stream().filter(lines -> lines > 500).count(),
        lineCounts.stream().filter(lines -> lines > 750).count(),
        lineCounts.stream().filter(lines -> lines > 1000).count());
  }

  private static long lineCountUnchecked(Path path) {
    try {
      return lineCount(path);
    } catch (IOException exception) {
      throw new IllegalStateException("Cannot count lines in " + path, exception);
    }
  }

  private static long lineCount(Path path) throws IOException {
    try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
      return lines.count();
    }
  }

  private static Map<String, Long> priorityHotspotLineCaps() {
    Map<String, Long> caps = new LinkedHashMap<>();
    caps.put("src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java", 1530L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/notification/FailedEventMessageService.java",
        662L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/notification/FailedEventQueryService.java",
        310L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/notification/FailedEventSearchSpecifications.java",
        159L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/notification/FailedEventSearchPageSupport.java",
        103L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/ops/OpsShardReadinessRoutePaths.java", 1111L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalVerificationHintBuilder.java",
        874L);
    caps.put(
        "src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder.java",
        793L);
    return Map.copyOf(caps);
  }

  private record SourceMetrics(
      long maxLines, long filesOver500Lines, long filesOver750Lines, long filesOver1000Lines) {}
}
