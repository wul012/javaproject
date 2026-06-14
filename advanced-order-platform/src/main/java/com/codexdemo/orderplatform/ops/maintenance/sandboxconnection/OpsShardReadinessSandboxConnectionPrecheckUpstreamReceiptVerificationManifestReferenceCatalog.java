package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.ReleaseApprovalRehearsalResponse;
import java.util.List;

final
class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestReferenceCatalog {

  private
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestReferenceCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
              .EvidenceReference>
      references(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt();
    return List.of(
        reference(
            "node-v1983-v2002-roadmap",
            "Node plan",
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport
                .NODE_OWNER_PLAN,
            "precheck-upstream-receipt-verification split roadmap",
            "Defines the Node-only module split and verification stop condition."),
        reference(
            "node-v245-precheck-packet",
            "Node receipt",
            receipt.consumedByNodePrecheckPacketVersion(),
            receipt.consumedByNodePrecheckPacketProfile(),
            "Frozen source packet shape consumed by the Java v99 echo receipt."),
        reference(
            "java-v99-precheck-packet-echo",
            "Java receipt",
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport
                .FROZEN_JAVA_EVIDENCE_VERSION,
            receipt.receiptVersion(),
            "Frozen Java evidence used by Node v247 and retained for Node v1983-v2002."),
        reference(
            "mini-kv-v108-non-participation",
            "mini-kv receipt",
            OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport
                .FROZEN_MINI_KV_EVIDENCE_VERSION,
            "mini-kv-non-participation-reference.v1",
            "Sibling evidence only; Java must not request mini-kv writes or startup."),
        reference(
            "node-v247-verification-report",
            "Node report",
            "Node v247",
            receipt.nextNodePrecheckUpstreamReceiptVerificationProfile(),
            "Existing report reused by the split module family."));
  }

  private static
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
          .EvidenceReference
      reference(String id, String source, String version, String profile, String role) {
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
        .EvidenceReference(id, source, version, profile, role, true, true);
  }
}
