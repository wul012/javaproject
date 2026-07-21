package com.codexdemo.orderplatform.ops.maintenance.rendering;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

  public static <T, S> S mapped(
      String heading,
      List<T> entries,
      Function<? super T, String> lineMapper,
      BiFunction<String, List<String>, S> sectionFactory) {
    Objects.requireNonNull(heading, "heading");
    Objects.requireNonNull(entries, "entries");
    Objects.requireNonNull(lineMapper, "lineMapper");
    Objects.requireNonNull(sectionFactory, "sectionFactory");
    return sectionFactory.apply(heading, entries.stream().map(lineMapper).toList());
  }

  public static <T, S> S groupedCounted(
      String heading,
      String countName,
      List<T> entries,
      Function<? super T, String> groupMapper,
      Function<? super T, String> lineMapper,
      BiFunction<String, List<String>, S> sectionFactory) {
    Objects.requireNonNull(heading, "heading");
    Objects.requireNonNull(countName, "countName");
    Objects.requireNonNull(entries, "entries");
    Objects.requireNonNull(groupMapper, "groupMapper");
    Objects.requireNonNull(lineMapper, "lineMapper");
    Objects.requireNonNull(sectionFactory, "sectionFactory");

    Map<String, List<String>> groups = new LinkedHashMap<>();
    entries.forEach(
        entry ->
            groups
                .computeIfAbsent(groupMapper.apply(entry), ignored -> new ArrayList<>())
                .add(lineMapper.apply(entry)));
    List<String> lines = new ArrayList<>();
    lines.add(countName + "=" + entries.size());
    groups.forEach((group, values) -> lines.add(group + ": " + String.join("; ", values)));
    return sectionFactory.apply(heading, List.copyOf(lines));
  }
}
