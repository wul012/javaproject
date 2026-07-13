package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class JavaEleganceGateTests {

  private static final Path MAIN_ROOT = Path.of("src", "main", "java");
  private static final Path TEST_ROOT = Path.of("src", "test", "java");
  private static final Path ROUTE_OWNER =
      MAIN_ROOT.resolve(
          Path.of("com", "codexdemo", "orderplatform", "ops", "OpsShardReadinessRoutePaths.java"));

  private static final Pattern ROUTE_FIELD =
      Pattern.compile(
          "(?ms)^[ \\t]*(?:public\\s+)?static\\s+final\\s+String\\s+([A-Z][A-Z0-9_]*)\\s*=.*?;");

  @Test
  void longNameBaselinesOnlyShrink() throws IOException {
    assertWithin(metrics(MAIN_ROOT), new NameMetrics(1297, 21169, 2857));
    assertWithin(metrics(TEST_ROOT), new NameMetrics(795, 10226, 3834));
  }

  @Test
  void rootRouteAliasesNeedReaders() throws IOException {
    String owner = Files.readString(ROUTE_OWNER, StandardCharsets.UTF_8);
    List<String> fields =
        ROUTE_FIELD.matcher(owner).results().map(result -> result.group(1)).toList();
    String readers = allSourceExceptOwner();

    assertThat(fields).hasSize(27);
    assertThat(fields)
        .allSatisfy(field -> assertThat(hasRouteReader(readers, field)).as(field).isTrue());
  }

  @Test
  void spotbugsBaselineOnlyShrinks() throws IOException {
    String exclusions =
        Files.readString(Path.of("config", "spotbugs-exclude.xml"), StandardCharsets.UTF_8);
    assertThat(count(exclusions, "<Match>")).isLessThanOrEqualTo(686);
  }

  private static NameMetrics metrics(Path root) throws IOException {
    List<Path> files = JavaSourceNames.files(root);
    Set<String> unique = new HashSet<>();
    long occurrences = 0;
    long longStems = 0;
    for (Path file : files) {
      if (JavaSourceNames.stem(file).length() > JavaSourceNames.NAME_BUDGET) {
        longStems++;
      }
      List<String> names = JavaSourceNames.longIdentifiers(file);
      occurrences += names.size();
      unique.addAll(names);
    }
    return new NameMetrics(longStems, occurrences, unique.size());
  }

  private static String allSourceExceptOwner() throws IOException {
    StringBuilder source = new StringBuilder();
    for (Path root : List.of(MAIN_ROOT, TEST_ROOT)) {
      for (Path file : JavaSourceNames.files(root)) {
        if (!file.equals(ROUTE_OWNER)) {
          source.append(Files.readString(file, StandardCharsets.UTF_8)).append('\n');
        }
      }
    }
    return source.toString();
  }

  private static boolean hasRouteReader(String source, String field) {
    String reader = "OpsShardReadinessRoutePaths\\s*\\.\\s*" + Pattern.quote(field);
    return Pattern.compile(reader).matcher(source).find();
  }

  private static void assertWithin(NameMetrics actual, NameMetrics baseline) {
    assertThat(actual.longStems()).isLessThanOrEqualTo(baseline.longStems());
    assertThat(actual.longIdentifierUses()).isLessThanOrEqualTo(baseline.longIdentifierUses());
    assertThat(actual.longIdentifierNames()).isLessThanOrEqualTo(baseline.longIdentifierNames());
  }

  private static int count(String value, String token) {
    return (value.length() - value.replace(token, "").length()) / token.length();
  }

  private record NameMetrics(long longStems, long longIdentifierUses, long longIdentifierNames) {}
}
