package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalSandboxConnectionResponseRecords.RehearsalManagedAuditSandboxConnectionPreconditionReceipt;
import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog {

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.BoundarySnapshot>
      boundaries(RehearsalManagedAuditSandboxConnectionPreconditionReceipt receipt) {
    return List.of(
        boundary(
            "owner-approval",
            receipt.ownerApprovalBoundary().ownerApprovalArtifactIdField(),
            receipt.ownerApprovalBoundary().ownerApprovalArtifactRequired(),
            !receipt.ownerApprovalBoundary().ownerApprovalArtifactProvidedByJava()
                && !receipt.ownerApprovalBoundary().javaApprovalLedgerWritten()),
        boundary(
            "credential-handle",
            receipt.credentialBoundary().credentialHandleNameField(),
            receipt.credentialBoundary().credentialHandleReviewRequired(),
            !receipt.credentialBoundary().credentialValueReadByJava()
                && !receipt.credentialBoundary().credentialValueStoredByJava()),
        boundary(
            "schema-rehearsal",
            receipt.schemaRehearsalBoundary().schemaRehearsalIdField(),
            receipt.schemaRehearsalBoundary().schemaRehearsalEvidenceRequired(),
            !receipt.schemaRehearsalBoundary().schemaMigrationSqlExecutedByJava()
                && !receipt.schemaRehearsalBoundary().schemaMigrationAppliedByJava()),
        boundary(
            "rollback-path",
            receipt.rollbackPathBoundary().rollbackPathIdField(),
            receipt.rollbackPathBoundary().rollbackPathRequired()
                && receipt.rollbackPathBoundary().manualAbortMarkerRequired(),
            !receipt.rollbackPathBoundary().rollbackExecutionAllowedByJava()
                && !receipt.rollbackPathBoundary().restoreExecutionAllowedByJava()),
        boundary(
            "java-execution",
            "actualConnectionAttemptedByJava=false",
            true,
            !receipt.javaExecutionBoundary().actualConnectionAttemptedByJava()
                && !receipt.javaExecutionBoundary().javaStartsManagedAuditService()));
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .BoundarySnapshot
      boundary(String name, String evidence, boolean required, boolean closed) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
        .BoundarySnapshot(name, evidence, required, closed);
  }
}
