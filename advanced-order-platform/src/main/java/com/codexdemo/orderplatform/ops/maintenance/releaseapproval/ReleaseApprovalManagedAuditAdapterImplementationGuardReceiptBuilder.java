package com.codexdemo.orderplatform.ops.maintenance.releaseapproval;

import java.util.ArrayList;
import java.util.List;

final class ReleaseApprovalManagedAuditAdapterImplementationGuardReceiptBuilder {

  private static final String RECEIPT_VERSION =
      "java-release-approval-rehearsal-managed-audit-adapter-implementation-guard-receipt.v1";

  private static final String SOURCE_SCHEMA_VERSION =
      "java-release-approval-rehearsal-response-schema.v13";

  private static final String NODE_V220_VERSION = "Node v220";

  private static final String NODE_V220_PROFILE = "managed-audit-adapter-disabled-shell.v1";

  private static final String NODE_V220_ENDPOINT =
      "/api/v1/audit/managed-audit-adapter-disabled-shell";

  private static final String NODE_V221_VERSION = "Node v221";

  private static final String NODE_V221_PROFILE =
      "managed-audit-local-adapter-candidate-dry-run.v1";

  ReleaseApprovalRehearsalResponseRecords.RehearsalManagedAuditAdapterImplementationGuardReceipt
      build(
          ReleaseApprovalRehearsalResponseRecords.RehearsalOpsEvidenceServiceQualitySplitReceipt
              opsEvidenceServiceQualitySplitReceipt) {
    boolean sourceReceiptAccepted =
        ReleaseApprovalContractConstants
                .RELEASE_APPROVAL_REHEARSAL_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_VERSION
                .equals(opsEvidenceServiceQualitySplitReceipt.receiptVersion())
            && opsEvidenceServiceQualitySplitReceipt.readyForNodeV219ImplementationPrecheck()
            && !opsEvidenceServiceQualitySplitReceipt.apiShapeChanged()
            && !opsEvidenceServiceQualitySplitReceipt.approvalDecisionCreated()
            && !opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten()
            && !opsEvidenceServiceQualitySplitReceipt.approvalRecordPersisted()
            && !opsEvidenceServiceQualitySplitReceipt.managedAuditStoreWritten()
            && !opsEvidenceServiceQualitySplitReceipt.sqlExecuted()
            && !opsEvidenceServiceQualitySplitReceipt.deploymentTriggered()
            && !opsEvidenceServiceQualitySplitReceipt.rollbackTriggered()
            && !opsEvidenceServiceQualitySplitReceipt.restoreExecuted()
            && !opsEvidenceServiceQualitySplitReceipt.nodeMayTreatAsProductionAuditRecord();
    boolean nodeV220DisabledShellReady = true;
    boolean nodeV220SelectedAdapterDisabled = true;
    boolean nodeV220LocalDryRunOnlyDeclared = true;
    boolean nodeV220AppendWritten = false;
    boolean nodeV220QueryReturnedRecords = false;
    boolean nodeV220ExternalManagedAuditAccessed = false;
    boolean nodeV220LocalDryRunWritePerformed = false;
    boolean javaApprovalDecisionCreated = false;
    boolean javaApprovalLedgerWritten = false;
    boolean javaApprovalRecordPersisted = false;
    boolean javaManagedAuditStoreWritten = false;
    boolean javaSqlExecuted = false;
    boolean javaDeploymentTriggered = false;
    boolean javaRollbackTriggered = false;
    boolean javaRestoreExecuted = false;

    List<String> guardWarnings = new ArrayList<>();
    if (!sourceReceiptAccepted) {
      guardWarnings.add("NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY");
    }
    boolean readyForNodeV221LocalAdapterCandidateDryRun =
        sourceReceiptAccepted
            && nodeV220DisabledShellReady
            && nodeV220SelectedAdapterDisabled
            && nodeV220LocalDryRunOnlyDeclared
            && !nodeV220AppendWritten
            && !nodeV220QueryReturnedRecords
            && !nodeV220ExternalManagedAuditAccessed
            && !nodeV220LocalDryRunWritePerformed
            && !javaApprovalDecisionCreated
            && !javaApprovalLedgerWritten
            && !javaApprovalRecordPersisted
            && !javaManagedAuditStoreWritten
            && !javaSqlExecuted
            && !javaDeploymentTriggered
            && !javaRollbackTriggered
            && !javaRestoreExecuted;
    String guardDigest =
        ReleaseApprovalDigestSupport.digest(
            List.of(
                ReleaseApprovalDigestSupport.line("receiptVersion", RECEIPT_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "sourceQualitySplitReceiptVersion",
                    opsEvidenceServiceQualitySplitReceipt.receiptVersion()),
                ReleaseApprovalDigestSupport.line(
                    "sourceQualitySplitSchemaVersion", SOURCE_SCHEMA_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodeDisabledShellVersion", NODE_V220_VERSION),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodeDisabledShellProfile", NODE_V220_PROFILE),
                ReleaseApprovalDigestSupport.line(
                    "consumedByNodeDisabledShellState", "disabled-shell-ready"),
                ReleaseApprovalDigestSupport.line(
                    "nodeV220SelectedAdapterDisabled", nodeV220SelectedAdapterDisabled),
                ReleaseApprovalDigestSupport.line(
                    "nodeV220LocalDryRunOnlyDeclared", nodeV220LocalDryRunOnlyDeclared),
                ReleaseApprovalDigestSupport.line("nodeV220AppendWritten", nodeV220AppendWritten),
                ReleaseApprovalDigestSupport.line(
                    "nodeV220QueryReturnedRecords", nodeV220QueryReturnedRecords),
                ReleaseApprovalDigestSupport.line(
                    "nodeV220ExternalManagedAuditAccessed", nodeV220ExternalManagedAuditAccessed),
                ReleaseApprovalDigestSupport.line(
                    "nodeV220LocalDryRunWritePerformed", nodeV220LocalDryRunWritePerformed),
                ReleaseApprovalDigestSupport.line(
                    "javaApprovalLedgerWritten", javaApprovalLedgerWritten),
                ReleaseApprovalDigestSupport.line(
                    "javaManagedAuditStoreWritten", javaManagedAuditStoreWritten),
                ReleaseApprovalDigestSupport.line("javaSqlExecuted", javaSqlExecuted),
                ReleaseApprovalDigestSupport.line(
                    "readyForNodeV221LocalAdapterCandidateDryRun",
                    readyForNodeV221LocalAdapterCandidateDryRun)));

    return new ReleaseApprovalRehearsalResponseRecords
        .RehearsalManagedAuditAdapterImplementationGuardReceipt(
        RECEIPT_VERSION,
        opsEvidenceServiceQualitySplitReceipt.receiptVersion(),
        SOURCE_SCHEMA_VERSION,
        NODE_V220_VERSION,
        NODE_V220_PROFILE,
        NODE_V220_ENDPOINT,
        "disabled-shell-ready",
        NODE_V221_VERSION,
        NODE_V221_PROFILE,
        true,
        nodeV220DisabledShellReady,
        nodeV220SelectedAdapterDisabled,
        nodeV220LocalDryRunOnlyDeclared,
        nodeV220AppendWritten,
        nodeV220QueryReturnedRecords,
        nodeV220ExternalManagedAuditAccessed,
        nodeV220LocalDryRunWritePerformed,
        javaApprovalDecisionCreated,
        javaApprovalLedgerWritten,
        javaApprovalRecordPersisted,
        javaManagedAuditStoreWritten,
        javaSqlExecuted,
        javaDeploymentTriggered,
        javaRollbackTriggered,
        javaRestoreExecuted,
        readyForNodeV221LocalAdapterCandidateDryRun,
        false,
        false,
        false,
        guardDigest,
        List.of(
            "Node v220 profileVersion must equal managed-audit-adapter-disabled-shell.v1",
            "Node v220 shellState must equal disabled-shell-ready",
            "Node v220 selectedAdapterKind must stay disabled",
            "Node v220 acceptedCandidateKinds may declare local-dry-run but must not select it",
            "Node v220 appendWritten, localDryRunWritePerformed, and externalManagedAuditAccessed must stay false"),
        List.of(
            "Create approval decision during Java v80 implementation guard",
            "Write approval ledger during Java v80 implementation guard",
            "Persist production approval record during Java v80 implementation guard",
            "Write managed audit store during Java v80 implementation guard",
            "Execute SQL during Java v80 implementation guard",
            "Trigger deployment or rollback during Java v80 implementation guard",
            "Execute restore during Java v80 implementation guard",
            "Select local-dry-run adapter from Java v80 guard"),
        List.of(
            "Node v220 managed audit adapter disabled shell must be complete",
            "Java v80 managed audit adapter implementation guard receipt must be ready",
            "mini-kv v89 adapter shell non-storage guard receipt must be present before Node v221",
            "Node v221 may write only .tmp or explicit test-directory local dry-run records",
            "Node v221 must not connect production external managed audit",
            "UPSTREAM_ACTIONS_ENABLED must remain false"),
        List.copyOf(guardWarnings),
        List.of(
            "Compare managedAuditAdapterImplementationGuardReceipt.consumedByNodeDisabledShellProfile with Node v220",
            "Require managedAuditAdapterImplementationGuardReceipt.readyForNodeV221LocalAdapterCandidateDryRun=true before Node v221",
            "Compare managedAuditAdapterImplementationGuardReceipt.guardDigest before local adapter candidate dry-run",
            "Keep managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false",
            "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false",
            "Keep managedAuditAdapterImplementationGuardReceipt.nodeV220ExternalManagedAuditAccessed=false"));
  }
}
