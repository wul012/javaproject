package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepJavaFinalPushStep0V1827Tests {

  private static final Path DOCS_ROOT = Path.of("docs");
  private static final Path OPS_DOCS_ROOT = DOCS_ROOT.resolve("ops");
  private static final Path PROGRESS = DOCS_ROOT.resolve("production-excellence-progress.md");
  private static final Path RECONCILIATION_NOTE =
      OPS_DOCS_ROOT.resolve("java-final-push-step0-reconciliation-v1827.md");
  private static final Path PROJECT_EXPLANATION =
      DOCS_ROOT.resolve(Path.of("project-explanation", "project-value-and-flow.md"));

  @Test
  void reconciliationNoteRecordsV1826GreenCloseout() throws IOException {
    assertThat(Files.isRegularFile(RECONCILIATION_NOTE)).isTrue();
    String note = read(RECONCILIATION_NOTE);

    assertThat(note)
        .contains(
            "v1827",
            "dd3e1db0",
            "27874073004",
            "completed successfully",
            "project-value-and-flow.md",
            "extraction-endgame-census");
  }

  @Test
  void progressLedgerNoLongerLeavesV1826Pending() throws IOException {
    String progress = read(PROGRESS);

    assertThat(progress)
        .contains(
            "| J37 | v1827 | completed; remote CI passed |",
            "| J36 | v1826 | completed; remote CI passed |",
            "GitHub Actions run `28791751229` passed",
            "GitHub Actions run `27874073004` passed",
            "docs/project-explanation/project-value-and-flow.md");
    assertThat(progress)
        .doesNotContain(
            "| J37 | v1827 | local gates passed; remote CI pending |",
            "| J36 | v1826 | local gates passed; remote CI pending |");
  }

  @Test
  void projectExplanationIsDurableRepositoryDocumentation() throws IOException {
    assertThat(Files.isRegularFile(PROJECT_EXPLANATION)).isTrue();

    String explanation = read(PROJECT_EXPLANATION);
    assertThat(explanation)
        .contains("Advanced Order Platform", "订单", "输入", "输出", "状态机", "Outbox", "失败事件", "Ops 只读证据");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
