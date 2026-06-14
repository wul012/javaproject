package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.ReleaseApprovalRehearsalResponse;
import java.util.List;

final
class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryCatalog {

  private
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
              .BoundaryGuard>
      guards(ReleaseApprovalRehearsalResponse rehearsal) {
    var boundary =
        rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt().javaExecutionBoundary();
    return List.of(
        guard(
            "carries-credential-value",
            "javaExecutionBoundary.carriesCredentialValue",
            boundary.carriesCredentialValue()),
        guard(
            "credential-value-read",
            "javaExecutionBoundary.credentialValueReadByJava",
            boundary.credentialValueReadByJava()),
        guard(
            "credential-value-stored",
            "javaExecutionBoundary.credentialValueStoredByJava",
            boundary.credentialValueStoredByJava()),
        guard(
            "actual-connection-attempted",
            "javaExecutionBoundary.actualConnectionAttemptedByJava",
            boundary.actualConnectionAttemptedByJava()),
        guard(
            "external-managed-audit-connection-opened",
            "javaExecutionBoundary.externalManagedAuditConnectionOpenedByJava",
            boundary.externalManagedAuditConnectionOpenedByJava()),
        guard(
            "schema-migration-requested",
            "javaExecutionBoundary.schemaMigrationRequestedByJava",
            boundary.schemaMigrationRequestedByJava()),
        guard(
            "schema-migration-sql-executed",
            "javaExecutionBoundary.schemaMigrationSqlExecutedByJava",
            boundary.schemaMigrationSqlExecutedByJava()),
        guard(
            "approval-ledger-written",
            "javaExecutionBoundary.approvalLedgerWrittenByJava",
            boundary.approvalLedgerWrittenByJava()),
        guard(
            "managed-audit-state-write-requested",
            "javaExecutionBoundary.managedAuditStateWriteRequestedByJava",
            boundary.managedAuditStateWriteRequestedByJava()),
        guard(
            "managed-audit-store-written",
            "javaExecutionBoundary.managedAuditStoreWrittenByJava",
            boundary.managedAuditStoreWrittenByJava()),
        guard(
            "sql-executed",
            "javaExecutionBoundary.sqlExecutedByJava",
            boundary.sqlExecutedByJava()),
        guard(
            "deployment-triggered",
            "javaExecutionBoundary.deploymentTriggeredByJava",
            boundary.deploymentTriggeredByJava()),
        guard(
            "rollback-triggered",
            "javaExecutionBoundary.rollbackTriggeredByJava",
            boundary.rollbackTriggeredByJava()),
        guard(
            "restore-executed",
            "javaExecutionBoundary.restoreExecutedByJava",
            boundary.restoreExecutedByJava()),
        guard(
            "upstream-service-auto-start-requested",
            "javaExecutionBoundary.upstreamServiceAutoStartRequestedByJava",
            boundary.upstreamServiceAutoStartRequestedByJava()),
        guard(
            "mini-kv-write-permission-requested",
            "javaExecutionBoundary.miniKvWritePermissionRequestedByJava",
            boundary.miniKvWritePermissionRequestedByJava()),
        guard(
            "production-window-opened",
            "javaExecutionBoundary.productionWindowOpenedByJava",
            boundary.productionWindowOpenedByJava()));
  }

  private static
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
          .BoundaryGuard
      guard(String name, String evidence, boolean actualValue) {
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
        .BoundaryGuard(name, evidence, false, actualValue, !actualValue);
  }
}
