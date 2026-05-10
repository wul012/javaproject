package com.codexdemo.orderplatform.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(
        name = "failed_event_replay_approval_history",
        indexes = {
                @Index(
                        name = "idx_failed_event_replay_approval_history_message",
                        columnList = "failed_event_message_id, changed_at"
                ),
                @Index(
                        name = "idx_failed_event_replay_approval_history_action",
                        columnList = "action, changed_at"
                ),
                @Index(
                        name = "idx_failed_event_replay_approval_history_operator_role",
                        columnList = "operator_role, changed_at"
                ),
                @Index(
                        name = "idx_failed_event_replay_approval_history_operator_id",
                        columnList = "operator_id, changed_at"
                )
        }
)
public class FailedEventReplayApprovalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "failed_event_message_id", nullable = false)
    private FailedEventMessage failedEventMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private FailedEventReplayApprovalHistoryAction action;

    @Column(name = "operator_id", nullable = false, length = 80)
    private String operatorId;

    @Column(name = "operator_role", nullable = false, length = 80)
    private String operatorRole;

    @Column(length = 500)
    private String note;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    protected FailedEventReplayApprovalHistory() {
    }

    private FailedEventReplayApprovalHistory(
            FailedEventMessage failedEventMessage,
            FailedEventReplayApprovalHistoryAction action,
            String operatorId,
            String operatorRole,
            String note,
            Instant changedAt
    ) {
        if (failedEventMessage == null) {
            throw new IllegalArgumentException("failedEventMessage is required");
        }
        if (action == null) {
            throw new IllegalArgumentException("action is required");
        }
        if (operatorId == null || operatorId.isBlank()) {
            throw new IllegalArgumentException("operatorId is required");
        }
        if (operatorRole == null || operatorRole.isBlank()) {
            throw new IllegalArgumentException("operatorRole is required");
        }
        if (changedAt == null) {
            throw new IllegalArgumentException("changedAt is required");
        }
        this.failedEventMessage = failedEventMessage;
        this.action = action;
        this.operatorId = operatorId;
        this.operatorRole = operatorRole;
        this.note = note;
        this.changedAt = changedAt;
    }

    public static FailedEventReplayApprovalHistory record(
            FailedEventMessage failedEventMessage,
            FailedEventReplayApprovalHistoryAction action,
            String operatorId,
            String operatorRole,
            String note,
            Instant changedAt
    ) {
        return new FailedEventReplayApprovalHistory(
                failedEventMessage,
                action,
                operatorId,
                operatorRole,
                note,
                changedAt
        );
    }

    public Long getId() {
        return id;
    }

    public FailedEventMessage getFailedEventMessage() {
        return failedEventMessage;
    }

    public FailedEventReplayApprovalHistoryAction getAction() {
        return action;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getOperatorRole() {
        return operatorRole;
    }

    public String getNote() {
        return note;
    }

    public Instant getChangedAt() {
        return changedAt;
    }
}
