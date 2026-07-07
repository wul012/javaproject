package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport;

public final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport {

  private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport() {}

  public static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService service() {
    return new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService(
        OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.service());
  }

  public static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse handoff() {
    return service().handoff();
  }
}
