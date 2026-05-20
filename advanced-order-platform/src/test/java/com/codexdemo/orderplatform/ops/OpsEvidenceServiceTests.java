package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalSandboxEndpointCredentialResolverEnvHandle;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverDisabledPrecheckEchoRecords
        .RehearsalSandboxEndpointCredentialResolverFailureClass;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverFakeShellArchiveEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverTestOnlyShellEchoRecords
        .RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker;
import com.codexdemo.orderplatform.ops.ReleaseApprovalSandboxEndpointCredentialResolverTestOnlyShellEchoRecords
        .RehearsalSandboxEndpointCredentialResolverTestOnlyShellFailureMapping;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class OpsEvidenceServiceTests {

    private final FailedEventSummaryService failedEventSummaryService =
            org.mockito.Mockito.mock(FailedEventSummaryService.class);

    private final OutboxRepository outboxRepository = org.mockito.Mockito.mock(OutboxRepository.class);

    private final IdempotencyStore idempotencyStore = org.mockito.Mockito.mock(IdempotencyStore.class);

    @Test
    void buildsReadOnlyEvidenceForControlPlane() {
        Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
        Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));

        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");

        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        OpsEvidenceResponse evidence = service.evidence();

        assertThat(evidence.evidenceVersion()).isEqualTo("java-ops-evidence.v1");
        assertThat(evidence.readOnly()).isTrue();
        assertThat(evidence.executionAllowed()).isFalse();
        assertThat(evidence.service().name()).isEqualTo("advanced-order-platform");
        assertThat(evidence.service().version()).isEqualTo("0.1.0-test");
        assertThat(evidence.service().profiles()).containsExactly("local", "ops");
        assertThat(evidence.healthProbe().endpoint()).isEqualTo("/actuator/health");
        assertThat(evidence.healthProbe().method()).isEqualTo("GET");
        assertThat(evidence.healthProbe().expectedStatus()).isEqualTo("UP");
        assertThat(evidence.healthProbe().evidenceEndpoint()).isEqualTo("/api/v1/ops/evidence");
        assertThat(evidence.healthProbe().additionalProbeEndpoints())
                .containsExactly(
                        "/api/v1/ops/overview",
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json"
                );
        assertThat(evidence.healthProbe().liveProbeRequiredForPass()).isTrue();
        assertThat(evidence.healthProbe().staticSampleOnly()).isFalse();
        assertThat(evidence.readOnlyWindow().windowVersion()).isEqualTo("java-read-only-window.v1");
        assertThat(evidence.readOnlyWindow().operatorStartRequired()).isTrue();
        assertThat(evidence.readOnlyWindow().nodeAutoStartAllowed()).isFalse();
        assertThat(evidence.readOnlyWindow().upstreamProbesRequired()).isTrue();
        assertThat(evidence.readOnlyWindow().upstreamActionsAllowed()).isFalse();
        assertThat(evidence.readOnlyWindow().readyForReadOnlyLiveProbe()).isTrue();
        assertThat(evidence.readOnlyWindow().readyForProductionOperations()).isFalse();
        assertThat(evidence.readOnlyWindow().allowedProbeEndpoints())
                .containsExactly(
                        "GET /actuator/health",
                        "GET /api/v1/ops/overview",
                        "GET /api/v1/ops/evidence",
                        "GET /api/v1/ops/release-approval-rehearsal",
                        "GET /contracts/ops-read-only-evidence.sample.json",
                        "GET /contracts/order-idempotency-boundary.sample.json",
                        "GET /contracts/order-idempotency-store-abstraction.sample.json",
                        "GET /contracts/release-verification-manifest.sample.json",
                        "GET /contracts/deployment-rollback-evidence.sample.json",
                        "GET /contracts/release-bundle-manifest.sample.json",
                        "GET /contracts/release-handoff-checklist.fixture.json",
                        "GET /contracts/release-audit-retention.fixture.json",
                        "GET /contracts/release-operator-signoff.fixture.json",
                        "GET /contracts/rollback-approver-evidence.fixture.json",
                        "GET /contracts/rollback-approval-handoff.sample.json",
                        "GET /contracts/rollback-approval-record.fixture.json",
                        "GET /contracts/rollback-sql-review-gate.sample.json",
                        "GET /contracts/production-secret-source-contract.sample.json",
                        "GET /contracts/production-deployment-runbook-contract.sample.json"
                );
        assertThat(evidence.readOnlyWindow().forbiddenOperations())
                .contains(
                        "POST /api/v1/failed-events/{id}/replay",
                        "Any non-GET Node upstream action"
                );
        assertThat(evidence.readOnlyWindow().requiredNodeEnvironment())
                .containsExactly("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false");
        assertThat(evidence.readOnlyWindow().replayPostBoundary())
                .contains("must not call POST /api/v1/failed-events/{id}/replay");
        assertThat(evidence.orderIdempotency().boundaryVersion()).isEqualTo("java-order-idempotency-boundary.v1");
        assertThat(evidence.orderIdempotency().storeAbstractionVersion()).isEqualTo("java-idempotency-store.v1");
        assertThat(evidence.orderIdempotency().createOrderEndpoint()).isEqualTo("/api/v1/orders");
        assertThat(evidence.orderIdempotency().requiredHeader()).isEqualTo("Idempotency-Key");
        assertThat(evidence.orderIdempotency().requestFingerprintVersion())
                .isEqualTo("order-create-request-sha256.v1");
        assertThat(evidence.orderIdempotency().sameKeyDifferentRequestErrorCode())
                .isEqualTo("IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST");
        assertThat(evidence.orderIdempotency().activeStore()).isEqualTo("jpa-order-idempotency-store");
        assertThat(evidence.orderIdempotency().activeStoreImplementation()).isEqualTo("JpaIdempotencyStore");
        assertThat(evidence.orderIdempotency().activeStoreMode()).isEqualTo("JPA_DATABASE");
        assertThat(evidence.orderIdempotency().authoritativeStore())
                .isEqualTo("orders table via orders.idempotency_key and orders.idempotency_request_fingerprint");
        assertThat(evidence.orderIdempotency().storeCandidates())
                .extracting(OpsEvidenceResponse.IdempotencyStoreCandidate::name)
                .containsExactly("jpa-order-idempotency-store", "mini-kv-ttl-token-adapter");
        assertThat(evidence.orderIdempotency().storeCandidates().get(1).enabled()).isFalse();
        assertThat(evidence.orderIdempotency().storeCandidates().get(1).connected()).isFalse();
        assertThat(evidence.orderIdempotency().storeCandidates().get(1).mode())
                .isEqualTo("DISABLED_CANDIDATE_ONLY");
        assertThat(evidence.orderIdempotency().miniKvConnected()).isFalse();
        assertThat(evidence.orderIdempotency().externalTokenStoreConnected()).isFalse();
        assertThat(evidence.orderIdempotency().changesPaymentOrInventoryTransaction()).isFalse();
        assertThat(evidence.releaseVerification().manifestVersion())
                .isEqualTo("java-release-verification-manifest.v1");
        assertThat(evidence.releaseVerification().manifestEndpoint())
                .isEqualTo("/contracts/release-verification-manifest.sample.json");
        assertThat(evidence.releaseVerification().verificationMode())
                .isEqualTo("LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS");
        assertThat(evidence.releaseVerification().requiredChecks())
                .containsExactly(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                );
        assertThat(evidence.releaseVerification().staticContractEndpoints())
                .contains(
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.releaseVerification().nodeMayExecuteBuild()).isFalse();
        assertThat(evidence.releaseVerification().nodeMayTriggerWrites()).isFalse();
        assertThat(evidence.releaseVerification().changesBusinessSemantics()).isFalse();
        assertThat(evidence.releaseVerification().requiresProductionSecrets()).isFalse();
        assertThat(evidence.deploymentRollback().evidenceVersion())
                .isEqualTo("java-deployment-rollback-evidence.v1");
        assertThat(evidence.deploymentRollback().evidenceEndpoint())
                .isEqualTo("/contracts/deployment-rollback-evidence.sample.json");
        assertThat(evidence.deploymentRollback().rollbackMode()).isEqualTo("READ_ONLY_BOUNDARY_SAMPLE");
        assertThat(evidence.deploymentRollback().rollbackSubjects())
                .containsExactly(
                        "java-package",
                        "runtime-configuration",
                        "database-migrations",
                        "static-contracts"
                );
        assertThat(evidence.deploymentRollback().requiresOperatorConfirmation())
                .containsExactly(
                        "artifact-version-target",
                        "deployment-window-owner",
                        "rollback-approver",
                        "configuration-secret-source",
                        "production-secret-source-contract",
                        "production-deployment-runbook-contract",
                        "database-migration-direction",
                        "release-handoff-checklist-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "rollback-approval-handoff",
                        "rollback-approval-record-fixture",
                        "rollback-sql-review-gate"
                );
        assertThat(evidence.deploymentRollback().packageRollbackSupported()).isTrue();
        assertThat(evidence.deploymentRollback().configRollbackSupported()).isTrue();
        assertThat(evidence.deploymentRollback().databaseMigrationRollbackAutomatic()).isFalse();
        assertThat(evidence.deploymentRollback().contractsRollbackByArtifactVersion()).isTrue();
        assertThat(evidence.deploymentRollback().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.deploymentRollback().requiresProductionDatabase()).isFalse();
        assertThat(evidence.deploymentRollback().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseBundle().manifestVersion())
                .isEqualTo("java-release-bundle-manifest.v1");
        assertThat(evidence.releaseBundle().manifestEndpoint())
                .isEqualTo("/contracts/release-bundle-manifest.sample.json");
        assertThat(evidence.releaseBundle().bundleMode()).isEqualTo("READ_ONLY_RELEASE_BUNDLE");
        assertThat(evidence.releaseBundle().artifact())
                .isEqualTo("target/advanced-order-platform-0.1.0-SNAPSHOT.jar");
        assertThat(evidence.releaseBundle().contractEndpoints())
                .containsExactly(
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/ops-evidence-field-guide.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json"
                );
        assertThat(evidence.releaseBundle().requiredEvidence())
                .containsExactly(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                );
        assertThat(evidence.releaseBundle().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseBundle().nodeMayExecuteBuild()).isFalse();
        assertThat(evidence.releaseBundle().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseBundle().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseBundle().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().fixtureVersion())
                .isEqualTo("java-release-handoff-checklist-fixture.v1");
        assertThat(evidence.releaseHandoffChecklistFixture().fixtureEndpoint())
                .isEqualTo("/contracts/release-handoff-checklist.fixture.json");
        assertThat(evidence.releaseHandoffChecklistFixture().fixtureMode())
                .isEqualTo("READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE");
        assertThat(evidence.releaseHandoffChecklistFixture().releaseOperator())
                .isEqualTo("release-operator-placeholder");
        assertThat(evidence.releaseHandoffChecklistFixture().rollbackApprover())
                .isEqualTo("rollback-approver-placeholder");
        assertThat(evidence.releaseHandoffChecklistFixture().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.releaseHandoffChecklistFixture().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.releaseHandoffChecklistFixture().secretSourceConfirmation())
                .isEqualTo("/contracts/production-secret-source-contract.sample.json");
        assertThat(evidence.releaseHandoffChecklistFixture().requiredChecklistFields())
                .containsExactly(
                        "release-operator",
                        "rollback-approver",
                        "artifact-target",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "deployment-runbook-contract",
                        "rollback-approval-record-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().checklistArtifacts())
                .containsExactly(
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().noSecretValueBoundaries())
                .containsExactly(
                        "checklist-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-handoff-checklist",
                        "node-may-render-release-handoff-review-only"
                );
        assertThat(evidence.releaseHandoffChecklistFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseHandoffChecklistFixture().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().deploymentExecutionAllowed()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.releaseHandoffChecklistFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().fixtureVersion())
                .isEqualTo("java-release-audit-retention-fixture.v1");
        assertThat(evidence.releaseAuditRetentionFixture().fixtureEndpoint())
                .isEqualTo("/contracts/release-audit-retention.fixture.json");
        assertThat(evidence.releaseAuditRetentionFixture().fixtureMode())
                .isEqualTo("READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE");
        assertThat(evidence.releaseAuditRetentionFixture().retentionId())
                .isEqualTo("release-retention-record-placeholder");
        assertThat(evidence.releaseAuditRetentionFixture().releaseOperator())
                .isEqualTo("release-operator-placeholder");
        assertThat(evidence.releaseAuditRetentionFixture().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.releaseAuditRetentionFixture().retentionDays()).isEqualTo(180);
        assertThat(evidence.releaseAuditRetentionFixture().evidenceEndpoints())
                .containsExactly(
                        "/api/v1/ops/evidence",
                        "/api/v1/ops/release-approval-rehearsal",
                        "/api/v1/failed-events/replay-evidence-index",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json"
                );
        assertThat(evidence.releaseAuditRetentionFixture().auditExportFields())
                .containsExactly(
                        "retention-id",
                        "release-operator",
                        "artifact-target",
                        "retention-days",
                        "evidence-endpoints",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "audit-export-location-placeholder",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.releaseAuditRetentionFixture().retainedArtifacts())
                .containsExactly(
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json"
                );
        assertThat(evidence.releaseAuditRetentionFixture().noSecretValueBoundaries())
                .containsExactly(
                        "retention-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-retention-record",
                        "node-may-render-retention-gate-only"
                );
        assertThat(evidence.releaseAuditRetentionFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseAuditRetentionFixture().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().auditExportReadOnly()).isTrue();
        assertThat(evidence.releaseAuditRetentionFixture().deploymentExecutionAllowed()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.releaseAuditRetentionFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().fixtureVersion())
                .isEqualTo("java-release-operator-signoff-fixture.v1");
        assertThat(evidence.releaseOperatorSignoffFixture().fixtureEndpoint())
                .isEqualTo("/contracts/release-operator-signoff.fixture.json");
        assertThat(evidence.releaseOperatorSignoffFixture().fixtureMode())
                .isEqualTo("READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE");
        assertThat(evidence.releaseOperatorSignoffFixture().releaseOperator())
                .isEqualTo("release-operator-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().rollbackApprover())
                .isEqualTo("rollback-approver-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().releaseWindow())
                .isEqualTo("release-window-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().operatorSignoffPlaceholder())
                .isEqualTo("operator-signoff-placeholder");
        assertThat(evidence.releaseOperatorSignoffFixture().requiredSignoffFields())
                .containsExactly(
                        "release-operator",
                        "rollback-approver",
                        "release-window",
                        "artifact-target",
                        "operator-signoff-placeholder",
                        "release-audit-retention-fixture",
                        "rollback-approver-evidence-fixture",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.releaseOperatorSignoffFixture().signoffArtifacts())
                .containsExactly(
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json"
                );
        assertThat(evidence.releaseOperatorSignoffFixture().noSecretValueBoundaries())
                .containsExactly(
                        "signoff-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-signoff",
                        "node-may-render-approval-prerequisite-gate-only"
                );
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayCreateApprovalDecision()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().deploymentExecutionAllowed()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.releaseOperatorSignoffFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().fixtureVersion())
                .isEqualTo("java-rollback-approver-evidence-fixture.v1");
        assertThat(evidence.rollbackApproverEvidenceFixture().fixtureEndpoint())
                .isEqualTo("/contracts/rollback-approver-evidence.fixture.json");
        assertThat(evidence.rollbackApproverEvidenceFixture().fixtureMode())
                .isEqualTo("READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE");
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackApprover())
                .isEqualTo("rollback-approver-placeholder");
        assertThat(evidence.rollbackApproverEvidenceFixture().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackSqlArtifactReference())
                .isEqualTo("rollback-sql-artifact-reference-placeholder");
        assertThat(evidence.rollbackApproverEvidenceFixture().productionDatabaseBoundary())
                .isEqualTo("production-database-connection-outside-this-fixture");
        assertThat(evidence.rollbackApproverEvidenceFixture().requiredEvidenceFields())
                .containsExactly(
                        "rollback-approver",
                        "database-migration-direction",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().evidenceArtifacts())
                .containsExactly(
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().noSecretValueBoundaries())
                .containsExactly(
                        "rollback-approver-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approver-evidence",
                        "node-may-render-decision-rehearsal-input-only"
                );
        assertThat(evidence.rollbackApproverEvidenceFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackApproverEvidenceFixture().nodeMayCreateApprovalDecision()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.rollbackApproverEvidenceFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().handoffVersion())
                .isEqualTo("java-rollback-approval-handoff.v1");
        assertThat(evidence.rollbackApprovalHandoff().handoffEndpoint())
                .isEqualTo("/contracts/rollback-approval-handoff.sample.json");
        assertThat(evidence.rollbackApprovalHandoff().approvalMode())
                .isEqualTo("OPERATOR_CONFIRMATION_REQUIRED");
        assertThat(evidence.rollbackApprovalHandoff().requiredConfirmationFields())
                .containsExactly(
                        "artifact-version-target",
                        "deployment-window-owner",
                        "rollback-approver",
                        "runtime-config-profile",
                        "configuration-secret-source",
                        "production-secret-source-contract",
                        "production-deployment-runbook-contract",
                        "database-migration-direction",
                        "release-handoff-checklist-fixture",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "rollback-approval-record-fixture",
                        "rollback-sql-review-gate",
                        "release-bundle-manifest",
                        "deployment-rollback-evidence"
                );
        assertThat(evidence.rollbackApprovalHandoff().handoffArtifacts())
                .containsExactly(
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/release-verification-manifest.sample.json"
                );
        assertThat(evidence.rollbackApprovalHandoff().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackApprovalHandoff().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().requiresProductionSecrets()).isFalse();
        assertThat(evidence.rollbackApprovalHandoff().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().fixtureVersion())
                .isEqualTo("java-rollback-approval-record-fixture.v1");
        assertThat(evidence.rollbackApprovalRecordFixture().fixtureEndpoint())
                .isEqualTo("/contracts/rollback-approval-record.fixture.json");
        assertThat(evidence.rollbackApprovalRecordFixture().fixtureMode())
                .isEqualTo("READ_ONLY_APPROVAL_RECORD_FIXTURE");
        assertThat(evidence.rollbackApprovalRecordFixture().reviewer())
                .isEqualTo("rollback-reviewer-placeholder");
        assertThat(evidence.rollbackApprovalRecordFixture().approvalTimestampPlaceholder())
                .isEqualTo("approval-timestamp-placeholder");
        assertThat(evidence.rollbackApprovalRecordFixture().rollbackTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(evidence.rollbackApprovalRecordFixture().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.rollbackApprovalRecordFixture().requiredRecordFields())
                .containsExactly(
                        "reviewer",
                        "approval-timestamp-placeholder",
                        "rollback-target",
                        "database-migration-direction",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().recordArtifacts())
                .containsExactly(
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().noSecretValueBoundaries())
                .containsExactly(
                        "record-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approval-record",
                        "node-may-render-release-window-packet-only"
                );
        assertThat(evidence.rollbackApprovalRecordFixture().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackApprovalRecordFixture().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().rollbackExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().rollbackSqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().requiresProductionSecrets()).isFalse();
        assertThat(evidence.rollbackApprovalRecordFixture().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().gateVersion())
                .isEqualTo("java-rollback-sql-review-gate.v1");
        assertThat(evidence.rollbackSqlReviewGate().gateEndpoint())
                .isEqualTo("/contracts/rollback-sql-review-gate.sample.json");
        assertThat(evidence.rollbackSqlReviewGate().gateMode())
                .isEqualTo("READ_ONLY_SQL_REVIEW_GATE");
        assertThat(evidence.rollbackSqlReviewGate().reviewOwner()).isEqualTo("database-release-owner");
        assertThat(evidence.rollbackSqlReviewGate().requiredReviewFields())
                .containsExactly(
                        "rollback-sql-review-owner",
                        "migration-direction",
                        "operator-approval-placeholder",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary"
                );
        assertThat(evidence.rollbackSqlReviewGate().migrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.rollbackSqlReviewGate().operatorApprovalPlaceholder())
                .isEqualTo("operator-approval-required-before-any-sql-execution");
        assertThat(evidence.rollbackSqlReviewGate().nodeMayConsume()).isTrue();
        assertThat(evidence.rollbackSqlReviewGate().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().sqlExecutionAllowed()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().requiresProductionDatabase()).isFalse();
        assertThat(evidence.rollbackSqlReviewGate().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.productionSecretSourceContract().contractVersion())
                .isEqualTo("java-production-secret-source-contract.v1");
        assertThat(evidence.productionSecretSourceContract().contractEndpoint())
                .isEqualTo("/contracts/production-secret-source-contract.sample.json");
        assertThat(evidence.productionSecretSourceContract().contractMode())
                .isEqualTo("READ_ONLY_SECRET_SOURCE_CONTRACT");
        assertThat(evidence.productionSecretSourceContract().sourceTypes())
                .containsExactly(
                        "external-secret-manager",
                        "environment-injected-secret",
                        "platform-managed-secret"
                );
        assertThat(evidence.productionSecretSourceContract().selectedSourceType())
                .isEqualTo("external-secret-manager");
        assertThat(evidence.productionSecretSourceContract().secretManagerOwner())
                .isEqualTo("platform-security-owner");
        assertThat(evidence.productionSecretSourceContract().rotationOwner())
                .isEqualTo("security-operations-owner");
        assertThat(evidence.productionSecretSourceContract().reviewCadence())
                .isEqualTo("quarterly-or-before-production-cutover");
        assertThat(evidence.productionSecretSourceContract().requiredConfirmationFields())
                .containsExactly(
                        "secret-manager-or-source-type",
                        "secret-manager-owner",
                        "rotation-owner",
                        "review-cadence",
                        "secret-value-access-boundary"
                );
        assertThat(evidence.productionSecretSourceContract().secretValueBoundaries())
                .containsExactly(
                        "contract-records-source-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-static-contracts",
                        "node-may-render-checklist-only"
                );
        assertThat(evidence.productionSecretSourceContract().nodeMayConsume()).isTrue();
        assertThat(evidence.productionSecretSourceContract().nodeMayReadSecretValues()).isFalse();
        assertThat(evidence.productionSecretSourceContract().requiresProductionSecrets()).isFalse();
        assertThat(evidence.productionSecretSourceContract().requiresProductionDatabase()).isFalse();
        assertThat(evidence.productionSecretSourceContract().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().contractVersion())
                .isEqualTo("java-production-deployment-runbook-contract.v1");
        assertThat(evidence.productionDeploymentRunbookContract().contractEndpoint())
                .isEqualTo("/contracts/production-deployment-runbook-contract.sample.json");
        assertThat(evidence.productionDeploymentRunbookContract().contractMode())
                .isEqualTo("READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT");
        assertThat(evidence.productionDeploymentRunbookContract().deploymentWindowOwner())
                .isEqualTo("release-window-owner");
        assertThat(evidence.productionDeploymentRunbookContract().rollbackApprover())
                .isEqualTo("rollback-approval-owner");
        assertThat(evidence.productionDeploymentRunbookContract().databaseMigrationDirectionOptions())
                .containsExactly(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                );
        assertThat(evidence.productionDeploymentRunbookContract().selectedDatabaseMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(evidence.productionDeploymentRunbookContract().secretSourceConfirmation())
                .isEqualTo("/contracts/production-secret-source-contract.sample.json");
        assertThat(evidence.productionDeploymentRunbookContract().requiredConfirmationFields())
                .containsExactly(
                        "deployment-window-owner",
                        "rollback-approver",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "rollback-sql-review-gate",
                        "operator-approval-placeholder",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture"
                );
        assertThat(evidence.productionDeploymentRunbookContract().runbookArtifacts())
                .containsExactly(
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json"
                );
        assertThat(evidence.productionDeploymentRunbookContract().nodeMayConsume()).isTrue();
        assertThat(evidence.productionDeploymentRunbookContract().nodeMayTriggerDeployment()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().nodeMayTriggerRollback()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().sqlExecutionAllowed()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().requiresProductionDatabase()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().requiresProductionSecrets()).isFalse();
        assertThat(evidence.productionDeploymentRunbookContract().changesOrderTransactionSemantics()).isFalse();
        assertThat(evidence.failedEventReplay().totalFailedEvents()).isEqualTo(4);
        assertThat(evidence.failedEventReplay().pendingReplayApprovals()).isEqualTo(2);
        assertThat(evidence.failedEventReplay().approvedReplayApprovals()).isEqualTo(1);
        assertThat(evidence.failedEventReplay().rejectedReplayApprovals()).isEqualTo(1);
        assertThat(evidence.failedEventReplay().replayBacklog()).isEqualTo(3);
        assertThat(evidence.failedEventReplay().latestFailedAt()).isEqualTo(latestFailedAt);
        assertThat(evidence.failedEventReplay().latestApprovalAt()).isEqualTo(latestApprovalAt);
        assertThat(evidence.failedEventReplay().realReplayAllowedByEvidence()).isFalse();
        assertThat(evidence.outbox().pendingEvents()).isEqualTo(6);
        assertThat(evidence.outbox().publisherEnabled()).isFalse();
        assertThat(evidence.outbox().rabbitMqEnabled()).isFalse();
        assertThat(evidence.outbox().blockers())
                .containsExactly("OUTBOX_PUBLISHER_DISABLED", "RABBITMQ_OUTBOX_DISABLED");
        assertThat(evidence.approvalExecution().requiredApprovalStatus()).isEqualTo("APPROVED");
        assertThat(evidence.approvalExecution().approvalRequired()).isTrue();
        assertThat(evidence.approvalExecution().dryRun()).isTrue();
        assertThat(evidence.approvalExecution().executionBlockers())
                .containsExactly(
                        "READ_ONLY_EVIDENCE_ENDPOINT",
                        "REPLAY_APPROVAL_PENDING",
                        "REPLAY_APPROVAL_REJECTED",
                        "REPLAY_BACKLOG_PRESENT"
                );
        assertThat(evidence.blockers())
                .contains(
                        "READ_ONLY_EVIDENCE_ENDPOINT",
                        "OUTBOX_PUBLISHER_DISABLED",
                        "RABBITMQ_OUTBOX_DISABLED"
                );
        assertThat(evidence.warnings())
                .containsExactly("OUTBOX_PENDING_EVENTS", "APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
        assertThat(evidence.evidenceEndpoints())
                .contains(
                        "/api/v1/ops/evidence",
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/ops-read-only-evidence.sample.json",
                        "/contracts/ops-evidence-field-guide.sample.json",
                        "/contracts/order-idempotency-boundary.sample.json",
                        "/contracts/order-idempotency-store-abstraction.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-handoff-checklist.fixture.json",
                        "/contracts/release-audit-retention.fixture.json",
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/api/v1/failed-events/{id}/replay-execution-contract",
                        "/api/v1/failed-events/replay-evidence-index"
                );

        ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
        assertThat(rehearsal.rehearsalVersion()).isEqualTo("java-release-approval-rehearsal.v1");
        assertThat(rehearsal.sourceEvidenceEndpoint()).isEqualTo("/api/v1/ops/evidence");
        assertThat(rehearsal.rehearsalMode()).isEqualTo("READ_ONLY_RELEASE_APPROVAL_REHEARSAL");
        assertThat(rehearsal.readOnly()).isTrue();
        assertThat(rehearsal.executionAllowed()).isFalse();
        assertThat(rehearsal.requestContext().contextVersion())
                .isEqualTo("java-release-approval-rehearsal-context.v1");
        assertThat(rehearsal.requestContext().requestId()).isEqualTo("rehearsal-request-id-not-supplied");
        assertThat(rehearsal.requestContext().requestIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.requestContext().operatorIdentity()).isEqualTo("operator-identity-not-supplied");
        assertThat(rehearsal.requestContext().operatorIdentitySource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.requestContext().auditCorrelationId()).isEqualTo("audit-correlation-id-not-supplied");
        assertThat(rehearsal.requestContext().auditCorrelationSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.requestContext().operatorAuthenticatedByJava()).isFalse();
        assertThat(rehearsal.requestContext().persistedByJava()).isFalse();
        assertThat(rehearsal.requestContext().approvalLedgerWritten()).isFalse();
        assertThat(rehearsal.requestContext().requiresProductionIdentityProvider()).isFalse();
        assertThat(rehearsal.requestContext().acceptedReadOnlyHeaders())
                .containsExactly(
                        "X-Rehearsal-Request-Id",
                        "X-Operator-Identity",
                        "X-Audit-Correlation-Id"
                );
        assertThat(rehearsal.requestContext().contextWarnings())
                .containsExactly(
                        "REHEARSAL_REQUEST_ID_MISSING",
                        "OPERATOR_IDENTITY_MISSING",
                        "AUDIT_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.operatorWindowHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-operator-window-hint.v1");
        assertThat(rehearsal.operatorWindowHint().operatorId()).isEqualTo("orderops-operator-id-not-supplied");
        assertThat(rehearsal.operatorWindowHint().operatorIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().operatorRoles()).isEqualTo("orderops-roles-not-supplied");
        assertThat(rehearsal.operatorWindowHint().operatorRolesSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().operatorVerifiedClaim())
                .isEqualTo("orderops-operator-verified-not-supplied");
        assertThat(rehearsal.operatorWindowHint().operatorVerifiedClaimSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().approvalCorrelationId())
                .isEqualTo("orderops-approval-correlation-id-not-supplied");
        assertThat(rehearsal.operatorWindowHint().approvalCorrelationIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.operatorWindowHint().operatorIdentityEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().operatorRolesEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().operatorVerifiedClaimEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().approvalCorrelationEchoed()).isFalse();
        assertThat(rehearsal.operatorWindowHint().operatorWindowContextComplete()).isFalse();
        assertThat(rehearsal.operatorWindowHint().productionIdpVerifiedByJava()).isFalse();
        assertThat(rehearsal.operatorWindowHint().persistedApprovalRecordByJava()).isFalse();
        assertThat(rehearsal.operatorWindowHint().nodeMayTreatAsProductionIdentity()).isFalse();
        assertThat(rehearsal.operatorWindowHint().acceptedOperatorWindowHeaders())
                .containsExactly(
                        "x-orderops-operator-id",
                        "x-orderops-roles",
                        "x-orderops-operator-verified",
                        "x-orderops-approval-correlation-id"
                );
        assertThat(rehearsal.operatorWindowHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_OPERATOR_ID_MISSING",
                        "ORDEROPS_OPERATOR_ROLES_MISSING",
                        "ORDEROPS_OPERATOR_VERIFIED_CLAIM_MISSING",
                        "ORDEROPS_APPROVAL_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.operatorWindowHint().nodeVerificationActions())
                .contains(
                        "Compare operatorWindowHint.operatorId with Node v198 operatorIdentity.operatorId",
                        "Keep nodeMayTreatAsProductionIdentity=false"
                );
        assertThat(rehearsal.ciEvidenceHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-ci-evidence-hint.v1");
        assertThat(rehearsal.ciEvidenceHint().manifestProfileVersion())
                .isEqualTo("ci-manifest-profile-version-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().manifestProfileVersionSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().manifestDigest()).isEqualTo("ci-manifest-digest-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().manifestDigestSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().manifestEndpoint()).isEqualTo("ci-manifest-endpoint-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().manifestEndpointSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().artifactRecordCount())
                .isEqualTo("ci-artifact-record-count-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().artifactRecordCountSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().approvalCorrelationId())
                .isEqualTo("ci-approval-correlation-id-not-supplied");
        assertThat(rehearsal.ciEvidenceHint().approvalCorrelationIdSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.ciEvidenceHint().manifestProfileVersionEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().manifestDigestEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().manifestEndpointEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().artifactRecordCountEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().approvalCorrelationEchoed()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().ciEvidenceContextComplete()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().noLedgerWriteProof())
                .isEqualTo("NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS");
        assertThat(rehearsal.ciEvidenceHint().noLedgerWriteProved()).isTrue();
        assertThat(rehearsal.ciEvidenceHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().productionWindowAllowedByJava()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().nodeMayTreatAsCiArtifactPublication()).isFalse();
        assertThat(rehearsal.ciEvidenceHint().acceptedCiEvidenceHeaders())
                .containsExactly(
                        "x-orderops-ci-manifest-version",
                        "x-orderops-ci-manifest-digest",
                        "x-orderops-ci-manifest-endpoint",
                        "x-orderops-ci-artifact-record-count",
                        "x-orderops-ci-approval-correlation-id"
                );
        assertThat(rehearsal.ciEvidenceHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_CI_MANIFEST_VERSION_MISSING",
                        "ORDEROPS_CI_MANIFEST_DIGEST_MISSING",
                        "ORDEROPS_CI_MANIFEST_ENDPOINT_MISSING",
                        "ORDEROPS_CI_ARTIFACT_RECORD_COUNT_MISSING",
                        "ORDEROPS_CI_APPROVAL_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.ciEvidenceHint().nodeVerificationActions())
                .contains(
                        "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                        "Keep ciArtifactUploadedByJava=false and githubArtifactAccessedByJava=false"
                );
        assertThat(rehearsal.artifactRetentionHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-artifact-retention-hint.v1");
        assertThat(rehearsal.artifactRetentionHint().sourceRetentionFixtureVersion())
                .isEqualTo("java-release-audit-retention-fixture.v1");
        assertThat(rehearsal.artifactRetentionHint().sourceRetentionFixtureEndpoint())
                .isEqualTo("/contracts/release-audit-retention.fixture.json");
        assertThat(rehearsal.artifactRetentionHint().retentionId())
                .isEqualTo("release-retention-record-placeholder");
        assertThat(rehearsal.artifactRetentionHint().artifactTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(rehearsal.artifactRetentionHint().javaRetentionDays()).isEqualTo(180);
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractVersion())
                .isEqualTo("ci-upload-contract-version-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractVersionSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractDigest())
                .isEqualTo("ci-upload-contract-digest-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciUploadContractDigestSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactName())
                .isEqualTo("ci-artifact-name-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactNameSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactRoot())
                .isEqualTo("ci-artifact-root-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciArtifactRootSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciRetentionDays())
                .isEqualTo("ci-retention-days-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciRetentionDaysSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().ciUploadMode())
                .isEqualTo("ci-upload-mode-not-supplied");
        assertThat(rehearsal.artifactRetentionHint().ciUploadModeSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.artifactRetentionHint().uploadContractVersionEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().uploadContractDigestEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().artifactNameEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().artifactRootEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().retentionDaysEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().uploadModeEchoed()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().artifactRetentionContextComplete()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().retentionDaysWithinJavaRetention()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().javaRetentionFixtureReadOnly()).isTrue();
        assertThat(rehearsal.artifactRetentionHint().auditExportReadOnly()).isTrue();
        assertThat(rehearsal.artifactRetentionHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().productionWindowAllowedByJava()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().nodeMayTreatAsRetentionAuthorization()).isFalse();
        assertThat(rehearsal.artifactRetentionHint().acceptedArtifactRetentionHeaders())
                .containsExactly(
                        "x-orderops-ci-upload-contract-version",
                        "x-orderops-ci-upload-contract-digest",
                        "x-orderops-ci-artifact-name",
                        "x-orderops-ci-artifact-root",
                        "x-orderops-ci-retention-days",
                        "x-orderops-ci-upload-mode"
                );
        assertThat(rehearsal.artifactRetentionHint().releaseEvidenceEndpoints())
                .contains(
                        "/api/v1/ops/evidence",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/release-bundle-manifest.sample.json"
                );
        assertThat(rehearsal.artifactRetentionHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_CI_UPLOAD_CONTRACT_VERSION_MISSING",
                        "ORDEROPS_CI_UPLOAD_CONTRACT_DIGEST_MISSING",
                        "ORDEROPS_CI_ARTIFACT_NAME_MISSING",
                        "ORDEROPS_CI_ARTIFACT_ROOT_MISSING",
                        "ORDEROPS_CI_RETENTION_DAYS_MISSING",
                        "ORDEROPS_CI_UPLOAD_MODE_MISSING"
                );
        assertThat(rehearsal.artifactRetentionHint().nodeVerificationActions())
                .contains(
                        "Compare artifactRetentionHint.ciUploadContractDigest with Node v202 dryRunContract.contractDigest",
                        "Require artifactRetentionHint.retentionDaysWithinJavaRetention=true before Node v203 retention gate",
                        "Keep ciArtifactUploadedByJava=false and githubArtifactAccessedByJava=false"
                );
        assertThat(rehearsal.liveReadinessHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-live-readiness-hint.v1");
        assertThat(rehearsal.liveReadinessHint().serverTimestamp()).isEqualTo(rehearsal.sampledAt());
        assertThat(rehearsal.liveReadinessHint().serverTimestampSource()).isEqualTo("sampledAt");
        assertThat(rehearsal.liveReadinessHint().readOnlyEndpointVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v31");
        assertThat(rehearsal.liveReadinessHint().readOnlyEndpoint())
                .isEqualTo("/api/v1/ops/release-approval-rehearsal");
        assertThat(rehearsal.liveReadinessHint().healthEndpoint()).isEqualTo("/actuator/health");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightVersion())
                .isEqualTo("runtime-preflight-version-not-supplied");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightVersionSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightDigest())
                .isEqualTo("runtime-preflight-digest-not-supplied");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightDigestSource()).isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.liveReadinessHint().runtimeSmokeSessionId())
                .isEqualTo("runtime-smoke-session-id-not-supplied");
        assertThat(rehearsal.liveReadinessHint().runtimeReadTargetId())
                .isEqualTo("runtime-read-target-id-not-supplied");
        assertThat(rehearsal.liveReadinessHint().runtimeWindowMode())
                .isEqualTo("runtime-window-mode-not-supplied");
        assertThat(rehearsal.liveReadinessHint().sourcePreflightVersionEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().sourcePreflightDigestEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().runtimeSmokeSessionIdEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().runtimeReadTargetIdEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().runtimeWindowModeEchoed()).isFalse();
        assertThat(rehearsal.liveReadinessHint().liveReadinessContextComplete()).isFalse();
        assertThat(rehearsal.liveReadinessHint().readyForRuntimeSmokeRead()).isTrue();
        assertThat(rehearsal.liveReadinessHint().readOnlyEndpointReady()).isTrue();
        assertThat(rehearsal.liveReadinessHint().runtimeSmokeExecutedByJava()).isFalse();
        assertThat(rehearsal.liveReadinessHint().nodeMustRecordPidAndCleanup()).isTrue();
        assertThat(rehearsal.liveReadinessHint().javaStartedProcessForNode()).isFalse();
        assertThat(rehearsal.liveReadinessHint().processCleanupRecordedByJava()).isFalse();
        assertThat(rehearsal.liveReadinessHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(rehearsal.liveReadinessHint().acceptedLiveReadinessHeaders())
                .containsExactly(
                        "x-orderops-runtime-preflight-version",
                        "x-orderops-runtime-preflight-digest",
                        "x-orderops-runtime-smoke-session-id",
                        "x-orderops-runtime-read-target-id",
                        "x-orderops-runtime-window-mode"
                );
        assertThat(rehearsal.liveReadinessHint().allowedReadTargets())
                .containsExactly(
                        "GET /actuator/health",
                        "GET /api/v1/ops/release-approval-rehearsal"
                );
        assertThat(rehearsal.liveReadinessHint().forbiddenRuntimeOperations())
                .contains(
                        "POST /api/v1/orders",
                        "POST /api/v1/failed-events/{id}/replay",
                        "Java process start/stop is owned by Node v205 smoke orchestration"
                );
        assertThat(rehearsal.liveReadinessHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_RUNTIME_PREFLIGHT_VERSION_MISSING",
                        "ORDEROPS_RUNTIME_PREFLIGHT_DIGEST_MISSING",
                        "ORDEROPS_RUNTIME_SMOKE_SESSION_ID_MISSING",
                        "ORDEROPS_RUNTIME_READ_TARGET_ID_MISSING",
                        "ORDEROPS_RUNTIME_WINDOW_MODE_MISSING"
                );
        assertThat(rehearsal.liveReadinessHint().nodeVerificationActions())
                .contains(
                        "Compare liveReadinessHint.sourcePreflightDigest with Node v204 runtimeWindow.preflightDigest",
                        "Require liveReadinessHint.readOnlyEndpointReady=true before counting Java read target as ready",
                        "Keep runtimeSmokeExecutedByJava=false and javaStartedProcessForNode=false"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-audit-persistence-handoff-hint.v1");
        assertThat(rehearsal.auditPersistenceHandoffHint().sourceRetentionFixtureVersion())
                .isEqualTo("java-release-audit-retention-fixture.v1");
        assertThat(rehearsal.auditPersistenceHandoffHint().sourceRetentionFixtureEndpoint())
                .isEqualTo("/contracts/release-audit-retention.fixture.json");
        assertThat(rehearsal.auditPersistenceHandoffHint().javaRetentionDays()).isEqualTo(180);
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersion())
                .isEqualTo("managed-audit-candidate-version-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersionSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigest())
                .isEqualTo("managed-audit-candidate-digest-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigestSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditSinkMode())
                .isEqualTo("managed-audit-sink-mode-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRetentionDays())
                .isEqualTo("managed-audit-retention-days-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicy())
                .isEqualTo("managed-audit-rotation-policy-not-supplied");
        assertThat(rehearsal.auditPersistenceHandoffHint().candidateVersionEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().candidateDigestEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().sinkModeEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().retentionDaysEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().rotationPolicyEchoed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().auditPersistenceHandoffContextComplete()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRetentionWithinJavaRetention()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaAuditSourceReadOnly()).isTrue();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaLedgerWriteAllowed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaManagedAuditWriteAllowed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().javaExternalAuditSystemAccessed()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().productionAuditStoreRequired()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().nodeMayUseAsManagedAuditInput()).isTrue();
        assertThat(rehearsal.auditPersistenceHandoffHint().nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.auditPersistenceHandoffHint().acceptedAuditPersistenceHeaders())
                .containsExactly(
                        "x-orderops-managed-audit-candidate-version",
                        "x-orderops-managed-audit-candidate-digest",
                        "x-orderops-managed-audit-sink-mode",
                        "x-orderops-managed-audit-retention-days",
                        "x-orderops-managed-audit-rotation-policy"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().handoffFieldPaths())
                .contains(
                        "requestContext.requestId",
                        "operatorWindowHint.operatorId",
                        "verificationHint.warningDigest",
                        "executionBoundaries.nodeMayWriteApprovalLedger"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().readOnlySourceEndpoints())
                .containsExactly(
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/release-audit-retention.fixture.json",
                        "/api/v1/ops/evidence"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_CANDIDATE_VERSION_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_CANDIDATE_DIGEST_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_SINK_MODE_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_RETENTION_DAYS_MISSING",
                        "ORDEROPS_MANAGED_AUDIT_ROTATION_POLICY_MISSING"
                );
        assertThat(rehearsal.auditPersistenceHandoffHint().nodeVerificationActions())
                .contains(
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateDigest with Node v208 adapter digest",
                        "Persist only the listed handoffFieldPaths in Node managed audit dry-run storage",
                        "Keep javaManagedAuditWriteAllowed=false and nodeMayTreatAsProductionAuditRecord=false"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-record-handoff-hint.v1");
        assertThat(rehearsal.approvalRecordHandoffHint().sourceApprovalRecordFixtureVersion())
                .isEqualTo("java-rollback-approval-record-fixture.v1");
        assertThat(rehearsal.approvalRecordHandoffHint().sourceApprovalRecordFixtureEndpoint())
                .isEqualTo("/contracts/rollback-approval-record.fixture.json");
        assertThat(rehearsal.approvalRecordHandoffHint().reviewerPlaceholder())
                .isEqualTo("rollback-reviewer-placeholder");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalTimestampPlaceholder())
                .isEqualTo("approval-timestamp-placeholder");
        assertThat(rehearsal.approvalRecordHandoffHint().rollbackTarget())
                .isEqualTo("release-tag-or-artifact-version-placeholder");
        assertThat(rehearsal.approvalRecordHandoffHint().selectedMigrationDirection())
                .isEqualTo("no-database-change");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersion())
                .isEqualTo("approval-binding-contract-version-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersionSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigest())
                .isEqualTo("approval-binding-contract-digest-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigestSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestId())
                .isEqualTo("approval-request-id-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestIdSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionState())
                .isEqualTo("approval-decision-state-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionStateSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationId())
                .isEqualTo("approval-record-correlation-id-not-supplied");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationIdSource())
                .isEqualTo("NOT_SUPPLIED");
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersionEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigestEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestIdEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionStateEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationEchoed()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordHandoffContextComplete()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordFixtureReadOnly()).isTrue();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalRecordPersisted()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalRecordAuthenticated()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().productionApprovalStoreRequired()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().nodeMayUseAsAuditApprovalInput()).isTrue();
        assertThat(rehearsal.approvalRecordHandoffHint().nodeMayTreatAsProductionApprovalRecord()).isFalse();
        assertThat(rehearsal.approvalRecordHandoffHint().acceptedApprovalRecordHeaders())
                .containsExactly(
                        "x-orderops-approval-binding-contract-version",
                        "x-orderops-approval-binding-contract-digest",
                        "x-orderops-approval-request-id",
                        "x-orderops-approval-decision-state",
                        "x-orderops-approval-record-correlation-id"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().handoffFieldPaths())
                .contains(
                        "operatorWindowHint.operatorId",
                        "approvalRecordHandoffHint.approvalRequestId",
                        "verificationHint.warningDigest"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().sourceRecordArtifacts())
                .contains(
                        "/contracts/rollback-approval-handoff.sample.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-sql-review-gate.sample.json"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().echoWarnings())
                .containsExactly(
                        "ORDEROPS_APPROVAL_BINDING_CONTRACT_VERSION_MISSING",
                        "ORDEROPS_APPROVAL_BINDING_CONTRACT_DIGEST_MISSING",
                        "ORDEROPS_APPROVAL_REQUEST_ID_MISSING",
                        "ORDEROPS_APPROVAL_DECISION_STATE_MISSING",
                        "ORDEROPS_APPROVAL_RECORD_CORRELATION_ID_MISSING"
                );
        assertThat(rehearsal.approvalRecordHandoffHint().nodeVerificationActions())
                .contains(
                        "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
                        "Compare approvalRecordHandoffHint.approvalBindingContractDigest with Node v210 binding digest",
                        "Keep javaApprovalRecordPersisted=false and nodeMayTreatAsProductionApprovalRecord=false"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().markerVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-handoff-verification-marker.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().sourceApprovalRecordHandoffHintVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-record-handoff-hint.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().sourceApprovalRecordHandoffSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v9");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeProfileVersion())
                .isEqualTo("managed-audit-identity-approval-provenance-dry-run-packet.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodePacketState())
                .isEqualTo("dry-run-packet-verified");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeEndpoint())
                .isEqualTo("/api/v1/audit/managed-identity-approval-provenance-dry-run-packet");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeRequestId())
                .isEqualTo("managed-audit-v211-identity-approval-provenance-request");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodePacketVersion())
                .isEqualTo("managed-audit-dry-run-record.v2-candidate");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeBindingContractVersion())
                .isEqualTo("managed-audit-identity-approval-binding-contract.v1");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunDirectoryLabel())
                .isEqualTo(".tmp");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunDirectoryPrefix())
                .isEqualTo("managed-audit-v211-");
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunFileName())
                .isEqualTo("managed-audit-packet.jsonl");
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211MayConsume()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211HandoffAccepted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211NoWriteBoundaryAccepted()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketAppendCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketQueryCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketDigestCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketCleanupCovered()).isTrue();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211JavaWriteAttempted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211MiniKvWriteAttempted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211ExternalAuditSystemAccessed()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211ProductionAuditRecordAllowed()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().javaApprovalRecordPersisted()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().readyForNodeV213RestoreDrillPlan()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.approvalHandoffVerificationMarker().consumedHandoffFieldPaths())
                .containsExactly(
                        "requestContext.requestId",
                        "operatorWindowHint.operatorId",
                        "operatorWindowHint.operatorRoles",
                        "approvalRecordHandoffHint.approvalRequestId",
                        "approvalRecordHandoffHint.approvalDecisionState",
                        "approvalRecordHandoffHint.approvalRecordCorrelationId",
                        "approvalRecordHandoffHint.reviewerPlaceholder",
                        "approvalRecordHandoffHint.approvalTimestampPlaceholder",
                        "verificationHint.warningDigest"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211AcceptedChecks())
                .contains(
                        "javaV75HandoffAccepted",
                        "javaV75NoWriteBoundaryValid",
                        "appendCovered",
                        "cleanupCovered",
                        "noRealApprovalDecisionCreated"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV213Prerequisites())
                .contains(
                        "Java v76 marker readyForNodeV213RestoreDrillPlan must be true",
                        "mini-kv v85 retention provenance replay marker must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.approvalHandoffVerificationMarker().markerWarnings())
                .containsExactly("NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE");
        assertThat(rehearsal.approvalHandoffVerificationMarker().nodeVerificationActions())
                .contains(
                        "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 profileVersion",
                        "Require approvalHandoffVerificationMarker.nodeV211HandoffAccepted=true before Node v213 restore drill plan",
                        "Keep approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().receiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().sourceApprovalHandoffMarkerVersion())
                .isEqualTo("java-release-approval-rehearsal-approval-handoff-verification-marker.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().sourceApprovalHandoffSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v10");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationVersion())
                .isEqualTo("managed-audit-restore-drill-archive-verification.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationState())
                .isEqualTo("verified-restore-drill-archive");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-restore-drill-archive-verification");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nextNodeCandidateVersion())
                .isEqualTo("Node v215");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nextNodeCandidateProfile())
                .isEqualTo("managed-audit-dry-run-adapter-candidate.v1");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteLocalDryRunFiles()).isTrue();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConnectManagedAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayCreateApprovalDecision()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteApprovalLedger()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayPersistApprovalRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteSql()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerDeployment()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerRollback()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteRestore()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalRecordPersisted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaManagedAuditWriteExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRollbackSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaDeploymentTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRollbackTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRestoreExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForNodeV215DryRunAdapterCandidate())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().acceptedSourceReceipts())
                .contains(
                        "Node v214 managed audit restore drill archive verification",
                        "Java v76 approval handoff verification marker",
                        "mini-kv v86 managed audit adapter restore boundary receipt must be present before Node v215"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().adapterBoundaryClaims())
                .contains(
                        "Node v215 may only write Node local .tmp or controlled test files",
                        "Node v215 must not connect real managed audit storage",
                        "Node v215 must not create Java approval decision",
                        "Node v215 must not write Java approval ledger",
                        "Node v215 must not execute Java SQL deployment rollback or restore"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().forbiddenAdapterOperations())
                .contains(
                        "Connect real managed audit storage from Node v215",
                        "Create Java approval decision from Node v215",
                        "Write Java approval ledger from Node v215",
                        "Persist Java approval record from Node v215",
                        "Execute Java SQL from Node v215",
                        "Set UPSTREAM_ACTIONS_ENABLED=true for Node v215"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215Prerequisites())
                .contains(
                        "Node v214 managed audit restore drill archive verification must be verified",
                        "Java v77 managed audit adapter boundary receipt must be ready",
                        "mini-kv v86 managed audit adapter restore boundary receipt must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().receiptWarnings())
                .containsExactly("NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY");
        assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
                        "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
                        "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .sourceManagedAuditAdapterBoundaryReceiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .sourceManagedAuditAdapterBoundarySchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v11");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationVersion())
                .isEqualTo("managed-audit-dry-run-adapter-archive-verification.v1");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationState())
                .isEqualTo("verified-dry-run-adapter-archive");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-dry-run-adapter-archive-verification");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nextNodeGateVersion())
                .isEqualTo("Node v217");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nextNodeGateProfile())
                .isEqualTo("managed-audit-adapter-production-hardening-readiness-gate.v1");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .operatorIdentityPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourcePrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .ledgerHandoffPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .retentionOwnerPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .failureHandlingPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .rollbackReviewPrerequisiteDocumented()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .externalManagedAuditStorageConfigRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .productionIdentityProviderRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourceRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().ledgerHandoffRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().retentionOwnerRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().failureHandlingRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().rollbackReviewRequired()).isTrue();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaCreatesApprovalDecision())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaWritesApprovalLedger())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaPersistsApprovalRecord())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaWritesManagedAuditStore())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesSql()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaTriggersDeployment())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaTriggersRollback()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesRestore()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayConnectManagedAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayWriteApprovalLedger())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayExecuteSql()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayTriggerDeployment())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayTriggerRollback())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayExecuteRestore())
                .isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForNodeV217ProductionHardeningReadinessGate()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForProductionOperations()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().prerequisiteCategories())
                .contains(
                        "operator identity",
                        "approval decision source",
                        "ledger handoff",
                        "retention owner",
                        "failure handling",
                        "rollback review"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .prerequisiteEvidenceRequired())
                .contains(
                        "Production operator identity must be bound by a real IdP outside Java v78",
                        "Approval decision source must be a real approval workflow outside Java v78",
                        "Approval ledger handoff must define ownership and append semantics outside Java v78",
                        "Rollback review evidence must exist before production adapter work"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .forbiddenProductionAdapterOperations())
                .contains(
                        "Connect real managed audit storage from Java v78 or Node v217",
                        "Write approval ledger from Java v78 or Node v217",
                        "Execute Java SQL from Java v78 or Node v217",
                        "Open production audit window from this receipt"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217Prerequisites())
                .contains(
                        "Node v216 managed audit dry-run adapter archive verification must be verified",
                        "Java v78 managed audit production adapter prerequisite receipt must be ready",
                        "mini-kv v87 managed audit adapter non-authoritative storage receipt must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptWarnings())
                .containsExactly("NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
                        "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
                        "Keep managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt()
                .sourceProductionAdapterPrerequisiteReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt()
                .sourceProductionAdapterPrerequisiteSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v12");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().consumedByNodeQualityPassVersion())
                .isEqualTo("Node v218");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().consumedByNodeQualityPassProfile())
                .isEqualTo("audit-route-managed-audit-helper-quality-pass.v1");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nextNodePrecheckVersion())
                .isEqualTo("Node v219");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nextNodePrecheckProfile())
                .isEqualTo("managed-audit-adapter-implementation-precheck-packet.v1");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219MayConsume()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().digestResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().hintResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().renderResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().recordResponsibilityDocumented()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().firstSafeSplitApplied()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().broadServiceSplitDeferred()).isTrue();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().apiShapeChanged()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalDecisionCreated()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalLedgerWritten()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().approvalRecordPersisted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().managedAuditStoreWritten()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().sqlExecuted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().deploymentTriggered()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().rollbackTriggered()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().restoreExecuted()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForNodeV219ImplementationPrecheck())
                .isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().responsibilityBoundaries())
                .contains(
                        "receipt builders own Node-facing handoff and prerequisite response blocks",
                        "digest helpers own warningDigestInputs and proofClaims stability",
                        "hint builders own request/header echo and read-only readiness hints",
                        "record types own response shape and schema-versioned field names"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().safeSplitSequence())
                .contains(
                        "Extract receipt builders after Node v219 has consumed v79 schema v13",
                        "Extract digest helpers only after warningDigest repeatability tests stay green",
                        "Run focused release approval rehearsal tests after each split"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().deferredSplitReasons())
                .contains(
                        "OpsEvidenceService still coordinates many evidence families, so broad split is deferred",
                        "Receipt extraction must not change warningDigest ordering or response field names"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().forbiddenQualityPassOperations())
                .contains(
                        "Create approval decision during Java v79 quality pass",
                        "Write approval ledger during Java v79 quality pass",
                        "Execute SQL during Java v79 quality pass",
                        "Change release approval rehearsal API path during Java v79 quality pass"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219Prerequisites())
                .contains(
                        "Node v218 audit route and managed audit helper quality pass must be complete",
                        "Java v79 quality split receipt must expose receipt digest hint render record boundaries",
                        "mini-kv v88 command dispatch quality receipt must be present before Node v219",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().receiptWarnings())
                .containsExactly("NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY");
        assertThat(rehearsal.opsEvidenceServiceQualitySplitReceipt().nodeVerificationActions())
                .contains(
                        "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                        "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                        "Keep opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().sourceQualitySplitReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().sourceQualitySplitSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v13");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellVersion())
                .isEqualTo("Node v220");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellProfile())
                .isEqualTo("managed-audit-adapter-disabled-shell.v1");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-adapter-disabled-shell");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().consumedByNodeDisabledShellState())
                .isEqualTo("disabled-shell-ready");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nextNodeCandidateVersion())
                .isEqualTo("Node v221");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nextNodeCandidateProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-dry-run.v1");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV221MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220DisabledShellReady())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220SelectedAdapterDisabled())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220LocalDryRunOnlyDeclared())
                .isTrue();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220AppendWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220QueryReturnedRecords())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220ExternalManagedAuditAccessed())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220LocalDryRunWritePerformed())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaDeploymentTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaRollbackTriggered()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().javaRestoreExecuted()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt()
                .readyForNodeV221LocalAdapterCandidateDryRun()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionWindow()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().acceptedAdapterShellChecks())
                .contains(
                        "Node v220 profileVersion must equal managed-audit-adapter-disabled-shell.v1",
                        "Node v220 shellState must equal disabled-shell-ready",
                        "Node v220 selectedAdapterKind must stay disabled",
                        "Node v220 acceptedCandidateKinds may declare local-dry-run but must not select it"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().forbiddenImplementationOperations())
                .contains(
                        "Write approval ledger during Java v80 implementation guard",
                        "Write managed audit store during Java v80 implementation guard",
                        "Execute SQL during Java v80 implementation guard",
                        "Select local-dry-run adapter from Java v80 guard"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV221Prerequisites())
                .contains(
                        "Node v220 managed audit adapter disabled shell must be complete",
                        "Java v80 managed audit adapter implementation guard receipt must be ready",
                        "mini-kv v89 adapter shell non-storage guard receipt must be present before Node v221",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().guardWarnings())
                .containsExactly("NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditAdapterImplementationGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
                        "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
                        "Keep managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                        "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .sourceImplementationGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .sourceImplementationGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v14");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportVersion()).isEqualTo("Node v222");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-verification-report.v1");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-local-adapter-candidate-verification-report");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportState())
                .isEqualTo("local-adapter-candidate-verification-ready");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nextNodeReviewVersion())
                .isEqualTo("Node v223");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nextNodeReviewProfile())
                .isEqualTo("managed-audit-external-adapter-connection-readiness-review.v1");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV223MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222VerificationReportReady())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222ReadOnlyReport())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222SourceEndpointRerunPerformed()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222AdditionalLocalDryRunWritePerformed()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV222ConnectsManagedAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ReadyForProductionAudit()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .ownerApprovalRequiredBeforeConnection()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .schemaMigrationReviewRequired()).isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialReviewRequired())
                .isTrue();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialValueReadByJava())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialValueStoredByJava())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditConnectionOpened()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditSchemaMigrated()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaDeploymentTriggered())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaRollbackTriggered())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().javaRestoreExecuted())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForNodeV223ExternalAdapterConnectionReadinessReview()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().requiredPreConnectionReviews())
                .contains(
                        "external managed audit owner approval",
                        "external managed audit schema migration review",
                        "external managed audit credential review"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().credentialBoundaryClaims())
                .contains(
                        "Java v81 must not read credential values",
                        "Java v81 must not store credential values",
                        "Java v81 must not open an external managed audit connection"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .forbiddenExternalAdapterOperations())
                .contains(
                        "Open external managed audit connection during Java v81 migration guard",
                        "Execute schema migration SQL during Java v81 migration guard",
                        "Write managed audit store during Java v81 migration guard"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeV223Prerequisites())
                .contains(
                        "Node v222 verification report must be ready and read-only",
                        "Java v81 external adapter migration guard receipt must be ready",
                        "mini-kv v90 external adapter non-participation receipt must be present before Node v223",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                );
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardWarnings())
                .containsExactly("NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditExternalAdapterMigrationGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                        "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                        "Keep managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .sourceExternalAdapterMigrationGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-external-adapter-migration-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .sourceExternalAdapterMigrationGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v15");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanVersion()).isEqualTo("Node v224");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-plan.v1");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-sandbox-adapter-dry-run-plan");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanState())
                .isEqualTo("sandbox-adapter-dry-run-plan-ready");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nextNodePackageVersion())
                .isEqualTo("Node v225");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nextNodePackageProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-package.v1");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeV225MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readyForManagedAuditSandboxAdapterDryRunPlan()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readyForManagedAuditSandboxAdapterDryRunPackage()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readOnlyPlan()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().connectsManagedAudit()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readsManagedAuditCredential()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().schemaMigrationExecuted()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().javaApprovalDecisionCreated()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().javaApprovalLedgerWritten()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationRehearsalRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationChecklistRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationExecutionAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().productionCredentialAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueRequired()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().externalManagedAuditConnectionOpened()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaManagedAuditStoreWritten()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaSqlExecuted()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().qualityGatesAreHardAcceptanceCriteria()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderOrHelperSplitApplied()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().longBooleanConstructorAvoided()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().receiptFieldsGroupedByBoundary()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().opsEvidenceServiceOnlyWiresReceipt()).isTrue();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForNodeV225SandboxAdapterDryRunPackage()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().requiredSandboxEvidence())
                .contains(
                        "Owner approval artifact identifier for sandbox rehearsal",
                        "Sandbox credential handle without credential value disclosure",
                        "Schema migration rehearsal checklist without SQL execution"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().forbiddenSandboxOperations())
                .contains(
                        "Read or print a production managed audit credential value during Java v82 guard",
                        "Open an external managed audit connection during Java v82 guard",
                        "Execute schema migration SQL during Java v82 guard"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeV225Prerequisites())
                .contains(
                        "Node v224 sandbox adapter dry-run plan must be ready and read-only",
                        "Java v82 sandbox approval/schema guard receipt must be ready",
                        "mini-kv v91 sandbox runtime evidence non-participation receipt must be present"
                );
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardWarnings())
                .containsExactly("NODE_V225_SOURCE_EXTERNAL_ADAPTER_MIGRATION_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224",
                        "Require managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=true before Node v225",
                        "Keep managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "Verify managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-handoff-marker.v1"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sourceSandboxAdapterApprovalSchemaGuardReceiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-adapter-approval-schema-guard-receipt.v1"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sourceSandboxAdapterApprovalSchemaGuardSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v16");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeEvidenceChecklistVersion()).isEqualTo("Node v227");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeEvidenceChecklistProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-evidence-checklist.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketVersion()).isEqualTo("Node v228");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-operator-packet.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-manual-sandbox-connection-operator-packet");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketState())
                .isEqualTo("manual-sandbox-connection-operator-packet-ready");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nextNodePacketVerificationVersion()).isEqualTo("Node v229");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nextNodePacketVerificationProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-packet-verification.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().nodeV229MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().javaStartsManagedAuditService()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().nodeAutoStartAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().ownerApprovalArtifactIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().schemaRehearsalIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().operatorPacketReadOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().ownerApprovalArtifactIdFieldRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().schemaRehearsalIdFieldRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().packetCreatesApprovalDecision()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialHandleNameField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialHandleNameRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialValueRequiredByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().rollbackPathIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().manualAbortMarkerField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForNodeV229ManualSandboxConnectionPacketVerification()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().acceptedOperatorPacketFields())
                .contains(
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                        "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().forbiddenHandoffOperations())
                .contains(
                        "Open a managed audit sandbox connection during Java v87 marker",
                        "Execute schema migration SQL during Java v87 marker",
                        "Write approval ledger or managed audit state during Java v87 marker"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().nodeV229Prerequisites())
                .contains(
                        "Node v228 manual sandbox connection operator packet must be archived",
                        "Java v87 sandbox connection operator handoff marker must be ready",
                        "mini-kv v96 sandbox connection receipt echo marker must be ready"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerWarnings())
                .containsExactly("NODE_V229_SOURCE_SANDBOX_ADAPTER_APPROVAL_SCHEMA_GUARD_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile with Node v228",
                        "Require managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=true before Node v229",
                        "Keep managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false",
                        "Keep managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-connection-preflight-echo-marker.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sourceSandboxConnectionOperatorHandoffMarkerVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-handoff-marker.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sourceSandboxConnectionOperatorHandoffSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v17");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .consumedByNodePreflightGateVersion()).isEqualTo("Node v230");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .consumedByNodePreflightGateProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-preflight-gate.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .consumedByNodePreflightGateEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-manual-sandbox-connection-preflight-gate");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .consumedByNodePreflightGateState())
                .isEqualTo("manual-sandbox-connection-preflight-gate-ready");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .nextNodePreflightVerificationVersion()).isEqualTo("Node v231");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .nextNodePreflightVerificationProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-preflight-verification.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().nodeV231MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowFlagName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_MANUAL_SANDBOX_WINDOW_APPROVED");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowFlagRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowOpenByDefault()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().nodeAutoStartAllowed()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().ownerApprovalArtifactIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().schemaRehearsalIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().rollbackPathIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().manualAbortMarkerField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().allRequiredPreflightFieldsRecognizedByJava()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().preflightGateReadOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().gateCreatesConnectionCommand()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .credentialBoundary().credentialHandleNameField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .readyForNodeV231ManualSandboxConnectionPreflightVerification()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().readyForProductionWindow())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().requiredPreflightFields())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                        "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                        "timeoutBudgetMs=15000",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_SANDBOX_WINDOW_APPROVED"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .forbiddenPreflightOperations())
                .contains(
                        "Open a managed audit sandbox connection during Java v88 preflight echo",
                        "Execute schema migration SQL during Java v88 preflight echo",
                        "Start Java, mini-kv, or external audit services automatically"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().nodeV231Prerequisites())
                .contains(
                        "Node v230 manual sandbox connection preflight gate must be archived",
                        "Java v88 sandbox connection preflight echo marker must be ready",
                        "mini-kv v97 no-start guard receipt must be ready"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerWarnings())
                .containsExactly("NODE_V231_SOURCE_SANDBOX_CONNECTION_OPERATOR_HANDOFF_MARKER_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxConnectionPreflightEchoMarker().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateProfile with Node v230",
                        "Require managedAuditSandboxConnectionPreflightEchoMarker.readyForNodeV231ManualSandboxConnectionPreflightVerification=true before Node v231",
                        "Compare managedAuditSandboxConnectionPreflightEchoMarker.requiredPreflightFields with Node v230 preflightFields",
                        "Keep managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialValueReadByJava=false"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-connection-precondition-receipt.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .sourceSandboxConnectionPreflightEchoMarkerVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-connection-preflight-echo-marker.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .sourceSandboxConnectionPreflightEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v18");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .consumedByNodeBlockedExecutionRehearsalVersion()).isEqualTo("Node v234");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .consumedByNodeBlockedExecutionRehearsalProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-blocked-execution-rehearsal.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .nextNodePreconditionIntakeVersion()).isEqualTo("Node v235");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().nodeV235MayConsume()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .credentialBoundary().credentialHandleReviewRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .rollbackPathBoundary().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .rollbackPathBoundary().manualAbortMarkerRequired()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().allPreconditionsDocumented())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .readyForNodeV235ManualSandboxConnectionPreconditionIntake()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().requiredPreconditionEvidence())
                .contains(
                        "owner approval artifact id field: ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "credential handle review field: ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "schema rehearsal evidence field: ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                        "timeout budget: 15000ms",
                        "manual abort marker field: ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .forbiddenPreconditionOperations())
                .contains(
                        "Open a managed audit sandbox connection during Java v91 precondition receipt",
                        "Read or print a managed audit credential value during Java v91 precondition receipt",
                        "Execute schema migration SQL during Java v91 precondition receipt"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().nodeV235Prerequisites())
                .contains(
                        "Node v234 blocked execution rehearsal must be archived",
                        "Java v91 sandbox connection precondition receipt must be present",
                        "mini-kv v100 current runtime fixture rolling evidence guard must be present"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptWarnings())
                .containsExactly("NODE_V235_SOURCE_SANDBOX_CONNECTION_PREFLIGHT_ECHO_MARKER_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionPreconditionReceipt.consumedByNodeBlockedExecutionRehearsalProfile with Node v234",
                        "Require managedAuditSandboxConnectionPreconditionReceipt.readyForNodeV235ManualSandboxConnectionPreconditionIntake=true before Node v235",
                        "Keep managedAuditSandboxConnectionPreconditionReceipt.readyForManagedAuditSandboxAdapterConnection=false",
                        "Keep managedAuditSandboxConnectionPreconditionReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-envelope-echo-receipt.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .sourceSandboxConnectionPreconditionReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v19");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeVersion()).isEqualTo("Node v236");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-dry-run-request-envelope.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeEndpoint())
                .isEqualTo("/api/v1/audit/managed-audit-manual-sandbox-connection-dry-run-request-envelope");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .nextNodeReadinessGateVersion()).isEqualTo("Node v237");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().nodeV237MayConsume())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().ownerApprovalArtifactIdField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().credentialHandleNameField())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().timeoutBudgetField()).isEqualTo("timeoutBudgetMs");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().operatorReviewFieldsComplete()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().dryRunEnvelopeReadOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().envelopeCreatesConnectionCommand()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary().credentialHandleOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary().credentialValueIncludedInEnvelope()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().managedAuditStoreWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().allEnvelopeFieldsEchoed())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().credentialValueExcluded())
                .isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForNodeV237ManualSandboxConnectionReadinessGate()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().echoedEnvelopeFieldNames())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                        "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                        "timeoutBudgetMs",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .forbiddenEnvelopeOperations())
                .contains(
                        "Include a managed audit credential value in the Java v92 dry-run envelope echo",
                        "Open a managed audit sandbox connection during Java v92 dry-run envelope echo",
                        "Write approval ledger or managed audit state during Java v92 dry-run envelope echo"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().nodeV237Prerequisites())
                .contains(
                        "Node v236 manual sandbox connection dry-run request envelope must be archived",
                        "Java v92 sandbox connection dry-run envelope echo receipt must be present",
                        "mini-kv v101 no-start / no-write evidence follow-up must be present"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptWarnings())
                .containsExactly("NODE_V237_SOURCE_SANDBOX_CONNECTION_PRECONDITION_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile with Node v236",
                        "Require managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate=true before Node v237",
                        "Keep managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope=false",
                        "Keep managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt().receiptVersion())
                .isEqualTo("java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-window-checklist-echo-receipt.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .sourceSandboxConnectionDryRunEnvelopeEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v20");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .consumedByNodeOperatorWindowChecklistVersion()).isEqualTo("Node v238");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .consumedByNodeOperatorWindowChecklistProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-operator-window-checklist.v1");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .nextNodeEvidenceVerificationVersion()).isEqualTo("Node v239");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().requiredApprovalCount()).isEqualTo(3);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().checklistStepCount()).isEqualTo(8);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().pauseConditionCount()).isEqualTo(8);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().forbiddenOperationCount()).isEqualTo(6);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().operatorChecklistReadOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().checklistCreatesConnectionCommand()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalBoundary().approvalItemCount()).isEqualTo(3);
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary().credentialHandleOnly()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary().credentialValueIncludedInChecklist()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .allChecklistFieldsEchoed()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalChecklistEchoComplete()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialValueExcluded()).isTrue();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .readyForNodeV239ManualSandboxConnectionEvidenceVerification()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt().receiptDigest())
                .startsWith("sha256:");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .echoedApprovalItemIds())
                .containsExactly("release-owner", "security-reviewer", "operations-owner");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .echoedChecklistStepPhases())
                .contains("source-readiness-gate", "credential-handle", "final-stop-gate");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .echoedPauseConditionCodes())
                .contains("SOURCE_GATE_NOT_READY", "CREDENTIAL_VALUE_REQUESTED", "UPSTREAM_ACTIONS_ENABLED");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .forbiddenChecklistOperations())
                .contains(
                        "Open a managed audit sandbox connection during Java v93 operator checklist echo",
                        "Write approval ledger or managed audit state during Java v93 operator checklist echo"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .nodeV239Prerequisites())
                .contains(
                        "Node v238 manual sandbox connection operator window checklist must be archived",
                        "Java v93 sandbox connection operator window checklist echo receipt must be present",
                        "mini-kv v102 operator window no-start / no-write receipt must be present"
                );
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt().receiptWarnings())
                .containsExactly("NODE_V239_SOURCE_SANDBOX_CONNECTION_DRY_RUN_ENVELOPE_ECHO_RECEIPT_NOT_READY");
        assertThat(rehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistProfile with Node v238",
                        "Require managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForNodeV239ManualSandboxConnectionEvidenceVerification=true before Node v239",
                        "Keep managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueIncludedInChecklist=false",
                        "Keep managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.failureTaxonomy().taxonomyVersion())
                .isEqualTo("java-release-approval-rehearsal-failure-taxonomy.v1");
        assertThat(rehearsal.failureTaxonomy().upstreamReadiness()).isEqualTo("READY");
        assertThat(rehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("WARNING");
        assertThat(rehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("WARNING");
        assertThat(rehearsal.failureTaxonomy().javaReadOnlyUpstreamReady()).isTrue();
        assertThat(rehearsal.failureTaxonomy().authContextComplete()).isFalse();
        assertThat(rehearsal.failureTaxonomy().auditCorrelationPresent()).isFalse();
        assertThat(rehearsal.failureTaxonomy().retryableByReadOnlyAdapter()).isTrue();
        assertThat(rehearsal.failureTaxonomy().writeActionRequired()).isFalse();
        assertThat(rehearsal.failureTaxonomy().failureCategories())
                .containsExactly(
                        "AUTH_CONTEXT_WARNING",
                        "AUDIT_CORRELATION_WARNING",
                        "READ_ONLY_EXECUTION_BLOCKED"
                );
        assertThat(rehearsal.failureTaxonomy().taxonomyWarnings())
                .containsExactly(
                        "REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING",
                        "AUDIT_CORRELATION_ID_MISSING",
                        "REHEARSAL_REMAINS_READ_ONLY"
                );
        assertThat(rehearsal.verificationHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-verification-hint.v1");
        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v31");
        assertThat(rehearsal.verificationHint().warningDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().noLedgerWriteProof())
                .isEqualTo("NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS");
        assertThat(rehearsal.verificationHint().noLedgerWriteProved()).isTrue();
        assertThat(rehearsal.verificationHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains(
                        "requestContext",
                        "operatorWindowHint",
                        "ciEvidenceHint",
                        "artifactRetentionHint",
                        "liveReadinessHint",
                        "auditPersistenceHandoffHint",
                        "approvalRecordHandoffHint",
                        "approvalHandoffVerificationMarker",
                        "managedAuditAdapterBoundaryReceipt",
                        "managedAuditProductionAdapterPrerequisiteReceipt",
                        "opsEvidenceServiceQualitySplitReceipt",
                        "managedAuditAdapterImplementationGuardReceipt",
                        "managedAuditExternalAdapterMigrationGuardReceipt",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt",
                        "managedAuditSandboxConnectionOperatorHandoffMarker",
                        "managedAuditSandboxConnectionPreflightEchoMarker",
                        "managedAuditSandboxConnectionPreconditionReceipt",
                        "managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt",
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt",
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceipt",
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker",
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker",
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker",
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker",
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker",
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt",
                        "failureTaxonomy",
                        "verificationHint",
                        "executionBoundaries"
                );
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .containsExactly(
                        "contextWarnings",
                        "operatorWindowEchoWarnings",
                        "ciEvidenceEchoWarnings",
                        "artifactRetentionEchoWarnings",
                        "liveReadinessEchoWarnings",
                        "auditPersistenceHandoffEchoWarnings",
                        "approvalRecordHandoffEchoWarnings",
                        "approvalHandoffVerificationMarkerWarnings",
                        "managedAuditAdapterBoundaryReceiptWarnings",
                        "managedAuditProductionAdapterPrerequisiteReceiptWarnings",
                        "opsEvidenceServiceQualitySplitReceiptWarnings",
                        "managedAuditAdapterImplementationGuardReceiptWarnings",
                        "managedAuditExternalAdapterMigrationGuardReceiptWarnings",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceiptWarnings",
                        "managedAuditSandboxConnectionOperatorHandoffMarkerWarnings",
                        "managedAuditSandboxConnectionPreflightEchoMarkerWarnings",
                        "managedAuditSandboxConnectionPreconditionReceiptWarnings",
                        "managedAuditSandboxConnectionDryRunEnvelopeEchoReceiptWarnings",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptWarnings",
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceiptWarnings",
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceiptWarnings",
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptWarnings",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerWarnings",
                        "managedAuditSandboxEndpointHandlePreflightEchoMarkerWarnings",
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerWarnings",
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerWarnings",
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerWarnings",
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptWarnings",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptWarnings",
                        "failureCategories",
                        "taxonomyWarnings",
                        "executionAllowed",
                        "approvalLedgerWritten",
                        "javaManagedAuditWriteAllowed",
                        "javaApprovalRecordPersisted",
                        "nodeMayTreatAsProductionApprovalRecord",
                        "nodeMayTreatAsProductionAuditRecord",
                        "nodeV211ProductionAuditRecordAllowed",
                        "nodeV211RealApprovalDecisionCreated",
                        "nodeV215MayConnectManagedAudit",
                        "nodeV215MayCreateApprovalDecision",
                        "nodeV215MayWriteApprovalLedger",
                        "nodeV215MayExecuteSql",
                        "nodeV215MayTriggerDeployment",
                        "nodeV215MayTriggerRollback",
                        "nodeV215MayExecuteRestore",
                        "nodeV217MayConnectManagedAudit",
                        "nodeV217MayWriteApprovalLedger",
                        "nodeV217MayExecuteSql",
                        "nodeV217MayTriggerDeployment",
                        "nodeV217MayTriggerRollback",
                        "nodeV217MayExecuteRestore",
                        "qualitySplitApiShapeChanged",
                        "qualitySplitApprovalDecisionCreated",
                        "qualitySplitApprovalLedgerWritten",
                        "qualitySplitManagedAuditStoreWritten",
                        "qualitySplitSqlExecuted",
                        "implementationGuardDigest",
                        "implementationGuardJavaApprovalLedgerWritten",
                        "implementationGuardJavaManagedAuditStoreWritten",
                        "implementationGuardJavaSqlExecuted",
                        "implementationGuardNodeV220AppendWritten",
                        "implementationGuardNodeV220ExternalManagedAuditAccessed",
                        "implementationGuardNodeV220LocalDryRunWritePerformed",
                        "externalAdapterMigrationGuardDigest",
                        "externalAdapterMigrationCredentialValueReadByJava",
                        "externalAdapterMigrationConnectionOpened",
                        "externalAdapterMigrationSchemaMigrated",
                        "externalAdapterMigrationJavaManagedAuditStoreWritten",
                        "externalAdapterMigrationJavaSqlExecuted",
                        "externalAdapterMigrationNodeV222SourceEndpointRerunPerformed",
                        "externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed",
                        "sandboxAdapterApprovalSchemaGuardDigest",
                        "sandboxAdapterOwnerApprovalArtifactProvidedByJava",
                        "sandboxAdapterSchemaMigrationSqlExecutedByJava",
                        "sandboxAdapterCredentialValueReadByJava",
                        "sandboxAdapterExternalManagedAuditConnectionOpened",
                        "sandboxAdapterJavaManagedAuditStoreWritten",
                        "sandboxAdapterJavaSqlExecuted",
                        "sandboxAdapterQualityGateBuilderOrHelperSplitApplied",
                        "sandboxConnectionOperatorHandoffMarkerDigest",
                        "sandboxConnectionOperatorWindowOpenedByJava",
                        "sandboxConnectionOwnerArtifactIdFieldRecognizedByJava",
                        "sandboxConnectionCredentialValueReadByJava",
                        "sandboxConnectionSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionRollbackTriggeredByJava",
                        "sandboxConnectionExternalManagedAuditConnectionOpenedByJava",
                        "sandboxConnectionPreflightEchoMarkerDigest",
                        "sandboxConnectionPreflightManualWindowOpenedByJava",
                        "sandboxConnectionPreflightManualWindowOpenByDefault",
                        "sandboxConnectionPreflightCredentialValueReadByJava",
                        "sandboxConnectionPreflightSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionPreflightExternalManagedAuditConnectionOpenedByJava",
                        "sandboxConnectionPreflightNodeAutoStartAllowed",
                        "sandboxConnectionPreconditionReceiptDigest",
                        "sandboxConnectionPreconditionOwnerApprovalArtifactProvidedByJava",
                        "sandboxConnectionPreconditionCredentialValueReadByJava",
                        "sandboxConnectionPreconditionSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionPreconditionExternalManagedAuditConnectionOpenedByJava",
                        "sandboxConnectionPreconditionActualConnectionAttemptedByJava",
                        "sandboxConnectionPreconditionNodeAutoStartAllowed",
                        "sandboxConnectionDryRunEnvelopeEchoReceiptDigest",
                        "sandboxConnectionDryRunEnvelopeCredentialValueIncluded",
                        "sandboxConnectionDryRunEnvelopeCredentialValueReadByJava",
                        "sandboxConnectionDryRunEnvelopeActualConnectionAttemptedByJava",
                        "sandboxConnectionDryRunEnvelopeSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionDryRunEnvelopeApprovalLedgerWrittenByJava",
                        "sandboxConnectionDryRunEnvelopeManagedAuditStoreWrittenByJava",
                        "sandboxConnectionDryRunEnvelopeNodeAutoStartAllowed",
                        "sandboxConnectionOperatorWindowChecklistEchoReceiptDigest",
                        "sandboxConnectionOperatorWindowChecklistCredentialValueIncluded",
                        "sandboxConnectionOperatorWindowChecklistCredentialValueReadByJava",
                        "sandboxConnectionOperatorWindowChecklistActualConnectionAttemptedByJava",
                        "sandboxConnectionOperatorWindowChecklistSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionOperatorWindowChecklistApprovalLedgerWrittenByJava",
                        "sandboxConnectionOperatorWindowChecklistManagedAuditStateWriteRequestedByJava",
                        "sandboxConnectionOperatorWindowChecklistNodeAutoStartAllowed",
                        "sandboxConnectionOperatorWindowChecklistProductionWindowOpenedByJava",
                        "sandboxConnectionDryRunCommandPackageEchoReceiptDigest",
                        "sandboxConnectionDryRunCommandPackageCommandCount",
                        "sandboxConnectionDryRunCommandPackageDisabledByDefault",
                        "sandboxConnectionDryRunCommandPackageDryRunOnly",
                        "sandboxConnectionDryRunCommandPackageCarriesCredentialValue",
                        "sandboxConnectionDryRunCommandPackageCredentialValueReadByJava",
                        "sandboxConnectionDryRunCommandPackageActualConnectionAttemptedByJava",
                        "sandboxConnectionDryRunCommandPackageSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionDryRunCommandPackageApprovalLedgerWrittenByJava",
                        "sandboxConnectionDryRunCommandPackageManagedAuditStateWriteRequestedByJava",
                        "sandboxConnectionDryRunCommandPackageUpstreamServiceAutoStartRequestedByJava",
                        "sandboxConnectionDryRunCommandPackageMiniKvWritePermissionRequestedByJava",
                        "sandboxConnectionPrecheckPacketEchoReceiptDigest",
                        "sandboxConnectionPrecheckPacketPrecheckItemCount",
                        "sandboxConnectionPrecheckPacketDisabledByDefault",
                        "sandboxConnectionPrecheckPacketDryRunOnly",
                        "sandboxConnectionPrecheckPacketCarriesCredentialValue",
                        "sandboxConnectionPrecheckPacketCredentialValueReadByJava",
                        "sandboxConnectionPrecheckPacketActualConnectionAttemptedByJava",
                        "sandboxConnectionPrecheckPacketSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionPrecheckPacketApprovalLedgerWrittenByJava",
                        "sandboxConnectionPrecheckPacketManagedAuditStateWriteRequestedByJava",
                        "sandboxConnectionPrecheckPacketUpstreamServiceAutoStartRequestedByJava",
                        "sandboxConnectionPrecheckPacketMiniKvWritePermissionRequestedByJava",
                        "sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptDigest",
                        "sandboxConnectionDisabledAdapterClientPrecheckRequiredEnvHandleCount",
                        "sandboxConnectionDisabledAdapterClientPrecheckFailureClassCount",
                        "sandboxConnectionDisabledAdapterClientPrecheckDryRunResponseFieldCount",
                        "sandboxConnectionDisabledAdapterClientPrecheckClientMayBeInstantiated",
                        "sandboxConnectionDisabledAdapterClientPrecheckExternalRequestMayBeSent",
                        "sandboxConnectionDisabledAdapterClientPrecheckCredentialValueMayBeLoaded",
                        "sandboxConnectionDisabledAdapterClientPrecheckActualConnectionAttemptedByJava",
                        "sandboxConnectionDisabledAdapterClientPrecheckSchemaMigrationSqlExecutedByJava",
                        "sandboxConnectionDisabledAdapterClientPrecheckApprovalLedgerWrittenByJava",
                        "sandboxConnectionDisabledAdapterClientPrecheckUpstreamServiceAutoStartRequestedByJava",
                        "sandboxConnectionDisabledAdapterClientPrecheckMiniKvWritePermissionRequestedByJava",
                        "sandboxConnectionFakeTransportDryRunPacketEchoMarkerDigest",
                        "sandboxConnectionFakeTransportDryRunPacketRequestShapeFieldCount",
                        "sandboxConnectionFakeTransportDryRunPacketResponseShapeFieldCount",
                        "sandboxConnectionFakeTransportDryRunPacketFailureMappingCount",
                        "sandboxConnectionFakeTransportDryRunPacketTimeoutBudgetMs",
                        "sandboxConnectionFakeTransportDryRunPacketCleanupArtifactCount",
                        "sandboxConnectionFakeTransportDryRunPacketConnectionAttempted",
                        "sandboxConnectionFakeTransportDryRunPacketExternalRequestSent",
                        "sandboxConnectionFakeTransportDryRunPacketCredentialValueRead",
                        "sandboxConnectionFakeTransportDryRunPacketSchemaMigrationExecuted",
                        "sandboxConnectionFakeTransportDryRunPacketProductionRecordWritten",
                        "sandboxConnectionFakeTransportDryRunPacketJavaStarted",
                        "sandboxConnectionFakeTransportDryRunPacketMiniKvStarted",
                        "sandboxConnectionFakeTransportDryRunPacketExternalAuditServiceStarted",
                        "sandboxEndpointHandlePreflightEchoMarkerDigest",
                        "sandboxEndpointHandlePreflightRequiredReviewItemCount",
                        "sandboxEndpointHandlePreflightCompletedReviewItemCount",
                        "sandboxEndpointHandlePreflightForbiddenOperationCount",
                        "sandboxEndpointHandlePreflightEndpointHandleOnly",
                        "sandboxEndpointHandlePreflightCredentialHandleOnly",
                        "sandboxEndpointHandlePreflightRawEndpointUrlParsed",
                        "sandboxEndpointHandlePreflightRawEndpointUrlIncluded",
                        "sandboxEndpointHandlePreflightCredentialValueRead",
                        "sandboxEndpointHandlePreflightExternalRequestSent",
                        "sandboxEndpointHandlePreflightSchemaMigrationExecuted",
                        "sandboxEndpointHandlePreflightAutomaticUpstreamStart",
                        "sandboxEndpointHandlePreflightConnectsManagedAudit",
                        "sandboxEndpointHandlePreflightJavaStarted",
                        "sandboxEndpointHandlePreflightMiniKvStarted",
                        "sandboxEndpointCredentialResolverDecisionEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverDecisionRequiredFieldCount",
                        "sandboxEndpointCredentialResolverDecisionNoGoConditionCount",
                        "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeRead",
                        "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeLoaded",
                        "sandboxEndpointCredentialResolverDecisionCredentialValueMayBeStored",
                        "sandboxEndpointCredentialResolverDecisionRawEndpointUrlMayBeParsed",
                        "sandboxEndpointCredentialResolverDecisionExternalRequestMayBeSent",
                        "sandboxEndpointCredentialResolverDecisionManagedAuditConnectionMayOpen",
                        "sandboxEndpointCredentialResolverDecisionSchemaMigrationMayExecute",
                        "sandboxEndpointCredentialResolverDecisionApprovalLedgerMayBeWritten",
                        "sandboxEndpointCredentialResolverDecisionJavaOrMiniKvStartAllowed",
                        "sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverDisabledPrecheckRequiredEnvHandleCount",
                        "sandboxEndpointCredentialResolverDisabledPrecheckOptInGateCount",
                        "sandboxEndpointCredentialResolverDisabledPrecheckFailureClassCount",
                        "sandboxEndpointCredentialResolverDisabledPrecheckDryRunResponseFieldCount",
                        "sandboxEndpointCredentialResolverDisabledPrecheckInheritedNoGoConditionCount",
                        "sandboxEndpointCredentialResolverDisabledPrecheckResolverClientMayBeInstantiated",
                        "sandboxEndpointCredentialResolverDisabledPrecheckSecretProviderMayBeInstantiated",
                        "sandboxEndpointCredentialResolverDisabledPrecheckCredentialValueMayBeLoaded",
                        "sandboxEndpointCredentialResolverDisabledPrecheckRawEndpointUrlMayBeParsed",
                        "sandboxEndpointCredentialResolverDisabledPrecheckExternalRequestMayBeSent",
                        "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectCredentialValueRead",
                        "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectRawEndpointUrlParsed",
                        "sandboxEndpointCredentialResolverDisabledPrecheckSideEffectExternalRequestSent",
                        "sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverTestOnlyShellRequestShapeFieldCount",
                        "sandboxEndpointCredentialResolverTestOnlyShellResponseShapeFieldCount",
                        "sandboxEndpointCredentialResolverTestOnlyShellFailureMappingCount",
                        "sandboxEndpointCredentialResolverTestOnlyShellGuardConditionCount",
                        "sandboxEndpointCredentialResolverTestOnlyShellFakeResolverOnly",
                        "sandboxEndpointCredentialResolverTestOnlyShellCredentialValueAccepted",
                        "sandboxEndpointCredentialResolverTestOnlyShellRawEndpointUrlAccepted",
                        "sandboxEndpointCredentialResolverTestOnlyShellResolverClientInstantiated",
                        "sandboxEndpointCredentialResolverTestOnlyShellSecretProviderInstantiated",
                        "sandboxEndpointCredentialResolverTestOnlyShellExternalRequestSent",
                        "sandboxEndpointCredentialResolverTestOnlyShellProbeCredentialValueRead",
                        "sandboxEndpointCredentialResolverTestOnlyShellProbeExternalRequestSent",
                        "sandboxEndpointCredentialResolverTestOnlyShellProbeProductionRecordWritten",
                        "sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverFakeShellArchiveCheckCount",
                        "sandboxEndpointCredentialResolverFakeShellArchivePassedCheckCount",
                        "sandboxEndpointCredentialResolverFakeShellArchiveFileCount",
                        "sandboxEndpointCredentialResolverFakeShellArchiveRequiredSnippetCount",
                        "sandboxEndpointCredentialResolverFakeShellArchiveMatchedSnippetCount",
                        "sandboxEndpointCredentialResolverFakeShellArchiveRerunsFakeShellBehavior",
                        "sandboxEndpointCredentialResolverFakeShellArchiveReadsFilesOnly",
                        "sandboxEndpointCredentialResolverFakeShellArchiveRouteResponsesVerified",
                        "sandboxEndpointCredentialResolverFakeShellArchiveCredentialValueRead",
                        "sandboxEndpointCredentialResolverFakeShellArchiveRawEndpointUrlParsed",
                        "sandboxEndpointCredentialResolverFakeShellArchiveExternalRequestSent",
                        "sandboxEndpointCredentialResolverFakeShellArchiveSecretProviderInstantiated",
                        "sandboxEndpointCredentialResolverFakeShellArchiveResolverClientInstantiated",
                        "sandboxEndpointCredentialResolverFakeShellArchiveConnectsManagedAudit",
                        "sandboxEndpointCredentialResolverFakeShellArchiveApprovalLedgerWritten",
                        "sandboxEndpointCredentialResolverFakeShellArchiveSqlExecuted",
                        "sandboxEndpointCredentialResolverFakeShellArchiveSchemaMigrationExecuted",
                        "sandboxEndpointCredentialResolverFakeShellArchiveAutomaticUpstreamStart",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionReadinessDecision",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionCheckCount",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionPassedCheckCount",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionMissingRequirementCount",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionProductionBlockerCount",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionCredentialValueRead",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionRawEndpointUrlParsed",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionExternalRequestSent",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionSecretProviderInstantiated",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionResolverClientInstantiated",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionConnectsManagedAudit",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionApprovalLedgerWritten",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionSqlExecuted",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionSchemaMigrationExecuted",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionAutomaticUpstreamStart",
                        "nodeMayWriteApprovalLedger"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "executionAllowed=false",
                        "requestContext.approvalLedgerWritten=false",
                        "ciEvidenceHint.noLedgerWriteProved=true",
                        "ciEvidenceHint.ciArtifactUploadedByJava=false",
                        "artifactRetentionHint.javaRetentionFixtureReadOnly=true",
                        "artifactRetentionHint.githubArtifactAccessedByJava=false",
                        "liveReadinessHint.readOnlyEndpointReady=true",
                        "liveReadinessHint.javaStartedProcessForNode=false",
                        "auditPersistenceHandoffHint.javaAuditSourceReadOnly=true",
                        "auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false",
                        "auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false",
                        "approvalRecordHandoffHint.approvalRecordFixtureReadOnly=true",
                        "approvalRecordHandoffHint.javaApprovalRecordPersisted=false",
                        "approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord=false",
                        "approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false",
                        "approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false",
                        "approvalHandoffVerificationMarker.javaApprovalRecordPersisted=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment=false",
                        "managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger=false",
                        "managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql=false",
                        "opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false",
                        "opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false",
                        "opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false",
                        "managedAuditAdapterImplementationGuardReceipt.nodeV220SelectedAdapterDisabled=true",
                        "managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false",
                        "managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
                        "managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted=false",
                        "managedAuditExternalAdapterMigrationGuardReceipt.ownerApprovalRequiredBeforeConnection=true",
                        "managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false",
                        "managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false",
                        "managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary.ownerApprovalArtifactRequired=true",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.ownerApprovalBoundary.ownerApprovalArtifactProvidedByJava=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.sandboxCredentialHandleRequired=true",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.externalManagedAuditConnectionOpened=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.executionBoundary.javaSqlExecuted=false",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.builderOrHelperSplitApplied=true",
                        "managedAuditSandboxAdapterApprovalSchemaGuardReceipt.qualityGateBoundary.longBooleanConstructorAvoided=true",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.sandboxConnectionWindowBoundary.manualSandboxConnectionWindowOpenedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.ownerApprovalArtifactIdFieldRecognizedByJava=true",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.operatorPacketBoundary.schemaRehearsalIdFieldRecognizedByJava=true",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.rollbackPathBoundary.rollbackExecutionAllowedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
                        "managedAuditSandboxConnectionOperatorHandoffMarker.javaExecutionBoundary.sqlExecutedByJava=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenedByJava=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.sandboxConnectionWindowBoundary.manualWindowOpenByDefault=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.allRequiredPreflightFieldsRecognizedByJava=true",
                        "managedAuditSandboxConnectionPreflightEchoMarker.preflightFieldBoundary.preflightGateReadOnly=true",
                        "managedAuditSandboxConnectionPreflightEchoMarker.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.schemaRehearsalBoundary.schemaMigrationSqlExecutedByJava=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
                        "managedAuditSandboxConnectionPreflightEchoMarker.javaExecutionBoundary.sqlExecutedByJava=false",
                        "managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope=false",
                        "managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false",
                        "managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.operatorChecklistReadOnly=true",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.checklistFieldBoundary.checklistCreatesConnectionCommand=false",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueIncludedInChecklist=false",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueReadByJava=false",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false",
                        "managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.requiredEnvHandleCount=5",
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.requestShapeFieldCount=8",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.cleanupArtifactCount=0",
                        "executionBoundaries.nodeMayWriteApprovalLedger=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Verify responseSchemaVersion before importing operator window results",
                        "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                        "Compare artifactRetentionHint.ciArtifactName and ciRetentionDays with Node v202 dry-run contract",
                        "Compare liveReadinessHint.sourcePreflightVersion and runtimeSmokeSessionId with Node v204/v205 smoke context",
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateVersion with Node v208 managed audit candidate",
                        "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
                        "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 packet profile",
                        "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
                        "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
                        "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
                        "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
                        "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                        "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                        "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
                        "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
                        "Compare managedAuditExternalAdapterMigrationGuardReceipt.consumedByNodeVerificationReportProfile with Node v222",
                        "Require managedAuditExternalAdapterMigrationGuardReceipt.readyForNodeV223ExternalAdapterConnectionReadinessReview=true before Node v223",
                        "Compare managedAuditSandboxAdapterApprovalSchemaGuardReceipt.consumedByNodeSandboxPlanProfile with Node v224",
                        "Require managedAuditSandboxAdapterApprovalSchemaGuardReceipt.readyForNodeV225SandboxAdapterDryRunPackage=true before Node v225",
                        "Compare managedAuditSandboxConnectionOperatorHandoffMarker.consumedByNodeOperatorPacketProfile with Node v228",
                        "Require managedAuditSandboxConnectionOperatorHandoffMarker.readyForNodeV229ManualSandboxConnectionPacketVerification=true before Node v229",
                        "Compare managedAuditSandboxConnectionPreflightEchoMarker.consumedByNodePreflightGateProfile with Node v230",
                        "Require managedAuditSandboxConnectionPreflightEchoMarker.readyForNodeV231ManualSandboxConnectionPreflightVerification=true before Node v231",
                        "Compare managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile with Node v236",
                        "Require managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate=true before Node v237",
                        "Compare managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistProfile with Node v238",
                        "Require managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForNodeV239ManualSandboxConnectionEvidenceVerification=true before Node v239",
                        "Compare managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportDryRunPacketProfile with Node v255",
                        "Require managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification=true before Node v257",
                        "Keep UPSTREAM_ACTIONS_ENABLED=false"
                );
        assertThat(rehearsal.releaseApprovalInputs().releaseOperatorSignoffFixtureEndpoint())
                .isEqualTo("/contracts/release-operator-signoff.fixture.json");
        assertThat(rehearsal.releaseApprovalInputs().rollbackApproverEvidenceFixtureEndpoint())
                .isEqualTo("/contracts/rollback-approver-evidence.fixture.json");
        assertThat(rehearsal.releaseApprovalInputs().rollbackApprovalRecordFixtureEndpoint())
                .isEqualTo("/contracts/rollback-approval-record.fixture.json");
        assertThat(rehearsal.releaseApprovalInputs().releaseBundleManifestEndpoint())
                .isEqualTo("/contracts/release-bundle-manifest.sample.json");
        assertThat(rehearsal.releaseApprovalInputs().requiredEvidenceEndpoints())
                .containsExactly(
                        "/contracts/release-operator-signoff.fixture.json",
                        "/contracts/rollback-approver-evidence.fixture.json",
                        "/contracts/rollback-approval-record.fixture.json",
                        "/contracts/release-bundle-manifest.sample.json",
                        "/contracts/release-verification-manifest.sample.json",
                        "/contracts/deployment-rollback-evidence.sample.json",
                        "/contracts/production-deployment-runbook-contract.sample.json",
                        "/contracts/production-secret-source-contract.sample.json",
                        "/contracts/rollback-sql-review-gate.sample.json"
                );
        assertThat(rehearsal.liveSignals().pendingReplayApprovals()).isEqualTo(2);
        assertThat(rehearsal.liveSignals().approvedReplayApprovals()).isEqualTo(1);
        assertThat(rehearsal.liveSignals().rejectedReplayApprovals()).isEqualTo(1);
        assertThat(rehearsal.liveSignals().replayBacklog()).isEqualTo(3);
        assertThat(rehearsal.liveSignals().pendingOutboxEvents()).isEqualTo(6);
        assertThat(rehearsal.liveSignals().realReplayAllowedByEvidence()).isFalse();
        assertThat(rehearsal.liveSignals().approvalExecutionDryRun()).isTrue();
        assertThat(rehearsal.liveSignals().evidenceExecutionAllowed()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayConsume()).isTrue();
        assertThat(rehearsal.executionBoundaries().nodeMayCreateApprovalDecision()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayWriteApprovalLedger()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayTriggerDeployment()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayTriggerRollback()).isFalse();
        assertThat(rehearsal.executionBoundaries().nodeMayExecuteRollbackSql()).isFalse();
        assertThat(rehearsal.executionBoundaries().requiresProductionDatabase()).isFalse();
        assertThat(rehearsal.executionBoundaries().requiresProductionSecrets()).isFalse();
        assertThat(rehearsal.executionBoundaries().changesOrderTransactionSemantics()).isFalse();
        assertThat(rehearsal.rehearsalBlockers())
                .contains(
                        "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
                        "APPROVAL_DECISION_CREATION_DISABLED",
                        "ROLLBACK_SQL_EXECUTION_DISABLED",
                        "REPLAY_APPROVAL_PENDING"
                );
        assertThat(rehearsal.requiredNodeEnvironment())
                .containsExactly("UPSTREAM_PROBES_ENABLED=true", "UPSTREAM_ACTIONS_ENABLED=false");
        assertThat(rehearsal.nextEvidenceActions())
                .containsExactly(
                        "GET /api/v1/ops/evidence",
                        "GET /api/v1/ops/release-approval-rehearsal",
                        "GET /contracts/release-operator-signoff.fixture.json",
                        "GET /contracts/rollback-approver-evidence.fixture.json",
                        "GET /contracts/rollback-approval-record.fixture.json",
                        "Keep UPSTREAM_ACTIONS_ENABLED=false"
                );

        ReleaseApprovalRehearsalResponse headerBackedRehearsal =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(headerBackedRehearsal.requestContext().requestId()).isEqualTo("rehearsal-v67-001");
        assertThat(headerBackedRehearsal.requestContext().requestIdSource())
                .isEqualTo("X-Rehearsal-Request-Id");
        assertThat(headerBackedRehearsal.requestContext().operatorIdentity())
                .isEqualTo("release-operator@example.test");
        assertThat(headerBackedRehearsal.requestContext().operatorIdentitySource())
                .isEqualTo("X-Operator-Identity");
        assertThat(headerBackedRehearsal.requestContext().auditCorrelationId())
                .isEqualTo("audit-correlation-v67");
        assertThat(headerBackedRehearsal.requestContext().auditCorrelationSource())
                .isEqualTo("X-Audit-Correlation-Id");
        assertThat(headerBackedRehearsal.requestContext().contextWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorId()).isEqualTo("operator-198");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorIdSource())
                .isEqualTo("x-orderops-operator-id");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorRoles()).isEqualTo("operator,auditor");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorRolesSource())
                .isEqualTo("x-orderops-roles");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorVerifiedClaim()).isEqualTo("true");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorVerifiedClaimSource())
                .isEqualTo("x-orderops-operator-verified");
        assertThat(headerBackedRehearsal.operatorWindowHint().approvalCorrelationId())
                .isEqualTo("approval-v198-operator-window");
        assertThat(headerBackedRehearsal.operatorWindowHint().approvalCorrelationIdSource())
                .isEqualTo("x-orderops-approval-correlation-id");
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorIdentityEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorRolesEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorVerifiedClaimEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().approvalCorrelationEchoed()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().operatorWindowContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.operatorWindowHint().productionIdpVerifiedByJava()).isFalse();
        assertThat(headerBackedRehearsal.operatorWindowHint().persistedApprovalRecordByJava()).isFalse();
        assertThat(headerBackedRehearsal.operatorWindowHint().nodeMayTreatAsProductionIdentity()).isFalse();
        assertThat(headerBackedRehearsal.operatorWindowHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersion())
                .isEqualTo("real-read-window-ci-archive-artifact-manifest.v1");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersionSource())
                .isEqualTo("x-orderops-ci-manifest-version");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigest())
                .isEqualTo("sha256:node-v200-manifest-digest");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigestSource())
                .isEqualTo("x-orderops-ci-manifest-digest");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestEndpoint())
                .isEqualTo("/api/v1/production/real-read-window-ci-archive-artifact-manifest");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestEndpointSource())
                .isEqualTo("x-orderops-ci-manifest-endpoint");
        assertThat(headerBackedRehearsal.ciEvidenceHint().artifactRecordCount()).isEqualTo("9");
        assertThat(headerBackedRehearsal.ciEvidenceHint().artifactRecordCountSource())
                .isEqualTo("x-orderops-ci-artifact-record-count");
        assertThat(headerBackedRehearsal.ciEvidenceHint().approvalCorrelationId())
                .isEqualTo("approval-v198-operator-window");
        assertThat(headerBackedRehearsal.ciEvidenceHint().approvalCorrelationIdSource())
                .isEqualTo("x-orderops-ci-approval-correlation-id");
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersionEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigestEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().manifestEndpointEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().artifactRecordCountEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().approvalCorrelationEchoed()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().ciEvidenceContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().noLedgerWriteProved()).isTrue();
        assertThat(headerBackedRehearsal.ciEvidenceHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().productionWindowAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().nodeMayTreatAsCiArtifactPublication()).isFalse();
        assertThat(headerBackedRehearsal.ciEvidenceHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractVersion())
                .isEqualTo("real-read-window-ci-artifact-upload-dry-run-contract.v1");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractVersionSource())
                .isEqualTo("x-orderops-ci-upload-contract-version");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractDigest())
                .isEqualTo("sha256:node-v202-upload-contract-digest");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadContractDigestSource())
                .isEqualTo("x-orderops-ci-upload-contract-digest");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactName())
                .isEqualTo("orderops-real-read-window-evidence-v191-v201");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactNameSource())
                .isEqualTo("x-orderops-ci-artifact-name");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactRoot()).isEqualTo("c/");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactRootSource())
                .isEqualTo("x-orderops-ci-artifact-root");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciRetentionDays()).isEqualTo("30");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciRetentionDaysSource())
                .isEqualTo("x-orderops-ci-retention-days");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadMode())
                .isEqualTo("dry-run-contract-only");
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciUploadModeSource())
                .isEqualTo("x-orderops-ci-upload-mode");
        assertThat(headerBackedRehearsal.artifactRetentionHint().artifactRetentionContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.artifactRetentionHint().retentionDaysWithinJavaRetention()).isTrue();
        assertThat(headerBackedRehearsal.artifactRetentionHint().javaRetentionFixtureReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.artifactRetentionHint().ciArtifactUploadedByJava()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().githubArtifactAccessedByJava()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().productionWindowAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().nodeMayTreatAsRetentionAuthorization()).isFalse();
        assertThat(headerBackedRehearsal.artifactRetentionHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightVersion())
                .isEqualTo("three-project-real-read-runtime-smoke-preflight.v1");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightVersionSource())
                .isEqualTo("x-orderops-runtime-preflight-version");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightDigest())
                .isEqualTo("sha256:node-v204-preflight-digest");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightDigestSource())
                .isEqualTo("x-orderops-runtime-preflight-digest");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeSessionId())
                .isEqualTo("runtime-smoke-v205-session-001");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeSessionIdSource())
                .isEqualTo("x-orderops-runtime-smoke-session-id");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeReadTargetId())
                .isEqualTo("java-release-approval-rehearsal");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeReadTargetIdSource())
                .isEqualTo("x-orderops-runtime-read-target-id");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeWindowMode())
                .isEqualTo("manual-open-window-plan");
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeWindowModeSource())
                .isEqualTo("x-orderops-runtime-window-mode");
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightVersionEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().sourcePreflightDigestEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeSessionIdEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeReadTargetIdEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeWindowModeEchoed()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().liveReadinessContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().readyForRuntimeSmokeRead()).isTrue();
        assertThat(headerBackedRehearsal.liveReadinessHint().runtimeSmokeExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.liveReadinessHint().javaStartedProcessForNode()).isFalse();
        assertThat(headerBackedRehearsal.liveReadinessHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(headerBackedRehearsal.liveReadinessHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersion())
                .isEqualTo("managed-audit-persistence-boundary-candidate.v1");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersionSource())
                .isEqualTo("x-orderops-managed-audit-candidate-version");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigest())
                .isEqualTo("sha256:node-v208-managed-audit-candidate-digest");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigestSource())
                .isEqualTo("x-orderops-managed-audit-candidate-digest");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditSinkMode())
                .isEqualTo("file-or-sqlite-dry-run-candidate");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditSinkModeSource())
                .isEqualTo("x-orderops-managed-audit-sink-mode");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRetentionDays())
                .isEqualTo("30");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRetentionDaysSource())
                .isEqualTo("x-orderops-managed-audit-retention-days");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicy())
                .isEqualTo("size-and-age-rotation-candidate");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicySource())
                .isEqualTo("x-orderops-managed-audit-rotation-policy");
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().candidateVersionEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().candidateDigestEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().sinkModeEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().retentionDaysEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().rotationPolicyEchoed()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().auditPersistenceHandoffContextComplete())
                .isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().managedAuditRetentionWithinJavaRetention())
                .isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaAuditSourceReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaLedgerWriteAllowed()).isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaManagedAuditWriteAllowed()).isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().javaExternalAuditSystemAccessed()).isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().nodeMayUseAsManagedAuditInput()).isTrue();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.auditPersistenceHandoffHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractVersion())
                .isEqualTo("managed-audit-identity-approval-binding-contract.v1");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractVersionSource())
                .isEqualTo("x-orderops-approval-binding-contract-version");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractDigest())
                .isEqualTo("sha256:node-v210-approval-binding-digest");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractDigestSource())
                .isEqualTo("x-orderops-approval-binding-contract-digest");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRequestId())
                .isEqualTo("approval-request-v210-001");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRequestIdSource())
                .isEqualTo("x-orderops-approval-request-id");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalDecisionState())
                .isEqualTo("APPROVED_DRY_RUN_ONLY");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalDecisionStateSource())
                .isEqualTo("x-orderops-approval-decision-state");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordCorrelationId())
                .isEqualTo("approval-record-correlation-v210");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordCorrelationIdSource())
                .isEqualTo("x-orderops-approval-record-correlation-id");
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractVersionEchoed())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalBindingContractDigestEchoed())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRequestIdEchoed()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalDecisionStateEchoed()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordCorrelationEchoed()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordHandoffContextComplete())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().approvalRecordFixtureReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalDecisionCreated()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalRecordPersisted()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().javaApprovalRecordAuthenticated()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().productionApprovalStoreRequired()).isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().nodeMayUseAsAuditApprovalInput()).isTrue();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().nodeMayTreatAsProductionApprovalRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalRecordHandoffHint().echoWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211HandoffAccepted()).isTrue();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211NoWriteBoundaryAccepted()).isTrue();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().readyForNodeV213RestoreDrillPlan())
                .isTrue();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().markerWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211ProductionAuditRecordAllowed())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalDecisionCreated())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalLedgerWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().nodeMayTreatAsProductionAuditRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.approvalHandoffVerificationMarker().consumedHandoffFieldPaths())
                .contains(
                        "approvalRecordHandoffHint.approvalRecordCorrelationId",
                        "approvalRecordHandoffHint.approvalTimestampPlaceholder",
                        "verificationHint.warningDigest"
                );
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt()
                .readyForNodeV215DryRunAdapterCandidate()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().receiptWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConsume()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteLocalDryRunFiles())
                .isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConnectManagedAudit())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayCreateApprovalDecision())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteApprovalLedger())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayPersistApprovalRecord())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteSql()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerDeployment())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerRollback())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteRestore())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().javaManagedAuditWriteExecuted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionWindow()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterBoundaryReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForNodeV217ProductionHardeningReadinessGate()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .operatorIdentityPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourcePrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .ledgerHandoffPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .retentionOwnerPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .failureHandlingPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .rollbackReviewPrerequisiteDocumented()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaCreatesApprovalDecision()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaWritesApprovalLedger()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaWritesManagedAuditStore()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesSql())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaTriggersDeployment()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaTriggersRollback()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .javaExecutesRestore()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayConnectManagedAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayWriteApprovalLedger()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayExecuteSql()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayTriggerDeployment()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayTriggerRollback()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayExecuteRestore()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForProductionOperations()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt()
                .readyForNodeV219ImplementationPrecheck()).isTrue();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().receiptWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().nodeV219MayConsume()).isTrue();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().firstSafeSplitApplied()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().broadServiceSplitDeferred()).isTrue();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().apiShapeChanged()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().approvalDecisionCreated()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().approvalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().approvalRecordPersisted()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().managedAuditStoreWritten()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().sqlExecuted()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().deploymentTriggered()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().rollbackTriggered()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().restoreExecuted()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(headerBackedRehearsal.opsEvidenceServiceQualitySplitReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .readyForNodeV221LocalAdapterCandidateDryRun()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .consumedByNodeDisabledShellProfile())
                .isEqualTo("managed-audit-adapter-disabled-shell.v1");
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220SelectedAdapterDisabled()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220LocalDryRunOnlyDeclared()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().nodeV220AppendWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220ExternalManagedAuditAccessed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeV220LocalDryRunWritePerformed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalDecisionCreated())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalLedgerWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaApprovalRecordPersisted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaManagedAuditStoreWritten())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaSqlExecuted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaDeploymentTriggered())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaRollbackTriggered())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().javaRestoreExecuted())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionAudit())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().readyForProductionWindow())
                .isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForNodeV223ExternalAdapterConnectionReadinessReview()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .consumedByNodeVerificationReportProfile())
                .isEqualTo("managed-audit-local-adapter-candidate-verification-report.v1");
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ReadOnlyReport()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222SourceEndpointRerunPerformed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222AdditionalLocalDryRunWritePerformed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeV222ConnectsManagedAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .ownerApprovalRequiredBeforeConnection()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .schemaMigrationReviewRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .credentialReviewRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditConnectionOpened()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .externalManagedAuditSchemaMigrated()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .javaApprovalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .javaManagedAuditStoreWritten()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .javaSqlExecuted()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .readyForProductionWindow()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForNodeV225SandboxAdapterDryRunPackage()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .consumedByNodeSandboxPlanProfile())
                .isEqualTo("managed-audit-sandbox-adapter-dry-run-plan.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().readOnlyPlan()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeV224SandboxPlan().connectsManagedAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationExecutionAllowed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().sandboxCredentialHandleRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().externalManagedAuditConnectionOpened()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaManagedAuditStoreWritten()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .executionBoundary().javaSqlExecuted()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderClassName())
                .isEqualTo("ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder");
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().builderOrHelperSplitApplied()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .qualityGateBoundary().longBooleanConstructorAvoided()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForProductionAudit()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .readyForProductionWindow()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForNodeV229ManualSandboxConnectionPacketVerification()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .consumedByNodeOperatorPacketProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-operator-packet.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().manualSandboxConnectionWindowOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .sandboxConnectionWindowBoundary().connectionExecutionAllowed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().ownerApprovalArtifactIdFieldRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .operatorPacketBoundary().schemaRehearsalIdFieldRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialHandleNameRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .readyForNodeV231ManualSandboxConnectionPreflightVerification()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .consumedByNodePreflightGateProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-preflight-gate.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowFlagName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_MANUAL_SANDBOX_WINDOW_APPROVED");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowOpenByDefault()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().manualWindowOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .sandboxConnectionWindowBoundary().nodeAutoStartAllowed()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().allRequiredPreflightFieldsRecognizedByJava()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .preflightFieldBoundary().gateCreatesConnectionCommand()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker()
                .requiredPreflightFields())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
                        "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
                        "timeoutBudgetMs=15000",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT",
                        "ORDEROPS_MANAGED_AUDIT_MANUAL_SANDBOX_WINDOW_APPROVED"
                );
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .readyForNodeV235ManualSandboxConnectionPreconditionIntake()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .consumedByNodeBlockedExecutionRehearsalProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-blocked-execution-rehearsal.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary().ownerApprovalArtifactRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .credentialBoundary().credentialHandleReviewRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .rollbackPathBoundary().rollbackExecutionAllowedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForNodeV237ManualSandboxConnectionReadinessGate()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptWarnings())
                .isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-dry-run-request-envelope.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary().operatorReviewFieldsComplete()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary().credentialHandleOnly()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary().credentialValueIncludedInEnvelope()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptDigest())
                .startsWith("sha256:");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .readyForNodeV239ManualSandboxConnectionEvidenceVerification()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .receiptWarnings()).isEmpty();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .consumedByNodeOperatorWindowChecklistProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-operator-window-checklist.v1");
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary().operatorChecklistReadOnly()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalBoundary().allApprovalItemsRequired()).isTrue();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary().credentialValueReadByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(headerBackedRehearsal.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .receiptDigest()).startsWith("sha256:");
        assertThat(headerBackedRehearsal.failureTaxonomy().upstreamReadiness()).isEqualTo("READY");
        assertThat(headerBackedRehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("READY");
        assertThat(headerBackedRehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("READY");
        assertThat(headerBackedRehearsal.failureTaxonomy().authContextComplete()).isTrue();
        assertThat(headerBackedRehearsal.failureTaxonomy().auditCorrelationPresent()).isTrue();
        assertThat(headerBackedRehearsal.failureTaxonomy().failureCategories())
                .containsExactly("READ_ONLY_EXECUTION_BLOCKED");
        assertThat(headerBackedRehearsal.failureTaxonomy().taxonomyWarnings())
                .containsExactly("REHEARSAL_REMAINS_READ_ONLY");
        assertThat(headerBackedRehearsal.verificationHint().hintVersion())
                .isEqualTo("java-release-approval-rehearsal-verification-hint.v1");
        assertThat(headerBackedRehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v31");
        assertThat(headerBackedRehearsal.verificationHint().warningDigest()).startsWith("sha256:");
        assertThat(headerBackedRehearsal.verificationHint().warningDigest())
                .isNotEqualTo(rehearsal.verificationHint().warningDigest());
        ReleaseApprovalRehearsalResponse repeatedHeaderBackedRehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());
        assertThat(repeatedHeaderBackedRehearsal.verificationHint().warningDigest())
                .isEqualTo(headerBackedRehearsal.verificationHint().warningDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditAdapterImplementationGuardReceipt().guardDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditExternalAdapterMigrationGuardReceipt().guardDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest())
                .isEqualTo(
                        headerBackedRehearsal.managedAuditSandboxAdapterApprovalSchemaGuardReceipt().guardDigest()
                );
        assertThat(repeatedHeaderBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditSandboxConnectionOperatorHandoffMarker().markerDigest());
        assertThat(repeatedHeaderBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerDigest())
                .isEqualTo(headerBackedRehearsal.managedAuditSandboxConnectionPreflightEchoMarker().markerDigest());
        assertThat(headerBackedRehearsal.verificationHint().noLedgerWriteProved()).isTrue();
        assertThat(headerBackedRehearsal.verificationHint().nodeMayTreatAsProductionAuthorization()).isFalse();
        assertThat(headerBackedRehearsal.requestContext().operatorAuthenticatedByJava()).isFalse();
        assertThat(headerBackedRehearsal.requestContext().persistedByJava()).isFalse();
        assertThat(headerBackedRehearsal.requestContext().approvalLedgerWritten()).isFalse();
        assertThat(headerBackedRehearsal.executionAllowed()).isFalse();
        assertThat(headerBackedRehearsal.executionBoundaries().nodeMayWriteApprovalLedger()).isFalse();
    }

    @Test
    void releaseApprovalRehearsalExposesDryRunCommandPackageEchoReceipt() {
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                Instant.parse("2026-05-12T01:00:00Z"),
                Instant.parse("2026-05-12T01:05:00Z"),
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDryRunCommandPackageEchoReceipt
                receipt = rehearsal.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-command-package-echo-receipt.v1"
                );
        assertThat(receipt.sourceSandboxConnectionOperatorWindowChecklistEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v21");
        assertThat(receipt.consumedByNodeDryRunCommandPackageVersion()).isEqualTo("Node v241");
        assertThat(receipt.consumedByNodeDryRunCommandPackageProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-dry-run-command-package.v1");
        assertThat(receipt.nextNodeUpstreamEchoVerificationVersion()).isEqualTo("Node v244");
        assertThat(receipt.packageShape().commandCount()).isEqualTo(6);
        assertThat(receipt.packageShape().disabledByDefault()).isTrue();
        assertThat(receipt.packageShape().dryRunOnly()).isTrue();
        assertThat(receipt.fieldEcho().credentialHandleCommandId()).isEqualTo("verify-credential-handle");
        assertThat(receipt.fieldEcho().schemaRehearsalCommandId()).isEqualTo("review-schema-rehearsal");
        assertThat(receipt.fieldEcho().rollbackPathCommandId()).isEqualTo("review-rollback-path");
        assertThat(receipt.fieldEcho().timeoutBudgetCommandId()).isEqualTo("confirm-timeout-budget");
        assertThat(receipt.fieldEcho().manualAbortCommandId()).isEqualTo("confirm-manual-abort-marker");
        assertThat(receipt.fieldEcho().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(receipt.fieldEcho().credentialValueEchoed()).isFalse();
        assertThat(receipt.echoedCommandIds())
                .containsExactly(
                        "review-owner-approval-artifact",
                        "verify-credential-handle",
                        "review-schema-rehearsal",
                        "review-rollback-path",
                        "confirm-timeout-budget",
                        "confirm-manual-abort-marker"
                );
        assertThat(receipt.javaExecutionBoundary().carriesCredentialValue()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueReadByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStateWriteRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().upstreamServiceAutoStartRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().miniKvWritePermissionRequestedByJava()).isFalse();
        assertThat(receipt.readyForNodeV244ManualSandboxDryRunCommandUpstreamEchoVerification()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceiptWarnings",
                        "sandboxConnectionDryRunCommandPackageEchoReceiptDigest",
                        "sandboxConnectionDryRunCommandPackageApprovalLedgerWrittenByJava"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.packageShape.commandCount=6",
                        "managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.consumedByNodeDryRunCommandPackageProfile with Node v241",
                        "Require managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt.readyForNodeV244ManualSandboxDryRunCommandUpstreamEchoVerification=true before Node v244"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }

    @Test
    void releaseApprovalRehearsalExposesPrecheckPacketEchoReceipt() {
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                Instant.parse("2026-05-12T01:00:00Z"),
                Instant.parse("2026-05-12T01:05:00Z"),
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionPrecheckPacketEchoReceipt
                receipt = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-precheck-packet-echo-receipt.v1"
                );
        assertThat(receipt.sourceDryRunCommandPackageEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v22");
        assertThat(receipt.consumedByNodePrecheckPacketVersion()).isEqualTo("Node v245");
        assertThat(receipt.consumedByNodePrecheckPacketProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-precheck-packet.v1");
        assertThat(receipt.nextNodePrecheckUpstreamReceiptVerificationVersion()).isEqualTo("Node v246");
        assertThat(receipt.packetShape().precheckItemCount()).isEqualTo(7);
        assertThat(receipt.packetShape().disabledByDefault()).isTrue();
        assertThat(receipt.packetShape().dryRunOnly()).isTrue();
        assertThat(receipt.fieldEcho().ownerApprovalArtifactItemId()).isEqualTo("owner-approval-artifact");
        assertThat(receipt.fieldEcho().credentialHandleReviewItemId()).isEqualTo("credential-handle-review");
        assertThat(receipt.fieldEcho().schemaMigrationRehearsalItemId()).isEqualTo("schema-migration-rehearsal");
        assertThat(receipt.fieldEcho().operatorWindowItemId()).isEqualTo("operator-window");
        assertThat(receipt.fieldEcho().rollbackPathItemId()).isEqualTo("rollback-path");
        assertThat(receipt.fieldEcho().abortMarkerItemId()).isEqualTo("abort-marker");
        assertThat(receipt.fieldEcho().timeoutPolicyItemId()).isEqualTo("timeout-policy");
        assertThat(receipt.fieldEcho().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(receipt.fieldEcho().credentialValueEchoed()).isFalse();
        assertThat(receipt.echoedPrecheckItemIds())
                .containsExactly(
                        "owner-approval-artifact",
                        "credential-handle-review",
                        "schema-migration-rehearsal",
                        "operator-window",
                        "rollback-path",
                        "abort-marker",
                        "timeout-policy"
                );
        assertThat(receipt.javaExecutionBoundary().carriesCredentialValue()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueReadByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStateWriteRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().upstreamServiceAutoStartRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().miniKvWritePermissionRequestedByJava()).isFalse();
        assertThat(receipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionPrecheckPacketEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceiptWarnings",
                        "sandboxConnectionPrecheckPacketEchoReceiptDigest",
                        "sandboxConnectionPrecheckPacketApprovalLedgerWrittenByJava"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceipt.packetShape.precheckItemCount=7",
                        "managedAuditSandboxConnectionPrecheckPacketEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionPrecheckPacketEchoReceipt.consumedByNodePrecheckPacketProfile with Node v245",
                        "Require managedAuditSandboxConnectionPrecheckPacketEchoReceipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification=true before Node v246"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionPrecheckPacketEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }

    @Test
    void releaseApprovalRehearsalExposesDisabledAdapterClientPrecheckEchoReceipt() {
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                Instant.parse("2026-05-12T01:00:00Z"),
                Instant.parse("2026-05-12T01:05:00Z"),
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt
                receipt = rehearsal.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-disabled-adapter-client-precheck-echo-receipt.v1"
                );
        assertThat(receipt.sourcePrecheckPacketEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v23");
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckVersion()).isEqualTo("Node v252");
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-disabled-adapter-client-precheck.v1");
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-disabled-adapter-client-precheck"
                );
        assertThat(receipt.consumedByNodeDisabledAdapterClientPrecheckState())
                .isEqualTo("disabled-adapter-client-precheck-ready");
        assertThat(receipt.consumedByNodeTestOnlyAdapterShellContractVersion()).isEqualTo("Node v253");
        assertThat(receipt.consumedByNodeTestOnlyAdapterShellContractProfile())
                .isEqualTo("managed-audit-manual-sandbox-connection-test-only-adapter-shell-contract.v1");
        assertThat(receipt.nextNodeDisabledAdapterClientUpstreamEchoVerificationVersion())
                .isEqualTo("Node v254");
        assertThat(receipt.nodeV254MayConsume()).isTrue();
        assertThat(receipt.precheckShape().adapterMode()).isEqualTo("disabled-client-precheck-only");
        assertThat(receipt.precheckShape().precheckState())
                .isEqualTo("disabled-adapter-client-precheck-ready");
        assertThat(receipt.precheckShape().requiredEnvHandleCount()).isEqualTo(5);
        assertThat(receipt.precheckShape().failureClassCount()).isEqualTo(6);
        assertThat(receipt.precheckShape().dryRunResponseFieldCount()).isEqualTo(10);
        assertThat(receipt.precheckShape().envHandlesRemainHandleOnly()).isTrue();
        assertThat(receipt.precheckShape().noEnvValueReadForPrecheck()).isTrue();
        assertThat(receipt.precheckShape().dryRunResponseReadOnly()).isTrue();
        assertThat(receipt.precheckShape().precheckCreatesRealClient()).isFalse();
        assertThat(receipt.clientBoundary().clientImplementationStatus()).isEqualTo("not-implemented");
        assertThat(receipt.clientBoundary().clientMayBeInstantiated()).isFalse();
        assertThat(receipt.clientBoundary().externalRequestMayBeSent()).isFalse();
        assertThat(receipt.clientBoundary().credentialValueMayBeLoaded()).isFalse();
        assertThat(receipt.clientBoundary().optInGateRequired()).isTrue();
        assertThat(receipt.clientBoundary().productionEndpointAllowed()).isFalse();
        assertThat(receipt.clientBoundary().realTransportAllowed()).isFalse();
        assertThat(receipt.clientBoundary().realAdapterClientImplemented()).isFalse();
        assertThat(receipt.optInGate().gateName())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_ADAPTER_CLIENT_ENABLED");
        assertThat(receipt.optInGate().requiredValueForFutureConnection()).isEqualTo("true");
        assertThat(receipt.optInGate().currentDefault()).isEqualTo("false");
        assertThat(receipt.optInGate().precheckTreatsEnabledAsBlocked()).isTrue();
        assertThat(receipt.optInGate().operatorApprovalRequired()).isTrue();
        assertThat(receipt.javaExecutionBoundary().carriesCredentialValue()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueReadByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().credentialValueStoredByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().externalManagedAuditConnectionOpenedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().externalRequestSentByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().schemaMigrationSqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().approvalLedgerWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStateWriteRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().managedAuditStoreWrittenByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().sqlExecutedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().upstreamServiceAutoStartRequestedByJava()).isFalse();
        assertThat(receipt.javaExecutionBoundary().miniKvWritePermissionRequestedByJava()).isFalse();
        assertThat(receipt.echoedRequiredEnvHandles())
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_ADAPTER_CLIENT_ENABLED",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
                        "ORDEROPS_MANAGED_AUDIT_TIMEOUT_BUDGET_MS"
                );
        assertThat(receipt.echoedFailureClassCodes())
                .containsExactly(
                        "ADAPTER_CLIENT_DISABLED",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "ENDPOINT_HANDLE_MISSING",
                        "SCHEMA_REHEARSAL_MISSING",
                        "MANUAL_WINDOW_NOT_OPEN"
                );
        assertThat(receipt.echoedDryRunResponseFields())
                .contains(
                        "connectionAttempted",
                        "credentialValueRead",
                        "externalRequestSent",
                        "schemaMigrationExecuted"
                );
        assertThat(receipt.reusedNoGoConditions())
                .contains(
                        "CREDENTIAL_VALUE_REQUIRED",
                        "APPROVAL_LEDGER_WRITE_REQUIRED",
                        "MINI_KV_STORAGE_BACKEND_REQUIRED"
                );
        assertThat(receipt.forbiddenPrecheckOperations())
                .contains(
                        "instantiate managed audit adapter client",
                        "read credential value",
                        "send external managed audit request",
                        "write approval ledger"
                );
        assertThat(receipt.envHandlesEchoed()).isTrue();
        assertThat(receipt.failureTaxonomyEchoed()).isTrue();
        assertThat(receipt.dryRunResponseShapeEchoed()).isTrue();
        assertThat(receipt.disabledClientBoundaryEchoed()).isTrue();
        assertThat(receipt.readOnlyPrecheckBoundaryEchoed()).isTrue();
        assertThat(receipt.readyForNodeV254DisabledAdapterClientUpstreamEchoVerification()).isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(receipt.nodeV254Prerequisites())
                .contains(
                        "Java v102 disabled adapter client precheck echo receipt is present",
                        "mini-kv v111 non-participation receipt is present",
                        "UPSTREAM_ACTIONS_ENABLED remains false"
                );
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceiptWarnings",
                        "sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptDigest",
                        "sandboxConnectionDisabledAdapterClientPrecheckApprovalLedgerWrittenByJava"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.precheckShape.requiredEnvHandleCount=5",
                        "managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.consumedByNodeDisabledAdapterClientPrecheckProfile with Node v252",
                        "Require managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt.readyForNodeV254DisabledAdapterClientUpstreamEchoVerification=true before Node v254"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }

    @Test
    void releaseApprovalRehearsalExposesFakeTransportDryRunPacketEchoMarker() {
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                Instant.parse("2026-05-12T01:00:00Z"),
                Instant.parse("2026-05-12T01:05:00Z"),
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker
                marker = rehearsal.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-connection-fake-transport-dry-run-packet-echo-marker.v1"
                );
        assertThat(marker.sourceDisabledAdapterClientPrecheckEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v24");
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketVersion()).isEqualTo("Node v255");
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-adapter-dry-run-verification-packet.v1"
                );
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-fake-transport-adapter-dry-run-verification-packet"
                );
        assertThat(marker.consumedByNodeFakeTransportDryRunPacketState())
                .isEqualTo("fake-transport-adapter-dry-run-verification-packet-ready");
        assertThat(marker.consumedByNodeFakeTransportPacketArchiveVerificationVersion()).isEqualTo("Node v256");
        assertThat(marker.consumedByNodeFakeTransportPacketArchiveVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-packet-archive-verification.v1"
                );
        assertThat(marker.consumedByNodeFakeTransportPacketArchiveVerificationState())
                .isEqualTo("fake-transport-packet-archive-verification-ready");
        assertThat(marker.nextNodeFakeTransportPacketUpstreamEchoVerificationVersion()).isEqualTo("Node v257");
        assertThat(marker.nextNodeFakeTransportPacketUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-packet-upstream-echo-verification.v1"
                );
        assertThat(marker.nodeV257MayConsume()).isTrue();
        assertThat(marker.packetMode()).isEqualTo("fake-transport-adapter-dry-run-verification-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v253 + Node v254 + Node v255 + Node v256");
        assertThat(marker.requestShape().requestId()).isEqualTo("managed-audit-v255-fake-transport-dry-run");
        assertThat(marker.requestShape().transportKind()).isEqualTo("fake-in-memory");
        assertThat(marker.requestShape().credentialHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(marker.requestShape().endpointHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE");
        assertThat(marker.requestShape().ownerApprovalArtifactId())
                .isEqualTo("owner-approval-artifact-review-only");
        assertThat(marker.requestShape().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(marker.requestShape().dryRun()).isTrue();
        assertThat(marker.requestShape().fakeTransportOnly()).isTrue();
        assertThat(marker.requestShape().credentialValueIncluded()).isFalse();
        assertThat(marker.requestShape().rawEndpointUrlIncluded()).isFalse();
        assertThat(marker.requestShape().payloadMayContainSecrets()).isFalse();
        assertThat(marker.requestShape().requestShapeFieldCount()).isEqualTo(8);
        assertThat(marker.responseShape().status()).isEqualTo("fake-transport-dry-run-accepted");
        assertThat(marker.responseShape().code()).isEqualTo("TEST_ONLY_FAKE_TRANSPORT_DRY_RUN");
        assertThat(marker.responseShape().fakeTransportOnly()).isTrue();
        assertThat(marker.responseShape().timeoutBudgetMs()).isEqualTo(15000);
        assertThat(marker.responseShape().connectionAttempted()).isFalse();
        assertThat(marker.responseShape().externalRequestSent()).isFalse();
        assertThat(marker.responseShape().credentialValueRead()).isFalse();
        assertThat(marker.responseShape().schemaMigrationExecuted()).isFalse();
        assertThat(marker.responseShape().productionRecordWritten()).isFalse();
        assertThat(marker.responseShape().responseShapeFieldCount()).isEqualTo(9);
        assertThat(marker.timeoutBoundary().finiteBudget()).isTrue();
        assertThat(marker.timeoutBoundary().budgetSource()).isEqualTo("operator-review-field");
        assertThat(marker.timeoutBoundary().budgetSpent()).isFalse();
        assertThat(marker.timeoutBoundary().timerStarted()).isFalse();
        assertThat(marker.timeoutBoundary().timeoutClassifiable()).isTrue();
        assertThat(marker.failureMappingShape().sourceFailureMappingCount()).isEqualTo(6);
        assertThat(marker.failureMappingShape().mappedFailureCount()).isEqualTo(6);
        assertThat(marker.failureMappingShape().guardConditionCount()).isEqualTo(7);
        assertThat(marker.failureMappingShape().allFailuresNonRetryable()).isTrue();
        assertThat(marker.failureMappingShape().credentialValueRequestStillBlocked()).isTrue();
        assertThat(marker.failureMappingShape().manualWindowClosedStillBlocked()).isTrue();
        assertThat(marker.failureMappingShape().failureMappingCovered()).isTrue();
        assertThat(marker.cleanupBoundary().inMemoryOnly()).isTrue();
        assertThat(marker.cleanupBoundary().temporaryDirectoryCreated()).isFalse();
        assertThat(marker.cleanupBoundary().temporaryFileCreated()).isFalse();
        assertThat(marker.cleanupBoundary().cleanupRequired()).isFalse();
        assertThat(marker.cleanupBoundary().cleanupArtifactCount()).isEqualTo(0);
        assertThat(marker.cleanupBoundary().cleanupVerified()).isTrue();
        assertThat(marker.cleanupBoundary().nodeServiceStartedByPacket()).isFalse();
        assertThat(marker.sideEffectBoundary().connectionAttempted()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueStored()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().productionRecordWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().managedAuditStateWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().javaStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().miniKvStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().externalAuditServiceStarted()).isFalse();
        assertThat(marker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification()).isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(marker.echoedRequestFieldNames())
                .contains("credentialValueIncluded", "rawEndpointUrlIncluded", "payloadMayContainSecrets");
        assertThat(marker.echoedResponseFieldNames())
                .contains(
                        "connectionAttempted",
                        "externalRequestSent",
                        "credentialValueRead",
                        "schemaMigrationExecuted",
                        "productionRecordWritten"
                );
        assertThat(marker.echoedFailureMappingCodes())
                .containsExactly(
                        "ADAPTER_CLIENT_DISABLED",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "ENDPOINT_HANDLE_MISSING",
                        "SCHEMA_REHEARSAL_MISSING",
                        "MANUAL_WINDOW_NOT_OPEN"
                );
        assertThat(marker.forbiddenFakeTransportOperations())
                .contains(
                        "instantiate real managed audit adapter client",
                        "include raw endpoint URL",
                        "create temporary dry-run directory or file"
                );
        assertThat(marker.nodeV257Prerequisites())
                .contains(
                        "Java v103 fake transport dry-run packet echo marker is present",
                        "mini-kv v112 fake transport dry-run packet non-participation receipt is present",
                        "UPSTREAM_ACTIONS_ENABLED remains false"
                );
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarkerWarnings",
                        "sandboxConnectionFakeTransportDryRunPacketEchoMarkerDigest",
                        "sandboxConnectionFakeTransportDryRunPacketCleanupArtifactCount"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.requestShape.requestShapeFieldCount=8",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.responseShape.responseShapeFieldCount=9",
                        "managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.cleanupBoundary.cleanupArtifactCount=0"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.consumedByNodeFakeTransportDryRunPacketProfile with Node v255",
                        "Require managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker.readyForNodeV257FakeTransportPacketUpstreamEchoVerification=true before Node v257"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointHandlePreflightEchoMarker() {
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                Instant.parse("2026-05-12T01:00:00Z"),
                Instant.parse("2026-05-12T01:05:00Z"),
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxEndpointHandlePreflightEchoMarker
                marker = rehearsal.managedAuditSandboxEndpointHandlePreflightEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-handle-preflight-echo-marker.v1"
                );
        assertThat(marker.sourceFakeTransportDryRunPacketEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v25");
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewVersion()).isEqualTo("Node v258");
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review"
                );
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-preflight-review?format=markdown"
                );
        assertThat(marker.consumedByNodeSandboxEndpointHandlePreflightReviewState())
                .isEqualTo("sandbox-endpoint-handle-preflight-review-ready");
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationVersion()).isEqualTo("Node v257");
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-fake-transport-packet-upstream-echo-verification.v1"
                );
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-fake-transport-packet-upstream-echo-verification"
                );
        assertThat(marker.sourceNodeFakeTransportPacketUpstreamEchoVerificationState())
                .isEqualTo("fake-transport-packet-upstream-echo-verification-ready");
        assertThat(marker.nextNodeSandboxEndpointHandleUpstreamEchoVerificationVersion()).isEqualTo("Node v259");
        assertThat(marker.nextNodeSandboxEndpointHandleUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-upstream-echo-verification.v1"
                );
        assertThat(marker.nodeV259MayConsume()).isTrue();
        assertThat(marker.reviewMode()).isEqualTo("sandbox-endpoint-handle-preflight-review-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v257");
        assertThat(marker.sourceNodeV257().readyForUpstreamEchoVerification()).isTrue();
        assertThat(marker.sourceNodeV257().requestShapeAligned()).isTrue();
        assertThat(marker.sourceNodeV257().responseShapeAligned()).isTrue();
        assertThat(marker.sourceNodeV257().timeoutBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().failureMappingAligned()).isTrue();
        assertThat(marker.sourceNodeV257().cleanupBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().archiveNoRerunAligned()).isTrue();
        assertThat(marker.sourceNodeV257().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV257().upstreamActionsStillDisabled()).isTrue();
        assertThat(marker.sourceNodeV257().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.sourceNodeV257().connectsManagedAudit()).isFalse();
        assertThat(marker.sourceNodeV257().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sourceNodeV257().storesManagedAuditCredential()).isFalse();
        assertThat(marker.sourceNodeV257().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sourceNodeV257().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV257().evidenceFileCount()).isEqualTo(6);
        assertThat(marker.sourceNodeV257().matchedSnippetCount()).isEqualTo(33);
        assertThat(marker.sourceNodeV257().readyForNodeV258PreflightReview()).isTrue();
        assertThat(marker.preflightReview().endpointHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE");
        assertThat(marker.preflightReview().credentialHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(marker.preflightReview().ownerApprovalArtifactId())
                .isEqualTo("owner-approval-artifact-review-only");
        assertThat(marker.preflightReview().schemaRehearsalId())
                .isEqualTo("schema-migration-rehearsal-review-only");
        assertThat(marker.preflightReview().operatorWindowMarker())
                .isEqualTo("manual-sandbox-endpoint-window-review-only");
        assertThat(marker.preflightReview().requiredReviewItemCount()).isEqualTo(7);
        assertThat(marker.preflightReview().completedReviewItemCount()).isEqualTo(7);
        assertThat(marker.preflightReview().forbiddenOperationCount()).isEqualTo(7);
        assertThat(marker.preflightReview().readOnlyPreflightReview()).isTrue();
        assertThat(marker.preflightReview().endpointHandleOnly()).isTrue();
        assertThat(marker.preflightReview().credentialHandleOnly()).isTrue();
        assertThat(marker.networkAllowlistReview().allowlistHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_NETWORK_ALLOWLIST_HANDLE");
        assertThat(marker.networkAllowlistReview().rawHostIncluded()).isFalse();
        assertThat(marker.networkAllowlistReview().cidrIncluded()).isFalse();
        assertThat(marker.networkAllowlistReview().reviewed()).isTrue();
        assertThat(marker.tlsPolicyReview().policyHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_TLS_POLICY_HANDLE");
        assertThat(marker.tlsPolicyReview().certificateMaterialIncluded()).isFalse();
        assertThat(marker.tlsPolicyReview().privateKeyIncluded()).isFalse();
        assertThat(marker.tlsPolicyReview().reviewed()).isTrue();
        assertThat(marker.redactionPolicy().policyHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_REDACTION_POLICY_HANDLE");
        assertThat(marker.redactionPolicy().credentialValueRedacted()).isTrue();
        assertThat(marker.redactionPolicy().rawEndpointUrlRedacted()).isTrue();
        assertThat(marker.redactionPolicy().payloadSecretRedacted()).isTrue();
        assertThat(marker.redactionPolicy().reviewed()).isTrue();
        assertThat(marker.operatorWindow().manualWindowRequired()).isTrue();
        assertThat(marker.operatorWindow().windowOpen()).isFalse();
        assertThat(marker.operatorWindow().executionBlockedUntilWindowOpen()).isTrue();
        assertThat(marker.operatorWindow().operatorIdentityRequired()).isTrue();
        assertThat(marker.operatorWindow().approvalCorrelationRequired()).isTrue();
        assertThat(marker.operatorWindow().reviewed()).isTrue();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlIncluded()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().storesManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().executionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().javaStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().miniKvStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().externalAuditServiceStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().productionAuditAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().productionWindowAllowed()).isFalse();
        assertThat(marker.sourceNodeV257Echoed()).isTrue();
        assertThat(marker.endpointHandleReviewEchoed()).isTrue();
        assertThat(marker.credentialHandleReviewEchoed()).isTrue();
        assertThat(marker.ownerApprovalArtifactReviewEchoed()).isTrue();
        assertThat(marker.networkAllowlistReviewEchoed()).isTrue();
        assertThat(marker.tlsPolicyReviewEchoed()).isTrue();
        assertThat(marker.redactionPolicyEchoed()).isTrue();
        assertThat(marker.operatorWindowReviewEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification()).isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.requiredReviewItems())
                .containsExactly(
                        "endpoint handle review",
                        "credential handle review",
                        "owner approval artifact review",
                        "network allowlist review",
                        "TLS policy review",
                        "redaction policy review",
                        "operator window review"
                );
        assertThat(marker.forbiddenOperations())
                .contains(
                        "read credential value",
                        "parse raw endpoint URL",
                        "send real managed audit request",
                        "write approval ledger",
                        "start Java or mini-kv"
                );
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v104 sandbox endpoint handle preflight echo marker",
                        "mini-kv v113 sandbox endpoint handle non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointHandlePreflightEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointHandlePreflightEchoMarkerWarnings",
                        "sandboxEndpointHandlePreflightEchoMarkerDigest",
                        "sandboxEndpointHandlePreflightRawEndpointUrlParsed"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker.preflightReview.requiredReviewItemCount=7",
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.rawEndpointUrlParsed=false",
                        "managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointHandlePreflightEchoMarker.consumedByNodeSandboxEndpointHandlePreflightReviewProfile with Node v258",
                        "Require managedAuditSandboxEndpointHandlePreflightEchoMarker.readyForNodeV259SandboxEndpointHandleUpstreamEchoVerification=true before Node v259",
                        "Keep managedAuditSandboxEndpointHandlePreflightEchoMarker.sideEffectBoundary.externalRequestSent=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointHandlePreflightEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

    @Test
    void releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDecisionEchoMarker() {
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                Instant.parse("2026-05-12T01:00:00Z"),
                Instant.parse("2026-05-12T01:05:00Z"),
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarker marker =
                rehearsal.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-decision-echo-marker.v1"
                );
        assertThat(marker.sourceEndpointHandlePreflightEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v26");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordVersion())
                .isEqualTo("Node v260");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-decision-record?format=markdown"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordState())
                .isEqualTo("sandbox-endpoint-credential-resolver-decision-record-ready");
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationVersion())
                .isEqualTo("Node v259");
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-upstream-echo-verification.v1"
                );
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-handle-upstream-echo-verification"
                );
        assertThat(marker.sourceNodeSandboxEndpointHandleUpstreamEchoVerificationState())
                .isEqualTo("sandbox-endpoint-handle-upstream-echo-verification-ready");
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationVersion())
                .isEqualTo("Node v261");
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-upstream-echo-verification.v1"
                );
        assertThat(marker.nodeV261MayConsume()).isTrue();
        assertThat(marker.recordMode()).isEqualTo("sandbox-endpoint-credential-resolver-decision-record-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v259 sandbox endpoint handle upstream echo verification");
        assertThat(marker.sourceNodeV259().sourceVersion()).isEqualTo("Node v259");
        assertThat(marker.sourceNodeV259().verificationState())
                .isEqualTo("sandbox-endpoint-handle-upstream-echo-verification-ready");
        assertThat(marker.sourceNodeV259().endpointHandleAligned()).isTrue();
        assertThat(marker.sourceNodeV259().credentialHandleAligned()).isTrue();
        assertThat(marker.sourceNodeV259().reviewCountsAligned()).isTrue();
        assertThat(marker.sourceNodeV259().policyReviewsAligned()).isTrue();
        assertThat(marker.sourceNodeV259().operatorWindowAligned()).isTrue();
        assertThat(marker.sourceNodeV259().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().rawEndpointBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV259().miniKvNonParticipationAligned()).isTrue();
        assertThat(marker.sourceNodeV259().nodeV259BlocksRealConnection()).isTrue();
        assertThat(marker.sourceNodeV259().evidenceFileCount()).isEqualTo(6);
        assertThat(marker.sourceNodeV259().matchedSnippetCount()).isEqualTo(39);
        assertThat(marker.sourceNodeV259().checkCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV259().passedCheckCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV259().productionBlockerCount()).isZero();
        assertThat(marker.sourceNodeV259().warningCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV259().recommendationCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV259().sourceNodeV258Ready()).isTrue();
        assertThat(marker.sourceNodeV259().javaV104Ready()).isTrue();
        assertThat(marker.sourceNodeV259().miniKvV113Ready()).isTrue();
        assertThat(marker.sourceNodeV259().readyForNodeV260CredentialResolverDecisionRecord()).isTrue();
        assertThat(marker.decisionRecord().decisionDigest()).startsWith("sha256:");
        assertThat(marker.decisionRecord().recordMode())
                .isEqualTo("sandbox-endpoint-credential-resolver-decision-record-only");
        assertThat(marker.decisionRecord().decisionScope())
                .isEqualTo("managed-audit-sandbox-endpoint-credential-resolver");
        assertThat(marker.decisionRecord().decisionStatus())
                .isEqualTo("human-review-required-before-credential-resolution");
        assertThat(marker.decisionRecord().endpointHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE");
        assertThat(marker.decisionRecord().credentialHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
        assertThat(marker.decisionRecord().resolverPolicyHandle())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE");
        assertThat(marker.decisionRecord().approvalMarker())
                .isEqualTo("ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER");
        assertThat(marker.decisionRecord().operatorIdentityRequired()).isTrue();
        assertThat(marker.decisionRecord().approvalCorrelationRequired()).isTrue();
        assertThat(marker.decisionRecord().resolverMode()).isEqualTo("policy-record-only-no-value-read");
        assertThat(marker.decisionRecord().resolverCandidateImplementation()).isEqualTo("not-implemented");
        assertThat(marker.decisionRecord().requiredDecisionFieldCount()).isEqualTo(8);
        assertThat(marker.decisionRecord().explicitNoGoConditionCount()).isEqualTo(9);
        assertThat(marker.decisionRecord().requiredDecisionFields())
                .extracting(ReleaseApprovalRehearsalResponseRecords.RehearsalSandboxEndpointCredentialResolverDecisionField::id)
                .containsExactly(
                        "endpoint-handle",
                        "credential-handle",
                        "resolver-policy-handle",
                        "approval-marker",
                        "operator-identity",
                        "approval-correlation",
                        "redaction-policy",
                        "fallback-rotation-plan"
                );
        assertThat(marker.decisionRecord().requiredDecisionFields())
                .allMatch(field -> field.required() && !field.nodeMayReadValue());
        assertThat(marker.decisionRecord().explicitNoGoConditions())
                .extracting(ReleaseApprovalRehearsalResponseRecords.RehearsalSandboxEndpointCredentialResolverNoGoCondition::code)
                .containsExactly(
                        "CREDENTIAL_VALUE_REQUIRED",
                        "RAW_ENDPOINT_URL_REQUIRED",
                        "REAL_CONNECTION_REQUIRED",
                        "EXTERNAL_REQUEST_REQUIRED",
                        "SCHEMA_MIGRATION_REQUIRED",
                        "UPSTREAM_WRITE_REQUIRED",
                        "AUTO_START_REQUIRED",
                        "MINI_KV_BACKEND_REQUIRED",
                        "PRODUCTION_WINDOW_REQUIRED"
                );
        assertThat(marker.decisionRecord().explicitNoGoConditions())
                .allMatch(noGoCondition -> !noGoCondition.allowed());
        assertThat(marker.decisionRecord().credentialValueMayBeRead()).isFalse();
        assertThat(marker.decisionRecord().credentialValueMayBeLoaded()).isFalse();
        assertThat(marker.decisionRecord().credentialValueMayBeStored()).isFalse();
        assertThat(marker.decisionRecord().rawEndpointUrlMayBeParsed()).isFalse();
        assertThat(marker.decisionRecord().managedAuditConnectionMayOpen()).isFalse();
        assertThat(marker.decisionRecord().schemaMigrationMayExecute()).isFalse();
        assertThat(marker.decisionRecord().externalRequestMayBeSent()).isFalse();
        assertThat(marker.decisionRecord().nodeMayStartJavaOrMiniKv()).isFalse();
        assertThat(marker.decisionRecord().miniKvMayActAsManagedAuditStorage()).isFalse();
        assertThat(marker.decisionRecord().approvalLedgerMayBeWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().readOnlyDecisionRecord()).isTrue();
        assertThat(marker.sideEffectBoundary().credentialResolverDecisionOnly()).isTrue();
        assertThat(marker.sideEffectBoundary().executionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueLoaded()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(marker.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(marker.sideEffectBoundary().javaStarted()).isFalse();
        assertThat(marker.sideEffectBoundary().miniKvStarted()).isFalse();
        assertThat(marker.sourceNodeV259Echoed()).isTrue();
        assertThat(marker.decisionFieldsEchoed()).isTrue();
        assertThat(marker.endpointHandleEchoed()).isTrue();
        assertThat(marker.credentialHandleEchoed()).isTrue();
        assertThat(marker.resolverPolicyEchoed()).isTrue();
        assertThat(marker.approvalMarkerEchoed()).isTrue();
        assertThat(marker.operatorIdentityRequirementEchoed()).isTrue();
        assertThat(marker.approvalCorrelationRequirementEchoed()).isTrue();
        assertThat(marker.redactionPolicyEchoed()).isTrue();
        assertThat(marker.fallbackRotationPlanEchoed()).isTrue();
        assertThat(marker.explicitNoGoConditionsEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification()).isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.requiredDecisionFieldIds()).containsExactlyElementsOf(
                marker.decisionRecord().requiredDecisionFields().stream()
                        .map(ReleaseApprovalRehearsalResponseRecords.RehearsalSandboxEndpointCredentialResolverDecisionField::id)
                        .toList()
        );
        assertThat(marker.explicitNoGoConditionCodes()).containsExactlyElementsOf(
                marker.decisionRecord().explicitNoGoConditions().stream()
                        .map(ReleaseApprovalRehearsalResponseRecords.RehearsalSandboxEndpointCredentialResolverNoGoCondition::code)
                        .toList()
        );
        assertThat(marker.nodeWarningCodes())
                .containsExactly("DECISION_RECORD_ONLY", "REAL_CREDENTIAL_STILL_ABSENT");
        assertThat(marker.nodeRecommendationCodes())
                .containsExactly("START_POST_V260_PLAN", "DESIGN_DISABLED_RESOLVER_PRECHECK_LATER");
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v105 sandbox endpoint credential resolver decision echo marker",
                        "mini-kv v114 sandbox endpoint credential resolver non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerWarnings",
                        "sandboxEndpointCredentialResolverDecisionEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverDecisionRawEndpointUrlMayBeParsed"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.requiredDecisionFieldCount=8",
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.decisionRecord.credentialValueMayBeRead=false",
                        "managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDecisionRecordProfile with Node v260",
                        "Require managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.readyForNodeV261SandboxEndpointCredentialResolverUpstreamEchoVerification=true before Node v261",
                        "Keep managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker.sideEffectBoundary.externalRequestSent=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker() {
        Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
        Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker marker =
                rehearsal.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-disabled-precheck-echo-marker.v1"
                );
        assertThat(marker.sourceCredentialResolverDecisionEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v27");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckVersion())
                .isEqualTo("Node v262");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckMarkdownEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-disabled-precheck?format=markdown"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckState())
                .isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-ready");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationVersion())
                .isEqualTo("Node v261");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-upstream-echo-verification.v1"
                );
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverUpstreamEchoVerificationState())
                .isEqualTo("sandbox-endpoint-credential-resolver-upstream-echo-verification-ready");
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationVersion())
                .isEqualTo("Node v263");
        assertThat(marker.nodeV263MayConsume()).isTrue();
        assertThat(marker.precheckMode()).isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-only");
        assertThat(marker.sourceSpan()).isEqualTo("Node v261 credential resolver upstream echo verification");
        assertThat(marker.sourceNodeV261().sourceVersion()).isEqualTo("Node v261");
        assertThat(marker.sourceNodeV261().verificationMode())
                .isEqualTo("java-v105-plus-mini-kv-v114-credential-resolver-upstream-echo-verification-only");
        assertThat(marker.sourceNodeV261().sourceSpan()).isEqualTo("Node v260 + Java v105 + mini-kv v114");
        assertThat(marker.sourceNodeV261().sourceNodeV260Ready()).isTrue();
        assertThat(marker.sourceNodeV261().javaV105EchoReady()).isTrue();
        assertThat(marker.sourceNodeV261().miniKvV114NonParticipationReady()).isTrue();
        assertThat(marker.sourceNodeV261().decisionRecordAligned()).isTrue();
        assertThat(marker.sourceNodeV261().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().rawEndpointBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV261().upstreamActionsStillDisabled()).isTrue();
        assertThat(marker.sourceNodeV261().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sourceNodeV261().credentialValueRead()).isFalse();
        assertThat(marker.sourceNodeV261().credentialValueLoaded()).isFalse();
        assertThat(marker.sourceNodeV261().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sourceNodeV261().externalRequestSent()).isFalse();
        assertThat(marker.sourceNodeV261().connectsManagedAudit()).isFalse();
        assertThat(marker.sourceNodeV261().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sourceNodeV261().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV261().checkCount()).isEqualTo(20);
        assertThat(marker.sourceNodeV261().passedCheckCount()).isEqualTo(20);
        assertThat(marker.sourceNodeV261().productionBlockerCount()).isZero();
        assertThat(marker.sourceNodeV261().warningCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV261().recommendationCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV261().readyForNodeV262CredentialResolverDisabledPrecheck()).isTrue();
        assertThat(marker.disabledPrecheck().precheckDigest()).startsWith("sha256:");
        assertThat(marker.disabledPrecheck().precheckMode())
                .isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-only");
        assertThat(marker.disabledPrecheck().resolverImplementationStatus()).isEqualTo("not-implemented");
        assertThat(marker.disabledPrecheck().secretProviderImplementationStatus()).isEqualTo("not-implemented");
        assertThat(marker.disabledPrecheck().resolverClientMayBeInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().secretProviderMayBeInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().credentialValueMayBeLoaded()).isFalse();
        assertThat(marker.disabledPrecheck().rawEndpointUrlMayBeParsed()).isFalse();
        assertThat(marker.disabledPrecheck().externalRequestMayBeSent()).isFalse();
        assertThat(marker.disabledPrecheck().optInGateRequired()).isTrue();
        assertThat(marker.disabledPrecheck().requiredEnvHandleCount()).isEqualTo(6);
        assertThat(marker.disabledPrecheck().optInGateCount()).isEqualTo(2);
        assertThat(marker.disabledPrecheck().failureClassCount()).isEqualTo(7);
        assertThat(marker.disabledPrecheck().dryRunResponseFieldCount()).isEqualTo(12);
        assertThat(marker.disabledPrecheck().inheritedNoGoConditionCount()).isEqualTo(9);
        assertThat(marker.disabledPrecheck().requiredEnvHandles())
                .extracting(RehearsalSandboxEndpointCredentialResolverEnvHandle::name)
                .containsExactly(
                        "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_ENABLED",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_RESOLUTION_ENABLED",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_ENDPOINT_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_RESOLVER_POLICY_HANDLE",
                        "ORDEROPS_MANAGED_AUDIT_CREDENTIAL_RESOLVER_APPROVAL_MARKER"
                );
        assertThat(marker.disabledPrecheck().requiredEnvHandles())
                .allMatch(handle -> !handle.valueRequiredForPrecheck()
                        && !handle.credentialValue()
                        && !handle.rawEndpointValue());
        assertThat(marker.disabledPrecheck().optInGates())
                .allMatch(gate -> "true".equals(gate.requiredValueForFutureResolver())
                        && "false".equals(gate.currentDefault())
                        && gate.precheckTreatsEnabledAsBlocked()
                        && gate.operatorApprovalRequired());
        assertThat(marker.disabledPrecheck().failureTaxonomy())
                .extracting(RehearsalSandboxEndpointCredentialResolverFailureClass::code)
                .containsExactly(
                        "RESOLVER_DISABLED",
                        "APPROVAL_MARKER_MISSING",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "RAW_ENDPOINT_URL_REQUESTED",
                        "EXTERNAL_REQUEST_REQUESTED",
                        "SCHEMA_MIGRATION_REQUESTED"
                );
        assertThat(marker.disabledPrecheck().dryRunResponseShape().fields())
                .containsExactly(
                        "readyState",
                        "resolverMode",
                        "resolverClientInstantiated",
                        "secretProviderInstantiated",
                        "credentialValueRead",
                        "credentialValueLoaded",
                        "rawEndpointUrlParsed",
                        "externalRequestSent",
                        "connectsManagedAudit",
                        "schemaMigrationExecuted",
                        "failureClassCount",
                        "nextAction"
                );
        assertThat(marker.disabledPrecheck().dryRunResponseShape().readyState())
                .isEqualTo("sandbox-endpoint-credential-resolver-disabled-precheck-ready");
        assertThat(marker.disabledPrecheck().dryRunResponseShape().resolverClientInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().secretProviderInstantiated()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().credentialValueRead()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().credentialValueLoaded()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().externalRequestSent()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().connectsManagedAudit()).isFalse();
        assertThat(marker.disabledPrecheck().dryRunResponseShape().schemaMigrationExecuted()).isFalse();
        assertThat(marker.disabledPrecheck().inheritedNoGoConditions())
                .containsExactly(
                        "CREDENTIAL_VALUE_REQUIRED",
                        "RAW_ENDPOINT_URL_REQUIRED",
                        "REAL_CONNECTION_REQUIRED",
                        "EXTERNAL_REQUEST_REQUIRED",
                        "SCHEMA_MIGRATION_REQUIRED",
                        "UPSTREAM_WRITE_REQUIRED",
                        "AUTO_START_REQUIRED",
                        "MINI_KV_BACKEND_REQUIRED",
                        "PRODUCTION_WINDOW_REQUIRED"
                );
        assertThat(marker.sideEffectBoundary().readOnlyDisabledPrecheck()).isTrue();
        assertThat(marker.sideEffectBoundary().disabledCredentialResolverPrecheckOnly()).isTrue();
        assertThat(marker.sideEffectBoundary().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueLoaded()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(marker.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(marker.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV261Echoed()).isTrue();
        assertThat(marker.envHandlesEchoed()).isTrue();
        assertThat(marker.optInGatesEchoed()).isTrue();
        assertThat(marker.failureTaxonomyEchoed()).isTrue();
        assertThat(marker.dryRunResponseShapeEchoed()).isTrue();
        assertThat(marker.inheritedNoGoConditionsEchoed()).isTrue();
        assertThat(marker.resolverImplementationAbsentEchoed()).isTrue();
        assertThat(marker.secretProviderAbsentEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.upstreamActionsStillDisabledEchoed()).isTrue();
        assertThat(marker.readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification())
                .isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.nodeWarningCodes())
                .containsExactly("DISABLED_PRECHECK_ONLY", "UPSTREAM_ECHO_REQUIRED_NEXT");
        assertThat(marker.nodeRecommendationCodes())
                .containsExactly("ASK_JAVA_MINI_KV_FOR_ECHO_NEXT", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v106 sandbox endpoint credential resolver disabled precheck echo marker",
                        "mini-kv v115 sandbox endpoint credential resolver disabled precheck non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerWarnings",
                        "sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverDisabledPrecheckRawEndpointUrlMayBeParsed"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.requiredEnvHandleCount=6",
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.disabledPrecheck.resolverClientMayBeInstantiated=false",
                        "managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.consumedByNodeSandboxEndpointCredentialResolverDisabledPrecheckProfile with Node v262",
                        "Require managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.readyForNodeV263SandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerification=true before Node v263",
                        "Keep managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker.sideEffectBoundary.externalRequestSent=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverTestOnlyShellEchoMarker() {
        Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
        Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker marker =
                rehearsal.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker();
        assertThat(marker.markerVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-test-only-shell-echo-marker.v1"
                );
        assertThat(marker.sourceDisabledPrecheckEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v28");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractVersion())
                .isEqualTo("Node v264");
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-sandbox-endpoint-credential-resolver-test-only-shell-contract.v1"
                );
        assertThat(marker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractState())
                .isEqualTo("sandbox-endpoint-credential-resolver-test-only-shell-contract-ready");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationVersion())
                .isEqualTo("Node v263");
        assertThat(marker.sourceNodeSandboxEndpointCredentialResolverDisabledPrecheckUpstreamEchoVerificationState())
                .isEqualTo(
                        "sandbox-endpoint-credential-resolver-disabled-precheck-upstream-echo-verification-ready"
                );
        assertThat(marker.nextNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationVersion())
                .isEqualTo("Node v265");
        assertThat(marker.nodeV265MayConsume()).isTrue();
        assertThat(marker.shellMode()).isEqualTo("test-only-fake-resolver-contract");
        assertThat(marker.sourceSpan()).isEqualTo("Node v264 credential resolver test-only shell contract");
        assertThat(marker.sourceNodeV263().sourceVersion()).isEqualTo("Node v263");
        assertThat(marker.sourceNodeV263().verificationMode())
                .isEqualTo(
                        "java-v106-plus-mini-kv-v115-disabled-credential-resolver-precheck-upstream-echo-verification-only"
                );
        assertThat(marker.sourceNodeV263().sourceSpan()).isEqualTo("Node v262 + Java v106 + mini-kv v115");
        assertThat(marker.sourceNodeV263().sourceNodeV262Ready()).isTrue();
        assertThat(marker.sourceNodeV263().javaV106EchoReady()).isTrue();
        assertThat(marker.sourceNodeV263().miniKvV115NonParticipationReady()).isTrue();
        assertThat(marker.sourceNodeV263().disabledPrecheckAligned()).isTrue();
        assertThat(marker.sourceNodeV263().requiredEnvHandlesAligned()).isTrue();
        assertThat(marker.sourceNodeV263().optInGatesAligned()).isTrue();
        assertThat(marker.sourceNodeV263().failureTaxonomyAligned()).isTrue();
        assertThat(marker.sourceNodeV263().dryRunResponseShapeAligned()).isTrue();
        assertThat(marker.sourceNodeV263().inheritedNoGoConditionsAligned()).isTrue();
        assertThat(marker.sourceNodeV263().credentialBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().rawEndpointBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().connectionBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().writeBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().autoStartBoundaryAligned()).isTrue();
        assertThat(marker.sourceNodeV263().upstreamActionsStillDisabled()).isTrue();
        assertThat(marker.sourceNodeV263().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sourceNodeV263().credentialValueRead()).isFalse();
        assertThat(marker.sourceNodeV263().credentialValueLoaded()).isFalse();
        assertThat(marker.sourceNodeV263().credentialValueStored()).isFalse();
        assertThat(marker.sourceNodeV263().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sourceNodeV263().rawEndpointUrlIncluded()).isFalse();
        assertThat(marker.sourceNodeV263().externalRequestSent()).isFalse();
        assertThat(marker.sourceNodeV263().secretProviderInstantiated()).isFalse();
        assertThat(marker.sourceNodeV263().resolverClientInstantiated()).isFalse();
        assertThat(marker.sourceNodeV263().connectsManagedAudit()).isFalse();
        assertThat(marker.sourceNodeV263().schemaMigrationExecuted()).isFalse();
        assertThat(marker.sourceNodeV263().automaticUpstreamStart()).isFalse();
        assertThat(marker.sourceNodeV263().failureClassCount()).isEqualTo(7);
        assertThat(marker.sourceNodeV263().requiredEnvHandleCount()).isEqualTo(6);
        assertThat(marker.sourceNodeV263().optInGateCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV263().dryRunResponseFieldCount()).isEqualTo(12);
        assertThat(marker.sourceNodeV263().inheritedNoGoConditionCount()).isEqualTo(9);
        assertThat(marker.sourceNodeV263().checkCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV263().passedCheckCount()).isEqualTo(19);
        assertThat(marker.sourceNodeV263().productionBlockerCount()).isZero();
        assertThat(marker.sourceNodeV263().warningCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV263().recommendationCount()).isEqualTo(2);
        assertThat(marker.sourceNodeV263().readyForNodeV264CredentialResolverTestOnlyShellContract()).isTrue();
        assertThat(marker.resolverShellContract().contractDigest()).startsWith("sha256:");
        assertThat(marker.resolverShellContract().shellName())
                .isEqualTo("ManagedAuditSandboxEndpointCredentialResolverTestOnlyShell");
        assertThat(marker.resolverShellContract().shellMode()).isEqualTo("test-only-fake-resolver-contract");
        assertThat(marker.resolverShellContract().resolverKind()).isEqualTo("fake-in-memory");
        assertThat(marker.resolverShellContract().realResolverImplemented()).isFalse();
        assertThat(marker.resolverShellContract().realSecretProviderAllowed()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverOnly()).isTrue();
        assertThat(marker.resolverShellContract().requestShapeFieldCount()).isEqualTo(9);
        assertThat(marker.resolverShellContract().responseShapeFieldCount()).isEqualTo(13);
        assertThat(marker.resolverShellContract().failureMappingCount()).isEqualTo(7);
        assertThat(marker.resolverShellContract().guardConditionCount()).isEqualTo(10);
        assertThat(marker.resolverShellContract().requestShape().fields())
                .containsExactly(
                        "requestId",
                        "operation",
                        "credentialHandle",
                        "endpointHandle",
                        "resolverPolicyHandle",
                        "approvalMarker",
                        "approvalCorrelationId",
                        "dryRun",
                        "fakeResolverOnly"
                );
        assertThat(marker.resolverShellContract().requestShape().credentialHandleOnly()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().credentialValueAccepted()).isFalse();
        assertThat(marker.resolverShellContract().requestShape().endpointHandleOnly()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().rawEndpointUrlAccepted()).isFalse();
        assertThat(marker.resolverShellContract().requestShape().resolverPolicyHandleRequired()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().approvalMarkerRequired()).isTrue();
        assertThat(marker.resolverShellContract().requestShape().payloadMayContainSecrets()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().fields())
                .containsExactly(
                        "requestId",
                        "status",
                        "code",
                        "fakeResolverOnly",
                        "resolverClientInstantiated",
                        "secretProviderInstantiated",
                        "credentialValueRead",
                        "credentialValueLoaded",
                        "rawEndpointUrlParsed",
                        "externalRequestSent",
                        "connectsManagedAudit",
                        "schemaMigrationExecuted",
                        "productionRecordWritten"
                );
        assertThat(marker.resolverShellContract().responseShape().fakeResolverResponseOnly()).isTrue();
        assertThat(marker.resolverShellContract().responseShape().resolverClientInstantiated()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().secretProviderInstantiated()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().credentialValueRead()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().credentialValueLoaded()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().externalRequestSent()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().connectsManagedAudit()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().schemaMigrationExecuted()).isFalse();
        assertThat(marker.resolverShellContract().responseShape().productionRecordWritten()).isFalse();
        assertThat(marker.resolverShellContract().failureMapping())
                .extracting(RehearsalSandboxEndpointCredentialResolverTestOnlyShellFailureMapping::sourceFailureCode)
                .containsExactly(
                        "RESOLVER_DISABLED",
                        "APPROVAL_MARKER_MISSING",
                        "CREDENTIAL_HANDLE_MISSING",
                        "CREDENTIAL_VALUE_REQUESTED",
                        "RAW_ENDPOINT_URL_REQUESTED",
                        "EXTERNAL_REQUEST_REQUESTED",
                        "SCHEMA_MIGRATION_REQUESTED"
                );
        assertThat(marker.resolverShellContract().failureMapping())
                .allMatch(mapping -> mapping.shellFailureCode().startsWith("TEST_ONLY_") && !mapping.retryable());
        assertThat(marker.resolverShellContract().guardConditions())
                .allMatch(condition -> condition.required() && condition.value());
        assertThat(marker.resolverShellContract().fakeResolverProbe().requestId())
                .isEqualTo("managed-audit-v264-test-only-resolver-shell-probe");
        assertThat(marker.resolverShellContract().fakeResolverProbe().resolverKind()).isEqualTo("fake-in-memory");
        assertThat(marker.resolverShellContract().fakeResolverProbe().acceptedByFakeResolver()).isTrue();
        assertThat(marker.resolverShellContract().fakeResolverProbe().responseCode())
                .isEqualTo("TEST_ONLY_FAKE_RESOLVER");
        assertThat(marker.resolverShellContract().fakeResolverProbe().credentialValueRead()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().credentialValueLoaded()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().externalRequestSent()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().connectsManagedAudit()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().schemaMigrationExecuted()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().productionRecordWritten()).isFalse();
        assertThat(marker.resolverShellContract().fakeResolverProbe().probeDigest()).startsWith("sha256:");
        assertThat(marker.sideEffectBoundary().testOnlyShell()).isTrue();
        assertThat(marker.sideEffectBoundary().readOnlyContract()).isTrue();
        assertThat(marker.sideEffectBoundary().fakeResolverOnly()).isTrue();
        assertThat(marker.sideEffectBoundary().handleOnlyRequest()).isTrue();
        assertThat(marker.sideEffectBoundary().credentialResolverExecutionAllowed()).isFalse();
        assertThat(marker.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(marker.sideEffectBoundary().readsManagedAuditCredential()).isFalse();
        assertThat(marker.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(marker.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(marker.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(marker.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(marker.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(marker.sourceNodeV263Echoed()).isTrue();
        assertThat(marker.requestShapeEchoed()).isTrue();
        assertThat(marker.responseShapeEchoed()).isTrue();
        assertThat(marker.failureMappingEchoed()).isTrue();
        assertThat(marker.guardConditionsEchoed()).isTrue();
        assertThat(marker.fakeResolverProbeEchoed()).isTrue();
        assertThat(marker.fakeResolverOnlyEchoed()).isTrue();
        assertThat(marker.handleOnlyRequestEchoed()).isTrue();
        assertThat(marker.sideEffectBoundaryEchoed()).isTrue();
        assertThat(marker.upstreamActionsStillDisabledEchoed()).isTrue();
        assertThat(marker.readyForNodeV265SandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerification())
                .isTrue();
        assertThat(marker.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(marker.readyForProductionAudit()).isFalse();
        assertThat(marker.readyForProductionWindow()).isFalse();
        assertThat(marker.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(marker.nodeWarningCodes())
                .containsExactly("TEST_ONLY_SHELL_NOT_A_REAL_RESOLVER", "UPSTREAM_ECHO_REQUIRED_NEXT");
        assertThat(marker.nodeRecommendationCodes())
                .containsExactly("ASK_JAVA_MINI_KV_FOR_ECHO_NEXT", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");
        assertThat(marker.nextRequiredEchoVersions())
                .contains(
                        "Java v107 sandbox endpoint credential resolver test-only shell echo marker",
                        "mini-kv v116 sandbox endpoint credential resolver test-only shell non-participation receipt"
                );
        assertThat(marker.markerWarnings()).isEmpty();
        assertThat(marker.markerDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerWarnings",
                        "sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerDigest",
                        "sandboxEndpointCredentialResolverTestOnlyShellRawEndpointUrlAccepted"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShapeFieldCount=9",
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.resolverShellContract.requestShape.credentialValueAccepted=false",
                        "managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.consumedByNodeSandboxEndpointCredentialResolverTestOnlyShellContractProfile with Node v264",
                        "Require managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.readyForNodeV265SandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerification=true before Node v265",
                        "Keep managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker.sideEffectBoundary.connectsManagedAudit=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker().markerDigest())
                .isEqualTo(marker.markerDigest());
    }

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt() {
        Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
        Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-fake-shell-archive-echo-receipt.v1"
                );
        assertThat(receipt.sourceTestOnlyShellEchoMarkerSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v29");
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationVersion())
                .isEqualTo("Node v266");
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-fake-shell-archive-verification.v1"
                );
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-fake-shell-archive-verification"
                );
        assertThat(receipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationState())
                .isEqualTo("credential-resolver-fake-shell-archive-verification-ready");
        assertThat(receipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellContractVersion())
                .isEqualTo("Node v264");
        assertThat(receipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationVersion())
                .isEqualTo("Node v265");
        assertThat(receipt.sourceNodeSandboxEndpointCredentialResolverTestOnlyShellUpstreamEchoVerificationState())
                .isEqualTo("sandbox-endpoint-credential-resolver-test-only-shell-upstream-echo-verification-ready");
        assertThat(receipt.nextNodeSandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerificationVersion())
                .isEqualTo("Node v267");
        assertThat(receipt.nodeV267MayConsume()).isTrue();
        assertThat(receipt.archiveEchoMode())
                .isEqualTo("java-v110-credential-resolver-fake-shell-archive-echo-receipt-only");
        assertThat(receipt.sourceSpan())
                .isEqualTo("Node v264 credential resolver fake shell contract + Node v265 upstream echo archive");
        assertThat(receipt.sourceNodeV266().checkCount()).isEqualTo(28);
        assertThat(receipt.sourceNodeV266().passedCheckCount()).isEqualTo(28);
        assertThat(receipt.sourceNodeV266().archiveFileCount()).isEqualTo(9);
        assertThat(receipt.sourceNodeV266().requiredSnippetCount()).isEqualTo(24);
        assertThat(receipt.sourceNodeV266().matchedSnippetCount()).isEqualTo(24);
        assertThat(receipt.sourceNodeV266().productionBlockerCount()).isZero();
        assertThat(receipt.sourceNodeV266().warningCount()).isEqualTo(1);
        assertThat(receipt.sourceNodeV266().recommendationCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV266().sourceNodeV264Ready()).isTrue();
        assertThat(receipt.sourceNodeV266().sourceNodeV265Ready()).isTrue();
        assertThat(receipt.sourceNodeV266().sourceNodeV265ConsumesUpstreamEchoes()).isTrue();
        assertThat(receipt.sourceNodeV266().javaV107EchoReady()).isTrue();
        assertThat(receipt.sourceNodeV266().miniKvV116NonParticipationReady()).isTrue();
        assertThat(receipt.sourceNodeV266().javaV109OptimizationContextReady()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveFilesPresent()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveFilesNonEmpty()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveSnippetsMatched()).isTrue();
        assertThat(receipt.sourceNodeV266().routeResponsesVerified()).isTrue();
        assertThat(receipt.sourceNodeV266().noArchiveVerificationFakeShellRerun()).isTrue();
        assertThat(receipt.sourceNodeV266().readOnlyArchiveVerification()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveVerificationReadsFilesOnly()).isTrue();
        assertThat(receipt.sourceNodeV266().archiveVerificationRerunsFakeShellBehavior()).isFalse();
        assertThat(receipt.sourceNodeV266().upstreamActionsStillDisabled()).isTrue();
        assertThat(receipt.sourceNodeV266().credentialValueRead()).isFalse();
        assertThat(receipt.sourceNodeV266().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sourceNodeV266().externalRequestSent()).isFalse();
        assertThat(receipt.sourceNodeV266().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV266().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV266().connectsManagedAudit()).isFalse();
        assertThat(receipt.sourceNodeV266().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sourceNodeV266().automaticUpstreamStart()).isFalse();
        assertThat(receipt.archiveEvidence().archiveRoots()).containsExactly("c/264/", "c/265/");
        assertThat(receipt.archiveEvidence().sourceVersions()).containsExactly("Node v264", "Node v265");
        assertThat(receipt.archiveEvidence().archiveFileCount()).isEqualTo(9);
        assertThat(receipt.archiveEvidence().requiredSnippetCount()).isEqualTo(24);
        assertThat(receipt.archiveEvidence().matchedSnippetCount()).isEqualTo(24);
        assertThat(receipt.archiveEvidence().files())
                .extracting(file -> file.id())
                .containsExactly(
                        "v264-html-archive",
                        "v264-screenshot",
                        "v264-explanation",
                        "v264-code-walkthrough",
                        "v265-html-archive",
                        "v265-screenshot",
                        "v265-explanation",
                        "v265-code-walkthrough",
                        "active-plan"
                );
        assertThat(receipt.archiveEvidence().snippets())
                .extracting(snippet -> snippet.id())
                .contains("plan-v266", "v265-walkthrough-mini-kv-v116");
        assertThat(receipt.archiveVerification().archiveVerificationReadsFilesOnly()).isTrue();
        assertThat(receipt.archiveVerification().archiveVerificationRerunsFakeShellBehavior()).isFalse();
        assertThat(receipt.archiveVerification().upstreamActionsEnabled()).isFalse();
        assertThat(receipt.archiveVerification().productionAuditAllowed()).isFalse();
        assertThat(receipt.archiveVerification().routeResponsesVerified()).isTrue();
        assertThat(receipt.archiveChecks().sourceNodeV265ConsumesUpstreamEchoes()).isTrue();
        assertThat(receipt.archiveChecks().archiveFilesPresent()).isTrue();
        assertThat(receipt.archiveChecks().archiveSnippetsMatched()).isTrue();
        assertThat(receipt.archiveChecks().noArchiveVerificationFakeShellRerun()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyArchiveVerification()).isTrue();
        assertThat(receipt.sideEffectBoundary().archiveVerificationReadsFilesOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().archiveVerificationRerunsFakeShellBehavior()).isFalse();
        assertThat(receipt.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().managedAuditStoreWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV266Echoed()).isTrue();
        assertThat(receipt.sourceNodeV264ContractEchoed()).isTrue();
        assertThat(receipt.sourceNodeV265UpstreamEchoed()).isTrue();
        assertThat(receipt.archiveEvidenceEchoed()).isTrue();
        assertThat(receipt.archiveSnippetsEchoed()).isTrue();
        assertThat(receipt.routeResponsesEchoed()).isTrue();
        assertThat(receipt.readOnlyArchiveBoundaryEchoed()).isTrue();
        assertThat(receipt.noFakeShellRerunEchoed()).isTrue();
        assertThat(receipt.sideEffectBoundaryEchoed()).isTrue();
        assertThat(receipt.upstreamActionsStillDisabledEchoed()).isTrue();
        assertThat(receipt.readyForNodeV267SandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerification())
                .isTrue();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.readyForProductionWindow()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.nodeWarningCodes()).containsExactly("ARCHIVE_VERIFICATION_ONLY");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("WRITE_POST_V266_PLAN", "KEEP_REAL_RESOLVER_OUT_OF_SCOPE");
        assertThat(receipt.nextRequiredEchoVersions())
                .contains(
                        "Java v110 credential resolver fake-shell archive echo receipt",
                        "mini-kv v117 credential resolver fake-shell archive non-participation receipt"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().responseSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v31");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverFakeShellArchiveEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverFakeShellArchiveExternalRequestSent"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveEvidence.archiveFileCount=9",
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.archiveVerification.archiveVerificationRerunsFakeShellBehavior=false",
                        "managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.consumedByNodeSandboxEndpointCredentialResolverFakeShellArchiveVerificationProfile with Node v266",
                        "Require managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.readyForNodeV267SandboxEndpointCredentialResolverFakeShellArchiveUpstreamEchoVerification=true before Node v267",
                        "Keep managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt.sideEffectBoundary.connectsManagedAudit=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverFakeShellArchiveEchoReceipt().receiptDigest())
                .isEqualTo(receipt.receiptDigest());
    }

    @Test
    void releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt() {
        Instant latestFailedAt = Instant.parse("2026-05-12T01:00:00Z");
        Instant latestApprovalAt = Instant.parse("2026-05-12T01:05:00Z");
        when(failedEventSummaryService.summary()).thenReturn(new FailedEventSummaryResponse(
                Instant.parse("2026-05-12T01:10:00Z"),
                4,
                2,
                1,
                1,
                latestFailedAt,
                latestApprovalAt,
                3
        ));
        when(outboxRepository.countByPublishedAtIsNull()).thenReturn(6L);
        when(idempotencyStore.descriptor()).thenReturn(new IdempotencyStoreDescriptor(
                "java-idempotency-store.v1",
                "jpa-order-idempotency-store",
                "JpaIdempotencyStore",
                "JPA_DATABASE",
                "orders table",
                "orders.idempotency_key",
                "orders.idempotency_request_fingerprint",
                true,
                false,
                false,
                true,
                false,
                "DISABLED_CANDIDATE_ONLY",
                "mini-kv-ttl-token-adapter is documented for later TTL-token experiments, not wired into create-order.",
                false
        ));
        OutboxPublisherProperties outboxPublisherProperties = new OutboxPublisherProperties();
        outboxPublisherProperties.setEnabled(false);
        OutboxRabbitMqProperties outboxRabbitMqProperties = new OutboxRabbitMqProperties();
        outboxRabbitMqProperties.setEnabled(false);
        outboxRabbitMqProperties.setExchange("order-platform.outbox");
        outboxRabbitMqProperties.setQueue("order-platform.outbox.events");
        outboxRabbitMqProperties.setDeadLetterQueue("order-platform.outbox.events.dlq");
        MockEnvironment environment = new MockEnvironment()
                .withProperty("spring.application.name", "advanced-order-platform")
                .withProperty("info.app.version", "0.1.0-test");
        environment.setActiveProfiles("local", "ops");
        OpsEvidenceService service = new OpsEvidenceService(
                failedEventSummaryService,
                outboxRepository,
                outboxPublisherProperties,
                outboxRabbitMqProperties,
                idempotencyStore,
                environment
        );

        ReleaseApprovalRehearsalResponse rehearsal =
                service.releaseApprovalRehearsal(headerBackedRehearsalRequest());

        RehearsalManagedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt receipt =
                rehearsal.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt();
        assertThat(receipt.receiptVersion())
                .isEqualTo(
                        "java-release-approval-rehearsal-managed-audit-sandbox-endpoint-credential-resolver-production-readiness-blocked-decision-echo-receipt.v1"
                );
        assertThat(receipt.sourceFakeShellArchiveEchoReceiptSchemaVersion())
                .isEqualTo("java-release-approval-rehearsal-response-schema.v30");
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateVersion())
                .isEqualTo("Node v268");
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateProfile())
                .isEqualTo(
                        "managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-decision-gate.v1"
                );
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateEndpoint())
                .isEqualTo(
                        "/api/v1/audit/managed-audit-manual-sandbox-connection-credential-resolver-production-readiness-decision-gate"
                );
        assertThat(receipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateState())
                .isEqualTo("blocked");
        assertThat(receipt.sourceNodeCredentialResolverFakeShellArchiveUpstreamEchoVerificationVersion())
                .isEqualTo("Node v267");
        assertThat(receipt.nodeV269MayConsume()).isTrue();
        assertThat(receipt.decisionEchoMode())
                .isEqualTo(
                        "java-v111-credential-resolver-production-readiness-blocked-decision-echo-receipt-only"
                );
        assertThat(receipt.sourceSpan())
                .isEqualTo("Node v268 credential resolver production readiness blocked decision gate");
        assertThat(receipt.sourceNodeV268().decisionGateState()).isEqualTo("blocked");
        assertThat(receipt.sourceNodeV268().readinessDecision()).isEqualTo("blocked");
        assertThat(receipt.sourceNodeV268().sourceSpan()).isEqualTo("Node v267");
        assertThat(receipt.sourceNodeV268().sourceNodeV267Ready()).isTrue();
        assertThat(receipt.sourceNodeV268().sourceNodeV267BlocksRealResolver()).isTrue();
        assertThat(receipt.sourceNodeV268().archiveEchoChainReady()).isTrue();
        assertThat(receipt.sourceNodeV268().decisionGateEvaluated()).isTrue();
        assertThat(receipt.sourceNodeV268().productionReadinessGateOnly()).isTrue();
        assertThat(receipt.sourceNodeV268().readOnlyDecisionGate()).isTrue();
        assertThat(receipt.sourceNodeV268().readyForCredentialResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.sourceNodeV268().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sourceNodeV268().realResolverImplementationAllowed()).isFalse();
        assertThat(receipt.sourceNodeV268().executionAllowed()).isFalse();
        assertThat(receipt.sourceNodeV268().credentialValueRead()).isFalse();
        assertThat(receipt.sourceNodeV268().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sourceNodeV268().externalRequestSent()).isFalse();
        assertThat(receipt.sourceNodeV268().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV268().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sourceNodeV268().connectsManagedAudit()).isFalse();
        assertThat(receipt.sourceNodeV268().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sourceNodeV268().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV268().checkCount()).isEqualTo(25);
        assertThat(receipt.sourceNodeV268().passedCheckCount()).isEqualTo(15);
        assertThat(receipt.sourceNodeV268().sourceCheckCount()).isEqualTo(18);
        assertThat(receipt.sourceNodeV268().sourcePassedCheckCount()).isEqualTo(18);
        assertThat(receipt.sourceNodeV268().archiveFileCount()).isEqualTo(9);
        assertThat(receipt.sourceNodeV268().evidenceFileCount()).isEqualTo(7);
        assertThat(receipt.sourceNodeV268().requiredSnippetCount()).isEqualTo(24);
        assertThat(receipt.sourceNodeV268().matchedSnippetCount()).isEqualTo(32);
        assertThat(receipt.sourceNodeV268().missingPreImplementationRequirementCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV268().productionBlockerCount()).isEqualTo(10);
        assertThat(receipt.sourceNodeV268().warningCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV268().recommendationCount()).isEqualTo(2);
        assertThat(receipt.sourceNodeV268().readyForJavaV111EchoReceipt()).isTrue();
        assertThat(receipt.sourceNodeV268().readyForMiniKvV118NonParticipationReceipt()).isTrue();
        assertThat(receipt.preImplementationRequirements().planDocumentPresent()).isFalse();
        assertThat(receipt.preImplementationRequirements().credentialHandleBoundaryDefined()).isFalse();
        assertThat(receipt.preImplementationRequirements().secretProviderStubDefined()).isFalse();
        assertThat(receipt.preImplementationRequirements().auditLedgerWritePolicyDefined()).isFalse();
        assertThat(receipt.productionReadinessDecision().decisionDigest()).startsWith("sha256:");
        assertThat(receipt.productionReadinessDecision().decision()).isEqualTo("blocked");
        assertThat(receipt.productionReadinessDecision().allowsRealResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsRealCredentialResolverImplementation()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsSecretProviderStub()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsCredentialValueRead()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsRawEndpointUrlParse()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsExternalRequest()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsManagedAuditConnection()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsSchemaMigration()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsApprovalLedgerWrite()).isFalse();
        assertThat(receipt.productionReadinessDecision().allowsAutomaticUpstreamStart()).isFalse();
        assertThat(receipt.productionReadinessDecision().nextPlanRequiredBeforeImplementation()).isTrue();
        assertThat(receipt.decisionChecks().decisionGateEvaluated()).isTrue();
        assertThat(receipt.decisionChecks().credentialBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().rawEndpointBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().resolverBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().connectionBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().writeBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().autoStartBoundaryStillClosed()).isTrue();
        assertThat(receipt.decisionChecks().preImplementationPlanPresent()).isFalse();
        assertThat(receipt.decisionChecks().credentialHandleBoundaryDefined()).isFalse();
        assertThat(receipt.decisionChecks().productionAuditStillBlocked()).isTrue();
        assertThat(receipt.decisionChecks().realResolverImplementationStillBlocked()).isTrue();
        assertThat(receipt.sideEffectBoundary().readOnlyDecisionGate()).isTrue();
        assertThat(receipt.sideEffectBoundary().productionReadinessGateOnly()).isTrue();
        assertThat(receipt.sideEffectBoundary().readyForCredentialResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.sideEffectBoundary().readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.sideEffectBoundary().readyForProductionOperations()).isFalse();
        assertThat(receipt.sideEffectBoundary().credentialValueRead()).isFalse();
        assertThat(receipt.sideEffectBoundary().rawEndpointUrlParsed()).isFalse();
        assertThat(receipt.sideEffectBoundary().externalRequestSent()).isFalse();
        assertThat(receipt.sideEffectBoundary().secretProviderInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().resolverClientInstantiated()).isFalse();
        assertThat(receipt.sideEffectBoundary().connectsManagedAudit()).isFalse();
        assertThat(receipt.sideEffectBoundary().approvalLedgerWritten()).isFalse();
        assertThat(receipt.sideEffectBoundary().sqlExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().schemaMigrationExecuted()).isFalse();
        assertThat(receipt.sideEffectBoundary().automaticUpstreamStart()).isFalse();
        assertThat(receipt.sourceNodeV268Echoed()).isTrue();
        assertThat(receipt.sourceNodeV267UpstreamEchoed()).isTrue();
        assertThat(receipt.blockedDecisionEchoed()).isTrue();
        assertThat(receipt.preImplementationRequirementsEchoed()).isTrue();
        assertThat(receipt.missingRequirementBlockersEchoed()).isTrue();
        assertThat(receipt.noCredentialBoundaryEchoed()).isTrue();
        assertThat(receipt.noRawEndpointBoundaryEchoed()).isTrue();
        assertThat(receipt.noResolverBoundaryEchoed()).isTrue();
        assertThat(receipt.noConnectionBoundaryEchoed()).isTrue();
        assertThat(receipt.noWriteBoundaryEchoed()).isTrue();
        assertThat(receipt.noAutoStartBoundaryEchoed()).isTrue();
        assertThat(receipt.readyForNodeV269CredentialResolverProductionReadinessBlockedDecisionUpstreamEchoVerification())
                .isTrue();
        assertThat(receipt.readyForCredentialResolverPreImplementationPlan()).isFalse();
        assertThat(receipt.readyForManagedAuditSandboxAdapterConnection()).isFalse();
        assertThat(receipt.readyForProductionAudit()).isFalse();
        assertThat(receipt.readyForProductionWindow()).isFalse();
        assertThat(receipt.nodeMayTreatAsProductionAuditRecord()).isFalse();
        assertThat(receipt.missingRequirementCodes())
                .containsExactly(
                        "REAL_RESOLVER_PRE_IMPLEMENTATION_PLAN_MISSING",
                        "CREDENTIAL_HANDLE_BOUNDARY_MISSING",
                        "ENDPOINT_HANDLE_BOUNDARY_MISSING",
                        "SECRET_PROVIDER_STUB_MISSING",
                        "OPERATOR_APPROVAL_BOUNDARY_MISSING",
                        "ROLLBACK_BOUNDARY_MISSING",
                        "REDACTION_POLICY_MISSING",
                        "EXTERNAL_REQUEST_SIMULATION_PLAN_MISSING",
                        "SCHEMA_MIGRATION_POLICY_MISSING",
                        "AUDIT_LEDGER_WRITE_POLICY_MISSING"
                );
        assertThat(receipt.productionBlockerCodes()).containsExactlyElementsOf(receipt.missingRequirementCodes());
        assertThat(receipt.nodeWarningCodes())
                .containsExactly("DECISION_GATE_ONLY", "SOURCE_CHAIN_READY_BUT_NOT_PRODUCTION_READY");
        assertThat(receipt.nodeRecommendationCodes())
                .containsExactly("WRITE_SUCCESSOR_PLAN", "REQUEST_PARALLEL_UPSTREAM_ECHO");
        assertThat(receipt.nextRequiredEchoVersions())
                .contains(
                        "Java v111 credential resolver production-readiness blocked-decision echo receipt",
                        "mini-kv v118 credential resolver production-readiness blocked-decision non-participation receipt"
                );
        assertThat(receipt.receiptWarnings()).isEmpty();
        assertThat(receipt.receiptDigest()).startsWith("sha256:");
        assertThat(rehearsal.verificationHint().schemaFields())
                .contains("managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt");
        assertThat(rehearsal.verificationHint().warningDigestInputs())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptWarnings",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceiptDigest",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionCredentialValueRead",
                        "sandboxEndpointCredentialResolverProductionReadinessBlockedDecisionAutomaticUpstreamStart"
                );
        assertThat(rehearsal.verificationHint().proofClaims())
                .contains(
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.readinessDecision=blocked",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sourceNodeV268.missingPreImplementationRequirementCount=10",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsManagedAuditConnection=false",
                        "managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.readyForManagedAuditSandboxAdapterConnection=false"
                );
        assertThat(rehearsal.verificationHint().nodeVerificationActions())
                .contains(
                        "Compare managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.consumedByNodeCredentialResolverProductionReadinessDecisionGateProfile with Node v268",
                        "Require managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.readyForNodeV269CredentialResolverProductionReadinessBlockedDecisionUpstreamEchoVerification=true before Node v269",
                        "Keep managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.productionReadinessDecision.allowsCredentialValueRead=false",
                        "Keep managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt.sideEffectBoundary.automaticUpstreamStart=false"
                );

        ReleaseApprovalRehearsalResponse repeated =
                service.releaseApprovalRehearsal(paddedHeaderBackedRehearsalRequest());
        assertThat(repeated.managedAuditSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoReceipt()
                .receiptDigest()).isEqualTo(receipt.receiptDigest());
    }

    private ReleaseApprovalRehearsalRequest paddedHeaderBackedRehearsalRequest() {
        return new ReleaseApprovalRehearsalRequest(
                new ReleaseApprovalRehearsalRequest.Context(
                        " rehearsal-v67-001 ",
                        " release-operator@example.test ",
                        " audit-correlation-v67 "
                ),
                new ReleaseApprovalRehearsalRequest.OperatorWindow(
                        " operator-198 ",
                        " operator,auditor ",
                        " true ",
                        " approval-v198-operator-window "
                ),
                new ReleaseApprovalRehearsalRequest.CiEvidence(
                        " real-read-window-ci-archive-artifact-manifest.v1 ",
                        " sha256:node-v200-manifest-digest ",
                        " /api/v1/production/real-read-window-ci-archive-artifact-manifest ",
                        " 9 ",
                        " approval-v198-operator-window "
                ),
                new ReleaseApprovalRehearsalRequest.ArtifactRetention(
                        " real-read-window-ci-artifact-upload-dry-run-contract.v1 ",
                        " sha256:node-v202-upload-contract-digest ",
                        " orderops-real-read-window-evidence-v191-v201 ",
                        " c/ ",
                        " 30 ",
                        " dry-run-contract-only "
                ),
                new ReleaseApprovalRehearsalRequest.RuntimeReadiness(
                        " three-project-real-read-runtime-smoke-preflight.v1 ",
                        " sha256:node-v204-preflight-digest ",
                        " runtime-smoke-v205-session-001 ",
                        " java-release-approval-rehearsal ",
                        " manual-open-window-plan "
                ),
                new ReleaseApprovalRehearsalRequest.ManagedAudit(
                        " managed-audit-persistence-boundary-candidate.v1 ",
                        " sha256:node-v208-managed-audit-candidate-digest ",
                        " file-or-sqlite-dry-run-candidate ",
                        " 30 ",
                        " size-and-age-rotation-candidate "
                ),
                new ReleaseApprovalRehearsalRequest.ApprovalBinding(
                        " managed-audit-identity-approval-binding-contract.v1 ",
                        " sha256:node-v210-approval-binding-digest ",
                        " approval-request-v210-001 ",
                        " APPROVED_DRY_RUN_ONLY ",
                        " approval-record-correlation-v210 "
                )
        );
    }

    private ReleaseApprovalRehearsalRequest headerBackedRehearsalRequest() {
        return new ReleaseApprovalRehearsalRequest(
                new ReleaseApprovalRehearsalRequest.Context(
                        "rehearsal-v67-001",
                        "release-operator@example.test",
                        "audit-correlation-v67"
                ),
                new ReleaseApprovalRehearsalRequest.OperatorWindow(
                        "operator-198",
                        "operator,auditor",
                        "true",
                        "approval-v198-operator-window"
                ),
                new ReleaseApprovalRehearsalRequest.CiEvidence(
                        "real-read-window-ci-archive-artifact-manifest.v1",
                        "sha256:node-v200-manifest-digest",
                        "/api/v1/production/real-read-window-ci-archive-artifact-manifest",
                        "9",
                        "approval-v198-operator-window"
                ),
                new ReleaseApprovalRehearsalRequest.ArtifactRetention(
                        "real-read-window-ci-artifact-upload-dry-run-contract.v1",
                        "sha256:node-v202-upload-contract-digest",
                        "orderops-real-read-window-evidence-v191-v201",
                        "c/",
                        "30",
                        "dry-run-contract-only"
                ),
                new ReleaseApprovalRehearsalRequest.RuntimeReadiness(
                        "three-project-real-read-runtime-smoke-preflight.v1",
                        "sha256:node-v204-preflight-digest",
                        "runtime-smoke-v205-session-001",
                        "java-release-approval-rehearsal",
                        "manual-open-window-plan"
                ),
                new ReleaseApprovalRehearsalRequest.ManagedAudit(
                        "managed-audit-persistence-boundary-candidate.v1",
                        "sha256:node-v208-managed-audit-candidate-digest",
                        "file-or-sqlite-dry-run-candidate",
                        "30",
                        "size-and-age-rotation-candidate"
                ),
                new ReleaseApprovalRehearsalRequest.ApprovalBinding(
                        "managed-audit-identity-approval-binding-contract.v1",
                        "sha256:node-v210-approval-binding-digest",
                        "approval-request-v210-001",
                        "APPROVED_DRY_RUN_ONLY",
                        "approval-record-correlation-v210"
                )
        );
    }
}
