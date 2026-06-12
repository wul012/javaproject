package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationInventoryTests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path INVENTORY = DOCS_ROOT.resolve("ops-consolidation-inventory-v1796.md");

  @Test
  void inventoryStaysDiscoverableFromOpsDocsIndex() throws IOException {
    assertThat(Files.isRegularFile(INVENTORY)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));

    assertThat(readme)
        .contains(
            "ops-consolidation-inventory-v1796.md",
            "root-package pressure",
            "load-bearing archive",
            "reduction candidates");
  }

  @Test
  void inventoryPreservesMeasuredOpsPressureAndRouteFamilies() throws IOException {
    String inventory = read(INVENTORY);

    assertThat(inventory)
        .contains(
            "1,352",
            "1,330",
            "1,210",
            "OperatorEvidenceValueSupply",
            "RouteCleanup",
            "ReleaseAcceptance",
            "MinimalReadOnlyGate",
            "CandidateDocument",
            "CodeWalkthrough",
            "Controller",
            "Service",
            "Catalog",
            "Renderer");
  }

  @Test
  void inventoryKeepsLoadBearingArchiveAndStopLineExplicit() throws IOException {
    String inventory = read(INVENTORY);

    assertThat(inventory)
        .contains(
            "Do not rename or move archive folders",
            "`a/` through `f/`",
            "`e/<version>/`",
            "evidence JSON",
            "read-only route paths stay in place",
            "No class moves in v1796",
            "contract-preserving extraction");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
