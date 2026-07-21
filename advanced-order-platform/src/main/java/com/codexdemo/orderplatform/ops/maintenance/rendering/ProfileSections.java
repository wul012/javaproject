package com.codexdemo.orderplatform.ops.maintenance.rendering;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ProfileSections {

  private ProfileSections() {}

  public static List<Rendered> render(List<Section> sections, List<Field> fields) {
    var sectionCopy = List.copyOf(sections);
    var fieldCopy = List.copyOf(fields);
    Map<String, List<Field>> fieldsBySection =
        fieldCopy.stream()
            .collect(
                Collectors.groupingBy(Field::sectionCode, LinkedHashMap::new, Collectors.toList()));
    return sectionCopy.stream()
        .map(section -> render(section, fieldsBySection.getOrDefault(section.code(), List.of())))
        .toList();
  }

  private static Rendered render(Section section, List<Field> fields) {
    String body =
        fields.stream()
            .map(field -> "- " + field.name() + ": " + field.value())
            .collect(Collectors.joining("\n"));
    return new Rendered(
        section.order(), section.code(), section.group(), "### " + section.heading(), body);
  }

  public record Section(int order, String code, String group, String heading) {

    public Section {
      if (order < 1) {
        throw new IllegalArgumentException("order must be positive");
      }
      Objects.requireNonNull(code, "code");
      Objects.requireNonNull(group, "group");
      Objects.requireNonNull(heading, "heading");
    }

    public static Section ungrouped(int order, String code, String heading) {
      return new Section(order, code, "", heading);
    }
  }

  public record Field(String sectionCode, String name, String value) {

    public Field {
      Objects.requireNonNull(sectionCode, "sectionCode");
      Objects.requireNonNull(name, "name");
      Objects.requireNonNull(value, "value");
    }
  }

  public record Rendered(
      int order, String code, String group, String markdownHeading, String markdownBody) {}
}
