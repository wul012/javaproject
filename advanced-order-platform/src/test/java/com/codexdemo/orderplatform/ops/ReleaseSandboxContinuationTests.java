package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalTestSupport;
import org.junit.jupiter.api.Test;

class ReleaseSandboxContinuationTests extends ReleaseApprovalRehearsalTestSupport {

  @Test
  void buildsPreconditionOverviewForDefaultRequest() {
    OpsEvidenceService service = readOnlyFixtureService();

    ReleaseApprovalRehearsalResponse rehearsal = service.releaseApprovalRehearsal();
    assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptVersion())
        .isEqualTo(
            "java-release-approval-rehearsal-managed-audit-sandbox-connection-precondition-receipt.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .sourceSandboxConnectionPreflightEchoMarkerVersion())
        .isEqualTo(
            "java-release-approval-rehearsal-managed-audit-sandbox-connection-preflight-echo-marker.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .sourceSandboxConnectionPreflightEchoMarkerSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v18");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .consumedByNodeBlockedExecutionRehearsalVersion())
        .isEqualTo("Node v234");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .consumedByNodeBlockedExecutionRehearsalProfile())
        .isEqualTo("managed-audit-manual-sandbox-connection-blocked-execution-rehearsal.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .nextNodePreconditionIntakeVersion())
        .isEqualTo("Node v235");
    assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().nodeV235MayConsume())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary()
                .ownerApprovalArtifactIdField())
        .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary()
                .ownerApprovalArtifactRequired())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .ownerApprovalBoundary()
                .ownerApprovalArtifactProvidedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .credentialBoundary()
                .credentialHandleReviewRequired())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .credentialBoundary()
                .credentialValueReadByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .schemaRehearsalBoundary()
                .schemaMigrationSqlExecutedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .rollbackPathBoundary()
                .timeoutBudgetMs())
        .isEqualTo(15000);
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .rollbackPathBoundary()
                .manualAbortMarkerRequired())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .javaExecutionBoundary()
                .externalManagedAuditConnectionOpenedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .javaExecutionBoundary()
                .actualConnectionAttemptedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .allPreconditionsDocumented())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .readyForNodeV235ManualSandboxConnectionPreconditionIntake())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .readyForManagedAuditSandboxAdapterConnection())
        .isFalse();
    assertThat(
            rehearsal.managedAuditSandboxConnectionPreconditionReceipt().readyForProductionAudit())
        .isFalse();
    assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptDigest())
        .startsWith("sha256:");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .requiredPreconditionEvidence())
        .contains(
            "owner approval artifact id field: ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID",
            "credential handle review field: ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE",
            "schema rehearsal evidence field: ORDEROPS_MANAGED_AUDIT_SCHEMA_REHEARSAL_ID",
            "timeout budget: 15000ms",
            "manual abort marker field: ORDEROPS_MANAGED_AUDIT_MANUAL_ABORT");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionPreconditionReceipt()
                .forbiddenPreconditionOperations())
        .contains(
            "Open a managed audit sandbox connection during Java v91 precondition receipt",
            "Read or print a managed audit credential value during Java v91 precondition receipt",
            "Execute schema migration SQL during Java v91 precondition receipt");
    assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().nodeV235Prerequisites())
        .contains(
            "Node v234 blocked execution rehearsal must be archived",
            "Java v91 sandbox connection precondition receipt must be present",
            "mini-kv v100 current runtime fixture rolling evidence guard must be present");
    assertThat(rehearsal.managedAuditSandboxConnectionPreconditionReceipt().receiptWarnings())
        .containsExactly("NODE_V235_SOURCE_SANDBOX_CONNECTION_PREFLIGHT_ECHO_MARKER_NOT_READY");
    assertThat(
            rehearsal.managedAuditSandboxConnectionPreconditionReceipt().nodeVerificationActions())
        .contains(
            "Compare managedAuditSandboxConnectionPreconditionReceipt.consumedByNodeBlockedExecutionRehearsalProfile with Node v234",
            "Require managedAuditSandboxConnectionPreconditionReceipt.readyForNodeV235ManualSandboxConnectionPreconditionIntake=true before Node v235",
            "Keep managedAuditSandboxConnectionPreconditionReceipt.readyForManagedAuditSandboxAdapterConnection=false",
            "Keep managedAuditSandboxConnectionPreconditionReceipt.javaExecutionBoundary.actualConnectionAttemptedByJava=false");
    assertThat(rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().receiptVersion())
        .isEqualTo(
            "java-release-approval-rehearsal-managed-audit-sandbox-connection-dry-run-envelope-echo-receipt.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .sourceSandboxConnectionPreconditionReceiptSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v19");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeVersion())
        .isEqualTo("Node v236");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeProfile())
        .isEqualTo("managed-audit-manual-sandbox-connection-dry-run-request-envelope.v1");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .consumedByNodeDryRunRequestEnvelopeEndpoint())
        .isEqualTo(
            "/api/v1/audit/managed-audit-manual-sandbox-connection-dry-run-request-envelope");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .nextNodeReadinessGateVersion())
        .isEqualTo("Node v237");
    assertThat(
            rehearsal.managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt().nodeV237MayConsume())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary()
                .ownerApprovalArtifactIdField())
        .isEqualTo("ORDEROPS_MANAGED_AUDIT_OWNER_APPROVAL_ARTIFACT_ID");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary()
                .credentialHandleNameField())
        .isEqualTo("ORDEROPS_MANAGED_AUDIT_SANDBOX_CREDENTIAL_HANDLE");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary()
                .timeoutBudgetField())
        .isEqualTo("timeoutBudgetMs");
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary()
                .operatorReviewFieldsComplete())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary()
                .dryRunEnvelopeReadOnly())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .envelopeFieldBoundary()
                .envelopeCreatesConnectionCommand())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary()
                .credentialHandleOnly())
        .isTrue();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary()
                .credentialValueIncludedInEnvelope())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .credentialBoundary()
                .credentialValueReadByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary()
                .actualConnectionAttemptedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary()
                .schemaMigrationSqlExecutedByJava())
        .isFalse();
    assertThat(
            rehearsal
                .managedAuditSandboxConnectionDryRunEnvelopeEchoReceipt()
                .javaExecutionBoundary()
                .approvalLedgerWrittenByJava())
        .isFalse();
  }
}
