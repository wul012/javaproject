package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalRehearsalHintBuilder {

    ReleaseApprovalRehearsalResponse.RehearsalRequestContext rehearsalRequestContext(
            String normalizedRequestId,
            String normalizedOperatorIdentity,
            String normalizedAuditCorrelationId
    ) {
        List<String> warnings = new ArrayList<>();
        addMissingContextWarning(warnings, normalizedRequestId, "REHEARSAL_REQUEST_ID_MISSING");
        addMissingContextWarning(warnings, normalizedOperatorIdentity, "OPERATOR_IDENTITY_MISSING");
        addMissingContextWarning(warnings, normalizedAuditCorrelationId, "AUDIT_CORRELATION_ID_MISSING");

        return new ReleaseApprovalRehearsalResponse.RehearsalRequestContext(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_CONTEXT_VERSION,
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

    ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint rehearsalOperatorWindowHint(
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
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_OPERATOR_WINDOW_HINT_VERSION,
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

    ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint rehearsalCiEvidenceHint(
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
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_CI_EVIDENCE_HINT_VERSION,
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

    ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint rehearsalArtifactRetentionHint(
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
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ARTIFACT_RETENTION_HINT_VERSION,
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

    ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint rehearsalLiveReadinessHint(
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
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_LIVE_READINESS_HINT_VERSION,
                evidence.sampledAt(),
                "sampledAt",
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
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
                        .contains("GET " + OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT),
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
                        "GET " + OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT
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
}
