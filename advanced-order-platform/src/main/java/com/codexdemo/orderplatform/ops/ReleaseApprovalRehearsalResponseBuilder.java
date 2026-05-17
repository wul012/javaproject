package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalRehearsalResponseBuilder {

    ReleaseApprovalRehearsalResponse build(
            OpsEvidenceResponse evidence,
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
            String managedAuditRotationPolicy,
            String approvalBindingContractVersion,
            String approvalBindingContractDigest,
            String approvalRequestId,
            String approvalDecisionState,
            String approvalRecordCorrelationId
    ) {
        ReleaseApprovalRehearsalHintBuilder rehearsalHintBuilder = new ReleaseApprovalRehearsalHintBuilder();
        ReleaseApprovalRehearsalHandoffHintBuilder handoffHintBuilder =
                new ReleaseApprovalRehearsalHandoffHintBuilder();
        ReleaseApprovalRehearsalFailureTaxonomyBuilder failureTaxonomyBuilder =
                new ReleaseApprovalRehearsalFailureTaxonomyBuilder();
        String normalizedRequestId = ContextHeaderField.normalizeValue(requestId);
        String normalizedOperatorIdentity = ContextHeaderField.normalizeValue(operatorIdentity);
        String normalizedAuditCorrelationId = ContextHeaderField.normalizeValue(auditCorrelationId);
        String normalizedOperatorWindowOperatorId = ContextHeaderField.normalizeValue(operatorWindowOperatorId);
        String normalizedOperatorWindowRoles = ContextHeaderField.normalizeValue(operatorWindowRoles);
        String normalizedOperatorWindowVerifiedClaim = ContextHeaderField.normalizeValue(operatorWindowVerifiedClaim);
        String normalizedOperatorWindowApprovalCorrelationId =
                ContextHeaderField.normalizeValue(operatorWindowApprovalCorrelationId);
        String normalizedCiManifestVersion = ContextHeaderField.normalizeValue(ciManifestVersion);
        String normalizedCiManifestDigest = ContextHeaderField.normalizeValue(ciManifestDigest);
        String normalizedCiManifestEndpoint = ContextHeaderField.normalizeValue(ciManifestEndpoint);
        String normalizedCiArtifactRecordCount = ContextHeaderField.normalizeValue(ciArtifactRecordCount);
        String normalizedCiApprovalCorrelationId = ContextHeaderField.normalizeValue(ciApprovalCorrelationId);
        String normalizedCiUploadContractVersion = ContextHeaderField.normalizeValue(ciUploadContractVersion);
        String normalizedCiUploadContractDigest = ContextHeaderField.normalizeValue(ciUploadContractDigest);
        String normalizedCiArtifactName = ContextHeaderField.normalizeValue(ciArtifactName);
        String normalizedCiArtifactRoot = ContextHeaderField.normalizeValue(ciArtifactRoot);
        String normalizedCiRetentionDays = ContextHeaderField.normalizeValue(ciRetentionDays);
        String normalizedCiUploadMode = ContextHeaderField.normalizeValue(ciUploadMode);
        String normalizedRuntimePreflightVersion = ContextHeaderField.normalizeValue(runtimePreflightVersion);
        String normalizedRuntimePreflightDigest = ContextHeaderField.normalizeValue(runtimePreflightDigest);
        String normalizedRuntimeSmokeSessionId = ContextHeaderField.normalizeValue(runtimeSmokeSessionId);
        String normalizedRuntimeReadTargetId = ContextHeaderField.normalizeValue(runtimeReadTargetId);
        String normalizedRuntimeWindowMode = ContextHeaderField.normalizeValue(runtimeWindowMode);
        String normalizedManagedAuditCandidateVersion = ContextHeaderField.normalizeValue(managedAuditCandidateVersion);
        String normalizedManagedAuditCandidateDigest = ContextHeaderField.normalizeValue(managedAuditCandidateDigest);
        String normalizedManagedAuditSinkMode = ContextHeaderField.normalizeValue(managedAuditSinkMode);
        String normalizedManagedAuditRetentionDays = ContextHeaderField.normalizeValue(managedAuditRetentionDays);
        String normalizedManagedAuditRotationPolicy = ContextHeaderField.normalizeValue(managedAuditRotationPolicy);
        String normalizedApprovalBindingContractVersion =
                ContextHeaderField.normalizeValue(approvalBindingContractVersion);
        String normalizedApprovalBindingContractDigest = ContextHeaderField.normalizeValue(approvalBindingContractDigest);
        String normalizedApprovalRequestId = ContextHeaderField.normalizeValue(approvalRequestId);
        String normalizedApprovalDecisionState = ContextHeaderField.normalizeValue(approvalDecisionState);
        String normalizedApprovalRecordCorrelationId = ContextHeaderField.normalizeValue(approvalRecordCorrelationId);
        ReleaseApprovalRehearsalResponse.RehearsalRequestContext requestContext =
                rehearsalHintBuilder.rehearsalRequestContext(
                        normalizedRequestId,
                        normalizedOperatorIdentity,
                        normalizedAuditCorrelationId
                );
        ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint operatorWindowHint =
                rehearsalHintBuilder.rehearsalOperatorWindowHint(
                        normalizedOperatorWindowOperatorId,
                        normalizedOperatorWindowRoles,
                        normalizedOperatorWindowVerifiedClaim,
                        normalizedOperatorWindowApprovalCorrelationId
                );
        ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint ciEvidenceHint =
                rehearsalHintBuilder.rehearsalCiEvidenceHint(
                        normalizedCiManifestVersion,
                        normalizedCiManifestDigest,
                        normalizedCiManifestEndpoint,
                        normalizedCiArtifactRecordCount,
                        normalizedCiApprovalCorrelationId
                );
        ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint artifactRetentionHint =
                rehearsalHintBuilder.rehearsalArtifactRetentionHint(
                        evidence.releaseAuditRetentionFixture(),
                        normalizedCiUploadContractVersion,
                        normalizedCiUploadContractDigest,
                        normalizedCiArtifactName,
                        normalizedCiArtifactRoot,
                        normalizedCiRetentionDays,
                        normalizedCiUploadMode
                );
        ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint liveReadinessHint =
                rehearsalHintBuilder.rehearsalLiveReadinessHint(
                        evidence,
                        normalizedRuntimePreflightVersion,
                        normalizedRuntimePreflightDigest,
                        normalizedRuntimeSmokeSessionId,
                        normalizedRuntimeReadTargetId,
                        normalizedRuntimeWindowMode
        );
        ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint =
                handoffHintBuilder.rehearsalAuditPersistenceHandoffHint(
                        evidence.releaseAuditRetentionFixture(),
                        normalizedManagedAuditCandidateVersion,
                        normalizedManagedAuditCandidateDigest,
                        normalizedManagedAuditSinkMode,
                        normalizedManagedAuditRetentionDays,
                        normalizedManagedAuditRotationPolicy
                );
        ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint =
                handoffHintBuilder.rehearsalApprovalRecordHandoffHint(
                        evidence.rollbackApprovalRecordFixture(),
                        normalizedApprovalBindingContractVersion,
                        normalizedApprovalBindingContractDigest,
                        normalizedApprovalRequestId,
                        normalizedApprovalDecisionState,
                        normalizedApprovalRecordCorrelationId
        );
        ReleaseApprovalRehearsalResponse.RehearsalApprovalHandoffVerificationMarker
                approvalHandoffVerificationMarker =
                        handoffHintBuilder.rehearsalApprovalHandoffVerificationMarker(approvalRecordHandoffHint);
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterBoundaryReceipt
                managedAuditAdapterBoundaryReceipt =
                        new ReleaseApprovalManagedAuditAdapterBoundaryReceiptBuilder()
                                .build(approvalHandoffVerificationMarker);
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                managedAuditProductionAdapterPrerequisiteReceipt =
                        new ReleaseApprovalManagedAuditProductionAdapterPrerequisiteReceiptBuilder()
                                .build(managedAuditAdapterBoundaryReceipt);
        ReleaseApprovalRehearsalResponse.RehearsalOpsEvidenceServiceQualitySplitReceipt
                opsEvidenceServiceQualitySplitReceipt =
                        new ReleaseApprovalOpsEvidenceServiceQualitySplitReceiptBuilder()
                                .build(managedAuditProductionAdapterPrerequisiteReceipt);
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterImplementationGuardReceipt
                managedAuditAdapterImplementationGuardReceipt =
                        new ReleaseApprovalManagedAuditAdapterImplementationGuardReceiptBuilder()
                                .build(opsEvidenceServiceQualitySplitReceipt);
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                managedAuditExternalAdapterMigrationGuardReceipt =
                        new ReleaseApprovalManagedAuditExternalAdapterMigrationGuardReceiptBuilder()
                                .build(managedAuditAdapterImplementationGuardReceipt);
        ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder
                sandboxAdapterApprovalSchemaGuardReceiptBuilder =
                        new ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder();
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                managedAuditSandboxAdapterApprovalSchemaGuardReceipt =
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder
                                .build(managedAuditExternalAdapterMigrationGuardReceipt);
        ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder
                sandboxConnectionOperatorHandoffMarkerBuilder =
                        new ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder();
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                managedAuditSandboxConnectionOperatorHandoffMarker =
                        sandboxConnectionOperatorHandoffMarkerBuilder
                                .build(managedAuditSandboxAdapterApprovalSchemaGuardReceipt);
        ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder
                sandboxConnectionPreflightEchoMarkerBuilder =
                        new ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder();
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                managedAuditSandboxConnectionPreflightEchoMarker =
                        sandboxConnectionPreflightEchoMarkerBuilder
                                .build(managedAuditSandboxConnectionOperatorHandoffMarker);
        ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder
                sandboxConnectionPreconditionReceiptBuilder =
                        new ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder();
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                managedAuditSandboxConnectionPreconditionReceipt =
                        sandboxConnectionPreconditionReceiptBuilder
                                .build(managedAuditSandboxConnectionPreflightEchoMarker);
        ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy =
                failureTaxonomyBuilder.build(
                        evidence,
                        normalizedRequestId,
                        normalizedOperatorIdentity,
                        normalizedAuditCorrelationId
                );
        ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries = executionBoundaries();
        return new ReleaseApprovalRehearsalResponse(
                evidence.sampledAt(),
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_VERSION,
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
                approvalRecordHandoffHint,
                approvalHandoffVerificationMarker,
                managedAuditAdapterBoundaryReceipt,
                managedAuditProductionAdapterPrerequisiteReceipt,
                opsEvidenceServiceQualitySplitReceipt,
                managedAuditAdapterImplementationGuardReceipt,
                managedAuditExternalAdapterMigrationGuardReceipt,
                managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
                managedAuditSandboxConnectionOperatorHandoffMarker,
                managedAuditSandboxConnectionPreflightEchoMarker,
                managedAuditSandboxConnectionPreconditionReceipt,
                failureTaxonomy,
                releaseApprovalVerificationHint(
                        requestContext,
                        operatorWindowHint,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        approvalRecordHandoffHint,
                        approvalHandoffVerificationMarker,
                        managedAuditAdapterBoundaryReceipt,
                        managedAuditProductionAdapterPrerequisiteReceipt,
                        opsEvidenceServiceQualitySplitReceipt,
                        managedAuditAdapterImplementationGuardReceipt,
                        managedAuditExternalAdapterMigrationGuardReceipt,
                        managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
                        sandboxAdapterApprovalSchemaGuardReceiptBuilder,
                        managedAuditSandboxConnectionOperatorHandoffMarker,
                        sandboxConnectionOperatorHandoffMarkerBuilder,
                        managedAuditSandboxConnectionPreflightEchoMarker,
                        sandboxConnectionPreflightEchoMarkerBuilder,
                        managedAuditSandboxConnectionPreconditionReceipt,
                        sandboxConnectionPreconditionReceiptBuilder,
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
            ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint,
            ReleaseApprovalRehearsalResponse.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalOpsEvidenceServiceQualitySplitReceipt
                    opsEvidenceServiceQualitySplitReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditAdapterImplementationGuardReceipt
                    managedAuditAdapterImplementationGuardReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditExternalAdapterMigrationGuardReceipt
                    managedAuditExternalAdapterMigrationGuardReceipt,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxAdapterApprovalSchemaGuardReceipt
                    managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
            ReleaseApprovalManagedAuditSandboxAdapterApprovalSchemaGuardReceiptBuilder
                    sandboxAdapterApprovalSchemaGuardReceiptBuilder,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionOperatorHandoffMarker
                    managedAuditSandboxConnectionOperatorHandoffMarker,
            ReleaseApprovalManagedAuditSandboxConnectionOperatorHandoffMarkerBuilder
                    sandboxConnectionOperatorHandoffMarkerBuilder,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionPreflightEchoMarker
                    managedAuditSandboxConnectionPreflightEchoMarker,
            ReleaseApprovalManagedAuditSandboxConnectionPreflightEchoMarkerBuilder
                    sandboxConnectionPreflightEchoMarkerBuilder,
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionPreconditionReceipt
                    managedAuditSandboxConnectionPreconditionReceipt,
            ReleaseApprovalManagedAuditSandboxConnectionPreconditionReceiptBuilder
                    sandboxConnectionPreconditionReceiptBuilder,
            ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries
    ) {
        return new ReleaseApprovalVerificationHintBuilder(
                sandboxAdapterApprovalSchemaGuardReceiptBuilder,
                sandboxConnectionOperatorHandoffMarkerBuilder,
                sandboxConnectionPreflightEchoMarkerBuilder,
                sandboxConnectionPreconditionReceiptBuilder
        )
                .build(
                        requestContext,
                        operatorWindowHint,
                        ciEvidenceHint,
                        artifactRetentionHint,
                        liveReadinessHint,
                        auditPersistenceHandoffHint,
                        approvalRecordHandoffHint,
                        approvalHandoffVerificationMarker,
                        managedAuditAdapterBoundaryReceipt,
                        managedAuditProductionAdapterPrerequisiteReceipt,
                        opsEvidenceServiceQualitySplitReceipt,
                        managedAuditAdapterImplementationGuardReceipt,
                        managedAuditExternalAdapterMigrationGuardReceipt,
                        managedAuditSandboxAdapterApprovalSchemaGuardReceipt,
                        managedAuditSandboxConnectionOperatorHandoffMarker,
                        managedAuditSandboxConnectionPreflightEchoMarker,
                        managedAuditSandboxConnectionPreconditionReceipt,
                        failureTaxonomy,
                        executionBoundaries
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
        return ExecutionBoundaryFlags.readOnlyRehearsal().toExecutionBoundaries();
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
                "GET " + OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                "GET " + OpsEvidenceService.RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT,
                "GET " + OpsEvidenceService.ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT,
                "GET " + OpsEvidenceService.ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT,
                "Keep UPSTREAM_ACTIONS_ENABLED=false"
        );
    }

    private record ExecutionBoundaryFlags(
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

        static ExecutionBoundaryFlags readOnlyRehearsal() {
            return new ExecutionBoundaryFlags(
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

        ReleaseApprovalRehearsalResponse.ExecutionBoundaries toExecutionBoundaries() {
            return new ReleaseApprovalRehearsalResponse.ExecutionBoundaries(
                    nodeMayConsume,
                    nodeMayCreateApprovalDecision,
                    nodeMayWriteApprovalLedger,
                    nodeMayTriggerDeployment,
                    nodeMayTriggerRollback,
                    nodeMayExecuteRollbackSql,
                    requiresProductionDatabase,
                    requiresProductionSecrets,
                    changesOrderTransactionSemantics
            );
        }
    }
}
