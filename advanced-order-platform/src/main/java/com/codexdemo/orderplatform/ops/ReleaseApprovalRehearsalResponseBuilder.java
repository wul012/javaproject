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
        NormalizedRequest normalizedRequest = NormalizedRequest.from(request);
        RehearsalSections sections = rehearsalSections(
                evidence,
                normalizedRequest,
                rehearsalHintBuilder,
                handoffHintBuilder
        );
        ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder.ReceiptChain managedAuditReceiptChain =
                new ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder()
                        .build(sections.approvalRecordHandoffHint());
        ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy failureTaxonomy =
                failureTaxonomyBuilder.build(
                        evidence,
                        normalizedRequest.requestId(),
                        normalizedRequest.operatorIdentity(),
                        normalizedRequest.auditCorrelationId()
                );
        ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries = executionBoundaries();
        return new ReleaseApprovalRehearsalResponse(
                evidence.sampledAt(),
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_VERSION,
                "/api/v1/ops/evidence",
                "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
                true,
                false,
                sections.requestContext(),
                sections.operatorWindowHint(),
                sections.ciEvidenceHint(),
                sections.artifactRetentionHint(),
                sections.liveReadinessHint(),
                sections.auditPersistenceHandoffHint(),
                sections.approvalRecordHandoffHint(),
                managedAuditReceiptChain.approvalHandoffVerificationMarker(),
                managedAuditReceiptChain.managedAuditAdapterBoundaryReceipt(),
                managedAuditReceiptChain.managedAuditProductionAdapterPrerequisiteReceipt(),
                managedAuditReceiptChain.opsEvidenceServiceQualitySplitReceipt(),
                managedAuditReceiptChain.managedAuditAdapterImplementationGuardReceipt(),
                managedAuditReceiptChain.managedAuditExternalAdapterMigrationGuardReceipt(),
                managedAuditReceiptChain.managedAuditSandboxAdapterApprovalSchemaGuardReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionOperatorHandoffMarker(),
                managedAuditReceiptChain.managedAuditSandboxConnectionPreflightEchoMarker(),
                managedAuditReceiptChain.managedAuditSandboxConnectionPreconditionReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionPrecheckPacketEchoReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt(),
                managedAuditReceiptChain.managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker(),
                managedAuditReceiptChain.managedAuditSandboxEndpointHandlePreflightEchoMarker(),
                managedAuditReceiptChain.managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker(),
                managedAuditReceiptChain.managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker(),
                managedAuditReceiptChain.managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker(),
                failureTaxonomy,
                releaseApprovalVerificationHint(
                        sections,
                        managedAuditReceiptChain,
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

    private RehearsalSections rehearsalSections(
            OpsEvidenceResponse evidence,
            NormalizedRequest normalizedRequest,
            ReleaseApprovalRehearsalHintBuilder rehearsalHintBuilder,
            ReleaseApprovalRehearsalHandoffHintBuilder handoffHintBuilder
    ) {
        ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext =
                rehearsalHintBuilder.rehearsalRequestContext(
                        normalizedRequest.requestId(),
                        normalizedRequest.operatorIdentity(),
                        normalizedRequest.auditCorrelationId()
                );
        ReleaseApprovalRehearsalResponseRecords.RehearsalOperatorWindowHint operatorWindowHint =
                rehearsalHintBuilder.rehearsalOperatorWindowHint(
                        normalizedRequest.operatorWindowOperatorId(),
                        normalizedRequest.operatorWindowRoles(),
                        normalizedRequest.operatorWindowVerifiedClaim(),
                        normalizedRequest.operatorWindowApprovalCorrelationId()
                );
        ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint =
                rehearsalHintBuilder.rehearsalCiEvidenceHint(
                        normalizedRequest.ciManifestVersion(),
                        normalizedRequest.ciManifestDigest(),
                        normalizedRequest.ciManifestEndpoint(),
                        normalizedRequest.ciArtifactRecordCount(),
                        normalizedRequest.ciApprovalCorrelationId()
                );
        ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint =
                rehearsalHintBuilder.rehearsalArtifactRetentionHint(
                        evidence.releaseAuditRetentionFixture(),
                        normalizedRequest.ciUploadContractVersion(),
                        normalizedRequest.ciUploadContractDigest(),
                        normalizedRequest.ciArtifactName(),
                        normalizedRequest.ciArtifactRoot(),
                        normalizedRequest.ciRetentionDays(),
                        normalizedRequest.ciUploadMode()
                );
        ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint =
                rehearsalHintBuilder.rehearsalLiveReadinessHint(
                        evidence,
                        normalizedRequest.runtimePreflightVersion(),
                        normalizedRequest.runtimePreflightDigest(),
                        normalizedRequest.runtimeSmokeSessionId(),
                        normalizedRequest.runtimeReadTargetId(),
                        normalizedRequest.runtimeWindowMode()
                );
        ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint =
                handoffHintBuilder.rehearsalAuditPersistenceHandoffHint(
                        evidence.releaseAuditRetentionFixture(),
                        normalizedRequest.managedAuditCandidateVersion(),
                        normalizedRequest.managedAuditCandidateDigest(),
                        normalizedRequest.managedAuditSinkMode(),
                        normalizedRequest.managedAuditRetentionDays(),
                        normalizedRequest.managedAuditRotationPolicy()
                );
        ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint =
                handoffHintBuilder.rehearsalApprovalRecordHandoffHint(
                        evidence.rollbackApprovalRecordFixture(),
                        normalizedRequest.approvalBindingContractVersion(),
                        normalizedRequest.approvalBindingContractDigest(),
                        normalizedRequest.approvalRequestId(),
                        normalizedRequest.approvalDecisionState(),
                        normalizedRequest.approvalRecordCorrelationId()
                );
        return new RehearsalSections(
                requestContext,
                operatorWindowHint,
                ciEvidenceHint,
                artifactRetentionHint,
                liveReadinessHint,
                auditPersistenceHandoffHint,
                approvalRecordHandoffHint
        );
    }

    private ReleaseApprovalRehearsalResponseRecords.RehearsalVerificationHint releaseApprovalVerificationHint(
            RehearsalSections sections,
            ReleaseApprovalRehearsalManagedAuditReceiptChainBuilder.ReceiptChain managedAuditReceiptChain,
            ReleaseApprovalRehearsalResponseRecords.RehearsalFailureTaxonomy failureTaxonomy,
            ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries
    ) {
        return new ReleaseApprovalVerificationHintBuilder(
                managedAuditReceiptChain.sandboxAdapterApprovalSchemaGuardReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionOperatorHandoffMarkerBuilder(),
                managedAuditReceiptChain.sandboxConnectionPreflightEchoMarkerBuilder(),
                managedAuditReceiptChain.sandboxConnectionPreconditionReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionDryRunEnvelopeEchoReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionOperatorWindowChecklistEchoReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionDryRunCommandPackageEchoReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionPrecheckPacketEchoReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionDisabledAdapterClientPrecheckEchoReceiptBuilder(),
                managedAuditReceiptChain.sandboxConnectionFakeTransportDryRunPacketEchoMarkerBuilder(),
                managedAuditReceiptChain.sandboxEndpointHandlePreflightEchoMarkerBuilder(),
                managedAuditReceiptChain.sandboxEndpointCredentialResolverDecisionEchoMarkerBuilder(),
                managedAuditReceiptChain.sandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder(),
                managedAuditReceiptChain.sandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder()
        )
                .build(
                        sections.requestContext(),
                        sections.operatorWindowHint(),
                        sections.ciEvidenceHint(),
                        sections.artifactRetentionHint(),
                        sections.liveReadinessHint(),
                        sections.auditPersistenceHandoffHint(),
                        sections.approvalRecordHandoffHint(),
                        managedAuditReceiptChain.approvalHandoffVerificationMarker(),
                        managedAuditReceiptChain.managedAuditAdapterBoundaryReceipt(),
                        managedAuditReceiptChain.managedAuditProductionAdapterPrerequisiteReceipt(),
                        managedAuditReceiptChain.opsEvidenceServiceQualitySplitReceipt(),
                        managedAuditReceiptChain.managedAuditAdapterImplementationGuardReceipt(),
                        managedAuditReceiptChain.managedAuditExternalAdapterMigrationGuardReceipt(),
                        managedAuditReceiptChain.managedAuditSandboxAdapterApprovalSchemaGuardReceipt(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionOperatorHandoffMarker(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionPreflightEchoMarker(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionPreconditionReceipt(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionDryRunCommandPackageEchoReceipt(),
                        managedAuditReceiptChain.managedAuditSandboxConnectionPrecheckPacketEchoReceipt(),
                        managedAuditReceiptChain
                                .managedAuditSandboxConnectionDisabledAdapterClientPrecheckEchoReceipt(),
                        managedAuditReceiptChain
                                .managedAuditSandboxConnectionFakeTransportDryRunPacketEchoMarker(),
                        managedAuditReceiptChain
                                .managedAuditSandboxEndpointHandlePreflightEchoMarker(),
                        managedAuditReceiptChain
                                .managedAuditSandboxEndpointCredentialResolverDecisionEchoMarker(),
                        managedAuditReceiptChain
                                .managedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker(),
                        managedAuditReceiptChain
                                .managedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarker(),
                        failureTaxonomy,
                        executionBoundaries
                );
    }

    private ReleaseApprovalRehearsalResponseRecords.ReleaseApprovalInputs releaseApprovalInputs(
            OpsEvidenceResponse evidence
    ) {
        return new ReleaseApprovalRehearsalResponseRecords.ReleaseApprovalInputs(
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

    private ReleaseApprovalRehearsalResponseRecords.LiveSignals liveSignals(OpsEvidenceResponse evidence) {
        return new ReleaseApprovalRehearsalResponseRecords.LiveSignals(
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

    private ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries executionBoundaries() {
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

    private record RehearsalSections(
            ReleaseApprovalRehearsalResponseRecords.RehearsalRequestContext requestContext,
            ReleaseApprovalRehearsalResponseRecords.RehearsalOperatorWindowHint operatorWindowHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalCiEvidenceHint ciEvidenceHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalArtifactRetentionHint artifactRetentionHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalLiveReadinessHint liveReadinessHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint,
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint
    ) {
    }

    private record NormalizedRequest(
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

        static NormalizedRequest from(ReleaseApprovalRehearsalRequest request) {
            ReleaseApprovalRehearsalRequest normalizedRequest =
                    request == null ? ReleaseApprovalRehearsalRequest.empty() : request;
            ReleaseApprovalRehearsalRequest.Context context = normalizedRequest.context();
            ReleaseApprovalRehearsalRequest.OperatorWindow operatorWindow = normalizedRequest.operatorWindow();
            ReleaseApprovalRehearsalRequest.CiEvidence ciEvidence = normalizedRequest.ciEvidence();
            ReleaseApprovalRehearsalRequest.ArtifactRetention artifactRetention =
                    normalizedRequest.artifactRetention();
            ReleaseApprovalRehearsalRequest.RuntimeReadiness runtimeReadiness =
                    normalizedRequest.runtimeReadiness();
            ReleaseApprovalRehearsalRequest.ManagedAudit managedAudit = normalizedRequest.managedAudit();
            ReleaseApprovalRehearsalRequest.ApprovalBinding approvalBinding = normalizedRequest.approvalBinding();

            return new NormalizedRequest(
                    normalized(context.requestId()),
                    normalized(context.operatorIdentity()),
                    normalized(context.auditCorrelationId()),
                    normalized(operatorWindow.operatorId()),
                    normalized(operatorWindow.roles()),
                    normalized(operatorWindow.verifiedClaim()),
                    normalized(operatorWindow.approvalCorrelationId()),
                    normalized(ciEvidence.manifestVersion()),
                    normalized(ciEvidence.manifestDigest()),
                    normalized(ciEvidence.manifestEndpoint()),
                    normalized(ciEvidence.artifactRecordCount()),
                    normalized(ciEvidence.approvalCorrelationId()),
                    normalized(artifactRetention.uploadContractVersion()),
                    normalized(artifactRetention.uploadContractDigest()),
                    normalized(artifactRetention.artifactName()),
                    normalized(artifactRetention.artifactRoot()),
                    normalized(artifactRetention.retentionDays()),
                    normalized(artifactRetention.uploadMode()),
                    normalized(runtimeReadiness.preflightVersion()),
                    normalized(runtimeReadiness.preflightDigest()),
                    normalized(runtimeReadiness.smokeSessionId()),
                    normalized(runtimeReadiness.readTargetId()),
                    normalized(runtimeReadiness.windowMode()),
                    normalized(managedAudit.candidateVersion()),
                    normalized(managedAudit.candidateDigest()),
                    normalized(managedAudit.sinkMode()),
                    normalized(managedAudit.retentionDays()),
                    normalized(managedAudit.rotationPolicy()),
                    normalized(approvalBinding.contractVersion()),
                    normalized(approvalBinding.contractDigest()),
                    normalized(approvalBinding.requestId()),
                    normalized(approvalBinding.decisionState()),
                    normalized(approvalBinding.recordCorrelationId())
            );
        }

        private static String normalized(String value) {
            return ContextHeaderField.normalizeValue(value);
        }
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

        ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries toExecutionBoundaries() {
            return new ReleaseApprovalRehearsalResponseRecords.ExecutionBoundaries(
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
