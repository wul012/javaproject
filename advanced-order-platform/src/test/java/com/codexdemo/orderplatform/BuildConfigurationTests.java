package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class BuildConfigurationTests {

  private static final Path ROOT = Path.of("").toAbsolutePath();

  @Test
  void spotlessRatchetDefaultsToCanonicalJavaRemote() throws Exception {
    String pom = Files.readString(ROOT.resolve("pom.xml"), StandardCharsets.UTF_8);

    assertThat(pom)
        .contains("<spotless.ratchetFrom>javaproject/master</spotless.ratchetFrom>")
        .doesNotContain("<spotless.ratchetFrom>origin/master</spotless.ratchetFrom>");
  }
}
