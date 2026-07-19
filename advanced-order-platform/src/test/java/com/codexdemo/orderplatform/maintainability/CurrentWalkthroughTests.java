package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class CurrentWalkthroughTests {

  private static final Path ROOT = Path.of("代码讲解记录_生产雏形阶段8", "v1868-v1872");

  @Test
  void currentVolumeStaysSubstantive() throws IOException {
    List<Path> files;
    try (Stream<Path> paths = Files.list(ROOT)) {
      files =
          paths
              .filter(Files::isRegularFile)
              .filter(path -> path.getFileName().toString().endsWith(".md"))
              .sorted()
              .toList();
    }

    assertThat(files).hasSizeGreaterThanOrEqualTo(2);
    for (Path file : files) {
      String text = Files.readString(file, StandardCharsets.UTF_8);
      long han =
          text.codePoints()
              .filter(point -> Character.UnicodeScript.of(point) == Character.UnicodeScript.HAN)
              .count();
      long headings = text.lines().filter(line -> line.startsWith("## ")).count();

      assertThat(han).as(file.toString()).isGreaterThanOrEqualTo(3000);
      assertThat(headings).as(file.toString()).isEqualTo(10);
    }
  }
}
