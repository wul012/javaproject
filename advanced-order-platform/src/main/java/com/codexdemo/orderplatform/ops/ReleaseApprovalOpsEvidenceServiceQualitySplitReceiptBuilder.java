package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalOpsEvidenceServiceQualitySplitReceiptBuilder {

    private static final String RECEIPT_VERSION =
            "java-release-approval-rehearsal-ops-evidence-service-quality-split-receipt.v1";

    private static final String SOURCE_SCHEMA_VERSION =
            "java-release-approval-rehearsal-response-schema.v12";

    private static final String NODE_V218_VERSION = "Node v218";

    private static final String NODE_V218_PROFILE =
            "audit-route-managed-audit-helper-quality-pass.v1";

    private static final String NODE_V219_VERSION = "Node v219";

    private static final String NODE_V219_PROFILE =
            "managed-audit-adapter-implementation-precheck-packet.v1";

    ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt build(
            ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditProductionAdapterPrerequisiteReceipt
                    managedAuditProductionAdapterPrerequisiteReceipt
    ) {
        boolean sourceReceiptAccepted =
                OpsEvidenceService
                        .RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_VERSION
                        .equals(managedAuditProductionAdapterPrerequisiteReceipt.receiptVersion())
                        && managedAuditProductionAdapterPrerequisiteReceipt
                                .readyForNodeV217ProductionHardeningReadinessGate()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersDeployment()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaTriggersRollback()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesRestore()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerDeployment()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayTriggerRollback()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteRestore()
                        && !managedAuditProductionAdapterPrerequisiteReceipt.nodeMayTreatAsProductionAuditRecord();
        List<String> receiptWarnings = new ArrayList<>();
        if (!sourceReceiptAccepted) {
            receiptWarnings.add("NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY");
        }

        boolean responsibilitiesDocumented = true;
        boolean firstSafeSplitApplied = false;
        boolean readyForNodeV219ImplementationPrecheck =
                sourceReceiptAccepted && responsibilitiesDocumented;

        return new ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt(
                RECEIPT_VERSION,
                managedAuditProductionAdapterPrerequisiteReceipt.receiptVersion(),
                SOURCE_SCHEMA_VERSION,
                NODE_V218_VERSION,
                NODE_V218_PROFILE,
                NODE_V219_VERSION,
                NODE_V219_PROFILE,
                true,
                true,
                true,
                true,
                true,
                true,
                firstSafeSplitApplied,
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
                readyForNodeV219ImplementationPrecheck,
                false,
                false,
                false,
                List.of(
                        "receipt builders own Node-facing handoff and prerequisite response blocks",
                        "digest helpers own warningDigestInputs and proofClaims stability",
                        "hint builders own request/header echo and read-only readiness hints",
                        "render responsibilities remain outside Java API response assembly",
                        "record types own response shape and schema-versioned field names"
                ),
                List.of(
                        "Extract receipt builders after Node v219 has consumed v79 schema v13",
                        "Extract digest helpers only after warningDigest repeatability tests stay green",
                        "Extract hint builders in small groups without changing endpoint paths",
                        "Keep record declarations schema-first until Node v220 adapter wiring plan is clear",
                        "Run focused release approval rehearsal tests after each split"
                ),
                List.of(
                        "OpsEvidenceService still coordinates many evidence families, so broad split is deferred",
                        "Node v219 needs a stable schema v13 before Java moves helper classes",
                        "Receipt extraction must not change warningDigest ordering or response field names",
                        "No real adapter wiring exists yet, so quality work must stay read-only"
                ),
                List.of(
                        "Create approval decision during Java v79 quality pass",
                        "Write approval ledger during Java v79 quality pass",
                        "Persist production approval record during Java v79 quality pass",
                        "Write managed audit store during Java v79 quality pass",
                        "Execute SQL during Java v79 quality pass",
                        "Trigger deployment or rollback during Java v79 quality pass",
                        "Execute restore during Java v79 quality pass",
                        "Change release approval rehearsal API path during Java v79 quality pass"
                ),
                List.of(
                        "Node v218 audit route and managed audit helper quality pass must be complete",
                        "Java v79 quality split receipt must expose receipt digest hint render record boundaries",
                        "mini-kv v88 command dispatch quality receipt must be present before Node v219",
                        "Node v219 must remain an implementation precheck and not connect real managed audit",
                        "UPSTREAM_ACTIONS_ENABLED must remain false"
                ),
                List.copyOf(receiptWarnings),
                List.of(
                        "Compare opsEvidenceServiceQualitySplitReceipt.consumedByNodeQualityPassVersion with Node v218",
                        "Require opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck=true before Node v219",
                        "Keep opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false",
                        "Keep opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false",
                        "Keep opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false"
                )
        );
    }
}
