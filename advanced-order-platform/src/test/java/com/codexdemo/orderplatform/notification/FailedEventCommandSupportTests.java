package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class FailedEventCommandSupportTests {

  @Test
  void firstNonBlankPreservesTheSelectedValue() {
    assertThat(FailedEventCommandSupport.firstNonBlank(null, " ", "  selected  ", "later"))
        .isEqualTo("  selected  ");
    assertThat(FailedEventCommandSupport.firstNonBlank(null, " ")).isNull();
  }

  @Test
  void truncateKeepsNullAndShortValuesButCapsLongValues() {
    assertThat(FailedEventCommandSupport.truncate(null, 3)).isNull();
    assertThat(FailedEventCommandSupport.truncate("abc", 3)).isEqualTo("abc");
    assertThat(FailedEventCommandSupport.truncate("abcdef", 3)).isEqualTo("abc");
  }

  @Test
  void operatorContextIsRequiredAtEveryCommandBoundary() {
    FailedEventOperatorContext context =
        new FailedEventOperatorContext("operator", "ORDER_SUPPORT");

    assertThat(FailedEventCommandSupport.requireOperatorContext(context)).isSameAs(context);
    assertThatThrownBy(() -> FailedEventCommandSupport.requireOperatorContext(null))
        .isInstanceOfSatisfying(
            ResponseStatusException.class,
            exception -> {
              assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
              assertThat(exception.getReason()).isEqualTo("operator context is required");
            });
  }
}
