package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
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

    private void deleteFailedEventData() {
        failedEventReplayApprovalHistoryRepository.deleteAll();
        failedEventManagementHistoryRepository.deleteAll();
        failedEventReplayAttemptRepository.deleteAll();
        failedEventMessageRepository.deleteAll();
    }
}
