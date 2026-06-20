package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport() {}

  static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService service() {
    return new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService(
        OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.service());
  }

  static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse handoff() {
    return service().handoff();
  }
}
