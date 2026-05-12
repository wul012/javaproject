package com.codexdemo.orderplatform.notification;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FailedEventReplayEvidenceIndexService {

    static final String EVIDENCE_VERSION = "failed-event-replay-evidence-index.v1";

    public FailedEventReplayEvidenceIndexResponse index() {
        return new FailedEventReplayEvidenceIndexResponse(
                Instant.now(),
                EVIDENCE_VERSION,
                true,
                false,
                liveEvidenceEndpoints(),
                staticEvidenceSamples(),
                auditIdentityFields(),
                executionSafetyRules(),
                productionReadinessNotes()
        );
    }

    private List<FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint> liveEvidenceEndpoints() {
        return List.of(
                new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
                        "failed-event-summary",
                        "GET",
                        "/api/v1/failed-events/summary",
                        "Aggregate replay backlog and approval status signals.",
                        true,
                        false
                ),
                new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
                        "replay-readiness",
                        "GET",
                        "/api/v1/failed-events/{id}/replay-readiness",
                        "Explain whether one failed event is eligible for replay.",
                        true,
                        false
                ),
                new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
                        "approval-status",
                        "GET",
                        "/api/v1/failed-events/{id}/approval-status",
                        "Expose stored approval status and approval/replay eligibility digests.",
                        true,
                        false
                ),
                new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
                        "replay-execution-contract",
                        "GET",
                        "/api/v1/failed-events/{id}/replay-execution-contract",
                        "Describe the preconditions and expected side effects of real replay.",
                        true,
                        false
                ),
                new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
                        "replay-attempts",
                        "GET",
                        "/api/v1/failed-events/{id}/replay-attempts",
                        "Read replay attempt audit history for one failed event.",
                        true,
                        false
                ),
                new FailedEventReplayEvidenceIndexResponse.LiveEvidenceEndpoint(
                        "replay-approval-history",
                        "GET",
                        "/api/v1/failed-events/{id}/replay-approval-history",
                        "Read replay approval request/review audit history for one failed event.",
                        true,
                        false
                )
        );
    }

    private List<FailedEventReplayEvidenceIndexResponse.StaticEvidenceSample> staticEvidenceSamples() {
        return List.of(
                new FailedEventReplayEvidenceIndexResponse.StaticEvidenceSample(
                        "execution-contract-approved",
                        "/contracts/failed-event-replay-execution-contract-approved.sample.json",
                        "APPROVED_EXECUTION_CONTRACT",
                        "failed-event-replay-execution-contract.v1",
                        List.of(
                                "contractDigest",
                                "approvalDigest",
                                "replayEligibilityDigest",
                                "replayPreconditionsSatisfied",
                                "expectedSideEffects"
                        )
                ),
                new FailedEventReplayEvidenceIndexResponse.StaticEvidenceSample(
                        "execution-contract-blocked",
                        "/contracts/failed-event-replay-execution-contract-blocked.sample.json",
                        "BLOCKED_EXECUTION_CONTRACT",
                        "failed-event-replay-execution-contract.v1",
                        List.of(
                                "approvalStatus",
                                "blockedBy",
                                "executionChecks",
                                "expectedSideEffects",
                                "nextAllowedActions"
                        )
                ),
                new FailedEventReplayEvidenceIndexResponse.StaticEvidenceSample(
                        "replay-audit-approved",
                        "/contracts/failed-event-replay-audit-approved.sample.json",
                        "APPROVED_REPLAY_AUDIT",
                        "failed-event-replay-audit-evidence.v1",
                        List.of(
                                "operator",
                                "requestId",
                                "decisionId",
                                "dryRun",
                                "executionAllowed",
                                "auditTrail"
                        )
                ),
                new FailedEventReplayEvidenceIndexResponse.StaticEvidenceSample(
                        "replay-audit-blocked",
                        "/contracts/failed-event-replay-audit-blocked.sample.json",
                        "BLOCKED_REPLAY_AUDIT",
                        "failed-event-replay-audit-evidence.v1",
                        List.of(
                                "operator",
                                "requestId",
                                "decisionId",
                                "dryRun",
                                "executionAllowed",
                                "blockedBy",
                                "auditTrail"
                        )
                )
        );
    }

    private List<String> auditIdentityFields() {
        return List.of(
                "operator.operatorId",
                "operator.operatorRole",
                "requestId",
                "decisionId",
                "approval.requestedBy",
                "approval.reviewedBy",
                "execution.attemptAuditType",
                "execution.attemptStatus"
        );
    }

    private List<String> executionSafetyRules() {
        return List.of(
                "REAL_REPLAY_REQUIRES_APPROVED_STATUS",
                "REAL_REPLAY_REQUIRES_OPERATOR_ACTION_REPLAY_FAILED_EVENT",
                "REAL_REPLAY_REQUIRES_NON_BLANK_REASON",
                "REAL_REPLAY_REQUIRES_RABBITMQ_OUTBOX_ENABLED",
                "READ_ONLY_EVIDENCE_ENDPOINTS_MUST_NOT_CHANGE_REPLAY_STATE",
                "BLOCKED_PRECHECK_MUST_NOT_CREATE_REPLAY_ATTEMPT"
        );
    }

    private List<String> productionReadinessNotes() {
        return List.of(
                "This index is read-only and does not execute replay.",
                "Static samples are fixtures for smoke, diagnostics, and control-plane contract alignment.",
                "Live endpoints should be queried before POST /api/v1/failed-events/{id}/replay.",
                "The real replay POST still owns the final approval, role, request, and RabbitMQ checks."
        );
    }
}
