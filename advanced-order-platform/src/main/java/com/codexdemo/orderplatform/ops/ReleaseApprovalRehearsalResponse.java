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
