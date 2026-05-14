package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codexdemo.orderplatform.notification.FailedEventManagementHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventMessage;
import com.codexdemo.orderplatform.notification.FailedEventMessageRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayApprovalHistoryRepository;
import com.codexdemo.orderplatform.notification.FailedEventReplayAttemptRepository;
import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsOverviewIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void opsOverviewReturnsReadOnlyBusinessSignals() throws Exception {
        FailedEventMessage pendingApproval = FailedEventMessage.record(
                "ops-overview-pending",
                "event-ops-1",
                "OrderNotificationFailed",
                "ORDER",
                "1001",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v36 pending approval",
                "{\"orderId\":1001}"
        );
        pendingApproval.requestReplayApproval("needs operator review", "ops-user", Instant.now());
        failedEventMessageRepository.save(pendingApproval);
        failedEventMessageRepository.save(FailedEventMessage.record(
                "ops-overview-recorded",
                "event-ops-2",
                "OrderNotificationFailed",
                "ORDER",
                "1002",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v36 recorded",
                "{\"orderId\":1002}"
        ));

        mockMvc.perform(get("/api/v1/ops/overview"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.application.name").value("advanced-order-platform"))
                .andExpect(jsonPath("$.application.profiles").isArray())
                .andExpect(jsonPath("$.application.startedAt").exists())
                .andExpect(jsonPath("$.application.uptimeSeconds").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.orders.total").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.inventory.items").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.outbox.pending").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.failedEvents.total").value(2))
                .andExpect(jsonPath("$.failedEvents.pendingReplayApprovals").value(1))
                .andExpect(jsonPath("$.failedEvents.latestFailedAt").exists());
    }

    @Test
    void opsEvidenceReturnsReadOnlyExecutionSignals() throws Exception {
        FailedEventMessage pendingApproval = FailedEventMessage.record(
                "ops-evidence-pending",
                "event-ops-evidence-1",
                "OrderNotificationFailed",
                "ORDER",
                "2001",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v45 pending approval",
                "{\"orderId\":2001}"
        );
        pendingApproval.requestReplayApproval("needs operator review", "ops-user", Instant.now());
        failedEventMessageRepository.save(pendingApproval);
        FailedEventMessage approvedReplay = FailedEventMessage.record(
                "ops-evidence-approved",
                "event-ops-evidence-2",
                "OrderNotificationFailed",
                "ORDER",
                "2002",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v45 approved replay",
                "{\"orderId\":2002}"
        );
        approvedReplay.requestReplayApproval("safe to replay", "ops-user", Instant.now());
        approvedReplay.approveReplay("ops-reviewer", "approved for evidence", Instant.now());
        failedEventMessageRepository.save(approvedReplay);

        mockMvc.perform(get("/api/v1/ops/evidence"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.service.name").value("advanced-order-platform"))
                .andExpect(jsonPath("$.service.version").value("0.1.0-SNAPSHOT"))
                .andExpect(jsonPath("$.service.profiles").isArray())
                .andExpect(jsonPath("$.service.startedAt").exists())
                .andExpect(jsonPath("$.service.uptimeSeconds").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.healthProbe.endpoint").value("/actuator/health"))
                .andExpect(jsonPath("$.healthProbe.method").value("GET"))
                .andExpect(jsonPath("$.healthProbe.expectedStatus").value("UP"))
                .andExpect(jsonPath("$.healthProbe.evidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/api/v1/ops/overview")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/ops-read-only-evidence.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.healthProbe.liveProbeRequiredForPass").value(true))
                .andExpect(jsonPath("$.healthProbe.staticSampleOnly").value(false))
                .andExpect(jsonPath("$.failedEventReplay.totalFailedEvents").value(2))
                .andExpect(jsonPath("$.failedEventReplay.replayBacklog").value(2))
                .andExpect(jsonPath("$.failedEventReplay.pendingReplayApprovals").value(1))
                .andExpect(jsonPath("$.failedEventReplay.approvedReplayApprovals").value(1))
                .andExpect(jsonPath("$.failedEventReplay.latestFailedAt").exists())
                .andExpect(jsonPath("$.failedEventReplay.latestApprovalAt").exists())
                .andExpect(jsonPath("$.failedEventReplay.realReplayEndpoint")
                        .value("/api/v1/failed-events/{id}/replay"))
                .andExpect(jsonPath("$.failedEventReplay.realReplayAllowedByEvidence").value(false))
                .andExpect(jsonPath("$.outbox.pendingEvents").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.outbox.publisherEnabled").value(false))
                .andExpect(jsonPath("$.outbox.rabbitMqEnabled").value(false))
                .andExpect(jsonPath("$.outbox.exchange").value("order-platform.outbox"))
                .andExpect(jsonPath("$.outbox.queue").value("order-platform.outbox.events"))
                .andExpect(jsonPath("$.outbox.deadLetterQueue").value("order-platform.outbox.events.dlq"))
                .andExpect(jsonPath("$.outbox.blockers", hasItem("OUTBOX_PUBLISHER_DISABLED")))
                .andExpect(jsonPath("$.outbox.blockers", hasItem("RABBITMQ_OUTBOX_DISABLED")))
                .andExpect(jsonPath("$.approvalExecution.requiredApprovalStatus").value("APPROVED"))
                .andExpect(jsonPath("$.approvalExecution.approvalRequired").value(true))
                .andExpect(jsonPath("$.approvalExecution.dryRun").value(true))
                .andExpect(jsonPath("$.approvalExecution.executionBlockers", hasItem("READ_ONLY_EVIDENCE_ENDPOINT")))
                .andExpect(jsonPath("$.approvalExecution.executionBlockers", hasItem("REPLAY_APPROVAL_PENDING")))
                .andExpect(jsonPath("$.approvalExecution.executionBlockers", hasItem("REPLAY_BACKLOG_PRESENT")))
                .andExpect(jsonPath("$.readOnlyWindow.windowVersion").value("java-read-only-window.v1"))
                .andExpect(jsonPath("$.readOnlyWindow.operatorStartRequired").value(true))
                .andExpect(jsonPath("$.readOnlyWindow.nodeAutoStartAllowed").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.upstreamProbesRequired").value(true))
                .andExpect(jsonPath("$.readOnlyWindow.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.readyForReadOnlyLiveProbe").value(true))
                .andExpect(jsonPath("$.readOnlyWindow.readyForProductionOperations").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints", hasItem("GET /actuator/health")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints", hasItem("GET /api/v1/ops/evidence")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.forbiddenOperations",
                        hasItem("POST /api/v1/failed-events/{id}/replay")))
                .andExpect(jsonPath("$.readOnlyWindow.forbiddenOperations",
                        hasItem("Any non-GET Node upstream action")))
                .andExpect(jsonPath("$.readOnlyWindow.requiredNodeEnvironment",
                        hasItem("UPSTREAM_PROBES_ENABLED=true")))
                .andExpect(jsonPath("$.readOnlyWindow.requiredNodeEnvironment",
                        hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.readOnlyWindow.replayPostBoundary")
                        .value("Node real-read window must not call POST /api/v1/failed-events/{id}/replay"))
                .andExpect(jsonPath("$.orderIdempotency.boundaryVersion")
                        .value("java-order-idempotency-boundary.v1"))
                .andExpect(jsonPath("$.orderIdempotency.createOrderEndpoint").value("/api/v1/orders"))
                .andExpect(jsonPath("$.orderIdempotency.requiredHeader").value("Idempotency-Key"))
                .andExpect(jsonPath("$.orderIdempotency.requestFingerprintVersion")
                        .value("order-create-request-sha256.v1"))
                .andExpect(jsonPath("$.orderIdempotency.sameKeyDifferentRequestErrorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.orderIdempotency.miniKvConnected").value(false))
                .andExpect(jsonPath("$.orderIdempotency.externalTokenStoreConnected").value(false))
                .andExpect(jsonPath("$.orderIdempotency.changesPaymentOrInventoryTransaction").value(false))
                .andExpect(jsonPath("$.blockers", hasItem("READ_ONLY_EVIDENCE_ENDPOINT")))
                .andExpect(jsonPath("$.blockers", hasItem("OUTBOX_PUBLISHER_DISABLED")))
                .andExpect(jsonPath("$.blockers", hasItem("RABBITMQ_OUTBOX_DISABLED")))
                .andExpect(jsonPath("$.warnings", hasItem("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/overview")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/evidence")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/contracts/ops-read-only-evidence.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/ops-evidence-field-guide.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/{id}/replay-execution-contract")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")));
    }

    @Test
    void staticOpsReadOnlyEvidenceSampleCoversProductionPassBoundary() throws Exception {
        mockMvc.perform(get("/contracts/ops-read-only-evidence.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("OPS_READ_ONLY_EVIDENCE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.service.name").value("advanced-order-platform"))
                .andExpect(jsonPath("$.healthProbe.endpoint").value("/actuator/health"))
                .andExpect(jsonPath("$.healthProbe.expectedStatus").value("UP"))
                .andExpect(jsonPath("$.healthProbe.liveProbeRequiredForPass").value(true))
                .andExpect(jsonPath("$.healthProbe.staticSampleOnly").value(true))
                .andExpect(jsonPath("$.failedEventReplay.realReplayAllowedByEvidence").value(false))
                .andExpect(jsonPath("$.failedEventReplay.realReplayEndpoint")
                        .value("/api/v1/failed-events/{id}/replay"))
                .andExpect(jsonPath("$.outbox.publisherEnabled").value(false))
                .andExpect(jsonPath("$.outbox.rabbitMqEnabled").value(false))
                .andExpect(jsonPath("$.approvalExecution.dryRun").value(true))
                .andExpect(jsonPath("$.approvalExecution.executionBlockers",
                        hasItem("READ_ONLY_EVIDENCE_ENDPOINT")))
                .andExpect(jsonPath("$.readOnlyWindow.windowVersion").value("java-read-only-window.v1"))
                .andExpect(jsonPath("$.readOnlyWindow.nodeAutoStartAllowed").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.upstreamActionsAllowed").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.readyForReadOnlyLiveProbe").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.readyForProductionOperations").value(false))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints", hasItem("GET /actuator/health")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.forbiddenOperations",
                        hasItem("Any non-GET Node upstream action")))
                .andExpect(jsonPath("$.readOnlyWindow.requiredNodeEnvironment",
                        hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.orderIdempotency.boundaryVersion")
                        .value("java-order-idempotency-boundary.v1"))
                .andExpect(jsonPath("$.orderIdempotency.sameKeyDifferentRequestErrorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.orderIdempotency.miniKvConnected").value(false))
                .andExpect(jsonPath("$.blockers", hasItem("OUTBOX_PUBLISHER_DISABLED")))
                .andExpect(jsonPath("$.warnings", hasItem("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/evidence")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/ops-evidence-field-guide.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")))
                .andExpect(jsonPath("$.productionPassBoundary.readyForProductionPassEvidence").value(false))
                .andExpect(jsonPath("$.productionPassBoundary.allowedProbeEndpoints",
                        hasItem("GET /api/v1/ops/evidence")))
                .andExpect(jsonPath("$.productionPassBoundary.forbiddenOperations",
                        hasItem("POST /api/v1/failed-events/{id}/replay")));
    }

    @Test
    void staticOpsEvidenceFieldGuideExplainsReadOnlyCaptureFields() throws Exception {
        mockMvc.perform(get("/contracts/ops-evidence-field-guide.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guideVersion").value("java-ops-evidence-field-guide.v1"))
                .andExpect(jsonPath("$.evidenceVersion").value("java-ops-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("OPS_EVIDENCE_FIELD_GUIDE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.sourceSampleEndpoint")
                        .value("/contracts/ops-read-only-evidence.sample.json"))
                .andExpect(jsonPath("$.releaseReviewUse.intendedConsumer")
                        .value("Node read-only capture release evidence review"))
                .andExpect(jsonPath("$.releaseReviewUse.mayBeUsedForProductionPass").value(false))
                .andExpect(jsonPath("$.releaseReviewUse.requiredLiveEvidence",
                        hasItem("GET /actuator/health returns UP")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("service")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("healthProbe")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("readOnlyWindow")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("orderIdempotency")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("executionBoundaries")))
                .andExpect(jsonPath("$.fieldGroups[1].fields[*].path",
                        hasItem("healthProbe.staticSampleOnly")))
                .andExpect(jsonPath("$.fieldGroups[2].fields[*].path",
                        hasItem("readOnlyWindow.readyForReadOnlyLiveProbe")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.sameKeyDifferentRequestErrorCode")))
                .andExpect(jsonPath("$.fieldGroups[4].fields[*].path",
                        hasItem("failedEventReplay.realReplayAllowedByEvidence")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("POST /api/v1/failed-events/{id}/replay")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Any non-GET Node upstream action")));
    }

    @Test
    void staticOrderIdempotencyBoundarySampleExplainsCreateOrderConflictBoundary() throws Exception {
        mockMvc.perform(get("/contracts/order-idempotency-boundary.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boundaryVersion").value("java-order-idempotency-boundary.v1"))
                .andExpect(jsonPath("$.scenario").value("ORDER_IDEMPOTENCY_BOUNDARY_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.createOrderEndpoint").value("/api/v1/orders"))
                .andExpect(jsonPath("$.requiredHeader").value("Idempotency-Key"))
                .andExpect(jsonPath("$.requestFingerprint.version")
                        .value("order-create-request-sha256.v1"))
                .andExpect(jsonPath("$.requestFingerprint.scope",
                        hasItem("items aggregated by productId")))
                .andExpect(jsonPath("$.sameKeySameRequest.httpStatus").value(200))
                .andExpect(jsonPath("$.sameKeySameRequest.sideEffects",
                        hasItem("NO_NEW_INVENTORY_RESERVATION")))
                .andExpect(jsonPath("$.sameKeyDifferentRequest.httpStatus").value(409))
                .andExpect(jsonPath("$.sameKeyDifferentRequest.errorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.sameKeyDifferentRequest.sideEffects",
                        hasItem("NO_NEW_OUTBOX_EVENT")))
                .andExpect(jsonPath("$.storage.authoritativeStore").value("orders table"))
                .andExpect(jsonPath("$.storage.miniKvConnected").value(false))
                .andExpect(jsonPath("$.storage.orderAuthoritativeStoreRemainsJavaDatabase").value(true))
                .andExpect(jsonPath("$.verticalSliceBoundary.intendedConsumer")
                        .value("Node idempotency vertical readiness review"))
                .andExpect(jsonPath("$.verticalSliceBoundary.mayBeUsedForProductionPass").value(false))
                .andExpect(jsonPath("$.forbiddenOperationsForReadOnlyEvidence",
                        hasItem("Connecting mini-kv as the authoritative order store")));
    }

    private void deleteFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }
}
