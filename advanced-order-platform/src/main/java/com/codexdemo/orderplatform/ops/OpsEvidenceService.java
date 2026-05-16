package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.notification.FailedEventSummaryResponse;
import com.codexdemo.orderplatform.notification.FailedEventSummaryService;
import com.codexdemo.orderplatform.order.IdempotencyStore;
import com.codexdemo.orderplatform.order.IdempotencyStoreDescriptor;
import com.codexdemo.orderplatform.order.JpaIdempotencyStore;
import com.codexdemo.orderplatform.outbox.OutboxPublisherProperties;
import com.codexdemo.orderplatform.outbox.OutboxRabbitMqProperties;
import com.codexdemo.orderplatform.outbox.OutboxRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsEvidenceService {

    static final String EVIDENCE_VERSION = "java-ops-evidence.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_VERSION = "java-release-approval-rehearsal.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_ENDPOINT = "/api/v1/ops/release-approval-rehearsal";

    static final String RELEASE_APPROVAL_REHEARSAL_CONTEXT_VERSION =
            "java-release-approval-rehearsal-context.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_OPERATOR_WINDOW_HINT_VERSION =
            "java-release-approval-rehearsal-operator-window-hint.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_CI_EVIDENCE_HINT_VERSION =
            "java-release-approval-rehearsal-ci-evidence-hint.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_ARTIFACT_RETENTION_HINT_VERSION =
            "java-release-approval-rehearsal-artifact-retention-hint.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_LIVE_READINESS_HINT_VERSION =
            "java-release-approval-rehearsal-live-readiness-hint.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_AUDIT_PERSISTENCE_HANDOFF_HINT_VERSION =
            "java-release-approval-rehearsal-audit-persistence-handoff-hint.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_FAILURE_TAXONOMY_VERSION =
            "java-release-approval-rehearsal-failure-taxonomy.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION =
            "java-release-approval-rehearsal-verification-hint.v1";

    static final String RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION =
            "java-release-approval-rehearsal-response-schema.v8";

    static final String RELEASE_VERIFICATION_MANIFEST_VERSION = "java-release-verification-manifest.v1";

    static final String RELEASE_VERIFICATION_MANIFEST_ENDPOINT =
            "/contracts/release-verification-manifest.sample.json";

    static final String DEPLOYMENT_ROLLBACK_EVIDENCE_VERSION = "java-deployment-rollback-evidence.v1";

    static final String DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT =
            "/contracts/deployment-rollback-evidence.sample.json";

    static final String RELEASE_BUNDLE_MANIFEST_VERSION = "java-release-bundle-manifest.v1";

    static final String RELEASE_BUNDLE_MANIFEST_ENDPOINT =
            "/contracts/release-bundle-manifest.sample.json";

    static final String RELEASE_HANDOFF_CHECKLIST_FIXTURE_VERSION =
            "java-release-handoff-checklist-fixture.v1";

    static final String RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT =
            "/contracts/release-handoff-checklist.fixture.json";

    static final String RELEASE_AUDIT_RETENTION_FIXTURE_VERSION =
            "java-release-audit-retention-fixture.v1";

    static final String RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT =
            "/contracts/release-audit-retention.fixture.json";

    static final String RELEASE_OPERATOR_SIGNOFF_FIXTURE_VERSION =
            "java-release-operator-signoff-fixture.v1";

    static final String RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT =
            "/contracts/release-operator-signoff.fixture.json";

    static final String ROLLBACK_APPROVER_EVIDENCE_FIXTURE_VERSION =
            "java-rollback-approver-evidence-fixture.v1";

    static final String ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT =
            "/contracts/rollback-approver-evidence.fixture.json";

    static final String ROLLBACK_APPROVAL_HANDOFF_VERSION = "java-rollback-approval-handoff.v1";

    static final String ROLLBACK_APPROVAL_HANDOFF_ENDPOINT =
            "/contracts/rollback-approval-handoff.sample.json";

    static final String ROLLBACK_APPROVAL_RECORD_FIXTURE_VERSION =
            "java-rollback-approval-record-fixture.v1";

    static final String ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT =
            "/contracts/rollback-approval-record.fixture.json";

    static final String ROLLBACK_SQL_REVIEW_GATE_VERSION = "java-rollback-sql-review-gate.v1";

    static final String ROLLBACK_SQL_REVIEW_GATE_ENDPOINT =
            "/contracts/rollback-sql-review-gate.sample.json";

    static final String PRODUCTION_SECRET_SOURCE_CONTRACT_VERSION = "java-production-secret-source-contract.v1";

    static final String PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT =
            "/contracts/production-secret-source-contract.sample.json";

    static final String PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_VERSION =
            "java-production-deployment-runbook-contract.v1";

    static final String PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT =
            "/contracts/production-deployment-runbook-contract.sample.json";

    private static final String REAL_REPLAY_ENDPOINT = "/api/v1/failed-events/{id}/replay";

    private final Instant startedAt = Instant.now();

    private final FailedEventSummaryService failedEventSummaryService;

    private final OutboxRepository outboxRepository;

    private final OutboxPublisherProperties outboxPublisherProperties;

    private final OutboxRabbitMqProperties outboxRabbitMqProperties;

    private final IdempotencyStore idempotencyStore;

    private final Environment environment;

    public OpsEvidenceService(
            FailedEventSummaryService failedEventSummaryService,
            OutboxRepository outboxRepository,
            OutboxPublisherProperties outboxPublisherProperties,
            OutboxRabbitMqProperties outboxRabbitMqProperties,
            IdempotencyStore idempotencyStore,
            Environment environment
    ) {
        this.failedEventSummaryService = failedEventSummaryService;
        this.outboxRepository = outboxRepository;
        this.outboxPublisherProperties = outboxPublisherProperties;
        this.outboxRabbitMqProperties = outboxRabbitMqProperties;
        this.idempotencyStore = idempotencyStore;
        this.environment = environment;
    }

    @Transactional(readOnly = true)
    public OpsEvidenceResponse evidence() {
        Instant sampledAt = Instant.now();
        FailedEventSummaryResponse failedEventSummary = failedEventSummaryService.summary();
        long pendingOutboxEvents = outboxRepository.countByPublishedAtIsNull();
        List<String> outboxBlockers = outboxBlockers();
        List<String> executionBlockers = executionBlockers(failedEventSummary);
        List<String> blockers = blockers(outboxBlockers, executionBlockers);

        return new OpsEvidenceResponse(
                sampledAt,
                EVIDENCE_VERSION,
                service(sampledAt),
                healthProbe(false),
                true,
                false,
                readOnlyWindow(true),
                orderIdempotency(),
                releaseVerification(),
                deploymentRollback(),
                releaseBundle(),
                releaseHandoffChecklistFixture(),
                releaseAuditRetentionFixture(),
                releaseOperatorSignoffFixture(),
                rollbackApproverEvidenceFixture(),
                rollbackApprovalHandoff(),
                rollbackApprovalRecordFixture(),
                rollbackSqlReviewGate(),
                productionSecretSourceContract(),
                productionDeploymentRunbookContract(),
                failedEventReplay(failedEventSummary),
                outbox(pendingOutboxEvents, outboxBlockers),
                approvalExecution(executionBlockers),
                blockers,
                warnings(failedEventSummary, pendingOutboxEvents),
                evidenceEndpoints()
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal() {
        return releaseApprovalRehearsal(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            String requestId,
            String operatorIdentity,
            String auditCorrelationId
    ) {
        return releaseApprovalRehearsal(
                requestId,
                operatorIdentity,
                auditCorrelationId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            String requestId,
            String operatorIdentity,
            String auditCorrelationId,
            String operatorWindowOperatorId,
            String operatorWindowRoles,
            String operatorWindowVerifiedClaim,
            String operatorWindowApprovalCorrelationId,
            String ciManifestVersion,
            String ciManifestDigest,
            String ciManifestEndpoint,
            String ciArtifactRecordCount,
            String ciApprovalCorrelationId
    ) {
        return releaseApprovalRehearsal(
                requestId,
                operatorIdentity,
                auditCorrelationId,
                operatorWindowOperatorId,
                operatorWindowRoles,
                operatorWindowVerifiedClaim,
                operatorWindowApprovalCorrelationId,
                ciManifestVersion,
                ciManifestDigest,
                ciManifestEndpoint,
                ciArtifactRecordCount,
                ciApprovalCorrelationId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            String requestId,
            String operatorIdentity,
            String auditCorrelationId,
            String operatorWindowOperatorId,
            String operatorWindowRoles,
            String operatorWindowVerifiedClaim,
            String operatorWindowApprovalCorrelationId
    ) {
        return releaseApprovalRehearsal(
                requestId,
                operatorIdentity,
                auditCorrelationId,
                operatorWindowOperatorId,
                operatorWindowRoles,
                operatorWindowVerifiedClaim,
                operatorWindowApprovalCorrelationId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            String requestId,
            String operatorIdentity,
            String auditCorrelationId,
            String operatorWindowOperatorId,
            String operatorWindowRoles,
            String operatorWindowVerifiedClaim,
            String operatorWindowApprovalCorrelationId,
            String ciManifestVersion,
            String ciManifestDigest,
            String ciManifestEndpoint,
            String ciArtifactRecordCount,
            String ciApprovalCorrelationId,
            String ciUploadContractVersion,
            String ciUploadContractDigest,
            String ciArtifactName,
            String ciArtifactRoot,
            String ciRetentionDays,
            String ciUploadMode
    ) {
        return releaseApprovalRehearsal(
                requestId,
                operatorIdentity,
                auditCorrelationId,
                operatorWindowOperatorId,
                operatorWindowRoles,
                operatorWindowVerifiedClaim,
                operatorWindowApprovalCorrelationId,
                ciManifestVersion,
                ciManifestDigest,
                ciManifestEndpoint,
                ciArtifactRecordCount,
                ciApprovalCorrelationId,
                ciUploadContractVersion,
                ciUploadContractDigest,
                ciArtifactName,
                ciArtifactRoot,
                ciRetentionDays,
                ciUploadMode,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            String requestId,
            String operatorIdentity,
            String auditCorrelationId,
            String operatorWindowOperatorId,
            String operatorWindowRoles,
            String operatorWindowVerifiedClaim,
            String operatorWindowApprovalCorrelationId,
            String ciManifestVersion,
            String ciManifestDigest,
            String ciManifestEndpoint,
            String ciArtifactRecordCount,
            String ciApprovalCorrelationId,
            String ciUploadContractVersion,
            String ciUploadContractDigest,
            String ciArtifactName,
            String ciArtifactRoot,
            String ciRetentionDays,
            String ciUploadMode,
            String runtimePreflightVersion,
            String runtimePreflightDigest,
            String runtimeSmokeSessionId,
            String runtimeReadTargetId,
            String runtimeWindowMode
    ) {
        return releaseApprovalRehearsal(
                requestId,
                operatorIdentity,
                auditCorrelationId,
                operatorWindowOperatorId,
                operatorWindowRoles,
                operatorWindowVerifiedClaim,
                operatorWindowApprovalCorrelationId,
                ciManifestVersion,
                ciManifestDigest,
                ciManifestEndpoint,
                ciArtifactRecordCount,
                ciApprovalCorrelationId,
                ciUploadContractVersion,
                ciUploadContractDigest,
                ciArtifactName,
                ciArtifactRoot,
                ciRetentionDays,
                ciUploadMode,
                runtimePreflightVersion,
                runtimePreflightDigest,
                runtimeSmokeSessionId,
                runtimeReadTargetId,
                runtimeWindowMode,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Transactional(readOnly = true)
    public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
            String requestId,
            String operatorIdentity,
            String auditCorrelationId,
            String operatorWindowOperatorId,
            String operatorWindowRoles,
            String operatorWindowVerifiedClaim,
            String operatorWindowApprovalCorrelationId,
            String ciManifestVersion,
            String ciManifestDigest,
            String ciManifestEndpoint,
            String ciArtifactRecordCount,
            String ciApprovalCorrelationId,
            String ciUploadContractVersion,
            String ciUploadContractDigest,
            String ciArtifactName,
            String ciArtifactRoot,
            String ciRetentionDays,
            String ciUploadMode,
            String runtimePreflightVersion,
            String runtimePreflightDigest,
            String runtimeSmokeSessionId,
            String runtimeReadTargetId,
            String runtimeWindowMode,
            String managedAuditCandidateVersion,
            String managedAuditCandidateDigest,
            String managedAuditSinkMode,
            String managedAuditRetentionDays,
            String managedAuditRotationPolicy
    ) {
        OpsEvidenceResponse evidence = evidence();
        String normalizedRequestId = normalizeHeaderValue(requestId);
        String normalizedOperatorIdentity = normalizeHeaderValue(operatorIdentity);
        String normalizedAuditCorrelationId = normalizeHeaderValue(auditCorrelationId);
        String normalizedOperatorWindowOperatorId = normalizeHeaderValue(operatorWindowOperatorId);
        String normalizedOperatorWindowRoles = normalizeHeaderValue(operatorWindowRoles);
        String normalizedOperatorWindowVerifiedClaim = normalizeHeaderValue(operatorWindowVerifiedClaim);
        String normalizedOperatorWindowApprovalCorrelationId =
                normalizeHeaderValue(operatorWindowApprovalCorrelationId);
        String normalizedCiManifestVersion = normalizeHeaderValue(ciManifestVersion);
        String normalizedCiManifestDigest = normalizeHeaderValue(ciManifestDigest);
        String normalizedCiManifestEndpoint = normalizeHeaderValue(ciManifestEndpoint);
        String normalizedCiArtifactRecordCount = normalizeHeaderValue(ciArtifactRecordCount);
        String normalizedCiApprovalCorrelationId = normalizeHeaderValue(ciApprovalCorrelationId);
        String normalizedCiUploadContractVersion = normalizeHeaderValue(ciUploadContractVersion);
        String normalizedCiUploadContractDigest = normalizeHeaderValue(ciUploadContractDigest);
        String normalizedCiArtifactName = normalizeHeaderValue(ciArtifactName);
        String normalizedCiArtifactRoot = normalizeHeaderValue(ciArtifactRoot);
        String normalizedCiRetentionDays = normalizeHeaderValue(ciRetentionDays);
        String normalizedCiUploadMode = normalizeHeaderValue(ciUploadMode);
        String normalizedRuntimePreflightVersion = normalizeHeaderValue(runtimePreflightVersion);
        String normalizedRuntimePreflightDigest = normalizeHeaderValue(runtimePreflightDigest);
        String normalizedRuntimeSmokeSessionId = normalizeHeaderValue(runtimeSmokeSessionId);
        String normalizedRuntimeReadTargetId = normalizeHeaderValue(runtimeReadTargetId);
        String normalizedRuntimeWindowMode = normalizeHeaderValue(runtimeWindowMode);
        String normalizedManagedAuditCandidateVersion = normalizeHeaderValue(managedAuditCandidateVersion);
        String normalizedManagedAuditCandidateDigest = normalizeHeaderValue(managedAuditCandidateDigest);
        String normalizedManagedAuditSinkMode = normalizeHeaderValue(managedAuditSinkMode);
        String normalizedManagedAuditRetentionDays = normalizeHeaderValue(managedAuditRetentionDays);
        String normalizedManagedAuditRotationPolicy = normalizeHeaderValue(managedAuditRotationPolicy);
        ReleaseApprovalRehearsalResponse.RehearsalRequestContext requestContext = rehearsalRequestContext(
                normalizedRequestId,
                normalizedOperatorIdentity,
                normalizedAuditCorrelationId
        );
        ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint operatorWindowHint =
                rehearsalOperatorWindowHint(
                        normalizedOperatorWindowOperatorId,
                        normalizedOperatorWindowRoles,
                        normalizedOperatorWindowVerifiedClaim,
                        normalizedOperatorWindowApprovalCorrelationId
                );
        ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint ciEvidenceHint =
                rehearsalCiEvidenceHint(
                        normalizedCiManifestVersion,
                        normalizedCiManifestDigest,
                        normalizedCiManifestEndpoint,
                        normalizedCiArtifactRecordCount,
                        normalizedCiApprovalCorrelationId
                );
        ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint artifactRetentionHint =
                rehearsalArtifactRetentionHint(
                        evidence.releaseAuditRetentionFixture(),
                        normalizedCiUploadContractVersion,
                        normalizedCiUploadContractDigest,
                        normalizedCiArtifactName,
                        normalizedCiArtifactRoot,
                        normalizedCiRetentionDays,
                        normalizedCiUploadMode
                );
        ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint liveReadinessHint =
                rehearsalLiveReadinessHint(
                        evidence,
                        normalizedRuntimePreflightVersion,
                        normalizedRuntimePreflightDigest,
                        normalizedRuntimeSmokeSessionId,
                        normalizedRuntimeReadTargetId,
                        normalizedRuntimeWindowMode
                );
        ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint =
                rehearsalAuditPersistenceHandoffHint(
                        evidence.releaseAuditRetentionFixture(),
                        normalizedManagedAuditCandidateVersion,
                        normalizedManagedAuditCandidateDigest,
                        normalizedManagedAuditSinkMode,
                        normalizedManagedAuditRetentionDays,
                        normalizedManagedAuditRotationPolicy
                );
        ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy =
                releaseApprovalRehearsalFailureTaxonomy(
                        evidence,
                        normalizedRequestId,
                        normalizedOperatorIdentity,
                        normalizedAuditCorrelationId
                );
        ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries = executionBoundaries();
        return new ReleaseApprovalRehearsalResponse(
                evidence.sampledAt(),
                RELEASE_APPROVAL_REHEARSAL_VERSION,
                "/api/v1/ops/evidence",
                "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
                true,
                false,
                requestContext,
                operatorWindowHint,
                ciEvidenceHint,
                artifactRetentionHint,
                liveReadinessHint,
                auditPersistenceHandoffHint,
                failureTaxonomy,
                releaseApprovalVerificationHint(
                        requestContext,
                        operatorWindowHint,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        failureTaxonomy,
                        executionBoundaries
                ),
                releaseApprovalInputs(evidence),
                liveSignals(evidence),
                executionBoundaries,
                releaseApprovalRehearsalBlockers(evidence),
                evidence.readOnlyWindow().requiredNodeEnvironment(),
                releaseApprovalNextEvidenceActions()
        );
    }

    private ReleaseApprovalRehearsalResponse.RehearsalVerificationHint releaseApprovalVerificationHint(
            ReleaseApprovalRehearsalResponse.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint operatorWindowHint,
            ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries
    ) {
        List<String> warningDigestInputs = List.of(
                "contextWarnings",
                "operatorWindowEchoWarnings",
                "ciEvidenceEchoWarnings",
                "artifactRetentionEchoWarnings",
                "liveReadinessEchoWarnings",
                "auditPersistenceHandoffEchoWarnings",
                "failureCategories",
                "taxonomyWarnings",
                "executionAllowed",
                "approvalLedgerWritten",
                "javaManagedAuditWriteAllowed",
                "nodeMayTreatAsProductionAuditRecord",
                "nodeMayWriteApprovalLedger"
        );
        List<String> proofClaims = List.of(
                "executionAllowed=false",
                "requestContext.approvalLedgerWritten=false",
                "ciEvidenceHint.noLedgerWriteProved=true",
                "ciEvidenceHint.ciArtifactUploadedByJava=false",
                "ciEvidenceHint.githubArtifactAccessedByJava=false",
                "ciEvidenceHint.productionWindowAllowedByJava=false",
                "artifactRetentionHint.javaRetentionFixtureReadOnly=true",
                "artifactRetentionHint.ciArtifactUploadedByJava=false",
                "artifactRetentionHint.githubArtifactAccessedByJava=false",
                "artifactRetentionHint.nodeMayTreatAsRetentionAuthorization=false",
                "liveReadinessHint.readOnlyEndpointReady=true",
                "liveReadinessHint.runtimeSmokeExecutedByJava=false",
                "liveReadinessHint.javaStartedProcessForNode=false",
                "liveReadinessHint.nodeMayTreatAsProductionAuthorization=false",
                "auditPersistenceHandoffHint.javaAuditSourceReadOnly=true",
                "auditPersistenceHandoffHint.javaLedgerWriteAllowed=false",
                "auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false",
                "auditPersistenceHandoffHint.javaExternalAuditSystemAccessed=false",
                "auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false",
                "executionBoundaries.nodeMayCreateApprovalDecision=false",
                "executionBoundaries.nodeMayWriteApprovalLedger=false",
                "executionBoundaries.nodeMayTriggerDeployment=false",
                "executionBoundaries.nodeMayTriggerRollback=false",
                "executionBoundaries.nodeMayExecuteRollbackSql=false"
        );
        return new ReleaseApprovalRehearsalResponse.RehearsalVerificationHint(
                RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION,
                RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
                warningDigest(
                        requestContext,
                        operatorWindowHint,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        failureTaxonomy,
                        executionBoundaries
                ),
                "NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS",
                !requestContext.approvalLedgerWritten()
                        && ciEvidenceHint.noLedgerWriteProved()
                        && artifactRetentionHint.javaRetentionFixtureReadOnly()
                        && !artifactRetentionHint.ciArtifactUploadedByJava()
                        && !artifactRetentionHint.githubArtifactAccessedByJava()
                        && liveReadinessHint.readOnlyEndpointReady()
                        && !liveReadinessHint.runtimeSmokeExecutedByJava()
                        && !liveReadinessHint.javaStartedProcessForNode()
                        && auditPersistenceHandoffHint.javaAuditSourceReadOnly()
                        && !auditPersistenceHandoffHint.javaLedgerWriteAllowed()
                        && !auditPersistenceHandoffHint.javaManagedAuditWriteAllowed()
                        && !auditPersistenceHandoffHint.javaExternalAuditSystemAccessed()
                        && !executionBoundaries.nodeMayCreateApprovalDecision()
                        && !executionBoundaries.nodeMayWriteApprovalLedger(),
                false,
                List.of(
                        "sampledAt",
                        "rehearsalVersion",
                        "requestContext",
                        "operatorWindowHint",
                        "ciEvidenceHint",
                        "artifactRetentionHint",
                        "liveReadinessHint",
                        "auditPersistenceHandoffHint",
                        "failureTaxonomy",
                        "verificationHint",
                        "releaseApprovalInputs",
                        "liveSignals",
                        "executionBoundaries",
                        "rehearsalBlockers",
                        "requiredNodeEnvironment",
                        "nextEvidenceActions"
                ),
                warningDigestInputs,
                proofClaims,
                List.of(
                        "Verify responseSchemaVersion before importing operator window results",
                        "Compare ciEvidenceHint.manifestProfileVersion with Node v200 manifest profileVersion",
                        "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                        "Require ciEvidenceHint.ciArtifactUploadedByJava=false until CI artifact upload exists outside Java",
                        "Compare artifactRetentionHint.ciArtifactName and ciRetentionDays with Node v202 dry-run contract",
                        "Require artifactRetentionHint.nodeMayTreatAsRetentionAuthorization=false until Node v203 retention gate passes",
                        "Compare liveReadinessHint.sourcePreflightVersion and runtimeSmokeSessionId with Node v204/v205 smoke context",
                        "Require liveReadinessHint.runtimeSmokeExecutedByJava=false; Node owns v205 process/run evidence",
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateVersion with Node v208 managed audit candidate",
                        "Require auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false until Node owns dry-run persistence",
                        "Compare warningDigest across closed-window and operator-window reads",
                        "Require noLedgerWriteProved=true before treating the response as read-only evidence",
                        "Keep UPSTREAM_ACTIONS_ENABLED=false"
                )
        );
    }

    private String warningDigest(
            ReleaseApprovalRehearsalResponse.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint operatorWindowHint,
            ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries
    ) {
        return digest(List.of(
                line("digestKind", "releaseApprovalRehearsalWarning"),
                line("hintVersion", RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION),
                line("responseSchemaVersion", RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION),
                line("contextWarnings", requestContext.contextWarnings()),
                line("operatorWindowEchoWarnings", operatorWindowHint.echoWarnings()),
                line("ciEvidenceEchoWarnings", ciEvidenceHint.echoWarnings()),
                line("artifactRetentionEchoWarnings", artifactRetentionHint.echoWarnings()),
                line("liveReadinessEchoWarnings", liveReadinessHint.echoWarnings()),
                line("auditPersistenceHandoffEchoWarnings", auditPersistenceHandoffHint.echoWarnings()),
                line("failureCategories", failureTaxonomy.failureCategories()),
                line("taxonomyWarnings", failureTaxonomy.taxonomyWarnings()),
                line("executionAllowed", false),
                line("approvalLedgerWritten", requestContext.approvalLedgerWritten()),
                line("ciArtifactUploadedByJava", ciEvidenceHint.ciArtifactUploadedByJava()),
                line("githubArtifactAccessedByJava", ciEvidenceHint.githubArtifactAccessedByJava()),
                line("retentionCiArtifactUploadedByJava", artifactRetentionHint.ciArtifactUploadedByJava()),
                line("retentionGithubArtifactAccessedByJava", artifactRetentionHint.githubArtifactAccessedByJava()),
                line("retentionAuthorization", artifactRetentionHint.nodeMayTreatAsRetentionAuthorization()),
                line("runtimeSmokeExecutedByJava", liveReadinessHint.runtimeSmokeExecutedByJava()),
                line("javaStartedProcessForNode", liveReadinessHint.javaStartedProcessForNode()),
                line("nodeMayTreatAsProductionAuthorization", liveReadinessHint.nodeMayTreatAsProductionAuthorization()),
                line("javaManagedAuditWriteAllowed", auditPersistenceHandoffHint.javaManagedAuditWriteAllowed()),
                line("javaExternalAuditSystemAccessed", auditPersistenceHandoffHint.javaExternalAuditSystemAccessed()),
                line(
                        "nodeMayTreatAsProductionAuditRecord",
                        auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord()
                ),
                line("nodeMayWriteApprovalLedger", executionBoundaries.nodeMayWriteApprovalLedger())
        ));
    }

    private ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint rehearsalOperatorWindowHint(
            String normalizedOperatorWindowOperatorId,
            String normalizedOperatorWindowRoles,
            String normalizedOperatorWindowVerifiedClaim,
            String normalizedOperatorWindowApprovalCorrelationId
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(
                warnings,
                normalizedOperatorWindowOperatorId,
                "ORDEROPS_OPERATOR_ID_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedOperatorWindowRoles,
                "ORDEROPS_OPERATOR_ROLES_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedOperatorWindowVerifiedClaim,
                "ORDEROPS_OPERATOR_VERIFIED_CLAIM_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedOperatorWindowApprovalCorrelationId,
                "ORDEROPS_APPROVAL_CORRELATION_ID_MISSING"
        );
        boolean operatorIdentityEchoed = normalizedOperatorWindowOperatorId != null;
        boolean operatorRolesEchoed = normalizedOperatorWindowRoles != null;
        boolean operatorVerifiedClaimEchoed = normalizedOperatorWindowVerifiedClaim != null;
        boolean approvalCorrelationEchoed = normalizedOperatorWindowApprovalCorrelationId != null;

        return new ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint(
                RELEASE_APPROVAL_REHEARSAL_OPERATOR_WINDOW_HINT_VERSION,
                valueOrPlaceholder(normalizedOperatorWindowOperatorId, "orderops-operator-id-not-supplied"),
                sourceFor(normalizedOperatorWindowOperatorId, "x-orderops-operator-id"),
                valueOrPlaceholder(normalizedOperatorWindowRoles, "orderops-roles-not-supplied"),
                sourceFor(normalizedOperatorWindowRoles, "x-orderops-roles"),
                valueOrPlaceholder(normalizedOperatorWindowVerifiedClaim, "orderops-operator-verified-not-supplied"),
                sourceFor(normalizedOperatorWindowVerifiedClaim, "x-orderops-operator-verified"),
                valueOrPlaceholder(
                        normalizedOperatorWindowApprovalCorrelationId,
                        "orderops-approval-correlation-id-not-supplied"
                ),
                sourceFor(
                        normalizedOperatorWindowApprovalCorrelationId,
                        "x-orderops-approval-correlation-id"
                ),
                operatorIdentityEchoed,
                operatorRolesEchoed,
                operatorVerifiedClaimEchoed,
                approvalCorrelationEchoed,
                operatorIdentityEchoed
                        && operatorRolesEchoed
                        && operatorVerifiedClaimEchoed
                        && approvalCorrelationEchoed,
                false,
                false,
                false,
                List.of(
                        "x-orderops-operator-id",
                        "x-orderops-roles",
                        "x-orderops-operator-verified",
                        "x-orderops-approval-correlation-id"
                ),
                List.copyOf(warnings),
                List.of(
                        "Compare operatorWindowHint.operatorId with Node v198 operatorIdentity.operatorId",
                        "Compare operatorWindowHint.operatorRoles with Node v198 operatorIdentity.roles",
                        "Compare operatorWindowHint.approvalCorrelationId with Node v198 approvalBinding.approvalCorrelationId",
                        "Require productionIdpVerifiedByJava=false until real IdP integration exists",
                        "Keep nodeMayTreatAsProductionIdentity=false"
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint rehearsalCiEvidenceHint(
            String normalizedCiManifestVersion,
            String normalizedCiManifestDigest,
            String normalizedCiManifestEndpoint,
            String normalizedCiArtifactRecordCount,
            String normalizedCiApprovalCorrelationId
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(
                warnings,
                normalizedCiManifestVersion,
                "ORDEROPS_CI_MANIFEST_VERSION_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiManifestDigest,
                "ORDEROPS_CI_MANIFEST_DIGEST_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiManifestEndpoint,
                "ORDEROPS_CI_MANIFEST_ENDPOINT_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiArtifactRecordCount,
                "ORDEROPS_CI_ARTIFACT_RECORD_COUNT_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiApprovalCorrelationId,
                "ORDEROPS_CI_APPROVAL_CORRELATION_ID_MISSING"
        );
        boolean manifestProfileVersionEchoed = normalizedCiManifestVersion != null;
        boolean manifestDigestEchoed = normalizedCiManifestDigest != null;
        boolean manifestEndpointEchoed = normalizedCiManifestEndpoint != null;
        boolean artifactRecordCountEchoed = normalizedCiArtifactRecordCount != null;
        boolean approvalCorrelationEchoed = normalizedCiApprovalCorrelationId != null;

        return new ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint(
                RELEASE_APPROVAL_REHEARSAL_CI_EVIDENCE_HINT_VERSION,
                valueOrPlaceholder(normalizedCiManifestVersion, "ci-manifest-profile-version-not-supplied"),
                sourceFor(normalizedCiManifestVersion, "x-orderops-ci-manifest-version"),
                valueOrPlaceholder(normalizedCiManifestDigest, "ci-manifest-digest-not-supplied"),
                sourceFor(normalizedCiManifestDigest, "x-orderops-ci-manifest-digest"),
                valueOrPlaceholder(normalizedCiManifestEndpoint, "ci-manifest-endpoint-not-supplied"),
                sourceFor(normalizedCiManifestEndpoint, "x-orderops-ci-manifest-endpoint"),
                valueOrPlaceholder(normalizedCiArtifactRecordCount, "ci-artifact-record-count-not-supplied"),
                sourceFor(normalizedCiArtifactRecordCount, "x-orderops-ci-artifact-record-count"),
                valueOrPlaceholder(normalizedCiApprovalCorrelationId, "ci-approval-correlation-id-not-supplied"),
                sourceFor(normalizedCiApprovalCorrelationId, "x-orderops-ci-approval-correlation-id"),
                manifestProfileVersionEchoed,
                manifestDigestEchoed,
                manifestEndpointEchoed,
                artifactRecordCountEchoed,
                approvalCorrelationEchoed,
                manifestProfileVersionEchoed
                        && manifestDigestEchoed
                        && manifestEndpointEchoed
                        && artifactRecordCountEchoed
                        && approvalCorrelationEchoed,
                "NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS",
                true,
                false,
                false,
                false,
                false,
                List.of(
                        "x-orderops-ci-manifest-version",
                        "x-orderops-ci-manifest-digest",
                        "x-orderops-ci-manifest-endpoint",
                        "x-orderops-ci-artifact-record-count",
                        "x-orderops-ci-approval-correlation-id"
                ),
                List.copyOf(warnings),
                List.of(
                        "Compare ciEvidenceHint.manifestProfileVersion with Node v200 profileVersion",
                        "Compare ciEvidenceHint.manifestDigest with Node v200 manifest.manifestDigest",
                        "Compare ciEvidenceHint.manifestEndpoint with Node v200 evidence endpoint",
                        "Compare ciEvidenceHint.approvalCorrelationId with operatorWindowHint.approvalCorrelationId when both are supplied",
                        "Keep ciArtifactUploadedByJava=false and githubArtifactAccessedByJava=false"
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint rehearsalArtifactRetentionHint(
            OpsEvidenceResponse.ReleaseAuditRetentionFixture retentionFixture,
            String normalizedCiUploadContractVersion,
            String normalizedCiUploadContractDigest,
            String normalizedCiArtifactName,
            String normalizedCiArtifactRoot,
            String normalizedCiRetentionDays,
            String normalizedCiUploadMode
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(
                warnings,
                normalizedCiUploadContractVersion,
                "ORDEROPS_CI_UPLOAD_CONTRACT_VERSION_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiUploadContractDigest,
                "ORDEROPS_CI_UPLOAD_CONTRACT_DIGEST_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiArtifactName,
                "ORDEROPS_CI_ARTIFACT_NAME_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiArtifactRoot,
                "ORDEROPS_CI_ARTIFACT_ROOT_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiRetentionDays,
                "ORDEROPS_CI_RETENTION_DAYS_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedCiUploadMode,
                "ORDEROPS_CI_UPLOAD_MODE_MISSING"
        );
        boolean uploadContractVersionEchoed = normalizedCiUploadContractVersion != null;
        boolean uploadContractDigestEchoed = normalizedCiUploadContractDigest != null;
        boolean artifactNameEchoed = normalizedCiArtifactName != null;
        boolean artifactRootEchoed = normalizedCiArtifactRoot != null;
        boolean retentionDaysEchoed = normalizedCiRetentionDays != null;
        boolean uploadModeEchoed = normalizedCiUploadMode != null;
        boolean retentionDaysWithinJavaRetention = retentionDaysWithinJavaRetention(
                normalizedCiRetentionDays,
                retentionFixture.retentionDays()
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint(
                RELEASE_APPROVAL_REHEARSAL_ARTIFACT_RETENTION_HINT_VERSION,
                retentionFixture.fixtureVersion(),
                retentionFixture.fixtureEndpoint(),
                retentionFixture.retentionId(),
                retentionFixture.artifactTarget(),
                retentionFixture.retentionDays(),
                valueOrPlaceholder(normalizedCiUploadContractVersion, "ci-upload-contract-version-not-supplied"),
                sourceFor(normalizedCiUploadContractVersion, "x-orderops-ci-upload-contract-version"),
                valueOrPlaceholder(normalizedCiUploadContractDigest, "ci-upload-contract-digest-not-supplied"),
                sourceFor(normalizedCiUploadContractDigest, "x-orderops-ci-upload-contract-digest"),
                valueOrPlaceholder(normalizedCiArtifactName, "ci-artifact-name-not-supplied"),
                sourceFor(normalizedCiArtifactName, "x-orderops-ci-artifact-name"),
                valueOrPlaceholder(normalizedCiArtifactRoot, "ci-artifact-root-not-supplied"),
                sourceFor(normalizedCiArtifactRoot, "x-orderops-ci-artifact-root"),
                valueOrPlaceholder(normalizedCiRetentionDays, "ci-retention-days-not-supplied"),
                sourceFor(normalizedCiRetentionDays, "x-orderops-ci-retention-days"),
                valueOrPlaceholder(normalizedCiUploadMode, "ci-upload-mode-not-supplied"),
                sourceFor(normalizedCiUploadMode, "x-orderops-ci-upload-mode"),
                uploadContractVersionEchoed,
                uploadContractDigestEchoed,
                artifactNameEchoed,
                artifactRootEchoed,
                retentionDaysEchoed,
                uploadModeEchoed,
                uploadContractVersionEchoed
                        && uploadContractDigestEchoed
                        && artifactNameEchoed
                        && artifactRootEchoed
                        && retentionDaysEchoed
                        && uploadModeEchoed,
                retentionDaysWithinJavaRetention,
                retentionFixture.nodeMayConsume()
                        && retentionFixture.auditExportReadOnly()
                        && !retentionFixture.deploymentExecutionAllowed()
                        && !retentionFixture.rollbackSqlExecutionAllowed(),
                retentionFixture.auditExportReadOnly(),
                false,
                false,
                false,
                false,
                List.of(
                        "x-orderops-ci-upload-contract-version",
                        "x-orderops-ci-upload-contract-digest",
                        "x-orderops-ci-artifact-name",
                        "x-orderops-ci-artifact-root",
                        "x-orderops-ci-retention-days",
                        "x-orderops-ci-upload-mode"
                ),
                retentionFixture.evidenceEndpoints(),
                List.copyOf(warnings),
                List.of(
                        "Compare artifactRetentionHint.ciUploadContractVersion with Node v202 profileVersion",
                        "Compare artifactRetentionHint.ciUploadContractDigest with Node v202 dryRunContract.contractDigest",
                        "Compare artifactRetentionHint.ciArtifactName with Node v202 dryRunContract.artifactName",
                        "Require artifactRetentionHint.retentionDaysWithinJavaRetention=true before Node v203 retention gate",
                        "Keep ciArtifactUploadedByJava=false and githubArtifactAccessedByJava=false"
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint rehearsalLiveReadinessHint(
            OpsEvidenceResponse evidence,
            String normalizedRuntimePreflightVersion,
            String normalizedRuntimePreflightDigest,
            String normalizedRuntimeSmokeSessionId,
            String normalizedRuntimeReadTargetId,
            String normalizedRuntimeWindowMode
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(
                warnings,
                normalizedRuntimePreflightVersion,
                "ORDEROPS_RUNTIME_PREFLIGHT_VERSION_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedRuntimePreflightDigest,
                "ORDEROPS_RUNTIME_PREFLIGHT_DIGEST_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedRuntimeSmokeSessionId,
                "ORDEROPS_RUNTIME_SMOKE_SESSION_ID_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedRuntimeReadTargetId,
                "ORDEROPS_RUNTIME_READ_TARGET_ID_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedRuntimeWindowMode,
                "ORDEROPS_RUNTIME_WINDOW_MODE_MISSING"
        );
        boolean sourcePreflightVersionEchoed = normalizedRuntimePreflightVersion != null;
        boolean sourcePreflightDigestEchoed = normalizedRuntimePreflightDigest != null;
        boolean runtimeSmokeSessionIdEchoed = normalizedRuntimeSmokeSessionId != null;
        boolean runtimeReadTargetIdEchoed = normalizedRuntimeReadTargetId != null;
        boolean runtimeWindowModeEchoed = normalizedRuntimeWindowMode != null;
        boolean liveReadinessContextComplete = sourcePreflightVersionEchoed
                && sourcePreflightDigestEchoed
                && runtimeSmokeSessionIdEchoed
                && runtimeReadTargetIdEchoed
                && runtimeWindowModeEchoed;

        return new ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint(
                RELEASE_APPROVAL_REHEARSAL_LIVE_READINESS_HINT_VERSION,
                evidence.sampledAt(),
                "sampledAt",
                RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
                RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                "/actuator/health",
                valueOrPlaceholder(
                        normalizedRuntimePreflightVersion,
                        "runtime-preflight-version-not-supplied"
                ),
                sourceFor(normalizedRuntimePreflightVersion, "x-orderops-runtime-preflight-version"),
                valueOrPlaceholder(
                        normalizedRuntimePreflightDigest,
                        "runtime-preflight-digest-not-supplied"
                ),
                sourceFor(normalizedRuntimePreflightDigest, "x-orderops-runtime-preflight-digest"),
                valueOrPlaceholder(
                        normalizedRuntimeSmokeSessionId,
                        "runtime-smoke-session-id-not-supplied"
                ),
                sourceFor(normalizedRuntimeSmokeSessionId, "x-orderops-runtime-smoke-session-id"),
                valueOrPlaceholder(
                        normalizedRuntimeReadTargetId,
                        "runtime-read-target-id-not-supplied"
                ),
                sourceFor(normalizedRuntimeReadTargetId, "x-orderops-runtime-read-target-id"),
                valueOrPlaceholder(
                        normalizedRuntimeWindowMode,
                        "runtime-window-mode-not-supplied"
                ),
                sourceFor(normalizedRuntimeWindowMode, "x-orderops-runtime-window-mode"),
                sourcePreflightVersionEchoed,
                sourcePreflightDigestEchoed,
                runtimeSmokeSessionIdEchoed,
                runtimeReadTargetIdEchoed,
                runtimeWindowModeEchoed,
                liveReadinessContextComplete,
                evidence.readOnly()
                        && !evidence.executionAllowed()
                        && evidence.readOnlyWindow().readyForReadOnlyLiveProbe(),
                evidence.readOnly()
                        && !evidence.executionAllowed()
                        && evidence.readOnlyWindow().allowedProbeEndpoints()
                        .contains("GET " + RELEASE_APPROVAL_REHEARSAL_ENDPOINT),
                false,
                true,
                false,
                false,
                false,
                List.of(
                        "x-orderops-runtime-preflight-version",
                        "x-orderops-runtime-preflight-digest",
                        "x-orderops-runtime-smoke-session-id",
                        "x-orderops-runtime-read-target-id",
                        "x-orderops-runtime-window-mode"
                ),
                List.of(
                        "GET /actuator/health",
                        "GET " + RELEASE_APPROVAL_REHEARSAL_ENDPOINT
                ),
                List.of(
                        "POST /api/v1/orders",
                        "POST /api/v1/failed-events/{id}/replay",
                        "PUT /api/v1/*",
                        "PATCH /api/v1/*",
                        "DELETE /api/v1/*",
                        "Java process start/stop is owned by Node v205 smoke orchestration"
                ),
                List.copyOf(warnings),
                List.of(
                        "Compare liveReadinessHint.sourcePreflightVersion with Node v204 profileVersion",
                        "Compare liveReadinessHint.sourcePreflightDigest with Node v204 runtimeWindow.preflightDigest",
                        "Compare liveReadinessHint.runtimeSmokeSessionId with Node v205 smoke session id",
                        "Require liveReadinessHint.readOnlyEndpointReady=true before counting Java read target as ready",
                        "Keep runtimeSmokeExecutedByJava=false and javaStartedProcessForNode=false"
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint
            rehearsalAuditPersistenceHandoffHint(
                    OpsEvidenceResponse.ReleaseAuditRetentionFixture retentionFixture,
                    String normalizedManagedAuditCandidateVersion,
                    String normalizedManagedAuditCandidateDigest,
                    String normalizedManagedAuditSinkMode,
                    String normalizedManagedAuditRetentionDays,
                    String normalizedManagedAuditRotationPolicy
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(
                warnings,
                normalizedManagedAuditCandidateVersion,
                "ORDEROPS_MANAGED_AUDIT_CANDIDATE_VERSION_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedManagedAuditCandidateDigest,
                "ORDEROPS_MANAGED_AUDIT_CANDIDATE_DIGEST_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedManagedAuditSinkMode,
                "ORDEROPS_MANAGED_AUDIT_SINK_MODE_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedManagedAuditRetentionDays,
                "ORDEROPS_MANAGED_AUDIT_RETENTION_DAYS_MISSING"
        );
        addMissingContextWarning(
                warnings,
                normalizedManagedAuditRotationPolicy,
                "ORDEROPS_MANAGED_AUDIT_ROTATION_POLICY_MISSING"
        );
        boolean candidateVersionEchoed = normalizedManagedAuditCandidateVersion != null;
        boolean candidateDigestEchoed = normalizedManagedAuditCandidateDigest != null;
        boolean sinkModeEchoed = normalizedManagedAuditSinkMode != null;
        boolean retentionDaysEchoed = normalizedManagedAuditRetentionDays != null;
        boolean rotationPolicyEchoed = normalizedManagedAuditRotationPolicy != null;
        boolean auditPersistenceHandoffContextComplete = candidateVersionEchoed
                && candidateDigestEchoed
                && sinkModeEchoed
                && retentionDaysEchoed
                && rotationPolicyEchoed;
        boolean managedAuditRetentionWithinJavaRetention = retentionDaysWithinJavaRetention(
                normalizedManagedAuditRetentionDays,
                retentionFixture.retentionDays()
        );
        boolean javaAuditSourceReadOnly = retentionFixture.nodeMayConsume()
                && retentionFixture.auditExportReadOnly()
                && !retentionFixture.deploymentExecutionAllowed()
                && !retentionFixture.rollbackSqlExecutionAllowed();

        return new ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint(
                RELEASE_APPROVAL_REHEARSAL_AUDIT_PERSISTENCE_HANDOFF_HINT_VERSION,
                retentionFixture.fixtureVersion(),
                retentionFixture.fixtureEndpoint(),
                retentionFixture.retentionDays(),
                valueOrPlaceholder(
                        normalizedManagedAuditCandidateVersion,
                        "managed-audit-candidate-version-not-supplied"
                ),
                sourceFor(normalizedManagedAuditCandidateVersion, "x-orderops-managed-audit-candidate-version"),
                valueOrPlaceholder(
                        normalizedManagedAuditCandidateDigest,
                        "managed-audit-candidate-digest-not-supplied"
                ),
                sourceFor(normalizedManagedAuditCandidateDigest, "x-orderops-managed-audit-candidate-digest"),
                valueOrPlaceholder(
                        normalizedManagedAuditSinkMode,
                        "managed-audit-sink-mode-not-supplied"
                ),
                sourceFor(normalizedManagedAuditSinkMode, "x-orderops-managed-audit-sink-mode"),
                valueOrPlaceholder(
                        normalizedManagedAuditRetentionDays,
                        "managed-audit-retention-days-not-supplied"
                ),
                sourceFor(normalizedManagedAuditRetentionDays, "x-orderops-managed-audit-retention-days"),
                valueOrPlaceholder(
                        normalizedManagedAuditRotationPolicy,
                        "managed-audit-rotation-policy-not-supplied"
                ),
                sourceFor(normalizedManagedAuditRotationPolicy, "x-orderops-managed-audit-rotation-policy"),
                candidateVersionEchoed,
                candidateDigestEchoed,
                sinkModeEchoed,
                retentionDaysEchoed,
                rotationPolicyEchoed,
                auditPersistenceHandoffContextComplete,
                managedAuditRetentionWithinJavaRetention,
                javaAuditSourceReadOnly,
                false,
                false,
                false,
                false,
                javaAuditSourceReadOnly,
                false,
                List.of(
                        "x-orderops-managed-audit-candidate-version",
                        "x-orderops-managed-audit-candidate-digest",
                        "x-orderops-managed-audit-sink-mode",
                        "x-orderops-managed-audit-retention-days",
                        "x-orderops-managed-audit-rotation-policy"
                ),
                List.of(
                        "sampledAt",
                        "requestContext.requestId",
                        "requestContext.operatorIdentity",
                        "requestContext.auditCorrelationId",
                        "operatorWindowHint.operatorId",
                        "operatorWindowHint.operatorRoles",
                        "operatorWindowHint.approvalCorrelationId",
                        "ciEvidenceHint.manifestDigest",
                        "artifactRetentionHint.sourceRetentionFixtureEndpoint",
                        "artifactRetentionHint.javaRetentionDays",
                        "liveReadinessHint.runtimeSmokeSessionId",
                        "failureTaxonomy.failureCategories",
                        "verificationHint.warningDigest",
                        "executionBoundaries.nodeMayWriteApprovalLedger"
                ),
                List.of(
                        RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                        retentionFixture.fixtureEndpoint(),
                        "/api/v1/ops/evidence"
                ),
                List.copyOf(warnings),
                List.of(
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateVersion with Node v208 candidate contract",
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateDigest with Node v208 adapter digest",
                        "Require auditPersistenceHandoffHint.managedAuditRetentionWithinJavaRetention=true before Node dry-run retention checks",
                        "Persist only the listed handoffFieldPaths in Node managed audit dry-run storage",
                        "Keep javaManagedAuditWriteAllowed=false and nodeMayTreatAsProductionAuditRecord=false"
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy releaseApprovalRehearsalFailureTaxonomy(
            OpsEvidenceResponse evidence,
            String normalizedRequestId,
            String normalizedOperatorIdentity,
            String normalizedAuditCorrelationId
    ) {
        boolean upstreamReady = evidence.readOnlyWindow().readyForReadOnlyLiveProbe()
                && evidence.healthProbe().liveProbeRequiredForPass()
                && !evidence.healthProbe().staticSampleOnly()
                && evidence.readOnly();
        boolean authContextComplete = normalizedRequestId != null && normalizedOperatorIdentity != null;
        boolean auditCorrelationPresent = normalizedAuditCorrelationId != null;

        List<String> failureCategories = new ArrayList<>();
        List<String> taxonomyWarnings = new ArrayList<>();

        if (!upstreamReady) {
            failureCategories.add("UPSTREAM_READINESS_WARNING");
            taxonomyWarnings.add("JAVA_READ_ONLY_UPSTREAM_NOT_READY");
        }
        if (!authContextComplete) {
            failureCategories.add("AUTH_CONTEXT_WARNING");
            taxonomyWarnings.add("REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING");
        }
        if (!auditCorrelationPresent) {
            failureCategories.add("AUDIT_CORRELATION_WARNING");
            taxonomyWarnings.add("AUDIT_CORRELATION_ID_MISSING");
        }
        failureCategories.add("READ_ONLY_EXECUTION_BLOCKED");
        taxonomyWarnings.add("REHEARSAL_REMAINS_READ_ONLY");

        return new ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy(
                RELEASE_APPROVAL_REHEARSAL_FAILURE_TAXONOMY_VERSION,
                readinessStatus(upstreamReady),
                readinessStatus(authContextComplete),
                readinessStatus(auditCorrelationPresent),
                upstreamReady,
                authContextComplete,
                auditCorrelationPresent,
                true,
                false,
                List.copyOf(failureCategories),
                List.copyOf(taxonomyWarnings)
        );
    }

    private String readinessStatus(boolean ready) {
        if (ready) {
            return "READY";
        }
        return "WARNING";
    }

    private ReleaseApprovalRehearsalResponse.RehearsalRequestContext rehearsalRequestContext(
            String normalizedRequestId,
            String normalizedOperatorIdentity,
            String normalizedAuditCorrelationId
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(warnings, normalizedRequestId, "REHEARSAL_REQUEST_ID_MISSING");
        addMissingContextWarning(warnings, normalizedOperatorIdentity, "OPERATOR_IDENTITY_MISSING");
        addMissingContextWarning(warnings, normalizedAuditCorrelationId, "AUDIT_CORRELATION_ID_MISSING");

        return new ReleaseApprovalRehearsalResponse.RehearsalRequestContext(
                RELEASE_APPROVAL_REHEARSAL_CONTEXT_VERSION,
                valueOrPlaceholder(normalizedRequestId, "rehearsal-request-id-not-supplied"),
                sourceFor(normalizedRequestId, "X-Rehearsal-Request-Id"),
                valueOrPlaceholder(normalizedOperatorIdentity, "operator-identity-not-supplied"),
                sourceFor(normalizedOperatorIdentity, "X-Operator-Identity"),
                valueOrPlaceholder(normalizedAuditCorrelationId, "audit-correlation-id-not-supplied"),
                sourceFor(normalizedAuditCorrelationId, "X-Audit-Correlation-Id"),
                false,
                false,
                false,
                false,
                List.of(
                        "X-Rehearsal-Request-Id",
                        "X-Operator-Identity",
                        "X-Audit-Correlation-Id"
                ),
                List.copyOf(warnings)
        );
    }

    private String normalizeHeaderValue(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String valueOrPlaceholder(String value, String placeholder) {
        if (value == null) {
            return placeholder;
        }
        return value;
    }

    private String sourceFor(String value, String headerName) {
        if (value == null) {
            return "NOT_SUPPLIED";
        }
        return headerName;
    }

    private boolean retentionDaysWithinJavaRetention(String value, int javaRetentionDays) {
        if (value == null) {
            return false;
        }
        try {
            int retentionDays = Integer.parseInt(value);
            return retentionDays > 0 && retentionDays <= javaRetentionDays;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void addMissingContextWarning(List<String> warnings, String value, String warning) {
        if (value == null) {
            warnings.add(warning);
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

    private String value(Object value) {
        if (value == null) {
            return "<null>";
        }
        if (value instanceof List<?> list) {
            return "[" + String.join(",", list.stream().map(this::value).toList()) + "]";
        }
        return String.valueOf(value);
    }

    private OpsEvidenceResponse.Service service(Instant sampledAt) {
        return new OpsEvidenceResponse.Service(
                environment.getProperty("spring.application.name", "advanced-order-platform"),
                environment.getProperty("info.app.version", "0.1.0-SNAPSHOT"),
                profiles(),
                startedAt,
                Math.max(Duration.between(startedAt, sampledAt).toSeconds(), 0)
        );
    }

    private OpsEvidenceResponse.HealthProbe healthProbe(boolean staticSampleOnly) {
        List<String> additionalProbeEndpoints = new ArrayList<>();
        additionalProbeEndpoints.add("/api/v1/ops/overview");
        additionalProbeEndpoints.add(RELEASE_APPROVAL_REHEARSAL_ENDPOINT);
        additionalProbeEndpoints.addAll(staticContractEndpoints(false));

        return new OpsEvidenceResponse.HealthProbe(
                "/actuator/health",
                "GET",
                "UP",
                "/api/v1/ops/evidence",
                List.copyOf(additionalProbeEndpoints),
                true,
                staticSampleOnly
        );
    }

    private OpsEvidenceResponse.ReadOnlyWindow readOnlyWindow(boolean readyForReadOnlyLiveProbe) {
        List<String> allowedProbeEndpoints = new ArrayList<>();
        allowedProbeEndpoints.add("GET /actuator/health");
        allowedProbeEndpoints.add("GET /api/v1/ops/overview");
        allowedProbeEndpoints.add("GET /api/v1/ops/evidence");
        allowedProbeEndpoints.add("GET " + RELEASE_APPROVAL_REHEARSAL_ENDPOINT);
        allowedProbeEndpoints.addAll(staticContractProbeEndpoints(false));

        return new OpsEvidenceResponse.ReadOnlyWindow(
                "java-read-only-window.v1",
                true,
                false,
                true,
                false,
                readyForReadOnlyLiveProbe,
                false,
                List.copyOf(allowedProbeEndpoints),
                List.of(
                        "POST /api/v1/orders",
                        "POST /api/v1/failed-events/{id}/replay",
                        "RabbitMQ replay publish",
                        "Outbox mutation",
                        "Any non-GET Node upstream action"
                ),
                List.of(
                        "UPSTREAM_PROBES_ENABLED=true",
                        "UPSTREAM_ACTIONS_ENABLED=false"
                ),
                "Node real-read window must not call POST /api/v1/failed-events/{id}/replay"
        );
    }

    private OpsEvidenceResponse.OrderIdempotency orderIdempotency() {
        IdempotencyStoreDescriptor descriptor = idempotencyStore.descriptor();
        return new OpsEvidenceResponse.OrderIdempotency(
                "java-order-idempotency-boundary.v1",
                descriptor.abstractionVersion(),
                "/api/v1/orders",
                "POST",
                "Idempotency-Key",
                120,
                "order-create-request-sha256.v1",
                "customerId plus aggregated productId:quantity pairs sorted by productId",
                "HTTP 200 replay of the existing order without a second inventory reservation or outbox event",
                "HTTP 409 conflict before inventory reservation and before outbox mutation",
                "IDEMPOTENCY_KEY_REUSED_WITH_DIFFERENT_REQUEST",
                descriptor.activeStore(),
                descriptor.activeImplementation(),
                descriptor.activeMode(),
                descriptor.authoritativeStore() + " via " + descriptor.keyColumn()
                        + " and " + descriptor.fingerprintColumn(),
                List.of(
                        new OpsEvidenceResponse.IdempotencyStoreCandidate(
                                descriptor.activeStore(),
                                "ORDER_CREATE_IDEMPOTENCY_AUTHORITY",
                                true,
                                true,
                                descriptor.activeMode(),
                                "Default Java database-backed idempotency store"
                        ),
                        new OpsEvidenceResponse.IdempotencyStoreCandidate(
                                JpaIdempotencyStore.MINI_KV_CANDIDATE,
                                "TTL_TOKEN_CANDIDATE",
                                descriptor.miniKvAdapterEnabled(),
                                descriptor.miniKvConnected(),
                                descriptor.miniKvCandidateMode(),
                                descriptor.disabledCandidateReason()
                        )
                ),
                descriptor.miniKvConnected(),
                descriptor.externalTokenStoreConnected(),
                descriptor.changesPaymentOrInventoryTransaction()
        );
    }

    private OpsEvidenceResponse.ReleaseVerification releaseVerification() {
        return new OpsEvidenceResponse.ReleaseVerification(
                RELEASE_VERIFICATION_MANIFEST_VERSION,
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                "LOCAL_OPERATOR_EXECUTES_AND_ARCHIVES_RESULTS",
                List.of(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                ),
                staticContractEndpoints(true),
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.DeploymentRollback deploymentRollback() {
        return new OpsEvidenceResponse.DeploymentRollback(
                DEPLOYMENT_ROLLBACK_EVIDENCE_VERSION,
                DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                "READ_ONLY_BOUNDARY_SAMPLE",
                List.of(
                        "java-package",
                        "runtime-configuration",
                        "database-migrations",
                        "static-contracts"
                ),
                List.of(
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
                ),
                true,
                true,
                false,
                true,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseBundle releaseBundle() {
        return new OpsEvidenceResponse.ReleaseBundle(
                RELEASE_BUNDLE_MANIFEST_VERSION,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                "READ_ONLY_RELEASE_BUNDLE",
                "target/advanced-order-platform-0.1.0-SNAPSHOT.jar",
                staticContractEndpoints(true),
                List.of(
                        "focused-maven-tests",
                        "non-docker-regression-tests",
                        "maven-package",
                        "http-smoke",
                        "static-contract-json-validation"
                ),
                true,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseHandoffChecklistFixture releaseHandoffChecklistFixture() {
        return new OpsEvidenceResponse.ReleaseHandoffChecklistFixture(
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_VERSION,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                "READ_ONLY_RELEASE_HANDOFF_CHECKLIST_FIXTURE",
                "release-operator-placeholder",
                "rollback-approver-placeholder",
                "release-tag-or-artifact-version-placeholder",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                List.of(
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
                ),
                releaseHandoffChecklistArtifacts(),
                List.of(
                        "checklist-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-handoff-checklist",
                        "node-may-render-release-handoff-review-only"
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseAuditRetentionFixture releaseAuditRetentionFixture() {
        return new OpsEvidenceResponse.ReleaseAuditRetentionFixture(
                RELEASE_AUDIT_RETENTION_FIXTURE_VERSION,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                "READ_ONLY_RELEASE_AUDIT_RETENTION_FIXTURE",
                "release-retention-record-placeholder",
                "release-operator-placeholder",
                "release-tag-or-artifact-version-placeholder",
                180,
                releaseAuditRetentionEndpoints(),
                List.of(
                        "retention-id",
                        "release-operator",
                        "artifact-target",
                        "retention-days",
                        "evidence-endpoints",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture",
                        "audit-export-location-placeholder",
                        "no-secret-value-boundary"
                ),
                releaseAuditRetentionArtifacts(),
                List.of(
                        "retention-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-retention-record",
                        "node-may-render-retention-gate-only"
                ),
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ReleaseOperatorSignoffFixture releaseOperatorSignoffFixture() {
        return new OpsEvidenceResponse.ReleaseOperatorSignoffFixture(
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_VERSION,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                "READ_ONLY_RELEASE_OPERATOR_SIGNOFF_FIXTURE",
                "release-operator-placeholder",
                "rollback-approver-placeholder",
                "release-window-placeholder",
                "release-tag-or-artifact-version-placeholder",
                "operator-signoff-placeholder",
                List.of(
                        "release-operator",
                        "rollback-approver",
                        "release-window",
                        "artifact-target",
                        "operator-signoff-placeholder",
                        "release-audit-retention-fixture",
                        "rollback-approver-evidence-fixture",
                        "no-secret-value-boundary"
                ),
                releaseOperatorSignoffArtifacts(),
                List.of(
                        "signoff-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-signoff",
                        "node-may-render-approval-prerequisite-gate-only"
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.RollbackApproverEvidenceFixture rollbackApproverEvidenceFixture() {
        return new OpsEvidenceResponse.RollbackApproverEvidenceFixture(
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_VERSION,
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                "READ_ONLY_ROLLBACK_APPROVER_EVIDENCE_FIXTURE",
                "rollback-approver-placeholder",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                "rollback-sql-artifact-reference-placeholder",
                "production-database-connection-outside-this-fixture",
                List.of(
                        "rollback-approver",
                        "database-migration-direction",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                ),
                rollbackApproverEvidenceArtifacts(),
                List.of(
                        "rollback-approver-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approver-evidence",
                        "node-may-render-decision-rehearsal-input-only"
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.RollbackApprovalHandoff rollbackApprovalHandoff() {
        return new OpsEvidenceResponse.RollbackApprovalHandoff(
                ROLLBACK_APPROVAL_HANDOFF_VERSION,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                "OPERATOR_CONFIRMATION_REQUIRED",
                List.of(
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
                ),
                List.of(
                        RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                        RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                        RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                        ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                        ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                        PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                        PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                        RELEASE_VERIFICATION_MANIFEST_ENDPOINT
                ),
                true,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.RollbackApprovalRecordFixture rollbackApprovalRecordFixture() {
        return new OpsEvidenceResponse.RollbackApprovalRecordFixture(
                ROLLBACK_APPROVAL_RECORD_FIXTURE_VERSION,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                "READ_ONLY_APPROVAL_RECORD_FIXTURE",
                "rollback-reviewer-placeholder",
                "approval-timestamp-placeholder",
                "release-tag-or-artifact-version-placeholder",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                List.of(
                        "reviewer",
                        "approval-timestamp-placeholder",
                        "rollback-target",
                        "database-migration-direction",
                        "rollback-sql-review-gate",
                        "no-secret-value-boundary"
                ),
                List.of(
                        ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                        ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                        ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                        PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                        PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT
                ),
                List.of(
                        "record-fixture-stores-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-approval-record",
                        "node-may-render-release-window-packet-only"
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.RollbackSqlReviewGate rollbackSqlReviewGate() {
        return new OpsEvidenceResponse.RollbackSqlReviewGate(
                ROLLBACK_SQL_REVIEW_GATE_VERSION,
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                "READ_ONLY_SQL_REVIEW_GATE",
                "database-release-owner",
                List.of(
                        "rollback-sql-review-owner",
                        "migration-direction",
                        "operator-approval-placeholder",
                        "rollback-sql-artifact-reference",
                        "production-database-access-boundary"
                ),
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "operator-approval-required-before-any-sql-execution",
                true,
                false,
                false,
                false,
                false
        );
    }

    private List<String> staticContractEndpoints(boolean includeFieldGuide) {
        List<String> endpoints = new ArrayList<>();
        endpoints.add("/contracts/ops-read-only-evidence.sample.json");
        if (includeFieldGuide) {
            endpoints.add("/contracts/ops-evidence-field-guide.sample.json");
        }
        endpoints.addAll(List.of(
                "/contracts/order-idempotency-boundary.sample.json",
                "/contracts/order-idempotency-store-abstraction.sample.json",
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
        ));
        return List.copyOf(endpoints);
    }

    private List<String> staticContractProbeEndpoints(boolean includeFieldGuide) {
        return staticContractEndpoints(includeFieldGuide).stream()
                .map(endpoint -> "GET " + endpoint)
                .toList();
    }

    private List<String> releaseHandoffChecklistArtifacts() {
        return List.of(
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT
        );
    }

    private List<String> releaseAuditRetentionEndpoints() {
        return List.of(
                "/api/v1/ops/evidence",
                RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                "/api/v1/failed-events/replay-evidence-index",
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
        );
    }

    private List<String> releaseAuditRetentionArtifacts() {
        return List.of(
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
        );
    }

    private List<String> releaseOperatorSignoffArtifacts() {
        return List.of(
                RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
        );
    }

    private List<String> rollbackApproverEvidenceArtifacts() {
        return List.of(
                ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                RELEASE_BUNDLE_MANIFEST_ENDPOINT
        );
    }

    private OpsEvidenceResponse.ProductionSecretSourceContract productionSecretSourceContract() {
        return new OpsEvidenceResponse.ProductionSecretSourceContract(
                PRODUCTION_SECRET_SOURCE_CONTRACT_VERSION,
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                "READ_ONLY_SECRET_SOURCE_CONTRACT",
                List.of(
                        "external-secret-manager",
                        "environment-injected-secret",
                        "platform-managed-secret"
                ),
                "external-secret-manager",
                "platform-security-owner",
                "security-operations-owner",
                "quarterly-or-before-production-cutover",
                List.of(
                        "secret-manager-or-source-type",
                        "secret-manager-owner",
                        "rotation-owner",
                        "review-cadence",
                        "secret-value-access-boundary"
                ),
                List.of(
                        "contract-records-source-metadata-only",
                        "secret-values-must-not-be-read",
                        "secret-values-must-not-be-embedded-in-static-contracts",
                        "node-may-render-checklist-only"
                ),
                true,
                false,
                false,
                false,
                false
        );
    }

    private OpsEvidenceResponse.ProductionDeploymentRunbookContract productionDeploymentRunbookContract() {
        return new OpsEvidenceResponse.ProductionDeploymentRunbookContract(
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_VERSION,
                PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT,
                "READ_ONLY_DEPLOYMENT_RUNBOOK_CONTRACT",
                "release-window-owner",
                "rollback-approval-owner",
                List.of(
                        "forward-only",
                        "rollback-script-reviewed",
                        "no-database-change"
                ),
                "no-database-change",
                PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT,
                List.of(
                        "deployment-window-owner",
                        "rollback-approver",
                        "database-migration-direction",
                        "secret-source-confirmation",
                        "rollback-sql-review-gate",
                        "operator-approval-placeholder",
                        "release-audit-retention-fixture",
                        "release-operator-signoff-fixture",
                        "rollback-approver-evidence-fixture"
                ),
                List.of(
                        RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                        DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                        RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT,
                        RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT,
                        RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                        ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                        ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
                        ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                        ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
                        PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
                ),
                true,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private List<String> profiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0) {
            return List.copyOf(Arrays.asList(activeProfiles));
        }
        return List.copyOf(Arrays.asList(environment.getDefaultProfiles()));
    }

    private OpsEvidenceResponse.FailedEventReplay failedEventReplay(FailedEventSummaryResponse summary) {
        return new OpsEvidenceResponse.FailedEventReplay(
                summary.totalFailedEvents(),
                summary.replayBacklog(),
                summary.pendingReplayApprovals(),
                summary.approvedReplayApprovals(),
                summary.rejectedReplayApprovals(),
                summary.latestFailedAt(),
                summary.latestApprovalAt(),
                REAL_REPLAY_ENDPOINT,
                false
        );
    }

    private OpsEvidenceResponse.Outbox outbox(long pendingOutboxEvents, List<String> outboxBlockers) {
        return new OpsEvidenceResponse.Outbox(
                pendingOutboxEvents,
                outboxPublisherProperties.isEnabled(),
                outboxRabbitMqProperties.isEnabled(),
                outboxRabbitMqProperties.getExchange(),
                outboxRabbitMqProperties.getQueue(),
                outboxRabbitMqProperties.getDeadLetterQueue(),
                outboxBlockers
        );
    }

    private OpsEvidenceResponse.ApprovalExecution approvalExecution(List<String> executionBlockers) {
        return new OpsEvidenceResponse.ApprovalExecution(
                "APPROVED",
                "contractDigest must match latest approval-status/readiness evidence before POST /replay",
                true,
                true,
                executionBlockers,
                List.of(
                        "GET /api/v1/failed-events/summary",
                        "GET /api/v1/failed-events/{id}/replay-readiness",
                        "GET /api/v1/failed-events/{id}/replay-execution-contract"
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.ReleaseApprovalInputs releaseApprovalInputs(
            OpsEvidenceResponse evidence
    ) {
        return new ReleaseApprovalRehearsalResponse.ReleaseApprovalInputs(
                evidence.releaseOperatorSignoffFixture().fixtureEndpoint(),
                evidence.rollbackApproverEvidenceFixture().fixtureEndpoint(),
                evidence.rollbackApprovalRecordFixture().fixtureEndpoint(),
                evidence.releaseBundle().manifestEndpoint(),
                evidence.releaseVerification().manifestEndpoint(),
                evidence.deploymentRollback().evidenceEndpoint(),
                evidence.productionDeploymentRunbookContract().contractEndpoint(),
                evidence.productionSecretSourceContract().contractEndpoint(),
                evidence.rollbackSqlReviewGate().gateEndpoint(),
                List.of(
                        evidence.releaseOperatorSignoffFixture().fixtureEndpoint(),
                        evidence.rollbackApproverEvidenceFixture().fixtureEndpoint(),
                        evidence.rollbackApprovalRecordFixture().fixtureEndpoint(),
                        evidence.releaseBundle().manifestEndpoint(),
                        evidence.releaseVerification().manifestEndpoint(),
                        evidence.deploymentRollback().evidenceEndpoint(),
                        evidence.productionDeploymentRunbookContract().contractEndpoint(),
                        evidence.productionSecretSourceContract().contractEndpoint(),
                        evidence.rollbackSqlReviewGate().gateEndpoint()
                )
        );
    }

    private ReleaseApprovalRehearsalResponse.LiveSignals liveSignals(OpsEvidenceResponse evidence) {
        return new ReleaseApprovalRehearsalResponse.LiveSignals(
                evidence.failedEventReplay().pendingReplayApprovals(),
                evidence.failedEventReplay().approvedReplayApprovals(),
                evidence.failedEventReplay().rejectedReplayApprovals(),
                evidence.failedEventReplay().replayBacklog(),
                evidence.outbox().pendingEvents(),
                evidence.failedEventReplay().realReplayAllowedByEvidence(),
                evidence.approvalExecution().dryRun(),
                evidence.executionAllowed()
        );
    }

    private ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries() {
        return new ReleaseApprovalRehearsalResponse.ExecutionBoundaries(
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    private List<String> releaseApprovalRehearsalBlockers(OpsEvidenceResponse evidence) {
        List<String> blockers = new ArrayList<>();
        blockers.add("READ_ONLY_RELEASE_APPROVAL_REHEARSAL");
        blockers.addAll(evidence.approvalExecution().executionBlockers());
        if (!evidence.releaseOperatorSignoffFixture().nodeMayCreateApprovalDecision()) {
            blockers.add("APPROVAL_DECISION_CREATION_DISABLED");
        }
        if (!evidence.rollbackApproverEvidenceFixture().nodeMayCreateApprovalDecision()) {
            blockers.add("ROLLBACK_APPROVER_DECISION_CREATION_DISABLED");
        }
        if (!evidence.productionDeploymentRunbookContract().nodeMayTriggerDeployment()) {
            blockers.add("DEPLOYMENT_EXECUTION_DISABLED");
        }
        if (!evidence.rollbackSqlReviewGate().sqlExecutionAllowed()) {
            blockers.add("ROLLBACK_SQL_EXECUTION_DISABLED");
        }
        return List.copyOf(blockers);
    }

    private List<String> releaseApprovalNextEvidenceActions() {
        return List.of(
                "GET /api/v1/ops/evidence",
                "GET " + RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                "GET " + RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                "GET " + ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                "GET " + ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                "Keep UPSTREAM_ACTIONS_ENABLED=false"
        );
    }

    private List<String> outboxBlockers() {
        List<String> blockers = new ArrayList<>();
        if (!outboxPublisherProperties.isEnabled()) {
            blockers.add("OUTBOX_PUBLISHER_DISABLED");
        }
        if (!outboxRabbitMqProperties.isEnabled()) {
            blockers.add("RABBITMQ_OUTBOX_DISABLED");
        }
        return List.copyOf(blockers);
    }

    private List<String> executionBlockers(FailedEventSummaryResponse summary) {
        List<String> blockers = new ArrayList<>();
        blockers.add("READ_ONLY_EVIDENCE_ENDPOINT");
        if (summary.pendingReplayApprovals() > 0) {
            blockers.add("REPLAY_APPROVAL_PENDING");
        }
        if (summary.rejectedReplayApprovals() > 0) {
            blockers.add("REPLAY_APPROVAL_REJECTED");
        }
        if (summary.replayBacklog() > 0) {
            blockers.add("REPLAY_BACKLOG_PRESENT");
        }
        return List.copyOf(blockers);
    }

    private List<String> blockers(List<String> outboxBlockers, List<String> executionBlockers) {
        List<String> blockers = new ArrayList<>();
        blockers.addAll(executionBlockers);
        blockers.addAll(outboxBlockers);
        return List.copyOf(blockers);
    }

    private List<String> warnings(FailedEventSummaryResponse summary, long pendingOutboxEvents) {
        List<String> warnings = new ArrayList<>();
        if (pendingOutboxEvents > 0) {
            warnings.add("OUTBOX_PENDING_EVENTS");
        }
        if (summary.approvedReplayApprovals() > 0) {
            warnings.add("APPROVED_REPLAY_REQUIRES_DIGEST_CHECK");
        }
        return List.copyOf(warnings);
    }

    private List<String> evidenceEndpoints() {
        List<String> endpoints = new ArrayList<>();
        endpoints.add("/api/v1/ops/overview");
        endpoints.add("/api/v1/ops/evidence");
        endpoints.add(RELEASE_APPROVAL_REHEARSAL_ENDPOINT);
        endpoints.addAll(staticContractEndpoints(true));
        endpoints.addAll(List.of(
                "/api/v1/failed-events/summary",
                "/api/v1/failed-events/{id}/approval-status",
                "/api/v1/failed-events/{id}/replay-readiness",
                "/api/v1/failed-events/{id}/replay-execution-contract",
                "/api/v1/failed-events/replay-evidence-index",
                "/contracts/failed-event-replay-execution-contract-approved.sample.json",
                "/contracts/failed-event-replay-execution-contract-blocked.sample.json"
        ));
        return List.copyOf(endpoints);
    }
}
