package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests extends OpsEvidenceServiceRehearsalTestSupport {

    @Test
    void buildsReleaseApprovalRehearsalOverviewForDefaultRequest() {
        OpsEvidenceService service = readOnlyFixtureService();

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
                .isEqualTo("java-release-approval-rehearsal-response-schema.v36");
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
    }
}
