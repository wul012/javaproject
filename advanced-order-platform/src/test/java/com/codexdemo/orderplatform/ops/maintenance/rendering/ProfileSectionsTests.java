package com.codexdemo.orderplatform.ops.maintenance.rendering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Field;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Rendered;
import com.codexdemo.orderplatform.ops.maintenance.rendering.ProfileSections.Section;
import java.util.List;
import org.junit.jupiter.api.Test;

class ProfileSectionsTests {

  @Test
  void rendersSectionsAndFieldsInInputOrder() {
    var sections =
        List.of(
            Section.ungrouped(2, "second", "Second"),
            new Section(1, "first", "submission", "First"));
    var fields =
        List.of(
            new Field("first", "alpha", "A"),
            new Field("second", "only", "2"),
            new Field("first", "beta", "B"));

    assertThat(ProfileSections.render(sections, fields))
        .containsExactly(
            new Rendered(2, "second", "", "### Second", "- only: 2"),
            new Rendered(1, "first", "submission", "### First", "- alpha: A\n- beta: B"));
  }

  @Test
  void ignoresOrphansAndAllowsEmptyBodies() {
    var sections = List.of(Section.ungrouped(1, "known", "Known"));
    var fields = List.of(new Field("orphan", "unused", "value"));

    assertThat(ProfileSections.render(sections, fields))
        .containsExactly(new Rendered(1, "known", "", "### Known", ""));
  }

  @Test
  void rejectsInvalidViewsAtTheBoundary() {
    assertThatThrownBy(() -> Section.ungrouped(0, "code", "Heading"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("order must be positive");
    assertThatThrownBy(() -> new Field("code", "name", null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("value");
  }
}
