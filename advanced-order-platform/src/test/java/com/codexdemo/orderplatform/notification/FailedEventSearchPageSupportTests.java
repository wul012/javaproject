package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class FailedEventSearchPageSupportTests {

  private static final Map<String, String> SORT_FIELDS =
      Map.of("id", "id", "failedAt", "failedAt", "eventType", "eventType");

  @Test
  void defaultsPageSizeAndStableTieBreakSort() {
    FailedEventSearchPageSupport.NormalizedPageRequest normalized =
        FailedEventSearchPageSupport.normalizePageRequest(
            null, null, null, null, SORT_FIELDS, "failedAt,desc");

    assertThat(normalized.pageRequest().getPageNumber()).isZero();
    assertThat(normalized.pageRequest().getPageSize()).isEqualTo(50);
    assertThat(normalized.sort()).isEqualTo("failedAt,desc");
    assertDirection(normalized.pageRequest(), "failedAt", Sort.Direction.DESC);
    assertDirection(normalized.pageRequest(), "id", Sort.Direction.DESC);
  }

  @Test
  void explicitPageSizeAndSortPreservePublicExpression() {
    FailedEventSearchPageSupport.NormalizedPageRequest normalized =
        FailedEventSearchPageSupport.normalizePageRequest(
            2, 25, 10, " eventType,asc ", SORT_FIELDS, "failedAt,desc");

    assertThat(normalized.pageRequest().getPageNumber()).isEqualTo(2);
    assertThat(normalized.pageRequest().getPageSize()).isEqualTo(25);
    assertThat(normalized.sort()).isEqualTo("eventType,asc");
    assertDirection(normalized.pageRequest(), "eventType", Sort.Direction.ASC);
    assertDirection(normalized.pageRequest(), "id", Sort.Direction.DESC);
  }

  @Test
  void exportKeepsDefaultAndMaximumLimits() {
    PageRequest defaults =
        FailedEventSearchPageSupport.normalizeExportPageRequest(
            null, null, SORT_FIELDS, "failedAt,desc");
    PageRequest maximum =
        FailedEventSearchPageSupport.normalizeExportPageRequest(
            5000, "id,asc", SORT_FIELDS, "failedAt,desc");

    assertThat(defaults.getPageSize()).isEqualTo(1000);
    assertThat(maximum.getPageSize()).isEqualTo(5000);
    assertDirection(maximum, "id", Sort.Direction.ASC);
  }

  @Test
  void invalidInputsKeepStatusAndMessages() {
    assertBadRequest(
        () ->
            FailedEventSearchPageSupport.normalizePageRequest(
                -1, 50, null, null, SORT_FIELDS, "failedAt,desc"),
        "page must be greater than or equal to 0");
    assertBadRequest(
        () ->
            FailedEventSearchPageSupport.normalizePageRequest(
                0, 201, null, null, SORT_FIELDS, "failedAt,desc"),
        "size must be between 1 and 200");
    assertBadRequest(
        () ->
            FailedEventSearchPageSupport.normalizeExportPageRequest(
                5001, null, SORT_FIELDS, "failedAt,desc"),
        "export limit must be between 1 and 5000");
    assertBadRequest(
        () ->
            FailedEventSearchPageSupport.normalizePageRequest(
                0, 50, null, "missing,desc", SORT_FIELDS, "failedAt,desc"),
        "sort field is not allowed: missing");
    assertBadRequest(
        () ->
            FailedEventSearchPageSupport.validateTimeRange(
                Instant.parse("2026-07-10T00:00:01Z"),
                Instant.parse("2026-07-10T00:00:00Z"),
                "from",
                "to"),
        "from must be before or equal to to");
  }

  private static void assertDirection(
      PageRequest pageRequest, String property, Sort.Direction direction) {
    assertThat(pageRequest.getSort().getOrderFor(property))
        .isNotNull()
        .extracting(Sort.Order::getDirection)
        .isEqualTo(direction);
  }

  private static void assertBadRequest(Runnable action, String reason) {
    assertThatThrownBy(action::run)
        .isInstanceOfSatisfying(
            ResponseStatusException.class,
            exception -> {
              assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
              assertThat(exception.getReason()).isEqualTo(reason);
            });
  }
}
