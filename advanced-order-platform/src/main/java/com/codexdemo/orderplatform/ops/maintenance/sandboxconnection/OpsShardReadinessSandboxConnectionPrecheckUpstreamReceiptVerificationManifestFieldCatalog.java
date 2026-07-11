package com.codexdemo.orderplatform.ops.maintenance.sandboxconnection;

import com.codexdemo.orderplatform.ops.maintenance.releaseapproval.ReleaseApprovalRehearsalResponse;
import java.util.List;

final
class OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestFieldCatalog {

  private
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestFieldCatalog() {}

  static List<
          OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
              .PrecheckField>
      fields(ReleaseApprovalRehearsalResponse rehearsal) {
    var fieldEcho = rehearsal.managedAuditSandboxConnectionPrecheckPacketEchoReceipt().fieldEcho();
    return List.of(
        field(
            fieldEcho.ownerApprovalArtifactItemId(),
            fieldEcho.ownerApprovalArtifactField(),
            "owner approval artifact id",
            fieldEcho.ownerApprovalArtifactEchoed()),
        field(
            fieldEcho.credentialHandleReviewItemId(),
            fieldEcho.credentialHandleReviewField(),
            "credential handle review",
            fieldEcho.credentialHandleReviewEchoed()),
        field(
            fieldEcho.schemaMigrationRehearsalItemId(),
            fieldEcho.schemaMigrationRehearsalIdField(),
            "schema rehearsal evidence id",
            fieldEcho.schemaMigrationRehearsalEchoed()),
        field(
            fieldEcho.operatorWindowItemId(),
            fieldEcho.operatorWindowField(),
            "manual operator window marker",
            fieldEcho.operatorWindowEchoed()),
        field(
            fieldEcho.rollbackPathItemId(),
            fieldEcho.rollbackPathField(),
            "rollback evidence path",
            fieldEcho.rollbackPathEchoed()),
        field(
            fieldEcho.abortMarkerItemId(),
            fieldEcho.abortMarkerField(),
            "manual abort marker",
            fieldEcho.abortMarkerEchoed()),
        field(
            fieldEcho.timeoutPolicyItemId(),
            fieldEcho.timeoutPolicyField(),
            String.valueOf(fieldEcho.timeoutBudgetMs()),
            fieldEcho.timeoutPolicyEchoed()));
  }

  private static
  OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
          .PrecheckField
      field(String id, String fieldName, String value, boolean echoed) {
    return new OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse
        .PrecheckField(id, fieldName, value, echoed, false);
  }
}
