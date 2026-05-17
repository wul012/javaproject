package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalRehearsalHandoffHintBuilder {

    ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint
            rehearsalAuditPersistenceHandoffHint(
                    OpsEvidenceResponse.ReleaseAuditRetentionFixture retentionFixture,
                    String normalizedManagedAuditCandidateVersion,
                    String normalizedManagedAuditCandidateDigest,
                    String normalizedManagedAuditSinkMode,
                    String normalizedManagedAuditRetentionDays,
                    String normalizedManagedAuditRotationPolicy
    ) {
        List<String> warnings = new ArrayList<>();
        ContextHeaderField managedAuditCandidateVersion = ContextHeaderField.from(
                normalizedManagedAuditCandidateVersion,
                "x-orderops-managed-audit-candidate-version",
                "managed-audit-candidate-version-not-supplied"
        );
        ContextHeaderField managedAuditCandidateDigest = ContextHeaderField.from(
                normalizedManagedAuditCandidateDigest,
                "x-orderops-managed-audit-candidate-digest",
                "managed-audit-candidate-digest-not-supplied"
        );
        ContextHeaderField managedAuditSinkMode = ContextHeaderField.from(
                normalizedManagedAuditSinkMode,
                "x-orderops-managed-audit-sink-mode",
                "managed-audit-sink-mode-not-supplied"
        );
        ContextHeaderField managedAuditRetentionDays = ContextHeaderField.from(
                normalizedManagedAuditRetentionDays,
                "x-orderops-managed-audit-retention-days",
                "managed-audit-retention-days-not-supplied"
        );
        ContextHeaderField managedAuditRotationPolicy = ContextHeaderField.from(
                normalizedManagedAuditRotationPolicy,
                "x-orderops-managed-audit-rotation-policy",
                "managed-audit-rotation-policy-not-supplied"
        );
        managedAuditCandidateVersion.addMissingWarning(
                warnings,
                "ORDEROPS_MANAGED_AUDIT_CANDIDATE_VERSION_MISSING"
        );
        managedAuditCandidateDigest.addMissingWarning(
                warnings,
                "ORDEROPS_MANAGED_AUDIT_CANDIDATE_DIGEST_MISSING"
        );
        managedAuditSinkMode.addMissingWarning(warnings, "ORDEROPS_MANAGED_AUDIT_SINK_MODE_MISSING");
        managedAuditRetentionDays.addMissingWarning(warnings, "ORDEROPS_MANAGED_AUDIT_RETENTION_DAYS_MISSING");
        managedAuditRotationPolicy.addMissingWarning(warnings, "ORDEROPS_MANAGED_AUDIT_ROTATION_POLICY_MISSING");
        boolean auditPersistenceHandoffContextComplete = ContextHeaderField.allEchoed(
                managedAuditCandidateVersion,
                managedAuditCandidateDigest,
                managedAuditSinkMode,
                managedAuditRetentionDays,
                managedAuditRotationPolicy
        );
        boolean managedAuditRetentionWithinJavaRetention = retentionDaysWithinJavaRetention(
                normalizedManagedAuditRetentionDays,
                retentionFixture.retentionDays()
        );
        boolean javaAuditSourceReadOnly = retentionFixture.nodeMayConsume()
                && retentionFixture.auditExportReadOnly()
                && !retentionFixture.deploymentExecutionAllowed()
                && !retentionFixture.rollbackSqlExecutionAllowed();
        AuditPersistenceHandoffFlags auditPersistenceHandoffFlags = AuditPersistenceHandoffFlags.fromReadOnly(
                managedAuditCandidateVersion.echoed(),
                managedAuditCandidateDigest.echoed(),
                managedAuditSinkMode.echoed(),
                managedAuditRetentionDays.echoed(),
                managedAuditRotationPolicy.echoed(),
                auditPersistenceHandoffContextComplete,
                managedAuditRetentionWithinJavaRetention,
                javaAuditSourceReadOnly
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_AUDIT_PERSISTENCE_HANDOFF_HINT_VERSION,
                retentionFixture.fixtureVersion(),
                retentionFixture.fixtureEndpoint(),
                retentionFixture.retentionDays(),
                managedAuditCandidateVersion.value(),
                managedAuditCandidateVersion.source(),
                managedAuditCandidateDigest.value(),
                managedAuditCandidateDigest.source(),
                managedAuditSinkMode.value(),
                managedAuditSinkMode.source(),
                managedAuditRetentionDays.value(),
                managedAuditRetentionDays.source(),
                managedAuditRotationPolicy.value(),
                managedAuditRotationPolicy.source(),
                auditPersistenceHandoffFlags.candidateVersionEchoed(),
                auditPersistenceHandoffFlags.candidateDigestEchoed(),
                auditPersistenceHandoffFlags.sinkModeEchoed(),
                auditPersistenceHandoffFlags.retentionDaysEchoed(),
                auditPersistenceHandoffFlags.rotationPolicyEchoed(),
                auditPersistenceHandoffFlags.auditPersistenceHandoffContextComplete(),
                auditPersistenceHandoffFlags.managedAuditRetentionWithinJavaRetention(),
                auditPersistenceHandoffFlags.javaAuditSourceReadOnly(),
                auditPersistenceHandoffFlags.javaLedgerWriteAllowed(),
                auditPersistenceHandoffFlags.javaManagedAuditWriteAllowed(),
                auditPersistenceHandoffFlags.javaExternalAuditSystemAccessed(),
                auditPersistenceHandoffFlags.productionAuditStoreRequired(),
                auditPersistenceHandoffFlags.nodeMayUseAsManagedAuditInput(),
                auditPersistenceHandoffFlags.nodeMayTreatAsProductionAuditRecord(),
                List.of(
                        "x-orderops-managed-audit-candidate-version",
                        "x-orderops-managed-audit-candidate-digest",
                        "x-orderops-managed-audit-sink-mode",
                        "x-orderops-managed-audit-retention-days",
                        "x-orderops-managed-audit-rotation-policy"
                ),
                List.of(
                        "sampledAt",
                        "requestContext.requestId",
                        "requestContext.operatorIdentity",
                        "requestContext.auditCorrelationId",
                        "operatorWindowHint.operatorId",
                        "operatorWindowHint.operatorRoles",
                        "operatorWindowHint.approvalCorrelationId",
                        "ciEvidenceHint.manifestDigest",
                        "artifactRetentionHint.sourceRetentionFixtureEndpoint",
                        "artifactRetentionHint.javaRetentionDays",
                        "liveReadinessHint.runtimeSmokeSessionId",
                        "failureTaxonomy.failureCategories",
                        "verificationHint.warningDigest",
                        "executionBoundaries.nodeMayWriteApprovalLedger"
                ),
                List.of(
                        OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_ENDPOINT,
                        retentionFixture.fixtureEndpoint(),
                        "/api/v1/ops/evidence"
                ),
                List.copyOf(warnings),
                List.of(
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateVersion with Node v208 candidate contract",
                        "Compare auditPersistenceHandoffHint.managedAuditCandidateDigest with Node v208 adapter digest",
                        "Require auditPersistenceHandoffHint.managedAuditRetentionWithinJavaRetention=true before Node dry-run retention checks",
                        "Persist only the listed handoffFieldPaths in Node managed audit dry-run storage",
                        "Keep javaManagedAuditWriteAllowed=false and nodeMayTreatAsProductionAuditRecord=false"
                )
        );
    }

    ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint
            rehearsalApprovalRecordHandoffHint(
                    OpsEvidenceResponse.RollbackApprovalRecordFixture approvalRecordFixture,
                    String normalizedApprovalBindingContractVersion,
                    String normalizedApprovalBindingContractDigest,
                    String normalizedApprovalRequestId,
                    String normalizedApprovalDecisionState,
                    String normalizedApprovalRecordCorrelationId
    ) {
        List<String> warnings = new ArrayList<>();
        ContextHeaderField approvalBindingContractVersion = ContextHeaderField.from(
                normalizedApprovalBindingContractVersion,
                "x-orderops-approval-binding-contract-version",
                "approval-binding-contract-version-not-supplied"
        );
        ContextHeaderField approvalBindingContractDigest = ContextHeaderField.from(
                normalizedApprovalBindingContractDigest,
                "x-orderops-approval-binding-contract-digest",
                "approval-binding-contract-digest-not-supplied"
        );
        ContextHeaderField approvalRequestId = ContextHeaderField.from(
                normalizedApprovalRequestId,
                "x-orderops-approval-request-id",
                "approval-request-id-not-supplied"
        );
        ContextHeaderField approvalDecisionState = ContextHeaderField.from(
                normalizedApprovalDecisionState,
                "x-orderops-approval-decision-state",
                "approval-decision-state-not-supplied"
        );
        ContextHeaderField approvalRecordCorrelationId = ContextHeaderField.from(
                normalizedApprovalRecordCorrelationId,
                "x-orderops-approval-record-correlation-id",
                "approval-record-correlation-id-not-supplied"
        );
        approvalBindingContractVersion.addMissingWarning(
                warnings,
                "ORDEROPS_APPROVAL_BINDING_CONTRACT_VERSION_MISSING"
        );
        approvalBindingContractDigest.addMissingWarning(
                warnings,
                "ORDEROPS_APPROVAL_BINDING_CONTRACT_DIGEST_MISSING"
        );
        approvalRequestId.addMissingWarning(warnings, "ORDEROPS_APPROVAL_REQUEST_ID_MISSING");
        approvalDecisionState.addMissingWarning(warnings, "ORDEROPS_APPROVAL_DECISION_STATE_MISSING");
        approvalRecordCorrelationId.addMissingWarning(
                warnings,
                "ORDEROPS_APPROVAL_RECORD_CORRELATION_ID_MISSING"
        );
        boolean approvalRecordHandoffContextComplete = ContextHeaderField.allEchoed(
                approvalBindingContractVersion,
                approvalBindingContractDigest,
                approvalRequestId,
                approvalDecisionState,
                approvalRecordCorrelationId
        );
        boolean approvalRecordFixtureReadOnly = approvalRecordFixture.nodeMayConsume()
                && !approvalRecordFixture.nodeMayTriggerRollback()
                && !approvalRecordFixture.rollbackExecutionAllowed()
                && !approvalRecordFixture.rollbackSqlExecutionAllowed();
        ApprovalRecordHandoffFlags approvalRecordHandoffFlags = ApprovalRecordHandoffFlags.fromReadOnly(
                approvalBindingContractVersion.echoed(),
                approvalBindingContractDigest.echoed(),
                approvalRequestId.echoed(),
                approvalDecisionState.echoed(),
                approvalRecordCorrelationId.echoed(),
                approvalRecordHandoffContextComplete,
                approvalRecordFixtureReadOnly
        );

        return new ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_APPROVAL_RECORD_HANDOFF_HINT_VERSION,
                approvalRecordFixture.fixtureVersion(),
                approvalRecordFixture.fixtureEndpoint(),
                approvalRecordFixture.reviewer(),
                approvalRecordFixture.approvalTimestampPlaceholder(),
                approvalRecordFixture.rollbackTarget(),
                approvalRecordFixture.selectedMigrationDirection(),
                approvalBindingContractVersion.value(),
                approvalBindingContractVersion.source(),
                approvalBindingContractDigest.value(),
                approvalBindingContractDigest.source(),
                approvalRequestId.value(),
                approvalRequestId.source(),
                approvalDecisionState.value(),
                approvalDecisionState.source(),
                approvalRecordCorrelationId.value(),
                approvalRecordCorrelationId.source(),
                approvalRecordHandoffFlags.approvalBindingContractVersionEchoed(),
                approvalRecordHandoffFlags.approvalBindingContractDigestEchoed(),
                approvalRecordHandoffFlags.approvalRequestIdEchoed(),
                approvalRecordHandoffFlags.approvalDecisionStateEchoed(),
                approvalRecordHandoffFlags.approvalRecordCorrelationEchoed(),
                approvalRecordHandoffFlags.approvalRecordHandoffContextComplete(),
                approvalRecordHandoffFlags.approvalRecordFixtureReadOnly(),
                approvalRecordHandoffFlags.javaApprovalDecisionCreated(),
                approvalRecordHandoffFlags.javaApprovalLedgerWritten(),
                approvalRecordHandoffFlags.javaApprovalRecordPersisted(),
                approvalRecordHandoffFlags.javaApprovalRecordAuthenticated(),
                approvalRecordHandoffFlags.productionApprovalStoreRequired(),
                approvalRecordHandoffFlags.nodeMayUseAsAuditApprovalInput(),
                approvalRecordHandoffFlags.nodeMayTreatAsProductionApprovalRecord(),
                List.of(
                        "x-orderops-approval-binding-contract-version",
                        "x-orderops-approval-binding-contract-digest",
                        "x-orderops-approval-request-id",
                        "x-orderops-approval-decision-state",
                        "x-orderops-approval-record-correlation-id"
                ),
                List.of(
                        "requestContext.requestId",
                        "requestContext.operatorIdentity",
                        "operatorWindowHint.operatorId",
                        "operatorWindowHint.operatorRoles",
                        "operatorWindowHint.operatorVerifiedClaim",
                        "operatorWindowHint.approvalCorrelationId",
                        "approvalRecordHandoffHint.approvalRequestId",
                        "approvalRecordHandoffHint.approvalDecisionState",
                        "approvalRecordHandoffHint.approvalRecordCorrelationId",
                        "approvalRecordHandoffHint.reviewerPlaceholder",
                        "approvalRecordHandoffHint.approvalTimestampPlaceholder",
                        "approvalRecordHandoffHint.rollbackTarget",
                        "approvalRecordHandoffHint.selectedMigrationDirection",
                        "verificationHint.warningDigest"
                ),
                approvalRecordFixture.recordArtifacts(),
                List.copyOf(warnings),
                List.of(
                        "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
                        "Compare approvalRecordHandoffHint.approvalBindingContractDigest with Node v210 binding digest",
                        "Require approvalRecordHandoffHint.approvalRecordHandoffContextComplete=true before Node v211 audit packet",
                        "Persist only the listed handoffFieldPaths in Node managed audit dry-run storage",
                        "Keep javaApprovalRecordPersisted=false and nodeMayTreatAsProductionApprovalRecord=false"
                )
        );
    }

    ReleaseApprovalRehearsalResponse.RehearsalApprovalHandoffVerificationMarker
            rehearsalApprovalHandoffVerificationMarker(
                    ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint
    ) {
        boolean nodeV211HandoffAccepted =
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_APPROVAL_RECORD_HANDOFF_HINT_VERSION.equals(
                        approvalRecordHandoffHint.hintVersion()
                )
                        && OpsEvidenceService.NODE_V210_APPROVAL_BINDING_CONTRACT_VERSION.equals(
                                approvalRecordHandoffHint.approvalBindingContractVersion()
                        )
                        && approvalRecordHandoffHint.approvalRecordHandoffContextComplete();
        boolean nodeV211NoWriteBoundaryAccepted = approvalRecordHandoffHint.approvalRecordFixtureReadOnly()
                && !approvalRecordHandoffHint.javaApprovalDecisionCreated()
                && !approvalRecordHandoffHint.javaApprovalLedgerWritten()
                && !approvalRecordHandoffHint.javaApprovalRecordPersisted()
                && !approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord();
        List<String> markerWarnings = new ArrayList<>();
        if (!nodeV211HandoffAccepted) {
            markerWarnings.add("NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE");
        }
        if (!nodeV211NoWriteBoundaryAccepted) {
            markerWarnings.add("NODE_V211_APPROVAL_HANDOFF_WRITE_BOUNDARY_INVALID");
        }
        boolean readyForNodeV213RestoreDrillPlan = nodeV211HandoffAccepted && nodeV211NoWriteBoundaryAccepted;
        ApprovalHandoffVerificationMarkerFlags verificationMarkerFlags =
                ApprovalHandoffVerificationMarkerFlags.fromReadOnly(
                        nodeV211HandoffAccepted,
                        nodeV211NoWriteBoundaryAccepted,
                        approvalRecordHandoffHint.javaApprovalRecordPersisted(),
                        approvalRecordHandoffHint.javaApprovalLedgerWritten(),
                        readyForNodeV213RestoreDrillPlan
                );

        return new ReleaseApprovalRehearsalResponse.RehearsalApprovalHandoffVerificationMarker(
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_APPROVAL_HANDOFF_VERIFICATION_MARKER_VERSION,
                approvalRecordHandoffHint.hintVersion(),
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_APPROVAL_RECORD_HANDOFF_SCHEMA_VERSION,
                OpsEvidenceService.NODE_V211_MANAGED_AUDIT_PROFILE_VERSION,
                OpsEvidenceService.NODE_V211_MANAGED_AUDIT_PACKET_STATE,
                OpsEvidenceService.NODE_V211_MANAGED_AUDIT_ENDPOINT,
                OpsEvidenceService.NODE_V211_MANAGED_AUDIT_REQUEST_ID,
                OpsEvidenceService.NODE_V211_MANAGED_AUDIT_PACKET_VERSION,
                OpsEvidenceService.NODE_V210_APPROVAL_BINDING_CONTRACT_VERSION,
                ".tmp",
                "managed-audit-v211-",
                "managed-audit-packet.jsonl",
                verificationMarkerFlags.nodeV211MayConsume(),
                verificationMarkerFlags.nodeV211HandoffAccepted(),
                verificationMarkerFlags.nodeV211NoWriteBoundaryAccepted(),
                verificationMarkerFlags.nodeV211PacketAppendCovered(),
                verificationMarkerFlags.nodeV211PacketQueryCovered(),
                verificationMarkerFlags.nodeV211PacketDigestCovered(),
                verificationMarkerFlags.nodeV211PacketCleanupCovered(),
                verificationMarkerFlags.nodeV211JavaWriteAttempted(),
                verificationMarkerFlags.nodeV211MiniKvWriteAttempted(),
                verificationMarkerFlags.nodeV211ExternalAuditSystemAccessed(),
                verificationMarkerFlags.nodeV211RealApprovalDecisionCreated(),
                verificationMarkerFlags.nodeV211RealApprovalLedgerWritten(),
                verificationMarkerFlags.nodeV211ProductionAuditRecordAllowed(),
                verificationMarkerFlags.javaApprovalRecordPersisted(),
                verificationMarkerFlags.javaApprovalLedgerWritten(),
                verificationMarkerFlags.readyForNodeV213RestoreDrillPlan(),
                verificationMarkerFlags.nodeMayTreatAsProductionAuditRecord(),
                List.of(
                        "requestContext.requestId",
                        "operatorWindowHint.operatorId",
                        "operatorWindowHint.operatorRoles",
                        "approvalRecordHandoffHint.approvalRequestId",
                        "approvalRecordHandoffHint.approvalDecisionState",
                        "approvalRecordHandoffHint.approvalRecordCorrelationId",
                        "approvalRecordHandoffHint.reviewerPlaceholder",
                        "approvalRecordHandoffHint.approvalTimestampPlaceholder",
                        "verificationHint.warningDigest"
                ),
                List.of(
                        "javaV75HandoffAccepted",
                        "javaV75NoWriteBoundaryValid",
                        "packetShapeBoundToContract",
                        "appendCovered",
                        "queryCovered",
                        "digestCovered",
                        "cleanupCovered",
                        "javaMiniKvWriteBlocked",
                        "noRealApprovalDecisionCreated",
                        "noExternalAuditAccessed"
                ),
                List.of(
                        "Node v212 packet verification report must verify managed-audit-identity-approval-provenance-dry-run-packet.v1",
                        "Java v76 marker readyForNodeV213RestoreDrillPlan must be true",
                        "mini-kv v85 retention provenance replay marker must be present",
                        "UPSTREAM_ACTIONS_ENABLED must remain false",
                        "Node v213 must not execute restore or connect real managed audit"
                ),
                List.copyOf(markerWarnings),
                List.of(
                        "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 profileVersion",
                        "Require approvalHandoffVerificationMarker.nodeV211HandoffAccepted=true before Node v213 restore drill plan",
                        "Keep approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false",
                        "Keep approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false"
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

    private record AuditPersistenceHandoffFlags(
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
            boolean nodeMayTreatAsProductionAuditRecord
    ) {

        static AuditPersistenceHandoffFlags fromReadOnly(
                boolean candidateVersionEchoed,
                boolean candidateDigestEchoed,
                boolean sinkModeEchoed,
                boolean retentionDaysEchoed,
                boolean rotationPolicyEchoed,
                boolean auditPersistenceHandoffContextComplete,
                boolean managedAuditRetentionWithinJavaRetention,
                boolean javaAuditSourceReadOnly
        ) {
            return new AuditPersistenceHandoffFlags(
                    candidateVersionEchoed,
                    candidateDigestEchoed,
                    sinkModeEchoed,
                    retentionDaysEchoed,
                    rotationPolicyEchoed,
                    auditPersistenceHandoffContextComplete,
                    managedAuditRetentionWithinJavaRetention,
                    javaAuditSourceReadOnly,
                    false,
                    false,
                    false,
                    false,
                    javaAuditSourceReadOnly,
                    false
            );
        }
    }

    private record ApprovalRecordHandoffFlags(
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
            boolean nodeMayTreatAsProductionApprovalRecord
    ) {

        static ApprovalRecordHandoffFlags fromReadOnly(
                boolean approvalBindingContractVersionEchoed,
                boolean approvalBindingContractDigestEchoed,
                boolean approvalRequestIdEchoed,
                boolean approvalDecisionStateEchoed,
                boolean approvalRecordCorrelationEchoed,
                boolean approvalRecordHandoffContextComplete,
                boolean approvalRecordFixtureReadOnly
        ) {
            return new ApprovalRecordHandoffFlags(
                    approvalBindingContractVersionEchoed,
                    approvalBindingContractDigestEchoed,
                    approvalRequestIdEchoed,
                    approvalDecisionStateEchoed,
                    approvalRecordCorrelationEchoed,
                    approvalRecordHandoffContextComplete,
                    approvalRecordFixtureReadOnly,
                    false,
                    false,
                    false,
                    false,
                    false,
                    approvalRecordFixtureReadOnly,
                    false
            );
        }
    }

    private record ApprovalHandoffVerificationMarkerFlags(
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
            boolean nodeMayTreatAsProductionAuditRecord
    ) {

        static ApprovalHandoffVerificationMarkerFlags fromReadOnly(
                boolean nodeV211HandoffAccepted,
                boolean nodeV211NoWriteBoundaryAccepted,
                boolean javaApprovalRecordPersisted,
                boolean javaApprovalLedgerWritten,
                boolean readyForNodeV213RestoreDrillPlan
        ) {
            return new ApprovalHandoffVerificationMarkerFlags(
                    true,
                    nodeV211HandoffAccepted,
                    nodeV211NoWriteBoundaryAccepted,
                    true,
                    true,
                    true,
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    javaApprovalRecordPersisted,
                    javaApprovalLedgerWritten,
                    readyForNodeV213RestoreDrillPlan,
                    false
            );
        }
    }
}
