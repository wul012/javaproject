package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalTestSupport;
import org.junit.jupiter.api.Test;

class OpsEvidenceServiceReleaseApprovalRehearsalAuditHandoffOverviewTests
    extends ReleaseApprovalRehearsalTestSupport {

  @Test
  void buildsReleaseApprovalRehearsalAuditHandoffOverviewForDefaultRequest() {
    OpsEvidenceService service = readOnlyFixtureService();

    ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
    assertThat(rehearsal.auditPersistenceHandoffHint().hintVersion())
        .isEqualTo("java-release-approval-rehearsal-audit-persistence-handoff-hint.v1");
    assertThat(rehearsal.auditPersistenceHandoffHint().sourceRetentionFixtureVersion())
        .isEqualTo("java-release-audit-retention-fixture.v1");
    assertThat(rehearsal.auditPersistenceHandoffHint().sourceRetentionFixtureEndpoint())
        .isEqualTo("/contracts/release-audit-retention.fixture.json");
    assertThat(rehearsal.auditPersistenceHandoffHint().javaRetentionDays()).isEqualTo(180);
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersion())
        .isEqualTo("managed-audit-candidate-version-not-supplied");
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateVersionSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigest())
        .isEqualTo("managed-audit-candidate-digest-not-supplied");
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditCandidateDigestSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditSinkMode())
        .isEqualTo("managed-audit-sink-mode-not-supplied");
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRetentionDays())
        .isEqualTo("managed-audit-retention-days-not-supplied");
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRotationPolicy())
        .isEqualTo("managed-audit-rotation-policy-not-supplied");
    assertThat(rehearsal.auditPersistenceHandoffHint().candidateVersionEchoed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().candidateDigestEchoed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().sinkModeEchoed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().retentionDaysEchoed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().rotationPolicyEchoed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().auditPersistenceHandoffContextComplete())
        .isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().managedAuditRetentionWithinJavaRetention())
        .isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().javaAuditSourceReadOnly()).isTrue();
    assertThat(rehearsal.auditPersistenceHandoffHint().javaLedgerWriteAllowed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().javaManagedAuditWriteAllowed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().javaExternalAuditSystemAccessed()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().productionAuditStoreRequired()).isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().nodeMayUseAsManagedAuditInput()).isTrue();
    assertThat(rehearsal.auditPersistenceHandoffHint().nodeMayTreatAsProductionAuditRecord())
        .isFalse();
    assertThat(rehearsal.auditPersistenceHandoffHint().acceptedAuditPersistenceHeaders())
        .containsExactly(
            "x-orderops-managed-audit-candidate-version",
            "x-orderops-managed-audit-candidate-digest",
            "x-orderops-managed-audit-sink-mode",
            "x-orderops-managed-audit-retention-days",
            "x-orderops-managed-audit-rotation-policy");
    assertThat(rehearsal.auditPersistenceHandoffHint().handoffFieldPaths())
        .contains(
            "requestContext.requestId",
            "operatorWindowHint.operatorId",
            "verificationHint.warningDigest",
            "executionBoundaries.nodeMayWriteApprovalLedger");
    assertThat(rehearsal.auditPersistenceHandoffHint().readOnlySourceEndpoints())
        .containsExactly(
            "/api/v1/ops/release-approval-rehearsal",
            "/contracts/release-audit-retention.fixture.json",
            "/api/v1/ops/evidence");
    assertThat(rehearsal.auditPersistenceHandoffHint().echoWarnings())
        .containsExactly(
            "ORDEROPS_MANAGED_AUDIT_CANDIDATE_VERSION_MISSING",
            "ORDEROPS_MANAGED_AUDIT_CANDIDATE_DIGEST_MISSING",
            "ORDEROPS_MANAGED_AUDIT_SINK_MODE_MISSING",
            "ORDEROPS_MANAGED_AUDIT_RETENTION_DAYS_MISSING",
            "ORDEROPS_MANAGED_AUDIT_ROTATION_POLICY_MISSING");
    assertThat(rehearsal.auditPersistenceHandoffHint().nodeVerificationActions())
        .contains(
            "Compare auditPersistenceHandoffHint.managedAuditCandidateDigest with Node v208 adapter digest",
            "Persist only the listed handoffFieldPaths in Node managed audit dry-run storage",
            "Keep javaManagedAuditWriteAllowed=false and nodeMayTreatAsProductionAuditRecord=false");
    assertThat(rehearsal.approvalRecordHandoffHint().hintVersion())
        .isEqualTo("java-release-approval-rehearsal-approval-record-handoff-hint.v1");
    assertThat(rehearsal.approvalRecordHandoffHint().sourceApprovalRecordFixtureVersion())
        .isEqualTo("java-rollback-approval-record-fixture.v1");
    assertThat(rehearsal.approvalRecordHandoffHint().sourceApprovalRecordFixtureEndpoint())
        .isEqualTo("/contracts/rollback-approval-record.fixture.json");
    assertThat(rehearsal.approvalRecordHandoffHint().reviewerPlaceholder())
        .isEqualTo("rollback-reviewer-placeholder");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalTimestampPlaceholder())
        .isEqualTo("approval-timestamp-placeholder");
    assertThat(rehearsal.approvalRecordHandoffHint().rollbackTarget())
        .isEqualTo("release-tag-or-artifact-version-placeholder");
    assertThat(rehearsal.approvalRecordHandoffHint().selectedMigrationDirection())
        .isEqualTo("no-database-change");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersion())
        .isEqualTo("approval-binding-contract-version-not-supplied");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersionSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigest())
        .isEqualTo("approval-binding-contract-digest-not-supplied");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigestSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestId())
        .isEqualTo("approval-request-id-not-supplied");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestIdSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionState())
        .isEqualTo("approval-decision-state-not-supplied");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionStateSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationId())
        .isEqualTo("approval-record-correlation-id-not-supplied");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationIdSource())
        .isEqualTo("NOT_SUPPLIED");
    assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractVersionEchoed())
        .isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().approvalBindingContractDigestEchoed())
        .isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRequestIdEchoed()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().approvalDecisionStateEchoed()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordCorrelationEchoed()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordHandoffContextComplete())
        .isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().approvalRecordFixtureReadOnly()).isTrue();
    assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalDecisionCreated()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalLedgerWritten()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalRecordPersisted()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().javaApprovalRecordAuthenticated()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().productionApprovalStoreRequired()).isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().nodeMayUseAsAuditApprovalInput()).isTrue();
    assertThat(rehearsal.approvalRecordHandoffHint().nodeMayTreatAsProductionApprovalRecord())
        .isFalse();
    assertThat(rehearsal.approvalRecordHandoffHint().acceptedApprovalRecordHeaders())
        .containsExactly(
            "x-orderops-approval-binding-contract-version",
            "x-orderops-approval-binding-contract-digest",
            "x-orderops-approval-request-id",
            "x-orderops-approval-decision-state",
            "x-orderops-approval-record-correlation-id");
    assertThat(rehearsal.approvalRecordHandoffHint().handoffFieldPaths())
        .contains(
            "operatorWindowHint.operatorId",
            "approvalRecordHandoffHint.approvalRequestId",
            "verificationHint.warningDigest");
    assertThat(rehearsal.approvalRecordHandoffHint().sourceRecordArtifacts())
        .contains(
            "/contracts/rollback-approval-handoff.sample.json",
            "/contracts/rollback-approver-evidence.fixture.json",
            "/contracts/rollback-sql-review-gate.sample.json");
    assertThat(rehearsal.approvalRecordHandoffHint().echoWarnings())
        .containsExactly(
            "ORDEROPS_APPROVAL_BINDING_CONTRACT_VERSION_MISSING",
            "ORDEROPS_APPROVAL_BINDING_CONTRACT_DIGEST_MISSING",
            "ORDEROPS_APPROVAL_REQUEST_ID_MISSING",
            "ORDEROPS_APPROVAL_DECISION_STATE_MISSING",
            "ORDEROPS_APPROVAL_RECORD_CORRELATION_ID_MISSING");
    assertThat(rehearsal.approvalRecordHandoffHint().nodeVerificationActions())
        .contains(
            "Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract",
            "Compare approvalRecordHandoffHint.approvalBindingContractDigest with Node v210 binding digest",
            "Keep javaApprovalRecordPersisted=false and nodeMayTreatAsProductionApprovalRecord=false");
    assertThat(rehearsal.approvalHandoffVerificationMarker().markerVersion())
        .isEqualTo("java-release-approval-rehearsal-approval-handoff-verification-marker.v1");
    assertThat(
            rehearsal.approvalHandoffVerificationMarker().sourceApprovalRecordHandoffHintVersion())
        .isEqualTo("java-release-approval-rehearsal-approval-record-handoff-hint.v1");
    assertThat(
            rehearsal
                .approvalHandoffVerificationMarker()
                .sourceApprovalRecordHandoffSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v9");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeProfileVersion())
        .isEqualTo("managed-audit-identity-approval-provenance-dry-run-packet.v1");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodePacketState())
        .isEqualTo("dry-run-packet-verified");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeEndpoint())
        .isEqualTo("/api/v1/audit/managed-identity-approval-provenance-dry-run-packet");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeRequestId())
        .isEqualTo("managed-audit-v211-identity-approval-provenance-request");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodePacketVersion())
        .isEqualTo("managed-audit-dry-run-record.v2-candidate");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeBindingContractVersion())
        .isEqualTo("managed-audit-identity-approval-binding-contract.v1");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunDirectoryLabel())
        .isEqualTo(".tmp");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunDirectoryPrefix())
        .isEqualTo("managed-audit-v211-");
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedByNodeDryRunFileName())
        .isEqualTo("managed-audit-packet.jsonl");
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211MayConsume()).isTrue();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211HandoffAccepted()).isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211NoWriteBoundaryAccepted())
        .isTrue();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketAppendCovered())
        .isTrue();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketQueryCovered()).isTrue();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketDigestCovered())
        .isTrue();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211PacketCleanupCovered())
        .isTrue();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211JavaWriteAttempted())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211MiniKvWriteAttempted())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211ExternalAuditSystemAccessed())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalDecisionCreated())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211RealApprovalLedgerWritten())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211ProductionAuditRecordAllowed())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().javaApprovalRecordPersisted())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().javaApprovalLedgerWritten()).isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().readyForNodeV213RestoreDrillPlan())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeMayTreatAsProductionAuditRecord())
        .isFalse();
    assertThat(rehearsal.approvalHandoffVerificationMarker().consumedHandoffFieldPaths())
        .containsExactly(
            "requestContext.requestId",
            "operatorWindowHint.operatorId",
            "operatorWindowHint.operatorRoles",
            "approvalRecordHandoffHint.approvalRequestId",
            "approvalRecordHandoffHint.approvalDecisionState",
            "approvalRecordHandoffHint.approvalRecordCorrelationId",
            "approvalRecordHandoffHint.reviewerPlaceholder",
            "approvalRecordHandoffHint.approvalTimestampPlaceholder",
            "verificationHint.warningDigest");
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV211AcceptedChecks())
        .contains(
            "javaV75HandoffAccepted",
            "javaV75NoWriteBoundaryValid",
            "appendCovered",
            "cleanupCovered",
            "noRealApprovalDecisionCreated");
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeV213Prerequisites())
        .contains(
            "Java v76 marker readyForNodeV213RestoreDrillPlan must be true",
            "mini-kv v85 retention provenance replay marker must be present",
            "UPSTREAM_ACTIONS_ENABLED must remain false");
    assertThat(rehearsal.approvalHandoffVerificationMarker().markerWarnings())
        .containsExactly("NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE");
    assertThat(rehearsal.approvalHandoffVerificationMarker().nodeVerificationActions())
        .contains(
            "Compare approvalHandoffVerificationMarker.consumedByNodeProfileVersion with Node v211 profileVersion",
            "Require approvalHandoffVerificationMarker.nodeV211HandoffAccepted=true before Node v213 restore drill plan",
            "Keep approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().receiptVersion())
        .isEqualTo("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().sourceApprovalHandoffMarkerVersion())
        .isEqualTo("java-release-approval-rehearsal-approval-handoff-verification-marker.v1");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().sourceApprovalHandoffSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v10");
    assertThat(
            rehearsal
                .managedAuditAdapterBoundaryReceipt()
                .consumedByNodeArchiveVerificationVersion())
        .isEqualTo("managed-audit-restore-drill-archive-verification.v1");
    assertThat(
            rehearsal.managedAuditAdapterBoundaryReceipt().consumedByNodeArchiveVerificationState())
        .isEqualTo("verified-restore-drill-archive");
    assertThat(
            rehearsal
                .managedAuditAdapterBoundaryReceipt()
                .consumedByNodeArchiveVerificationEndpoint())
        .isEqualTo("/api/v1/audit/managed-audit-restore-drill-archive-verification");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nextNodeCandidateVersion())
        .isEqualTo("Node v215");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nextNodeCandidateProfile())
        .isEqualTo("managed-audit-dry-run-adapter-candidate.v1");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConsume()).isTrue();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteLocalDryRunFiles())
        .isTrue();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayConnectManagedAudit())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayCreateApprovalDecision())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayWriteApprovalLedger())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayPersistApprovalRecord())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteSql()).isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerDeployment())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayTriggerRollback())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215MayExecuteRestore())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalDecisionCreated())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalLedgerWritten())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaApprovalRecordPersisted())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaManagedAuditWriteExecuted())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRollbackSqlExecuted()).isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaDeploymentTriggered()).isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRollbackTriggered()).isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().javaRestoreExecuted()).isFalse();
    assertThat(
            rehearsal.managedAuditAdapterBoundaryReceipt().readyForNodeV215DryRunAdapterCandidate())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionAudit()).isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().readyForProductionWindow()).isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeMayTreatAsProductionAuditRecord())
        .isFalse();
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().acceptedSourceReceipts())
        .contains(
            "Node v214 managed audit restore drill archive verification",
            "Java v76 approval handoff verification marker",
            "mini-kv v86 managed audit adapter restore boundary receipt must be present before Node v215");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().adapterBoundaryClaims())
        .contains(
            "Node v215 may only write Node local .tmp or controlled test files",
            "Node v215 must not connect real managed audit storage",
            "Node v215 must not create Java approval decision",
            "Node v215 must not write Java approval ledger",
            "Node v215 must not execute Java SQL deployment rollback or restore");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().forbiddenAdapterOperations())
        .contains(
            "Connect real managed audit storage from Node v215",
            "Create Java approval decision from Node v215",
            "Write Java approval ledger from Node v215",
            "Persist Java approval record from Node v215",
            "Execute Java SQL from Node v215",
            "Set UPSTREAM_ACTIONS_ENABLED=true for Node v215");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeV215Prerequisites())
        .contains(
            "Node v214 managed audit restore drill archive verification must be verified",
            "Java v77 managed audit adapter boundary receipt must be ready",
            "mini-kv v86 managed audit adapter restore boundary receipt must be present",
            "UPSTREAM_ACTIONS_ENABLED must remain false");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().receiptWarnings())
        .containsExactly("NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY");
    assertThat(rehearsal.managedAuditAdapterBoundaryReceipt().nodeVerificationActions())
        .contains(
            "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
            "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
            "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false");
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptVersion())
        .isEqualTo(
            "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .sourceManagedAuditAdapterBoundaryReceiptVersion())
        .isEqualTo("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .sourceManagedAuditAdapterBoundarySchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v11");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationVersion())
        .isEqualTo("managed-audit-dry-run-adapter-archive-verification.v1");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationState())
        .isEqualTo("verified-dry-run-adapter-archive");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .consumedByNodeArchiveVerificationEndpoint())
        .isEqualTo("/api/v1/audit/managed-audit-dry-run-adapter-archive-verification");
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nextNodeGateVersion())
        .isEqualTo("Node v217");
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nextNodeGateProfile())
        .isEqualTo("managed-audit-adapter-production-hardening-readiness-gate.v1");
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayConsume())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .operatorIdentityPrerequisiteDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourcePrerequisiteDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .ledgerHandoffPrerequisiteDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .retentionOwnerPrerequisiteDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .failureHandlingPrerequisiteDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .rollbackReviewPrerequisiteDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .externalManagedAuditStorageConfigRequired())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .productionIdentityProviderRequired())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .approvalDecisionSourceRequired())
        .isTrue();
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().ledgerHandoffRequired())
        .isTrue();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().retentionOwnerRequired())
        .isTrue();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().failureHandlingRequired())
        .isTrue();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().rollbackReviewRequired())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .javaCreatesApprovalDecision())
        .isFalse();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaWritesApprovalLedger())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .javaPersistsApprovalRecord())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .javaWritesManagedAuditStore())
        .isFalse();
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesSql())
        .isFalse();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaTriggersDeployment())
        .isFalse();
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaTriggersRollback())
        .isFalse();
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().javaExecutesRestore())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayConnectManagedAudit())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayWriteApprovalLedger())
        .isFalse();
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217MayExecuteSql())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayTriggerDeployment())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayTriggerRollback())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeV217MayExecuteRestore())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForNodeV217ProductionHardeningReadinessGate())
        .isFalse();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionAudit())
        .isFalse();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().readyForProductionWindow())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .readyForProductionOperations())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .nodeMayTreatAsProductionAuditRecord())
        .isFalse();
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().prerequisiteCategories())
        .contains(
            "operator identity",
            "approval decision source",
            "ledger handoff",
            "retention owner",
            "failure handling",
            "rollback review");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .prerequisiteEvidenceRequired())
        .contains(
            "Production operator identity must be bound by a real IdP outside Java v78",
            "Approval decision source must be a real approval workflow outside Java v78",
            "Approval ledger handoff must define ownership and append semantics outside Java v78",
            "Rollback review evidence must exist before production adapter work");
    assertThat(
            rehearsal
                .managedAuditProductionAdapterPrerequisiteReceipt()
                .forbiddenProductionAdapterOperations())
        .contains(
            "Connect real managed audit storage from Java v78 or Node v217",
            "Write approval ledger from Java v78 or Node v217",
            "Execute Java SQL from Java v78 or Node v217",
            "Open production audit window from this receipt");
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeV217Prerequisites())
        .contains(
            "Node v216 managed audit dry-run adapter archive verification must be verified",
            "Java v78 managed audit production adapter prerequisite receipt must be ready",
            "mini-kv v87 managed audit adapter non-authoritative storage receipt must be present",
            "UPSTREAM_ACTIONS_ENABLED must remain false");
    assertThat(rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().receiptWarnings())
        .containsExactly("NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY");
    assertThat(
            rehearsal.managedAuditProductionAdapterPrerequisiteReceipt().nodeVerificationActions())
        .contains(
            "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
            "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
            "Keep managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false");
  }
}
