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
        ReleaseApprovalInputs releaseApprovalInputs,
        LiveSignals liveSignals,
        ExecutionBoundaries executionBoundaries,
        List<String> rehearsalBlockers,
        List<String> requiredNodeEnvironment,
        List<String> nextEvidenceActions
) {

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
