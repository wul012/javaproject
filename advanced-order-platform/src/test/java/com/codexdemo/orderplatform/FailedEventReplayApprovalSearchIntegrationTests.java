package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codexdemo.orderplatform.common.PagedResponse;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageResponse;
import com.codexdemo.orderplatform.notification.FailedEventMessageSearchCriteria;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryAction;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryResponse;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistorySearchCriteria;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalStatus;
import com.codexdemo.orderplatform.notification.ReplayFailedEventRequest;
import com.codexdemo.orderplatform.notification.RequestFailedEventReplayApprovalRequest;
import com.codexdemo.orderplatform.notification.ReviewFailedEventReplayApprovalRequest;
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
class FailedEventReplayApprovalSearchIntegrationTests extends FailedEventSearchIntegrationTestSupport {

    @Test
    void requestsReviewsAndSearchesReplayApprovalsBeforeReplay() {
        FailedEventMessage failedMessage = failedEventMessageRepository.save(failedEventMessage(
                "v25-message-001",
                "25252525-2525-2525-2525-252525252501",
                "OrderCreated",
                "ORDER",
                "2501"
        ));
        ReplayFailedEventRequest replayRequest = new ReplayFailedEventRequest(
                null,
                null,
                null,
                null,
                null,
                "replay after operator approval"
        );

        assertThatThrownBy(() -> failedEventMessageService.replay(
                failedMessage.getId(),
                replayRequest,
                "ops-user",
                "SRE"
        ))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex -> {
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                    assertThat(ex.getReason()).contains("approved before replay");
                });

        FailedEventMessageResponse pending = failedEventMessageService.requestReplayApproval(
                failedMessage.getId(),
                new RequestFailedEventReplayApprovalRequest("operator verified the fixed event headers"),
                "ops-user",
                "sre"
        );
        PagedResponse<FailedEventMessageResponse> pendingSearch = failedEventMessageService.searchFailedMessages(
                new FailedEventMessageSearchCriteria(
                        null,
                        null,
                        null,
                        null,
                        null,
                        FailedEventReplayApprovalStatus.PENDING,
                        null,
                        null,
                        0,
                        10,
                        "replayApprovalRequestedAt,desc",
                        null
                )
        );

        assertThat(pending.replayApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.PENDING);
        assertThat(pending.replayApprovalReason()).isEqualTo("operator verified the fixed event headers");
        assertThat(pending.replayApprovalRequestedBy()).isEqualTo("ops-user");
        assertThat(pending.replayApprovalRequestedAt()).isNotNull();
        assertThat(pendingSearch.content()).extracting(FailedEventMessageResponse::id)
                .containsExactly(failedMessage.getId());
        assertThat(pendingSearch.sort()).isEqualTo("replayApprovalRequestedAt,desc");
        assertThat(failedEventMessageService.listReplayApprovalHistory(failedMessage.getId()))
                .singleElement()
                .satisfies(history -> {
                    assertThat(history.failedEventMessageId()).isEqualTo(failedMessage.getId());
                    assertThat(history.action()).isEqualTo(FailedEventReplayApprovalHistoryAction.REQUESTED);
                    assertThat(history.operatorId()).isEqualTo("ops-user");
                    assertThat(history.operatorRole()).isEqualTo("SRE");
                    assertThat(history.note()).isEqualTo("operator verified the fixed event headers");
                    assertThat(history.changedAt()).isNotNull();
                });

        assertThatThrownBy(() -> failedEventMessageService.requestReplayApproval(
                failedMessage.getId(),
                new RequestFailedEventReplayApprovalRequest("duplicate request"),
                "ops-user",
                "SRE"
        ))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
                );

        assertThatThrownBy(() -> failedEventMessageService.reviewReplayApproval(
                failedMessage.getId(),
                new ReviewFailedEventReplayApprovalRequest(
                        FailedEventReplayApprovalStatus.APPROVED,
                        "self approval should be blocked"
                ),
                "ops-user",
                "SRE"
        ))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex -> {
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                    assertThat(ex.getReason()).contains("cannot review own request");
                });
        assertThat(failedEventMessageService.listReplayApprovalHistory(failedMessage.getId()))
                .extracting(FailedEventReplayApprovalHistoryResponse::action)
                .containsExactly(FailedEventReplayApprovalHistoryAction.REQUESTED);

        FailedEventMessageResponse rejected = failedEventMessageService.reviewReplayApproval(
                failedMessage.getId(),
                new ReviewFailedEventReplayApprovalRequest(
                        FailedEventReplayApprovalStatus.REJECTED,
                        "payload repair is incomplete"
                ),
                "sre-lead",
                "SRE"
        );
        assertThat(rejected.replayApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.REJECTED);
        assertThat(rejected.replayApprovalReviewedBy()).isEqualTo("sre-lead");
        assertThat(rejected.replayApprovalReviewedAt()).isNotNull();
        assertThat(rejected.replayApprovalReviewNote()).isEqualTo("payload repair is incomplete");

        failedEventMessageService.requestReplayApproval(
                failedMessage.getId(),
                new RequestFailedEventReplayApprovalRequest("payload repair verified"),
                "ops-user",
                "ORDER_SUPPORT"
        );
        FailedEventMessageResponse approved = failedEventMessageService.reviewReplayApproval(
                failedMessage.getId(),
                new ReviewFailedEventReplayApprovalRequest(FailedEventReplayApprovalStatus.APPROVED, null),
                "sre-lead",
                "SRE"
        );
        assertThat(approved.replayApprovalStatus()).isEqualTo(FailedEventReplayApprovalStatus.APPROVED);
        assertThat(approved.replayApprovalReviewNote()).isNull();

        List<FailedEventReplayApprovalHistoryResponse> approvalHistory =
                failedEventMessageService.listReplayApprovalHistory(failedMessage.getId());
        PagedResponse<FailedEventReplayApprovalHistoryResponse> rejectedHistory =
                failedEventMessageService.searchReplayApprovalHistory(new FailedEventReplayApprovalHistorySearchCriteria(
                        failedMessage.getId(),
                        FailedEventReplayApprovalHistoryAction.REJECTED,
                        "sre-lead",
                        "sre",
                        pending.replayApprovalRequestedAt().minusSeconds(1),
                        Instant.now().plusSeconds(5),
                        0,
                        10,
                        "changedAt,desc",
                        null
                ));
        String approvalHistoryCsv = failedEventMessageService.exportReplayApprovalHistoryCsv(
                new FailedEventReplayApprovalHistorySearchCriteria(
                        failedMessage.getId(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        "changedAt,desc",
                        10
                )
        );

        assertThat(approvalHistory).extracting(FailedEventReplayApprovalHistoryResponse::action)
                .containsExactly(
                        FailedEventReplayApprovalHistoryAction.APPROVED,
                        FailedEventReplayApprovalHistoryAction.REQUESTED,
                        FailedEventReplayApprovalHistoryAction.REJECTED,
                        FailedEventReplayApprovalHistoryAction.REQUESTED
                );
        assertThat(rejectedHistory.content()).singleElement().satisfies(history -> {
            assertThat(history.failedEventMessageId()).isEqualTo(failedMessage.getId());
            assertThat(history.action()).isEqualTo(FailedEventReplayApprovalHistoryAction.REJECTED);
            assertThat(history.operatorId()).isEqualTo("sre-lead");
            assertThat(history.operatorRole()).isEqualTo("SRE");
            assertThat(history.note()).isEqualTo("payload repair is incomplete");
        });
        assertThat(rejectedHistory.sort()).isEqualTo("changedAt,desc");
        assertThat(approvalHistoryCsv.lines().toList()).hasSize(5);
        assertThat(approvalHistoryCsv).startsWith("id,failedEventMessageId,action,operatorId,operatorRole,note,changedAt");
        assertThat(approvalHistoryCsv).contains("APPROVED");
        assertThat(approvalHistoryCsv).contains("REJECTED");
        assertThat(approvalHistoryCsv).contains("payload repair is incomplete");

        assertThatThrownBy(() -> failedEventMessageService.replay(
                failedMessage.getId(),
                replayRequest,
                "ops-user",
                "ORDER_SUPPORT"
        ))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex -> {
                    assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
                    assertThat(ex.getReason()).contains("RabbitMQ outbox is disabled");
                });
    }
}
