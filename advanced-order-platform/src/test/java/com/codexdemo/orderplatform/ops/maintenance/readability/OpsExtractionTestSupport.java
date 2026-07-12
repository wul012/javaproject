package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

final class OpsExtractionTestSupport {

  private OpsExtractionTestSupport() {}

  static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }

  static List<Path> javaFiles(Path directory) throws IOException {
    try (Stream<Path> files = Files.list(directory)) {
      return files.filter(Files::isRegularFile).filter(OpsExtractionTestSupport::isJava).toList();
    }
  }

  static List<Path> allJavaFiles(Path directory) throws IOException {
    try (Stream<Path> files = Files.walk(directory)) {
      return files.filter(Files::isRegularFile).filter(OpsExtractionTestSupport::isJava).toList();
    }
  }

  static boolean isJava(Path path) {
    return path.getFileName().toString().endsWith(".java");
  }

  static int count(String source, String needle) {
    int matches = 0;
    int index = 0;
    while ((index = source.indexOf(needle, index)) >= 0) {
      matches++;
      index += needle.length();
    }
    return matches;
  }

  static List<String> requiredHeadings(String source) {
    return source
        .lines()
        .filter(line -> line.startsWith("## "))
        .map(line -> line.substring(3).trim())
        .toList();
  }

  static int hanCount(String source) {
    return (int)
        source.codePoints().filter(codePoint -> codePoint >= 0x4E00 && codePoint <= 0x9FFF).count();
  }

  static int letterCount(String source) {
    return (int) source.codePoints().filter(Character::isLetter).count();
  }
}
