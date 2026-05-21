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
class OpsReleaseApprovalRehearsalAdapterReadinessLiveAggregationIntegrationTests
        extends OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport {

    @Test
    void releaseApprovalRehearsalReturnsAdapterReadinessReadOnlySignals() throws Exception {
        seedReleaseApprovalReplayApprovals();

        mockMvc.perform(get("/api/v1/ops/release-approval-rehearsal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.sourceManagedAuditAdapterBoundaryReceiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.sourceManagedAuditAdapterBoundarySchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v11"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion")
                        .value("managed-audit-dry-run-adapter-archive-verification.v1"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationState")
                        .value("verified-dry-run-adapter-archive"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationEndpoint")
                        .value("/api/v1/audit/managed-audit-dry-run-adapter-archive-verification"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nextNodeGateVersion")
                        .value("Node v217"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nextNodeGateProfile")
                        .value("managed-audit-adapter-production-hardening-readiness-gate.v1"))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConsume")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.operatorIdentityPrerequisiteDocumented")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.approvalDecisionSourcePrerequisiteDocumented")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.ledgerHandoffPrerequisiteDocumented")
                        .value(true))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql").value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersDeployment")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersRollback")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesRestore")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.readyForProductionAudit")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.readyForProductionWindow")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.readyForProductionOperations")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeMayTreatAsProductionAuditRecord")
                        .value(false))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.prerequisiteCategories",
                        hasItem("operator identity")))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.prerequisiteEvidenceRequired",
                        hasItem("Production operator identity must be bound by a real IdP outside Java v78")))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.forbiddenProductionAdapterOperations",
                        hasItem("Connect real managed audit storage from Java v78 or Node v217")))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeV217Prerequisites",
                        hasItem("Java v78 managed audit production adapter prerequisite receipt must be ready")))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.receiptWarnings",
                        hasItem("NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY")))
                .andExpect(jsonPath("$.managedAuditProductionAdapterPrerequisiteReceipt.nodeVerificationActions",
                        hasItem("Keep managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.receiptVersion")
                        .value("java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.sourceProductionAdapterPrerequisiteReceiptVersion")
                        .value("java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.sourceProductionAdapterPrerequisiteSchemaVersion")
                        .value("java-release-approval-rehearsal-response-schema.v12"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion")
                        .value("Node v218"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassProfile")
                        .value("audit-route-managed-audit-helper-quality-pass.v1"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.nextNodePrecheckVersion")
                        .value("Node v219"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.nextNodePrecheckProfile")
                        .value("managed-audit-adapter-implementation-precheck-packet.v1"))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.nodeV219MayConsume")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.receiptResponsibilityDocumented")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.digestResponsibilityDocumented")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.hintResponsibilityDocumented")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.renderResponsibilityDocumented")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.recordResponsibilityDocumented")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.firstSafeSplitApplied")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.broadServiceSplitDeferred")
                        .value(true))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.apiShapeChanged")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.approvalRecordPersisted")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.sqlExecuted")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.deploymentTriggered")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.rollbackTriggered")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.restoreExecuted")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.readyForProductionAudit")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.readyForProductionWindow")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.nodeMayTreatAsProductionAuditRecord")
                        .value(false))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.responsibilityBoundaries",
                        hasItem("receipt builders own Node-facing handoff and prerequisite response blocks")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.safeSplitSequence",
                        hasItem("Extract receipt builders after Node v219 has consumed v79 schema v13")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.deferredSplitReasons",
                        hasItem("Receipt extraction must not change warningDigest ordering or response field names")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.forbiddenQualityPassOperations",
                        hasItem("Write approval ledger during Java v79 quality pass")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.nodeV219Prerequisites",
                        hasItem("mini-kv v88 command dispatch quality receipt must be present before Node v219")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.receiptWarnings",
                        hasItem("NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY")))
                .andExpect(jsonPath("$.opsEvidenceServiceQualitySplitReceipt.nodeVerificationActions",
                        hasItem("Keep opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false")))
                ;
    }
}
