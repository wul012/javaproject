package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

final class FailedEventSearchPageSupport {

  private static final int DEFAULT_EXPORT_LIMIT = 1000, MAX_EXPORT_LIMIT = 5000;

  private FailedEventSearchPageSupport() {}

  static NormalizedPageRequest normalizePageRequest(
      Integer page,
      Integer size,
      Integer limit,
      String sort,
      Map<String, String> allowedSortFields,
      String defaultSort) {
    int normalizedPage =
        normalizeBounded(page, 0, 0, Integer.MAX_VALUE, "page must be greater than or equal to 0");
    int normalizedSize =
        normalizeBounded(size == null ? limit : size, 50, 1, 200, "size must be between 1 and 200");
    SortInstruction sortInstruction = normalizeSort(sort, allowedSortFields, defaultSort);
    return new NormalizedPageRequest(
        PageRequest.of(normalizedPage, normalizedSize, sortInstruction.sort()),
        sortInstruction.expression());
  }

  static PageRequest normalizeExportPageRequest(
      Integer limit, String sort, Map<String, String> allowedSortFields, String defaultSort) {
    SortInstruction sortInstruction = normalizeSort(sort, allowedSortFields, defaultSort);
    int normalizedLimit =
        normalizeBounded(
            limit,
            DEFAULT_EXPORT_LIMIT,
            1,
            MAX_EXPORT_LIMIT,
            "export limit must be between 1 and 5000");
    return PageRequest.of(0, normalizedLimit, sortInstruction.sort());
  }

  static void validateSearchId(Long id, String fieldName) {
    if (id != null && id < 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, fieldName + " must be positive");
    }
  }

  static void validateTimeRange(Instant from, Instant to, String fromName, String toName) {
    if (from != null && to != null && from.isAfter(to)) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, fromName + " must be before or equal to " + toName);
    }
  }

  private static int normalizeBounded(
      Integer value, int defaultValue, int minimum, int maximum, String message) {
    int normalized = value == null ? defaultValue : value;
    if (normalized < minimum || normalized > maximum) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
    return normalized;
  }

  private static SortInstruction normalizeSort(
      String sort, Map<String, String> allowedSortFields, String defaultSort) {
    String expression = StringUtils.hasText(sort) ? sort.strip() : defaultSort;
    String[] parts = expression.split(",");
    if (parts.length < 1 || parts.length > 2) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "sort must use field,direction format");
    }
    String requestedField = parts[0].strip();
    String property = allowedSortFields.get(requestedField);
    if (property == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "sort field is not allowed: " + requestedField);
    }
    Sort.Direction direction = Sort.Direction.DESC;
    if (parts.length == 2 && StringUtils.hasText(parts[1])) {
      try {
        direction = Sort.Direction.fromString(parts[1].strip());
      } catch (IllegalArgumentException exception) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "sort direction must be asc or desc", exception);
      }
    }
    Sort sortOrder = Sort.by(direction, property);
    if (!"id".equals(property)) {
      sortOrder = sortOrder.and(Sort.by(Sort.Direction.DESC, "id"));
    }
    return new SortInstruction(
        sortOrder, requestedField + "," + direction.name().toLowerCase(java.util.Locale.ROOT));
  }

  record NormalizedPageRequest(PageRequest pageRequest, String sort) {}

  private record SortInstruction(Sort sort, String expression) {}
}
