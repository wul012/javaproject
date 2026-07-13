package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class JavaTrackCloseoutTests {

  private static final Path ROOT = Path.of("").toAbsolutePath();
  private static final String MATURITY =
      "single-project validation + verified read-only cross-project integration "
          + "(env-gated, single machine, no execution authority)";

  @Test
  void workflowUsesCurrentActions() throws IOException {
    String workflow = read(ROOT.resolve(Path.of("..", ".github", "workflows", "maven-ci.yml")));

    assertThat(workflow)
        .contains(
            "actions/checkout@v7",
            "actions/setup-java@v5",
            "actions/upload-artifact@v7",
            "Wrapper verify without Docker tests",
            "Docker-tagged integration tests",
            "Prod profile boot smoke")
        .doesNotContain("actions/checkout@v4", "actions/setup-java@v4", "upload-artifact@v4");
  }

  @Test
  void coverageFloorsStayRaised() throws IOException {
    String pom = read(ROOT.resolve("pom.xml"));

    assertThat(pom)
        .contains(
            "<jacoco.bundle.line.minimum>0.97</jacoco.bundle.line.minimum>",
            "<jacoco.root.line.minimum>0.86</jacoco.root.line.minimum>",
            "<jacoco.catalog.line.minimum>0.65</jacoco.catalog.line.minimum>",
            "<jacoco.common.line.minimum>0.62</jacoco.common.line.minimum>",
            "<jacoco.inventory.line.minimum>0.90</jacoco.inventory.line.minimum>",
            "<jacoco.notification.line.minimum>0.78</jacoco.notification.line.minimum>",
            "<jacoco.ops.line.minimum>0.98</jacoco.ops.line.minimum>",
            "<jacoco.readability.line.minimum>0.98</jacoco.readability.line.minimum>",
            "<jacoco.order.line.minimum>0.88</jacoco.order.line.minimum>",
            "<jacoco.outbox.line.minimum>0.70</jacoco.outbox.line.minimum>",
            "<jacoco.payment.line.minimum>0.91</jacoco.payment.line.minimum>");
  }

  @Test
  void securityBoundaryStaysExplicit() throws IOException {
    String prod = read(ROOT.resolve(Path.of("src", "main", "resources", "application-prod.yml")));
    String readiness = read(ROOT.resolve("PRODUCTION_READINESS.md"));

    assertThat(prod)
        .contains(
            "enabled: false",
            "show-sql: false",
            "format_sql: false",
            "timeout-per-shutdown-phase: 30s",
            "shutdown: graceful",
            "include: health,info,metrics");
    assertThat(readiness)
        .contains(
            "## Threat Model",
            MATURITY,
            "credential value",
            "managed audit",
            "deployment",
            "rollback SQL")
        .doesNotContain("production authorized");
  }

  @Test
  void docsAndReleaseStayHonest() throws IOException {
    String readme = read(ROOT.resolve("README.md"));
    String changelog = read(ROOT.resolve("CHANGELOG.md"));
    String evidence = read(ROOT.resolve(Path.of("docs", "java-track-final-evidence.md")));

    assertThat(readme).contains(MATURITY, "PRODUCTION_READINESS.md", "CHANGELOG.md");
    assertThat(changelog).contains("## v1867", "## v1866", "git tag", "0.1.0-SNAPSHOT");
    assertThat(changelog).doesNotContain("鏈」鐩");
    assertThat(evidence)
        .contains(
            "E1 Build & CI",
            "E2 Static analysis",
            "E3 Coverage",
            "E4 Security & config",
            "E5 Observability",
            "E6 Error handling",
            "E7 Docs honesty",
            "E8 Release discipline",
            "E9 Code health",
            "E10 Archive retention",
            "External Java-track review is required",
            "candidate is the strongest permitted status");
  }

  @Test
  void closeoutAssetsStayDiscoverable() {
    assertThat(ROOT.resolve(Path.of("scripts", "ops-root-census.ps1"))).isRegularFile();
    assertThat(ROOT.resolve(Path.of("scripts", "java-maintainability-census.ps1"))).isRegularFile();
    assertThat(ROOT.resolve(Path.of("scripts", "archive-retention-census.ps1"))).isRegularFile();
    assertThat(ROOT.resolve(Path.of("docs", "ops", "extraction-waivers.md"))).isRegularFile();
    assertThat(ROOT.resolve(Path.of("docs", "archive-retention-policy.md"))).isRegularFile();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
