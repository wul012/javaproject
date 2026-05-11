package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FailedEventMessageRepository
        extends JpaRepository<FailedEventMessage, Long>, JpaSpecificationExecutor<FailedEventMessage> {

    Optional<FailedEventMessage> findByMessageId(String messageId);

    long countByReplayApprovalStatus(FailedEventReplayApprovalStatus replayApprovalStatus);

    long countByStatusNot(FailedEventMessageStatus status);

    Optional<FailedEventMessage> findTopByOrderByFailedAtDescIdDesc();

    Optional<FailedEventMessage> findTopByReplayApprovalRequestedAtIsNotNullOrderByReplayApprovalRequestedAtDescIdDesc();

    Optional<FailedEventMessage> findTopByReplayApprovalReviewedAtIsNotNullOrderByReplayApprovalReviewedAtDescIdDesc();

    @Query("""
            select count(message)
            from FailedEventMessage message
            where message.status <> :replayedStatus
              and (
                    message.failedAt < :failedAt
                    or (message.failedAt = :failedAt and message.id < :id)
              )
            """)
    long countReplayBacklogBefore(
            @Param("replayedStatus") FailedEventMessageStatus replayedStatus,
            @Param("failedAt") Instant failedAt,
            @Param("id") Long id
    );
}
