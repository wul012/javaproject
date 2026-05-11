package com.codexdemo.orderplatform.notification;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FailedEventReplayApprovalHistoryRepository
        extends JpaRepository<FailedEventReplayApprovalHistory, Long>,
        JpaSpecificationExecutor<FailedEventReplayApprovalHistory> {

    List<FailedEventReplayApprovalHistory> findByFailedEventMessageIdOrderByChangedAtDescIdDesc(
            Long failedEventMessageId
    );

    Optional<FailedEventReplayApprovalHistory> findTopByFailedEventMessageIdOrderByChangedAtDescIdDesc(
            Long failedEventMessageId
    );

    long countByFailedEventMessageId(Long failedEventMessageId);
}
