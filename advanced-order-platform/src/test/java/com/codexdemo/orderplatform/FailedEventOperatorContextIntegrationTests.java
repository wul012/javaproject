package com.codexdemo.orderplatform;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventManagementStatus;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryAction;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {"order.expiration.enabled=false", "outbox.publisher.enabled=false"})
@AutoConfigureMockMvc
class FailedEventOperatorContextIntegrationTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private FailedEventMessageRepository failedEventMessageRepository;

  @Autowired private FailedEventReplayAttemptRepository failedEventReplayAttemptRepository;

  @Autowired private FailedEventManagementHistoryRepository failedEventManagementHistoryRepository;

  @Autowired
  private FailedEventReplayApprovalHistoryRepository failedEventReplayApprovalHistoryRepository;

  @BeforeEach
  void cleanFailedEventData() {
    failedEventReplayApprovalHistoryRepository.deleteAll();
    failedEventManagementHistoryRepository.deleteAll();
    failedEventReplayAttemptRepository.deleteAll();
    failedEventMessageRepository.deleteAll();
  }

  @Test
  void resolvesOperatorContextFromHeadersForProbeAndManagementMutation() throws Exception {
    FailedEventMessage failedMessage = failedEventMessageRepository.save(failedEventMessage());

    mockMvc
        .perform(
            get("/api/v1/failed-events/operator-context")
                .header("X-Operator-Id", " ops-user ")
                .header("X-Operator-Role", " sre "))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operatorId").value("ops-user"))
        .andExpect(jsonPath("$.operatorRole").value("SRE"))
        .andExpect(
            jsonPath("$.allowedRoles").value(containsInAnyOrder("ORDER_SUPPORT", "SRE", "SYSTEM")))
        .andExpect(
            jsonPath("$.allowedRolesByAction.MANAGE_FAILED_EVENT")
                .value(containsInAnyOrder("ORDER_SUPPORT", "SRE", "SYSTEM")))
        .andExpect(
            jsonPath("$.allowedRolesByAction.REVIEW_REPLAY_APPROVAL")
                .value(containsInAnyOrder("SRE", "SYSTEM")))
        .andExpect(jsonPath("$.actionDecisions.MANAGE_FAILED_EVENT.allowed").value(true))
        .andExpect(
            jsonPath("$.actionDecisions.MANAGE_FAILED_EVENT.allowedRoles")
                .value(containsInAnyOrder("ORDER_SUPPORT", "SRE", "SYSTEM")))
        .andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowed").value(true))
        .andExpect(
            jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowedRoles")
                .value(containsInAnyOrder("SRE", "SYSTEM")))
        .andExpect(
            jsonPath("$.allowedActions")
                .value(
                    containsInAnyOrder(
                        "MANAGE_FAILED_EVENT",
                        "REQUEST_REPLAY_APPROVAL",
                        "REVIEW_REPLAY_APPROVAL",
                        "REPLAY_FAILED_EVENT")))
        .andExpect(jsonPath("$.deniedActions").isEmpty());

    mockMvc
        .perform(
            post("/api/v1/failed-events/management-status")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", " ops-user ")
                .header("X-Operator-Role", " order_support ")
                .content(
                    """
                                {
                                  "ids": [%d],
                                  "status": "INVESTIGATING",
                                  "note": "operator context v29"
                                }
                                """
                        .formatted(failedMessage.getId())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(FailedEventManagementStatus.INVESTIGATING.name()))
        .andExpect(jsonPath("$.items[0].managedBy").value("ops-user"));

    assertThat(
            failedEventManagementHistoryRepository
                .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId()))
        .singleElement()
        .satisfies(
            history -> {
              assertThat(history.getOperatorId()).isEqualTo("ops-user");
              assertThat(history.getOperatorRole()).isEqualTo("ORDER_SUPPORT");
              assertThat(history.getNote()).isEqualTo("operator context v29");
            });
  }

  @Test
  void appliesActionRolesForReplayApprovalReview() throws Exception {
    FailedEventMessage failedMessage = failedEventMessageRepository.save(failedEventMessage());

    mockMvc
        .perform(
            get("/api/v1/failed-events/operator-context")
                .header("X-Operator-Id", "ops-user")
                .header("X-Operator-Role", "viewer"))
        .andExpect(status().isForbidden());

    mockMvc
        .perform(
            get("/api/v1/failed-events/operator-context")
                .header("X-Operator-Id", "support-user")
                .header("X-Operator-Role", "order_support"))
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.allowedActions")
                .value(
                    containsInAnyOrder(
                        "MANAGE_FAILED_EVENT", "REQUEST_REPLAY_APPROVAL", "REPLAY_FAILED_EVENT")))
        .andExpect(jsonPath("$.actionDecisions.REQUEST_REPLAY_APPROVAL.allowed").value(true))
        .andExpect(
            jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.action")
                .value("REVIEW_REPLAY_APPROVAL"))
        .andExpect(jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowed").value(false))
        .andExpect(
            jsonPath("$.actionDecisions.REVIEW_REPLAY_APPROVAL.allowedRoles")
                .value(containsInAnyOrder("SRE", "SYSTEM")))
        .andExpect(jsonPath("$.deniedActions").value(containsInAnyOrder("REVIEW_REPLAY_APPROVAL")));

    mockMvc
        .perform(
            post("/api/v1/failed-events/{id}/replay-approval", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", " ops-user ")
                .header("X-Operator-Role", " order_support ")
                .content(
                    """
                                {
                                  "reason": "operator context approval request"
                                }
                                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.replayApprovalRequestedBy").value("ops-user"))
        .andExpect(jsonPath("$.replayApprovalStatus").value("PENDING"));

    assertThat(
            failedEventReplayApprovalHistoryRepository
                .findByFailedEventMessageIdOrderByChangedAtDescIdDesc(failedMessage.getId()))
        .singleElement()
        .satisfies(
            history -> {
              assertThat(history.getAction())
                  .isEqualTo(FailedEventReplayApprovalHistoryAction.REQUESTED);
              assertThat(history.getOperatorId()).isEqualTo("ops-user");
              assertThat(history.getOperatorRole()).isEqualTo("ORDER_SUPPORT");
            });

    mockMvc
        .perform(
            post("/api/v1/failed-events/{id}/replay-approval/review", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "support-reviewer")
                .header("X-Operator-Role", "ORDER_SUPPORT")
                .content(
                    """
                                {
                                  "status": "APPROVED",
                                  "note": "support role should not review"
                                }
                                """))
        .andExpect(status().isForbidden())
        .andExpect(
            status().reason("operator role is not allowed for action: REVIEW_REPLAY_APPROVAL"));

    mockMvc
        .perform(
            post("/api/v1/failed-events/{id}/replay-approval/review", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "sre-lead")
                .header("X-Operator-Role", "SRE")
                .content(
                    """
                                {
                                  "status": "APPROVED",
                                  "note": "sre review is allowed"
                                }
                                """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.replayApprovalReviewedBy").value("sre-lead"))
        .andExpect(jsonPath("$.replayApprovalStatus").value("APPROVED"));
  }

  @Test
  void rejectsInvalidFailedEventWriteRequestBodiesBeforeMutation() throws Exception {
    FailedEventMessage failedMessage = failedEventMessageRepository.save(failedEventMessage());

    mockMvc
        .perform(
            post("/api/v1/failed-events/management-status")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "ops-user")
                .header("X-Operator-Role", "SRE")
                .content(
                    """
                                {
                                  "ids": [],
                                  "status": "INVESTIGATING",
                                  "note": "empty id list should fail at the web boundary"
                                }
                                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());

    mockMvc
        .perform(
            post("/api/v1/failed-events/{id}/replay-approval", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "support-user")
                .header("X-Operator-Role", "ORDER_SUPPORT")
                .content(
                    """
                                {
                                  "reason": " "
                                }
                                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());

    mockMvc
        .perform(
            post("/api/v1/failed-events/{id}/replay-approval/review", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "sre-user")
                .header("X-Operator-Role", "SRE")
                .content(
                    """
                                {
                                  "note": "missing status should fail before review logic"
                                }
                                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());

    mockMvc
        .perform(
            post("/api/v1/failed-events/{id}/replay", failedMessage.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-Operator-Id", "sre-user")
                .header("X-Operator-Role", "SRE")
                .content(
                    """
                                {
                                  "reason": " "
                                }
                                """))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.title").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.fieldErrors[0]").exists());
  }

  private FailedEventMessage failedEventMessage() {
    return FailedEventMessage.record(
        "v29-message-001",
        "29292929-2929-2929-2929-292929292901",
        "OrderCreated",
        "ORDER",
        "2901",
        "order-platform.outbox.events",
        "order-platform.outbox.events.dlq",
        "listener rejected event",
        "{\"aggregateId\":\"2901\"}");
  }
}
