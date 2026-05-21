package com.codexdemo.orderplatform;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

abstract class OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private FailedEventMessageRepository failedEventMessageRepository;

    @Autowired
    private FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;

    @Autowired
    private FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;

    @Autowired
    private FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository;

    @BeforeEach
    void cleanFailedEventData() {
        deleteFailedEventData();
    }

    @AfterEach
    void removeFailedEventData() {
        deleteFailedEventData();
    }

    protected void seedReleaseApprovalReplayApprovals() {
        FailedEventMessage pendingApproval = FailedEventMessage.record(
                "release-approval-rehearsal-pending",
                "event-release-approval-rehearsal-1",
                "OrderNotificationFailed",
                "ORDER",
                "6601",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v66 pending approval",
                "{\"orderId\":6601}"
        );
        pendingApproval.requestReplayApproval("needs release rehearsal review", "ops-user", Instant.now());
        failedEventMessageRepository.save(pendingApproval);
        FailedEventMessage approvedReplay = FailedEventMessage.record(
                "release-approval-rehearsal-approved",
                "event-release-approval-rehearsal-2",
                "OrderNotificationFailed",
                "ORDER",
                "6602",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v66 approved replay",
                "{\"orderId\":6602}"
        );
        approvedReplay.requestReplayApproval("safe to rehearse", "ops-user", Instant.now());
        approvedReplay.approveReplay("ops-reviewer", "approved for rehearsal", Instant.now());
        failedEventMessageRepository.save(approvedReplay);
    }

    private void deleteFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }
}