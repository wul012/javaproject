package com.codexdemo.orderplatform.ops;

import java.time.Instant;
import java.util.List;

public record ReleaseApprovalRehearsalResponse(
        Instant sampledAt,
        String rehearsalVersion,
        String sourceEvidenceEndpoint,
        String rehearsalMode,
        boolean readOnly,
        boolean executionAllowed,
        RehearsalRequestContext requestContext,
        RehearsalOperatorWindowHint operatorWindowHint,
        RehearsalCiEvidenceHint ciEvidenceHint,
        RehearsalArtifactRetentionHint artifactRetentionHint,
        RehearsalLiveReadinessHint liveReadinessHint,
        RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
        RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
        RehearsalApprovalHandoffVerificationMarker approvalHandoffVerificationMarker,
        RehearsalFailureTaxonomy failureTaxonomy,
        RehearsalVerificationHint verificationHint,
        ReleaseApprovalInputs releaseApprovalInputs,
        LiveSignals liveSignals,
        ExecutionBoundaries executionBoundaries,
        List<String> rehearsalBlockers,
        List<String> requiredNodeEnvironment,
        List<String> nextEvidenceActions
) {

    public record RehearsalRequestContext(
            String contextVersion,
            String requestId,
            String requestIdSource,
            String operatorIdentity,
            String operatorIdentitySource,
            String auditCorrelationId,
            String auditCorrelationSource,
            boolean operatorAuthenticatedByJava,
            boolean persistedByJava,
            boolean approvalLedgerWritten,
            boolean requiresProductionIdentityProvider,
            List<String> acceptedReadOnlyHeaders,
            List<String> contextWarnings
    ) {
    }

    public record RehearsalOperatorWindowHint(
            String hintVersion,
            String operatorId,
            String operatorIdSource,
            String operatorRoles,
            String operatorRolesSource,
            String operatorVerifiedClaim,
            String operatorVerifiedClaimSource,
            String approvalCorrelationId,
            String approvalCorrelationIdSource,
            boolean operatorIdentityEchoed,
            boolean operatorRolesEchoed,
            boolean operatorVerifiedClaimEchoed,
            boolean approvalCorrelationEchoed,
            boolean operatorWindowContextComplete,
            boolean productionIdpVerifiedByJava,
            boolean persistedApprovalRecordByJava,
            boolean nodeMayTreatAsProductionIdentity,
            List<String> acceptedOperatorWindowHeaders,
            List<String> echoWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalCiEvidenceHint(
            String hintVersion,
            String manifestProfileVersion,
            String manifestProfileVersionSource,
            String manifestDigest,
            String manifestDigestSource,
            String manifestEndpoint,
            String manifestEndpointSource,
            String artifactRecordCount,
            String artifactRecordCountSource,
            String approvalCorrelationId,
            String approvalCorrelationIdSource,
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
            boolean nodeMayTreatAsCiArtifactPublication,
            List<String> acceptedCiEvidenceHeaders,
            List<String> echoWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalArtifactRetentionHint(
            String hintVersion,
            String sourceRetentionFixtureVersion,
            String sourceRetentionFixtureEndpoint,
            String retentionId,
            String artifactTarget,
            int javaRetentionDays,
            String ciUploadContractVersion,
            String ciUploadContractVersionSource,
            String ciUploadContractDigest,
            String ciUploadContractDigestSource,
            String ciArtifactName,
            String ciArtifactNameSource,
            String ciArtifactRoot,
            String ciArtifactRootSource,
            String ciRetentionDays,
            String ciRetentionDaysSource,
            String ciUploadMode,
            String ciUploadModeSource,
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
            boolean nodeMayTreatAsRetentionAuthorization,
            List<String> acceptedArtifactRetentionHeaders,
            List<String> releaseEvidenceEndpoints,
            List<String> echoWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalLiveReadinessHint(
            String hintVersion,
            Instant serverTimestamp,
            String serverTimestampSource,
            String readOnlyEndpointVersion,
            String readOnlyEndpoint,
            String healthEndpoint,
            String sourcePreflightVersion,
            String sourcePreflightVersionSource,
            String sourcePreflightDigest,
            String sourcePreflightDigestSource,
            String runtimeSmokeSessionId,
            String runtimeSmokeSessionIdSource,
            String runtimeReadTargetId,
            String runtimeReadTargetIdSource,
            String runtimeWindowMode,
            String runtimeWindowModeSource,
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
            boolean nodeMayTreatAsProductionAuthorization,
            List<String> acceptedLiveReadinessHeaders,
            List<String> allowedReadTargets,
            List<String> forbiddenRuntimeOperations,
            List<String> echoWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalAuditPersistenceHandoffHint(
            String hintVersion,
            String sourceRetentionFixtureVersion,
            String sourceRetentionFixtureEndpoint,
            int javaRetentionDays,
            String managedAuditCandidateVersion,
            String managedAuditCandidateVersionSource,
            String managedAuditCandidateDigest,
            String managedAuditCandidateDigestSource,
            String managedAuditSinkMode,
            String managedAuditSinkModeSource,
            String managedAuditRetentionDays,
            String managedAuditRetentionDaysSource,
            String managedAuditRotationPolicy,
            String managedAuditRotationPolicySource,
            boolean candidateVersionEchoed,
            boolean candidateDigestEchoed,
            boolean sinkModeEchoed,
            boolean retentionDaysEchoed,
            boolean rotationPolicyEchoed,
            boolean auditPersistenceHandoffContextComplete,
            boolean managedAuditRetentionWithinJavaRetention,
            boolean javaAuditSourceReadOnly,
            boolean javaLedgerWriteAllowed,
            boolean javaManagedAuditWriteAllowed,
            boolean javaExternalAuditSystemAccessed,
            boolean productionAuditStoreRequired,
            boolean nodeMayUseAsManagedAuditInput,
            boolean nodeMayTreatAsProductionAuditRecord,
            List<String> acceptedAuditPersistenceHeaders,
            List<String> handoffFieldPaths,
            List<String> readOnlySourceEndpoints,
            List<String> echoWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalApprovalRecordHandoffHint(
            String hintVersion,
            String sourceApprovalRecordFixtureVersion,
            String sourceApprovalRecordFixtureEndpoint,
            String reviewerPlaceholder,
            String approvalTimestampPlaceholder,
            String rollbackTarget,
            String selectedMigrationDirection,
            String approvalBindingContractVersion,
            String approvalBindingContractVersionSource,
            String approvalBindingContractDigest,
            String approvalBindingContractDigestSource,
            String approvalRequestId,
            String approvalRequestIdSource,
            String approvalDecisionState,
            String approvalDecisionStateSource,
            String approvalRecordCorrelationId,
            String approvalRecordCorrelationIdSource,
            boolean approvalBindingContractVersionEchoed,
            boolean approvalBindingContractDigestEchoed,
            boolean approvalRequestIdEchoed,
            boolean approvalDecisionStateEchoed,
            boolean approvalRecordCorrelationEchoed,
            boolean approvalRecordHandoffContextComplete,
            boolean approvalRecordFixtureReadOnly,
            boolean javaApprovalDecisionCreated,
            boolean javaApprovalLedgerWritten,
            boolean javaApprovalRecordPersisted,
            boolean javaApprovalRecordAuthenticated,
            boolean productionApprovalStoreRequired,
            boolean nodeMayUseAsAuditApprovalInput,
            boolean nodeMayTreatAsProductionApprovalRecord,
            List<String> acceptedApprovalRecordHeaders,
            List<String> handoffFieldPaths,
            List<String> sourceRecordArtifacts,
            List<String> echoWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalApprovalHandoffVerificationMarker(
            String markerVersion,
            String sourceApprovalRecordHandoffHintVersion,
            String sourceApprovalRecordHandoffSchemaVersion,
            String consumedByNodeProfileVersion,
            String consumedByNodePacketState,
            String consumedByNodeEndpoint,
            String consumedByNodeRequestId,
            String consumedByNodePacketVersion,
            String consumedByNodeBindingContractVersion,
            String consumedByNodeDryRunDirectoryLabel,
            String consumedByNodeDryRunDirectoryPrefix,
            String consumedByNodeDryRunFileName,
            boolean nodeV211MayConsume,
            boolean nodeV211HandoffAccepted,
            boolean nodeV211NoWriteBoundaryAccepted,
            boolean nodeV211PacketAppendCovered,
            boolean nodeV211PacketQueryCovered,
            boolean nodeV211PacketDigestCovered,
            boolean nodeV211PacketCleanupCovered,
            boolean nodeV211JavaWriteAttempted,
            boolean nodeV211MiniKvWriteAttempted,
            boolean nodeV211ExternalAuditSystemAccessed,
            boolean nodeV211RealApprovalDecisionCreated,
            boolean nodeV211RealApprovalLedgerWritten,
            boolean nodeV211ProductionAuditRecordAllowed,
            boolean javaApprovalRecordPersisted,
            boolean javaApprovalLedgerWritten,
            boolean readyForNodeV213RestoreDrillPlan,
            boolean nodeMayTreatAsProductionAuditRecord,
            List<String> consumedHandoffFieldPaths,
            List<String> nodeV211AcceptedChecks,
            List<String> nodeV213Prerequisites,
            List<String> markerWarnings,
            List<String> nodeVerificationActions
    ) {
    }

    public record RehearsalFailureTaxonomy(
            String taxonomyVersion,
            String upstreamReadiness,
            String authContextReadiness,
            String auditCorrelationReadiness,
            boolean javaReadOnlyUpstreamReady,
            boolean authContextComplete,
            boolean auditCorrelationPresent,
            boolean retryableByReadOnlyAdapter,
            boolean writeActionRequired,
            List<String> failureCategories,
            List<String> taxonomyWarnings
    ) {
    }

    public record RehearsalVerificationHint(
            String hintVersion,
            String responseSchemaVersion,
            String warningDigest,
            String noLedgerWriteProof,
            boolean noLedgerWriteProved,
            boolean nodeMayTreatAsProductionAuthorization,
            List<String> schemaFields,
            List<String> warningDigestInputs,
            List<String> proofClaims,
            List<String> nodeVerificationActions
    ) {
    }

    public record ReleaseApprovalInputs(
            String releaseOperatorSignoffFixtureEndpoint,
            String rollbackApproverEvidenceFixtureEndpoint,
            String rollbackApprovalRecordFixtureEndpoint,
            String releaseBundleManifestEndpoint,
            String releaseVerificationManifestEndpoint,
            String deploymentRollbackEvidenceEndpoint,
            String productionDeploymentRunbookContractEndpoint,
            String productionSecretSourceContractEndpoint,
            String rollbackSqlReviewGateEndpoint,
            List<String> requiredEvidenceEndpoints
    ) {
    }

    public record LiveSignals(
            long pendingReplayApprovals,
            long approvedReplayApprovals,
            long rejectedReplayApprovals,
            long replayBacklog,
            long pendingOutboxEvents,
            boolean realReplayAllowedByEvidence,
            boolean approvalExecutionDryRun,
            boolean evidenceExecutionAllowed
    ) {
    }

    public record ExecutionBoundaries(
            boolean nodeMayConsume,
            boolean nodeMayCreateApprovalDecision,
            boolean nodeMayWriteApprovalLedger,
            boolean nodeMayTriggerDeployment,
            boolean nodeMayTriggerRollback,
            boolean nodeMayExecuteRollbackSql,
            boolean requiresProductionDatabase,
            boolean requiresProductionSecrets,
            boolean changesOrderTransactionSemantics
    ) {
    }
}
