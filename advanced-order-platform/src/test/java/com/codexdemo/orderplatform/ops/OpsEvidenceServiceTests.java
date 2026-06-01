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
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/hardening",
                        "/api/v1/ops/shard-readiness/echo",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
                        "/api/v1/ops/shard-readiness/evidence-index",
                        "/api/v1/ops/shard-readiness/evidence-verification",
                        "/api/v1/ops/shard-readiness/evidence-handoff",
                        "/api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "/api/v1/ops/shard-readiness/live-read-gate-plan",
                        "/api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "/api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "/api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
                        "/api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
                        "/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout",
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-echo-v174.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json",
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
                        "GET /api/v1/ops/shard-readiness",
                        "GET /api/v1/ops/shard-readiness/hardening",
                        "GET /api/v1/ops/shard-readiness/echo",
                        "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "GET /api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "GET /api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
                        "GET /api/v1/ops/shard-readiness/evidence-index",
                        "GET /api/v1/ops/shard-readiness/evidence-verification",
                        "GET /api/v1/ops/shard-readiness/evidence-handoff",
                        "GET /api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "GET /api/v1/ops/shard-readiness/live-read-gate-plan",
                        "GET /api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "GET /api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
                        "GET /api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout",
                        "GET /api/v1/ops/release-approval-rehearsal",
                        "GET /contracts/java-shard-readiness-v153.fixture.json",
                        "GET /contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "GET /contracts/java-shard-readiness-echo-v174.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "GET /contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "GET /contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "GET /contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "GET /contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "GET /contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "GET /contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
                        "GET /contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json",
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
                        "/api/v1/ops/shard-readiness",
                        "/api/v1/ops/shard-readiness/hardening",
                        "/api/v1/ops/shard-readiness/echo",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity",
                        "/api/v1/ops/shard-readiness/evidence-index",
                        "/api/v1/ops/shard-readiness/evidence-verification",
                        "/api/v1/ops/shard-readiness/evidence-handoff",
                        "/api/v1/ops/shard-readiness/active-shard-plan-handoff",
                        "/api/v1/ops/shard-readiness/live-read-gate-plan",
                        "/api/v1/ops/shard-readiness/operator-service-lifecycle",
                        "/api/v1/ops/shard-readiness/declared-operator-lifecycle",
                        "/api/v1/ops/shard-readiness/runtime-execution-artifact-candidate",
                        "/api/v1/ops/shard-readiness/runtime-execution-packet-contribution",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-gate-input",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-contract-handoff",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-template-compatibility-intake",
                        "/api/v1/ops/shard-readiness/runtime-execution-approval-input-value-validation",
                        "/api/v1/ops/shard-readiness/runtime-execution-live-read-gate",
                        "/api/v1/ops/shard-readiness/runtime-execution-pass-evidence-closeout",
                        "/api/v1/ops/release-approval-rehearsal",
                        "/contracts/java-shard-readiness-v153.fixture.json",
                        "/contracts/java-shard-readiness-hardening-v154.fixture.json",
                        "/contracts/java-shard-readiness-echo-v174.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json",
                        "/contracts/java-shard-readiness-evidence-index-v155.fixture.json",
                        "/contracts/java-shard-readiness-evidence-verification-v156.fixture.json",
                        "/contracts/java-shard-readiness-evidence-handoff-v157.fixture.json",
                        "/contracts/java-shard-readiness-active-shard-plan-handoff-v158.fixture.json",
                        "/contracts/java-shard-readiness-live-read-gate-plan-v159.fixture.json",
                        "/contracts/java-shard-readiness-operator-service-lifecycle-v160.fixture.json",
                        "/contracts/java-shard-readiness-declared-operator-lifecycle-v161.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-gate-input-v164.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-contract-handoff-v165.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-v166.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-template-compatibility-intake-v167.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-approval-input-value-validation-v168.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-live-read-gate-v169.fixture.json",
                        "/contracts/java-shard-readiness-runtime-execution-pass-evidence-closeout-v170.fixture.json",
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

    }
}
