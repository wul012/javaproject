package com.codexdemo.orderplatform.ops;

import java.time.Instant;
import java.util.List;

public record OpsEvidenceResponse(
        Instant sampledAt,
        String evidenceVersion,
        Service service,
        HealthProbe healthProbe,
        boolean readOnly,
        boolean executionAllowed,
        ReadOnlyWindow readOnlyWindow,
        OrderIdempotency orderIdempotency,
        ReleaseVerification releaseVerification,
        DeploymentRollback deploymentRollback,
        ReleaseBundle releaseBundle,
        RollbackApprovalHandoff rollbackApprovalHandoff,
        RollbackSqlReviewGate rollbackSqlReviewGate,
        ProductionSecretSourceContract productionSecretSourceContract,
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {

    public record Service(
            String name,
            String version,
            List<String> profiles,
            Instant startedAt,
            long uptimeSeconds
    ) {
    }

    public record HealthProbe(
            String endpoint,
            String method,
            String expectedStatus,
            String evidenceEndpoint,
            List<String> additionalProbeEndpoints,
            boolean liveProbeRequiredForPass,
            boolean staticSampleOnly
    ) {
    }

    public record ReadOnlyWindow(
            String windowVersion,
            boolean operatorStartRequired,
            boolean nodeAutoStartAllowed,
            boolean upstreamProbesRequired,
            boolean upstreamActionsAllowed,
            boolean readyForReadOnlyLiveProbe,
            boolean readyForProductionOperations,
            List<String> allowedProbeEndpoints,
            List<String> forbiddenOperations,
            List<String> requiredNodeEnvironment,
            String replayPostBoundary
    ) {
    }

    public record OrderIdempotency(
            String boundaryVersion,
            String storeAbstractionVersion,
            String createOrderEndpoint,
            String createOrderMethod,
            String requiredHeader,
            int maxKeyLength,
            String requestFingerprintVersion,
            String requestFingerprintScope,
            String sameKeySameRequestOutcome,
            String sameKeyDifferentRequestOutcome,
            String sameKeyDifferentRequestErrorCode,
            String activeStore,
            String activeStoreImplementation,
            String activeStoreMode,
            String authoritativeStore,
            List<IdempotencyStoreCandidate> storeCandidates,
            boolean miniKvConnected,
            boolean externalTokenStoreConnected,
            boolean changesPaymentOrInventoryTransaction
    ) {
    }

    public record IdempotencyStoreCandidate(
            String name,
            String role,
            boolean enabled,
            boolean connected,
            String mode,
            String reason
    ) {
    }

    public record ReleaseVerification(
            String manifestVersion,
            String manifestEndpoint,
            String verificationMode,
            List<String> requiredChecks,
            List<String> staticContractEndpoints,
            boolean nodeMayExecuteBuild,
            boolean nodeMayTriggerWrites,
            boolean changesBusinessSemantics,
            boolean requiresProductionSecrets
    ) {
    }

    public record DeploymentRollback(
            String evidenceVersion,
            String evidenceEndpoint,
            String rollbackMode,
            List<String> rollbackSubjects,
            List<String> requiresOperatorConfirmation,
            boolean packageRollbackSupported,
            boolean configRollbackSupported,
            boolean databaseMigrationRollbackAutomatic,
            boolean contractsRollbackByArtifactVersion,
            boolean nodeMayTriggerRollback,
            boolean requiresProductionDatabase,
            boolean changesOrderTransactionSemantics
    ) {
    }

    public record ReleaseBundle(
            String manifestVersion,
            String manifestEndpoint,
            String bundleMode,
            String artifact,
            List<String> contractEndpoints,
            List<String> requiredEvidence,
            boolean nodeMayConsume,
            boolean nodeMayExecuteBuild,
            boolean nodeMayTriggerRollback,
            boolean requiresProductionDatabase,
            boolean changesOrderTransactionSemantics
    ) {
    }

    public record RollbackApprovalHandoff(
            String handoffVersion,
            String handoffEndpoint,
            String approvalMode,
            List<String> requiredConfirmationFields,
            List<String> handoffArtifacts,
            boolean nodeMayConsume,
            boolean nodeMayTriggerRollback,
            boolean rollbackSqlExecutionAllowed,
            boolean requiresProductionDatabase,
            boolean requiresProductionSecrets,
            boolean changesOrderTransactionSemantics
    ) {
    }

    public record RollbackSqlReviewGate(
            String gateVersion,
            String gateEndpoint,
            String gateMode,
            String reviewOwner,
            List<String> requiredReviewFields,
            List<String> migrationDirectionOptions,
            String operatorApprovalPlaceholder,
            boolean nodeMayConsume,
            boolean nodeMayTriggerRollback,
            boolean sqlExecutionAllowed,
            boolean requiresProductionDatabase,
            boolean changesOrderTransactionSemantics
    ) {
    }

    public record ProductionSecretSourceContract(
            String contractVersion,
            String contractEndpoint,
            String contractMode,
            List<String> sourceTypes,
            String selectedSourceType,
            String secretManagerOwner,
            String rotationOwner,
            String reviewCadence,
            List<String> requiredConfirmationFields,
            List<String> secretValueBoundaries,
            boolean nodeMayConsume,
            boolean nodeMayReadSecretValues,
            boolean requiresProductionSecrets,
            boolean requiresProductionDatabase,
            boolean changesOrderTransactionSemantics
    ) {
    }

    public record FailedEventReplay(
            long totalFailedEvents,
            long replayBacklog,
            long pendingReplayApprovals,
            long approvedReplayApprovals,
            long rejectedReplayApprovals,
            Instant latestFailedAt,
            Instant latestApprovalAt,
            String realReplayEndpoint,
            boolean realReplayAllowedByEvidence
    ) {
    }

    public record Outbox(
            long pendingEvents,
            boolean publisherEnabled,
            boolean rabbitMqEnabled,
            String exchange,
            String queue,
            String deadLetterQueue,
            List<String> blockers
    ) {
    }

    public record ApprovalExecution(
            String requiredApprovalStatus,
            String digestVerificationMode,
            boolean approvalRequired,
            boolean dryRun,
            List<String> executionBlockers,
            List<String> nextEvidenceActions
    ) {
    }
}
