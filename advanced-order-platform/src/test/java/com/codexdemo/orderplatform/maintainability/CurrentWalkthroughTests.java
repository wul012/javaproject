package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class CurrentWalkthroughTests {

  private static final List<Path> ROOTS =
      List.of(
          Path.of("代码讲解记录_生产雏形阶段8", "v1868-v1872"),
          Path.of("代码讲解记录_生产雏形阶段9", "v1873-v1877"),
          Path.of("代码讲解记录_生产雏形阶段9", "v1878-v1882"));
  private static final Path LATEST =
      ROOTS.get(2).resolve("v1879-release-acceptance-package-renderers.md");
  private static final List<String> STANDARD_HEADINGS =
      List.of(
          "## 入口路由",
          "## 响应模型",
          "## 上游证据配置",
          "## 服务层核心流程",
          "## Java 证据检查",
          "## mini-kv 证据检查",
          "## 阻断与安全边界",
          "## 测试覆盖",
          "## 实际工作量说明",
          "## 一句话总结");

  @Test
  void currentVolumeStaysSubstantive() throws IOException {
    List<Path> files = new ArrayList<>();
    for (Path root : ROOTS) {
      try (Stream<Path> paths = Files.list(root)) {
        paths
            .filter(Files::isRegularFile)
            .filter(path -> path.getFileName().toString().endsWith(".md"))
            .forEach(files::add);
      }
    }

    assertThat(files).hasSizeGreaterThanOrEqualTo(6);
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

  @Test
  void latestUsesStandardHeadings() throws IOException {
    String text = Files.readString(LATEST, StandardCharsets.UTF_8);

    assertThat(text.lines().filter(line -> line.startsWith("## ")))
        .containsExactlyElementsOf(STANDARD_HEADINGS);
    assertThat(text).contains("禁止硬凑", "本项目");
  }
}
