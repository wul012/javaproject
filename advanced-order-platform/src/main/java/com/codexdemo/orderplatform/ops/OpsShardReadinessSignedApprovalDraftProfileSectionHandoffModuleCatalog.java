package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffModuleCatalog {

    private OpsShardReadinessSignedApprovalDraftProfileSectionHandoffModuleCatalog() {
    }

    static List<OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry> modules() {
        return List.of(
                module(240, "signed-approval-draft-profile-section-handoff-types",
                        "defines the signed approval draft profile section handoff records"),
                module(241, "signed-approval-draft-profile-section-handoff-source-catalog",
                        "pins the source registry version, endpoint, and profile"),
                module(242, "signed-approval-draft-profile-section-handoff-section-catalog",
                        "maps registry sections into consumer-facing handoff entries"),
                module(243, "signed-approval-draft-profile-section-handoff-route-contract-catalog",
                        "transfers endpoint, profile, Java version, and Node marker route contracts"),
                module(244, "signed-approval-draft-profile-section-handoff-boundary-decisions",
                        "keeps artifact, approval, import, runtime, and write boundaries closed"),
                module(245, "signed-approval-draft-profile-section-handoff-renderer",
                        "renders stable Markdown handoff blocks for downstream readers"),
                module(246, "signed-approval-draft-profile-section-handoff-gates",
                        "publishes no-runtime gates for the handoff boundary"),
                module(247, "signed-approval-draft-profile-section-handoff-route",
                        "exposes the handoff without adding mutable behavior")
        );
    }

    private static OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry module(
            int order,
            String code,
            String responsibility
    ) {
        return new OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry(
                order,
                code,
                responsibility,
                "passed"
        );
    }
}
