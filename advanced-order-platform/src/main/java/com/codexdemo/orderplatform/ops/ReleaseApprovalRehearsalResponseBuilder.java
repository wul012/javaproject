package com.codexdemo.orderplatform.ops;

import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.RELEASE_OPERATOR_SIGNOFF_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.ROLLBACK_APPROVAL_RECORD_FIXTURE;
import static com.codexdemo.orderplatform.ops.OpsEvidenceStaticReleaseArtifact.ROLLBACK_APPROVER_EVIDENCE_FIXTURE;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalRehearsalResponseBuilder {

    ReleaseApprovalRehearsalResponse build(OpsEvidenceResponse evidence, ReleaseApprovalRehearsalRequest request) {
        ReleaseApprovalRehearsalHintBuilder rehearsalHintBuilder = new ReleaseApprovalRehearsalHintBuilder();
        ReleaseApprovalRehearsalHandoffHintBuilder handoffHintBuilder =
                new ReleaseApprovalRehearsalHandoffHintBuilder();
        ReleaseApprovalRehearsalFailureTaxonomyBuilder failureTaxonomyBuilder =
                new ReleaseApprovalRehearsalFailureTaxonomyBuilder();
        ReleaseApprovalRehearsalRequest normalizedRequest =
                request == null ? ReleaseApprovalRehearsalRequest.empty() : request;
        ReleaseApprovalRehearsalRequest.Context context = normalizedRequest.context();
        ReleaseApprovalRehearsalRequest.OperatorWindow operatorWindow = normalizedRequest.operatorWindow();
        ReleaseApprovalRehearsalRequest.CiEvidence ciEvidence = normalizedRequest.ciEvidence();
        ReleaseApprovalRehearsalRequest.ArtifactRetention artifactRetention = normalizedRequest.artifactRetention();
        ReleaseApprovalRehearsalRequest.RuntimeReadiness runtimeReadiness = normalizedRequest.runtimeReadiness();
        ReleaseApprovalRehearsalRequest.ManagedAudit managedAudit = normalizedRequest.managedAudit();
        ReleaseApprovalRehearsalRequest.ApprovalBinding approvalBinding = normalizedRequest.approvalBinding();
        String normalizedRequestId = ContextHeaderField.normalizeValue(context.requestId());
        String normalizedOperatorIdentity = ContextHeaderField.normalizeValue(context.operatorIdentity());
        String normalizedAuditCorrelationId = ContextHeaderField.normalizeValue(context.auditCorrelationId());
        String normalizedOperatorWindowOperatorId = ContextHeaderField.normalizeValue(operatorWindow.operatorId());
        String normalizedOperatorWindowRoles = ContextHeaderField.normalizeValue(operatorWindow.roles());
        String normalizedOperatorWindowVerifiedClaim = ContextHeaderField.normalizeValue(operatorWindow.verifiedClaim());
        String normalizedOperatorWindowApprovalCorrelationId =
                ContextHeaderField.normalizeValue(operatorWindow.approvalCorrelationId());
        String normalizedCiManifestVersion = ContextHeaderField.normalizeValue(ciEvidence.manifestVersion());
        String normalizedCiManifestDigest = ContextHeaderField.normalizeValue(ciEvidence.manifestDigest());
        String normalizedCiManifestEndpoint = ContextHeaderField.normalizeValue(ciEvidence.manifestEndpoint());
        String normalizedCiArtifactRecordCount = ContextHeaderField.normalizeValue(ciEvidence.artifactRecordCount());
        String normalizedCiApprovalCorrelationId = ContextHeaderField.normalizeValue(ciEvidence.approvalCorrelationId());
        String normalizedCiUploadContractVersion =
                ContextHeaderField.normalizeValue(artifactRetention.uploadContractVersion());
        String normalizedCiUploadContractDigest =
                ContextHeaderField.normalizeValue(artifactRetention.uploadContractDigest());
        String normalizedCiArtifactName = ContextHeaderField.normalizeValue(artifactRetention.artifactName());
        String normalizedCiArtifactRoot = ContextHeaderField.normalizeValue(artifactRetention.artifactRoot());
        String normalizedCiRetentionDays = ContextHeaderField.normalizeValue(artifactRetention.retentionDays());
        String normalizedCiUploadMode = ContextHeaderField.normalizeValue(artifactRetention.uploadMode());
        String normalizedRuntimePreflightVersion =
                ContextHeaderField.normalizeValue(runtimeReadiness.preflightVersion());
        String normalizedRuntimePreflightDigest = ContextHeaderField.normalizeValue(runtimeReadiness.preflightDigest());
        String normalizedRuntimeSmokeSessionId = ContextHeaderField.normalizeValue(runtimeReadiness.smokeSessionId());
        String normalizedRuntimeReadTargetId = ContextHeaderField.normalizeValue(runtimeReadiness.readTargetId());
        String normalizedRuntimeWindowMode = ContextHeaderField.normalizeValue(runtimeReadiness.windowMode());
        String normalizedManagedAuditCandidateVersion =
                ContextHeaderField.normalizeValue(managedAudit.candidateVersion());
        String normalizedManagedAuditCandidateDigest = ContextHeaderField.normalizeValue(managedAudit.candidateDigest());
        String normalizedManagedAuditSinkMode = ContextHeaderField.normalizeValue(managedAudit.sinkMode());
        String normalizedManagedAuditRetentionDays = ContextHeaderField.normalizeValue(managedAudit.retentionDays());
        String normalizedManagedAuditRotationPolicy = ContextHeaderField.normalizeValue(managedAudit.rotationPolicy());
        String normalizedApprovalBindingContractVersion =
                ContextHeaderField.normalizeValue(approvalBinding.contractVersion());
        String normalizedApprovalBindingContractDigest =
                ContextHeaderField.normalizeValue(approvalBinding.contractDigest());
        String normalizedApprovalRequestId = ContextHeaderField.normalizeValue(approvalBinding.requestId());
        String normalizedApprovalDecisionState = ContextHeaderField.normalizeValue(approvalBinding.decisionState());
        String normalizedApprovalRecordCorrelationId =
                ContextHeaderField.normalizeValue(approvalBinding.recordCorrelationId());
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
        ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                sandboxConnectionDryRunEnvelopeEchoReceiptBuilder =
                        new ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder();
        ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt =
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                                .build(managedAuditSandboxConnectionPreconditionReceipt);
        ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder =
                        new ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder();
        ReleaseApprovalRehearsalResponse
                .RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt =
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                                .build(managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt);
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
                managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
                managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
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
                        managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
                        sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
                        managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
                        sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder,
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
            ReleaseApprovalRehearsalResponse.RehearsalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceipt
                    managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
            ReleaseApprovalManagedAuditSandboxConnectionDryRunEnvelopeEchoReceiptBuilder
                    sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
            ReleaseApprovalRehearsalResponse
                    .RehearsalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
                    managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
            ReleaseApprovalManagedAuditSandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
                    sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder,
            ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries
    ) {
        return new ReleaseApprovalVerificationHintBuilder(
                sandboxAdapterApprovalSchemaGuardReceiptBuilder,
                sandboxConnectionOperatorHandoffMarkerBuilder,
                sandboxConnectionPreflightEchoMarkerBuilder,
                sandboxConnectionPreconditionReceiptBuilder,
                sandboxConnectionDryRunEnvelopeEchoReceiptBuilder,
                sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder
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
                        managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt,
                        managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt,
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
                "GET " + RELEASE_OPERATOR_SIGNOFF_FIXTURE.endpoint(),
                "GET " + ROLLBACK_APPROVER_EVIDENCE_FIXTURE.endpoint(),
                "GET " + ROLLBACK_APPROVAL_RECORD_FIXTURE.endpoint(),
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
