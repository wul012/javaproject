package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.ReleaseApprovalRehearsalResponse;
import java.util.List;

final
class OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierDownstreamIntakeCatalog {

  private
  OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierDownstreamIntakeCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
              .DownstreamIntakeGate>
      gates(ReleaseApprovalRehearsalResponse rehearsal) {
    var receipt = rehearsal.managedAuditSandboxConnectionPreconditionReceipt();
    return List.of(
        gate(
            "node-v234-blocked-execution-rehearsal",
            receipt.consumedByNodeBlockedExecutionRehearsalState(),
            "Node v234".equals(receipt.consumedByNodeBlockedExecutionRehearsalVersion())),
        gate(
            "java-v90-context-normalization",
            OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport
                .JAVA_CONTEXT_EVIDENCE_VERSION,
            rehearsal.requestContext().contextVersion().endsWith(".v1")),
        gate(
            "java-v91-precondition-receipt",
            receipt.receiptVersion(),
            receipt.nodeV235MayConsume()),
        gate(
            "mini-kv-v99-wal-regression-evidence",
            "frozen sibling evidence only; Java does not start mini-kv",
            true),
        gate(
            "upstream-actions-disabled",
            "UPSTREAM_ACTIONS_ENABLED must remain false",
            receipt
                .nodeV235Prerequisites()
                .contains("UPSTREAM_ACTIONS_ENABLED must remain false")));
  }

  private static OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
          .DownstreamIntakeGate
      gate(String name, String evidence, boolean ready) {
    return new OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse
        .DownstreamIntakeGate(name, evidence, ready);
  }
}
