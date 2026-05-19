package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalManagedAuditProductionAdapterPrerequisiteReceiptBuilder {

    private static final String RECEIPT_VERSION =
            "java-release-approval-rehearsal-managed-audit-production-adapter-prerequisite-receipt.v1";

    private static final String SOURCE_SCHEMA_VERSION =
            "java-release-approval-rehearsal-response-schema.v11";

    private static final String NODE_V216_PROFILE =
            "managed-audit-dry-run-adapter-archive-verification.v1";

    private static final String NODE_V216_STATE =
            "verified-dry-run-adapter-archive";

    private static final String NODE_V216_ENDPOINT =
            "/api/v1/audit/managed-audit-dry-run-adapter-archive-verification";

    private static final String NODE_V217_VERSION = "Node v217";

    private static final String NODE_V217_PROFILE =
            "managed-audit-adapter-production-hardening-readiness-gate.v1";

    ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt build(
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt
                    managedAuditAdapterBoundaryReceipt
    ) {
        boolean sourceReceiptAccepted =
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_VERSION.equals(
                        managedAuditAdapterBoundaryReceipt.receiptVersion()
                )
                        && managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayPersistApprovalRecord()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback()
                        && !managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore()
                        && !managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated()
                        && !managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten()
                        && !managedAuditAdapterBoundaryReceipt.javaApprovalRecordPersisted()
                        && !managedAuditAdapterBoundaryReceipt.javaManagedAuditWriteExecuted()
                        && !managedAuditAdapterBoundaryReceipt.nodeMayTreatAsProductionAuditRecord();
        List<String> receiptWarnings = new ArrayList<>();
        if (!sourceReceiptAccepted) {
            receiptWarnings.add("NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY");
        }
        boolean prerequisitesDocumented = true;
        boolean readyForNodeV217ProductionHardeningReadinessGate =
                sourceReceiptAccepted && prerequisitesDocumented;

        return new ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt(
                        RECEIPT_VERSION,
                        managedAuditAdapterBoundaryReceipt.receiptVersion(),
                        SOURCE_SCHEMA_VERSION,
                        NODE_V216_PROFILE,
                        NODE_V216_STATE,
                        NODE_V216_ENDPOINT,
                        NODE_V217_VERSION,
                        NODE_V217_PROFILE,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
                        true,
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
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        readyForNodeV217ProductionHardeningReadinessGate,
                        false,
                        false,
                        false,
                        false,
                        List.of(
                                "operator identity",
                                "approval decision source",
                                "ledger handoff",
                                "retention owner",
                                "failure handling",
                                "rollback review"
                        ),
                        List.of(
                                "Production operator identity must be bound by a real IdP outside Java v78",
                                "Approval decision source must be a real approval workflow outside Java v78",
                                "Approval ledger handoff must define ownership and append semantics outside Java v78",
                                "Managed audit retention owner must be assigned before production adapter work",
                                "Managed audit failure handling taxonomy must be reviewed before production adapter work",
                                "Rollback review evidence must exist before production adapter work"
                        ),
                        List.of(
                                "Connect real managed audit storage from Java v78 or Node v217",
                                "Create real approval decision from Java v78",
                                "Write approval ledger from Java v78 or Node v217",
                                "Persist production approval record from Java v78",
                                "Execute Java SQL from Java v78 or Node v217",
                                "Trigger deployment from Java v78 or Node v217",
                                "Trigger rollback from Java v78 or Node v217",
                                "Execute restore from Java v78 or Node v217",
                                "Open production audit window from this receipt"
                        ),
                        List.of(
                                "Node v216 managed audit dry-run adapter archive verification must be verified",
                                "Java v78 managed audit production adapter prerequisite receipt must be ready",
                                "mini-kv v87 managed audit adapter non-authoritative storage receipt must be present",
                                "Node v217 must remain a production-hardening readiness gate",
                                "UPSTREAM_ACTIONS_ENABLED must remain false"
                        ),
                        List.copyOf(receiptWarnings),
                        List.of(
                                "Compare managedAuditProductionAdapterPrerequisiteReceipt.consumedByNodeArchiveVerificationVersion with Node v216 profileVersion",
                                "Require managedAuditProductionAdapterPrerequisiteReceipt.readyForNodeV217ProductionHardeningReadinessGate=true before Node v217",
                                "Keep managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false",
                                "Keep managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false",
                                "Keep managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false"
                        )
                );
    }
}
