package com.codexdemo.orderplatform.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class FailedEventReplayExecutionContractServiceTests {

    private final FailedEventReplayApprovalStatusService approvalStatusService =
            org.mockito.Mockito.mock(FailedEventReplayApprovalStatusService.class);

    private final FailedEventReplayReadinessService readinessService =
            org.mockito.Mockito.mock(FailedEventReplayReadinessService.class);

    private final FailedEventReplayExecutionContractService service =
            new FailedEventReplayExecutionContractService(approvalStatusService, readinessService);

    @Test
    void returnsReadOnlyNotFoundContract() {
        when(approvalStatusService.approvalStatus(404L)).thenReturn(
                FailedEventReplayApprovalStatusResponse.notFound(404L, Instant.parse("2026-05-12T09:00:00Z"))
        );
        when(readinessService.readiness(404L)).thenReturn(
                FailedEventReplayReadinessResponse.notFound(404L, Instant.parse("2026-05-12T09:00:00Z"))
        );

        FailedEventReplayExecutionContractResponse response = service.executionContract(404L);

        assertThat(response.exists()).isFalse();
        assertThat(response.contractVersion())
                .isEqualTo(FailedEventReplayExecutionContractService.CONTRACT_VERSION);
        assertThat(response.contractDigest()).startsWith("sha256:");
        assertThat(response.replayPreconditionsSatisfied()).isFalse();
        assertThat(response.realReplayEndpointEnforcesApprovalDigest()).isFalse();
        assertThat(response.realReplayEndpointEnforcesReplayEligibilityDigest()).isFalse();
        assertThat(response.digestVerificationMode()).isEqualTo("CLIENT_PRECHECK_ONLY");
        assertThat(response.blockedBy()).containsExactly("FAILED_EVENT_NOT_FOUND");
        assertThat(response.executionChecks().get(0).checkId()).isEqualTo("FAILED_EVENT_EXISTS");
        assertThat(response.executionChecks().get(0).status()).isEqualTo("BLOCKED");
        assertThat(response.expectedSideEffects()).isEmpty();
    }

    @Test
    void returnsStableApprovedExecutionContractDigest() {
        FailedEventReplayApprovalStatusResponse approvalStatus = approvedApprovalStatus();
        FailedEventReplayReadinessResponse readiness = approvedReadiness();
        when(approvalStatusService.approvalStatus(10L)).thenReturn(approvalStatus);
        when(readinessService.readiness(10L)).thenReturn(readiness);

        FailedEventReplayExecutionContractResponse first = service.executionContract(10L);
        FailedEventReplayExecutionContractResponse second = service.executionContract(10L);

        assertThat(first.exists()).isTrue();
        assertThat(first.replayPreconditionsSatisfied()).isTrue();
        assertThat(first.contractDigest()).isEqualTo(second.contractDigest());
        assertThat(first.approvalDigest()).isEqualTo("sha256:approval");
        assertThat(first.replayEligibilityDigest()).isEqualTo("sha256:eligibility");
        assertThat(first.idempotencyKeyHint()).isEqualTo("failed-event-replay:10:order-1001");
        assertThat(first.executionChecks())
                .extracting(FailedEventReplayExecutionContractResponse.ExecutionCheck::status)
                .containsOnly("PASSED");
        assertThat(first.expectedSideEffects()).containsExactly(
                "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
                "SAVE_REPLAY_ATTEMPT_AUDIT",
                "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
                "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
        );
    }

    @Test
    void exposesApprovalBlockerInExecutionContract() {
        when(approvalStatusService.approvalStatus(11L)).thenReturn(pendingApprovalStatus());
        when(readinessService.readiness(11L)).thenReturn(pendingReadiness());

        FailedEventReplayExecutionContractResponse response = service.executionContract(11L);

        assertThat(response.exists()).isTrue();
        assertThat(response.replayPreconditionsSatisfied()).isFalse();
        assertThat(response.blockedBy()).containsExactly("REPLAY_APPROVAL_PENDING");
        assertThat(response.nextAllowedActions()).containsExactly("REVIEW_REPLAY_APPROVAL");
        assertThat(response.executionChecks())
                .filteredOn(check -> "REPLAY_APPROVAL_APPROVED".equals(check.checkId()))
                .singleElement()
                .satisfies(check -> {
                    assertThat(check.status()).isEqualTo("BLOCKED");
                    assertThat(check.evidenceDigest()).isEqualTo("sha256:approval-pending");
                    assertThat(check.blockedBy()).containsExactly("REPLAY_APPROVAL_PENDING");
                });
    }

    private FailedEventReplayApprovalStatusResponse approvedApprovalStatus() {
        return new FailedEventReplayApprovalStatusResponse(
                Instant.parse("2026-05-12T09:10:00Z"),
                10L,
                true,
                FailedEventReplayApprovalEvidenceDigests.EVIDENCE_VERSION,
                "sha256:approval",
                "sha256:eligibility",
                FailedEventMessageStatus.RECORDED,
                FailedEventManagementStatus.OPEN,
                FailedEventReplayApprovalStatus.APPROVED,
                FailedEventReplayApprovalStatus.APPROVED,
                true,
                false,
                true,
                false,
                "need replay",
                "ops-user",
                Instant.parse("2026-05-12T09:05:00Z"),
                "sre-user",
                Instant.parse("2026-05-12T09:08:00Z"),
                "approved",
                2,
                new FailedEventReplayApprovalStatusResponse.LatestApproval(
                        FailedEventReplayApprovalHistoryAction.APPROVED,
                        FailedEventReplayApprovalStatus.APPROVED,
                        "sre-user",
                        "SRE",
                        "approved",
                        Instant.parse("2026-05-12T09:08:00Z")
                ),
                List.of(),
                List.of("REPLAY_FAILED_EVENT")
        );
    }

    private FailedEventReplayReadinessResponse approvedReadiness() {
        return new FailedEventReplayReadinessResponse(
                Instant.parse("2026-05-12T09:10:00Z"),
                10L,
                true,
                "OrderNotificationFailed",
                "ORDER",
                "order-1001",
                Instant.parse("2026-05-12T09:00:00Z"),
                FailedEventManagementStatus.OPEN,
                FailedEventReplayApprovalStatus.APPROVED,
                1L,
                true,
                false,
                List.of(),
                List.of(),
                List.of("REPLAY_FAILED_EVENT"),
                null,
                null
        );
    }

    private FailedEventReplayApprovalStatusResponse pendingApprovalStatus() {
        return new FailedEventReplayApprovalStatusResponse(
                Instant.parse("2026-05-12T09:20:00Z"),
                11L,
                true,
                FailedEventReplayApprovalEvidenceDigests.EVIDENCE_VERSION,
                "sha256:approval-pending",
                "sha256:eligibility-pending",
                FailedEventMessageStatus.RECORDED,
                FailedEventManagementStatus.OPEN,
                FailedEventReplayApprovalStatus.PENDING,
                FailedEventReplayApprovalStatus.APPROVED,
                true,
                true,
                false,
                false,
                "need replay",
                "ops-user",
                Instant.parse("2026-05-12T09:15:00Z"),
                null,
                null,
                null,
                1,
                new FailedEventReplayApprovalStatusResponse.LatestApproval(
                        FailedEventReplayApprovalHistoryAction.REQUESTED,
                        FailedEventReplayApprovalStatus.PENDING,
                        "ops-user",
                        "ORDER_SUPPORT",
                        "need replay",
                        Instant.parse("2026-05-12T09:15:00Z")
                ),
                List.of("REPLAY_APPROVAL_PENDING"),
                List.of("REVIEW_REPLAY_APPROVAL")
        );
    }

    private FailedEventReplayReadinessResponse pendingReadiness() {
        return new FailedEventReplayReadinessResponse(
                Instant.parse("2026-05-12T09:20:00Z"),
                11L,
                true,
                "OrderNotificationFailed",
                "ORDER",
                "order-1002",
                Instant.parse("2026-05-12T09:12:00Z"),
                FailedEventManagementStatus.OPEN,
                FailedEventReplayApprovalStatus.PENDING,
                1L,
                false,
                true,
                List.of("REPLAY_APPROVAL_PENDING"),
                List.of(),
                List.of("REVIEW_REPLAY_APPROVAL"),
                null,
                null
        );
    }
}
