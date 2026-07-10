package com.codexdemo.orderplatform.maintainability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class JavaMaintainabilityWalkthroughTests {

  private static final Path WALKTHROUGH_ROOT = Path.of("代码讲解记录_生产雏形阶段6", "v1834-v1837");

  private static final List<String> REQUIRED_HEADINGS =
      List.of(
          "## 实际工作量说明",
          "## 入口路由",
          "## 响应模型",
          "## 上游证据配置",
          "## 服务层核心流程",
          "## Java 证据检查",
          "## mini-kv 证据检查",
          "## 阻断与安全边界",
          "## 测试覆盖",
          "## 一句话总结");

  @Test
  void everyMaintainabilityVersionHasSubstantiveChineseWalkthroughEvidence() throws IOException {
    List<Path> walkthroughs;
    try (Stream<Path> paths = Files.list(WALKTHROUGH_ROOT)) {
      walkthroughs =
          paths
              .filter(Files::isRegularFile)
              .filter(path -> path.getFileName().toString().endsWith(".md"))
              .sorted()
              .toList();
    }

    assertThat(walkthroughs).hasSizeGreaterThanOrEqualTo(3);
    for (Path walkthrough : walkthroughs) {
      String text = Files.readString(walkthrough, StandardCharsets.UTF_8);
      long hanCharacters =
          text.codePoints()
              .filter(
                  codePoint -> Character.UnicodeScript.of(codePoint) == Character.UnicodeScript.HAN)
              .count();
      long letters = text.codePoints().filter(Character::isLetter).count();

      assertThat(hanCharacters).as(walkthrough.toString()).isGreaterThanOrEqualTo(3000);
      assertThat(hanCharacters * 2).as(walkthrough.toString()).isGreaterThan(letters);
      assertThat(text).as(walkthrough.toString()).contains("禁止硬凑", "本项目");
      assertThat(text)
          .as(walkthrough.toString())
          .contains(REQUIRED_HEADINGS.toArray(String[]::new));
    }
  }
}
