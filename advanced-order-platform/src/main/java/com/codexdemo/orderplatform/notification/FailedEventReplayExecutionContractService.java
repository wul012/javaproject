package com.codexdemo.orderplatform.notification;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FailedEventReplayExecutionContractService {

    static final String CONTRACT_VERSION = "failed-event-replay-execution-contract.v1";

    private static final String DIGEST_VERIFICATION_MODE = "CLIENT_PRECHECK_ONLY";

    private static final String REAL_EXECUTION_METHOD = "POST";

    private static final String REAL_EXECUTION_PATH = "/api/v1/failed-events/{id}/replay";

    private final FailedEventReplayApprovalStatusService approvalStatusService;

    private final FailedEventReplayReadinessService readinessService;

    public FailedEventReplayExecutionContractService(
            FailedEventReplayApprovalStatusService approvalStatusService,
            FailedEventReplayReadinessService readinessService
    ) {
        this.approvalStatusService = approvalStatusService;
        this.readinessService = readinessService;
    }

    @Transactional(readOnly = true)
    public FailedEventReplayExecutionContractResponse executionContract(Long id) {
        Instant sampledAt = Instant.now();
        FailedEventReplayApprovalStatusResponse approvalStatus = approvalStatusService.approvalStatus(id);
        FailedEventReplayReadinessResponse readiness = readinessService.readiness(id);
        List<FailedEventReplayExecutionContractResponse.ExecutionCheck> executionChecks =
                executionChecks(approvalStatus, readiness);
        List<FailedEventReplayExecutionContractResponse.RequestRequirement> requestRequirements =
                requestRequirements();
        List<String> blockedBy = mergedBlockedBy(readiness.blockedBy(), approvalStatus.approvalBlockedBy());
        boolean replayPreconditionsSatisfied = readiness.exists() && readiness.eligibleForReplay();
        List<String> expectedSideEffects = expectedSideEffects(replayPreconditionsSatisfied);
        String idempotencyKeyHint = idempotencyKeyHint(readiness);
        String contractDigest = contractDigest(
                approvalStatus,
                readiness,
                replayPreconditionsSatisfied,
                idempotencyKeyHint,
                executionChecks,
                requestRequirements,
                blockedBy,
                expectedSideEffects
        );
        return new FailedEventReplayExecutionContractResponse(
                sampledAt,
                readiness.failedEventId(),
                readiness.exists(),
                CONTRACT_VERSION,
                contractDigest,
                approvalStatus.evidenceVersion(),
                approvalStatus.approvalDigest(),
                approvalStatus.replayEligibilityDigest(),
                approvalStatus.failedEventStatus(),
                approvalStatus.managementStatus(),
                approvalStatus.approvalStatus(),
                approvalStatus.requiredApprovalStatus(),
                replayPreconditionsSatisfied,
                false,
                false,
                DIGEST_VERIFICATION_MODE,
                REAL_EXECUTION_METHOD,
                REAL_EXECUTION_PATH,
                FailedEventOperatorAction.REPLAY_FAILED_EVENT.name(),
                idempotencyKeyHint,
                readiness.aggregateId(),
                executionChecks,
                requestRequirements,
                blockedBy,
                readiness.warnings(),
                expectedSideEffects,
                readiness.nextAllowedActions()
        );
    }

    private List<FailedEventReplayExecutionContractResponse.ExecutionCheck> executionChecks(
            FailedEventReplayApprovalStatusResponse approvalStatus,
            FailedEventReplayReadinessResponse readiness
    ) {
        boolean exists = readiness.exists();
        return List.of(
                check(
                        "FAILED_EVENT_EXISTS",
                        "FailedEventMessageRepository.findById",
                        "STATUS",
                        true,
                        exists ? "PASSED" : "BLOCKED",
                        "exists=true",
                        "exists=%s".formatted(exists),
                        null,
                        exists ? List.of() : List.of("FAILED_EVENT_NOT_FOUND")
                ),
                check(
                        "REPLAY_APPROVAL_APPROVED",
                        "FailedEventMessageService.replay",
                        "APPROVAL",
                        true,
                        approvalStatus.approvedForReplay() ? "PASSED" : "BLOCKED",
                        "approvalStatus=APPROVED",
                        "approvalStatus=%s".formatted(value(approvalStatus.approvalStatus())),
                        approvalStatus.approvalDigest(),
                        approvalStatus.approvalBlockedBy()
                ),
                check(
                        "REPLAY_ELIGIBILITY_DIGEST_AVAILABLE",
                        "FailedEventReplayApprovalStatusService.approvalStatus",
                        "DIGEST",
                        true,
                        hasText(approvalStatus.replayEligibilityDigest()) ? "PASSED" : "BLOCKED",
                        "replayEligibilityDigest starts with sha256:",
                        "replayEligibilityDigest=%s".formatted(value(approvalStatus.replayEligibilityDigest())),
                        approvalStatus.replayEligibilityDigest(),
                        hasText(approvalStatus.replayEligibilityDigest())
                                ? List.of()
                                : List.of("REPLAY_ELIGIBILITY_DIGEST_MISSING")
                ),
                existingStateCheck(
                        "FAILED_EVENT_NOT_REPLAYED",
                        "FailedEventMessageService.replay",
                        "STATUS",
                        "failedEventStatus!=REPLAYED",
                        approvalStatus.failedEventStatus(),
                        exists && approvalStatus.failedEventStatus() != FailedEventMessageStatus.REPLAYED,
                        "ALREADY_REPLAYED"
                ),
                readinessBlockerCheck(
                        "RABBITMQ_OUTBOX_ENABLED",
                        "FailedEventMessageService.replay",
                        "STATUS",
                        "outbox.rabbitmq.enabled=true",
                        "RABBITMQ_OUTBOX_DISABLED",
                        exists,
                        readiness
                ),
                readinessBlockerCheck(
                        "EVENT_TYPE_PRESENT",
                        "FailedEventMessageService.replay",
                        "STATUS",
                        "eventType present after request fallback",
                        "EVENT_TYPE_REQUIRED",
                        exists,
                        readiness
                ),
                readinessBlockerCheck(
                        "AGGREGATE_TYPE_PRESENT",
                        "FailedEventMessageService.replay",
                        "STATUS",
                        "aggregateType present after request fallback",
                        "AGGREGATE_TYPE_REQUIRED",
                        exists,
                        readiness
                ),
                readinessBlockerCheck(
                        "AGGREGATE_ID_PRESENT",
                        "FailedEventMessageService.replay",
                        "STATUS",
                        "aggregateId present after request fallback",
                        "AGGREGATE_ID_REQUIRED",
                        exists,
                        readiness
                ),
                readinessBlockerCheck(
                        "PAYLOAD_PRESENT",
                        "FailedEventMessageService.replay",
                        "STATUS",
                        "payload present after request fallback",
                        "PAYLOAD_REQUIRED",
                        exists,
                        readiness
                )
        );
    }

    private FailedEventReplayExecutionContractResponse.ExecutionCheck existingStateCheck(
            String checkId,
            String source,
            String category,
            String requiredValue,
            Object currentValue,
            boolean passed,
            String blockedReason
    ) {
        String status = currentValue == null ? "NOT_APPLICABLE" : passed ? "PASSED" : "BLOCKED";
        return check(
                checkId,
                source,
                category,
                true,
                status,
                requiredValue,
                "current=%s".formatted(value(currentValue)),
                null,
                "BLOCKED".equals(status) ? List.of(blockedReason) : List.of()
        );
    }

    private FailedEventReplayExecutionContractResponse.ExecutionCheck readinessBlockerCheck(
            String checkId,
            String source,
            String category,
            String requiredValue,
            String blocker,
            boolean exists,
            FailedEventReplayReadinessResponse readiness
    ) {
        boolean blocked = readiness.blockedBy().contains(blocker);
        String status = exists ? blocked ? "BLOCKED" : "PASSED" : "NOT_APPLICABLE";
        return check(
                checkId,
                source,
                category,
                true,
                status,
                requiredValue,
                "%s=%s".formatted(blocker, blocked),
                null,
                blocked ? List.of(blocker) : List.of()
        );
    }

    private FailedEventReplayExecutionContractResponse.ExecutionCheck check(
            String checkId,
            String source,
            String category,
            boolean required,
            String status,
            String requiredValue,
            String currentValue,
            String evidenceDigest,
            List<String> blockedBy
    ) {
        return new FailedEventReplayExecutionContractResponse.ExecutionCheck(
                checkId,
                source,
                category,
                required,
                status,
                requiredValue,
                currentValue,
                evidenceDigest,
                List.copyOf(blockedBy)
        );
    }

    private List<FailedEventReplayExecutionContractResponse.RequestRequirement> requestRequirements() {
        return List.of(
                new FailedEventReplayExecutionContractResponse.RequestRequirement(
                        "reason",
                        true,
                        "non-blank replay reason is required",
                        null
                ),
                new FailedEventReplayExecutionContractResponse.RequestRequirement(
                        "eventId",
                        false,
                        "must be a UUID when supplied",
                        "stored eventId, otherwise generated UUID"
                ),
                new FailedEventReplayExecutionContractResponse.RequestRequirement(
                        "eventType",
                        false,
                        "must be present after request fallback",
                        "stored eventType"
                ),
                new FailedEventReplayExecutionContractResponse.RequestRequirement(
                        "aggregateType",
                        false,
                        "must be present after request fallback",
                        "stored aggregateType"
                ),
                new FailedEventReplayExecutionContractResponse.RequestRequirement(
                        "aggregateId",
                        false,
                        "must be present after request fallback",
                        "stored aggregateId"
                ),
                new FailedEventReplayExecutionContractResponse.RequestRequirement(
                        "payload",
                        false,
                        "must be present after request fallback",
                        "stored payload"
                )
        );
    }

    private List<String> mergedBlockedBy(List<String> readinessBlockedBy, List<String> approvalBlockedBy) {
        Set<String> blockedBy = new LinkedHashSet<>();
        blockedBy.addAll(readinessBlockedBy);
        blockedBy.addAll(approvalBlockedBy);
        return List.copyOf(blockedBy);
    }

    private String idempotencyKeyHint(FailedEventReplayReadinessResponse readiness) {
        if (!readiness.exists()) {
            return null;
        }
        String aggregate = readiness.aggregateId() == null || readiness.aggregateId().isBlank()
                ? "unknown-aggregate"
                : readiness.aggregateId();
        return "failed-event-replay:%s:%s".formatted(readiness.failedEventId(), aggregate);
    }

    private List<String> expectedSideEffects(boolean replayPreconditionsSatisfied) {
        if (!replayPreconditionsSatisfied) {
            return List.of();
        }
        return List.of(
                "PUBLISH_RABBITMQ_REPLAY_MESSAGE",
                "SAVE_REPLAY_ATTEMPT_AUDIT",
                "MARK_FAILED_EVENT_REPLAYED_ON_SUCCESS",
                "MARK_FAILED_EVENT_REPLAY_FAILED_ON_BROKER_ERROR"
        );
    }

    private String contractDigest(
            FailedEventReplayApprovalStatusResponse approvalStatus,
            FailedEventReplayReadinessResponse readiness,
            boolean replayPreconditionsSatisfied,
            String idempotencyKeyHint,
            List<FailedEventReplayExecutionContractResponse.ExecutionCheck> executionChecks,
            List<FailedEventReplayExecutionContractResponse.RequestRequirement> requestRequirements,
            List<String> blockedBy,
            List<String> expectedSideEffects
    ) {
        List<String> lines = new ArrayList<>();
        lines.add(line("digestKind", "replayExecutionContract"));
        lines.add(line("contractVersion", CONTRACT_VERSION));
        lines.add(line("failedEventId", readiness.failedEventId()));
        lines.add(line("exists", readiness.exists()));
        lines.add(line("approvalEvidenceVersion", approvalStatus.evidenceVersion()));
        lines.add(line("approvalDigest", approvalStatus.approvalDigest()));
        lines.add(line("replayEligibilityDigest", approvalStatus.replayEligibilityDigest()));
        lines.add(line("failedEventStatus", approvalStatus.failedEventStatus()));
        lines.add(line("managementStatus", approvalStatus.managementStatus()));
        lines.add(line("approvalStatus", approvalStatus.approvalStatus()));
        lines.add(line("requiredApprovalStatus", approvalStatus.requiredApprovalStatus()));
        lines.add(line("replayPreconditionsSatisfied", replayPreconditionsSatisfied));
        lines.add(line("realReplayEndpointEnforcesApprovalDigest", false));
        lines.add(line("realReplayEndpointEnforcesReplayEligibilityDigest", false));
        lines.add(line("digestVerificationMode", DIGEST_VERIFICATION_MODE));
        lines.add(line("realExecutionMethod", REAL_EXECUTION_METHOD));
        lines.add(line("realExecutionPath", REAL_EXECUTION_PATH));
        lines.add(line("requiredOperatorAction", FailedEventOperatorAction.REPLAY_FAILED_EVENT.name()));
        lines.add(line("idempotencyKeyHint", idempotencyKeyHint));
        lines.add(line("expectedAggregateId", readiness.aggregateId()));
        addChecks(lines, executionChecks);
        addRequirements(lines, requestRequirements);
        lines.add(line("blockedBy", blockedBy));
        lines.add(line("warnings", readiness.warnings()));
        lines.add(line("expectedSideEffects", expectedSideEffects));
        lines.add(line("nextAllowedActions", readiness.nextAllowedActions()));
        return digest(lines);
    }

    private void addChecks(
            List<String> lines,
            List<FailedEventReplayExecutionContractResponse.ExecutionCheck> executionChecks
    ) {
        for (int i = 0; i < executionChecks.size(); i++) {
            FailedEventReplayExecutionContractResponse.ExecutionCheck check = executionChecks.get(i);
            String prefix = "executionChecks[%s].".formatted(i);
            lines.add(line(prefix + "checkId", check.checkId()));
            lines.add(line(prefix + "source", check.source()));
            lines.add(line(prefix + "category", check.category()));
            lines.add(line(prefix + "required", check.required()));
            lines.add(line(prefix + "status", check.status()));
            lines.add(line(prefix + "requiredValue", check.requiredValue()));
            lines.add(line(prefix + "currentValue", check.currentValue()));
            lines.add(line(prefix + "evidenceDigest", check.evidenceDigest()));
            lines.add(line(prefix + "blockedBy", check.blockedBy()));
        }
    }

    private void addRequirements(
            List<String> lines,
            List<FailedEventReplayExecutionContractResponse.RequestRequirement> requestRequirements
    ) {
        for (int i = 0; i < requestRequirements.size(); i++) {
            FailedEventReplayExecutionContractResponse.RequestRequirement requirement = requestRequirements.get(i);
            String prefix = "requestRequirements[%s].".formatted(i);
            lines.add(line(prefix + "field", requirement.field()));
            lines.add(line(prefix + "requiredForPost", requirement.requiredForPost()));
            lines.add(line(prefix + "rule", requirement.rule()));
            lines.add(line(prefix + "fallback", requirement.fallback()));
        }
    }

    private String digest(List<String> lines) {
        String canonical = String.join("\n", lines) + "\n";
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(canonical.getBytes(StandardCharsets.UTF_8));
            return "sha256:" + HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 digest algorithm is not available", ex);
        }
    }

    private String line(String key, Object value) {
        return key + "=" + value(value);
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String value(Object value) {
        if (value == null) {
            return "<null>";
        }
        if (value instanceof List<?> list) {
            return "[" + String.join(",", list.stream().map(this::value).toList()) + "]";
        }
        return String.valueOf(value);
    }
}
