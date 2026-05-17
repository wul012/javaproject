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
        ContextHeaderField requestId = ContextHeaderField.from(
                normalizedRequestId,
                "X-Rehearsal-Request-Id",
                "rehearsal-request-id-not-supplied"
        );
        ContextHeaderField operatorIdentity = ContextHeaderField.from(
                normalizedOperatorIdentity,
                "X-Operator-Identity",
                "operator-identity-not-supplied"
        );
        ContextHeaderField auditCorrelationId = ContextHeaderField.from(
                normalizedAuditCorrelationId,
                "X-Audit-Correlation-Id",
                "audit-correlation-id-not-supplied"
        );
        requestId.addMissingWarning(warnings, "REHEARSAL_REQUEST_ID_MISSING");
        operatorIdentity.addMissingWarning(warnings, "OPERATOR_IDENTITY_MISSING");
        auditCorrelationId.addMissingWarning(warnings, "AUDIT_CORRELATION_ID_MISSING");
        RequestContextFlags requestContextFlags = RequestContextFlags.readOnlyRehearsal();

        return new ReleaseApprovalRehearsalResponse.RehearsalRequestContext(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_CONTEXT_VERSION,
                requestId.value(),
                requestId.source(),
                operatorIdentity.value(),
                operatorIdentity.source(),
                auditCorrelationId.value(),
                auditCorrelationId.source(),
                requestContextFlags.operatorAuthenticatedByJava(),
                requestContextFlags.persistedByJava(),
                requestContextFlags.approvalLedgerWritten(),
                requestContextFlags.requiresProductionIdentityProvider(),
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
        ContextHeaderField operatorId = ContextHeaderField.from(
                normalizedOperatorWindowOperatorId,
                "x-orderops-operator-id",
                "orderops-operator-id-not-supplied"
        );
        ContextHeaderField operatorRoles = ContextHeaderField.from(
                normalizedOperatorWindowRoles,
                "x-orderops-roles",
                "orderops-roles-not-supplied"
        );
        ContextHeaderField operatorVerifiedClaim = ContextHeaderField.from(
                normalizedOperatorWindowVerifiedClaim,
                "x-orderops-operator-verified",
                "orderops-operator-verified-not-supplied"
        );
        ContextHeaderField approvalCorrelationId = ContextHeaderField.from(
                normalizedOperatorWindowApprovalCorrelationId,
                "x-orderops-approval-correlation-id",
                "orderops-approval-correlation-id-not-supplied"
        );
        operatorId.addMissingWarning(warnings, "ORDEROPS_OPERATOR_ID_MISSING");
        operatorRoles.addMissingWarning(warnings, "ORDEROPS_OPERATOR_ROLES_MISSING");
        operatorVerifiedClaim.addMissingWarning(warnings, "ORDEROPS_OPERATOR_VERIFIED_CLAIM_MISSING");
        approvalCorrelationId.addMissingWarning(warnings, "ORDEROPS_APPROVAL_CORRELATION_ID_MISSING");
        OperatorWindowFlags operatorWindowFlags = OperatorWindowFlags.fromEchoes(
                operatorId.echoed(),
                operatorRoles.echoed(),
                operatorVerifiedClaim.echoed(),
                approvalCorrelationId.echoed()
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_OPERATOR_WINDOW_HINT_VERSION,
                operatorId.value(),
                operatorId.source(),
                operatorRoles.value(),
                operatorRoles.source(),
                operatorVerifiedClaim.value(),
                operatorVerifiedClaim.source(),
                approvalCorrelationId.value(),
                approvalCorrelationId.source(),
                operatorWindowFlags.operatorIdentityEchoed(),
                operatorWindowFlags.operatorRolesEchoed(),
                operatorWindowFlags.operatorVerifiedClaimEchoed(),
                operatorWindowFlags.approvalCorrelationEchoed(),
                operatorWindowFlags.operatorWindowContextComplete(),
                operatorWindowFlags.productionIdpVerifiedByJava(),
                operatorWindowFlags.persistedApprovalRecordByJava(),
                operatorWindowFlags.nodeMayTreatAsProductionIdentity(),
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
        ContextHeaderField manifestVersion = ContextHeaderField.from(
                normalizedCiManifestVersion,
                "x-orderops-ci-manifest-version",
                "ci-manifest-profile-version-not-supplied"
        );
        ContextHeaderField manifestDigest = ContextHeaderField.from(
                normalizedCiManifestDigest,
                "x-orderops-ci-manifest-digest",
                "ci-manifest-digest-not-supplied"
        );
        ContextHeaderField manifestEndpoint = ContextHeaderField.from(
                normalizedCiManifestEndpoint,
                "x-orderops-ci-manifest-endpoint",
                "ci-manifest-endpoint-not-supplied"
        );
        ContextHeaderField artifactRecordCount = ContextHeaderField.from(
                normalizedCiArtifactRecordCount,
                "x-orderops-ci-artifact-record-count",
                "ci-artifact-record-count-not-supplied"
        );
        ContextHeaderField approvalCorrelationId = ContextHeaderField.from(
                normalizedCiApprovalCorrelationId,
                "x-orderops-ci-approval-correlation-id",
                "ci-approval-correlation-id-not-supplied"
        );
        manifestVersion.addMissingWarning(warnings, "ORDEROPS_CI_MANIFEST_VERSION_MISSING");
        manifestDigest.addMissingWarning(warnings, "ORDEROPS_CI_MANIFEST_DIGEST_MISSING");
        manifestEndpoint.addMissingWarning(warnings, "ORDEROPS_CI_MANIFEST_ENDPOINT_MISSING");
        artifactRecordCount.addMissingWarning(warnings, "ORDEROPS_CI_ARTIFACT_RECORD_COUNT_MISSING");
        approvalCorrelationId.addMissingWarning(warnings, "ORDEROPS_CI_APPROVAL_CORRELATION_ID_MISSING");
        CiEvidenceFlags ciEvidenceFlags = CiEvidenceFlags.fromEchoes(
                manifestVersion.echoed(),
                manifestDigest.echoed(),
                manifestEndpoint.echoed(),
                artifactRecordCount.echoed(),
                approvalCorrelationId.echoed()
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_CI_EVIDENCE_HINT_VERSION,
                manifestVersion.value(),
                manifestVersion.source(),
                manifestDigest.value(),
                manifestDigest.source(),
                manifestEndpoint.value(),
                manifestEndpoint.source(),
                artifactRecordCount.value(),
                artifactRecordCount.source(),
                approvalCorrelationId.value(),
                approvalCorrelationId.source(),
                ciEvidenceFlags.manifestProfileVersionEchoed(),
                ciEvidenceFlags.manifestDigestEchoed(),
                ciEvidenceFlags.manifestEndpointEchoed(),
                ciEvidenceFlags.artifactRecordCountEchoed(),
                ciEvidenceFlags.approvalCorrelationEchoed(),
                ciEvidenceFlags.ciEvidenceContextComplete(),
                ciEvidenceFlags.noLedgerWriteProof(),
                ciEvidenceFlags.noLedgerWriteProved(),
                ciEvidenceFlags.ciArtifactUploadedByJava(),
                ciEvidenceFlags.githubArtifactAccessedByJava(),
                ciEvidenceFlags.productionWindowAllowedByJava(),
                ciEvidenceFlags.nodeMayTreatAsCiArtifactPublication(),
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
        ContextHeaderField uploadContractVersion = ContextHeaderField.from(
                normalizedCiUploadContractVersion,
                "x-orderops-ci-upload-contract-version",
                "ci-upload-contract-version-not-supplied"
        );
        ContextHeaderField uploadContractDigest = ContextHeaderField.from(
                normalizedCiUploadContractDigest,
                "x-orderops-ci-upload-contract-digest",
                "ci-upload-contract-digest-not-supplied"
        );
        ContextHeaderField artifactName = ContextHeaderField.from(
                normalizedCiArtifactName,
                "x-orderops-ci-artifact-name",
                "ci-artifact-name-not-supplied"
        );
        ContextHeaderField artifactRoot = ContextHeaderField.from(
                normalizedCiArtifactRoot,
                "x-orderops-ci-artifact-root",
                "ci-artifact-root-not-supplied"
        );
        ContextHeaderField retentionDays = ContextHeaderField.from(
                normalizedCiRetentionDays,
                "x-orderops-ci-retention-days",
                "ci-retention-days-not-supplied"
        );
        ContextHeaderField uploadMode = ContextHeaderField.from(
                normalizedCiUploadMode,
                "x-orderops-ci-upload-mode",
                "ci-upload-mode-not-supplied"
        );
        uploadContractVersion.addMissingWarning(warnings, "ORDEROPS_CI_UPLOAD_CONTRACT_VERSION_MISSING");
        uploadContractDigest.addMissingWarning(warnings, "ORDEROPS_CI_UPLOAD_CONTRACT_DIGEST_MISSING");
        artifactName.addMissingWarning(warnings, "ORDEROPS_CI_ARTIFACT_NAME_MISSING");
        artifactRoot.addMissingWarning(warnings, "ORDEROPS_CI_ARTIFACT_ROOT_MISSING");
        retentionDays.addMissingWarning(warnings, "ORDEROPS_CI_RETENTION_DAYS_MISSING");
        uploadMode.addMissingWarning(warnings, "ORDEROPS_CI_UPLOAD_MODE_MISSING");
        boolean retentionDaysWithinJavaRetention = retentionDaysWithinJavaRetention(
                normalizedCiRetentionDays,
                retentionFixture.retentionDays()
        );
        ArtifactRetentionFlags artifactRetentionFlags = ArtifactRetentionFlags.fromEchoes(
                uploadContractVersion.echoed(),
                uploadContractDigest.echoed(),
                artifactName.echoed(),
                artifactRoot.echoed(),
                retentionDays.echoed(),
                uploadMode.echoed(),
                retentionDaysWithinJavaRetention,
                retentionFixture.nodeMayConsume()
                        && retentionFixture.auditExportReadOnly()
                        && !retentionFixture.deploymentExecutionAllowed()
                        && !retentionFixture.rollbackSqlExecutionAllowed(),
                retentionFixture.auditExportReadOnly()
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ARTIFACT_RETENTION_HINT_VERSION,
                retentionFixture.fixtureVersion(),
                retentionFixture.fixtureEndpoint(),
                retentionFixture.retentionId(),
                retentionFixture.artifactTarget(),
                retentionFixture.retentionDays(),
                uploadContractVersion.value(),
                uploadContractVersion.source(),
                uploadContractDigest.value(),
                uploadContractDigest.source(),
                artifactName.value(),
                artifactName.source(),
                artifactRoot.value(),
                artifactRoot.source(),
                retentionDays.value(),
                retentionDays.source(),
                uploadMode.value(),
                uploadMode.source(),
                artifactRetentionFlags.uploadContractVersionEchoed(),
                artifactRetentionFlags.uploadContractDigestEchoed(),
                artifactRetentionFlags.artifactNameEchoed(),
                artifactRetentionFlags.artifactRootEchoed(),
                artifactRetentionFlags.retentionDaysEchoed(),
                artifactRetentionFlags.uploadModeEchoed(),
                artifactRetentionFlags.artifactRetentionContextComplete(),
                artifactRetentionFlags.retentionDaysWithinJavaRetention(),
                artifactRetentionFlags.javaRetentionFixtureReadOnly(),
                artifactRetentionFlags.auditExportReadOnly(),
                artifactRetentionFlags.ciArtifactUploadedByJava(),
                artifactRetentionFlags.githubArtifactAccessedByJava(),
                artifactRetentionFlags.productionWindowAllowedByJava(),
                artifactRetentionFlags.nodeMayTreatAsRetentionAuthorization(),
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
        ContextHeaderField runtimePreflightVersion = ContextHeaderField.from(
                normalizedRuntimePreflightVersion,
                "x-orderops-runtime-preflight-version",
                "runtime-preflight-version-not-supplied"
        );
        ContextHeaderField runtimePreflightDigest = ContextHeaderField.from(
                normalizedRuntimePreflightDigest,
                "x-orderops-runtime-preflight-digest",
                "runtime-preflight-digest-not-supplied"
        );
        ContextHeaderField runtimeSmokeSessionId = ContextHeaderField.from(
                normalizedRuntimeSmokeSessionId,
                "x-orderops-runtime-smoke-session-id",
                "runtime-smoke-session-id-not-supplied"
        );
        ContextHeaderField runtimeReadTargetId = ContextHeaderField.from(
                normalizedRuntimeReadTargetId,
                "x-orderops-runtime-read-target-id",
                "runtime-read-target-id-not-supplied"
        );
        ContextHeaderField runtimeWindowMode = ContextHeaderField.from(
                normalizedRuntimeWindowMode,
                "x-orderops-runtime-window-mode",
                "runtime-window-mode-not-supplied"
        );
        runtimePreflightVersion.addMissingWarning(warnings, "ORDEROPS_RUNTIME_PREFLIGHT_VERSION_MISSING");
        runtimePreflightDigest.addMissingWarning(warnings, "ORDEROPS_RUNTIME_PREFLIGHT_DIGEST_MISSING");
        runtimeSmokeSessionId.addMissingWarning(warnings, "ORDEROPS_RUNTIME_SMOKE_SESSION_ID_MISSING");
        runtimeReadTargetId.addMissingWarning(warnings, "ORDEROPS_RUNTIME_READ_TARGET_ID_MISSING");
        runtimeWindowMode.addMissingWarning(warnings, "ORDEROPS_RUNTIME_WINDOW_MODE_MISSING");
        boolean liveReadinessContextComplete = ContextHeaderField.allEchoed(
                runtimePreflightVersion,
                runtimePreflightDigest,
                runtimeSmokeSessionId,
                runtimeReadTargetId,
                runtimeWindowMode
        );
        LiveReadinessFlags liveReadinessFlags = LiveReadinessFlags.fromEchoes(
                runtimePreflightVersion.echoed(),
                runtimePreflightDigest.echoed(),
                runtimeSmokeSessionId.echoed(),
                runtimeReadTargetId.echoed(),
                runtimeWindowMode.echoed(),
                liveReadinessContextComplete,
                evidence.readOnly()
                        && !evidence.executionAllowed()
                        && evidence.readOnlyWindow().readyForReadOnlyLiveProbe(),
                evidence.readOnly()
                        && !evidence.executionAllowed()
                        && evidence.readOnlyWindow().allowedProbeEndpoints()
                        .contains("GET " + OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT)
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_LIVE_READINESS_HINT_VERSION,
                evidence.sampledAt(),
                "sampledAt",
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION,
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                "/actuator/health",
                runtimePreflightVersion.value(),
                runtimePreflightVersion.source(),
                runtimePreflightDigest.value(),
                runtimePreflightDigest.source(),
                runtimeSmokeSessionId.value(),
                runtimeSmokeSessionId.source(),
                runtimeReadTargetId.value(),
                runtimeReadTargetId.source(),
                runtimeWindowMode.value(),
                runtimeWindowMode.source(),
                liveReadinessFlags.sourcePreflightVersionEchoed(),
                liveReadinessFlags.sourcePreflightDigestEchoed(),
                liveReadinessFlags.runtimeSmokeSessionIdEchoed(),
                liveReadinessFlags.runtimeReadTargetIdEchoed(),
                liveReadinessFlags.runtimeWindowModeEchoed(),
                liveReadinessFlags.liveReadinessContextComplete(),
                liveReadinessFlags.readyForRuntimeSmokeRead(),
                liveReadinessFlags.readOnlyEndpointReady(),
                liveReadinessFlags.runtimeSmokeExecutedByJava(),
                liveReadinessFlags.nodeMustRecordPidAndCleanup(),
                liveReadinessFlags.javaStartedProcessForNode(),
                liveReadinessFlags.processCleanupRecordedByJava(),
                liveReadinessFlags.nodeMayTreatAsProductionAuthorization(),
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

    private record RequestContextFlags(
            boolean operatorAuthenticatedByJava,
            boolean persistedByJava,
            boolean approvalLedgerWritten,
            boolean requiresProductionIdentityProvider
    ) {

        static RequestContextFlags readOnlyRehearsal() {
            return new RequestContextFlags(false, false, false, false);
        }
    }

    private record OperatorWindowFlags(
            boolean operatorIdentityEchoed,
            boolean operatorRolesEchoed,
            boolean operatorVerifiedClaimEchoed,
            boolean approvalCorrelationEchoed,
            boolean operatorWindowContextComplete,
            boolean productionIdpVerifiedByJava,
            boolean persistedApprovalRecordByJava,
            boolean nodeMayTreatAsProductionIdentity
    ) {

        static OperatorWindowFlags fromEchoes(
                boolean operatorIdentityEchoed,
                boolean operatorRolesEchoed,
                boolean operatorVerifiedClaimEchoed,
                boolean approvalCorrelationEchoed
        ) {
            return new OperatorWindowFlags(
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
                    false
            );
        }
    }

    private record CiEvidenceFlags(
            boolean manifestProfileVersionEchoed,
            boolean manifestDigestEchoed,
            boolean manifestEndpointEchoed,
            boolean artifactRecordCountEchoed,
            boolean approvalCorrelationEchoed,
            boolean ciEvidenceContextComplete,
            String noLedgerWriteProof,
            boolean noLedgerWriteProved,
            boolean ciArtifactUploadedByJava,
            boolean githubArtifactAccessedByJava,
            boolean productionWindowAllowedByJava,
            boolean nodeMayTreatAsCiArtifactPublication
    ) {

        static CiEvidenceFlags fromEchoes(
                boolean manifestProfileVersionEchoed,
                boolean manifestDigestEchoed,
                boolean manifestEndpointEchoed,
                boolean artifactRecordCountEchoed,
                boolean approvalCorrelationEchoed
        ) {
            boolean ciEvidenceContextComplete = manifestProfileVersionEchoed
                    && manifestDigestEchoed
                    && manifestEndpointEchoed
                    && artifactRecordCountEchoed
                    && approvalCorrelationEchoed;
            return new CiEvidenceFlags(
                    manifestProfileVersionEchoed,
                    manifestDigestEchoed,
                    manifestEndpointEchoed,
                    artifactRecordCountEchoed,
                    approvalCorrelationEchoed,
                    ciEvidenceContextComplete,
                    "NO_LEDGER_WRITE_PROOF_BY_RESPONSE_FIELDS",
                    true,
                    false,
                    false,
                    false,
                    false
            );
        }
    }

    private record ArtifactRetentionFlags(
            boolean uploadContractVersionEchoed,
            boolean uploadContractDigestEchoed,
            boolean artifactNameEchoed,
            boolean artifactRootEchoed,
            boolean retentionDaysEchoed,
            boolean uploadModeEchoed,
            boolean artifactRetentionContextComplete,
            boolean retentionDaysWithinJavaRetention,
            boolean javaRetentionFixtureReadOnly,
            boolean auditExportReadOnly,
            boolean ciArtifactUploadedByJava,
            boolean githubArtifactAccessedByJava,
            boolean productionWindowAllowedByJava,
            boolean nodeMayTreatAsRetentionAuthorization
    ) {

        static ArtifactRetentionFlags fromEchoes(
                boolean uploadContractVersionEchoed,
                boolean uploadContractDigestEchoed,
                boolean artifactNameEchoed,
                boolean artifactRootEchoed,
                boolean retentionDaysEchoed,
                boolean uploadModeEchoed,
                boolean retentionDaysWithinJavaRetention,
                boolean javaRetentionFixtureReadOnly,
                boolean auditExportReadOnly
        ) {
            boolean artifactRetentionContextComplete = uploadContractVersionEchoed
                    && uploadContractDigestEchoed
                    && artifactNameEchoed
                    && artifactRootEchoed
                    && retentionDaysEchoed
                    && uploadModeEchoed;
            return new ArtifactRetentionFlags(
                    uploadContractVersionEchoed,
                    uploadContractDigestEchoed,
                    artifactNameEchoed,
                    artifactRootEchoed,
                    retentionDaysEchoed,
                    uploadModeEchoed,
                    artifactRetentionContextComplete,
                    retentionDaysWithinJavaRetention,
                    javaRetentionFixtureReadOnly,
                    auditExportReadOnly,
                    false,
                    false,
                    false,
                    false
            );
        }
    }

    private record LiveReadinessFlags(
            boolean sourcePreflightVersionEchoed,
            boolean sourcePreflightDigestEchoed,
            boolean runtimeSmokeSessionIdEchoed,
            boolean runtimeReadTargetIdEchoed,
            boolean runtimeWindowModeEchoed,
            boolean liveReadinessContextComplete,
            boolean readyForRuntimeSmokeRead,
            boolean readOnlyEndpointReady,
            boolean runtimeSmokeExecutedByJava,
            boolean nodeMustRecordPidAndCleanup,
            boolean javaStartedProcessForNode,
            boolean processCleanupRecordedByJava,
            boolean nodeMayTreatAsProductionAuthorization
    ) {

        static LiveReadinessFlags fromEchoes(
                boolean sourcePreflightVersionEchoed,
                boolean sourcePreflightDigestEchoed,
                boolean runtimeSmokeSessionIdEchoed,
                boolean runtimeReadTargetIdEchoed,
                boolean runtimeWindowModeEchoed,
                boolean liveReadinessContextComplete,
                boolean readyForRuntimeSmokeRead,
                boolean readOnlyEndpointReady
        ) {
            return new LiveReadinessFlags(
                    sourcePreflightVersionEchoed,
                    sourcePreflightDigestEchoed,
                    runtimeSmokeSessionIdEchoed,
                    runtimeReadTargetIdEchoed,
                    runtimeWindowModeEchoed,
                    liveReadinessContextComplete,
                    readyForRuntimeSmokeRead,
                    readOnlyEndpointReady,
                    false,
                    true,
                    false,
                    false,
                    false
            );
        }
    }
}
