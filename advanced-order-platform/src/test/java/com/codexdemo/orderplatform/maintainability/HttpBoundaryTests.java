package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class HttpBoundaryTests {

  private static final Path SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform");
  private static final String SPRING_HTTP = "org.springframework.http";

  @Test
  void businessCodeAvoidsSpringHttp() throws IOException {
    List<Path> sources = new ArrayList<>();
    sources.addAll(JavaSourceNames.files(SOURCE_ROOT.resolve("order")));
    sources.addAll(JavaSourceNames.files(SOURCE_ROOT.resolve("inventory")));
    sources.add(SOURCE_ROOT.resolve(Path.of("common", "BusinessException.java")));

    List<Path> offenders =
        sources.stream()
            .filter(source -> !JavaSourceNames.stem(source).endsWith("Controller"))
            .filter(HttpBoundaryTests::importsSpringHttp)
            .toList();

    assertThat(offenders).isEmpty();
  }

  private static boolean importsSpringHttp(Path source) {
    try {
      return Files.readString(source, StandardCharsets.UTF_8).contains(SPRING_HTTP);
    } catch (IOException exception) {
      throw new IllegalStateException("Cannot inspect " + source, exception);
    }
  }
}
