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
class OpsReleaseApprovalRehearsalAdapterReceiptLiveAggregationIntegrationTests
        extends OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport {

    @Test
    void releaseApprovalRehearsalReturnsAdapterReceiptReadOnlySignals() throws Exception {
        seedReleaseApprovalReplayApprovals();

        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.sourceApprovalHandoffMarkerVersion")
                        .value("java-release-approval-rehearsal-approval-handoff-verification-marker.v1"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.sourceApprovalHandoffSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v10"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion")
                        .value("managed-audit-restore-drill-archive-verification.v1"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationState")
                        .value("verified-restore-drill-archive"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationEndpoint")
                        .value("/api/v1/audit/managed-audit-restore-drill-archive-verification"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nextNodeCandidateVersion")
                        .value("Node v215"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nextNodeCandidateProfile")
                        .value("managed-audit-dry-run-adapter-candidate.v1"))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayConsume").value(true))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayWriteLocalDryRunFiles")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayPersistApprovalRecord")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql").value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.javaApprovalRecordPersisted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.javaManagedAuditWriteExecuted")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.readyForProductionAudit").value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.readyForProductionWindow").value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeMayTreatAsProductionAuditRecord")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.acceptedSourceReceipts",
                        hasItem("Node v214 managed audit restore drill archive verification")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.acceptedSourceReceipts",
                        hasItem("mini-kv v86 managed audit adapter restore boundary receipt must be present before Node v215")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.adapterBoundaryClaims",
                        hasItem("Node v215 must not connect real managed audit storage")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.adapterBoundaryClaims",
                        hasItem("Node v215 must not write Java approval ledger")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.forbiddenAdapterOperations",
                        hasItem("Execute Java SQL from Node v215")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.forbiddenAdapterOperations",
                        hasItem("Set UPSTREAM_ACTIONS_ENABLED=true for Node v215")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeV215Prerequisites",
                        hasItem("Java v77 managed audit adapter boundary receipt must be ready")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.receiptWarnings",
                        hasItem("NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY")))
                .andExpect(jsonPath("$.managedAuditAdapterBoundaryReceipt.nodeVerificationActions",
                        hasItem("Keep managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false")))
                ;
    }
}
