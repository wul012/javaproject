package com.codexdemo.orderplatform;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "order.expiration.enabled=false",
        "outbox.publisher.enabled=false"
})
@AutoConfigureMockMvc
class OpsReleaseApprovalRehearsalAuditHandoffLiveAggregationIntegrationTests
        extends OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport {

    @Test
    void releaseApprovalRehearsalReturnsAuditAndApprovalHandoffReadOnlySignals() throws Exception {
        seedReleaseApprovalReplayApprovals();

        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.hintVersion")
                        .value("java-release-approval-rehearsal-audit-persistence-handoff-hint.v1"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.sourceRetentionFixtureVersion")
                        .value("java-release-audit-retention-fixture.v1"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.sourceRetentionFixtureEndpoint")
                        .value("/contracts/release-audit-retention.fixture.json"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.javaRetentionDays").value(180))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditCandidateVersion")
                        .value("managed-audit-candidate-version-not-supplied"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditCandidateVersionSource")
                        .value("NOT_SUPPLIED"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditCandidateDigest")
                        .value("managed-audit-candidate-digest-not-supplied"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditSinkMode")
                        .value("managed-audit-sink-mode-not-supplied"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditRetentionDays")
                        .value("managed-audit-retention-days-not-supplied"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditRotationPolicy")
                        .value("managed-audit-rotation-policy-not-supplied"))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.auditPersistenceHandoffContextComplete")
                        .value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.managedAuditRetentionWithinJavaRetention")
                        .value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.javaAuditSourceReadOnly").value(true))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.javaLedgerWriteAllowed").value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.javaManagedAuditWriteAllowed").value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.javaExternalAuditSystemAccessed").value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.productionAuditStoreRequired").value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.nodeMayUseAsManagedAuditInput").value(true))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord")
                        .value(false))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.acceptedAuditPersistenceHeaders",
                        hasItem("x-orderops-managed-audit-candidate-version")))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.acceptedAuditPersistenceHeaders",
                        hasItem("x-orderops-managed-audit-retention-days")))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.handoffFieldPaths",
                        hasItem("verificationHint.warningDigest")))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.readOnlySourceEndpoints",
                        hasItem("/api/v1/ops/release-approval-rehearsal")))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.echoWarnings",
                        hasItem("ORDEROPS_MANAGED_AUDIT_CANDIDATE_VERSION_MISSING")))
                .andExpect(jsonPath("$.auditPersistenceHandoffHint.echoWarnings",
                        hasItem("ORDEROPS_MANAGED_AUDIT_ROTATION_POLICY_MISSING")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.hintVersion")
                        .value("java-release-approval-rehearsal-approval-record-handoff-hint.v1"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.sourceApprovalRecordFixtureVersion")
                        .value("java-rollback-approval-record-fixture.v1"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.sourceApprovalRecordFixtureEndpoint")
                        .value("/contracts/rollback-approval-record.fixture.json"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.reviewerPlaceholder")
                        .value("rollback-reviewer-placeholder"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalTimestampPlaceholder")
                        .value("approval-timestamp-placeholder"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.rollbackTarget")
                        .value("release-tag-or-artifact-version-placeholder"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.selectedMigrationDirection")
                        .value("no-database-change"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalBindingContractVersion")
                        .value("approval-binding-contract-version-not-supplied"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalBindingContractVersionSource")
                        .value("NOT_SUPPLIED"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalBindingContractDigest")
                        .value("approval-binding-contract-digest-not-supplied"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalRequestId")
                        .value("approval-request-id-not-supplied"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalDecisionState")
                        .value("approval-decision-state-not-supplied"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalRecordCorrelationId")
                        .value("approval-record-correlation-id-not-supplied"))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalRecordHandoffContextComplete")
                        .value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.approvalRecordFixtureReadOnly").value(true))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.javaApprovalDecisionCreated").value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.javaApprovalLedgerWritten").value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.javaApprovalRecordPersisted").value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.javaApprovalRecordAuthenticated").value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.productionApprovalStoreRequired").value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.nodeMayUseAsAuditApprovalInput").value(true))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord")
                        .value(false))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.acceptedApprovalRecordHeaders",
                        hasItem("x-orderops-approval-binding-contract-version")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.acceptedApprovalRecordHeaders",
                        hasItem("x-orderops-approval-record-correlation-id")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.handoffFieldPaths",
                        hasItem("approvalRecordHandoffHint.approvalRequestId")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.handoffFieldPaths",
                        hasItem("verificationHint.warningDigest")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.sourceRecordArtifacts",
                        hasItem("/contracts/rollback-approval-handoff.sample.json")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.sourceRecordArtifacts",
                        hasItem("/contracts/rollback-sql-review-gate.sample.json")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.echoWarnings",
                        hasItem("ORDEROPS_APPROVAL_BINDING_CONTRACT_VERSION_MISSING")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.echoWarnings",
                        hasItem("ORDEROPS_APPROVAL_RECORD_CORRELATION_ID_MISSING")))
                .andExpect(jsonPath("$.approvalRecordHandoffHint.nodeVerificationActions",
                        hasItem("Compare approvalRecordHandoffHint.approvalBindingContractVersion with Node v210 binding contract")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.markerVersion")
                        .value("java-release-approval-rehearsal-approval-handoff-verification-marker.v1"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.sourceApprovalRecordHandoffHintVersion")
                        .value("java-release-approval-rehearsal-approval-record-handoff-hint.v1"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.sourceApprovalRecordHandoffSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v9"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeProfileVersion")
                        .value("managed-audit-identity-approval-provenance-dry-run-packet.v1"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodePacketState")
                        .value("dry-run-packet-verified"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeEndpoint")
                        .value("/api/v1/audit/managed-identity-approval-provenance-dry-run-packet"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeRequestId")
                        .value("managed-audit-v211-identity-approval-provenance-request"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodePacketVersion")
                        .value("managed-audit-dry-run-record.v2-candidate"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeBindingContractVersion")
                        .value("managed-audit-identity-approval-binding-contract.v1"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeDryRunDirectoryLabel")
                        .value(".tmp"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeDryRunDirectoryPrefix")
                        .value("managed-audit-v211-"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedByNodeDryRunFileName")
                        .value("managed-audit-packet.jsonl"))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211MayConsume").value(true))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211HandoffAccepted").value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211NoWriteBoundaryAccepted").value(true))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211PacketAppendCovered").value(true))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211PacketQueryCovered").value(true))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211PacketDigestCovered").value(true))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211PacketCleanupCovered").value(true))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211JavaWriteAttempted").value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211MiniKvWriteAttempted").value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211ExternalAuditSystemAccessed")
                        .value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated")
                        .value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed")
                        .value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.javaApprovalRecordPersisted").value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.javaApprovalLedgerWritten").value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.readyForNodeV213RestoreDrillPlan")
                        .value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeMayTreatAsProductionAuditRecord")
                        .value(false))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedHandoffFieldPaths",
                        hasItem("approvalRecordHandoffHint.approvalRequestId")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedHandoffFieldPaths",
                        hasItem("approvalRecordHandoffHint.approvalTimestampPlaceholder")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.consumedHandoffFieldPaths",
                        hasItem("verificationHint.warningDigest")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211AcceptedChecks",
                        hasItem("javaV75HandoffAccepted")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV211AcceptedChecks",
                        hasItem("cleanupCovered")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV213Prerequisites",
                        hasItem("Java v76 marker readyForNodeV213RestoreDrillPlan must be true")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeV213Prerequisites",
                        hasItem("UPSTREAM_ACTIONS_ENABLED must remain false")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.markerWarnings",
                        hasItem("NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE")))
                .andExpect(jsonPath("$.approvalHandoffVerificationMarker.nodeVerificationActions",
                        hasItem("Keep approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false")))
                ;
    }
}
