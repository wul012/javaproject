package com.codexdemo.orderplatform.maintainability;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

final class JavaSourceNames {

  static final int NAME_BUDGET = 40;

  private static final Pattern TRIVIA =
      Pattern.compile(
          "/\\*.*?\\*/|//[^\\r\\n]*|\"\"\"[\\s\\S]*?\"\"\"|\"(?:\\\\.|[^\"\\\\])*\"|'(?:\\\\.|[^'\\\\])*'",
          Pattern.DOTALL);

  private static final Pattern IDENTIFIER =
      Pattern.compile("(?<![A-Za-z0-9_$])[A-Za-z_$][A-Za-z0-9_$]*");

  private JavaSourceNames() {}

  static List<Path> files(Path root) throws IOException {
    try (Stream<Path> paths = Files.walk(root)) {
      return paths
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().endsWith(".java"))
          .toList();
    }
  }

  static List<String> longIdentifiers(Path source) throws IOException {
    String code = TRIVIA.matcher(Files.readString(source, StandardCharsets.UTF_8)).replaceAll(" ");
    return IDENTIFIER
        .matcher(code)
        .results()
        .map(result -> result.group())
        .filter(name -> name.length() > NAME_BUDGET)
        .toList();
  }

  static String stem(Path source) {
    String fileName = source.getFileName().toString();
    return fileName.substring(0, fileName.length() - ".java".length());
  }
}
