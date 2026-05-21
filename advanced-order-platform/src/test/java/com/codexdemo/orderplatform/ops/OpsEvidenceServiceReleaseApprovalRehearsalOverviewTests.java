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
                .isEqualTo("java-release-approval-rehearsal-response-schema.v40");
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
    }
}
