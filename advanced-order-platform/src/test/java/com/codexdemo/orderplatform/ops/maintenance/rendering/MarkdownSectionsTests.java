package com.codexdemo.orderplatform.ops.maintenance.rendering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MarkdownSectionsTests {

  @Test
  void buildsImmutableSnapshot() {
    var entries = new ArrayList<>(List.of("alpha", "beta"));

    Section section =
        MarkdownSections.counted(
            "Entries", "entry-count", entries, String::toUpperCase, Section::new);
    entries.add("gamma");

    assertThat(section.heading()).isEqualTo("Entries");
    assertThat(section.lines()).containsExactly("entry-count=2", "ALPHA", "BETA");
    assertThatThrownBy(() -> section.lines().add("DELTA"))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void rendersEmptySection() {
    Section section =
        MarkdownSections.counted(
            "Empty", "item-count", List.<String>of(), String::toUpperCase, Section::new);

    assertThat(section).isEqualTo(new Section("Empty", List.of("item-count=0")));
  }

  @Test
  void mapsImmutableSnapshotWithoutCountLine() {
    var entries = new ArrayList<>(List.of("alpha", "beta"));

    Section section =
        MarkdownSections.mapped("Entries", entries, String::toUpperCase, Section::new);
    entries.add("gamma");

    assertThat(section).isEqualTo(new Section("Entries", List.of("ALPHA", "BETA")));
    assertThatThrownBy(() -> section.lines().add("DELTA"))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  private record Section(String heading, List<String> lines) {}
}
