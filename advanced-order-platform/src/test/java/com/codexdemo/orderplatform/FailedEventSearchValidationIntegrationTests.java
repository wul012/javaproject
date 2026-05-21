package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistorySearchCriteria;
import com.codexdemo.orderplatform.notification.FailedEventManagementStatus;
import com.codexdemo.orderplatform.notification.FailedEventMessageSearchCriteria;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistorySearchCriteria;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptSearchCriteria;
import com.codexdemo.orderplatform.notification.MarkFailedEventManagementRequest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
class FailedEventSearchValidationIntegrationTests extends FailedEventSearchIntegrationTestSupport {

    @Test
    void rejectsInvalidSearchRangesAndLimits() {
        Instant now = Instant.now();

        assertBadRequest(() -> failedEventMessageService.searchFailedMessages(
                new FailedEventMessageSearchCriteria(null, null, null, null, now, now.minusSeconds(1), 10)
        ));
        assertBadRequest(() -> failedEventMessageService.searchFailedMessages(
                new FailedEventMessageSearchCriteria(null, null, null, null, null, null, 201)
        ));
        assertBadRequest(() -> failedEventMessageService.searchReplayAttempts(
                new FailedEventReplayAttemptSearchCriteria(null, null, null, null, now, now.minusSeconds(1), 10)
        ));
        assertBadRequest(() -> failedEventMessageService.searchReplayAttempts(
                new FailedEventReplayAttemptSearchCriteria(null, null, null, null, null, null, 0)
        ));
        assertBadRequest(() -> failedEventMessageService.searchFailedMessages(
                new FailedEventMessageSearchCriteria(null, null, null, null, null, null, -1, 50, null, null)
        ));
        assertBadRequest(() -> failedEventMessageService.searchFailedMessages(
                new FailedEventMessageSearchCriteria(null, null, null, null, null, null, 0, 201, null, null)
        ));
        assertBadRequest(() -> failedEventMessageService.searchFailedMessages(
                new FailedEventMessageSearchCriteria(null, null, null, null, null, null, 0, 50, "messageId,desc", null)
        ));
        assertBadRequest(() -> failedEventMessageService.searchReplayAttempts(
                new FailedEventReplayAttemptSearchCriteria(null, null, null, null, null, null, 0, 50, "operatorRole,sideways", null)
        ));
        assertBadRequest(() -> failedEventMessageService.searchManagementHistory(
                new FailedEventManagementHistorySearchCriteria(null, null, null, null, null, now, now.minusSeconds(1), 10)
        ));
        assertBadRequest(() -> failedEventMessageService.searchManagementHistory(
                new FailedEventManagementHistorySearchCriteria(null, null, null, null, null, null, null, 0, 50, "messageId,desc", null)
        ));
        assertBadRequest(() -> failedEventMessageService.searchReplayApprovalHistory(
                new FailedEventReplayApprovalHistorySearchCriteria(null, null, null, null, now, now.minusSeconds(1), 10)
        ));
        assertBadRequest(() -> failedEventMessageService.searchReplayApprovalHistory(
                new FailedEventReplayApprovalHistorySearchCriteria(null, null, null, null, null, null, 0, 50, "messageId,desc", null)
        ));
        assertBadRequest(() -> failedEventMessageService.exportFailedMessagesCsv(
                new FailedEventMessageSearchCriteria(null, null, null, null, null, null, null, null, null, null, 5001)
        ));
        assertBadRequest(() -> failedEventMessageService.exportManagementHistoryCsv(
                new FailedEventManagementHistorySearchCriteria(null, null, null, null, null, null, null, null, null, "messageId,desc", 10)
        ));
        assertBadRequest(() -> failedEventMessageService.exportReplayApprovalHistoryCsv(
                new FailedEventReplayApprovalHistorySearchCriteria(null, null, null, null, null, null, null, null, "messageId,desc", 10)
        ));
        assertBadRequest(() -> failedEventMessageService.markManagementStatus(
                new MarkFailedEventManagementRequest(
                        List.of(1L),
                        FailedEventManagementStatus.RESOLVED,
                        " "
                ),
                "ops-user",
                "SRE"
        ));
        assertBadRequest(() -> failedEventMessageService.markManagementStatus(
                new MarkFailedEventManagementRequest(
                        List.of(),
                        FailedEventManagementStatus.RESOLVED,
                        "resolved manually"
                ),
                "ops-user",
                "SRE"
        ));
        assertThatThrownBy(() -> failedEventMessageService.markManagementStatus(
                new MarkFailedEventManagementRequest(
                        List.of(1L),
                        FailedEventManagementStatus.RESOLVED,
                        "resolved manually"
                ),
                "ops-user",
                "VIEWER"
        ))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN)
                );
    }
}
