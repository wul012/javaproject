package com.codexdemo.orderplatform.ops.maintenance.rendering;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public final class MarkdownSections {

  private MarkdownSections() {}

  public static <T, S> S counted(
      String heading,
      String countName,
      List<T> entries,
      Function<? super T, String> lineMapper,
      BiFunction<String, List<String>, S> sectionFactory) {
    Objects.requireNonNull(heading, "heading");
    Objects.requireNonNull(countName, "countName");
    Objects.requireNonNull(entries, "entries");
    Objects.requireNonNull(lineMapper, "lineMapper");
    Objects.requireNonNull(sectionFactory, "sectionFactory");
    List<String> lines =
        Stream.concat(Stream.of(countName + "=" + entries.size()), entries.stream().map(lineMapper))
            .toList();
    return sectionFactory.apply(heading, lines);
  }
}
