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
                        hasItem("/api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/ops-read-only-evidence.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.healthProbe.additionalProbeEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
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
                        hasItem("GET /api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/production-deployment-runbook-contract.sample.json")))
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
                .andExpect(jsonPath("$.orderIdempotency.storeAbstractionVersion")
                        .value("java-idempotency-store.v1"))
                .andExpect(jsonPath("$.orderIdempotency.createOrderEndpoint").value("/api/v1/orders"))
                .andExpect(jsonPath("$.orderIdempotency.requiredHeader").value("Idempotency-Key"))
                .andExpect(jsonPath("$.orderIdempotency.requestFingerprintVersion")
                        .value("order-create-request-sha256.v1"))
                .andExpect(jsonPath("$.orderIdempotency.sameKeyDifferentRequestErrorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.orderIdempotency.activeStore").value("jpa-order-idempotency-store"))
                .andExpect(jsonPath("$.orderIdempotency.activeStoreImplementation").value("JpaIdempotencyStore"))
                .andExpect(jsonPath("$.orderIdempotency.activeStoreMode").value("JPA_DATABASE"))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[*].name",
                        hasItem("mini-kv-ttl-token-adapter")))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[1].enabled").value(false))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[1].connected").value(false))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[1].mode")
                        .value("DISABLED_CANDIDATE_ONLY"))
                .andExpect(jsonPath("$.orderIdempotency.miniKvConnected").value(false))
                .andExpect(jsonPath("$.orderIdempotency.externalTokenStoreConnected").value(false))
                .andExpect(jsonPath("$.orderIdempotency.changesPaymentOrInventoryTransaction").value(false))
                .andExpect(jsonPath("$.releaseVerification.manifestVersion")
                        .value("java-release-verification-manifest.v1"))
                .andExpect(jsonPath("$.releaseVerification.manifestEndpoint")
                        .value("/contracts/release-verification-manifest.sample.json"))
                .andExpect(jsonPath("$.releaseVerification.requiredChecks",
                        hasItem("focused-maven-tests")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.staticContractEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.releaseVerification.nodeMayExecuteBuild").value(false))
                .andExpect(jsonPath("$.releaseVerification.nodeMayTriggerWrites").value(false))
                .andExpect(jsonPath("$.releaseVerification.changesBusinessSemantics").value(false))
                .andExpect(jsonPath("$.deploymentRollback.evidenceVersion")
                        .value("java-deployment-rollback-evidence.v1"))
                .andExpect(jsonPath("$.deploymentRollback.evidenceEndpoint")
                        .value("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(jsonPath("$.deploymentRollback.rollbackSubjects",
                        hasItem("database-migrations")))
                .andExpect(jsonPath("$.deploymentRollback.requiresOperatorConfirmation",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.deploymentRollback.requiresOperatorConfirmation",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.deploymentRollback.requiresOperatorConfirmation",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.deploymentRollback.requiresOperatorConfirmation",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.deploymentRollback.packageRollbackSupported").value(true))
                .andExpect(jsonPath("$.deploymentRollback.configRollbackSupported").value(true))
                .andExpect(jsonPath("$.deploymentRollback.databaseMigrationRollbackAutomatic").value(false))
                .andExpect(jsonPath("$.deploymentRollback.contractsRollbackByArtifactVersion").value(true))
                .andExpect(jsonPath("$.deploymentRollback.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.deploymentRollback.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.deploymentRollback.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.releaseBundle.manifestVersion")
                        .value("java-release-bundle-manifest.v1"))
                .andExpect(jsonPath("$.releaseBundle.manifestEndpoint")
                        .value("/contracts/release-bundle-manifest.sample.json"))
                .andExpect(jsonPath("$.releaseBundle.bundleMode").value("READ_ONLY_RELEASE_BUNDLE"))
                .andExpect(jsonPath("$.releaseBundle.artifact")
                        .value("target/advanced-order-platform-0.1.0-SNAPSHOT.jar"))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseBundle.requiredEvidence",
                        hasItem("http-smoke")))
                .andExpect(jsonPath("$.releaseBundle.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.releaseBundle.nodeMayExecuteBuild").value(false))
                .andExpect(jsonPath("$.releaseBundle.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.releaseBundle.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.releaseBundle.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.fixtureVersion")
                        .value("java-release-handoff-checklist-fixture.v1"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.fixtureEndpoint")
                        .value("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.fixtureMode")
                        .value("READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.migrationDirectionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.selectedMigrationDirection")
                        .value("no-database-change"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.secretSourceConfirmation")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.requiredChecklistFields",
                        hasItem("release-operator")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.requiredChecklistFields",
                        hasItem("secret-source-confirmation")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.requiredChecklistFields",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.requiredChecklistFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.checklistArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.checklistArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.checklistArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.checklistArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.noSecretValueBoundaries",
                        hasItem("secret-values-must-not-be-read")))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.releaseHandoffChecklistFixture.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.fixtureVersion")
                        .value("java-release-audit-retention-fixture.v1"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.fixtureEndpoint")
                        .value("/contracts/release-audit-retention.fixture.json"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.fixtureMode")
                        .value("READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.retentionId")
                        .value("release-retention-record-placeholder"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.retentionDays").value(180))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.evidenceEndpoints",
                        hasItem("/api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.evidenceEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.evidenceEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.auditExportFields",
                        hasItem("audit-export-location-placeholder")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.auditExportFields",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.auditExportFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.retainedArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.retainedArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.retainedArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.noSecretValueBoundaries",
                        hasItem("secret-values-must-not-be-read")))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.auditExportReadOnly").value(true))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.fixtureVersion")
                        .value("java-release-operator-signoff-fixture.v1"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.fixtureEndpoint")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.fixtureMode")
                        .value("READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.releaseWindow")
                        .value("release-window-placeholder"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.operatorSignoffPlaceholder")
                        .value("operator-signoff-placeholder"))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.requiredSignoffFields",
                        hasItem("operator-signoff-placeholder")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.requiredSignoffFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.signoffArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.signoffArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.noSecretValueBoundaries",
                        hasItem("secret-values-must-not-be-read")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureVersion")
                        .value("java-rollback-approver-evidence-fixture.v1"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureMode")
                        .value("READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.migrationDirectionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.selectedMigrationDirection")
                        .value("no-database-change"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.rollbackSqlArtifactReference")
                        .value("rollback-sql-artifact-reference-placeholder"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.productionDatabaseBoundary")
                        .value("production-database-connection-outside-this-fixture"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.requiredEvidenceFields",
                        hasItem("rollback-sql-artifact-reference")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.evidenceArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.evidenceArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.noSecretValueBoundaries",
                        hasItem("secret-values-must-not-be-read")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffVersion")
                        .value("java-rollback-approval-handoff.v1"))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffEndpoint")
                        .value("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.approvalMode")
                        .value("OPERATOR_CONFIRMATION_REQUIRED"))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("release-handoff-checklist-fixture")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiredConfirmationFields",
                        hasItem("release-bundle-manifest")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.fixtureVersion")
                        .value("java-rollback-approval-record-fixture.v1"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.fixtureEndpoint")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.fixtureMode")
                        .value("READ_ONLY_APPROVAL_RECORD_FIXTURE"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.reviewer")
                        .value("rollback-reviewer-placeholder"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.approvalTimestampPlaceholder")
                        .value("approval-timestamp-placeholder"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.rollbackTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.migrationDirectionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.selectedMigrationDirection")
                        .value("no-database-change"))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.requiredRecordFields",
                        hasItem("no-secret-value-boundary")))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.recordArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.recordArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.noSecretValueBoundaries",
                        hasItem("secret-values-must-not-be-read")))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.rollbackApprovalRecordFixture.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.gateVersion")
                        .value("java-rollback-sql-review-gate.v1"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.gateEndpoint")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.gateMode")
                        .value("READ_ONLY_SQL_REVIEW_GATE"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.reviewOwner")
                        .value("database-release-owner"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.requiredReviewFields",
                        hasItem("migration-direction")))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.migrationDirectionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.operatorApprovalPlaceholder")
                        .value("operator-approval-required-before-any-sql-execution"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.productionSecretSourceContract.contractVersion")
                        .value("java-production-secret-source-contract.v1"))
                .andExpect(jsonPath("$.productionSecretSourceContract.contractEndpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.productionSecretSourceContract.contractMode")
                        .value("READ_ONLY_SECRET_SOURCE_CONTRACT"))
                .andExpect(jsonPath("$.productionSecretSourceContract.sourceTypes",
                        hasItem("external-secret-manager")))
                .andExpect(jsonPath("$.productionSecretSourceContract.selectedSourceType")
                        .value("external-secret-manager"))
                .andExpect(jsonPath("$.productionSecretSourceContract.secretManagerOwner")
                        .value("platform-security-owner"))
                .andExpect(jsonPath("$.productionSecretSourceContract.rotationOwner")
                        .value("security-operations-owner"))
                .andExpect(jsonPath("$.productionSecretSourceContract.reviewCadence")
                        .value("quarterly-or-before-production-cutover"))
                .andExpect(jsonPath("$.productionSecretSourceContract.requiredConfirmationFields",
                        hasItem("secret-value-access-boundary")))
                .andExpect(jsonPath("$.productionSecretSourceContract.secretValueBoundaries",
                        hasItem("secret-values-must-not-be-read")))
                .andExpect(jsonPath("$.productionSecretSourceContract.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.productionSecretSourceContract.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.productionSecretSourceContract.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.productionSecretSourceContract.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.productionSecretSourceContract.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.contractVersion")
                        .value("java-production-deployment-runbook-contract.v1"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.contractEndpoint")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.contractMode")
                        .value("READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.deploymentWindowOwner")
                        .value("release-window-owner"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.rollbackApprover")
                        .value("rollback-approval-owner"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.databaseMigrationDirectionOptions",
                        hasItem("no-database-change")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.selectedDatabaseMigrationDirection")
                        .value("no-database-change"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.secretSourceConfirmation")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.requiredConfirmationFields",
                        hasItem("deployment-window-owner")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.requiredConfirmationFields",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.requiredConfirmationFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.runbookArtifacts",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.runbookArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.runbookArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.runbookArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.blockers", hasItem("READ_ONLY_EVIDENCE_ENDPOINT")))
                .andExpect(jsonPath("$.blockers", hasItem("OUTBOX_PUBLISHER_DISABLED")))
                .andExpect(jsonPath("$.blockers", hasItem("RABBITMQ_OUTBOX_DISABLED")))
                .andExpect(jsonPath("$.warnings", hasItem("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/overview")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/evidence")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/contracts/ops-read-only-evidence.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/ops-evidence-field-guide.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
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
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.allowedProbeEndpoints",
                        hasItem("GET /contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.readOnlyWindow.forbiddenOperations",
                        hasItem("Any non-GET Node upstream action")))
                .andExpect(jsonPath("$.readOnlyWindow.requiredNodeEnvironment",
                        hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.orderIdempotency.boundaryVersion")
                        .value("java-order-idempotency-boundary.v1"))
                .andExpect(jsonPath("$.orderIdempotency.storeAbstractionVersion")
                        .value("java-idempotency-store.v1"))
                .andExpect(jsonPath("$.orderIdempotency.sameKeyDifferentRequestErrorCode")
                        .value("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST"))
                .andExpect(jsonPath("$.orderIdempotency.activeStore").value("jpa-order-idempotency-store"))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[*].name",
                        hasItem("mini-kv-ttl-token-adapter")))
                .andExpect(jsonPath("$.orderIdempotency.storeCandidates[1].enabled").value(false))
                .andExpect(jsonPath("$.orderIdempotency.miniKvConnected").value(false))
                .andExpect(jsonPath("$.releaseVerification.manifestVersion")
                        .value("java-release-verification-manifest.v1"))
                .andExpect(jsonPath("$.releaseVerification.requiredChecks",
                        hasItem("http-smoke")))
                .andExpect(jsonPath("$.releaseVerification.nodeMayExecuteBuild").value(false))
                .andExpect(jsonPath("$.deploymentRollback.evidenceVersion")
                        .value("java-deployment-rollback-evidence.v1"))
                .andExpect(jsonPath("$.deploymentRollback.requiresOperatorConfirmation",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.deploymentRollback.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.deploymentRollback.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.deploymentRollback.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.releaseBundle.manifestVersion")
                        .value("java-release-bundle-manifest.v1"))
                .andExpect(jsonPath("$.releaseBundle.contractEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseBundle.nodeMayExecuteBuild").value(false))
                .andExpect(jsonPath("$.releaseBundle.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.fixtureVersion")
                        .value("java-release-audit-retention-fixture.v1"))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.auditExportReadOnly").value(true))
                .andExpect(jsonPath("$.releaseAuditRetentionFixture.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.requiredSignoffFields",
                        hasItem("rollback-approver-evidence-fixture")))
                .andExpect(jsonPath("$.releaseOperatorSignoffFixture.signoffArtifacts",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureVersion")
                        .value("java-rollback-approver-evidence-fixture.v1"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.fixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.rollbackApproverEvidenceFixture.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.handoffVersion")
                        .value("java-rollback-approval-handoff.v1"))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackApprovalHandoff.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.gateVersion")
                        .value("java-rollback-sql-review-gate.v1"))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.rollbackSqlReviewGate.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.productionSecretSourceContract.contractVersion")
                        .value("java-production-secret-source-contract.v1"))
                .andExpect(jsonPath("$.productionSecretSourceContract.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.contractVersion")
                        .value("java-production-deployment-runbook-contract.v1"))
                .andExpect(jsonPath("$.productionDeploymentRunbookContract.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.blockers", hasItem("OUTBOX_PUBLISHER_DISABLED")))
                .andExpect(jsonPath("$.warnings", hasItem("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK")))
                .andExpect(jsonPath("$.evidenceEndpoints", hasItem("/api/v1/ops/evidence")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/ops-evidence-field-guide.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-boundary.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/order-idempotency-store-abstraction.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")))
                .andExpect(jsonPath("$.productionPassBoundary.readyForProductionPassEvidence").value(false))
                .andExpect(jsonPath("$.productionPassBoundary.allowedProbeEndpoints",
                        hasItem("GET /api/v1/ops/evidence")))
                .andExpect(jsonPath("$.productionPassBoundary.forbiddenOperations",
                        hasItem("POST /api/v1/failed-events/{id}/replay")));
    }

    @Test
    void releaseApprovalRehearsalReturnsReadOnlyLiveAggregation() throws Exception {
        FailedEventMessage pendingApproval = FailedEventMessage.record(
                "release-approval-rehearsal-pending",
                "event-release-approval-rehearsal-1",
                "OrderNotificationFailed",
                "ORDER",
                "6601",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v66 pending approval",
                "{\"orderId\":6601}"
        );
        pendingApproval.requestReplayApproval("needs release rehearsal review", "ops-user", Instant.now());
        failedEventMessageRepository.save(pendingApproval);
        FailedEventMessage approvedReplay = FailedEventMessage.record(
                "release-approval-rehearsal-approved",
                "event-release-approval-rehearsal-2",
                "OrderNotificationFailed",
                "ORDER",
                "6602",
                "order-platform.outbox.events",
                "order-platform.outbox.events.dlq",
                "v66 approved replay",
                "{\"orderId\":6602}"
        );
        approvedReplay.requestReplayApproval("safe to rehearse", "ops-user", Instant.now());
        approvedReplay.approveReplay("ops-reviewer", "approved for rehearsal", Instant.now());
        failedEventMessageRepository.save(approvedReplay);

        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sampledAt").exists())
                .andExpect(jsonPath("$.rehearsalVersion").value("java-release-approval-rehearsal.v1"))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.rehearsalMode").value("READ_ONLY_RELEASE_APPROVAL_REHEARSAL"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.releaseApprovalInputs.releaseOperatorSignoffFixtureEndpoint")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.rollbackApproverEvidenceFixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.rollbackApprovalRecordFixtureEndpoint")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.releaseBundleManifestEndpoint")
                        .value("/contracts/release-bundle-manifest.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.releaseVerificationManifestEndpoint")
                        .value("/contracts/release-verification-manifest.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.deploymentRollbackEvidenceEndpoint")
                        .value("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.productionDeploymentRunbookContractEndpoint")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.productionSecretSourceContractEndpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.rollbackSqlReviewGateEndpoint")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.releaseApprovalInputs.requiredEvidenceEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.releaseApprovalInputs.requiredEvidenceEndpoints",
                        hasItem("/contracts/rollback-approver-evidence.fixture.json")))
                .andExpect(jsonPath("$.releaseApprovalInputs.requiredEvidenceEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.liveSignals.pendingReplayApprovals").value(1))
                .andExpect(jsonPath("$.liveSignals.approvedReplayApprovals").value(1))
                .andExpect(jsonPath("$.liveSignals.replayBacklog").value(2))
                .andExpect(jsonPath("$.liveSignals.pendingOutboxEvents").value(greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.liveSignals.realReplayAllowedByEvidence").value(false))
                .andExpect(jsonPath("$.liveSignals.approvalExecutionDryRun").value(true))
                .andExpect(jsonPath("$.liveSignals.evidenceExecutionAllowed").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayWriteApprovalLedger").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.executionBoundaries.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.executionBoundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.executionBoundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.executionBoundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("READ_ONLY_RELEASE_APPROVAL_REHEARSAL")))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("APPROVAL_DECISION_CREATION_DISABLED")))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("ROLLBACK_SQL_EXECUTION_DISABLED")))
                .andExpect(jsonPath("$.rehearsalBlockers", hasItem("REPLAY_APPROVAL_PENDING")))
                .andExpect(jsonPath("$.requiredNodeEnvironment", hasItem("UPSTREAM_PROBES_ENABLED=true")))
                .andExpect(jsonPath("$.requiredNodeEnvironment", hasItem("UPSTREAM_ACTIONS_ENABLED=false")))
                .andExpect(jsonPath("$.nextEvidenceActions",
                        hasItem("GET /api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.nextEvidenceActions",
                        hasItem("GET /contracts/rollback-approver-evidence.fixture.json")));
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
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseVerification")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("deploymentRollback")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseBundle")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseHandoffChecklistFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseAuditRetentionFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("releaseOperatorSignoffFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackApproverEvidenceFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackApprovalHandoff")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackApprovalRecordFixture")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("rollbackSqlReviewGate")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("productionSecretSourceContract")))
                .andExpect(jsonPath("$.fieldGroups[*].name", hasItem("productionDeploymentRunbookContract")))
                .andExpect(jsonPath("$.fieldGroups[1].fields[*].path",
                        hasItem("healthProbe.staticSampleOnly")))
                .andExpect(jsonPath("$.fieldGroups[2].fields[*].path",
                        hasItem("readOnlyWindow.readyForReadOnlyLiveProbe")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.sameKeyDifferentRequestErrorCode")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.storeAbstractionVersion")))
                .andExpect(jsonPath("$.fieldGroups[3].fields[*].path",
                        hasItem("orderIdempotency.storeCandidates")))
                .andExpect(jsonPath("$.fieldGroups[4].fields[*].path",
                        hasItem("failedEventReplay.realReplayAllowedByEvidence")))
                .andExpect(jsonPath("$.fieldGroups[5].fields[*].path",
                        hasItem("releaseVerification.manifestVersion")))
                .andExpect(jsonPath("$.fieldGroups[6].fields[*].path",
                        hasItem("deploymentRollback.evidenceVersion")))
                .andExpect(jsonPath("$.fieldGroups[6].fields[*].path",
                        hasItem("deploymentRollback.databaseMigrationRollbackAutomatic")))
                .andExpect(jsonPath("$.fieldGroups[7].fields[*].path",
                        hasItem("releaseBundle.manifestVersion")))
                .andExpect(jsonPath("$.fieldGroups[7].fields[*].path",
                        hasItem("releaseBundle.nodeMayTriggerRollback")))
                .andExpect(jsonPath("$.fieldGroups[8].fields[*].path",
                        hasItem("releaseHandoffChecklistFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[8].fields[*].path",
                        hasItem("releaseHandoffChecklistFixture.deploymentExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[9].fields[*].path",
                        hasItem("releaseAuditRetentionFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[9].fields[*].path",
                        hasItem("releaseAuditRetentionFixture.auditExportReadOnly")))
                .andExpect(jsonPath("$.fieldGroups[10].fields[*].path",
                        hasItem("rollbackApprovalHandoff.handoffVersion")))
                .andExpect(jsonPath("$.fieldGroups[10].fields[*].path",
                        hasItem("rollbackApprovalHandoff.rollbackSqlExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[11].fields[*].path",
                        hasItem("rollbackApprovalRecordFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[11].fields[*].path",
                        hasItem("rollbackApprovalRecordFixture.rollbackExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[12].fields[*].path",
                        hasItem("rollbackSqlReviewGate.gateVersion")))
                .andExpect(jsonPath("$.fieldGroups[12].fields[*].path",
                        hasItem("rollbackSqlReviewGate.sqlExecutionAllowed")))
                .andExpect(jsonPath("$.fieldGroups[13].fields[*].path",
                        hasItem("productionSecretSourceContract.contractVersion")))
                .andExpect(jsonPath("$.fieldGroups[13].fields[*].path",
                        hasItem("productionSecretSourceContract.nodeMayReadSecretValues")))
                .andExpect(jsonPath("$.fieldGroups[14].fields[*].path",
                        hasItem("productionDeploymentRunbookContract.contractVersion")))
                .andExpect(jsonPath("$.fieldGroups[14].fields[*].path",
                        hasItem("productionDeploymentRunbookContract.nodeMayTriggerDeployment")))
                .andExpect(jsonPath("$.fieldGroups[15].fields[*].path",
                        hasItem("releaseOperatorSignoffFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[15].fields[*].path",
                        hasItem("releaseOperatorSignoffFixture.nodeMayCreateApprovalDecision")))
                .andExpect(jsonPath("$.fieldGroups[16].fields[*].path",
                        hasItem("rollbackApproverEvidenceFixture.fixtureVersion")))
                .andExpect(jsonPath("$.fieldGroups[16].fields[*].path",
                        hasItem("rollbackApproverEvidenceFixture.rollbackSqlExecutionAllowed")))
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

    @Test
    void staticOrderIdempotencyStoreAbstractionSampleExplainsDisabledMiniKvCandidate() throws Exception {
        mockMvc.perform(get("/contracts/order-idempotency-store-abstraction.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.abstractionVersion").value("java-idempotency-store.v1"))
                .andExpect(jsonPath("$.scenario").value("ORDER_IDEMPOTENCY_STORE_ABSTRACTION_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.activeStore.name").value("jpa-order-idempotency-store"))
                .andExpect(jsonPath("$.activeStore.implementation").value("JpaIdempotencyStore"))
                .andExpect(jsonPath("$.activeStore.mode").value("JPA_DATABASE"))
                .andExpect(jsonPath("$.activeStore.orderAuthoritative").value(true))
                .andExpect(jsonPath("$.activeStore.columns",
                        hasItem("orders.idempotency_key")))
                .andExpect(jsonPath("$.disabledCandidates[0].name").value("mini-kv-ttl-token-adapter"))
                .andExpect(jsonPath("$.disabledCandidates[0].enabled").value(false))
                .andExpect(jsonPath("$.disabledCandidates[0].connected").value(false))
                .andExpect(jsonPath("$.disabledCandidates[0].mode").value("DISABLED_CANDIDATE_ONLY"))
                .andExpect(jsonPath("$.boundaries.orderAuthoritativeStoreRemainsJavaDatabase").value(true))
                .andExpect(jsonPath("$.boundaries.changesPaymentOrInventoryTransaction").value(false))
                .andExpect(jsonPath("$.boundaries.nodeMayTriggerWrites").value(false))
                .andExpect(jsonPath("$.futureAdapterRules",
                        hasItem("Adapter must be introduced behind IdempotencyStore without changing create-order semantics")));
    }

    @Test
    void staticReleaseVerificationManifestExplainsJavaReleaseGate() throws Exception {
        mockMvc.perform(get("/contracts/release-verification-manifest.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manifestVersion").value("java-release-verification-manifest.v1"))
                .andExpect(jsonPath("$.scenario").value("JAVA_RELEASE_VERIFICATION_MANIFEST_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.releaseSubject.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.releaseSubject.buildTool").value("Maven"))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("focused-maven-tests")))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("non-docker-regression-tests")))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("maven-package")))
                .andExpect(jsonPath("$.verificationChecks[*].name",
                        hasItem("http-smoke")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.staticContracts[*].endpoint",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.releaseGate.intendedConsumer")
                        .value("Node cross-project release verification intake gate"))
                .andExpect(jsonPath("$.releaseGate.nodeMayExecuteMaven").value(false))
                .andExpect(jsonPath("$.releaseGate.nodeMayTriggerJavaWrites").value(false))
                .andExpect(jsonPath("$.releaseGate.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderCreateSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.archiveExpectation.runtimeArchiveRoot").value("c/<version>"));
    }

    @Test
    void staticDeploymentRollbackEvidenceSampleExplainsRollbackBoundaries() throws Exception {
        mockMvc.perform(get("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.evidenceVersion").value("java-deployment-rollback-evidence.v1"))
                .andExpect(jsonPath("$.scenario").value("DEPLOYMENT_ROLLBACK_EVIDENCE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.rollbackMode").value("READ_ONLY_BOUNDARY_SAMPLE"))
                .andExpect(jsonPath("$.rollbackSubjects", hasItem("java-package")))
                .andExpect(jsonPath("$.rollbackSubjects", hasItem("database-migrations")))
                .andExpect(jsonPath("$.packageRollback.supported").value(true))
                .andExpect(jsonPath("$.packageRollback.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.configurationRollback.supported").value(true))
                .andExpect(jsonPath("$.configurationRollback.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.configurationRollback.nodeMayModifyRuntimeConfig").value(false))
                .andExpect(jsonPath("$.databaseMigrationRollback.automatic").value(false))
                .andExpect(jsonPath("$.databaseMigrationRollback.requiresOperatorConfirmation").value(true))
                .andExpect(jsonPath("$.databaseMigrationRollback.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.staticContractRollback.byArtifactVersion").value(true))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.staticContractRollback.contractEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("release-handoff-checklist-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("rollback-approval-handoff")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("rollback-sql-review-gate")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("production-secret-source-contract")))
                .andExpect(jsonPath("$.requiresOperatorConfirmation",
                        hasItem("production-deployment-runbook-contract")))
                .andExpect(jsonPath("$.boundaries.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.boundaries.nodeMayExecuteMaven").value(false))
                .andExpect(jsonPath("$.boundaries.nodeMayTriggerJavaWrites").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing database rollback SQL from this sample")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering rollback from Node")));
    }

    @Test
    void staticReleaseBundleManifestExplainsReadOnlyBundleGate() throws Exception {
        mockMvc.perform(get("/contracts/release-bundle-manifest.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manifestVersion").value("java-release-bundle-manifest.v1"))
                .andExpect(jsonPath("$.scenario").value("JAVA_RELEASE_BUNDLE_MANIFEST_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.releaseSubject.project").value("advanced-order-platform"))
                .andExpect(jsonPath("$.releaseSubject.artifact")
                        .value("target/advanced-order-platform-0.1.0-SNAPSHOT.jar"))
                .andExpect(jsonPath("$.bundleMode").value("READ_ONLY_RELEASE_BUNDLE"))
                .andExpect(jsonPath("$.bundleInputs.releaseVerificationManifest")
                        .value("/contracts/release-verification-manifest.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.deploymentRollbackEvidence")
                        .value("/contracts/deployment-rollback-evidence.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.releaseHandoffChecklistFixture")
                        .value("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.releaseAuditRetentionFixture")
                        .value("/contracts/release-audit-retention.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.releaseOperatorSignoffFixture")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.rollbackApprovalHandoff")
                        .value("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.rollbackApprovalRecordFixture")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.bundleInputs.rollbackSqlReviewGate")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.productionSecretSourceContract")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.productionDeploymentRunbookContract")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.bundleInputs.runtimeArchiveRoot").value("c/<version>"))
                .andExpect(jsonPath("$.artifactEvidence.packageDockerRequired").value(false))
                .andExpect(jsonPath("$.verificationEvidence[*].name", hasItem("focused-maven-tests")))
                .andExpect(jsonPath("$.verificationEvidence[*].name", hasItem("http-smoke")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-verification-manifest.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.contractEndpoints",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteMaven").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerJavaWrites").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Maven from Node")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

    @Test
    void staticReleaseHandoffChecklistFixtureExplainsNoExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-release-handoff-checklist-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("RELEASE_HANDOFF_CHECKLIST_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/release-handoff-checklist.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE"))
                .andExpect(jsonPath("$.releaseChecklist.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.releaseChecklist.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.releaseChecklist.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.releaseChecklist.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.databaseMigration.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.secretSourceConfirmation.endpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.secretSourceConfirmation.secretValueRecorded").value(false))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name", hasItem("release-operator")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name", hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name", hasItem("artifact-target")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("secret-source-confirmation")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("deployment-runbook-contract")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiredChecklistFields[*].name",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.checklistArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this checklist")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderReleaseHandoffReview").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java deployment from Node")));
    }

    @Test
    void staticReleaseAuditRetentionFixtureExplainsReadOnlyRetentionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/release-audit-retention.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-release-audit-retention-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("RELEASE_AUDIT_RETENTION_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/release-audit-retention.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE"))
                .andExpect(jsonPath("$.retentionRecord.retentionId")
                        .value("release-retention-record-placeholder"))
                .andExpect(jsonPath("$.retentionRecord.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.retentionRecord.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.retentionRecord.retentionDays").value(180))
                .andExpect(jsonPath("$.retentionRecord.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/api/v1/failed-events/replay-evidence-index")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.evidenceEndpoints",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.auditExportFields[*].name", hasItem("retention-id")))
                .andExpect(jsonPath("$.auditExportFields[*].name",
                        hasItem("audit-export-location-placeholder")))
                .andExpect(jsonPath("$.auditExportFields[*].name",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.auditExportFields[*].name",
                        hasItem("no-secret-value-boundary")))
                .andExpect(jsonPath("$.retainedArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.retainedArtifacts",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.retainedArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this retention record")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderRetentionGate").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayWriteAuditExport").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.auditExportReadOnly").value(true))
                .andExpect(jsonPath("$.boundaries.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Writing audit export files from Node through this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this fixture")));
    }

    @Test
    void staticReleaseOperatorSignoffFixtureExplainsNoApprovalDecisionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-release-operator-signoff-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("RELEASE_OPERATOR_SIGNOFF_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/release-operator-signoff.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE"))
                .andExpect(jsonPath("$.signoffRecord.releaseOperator")
                        .value("release-operator-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.releaseWindow")
                        .value("release-window-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.artifactTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.operatorSignoffPlaceholder")
                        .value("operator-signoff-placeholder"))
                .andExpect(jsonPath("$.signoffRecord.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("release-operator")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("release-window")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name", hasItem("artifact-target")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name",
                        hasItem("operator-signoff-placeholder")))
                .andExpect(jsonPath("$.requiredSignoffFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.signoffArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.signoffArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this signoff")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderApprovalPrerequisiteGate").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayWriteApprovalLedger").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.deploymentExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.approvalDecisionCreated").value(false))
                .andExpect(jsonPath("$.boundaries.approvalLedgerWriteAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Creating a real approval decision from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Writing approval ledger entries from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this fixture")));
    }

    @Test
    void staticRollbackApproverEvidenceFixtureExplainsNoRollbackExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-rollback-approver-evidence-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_APPROVER_EVIDENCE_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/rollback-approver-evidence.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode")
                        .value("READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE"))
                .andExpect(jsonPath("$.approverEvidence.rollbackApprover")
                        .value("rollback-approver-placeholder"))
                .andExpect(jsonPath("$.approverEvidence.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.approverEvidence.evidenceStatus")
                        .value("PENDING_OPERATOR_CONFIRMATION"))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlArtifactReference")
                        .value("rollback-sql-artifact-reference-placeholder"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlTextEmbedded").value(false))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.databaseMigration.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.databaseMigration.productionDatabaseBoundary")
                        .value("production-database-connection-outside-this-fixture"))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name", hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("rollback-sql-artifact-reference")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("production-database-access-boundary")))
                .andExpect(jsonPath("$.requiredEvidenceFields[*].name",
                        hasItem("rollback-sql-review-gate")))
                .andExpect(jsonPath("$.evidenceArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.evidenceArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this evidence")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderDecisionRehearsalInput").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayCreateApprovalDecision").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayWriteApprovalLedger").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.approvalDecisionCreated").value(false))
                .andExpect(jsonPath("$.boundaries.approvalLedgerWriteAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Creating a real approval decision from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing database rollback SQL from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Connecting production database from this fixture")));
    }

    @Test
    void staticRollbackApprovalHandoffSampleExplainsHumanConfirmationBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.handoffVersion").value("java-rollback-approval-handoff.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_APPROVAL_HANDOFF_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.handoffEndpoint")
                        .value("/contracts/rollback-approval-handoff.sample.json"))
                .andExpect(jsonPath("$.approvalMode").value("OPERATOR_CONFIRMATION_REQUIRED"))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("artifact-version-target")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("runtime-config-profile")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("configuration-secret-source")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("production-secret-source-contract")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("production-deployment-runbook-contract")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("database-migration-direction")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-handoff-checklist-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("rollback-approval-record-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("rollback-sql-review-gate")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-bundle-manifest")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("deployment-rollback-evidence")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/release-bundle-manifest.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/deployment-rollback-evidence.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/rollback-approval-record.fixture.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/production-secret-source-contract.sample.json")))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/production-deployment-runbook-contract.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderChecklist").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayModifyRuntimeConfig").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing database rollback SQL from this handoff")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

    @Test
    void staticRollbackApprovalRecordFixtureExplainsNoExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fixtureVersion")
                        .value("java-rollback-approval-record-fixture.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_APPROVAL_RECORD_FIXTURE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.fixtureEndpoint")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.fixtureMode").value("READ_ONLY_APPROVAL_RECORD_FIXTURE"))
                .andExpect(jsonPath("$.approvalRecord.reviewer").value("rollback-reviewer-placeholder"))
                .andExpect(jsonPath("$.approvalRecord.approvalTimestampPlaceholder")
                        .value("approval-timestamp-placeholder"))
                .andExpect(jsonPath("$.approvalRecord.rollbackTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.approvalRecord.operatorMustReplacePlaceholders").value(true))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.databaseMigration.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.databaseMigration.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.requiredRecordFields[*].name", hasItem("reviewer")))
                .andExpect(jsonPath("$.requiredRecordFields[*].name",
                        hasItem("approval-timestamp-placeholder")))
                .andExpect(jsonPath("$.requiredRecordFields[*].name", hasItem("rollback-target")))
                .andExpect(jsonPath("$.requiredRecordFields[*].name",
                        hasItem("no-secret-value-boundary")))
                .andExpect(jsonPath("$.recordArtifacts",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.recordArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.noSecretValueBoundaries",
                        hasItem("Secret values must not be read by Java or Node when rendering this record")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderReleaseWindowPacket").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java rollback from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this fixture")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

    @Test
    void staticProductionSecretSourceContractSampleExplainsSecretValueBoundary() throws Exception {
        mockMvc.perform(get("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractVersion").value("java-production-secret-source-contract.v1"))
                .andExpect(jsonPath("$.scenario").value("PRODUCTION_SECRET_SOURCE_CONTRACT_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.contractEndpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.contractMode").value("READ_ONLY_SECRET_SOURCE_CONTRACT"))
                .andExpect(jsonPath("$.secretSource.selectedSourceType").value("external-secret-manager"))
                .andExpect(jsonPath("$.secretSource.allowedSourceTypes",
                        hasItem("platform-managed-secret")))
                .andExpect(jsonPath("$.secretSource.secretManagerOwner").value("platform-security-owner"))
                .andExpect(jsonPath("$.rotationPolicy.rotationOwner").value("security-operations-owner"))
                .andExpect(jsonPath("$.rotationPolicy.reviewCadence")
                        .value("quarterly-or-before-production-cutover"))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("secret-value-access-boundary")))
                .andExpect(jsonPath("$.secretValueBoundaries",
                        hasItem("Secret values are never read by this contract")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderChecklist").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayReadSecretValues").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this contract")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Embedding secret values in static JSON samples")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java runtime configuration changes from Node")));
    }

    @Test
    void staticProductionDeploymentRunbookContractSampleExplainsNoExecutionBoundary() throws Exception {
        mockMvc.perform(get("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractVersion")
                        .value("java-production-deployment-runbook-contract.v1"))
                .andExpect(jsonPath("$.scenario").value("PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.contractEndpoint")
                        .value("/contracts/production-deployment-runbook-contract.sample.json"))
                .andExpect(jsonPath("$.contractMode").value("READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT"))
                .andExpect(jsonPath("$.deploymentWindow.owner").value("release-window-owner"))
                .andExpect(jsonPath("$.deploymentWindow.rollbackApprover").value("rollback-approval-owner"))
                .andExpect(jsonPath("$.databaseMigration.directionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.databaseMigration.selectedDirection").value("no-database-change"))
                .andExpect(jsonPath("$.secretSourceConfirmation.endpoint")
                        .value("/contracts/production-secret-source-contract.sample.json"))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("deployment-window-owner")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("rollback-approver")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-audit-retention-fixture")))
                .andExpect(jsonPath("$.requiredConfirmationFields[*].name",
                        hasItem("release-operator-signoff-fixture")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/release-handoff-checklist.fixture.json")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/release-audit-retention.fixture.json")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/release-operator-signoff.fixture.json")))
                .andExpect(jsonPath("$.runbookArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerDeployment").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.boundaries.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing Java deployment from this runbook contract")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing rollback SQL from this runbook contract")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Reading production secret values from this runbook contract")));
    }

    @Test
    void staticRollbackSqlReviewGateSampleExplainsSqlReviewBoundary() throws Exception {
        mockMvc.perform(get("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateVersion").value("java-rollback-sql-review-gate.v1"))
                .andExpect(jsonPath("$.scenario").value("ROLLBACK_SQL_REVIEW_GATE_SAMPLE"))
                .andExpect(jsonPath("$.readOnly").value(true))
                .andExpect(jsonPath("$.executionAllowed").value(false))
                .andExpect(jsonPath("$.sourceEvidenceEndpoint").value("/api/v1/ops/evidence"))
                .andExpect(jsonPath("$.gateEndpoint")
                        .value("/contracts/rollback-sql-review-gate.sample.json"))
                .andExpect(jsonPath("$.gateMode").value("READ_ONLY_SQL_REVIEW_GATE"))
                .andExpect(jsonPath("$.reviewOwner").value("database-release-owner"))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("rollback-sql-review-owner")))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("migration-direction")))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("operator-approval-placeholder")))
                .andExpect(jsonPath("$.requiredReviewFields[*].name",
                        hasItem("rollback-sql-artifact-reference")))
                .andExpect(jsonPath("$.migrationDirectionOptions",
                        hasItem("rollback-script-reviewed")))
                .andExpect(jsonPath("$.operatorApprovalPlaceholder")
                        .value("operator-approval-required-before-any-sql-execution"))
                .andExpect(jsonPath("$.handoffArtifacts",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayConsume").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayRenderPreflight").value(true))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
                .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteRollbackSql").value(false))
                .andExpect(jsonPath("$.nodeConsumption.requiresUpstreamActionsEnabled").value(false))
                .andExpect(jsonPath("$.boundaries.sqlExecutionAllowed").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionDatabase").value(false))
                .andExpect(jsonPath("$.boundaries.requiresProductionSecrets").value(false))
                .andExpect(jsonPath("$.boundaries.changesOrderTransactionSemantics").value(false))
                .andExpect(jsonPath("$.boundaries.connectsMiniKv").value(false))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Executing rollback SQL from this sample")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Connecting to a production database from this sample")))
                .andExpect(jsonPath("$.forbiddenOperations",
                        hasItem("Triggering Java rollback from Node")));
    }

    private void deleteFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }
}
