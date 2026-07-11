package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import java.util.List;

final class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSourceCatalog {

  private OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSourceCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt>
      receipts(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    return List.of(
        new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.SourceReceipt(
            "managedAuditSandboxConnectionPreconditionReceipt",
            receipt.receiptVersion(),
            receipt.receiptDigest(),
            receipt.consumedByNodeBlockedExecutionRehearsalVersion(),
            receipt.consumedByNodeBlockedExecutionRehearsalProfile(),
            receipt.consumedByNodeBlockedExecutionRehearsalState(),
            receipt.nextNodePreconditionIntakeVersion(),
            receipt.nextNodePreconditionIntakeProfile(),
            receipt.nodeV235MayConsume(),
            receipt.readyForNodeV235ManualSandboxConnectionPreconditionIntake(),
            receipt.readyForManagedAuditSandboxAdapterConnection(),
            receipt.nodeMayTreatAsProductionAuditRecord()));
  }
}
