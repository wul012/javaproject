package com.codexdemo.orderplatform.ops;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport {

    private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport() {
    }

    static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService service() {
        return new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService(
                OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.service());
    }

    static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse handoff() {
        return service().handoff();
    }
}
