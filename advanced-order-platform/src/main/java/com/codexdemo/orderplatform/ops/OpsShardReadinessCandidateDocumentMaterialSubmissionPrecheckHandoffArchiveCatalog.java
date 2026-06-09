package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchiveCatalog {

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffArchiveCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArchiveHandle> archiveHandles(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck
    ) {
        return sourcePrecheck.checkpoints().stream()
                .map(checkpoint -> new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArchiveHandle(
                        "archive-" + checkpoint.code(),
                        checkpoint.code(),
                        "e/1187/archive/" + checkpoint.code() + ".json",
                        "read-only candidate material submission precheck archive",
                        "passed"))
                .toList();
    }
}
