package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ProductionReadinessDocumentationTests {

  private static final Path ROOT = Path.of("").toAbsolutePath();

  @Test
  void releaseDisciplineDocsStayDiscoverable() throws Exception {
    assertThat(Files.isRegularFile(ROOT.resolve("CHANGELOG.md"))).isTrue();
    assertThat(Files.isRegularFile(ROOT.resolve("PRODUCTION_READINESS.md"))).isTrue();

    String readme = read("README.md");
    assertThat(readme)
        .contains("CHANGELOG.md", "PRODUCTION_READINESS.md", "git tag", "0.1.0-SNAPSHOT");
  }

  @Test
  void changelogDefinesCurrentVersionPolicyAndRecentTags() throws Exception {
    String changelog = read("CHANGELOG.md");

    assertThat(changelog)
        .contains(
            "git tag",
            "0.1.0-SNAPSHOT",
            "v1796",
            "v1795",
            "v1794",
            "v1793",
            "v1792",
            "v1791",
            "v1790",
            "v1789",
            "v1788",
            "v1787",
            "v1786");
  }

  @Test
  void productionReadinessCentralizesRuntimeAndExecutionBoundaries() throws Exception {
    String readiness = read("PRODUCTION_READINESS.md");

    assertThat(readiness)
        .contains(
            "Payment Boundary",
            "SIMULATED",
            "outbox.rabbitmq.enabled=false",
            "notification.rabbitmq.enabled=false",
            "spring.h2.console.enabled",
            "ORDER_SUPPORT",
            "SRE",
            "SYSTEM",
            "credential value",
            "managed audit",
            "deployment",
            "rollback",
            "rollback SQL",
            "health,info,metrics");
  }

  private static String read(String fileName) throws Exception {
    return Files.readString(ROOT.resolve(fileName), StandardCharsets.UTF_8);
  }
}
