package com.codexdemo.orderplatform.notification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

final class FailedEventSearchSpecifications {

  private FailedEventSearchSpecifications() {}

  static Specification<FailedEventMessage> failedMessagesMatching(
      FailedEventMessageSearchCriteria criteria) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      addEquals(predicates, criteriaBuilder, root.get("status"), criteria.status());
      addEquals(
          predicates, criteriaBuilder, root.get("managementStatus"), criteria.managementStatus());
      addEquals(
          predicates,
          criteriaBuilder,
          root.get("replayApprovalStatus"),
          criteria.replayApprovalStatus());
      addTextEquals(predicates, criteriaBuilder, root.get("eventType"), criteria.eventType());
      addTextEquals(
          predicates, criteriaBuilder, root.get("aggregateType"), criteria.aggregateType());
      addTextEquals(predicates, criteriaBuilder, root.get("aggregateId"), criteria.aggregateId());
      if (criteria.failedFrom() != null) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(root.get("failedAt"), criteria.failedFrom()));
      }
      if (criteria.failedTo() != null) {
        predicates.add(
            criteriaBuilder.lessThanOrEqualTo(root.get("failedAt"), criteria.failedTo()));
      }
      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }

  static Specification<FailedEventReplayAttempt> replayAttemptsMatching(
      FailedEventReplayAttemptSearchCriteria criteria,
      FailedEventReplayProperties replayProperties) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      addEquals(
          predicates,
          criteriaBuilder,
          root.get("failedEventMessage").get("id"),
          criteria.failedEventMessageId());
      addEquals(predicates, criteriaBuilder, root.get("status"), criteria.status());
      addTextEquals(predicates, criteriaBuilder, root.get("operatorId"), criteria.operatorId());
      addTextEquals(
          predicates,
          criteriaBuilder,
          root.get("operatorRole"),
          replayProperties.normalize(criteria.operatorRole()));
      if (criteria.attemptedFrom() != null) {
        predicates.add(
            criteriaBuilder.greaterThanOrEqualTo(
                root.get("attemptedAt"), criteria.attemptedFrom()));
      }
      if (criteria.attemptedTo() != null) {
        predicates.add(
            criteriaBuilder.lessThanOrEqualTo(root.get("attemptedAt"), criteria.attemptedTo()));
      }
      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }

  static Specification<FailedEventManagementHistory> managementHistoryMatching(
      FailedEventManagementHistorySearchCriteria criteria,
      FailedEventReplayProperties replayProperties) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      addEquals(
          predicates,
          criteriaBuilder,
          root.get("failedEventMessage").get("id"),
          criteria.failedEventMessageId());
      addEquals(predicates, criteriaBuilder, root.get("previousStatus"), criteria.previousStatus());
      addEquals(predicates, criteriaBuilder, root.get("newStatus"), criteria.newStatus());
      addTextEquals(predicates, criteriaBuilder, root.get("operatorId"), criteria.operatorId());
      addTextEquals(
          predicates,
          criteriaBuilder,
          root.get("operatorRole"),
          replayProperties.normalize(criteria.operatorRole()));
      addChangedAtRange(
          predicates,
          criteriaBuilder,
          root.get("changedAt"),
          criteria.changedFrom(),
          criteria.changedTo());
      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }

  static Specification<FailedEventReplayApprovalHistory> replayApprovalHistoryMatching(
      FailedEventReplayApprovalHistorySearchCriteria criteria,
      FailedEventReplayProperties replayProperties) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      addEquals(
          predicates,
          criteriaBuilder,
          root.get("failedEventMessage").get("id"),
          criteria.failedEventMessageId());
      addEquals(predicates, criteriaBuilder, root.get("action"), criteria.action());
      addTextEquals(predicates, criteriaBuilder, root.get("operatorId"), criteria.operatorId());
      addTextEquals(
          predicates,
          criteriaBuilder,
          root.get("operatorRole"),
          replayProperties.normalize(criteria.operatorRole()));
      addChangedAtRange(
          predicates,
          criteriaBuilder,
          root.get("changedAt"),
          criteria.changedFrom(),
          criteria.changedTo());
      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }

  private static void addChangedAtRange(
      List<Predicate> predicates,
      CriteriaBuilder criteriaBuilder,
      Path<java.time.Instant> path,
      java.time.Instant from,
      java.time.Instant to) {
    if (from != null) {
      predicates.add(criteriaBuilder.greaterThanOrEqualTo(path, from));
    }
    if (to != null) {
      predicates.add(criteriaBuilder.lessThanOrEqualTo(path, to));
    }
  }

  private static <T> void addEquals(
      List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Path<T> path, T value) {
    if (value != null) {
      predicates.add(criteriaBuilder.equal(path, value));
    }
  }

  private static void addTextEquals(
      List<Predicate> predicates,
      CriteriaBuilder criteriaBuilder,
      Path<String> path,
      String value) {
    String normalized = StringUtils.hasText(value) ? value.strip() : null;
    if (normalized != null) {
      predicates.add(criteriaBuilder.equal(path, normalized));
    }
  }
}
