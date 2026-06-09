package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffModuleCatalog {

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffModuleCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry> modules() {
        return List.of(
                module(214, "material-submission-precheck-handoff-types",
                        "defines archive handoff records"),
                module(215, "material-submission-precheck-handoff-source",
                        "pins the source Java v1162 precheck lineage"),
                module(216, "material-submission-precheck-handoff-archive-policy",
                        "maps checkpoints and validators to archive handles and locks"),
                module(217, "material-submission-precheck-handoff-consumer",
                        "declares read-only consumer rules"),
                module(218, "material-submission-precheck-handoff-route",
                        "exposes the handoff route without accepting material")
        );
    }

    private static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry module(
            int order,
            String code,
            String responsibility
    ) {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry(
                order,
                code,
                responsibility,
                "passed"
        );
    }
}
