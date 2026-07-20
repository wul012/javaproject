package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class JavaEleganceGateTests {

  private static final Path MAIN_ROOT = Path.of("src", "main", "java");
  private static final Path TEST_ROOT = Path.of("src", "test", "java");
  private static final Path NAME_BASELINE = Path.of("config", "java-name-baseline.txt");
  private static final Path ROUTE_OWNER =
      MAIN_ROOT.resolve(
          Path.of("com", "codexdemo", "orderplatform", "ops", "OpsShardReadinessRoutePaths.java"));

  private static final Pattern ROUTE_FIELD =
      Pattern.compile(
          "(?ms)^[ \\t]*(?:public\\s+)?static\\s+final\\s+String\\s+([A-Z][A-Z0-9_]*)\\s*=.*?;");

  @Test
  void longNameBaselinesOnlyShrink() throws IOException {
    assertWithin(metrics(MAIN_ROOT), new NameMetrics(1243, 20851, 2802));
    assertWithin(metrics(TEST_ROOT), new NameMetrics(790, 10156, 3828));
  }

  @Test
  void exactNameBaselineMatchesSource() throws IOException {
    NameBaseline expected = readBaseline(Files.readString(NAME_BASELINE, StandardCharsets.UTF_8));
    NameBaseline actual = currentBaseline();

    assertThat(actual.longFiles()).containsExactlyInAnyOrderElementsOf(expected.longFiles());
    assertThat(actual.longNames()).containsExactlyInAnyOrderElementsOf(expected.longNames());
  }

  @Test
  void exactNameBaselineOnlyShrinks() throws IOException {
    var prior = GitChangeSet.priorFile(NAME_BASELINE);
    if (prior.isEmpty()) {
      return;
    }
    NameBaseline before = readBaseline(prior.orElseThrow());
    NameBaseline after = readBaseline(Files.readString(NAME_BASELINE, StandardCharsets.UTF_8));

    assertThat(after.longFiles()).isSubsetOf(before.longFiles());
    assertThat(after.longNames()).isSubsetOf(before.longNames());
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

  private static NameBaseline currentBaseline() throws IOException {
    Set<String> files = new HashSet<>();
    Set<String> names = new HashSet<>();
    for (Path root : List.of(MAIN_ROOT, TEST_ROOT)) {
      for (Path source : JavaSourceNames.files(root)) {
        if (JavaSourceNames.stem(source).length() > JavaSourceNames.NAME_BUDGET) {
          files.add(source.toString().replace('\\', '/'));
        }
        names.addAll(JavaSourceNames.longIdentifiers(source));
      }
    }
    return new NameBaseline(Set.copyOf(files), Set.copyOf(names));
  }

  private static NameBaseline readBaseline(String text) {
    Set<String> files = new HashSet<>();
    Set<String> names = new HashSet<>();
    Arrays.stream(text.split("\\R"))
        .filter(line -> !line.isBlank() && !line.startsWith("#"))
        .forEach(
            line -> {
              String[] parts = line.split("\\t", 2);
              assertThat(parts).as(line).hasSize(2);
              if (parts[0].equals("F")) {
                assertThat(files.add(parts[1])).as(parts[1]).isTrue();
              } else {
                assertThat(parts[0]).as(line).isEqualTo("I");
                assertThat(names.add(parts[1])).as(parts[1]).isTrue();
              }
            });
    return new NameBaseline(Set.copyOf(files), Set.copyOf(names));
  }

  private record NameMetrics(long longStems, long longIdentifierUses, long longIdentifierNames) {}

  private record NameBaseline(Set<String> longFiles, Set<String> longNames) {}
}
