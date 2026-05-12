package com.codexdemo.orderplatform.notification;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;

final class FailedEventReplayApprovalEvidenceDigests {

    static final String EVIDENCE_VERSION = "failed-event-approval-status.v1";

    private FailedEventReplayApprovalEvidenceDigests() {
    }

    static String approvalDigest(
            Long failedEventId,
            boolean exists,
            FailedEventReplayApprovalStatus approvalStatus,
            FailedEventReplayApprovalStatus requiredApprovalStatus,
            boolean approvalRequested,
            boolean approvalPending,
            boolean approvedForReplay,
            boolean rejected,
            String requestReason,
            String requestedBy,
            Instant requestedAt,
            String reviewedBy,
            Instant reviewedAt,
            String reviewNote,
            long historyCount,
            FailedEventReplayApprovalStatusResponse.LatestApproval latestApproval
    ) {
        return digest(List.of(
                line("digestKind", "approval"),
                line("evidenceVersion", EVIDENCE_VERSION),
                line("failedEventId", failedEventId),
                line("exists", exists),
                line("approvalStatus", approvalStatus),
                line("requiredApprovalStatus", requiredApprovalStatus),
                line("approvalRequested", approvalRequested),
                line("approvalPending", approvalPending),
                line("approvedForReplay", approvedForReplay),
                line("rejected", rejected),
                line("requestReason", requestReason),
                line("requestedBy", requestedBy),
                line("requestedAt", requestedAt),
                line("reviewedBy", reviewedBy),
                line("reviewedAt", reviewedAt),
                line("reviewNote", reviewNote),
                line("historyCount", historyCount),
                line("latestApproval.action", latestApproval == null ? null : latestApproval.action()),
                line("latestApproval.status", latestApproval == null ? null : latestApproval.status()),
                line("latestApproval.operatorId", latestApproval == null ? null : latestApproval.operatorId()),
                line("latestApproval.operatorRole", latestApproval == null ? null : latestApproval.operatorRole()),
                line("latestApproval.note", latestApproval == null ? null : latestApproval.note()),
                line("latestApproval.changedAt", latestApproval == null ? null : latestApproval.changedAt())
        ));
    }

    static String replayEligibilityDigest(
            Long failedEventId,
            boolean exists,
            FailedEventMessageStatus failedEventStatus,
            FailedEventManagementStatus managementStatus,
            FailedEventReplayApprovalStatus approvalStatus,
            FailedEventReplayApprovalStatus requiredApprovalStatus,
            boolean approvedForReplay,
            List<String> approvalBlockedBy,
            List<String> nextAllowedActions
    ) {
        return digest(List.of(
                line("digestKind", "replayEligibility"),
                line("evidenceVersion", EVIDENCE_VERSION),
                line("failedEventId", failedEventId),
                line("exists", exists),
                line("failedEventStatus", failedEventStatus),
                line("managementStatus", managementStatus),
                line("approvalStatus", approvalStatus),
                line("requiredApprovalStatus", requiredApprovalStatus),
                line("approvedForReplay", approvedForReplay),
                line("approvalBlockedBy", approvalBlockedBy),
                line("nextAllowedActions", nextAllowedActions)
        ));
    }

    private static String digest(List<String> lines) {
        String canonical = String.join("\n", lines) + "\n";
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(canonical.getBytes(StandardCharsets.UTF_8));
            return "sha256:" + HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 digest algorithm is not available", ex);
        }
    }

    private static String line(String key, Object value) {
        return key + "=" + value(value);
    }

    private static String value(Object value) {
        if (value == null) {
            return "<null>";
        }
        if (value instanceof List<?> list) {
            return "[" + String.join(",", list.stream().map(FailedEventReplayApprovalEvidenceDigests::value).toList())
                    + "]";
        }
        return String.valueOf(value);
    }
}
