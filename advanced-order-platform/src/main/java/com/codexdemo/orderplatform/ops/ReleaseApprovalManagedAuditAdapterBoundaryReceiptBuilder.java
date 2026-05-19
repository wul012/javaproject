package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalManagedAuditAdapterBoundaryReceiptBuilder {

    private static final String RECEIPT_VERSION =
            "java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1";

    private static final String SOURCE_SCHEMA_VERSION =
            "java-release-approval-rehearsal-response-schema.v10";

    private static final String NODE_V214_PROFILE =
            "managed-audit-restore-drill-archive-verification.v1";

    private static final String NODE_V214_STATE =
            "verified-restore-drill-archive";

    private static final String NODE_V214_ENDPOINT =
            "/api/v1/audit/managed-audit-restore-drill-archive-verification";

    private static final String NODE_V215_VERSION = "Node v215";

    private static final String NODE_V215_PROFILE =
            "managed-audit-dry-run-adapter-candidate.v1";

    ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt build(
            ReleaseApprovalRehearsalResponseRecords.RehearsalApprovalHandoffVerificationMarker
                    approvalHandoffVerificationMarker
    ) {
        boolean sourceMarkerAccepted =
                OpsEvidenceService.RELEASE_APPROVAL_REHEARSAL_APPROVAL_HANDOFF_VERIFICATION_MARKER_VERSION.equals(
                        approvalHandoffVerificationMarker.markerVersion()
                )
                        && approvalHandoffVerificationMarker.readyForNodeV213RestoreDrillPlan()
                        && !approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed()
                        && !approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated()
                        && !approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten()
                        && !approvalHandoffVerificationMarker.javaApprovalRecordPersisted()
                        && !approvalHandoffVerificationMarker.javaApprovalLedgerWritten()
                        && !approvalHandoffVerificationMarker.nodeMayTreatAsProductionAuditRecord();
        List<String> receiptWarnings = new ArrayList<>();
        if (!sourceMarkerAccepted) {
            receiptWarnings.add("NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY");
        }
        boolean adapterWritesBlocked = true;
        boolean readyForNodeV215DryRunAdapterCandidate = sourceMarkerAccepted && adapterWritesBlocked;

        return new ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterBoundaryReceipt(
                RECEIPT_VERSION,
                approvalHandoffVerificationMarker.markerVersion(),
                SOURCE_SCHEMA_VERSION,
                NODE_V214_PROFILE,
                NODE_V214_STATE,
                NODE_V214_ENDPOINT,
                NODE_V215_VERSION,
                NODE_V215_PROFILE,
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
                false,
                false,
                readyForNodeV215DryRunAdapterCandidate,
                false,
                false,
                false,
                List.of(
                        "Node v214 managed audit restore drill archive verification",
                        "Java v76 approval handoff verification marker",
                        "mini-kv v86 managed audit adapter restore boundary receipt must be present before Node v215"
                ),
                List.of(
                        "Node v215 may only write Node local .tmp or controlled test files",
                        "Node v215 must not connect real managed audit storage",
                        "Node v215 must not create Java approval decision",
                        "Node v215 must not write Java approval ledger",
                        "Node v215 must not execute Java SQL deployment rollback or restore"
                ),
                List.of(
                        "Connect real managed audit storage from Node v215",
                        "Create Java approval decision from Node v215",
                        "Write Java approval ledger from Node v215",
                        "Persist Java approval record from Node v215",
                        "Execute Java SQL from Node v215",
                        "Trigger Java deployment from Node v215",
                        "Trigger Java rollback from Node v215",
                        "Execute restore from Node v215",
                        "Set UPSTREAM_ACTIONS_ENABLED=true for Node v215"
                ),
                List.of(
                        "Node v214 managed audit restore drill archive verification must be verified",
                        "Java v77 managed audit adapter boundary receipt must be ready",
                        "mini-kv v86 managed audit adapter restore boundary receipt must be present",
                        "Node v215 writes only local .tmp or controlled test files",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                ),
                List.copyOf(receiptWarnings),
                List.of(
                        "Compare managedAuditAdapterBoundaryReceipt.consumedByNodeArchiveVerificationVersion with Node v214 profileVersion",
                        "Require managedAuditAdapterBoundaryReceipt.readyForNodeV215DryRunAdapterCandidate=true before Node v215",
                        "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false",
                        "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false",
                        "Keep managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false"
                )
        );
    }
}
