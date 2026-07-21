package com.codexdemo.orderplatform.ops.maintenance.rendering;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MarkdownOracle {

  private MarkdownOracle() {}

  public static <S> String sha256(
      List<S> sections,
      Function<? super S, String> heading,
      Function<? super S, List<String>> lines) {
    String snapshot =
        sections.stream()
            .map(section -> heading.apply(section) + "\n" + String.join("\n", lines.apply(section)))
            .collect(Collectors.joining("\n---\n"));
    try {
      byte[] digest =
          MessageDigest.getInstance("SHA-256").digest(snapshot.getBytes(StandardCharsets.UTF_8));
      return HexFormat.of().formatHex(digest);
    } catch (NoSuchAlgorithmException exception) {
      throw new IllegalStateException("SHA-256 is unavailable", exception);
    }
  }
}
