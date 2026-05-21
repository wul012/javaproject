package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessageService;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

abstract class FailedEventSearchIntegrationTestSupport {

    @Autowired
    protected FailedEventMessageService failedEventMessageService;

    @Autowired
    protected FailedEventMessageRepository failedEventMessageRepository;

    @Autowired
    protected FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;

    @Autowired
    protected FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;

    @Autowired
    protected FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository;

    @BeforeEach
    void cleanFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }

    protected void assertBadRequest(Runnable action) {
        assertThatThrownBy(action::run)
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        org.assertj.core.api.Assertions.assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
                );
    }

    protected FailedEventMessage failedEventMessage(
            String messageId,
            String eventId,
            String eventType,
            String aggregateType,
            String aggregateId
    ) {
        return FailedEventMessage.record(
                messageId,
                eventId,
                eventType,
                aggregateType,
                aggregateId,
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "listener rejected event",
                "{\"aggregateId\":\"" + aggregateId + "\"}"
        );
    }
}
