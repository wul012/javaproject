package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import java.util.List;

final
class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceCatalog {

  private
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
              .SourceReceipt>
      receipts(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
    return List.of(
        new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
            .SourceReceipt(
            "managedAuditSandboxConnectionPrecheckPacketEchoReceipt",
            receipt.receiptVersion(),
            receipt.receiptDigest(),
            receipt.consumedByNodePrecheckPacketVersion(),
            receipt.consumedByNodePrecheckPacketProfile(),
            receipt.consumedByNodePrecheckPacketEndpoint(),
            receipt.consumedByNodePrecheckPacketState(),
            receipt.nextNodePrecheckUpstreamReceiptVerificationVersion(),
            receipt.nextNodePrecheckUpstreamReceiptVerificationProfile(),
            receipt.nodeV246MayConsume(),
            receipt.readyForNodeV246ManualSandboxConnectionPrecheckUpstreamReceiptVerification(),
            receipt.readyForManagedAuditSandboxAdapterConnection(),
            receipt.readyForProductionAudit(),
            receipt.nodeMayTreatAsProductionAuditRecord(),
            List.copyOf(receipt.receiptWarnings()),
            List.copyOf(receipt.nodeVerificationActions())));
  }
}
