package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalTestSupport;
import org.junit.jupiter.api.Test;

class ReleaseSandboxChecklistTests extends ReleaseApprovalRehearsalTestSupport {

  @Test
  void buildsChecklistOverviewForDefaultRequest() {
    OpsEvidenceService service = readOnlyFixtureService();

    ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary()
                .managedAuditStoreWrittenByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .allEnvelopeFieldsEchoed())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialValueExcluded())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForNodeV237ManualSandboxConnectionReadinessGate())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForManagedAuditSandboxAdapterConnection())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .readyForProductionAudit())
        .isFalse();
    assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptDigest())
        .startsWith("sha256:");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .echoedEnvelopeFieldNames())
        .containsExactly(
            "ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
            "ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
            "ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
            "ORDEROPS_MANAGED_AUDIT_ROLLBACK_PATH_ID",
            "timeoutBudgetMs",
            "ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .forbiddenEnvelopeOperations())
        .contains(
            "Include a managed audit credential value in the Java v92 dry-run envelope echo",
            "Open a managed audit sandbox connection during Java v92 dry-run envelope echo",
            "Write approval ledger or managed audit state during Java v92 dry-run envelope echo");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .nodeV237Prerequisites())
        .contains(
            "Node v236 manual sandbox connection dry-run request envelope must be archived",
            "Java v92 sandbox connection dry-run envelope echo receipt must be present",
            "mini-kv v101 no-start / no-write evidence follow-up must be present");
    assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptWarnings())
        .containsExactly("NODE_V237_SOURCE_SANDBOX_CONNECTION_PRECONDITION_RECEIPT_NOT_READY");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .nodeVerificationActions())
        .contains(
            "Compare managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.consumedByNodeDryRunRequestEnvelopeProfile with Node v236",
            "Require managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.readyForNodeV237ManualSandboxConnectionReadinessGate=true before Node v237",
            "Keep managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.credentialBoundary.credentialValueIncludedInEnvelope=false",
            "Keep managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .receiptVersion())
        .isEqualTo(
            "java-release-approval-rehearsal-managed-audit-sandbox-connection-operator-window-checklist-echo-receipt.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .sourceSandboxConnectionDryRunEnvelopeEchoReceiptSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v20");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .consumedByNodeOperatorWindowChecklistVersion())
        .isEqualTo("Node v238");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .consumedByNodeOperatorWindowChecklistProfile())
        .isEqualTo("managed-audit-manual-sandbox-connection-operator-window-checklist.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .nextNodeEvidenceVerificationVersion())
        .isEqualTo("Node v239");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary()
                .requiredApprovalCount())
        .isEqualTo(3);
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary()
                .checklistStepCount())
        .isEqualTo(8);
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary()
                .pauseConditionCount())
        .isEqualTo(8);
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary()
                .forbiddenOperationCount())
        .isEqualTo(6);
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary()
                .operatorChecklistReadOnly())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .checklistFieldBoundary()
                .checklistCreatesConnectionCommand())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalBoundary()
                .approvalItemCount())
        .isEqualTo(3);
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalBoundary()
                .approvalLedgerWrittenByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary()
                .credentialHandleOnly())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary()
                .credentialValueIncludedInChecklist())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialBoundary()
                .credentialValueReadByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary()
                .actualConnectionAttemptedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary()
                .schemaMigrationSqlExecutedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .javaExecutionBoundary()
                .approvalLedgerWrittenByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .allChecklistFieldsEchoed())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .approvalChecklistEchoComplete())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .credentialValueExcluded())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .readyForNodeV239ManualSandboxConnectionEvidenceVerification())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .readyForManagedAuditSandboxAdapterConnection())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .receiptDigest())
        .startsWith("sha256:");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .echoedApprovalItemIds())
        .containsExactly("release-owner", "security-reviewer", "operations-owner");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .echoedChecklistStepPhases())
        .contains("source-readiness-gate", "credential-handle", "final-stop-gate");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .echoedPauseConditionCodes())
        .contains(
            "SOURCE_GATE_NOT_READY", "CREDENTIAL_VALUE_REQUESTED", "UPSTREAM_ACTIONS_ENABLED");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .forbiddenChecklistOperations())
        .contains(
            "Open a managed audit sandbox connection during Java v93 operator checklist echo",
            "Write approval ledger or managed audit state during Java v93 operator checklist echo");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .nodeV239Prerequisites())
        .contains(
            "Node v238 manual sandbox connection operator window checklist must be archived",
            "Java v93 sandbox connection operator window checklist echo receipt must be present",
            "mini-kv v102 operator window no-start / no-write receipt must be present");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .receiptWarnings())
        .containsExactly(
            "NODE_V239_SOURCE_SANDBOX_CONNECTION_DRY_RUN_ENVELOPE_ECHO_RECEIPT_NOT_READY");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt()
                .nodeVerificationActions())
        .contains(
            "Compare managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.consumedByNodeOperatorWindowChecklistProfile with Node v238",
            "Require managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.readyForNodeV239ManualSandboxConnectionEvidenceVerification=true before Node v239",
            "Keep managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.credentialBoundary.credentialValueIncludedInChecklist=false",
            "Keep managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt.javaExecutionBoundary.approvalLedgerWrittenByJava=false");
  }
}
