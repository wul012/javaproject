package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt;
import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierExecutionGuardCatalog {

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierExecutionGuardCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.ExecutionGuard>
      guards(RehearsalManagedAuditSandboxConnectionPreconditionReceipt receipt) {
    var execution = receipt.javaExecutionBoundary();
    return List.of(
        guard(
            "owner-approval-artifact-provided",
            "ownerApprovalArtifactProvidedByJava=false",
            !receipt.ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()),
        guard(
            "credential-value-read",
            "credentialValueReadByJava=false",
            !receipt.credentialBoundary().credentialValueReadByJava()),
        guard(
            "schema-migration-sql",
            "schemaMigrationSqlExecutedByJava=false",
            !receipt.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()),
        guard(
            "rollback-execution",
            "rollbackExecutionAllowedByJava=false",
            !receipt.rollbackPathBoundary().rollbackExecutionAllowedByJava()),
        guard(
            "restore-execution",
            "restoreExecutionAllowedByJava=false",
            !receipt.rollbackPathBoundary().restoreExecutionAllowedByJava()),
        guard(
            "approval-ledger-write",
            "approvalLedgerWrittenByJava=false",
            !execution.approvalLedgerWrittenByJava()),
        guard(
            "managed-audit-store-write",
            "managedAuditStoreWrittenByJava=false",
            !execution.managedAuditStoreWrittenByJava()),
        guard(
            "external-managed-audit-connection",
            "externalManagedAuditConnectionOpenedByJava=false",
            !execution.externalManagedAuditConnectionOpenedByJava()),
        guard("sql-execution", "sqlExecutedByJava=false", !execution.sqlExecutedByJava()),
        guard(
            "deployment-trigger",
            "deploymentTriggeredByJava=false",
            !execution.deploymentTriggeredByJava()),
        guard(
            "managed-audit-service-start",
            "javaStartsManagedAuditService=false",
            !execution.javaStartsManagedAuditService()),
        guard(
            "actual-connection-attempt",
            "actualConnectionAttemptedByJava=false",
            !execution.actualConnectionAttemptedByJava()));
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .ExecutionGuard
      guard(String name, String evidence, boolean passed) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
        .ExecutionGuard(name, evidence, passed);
  }
}
