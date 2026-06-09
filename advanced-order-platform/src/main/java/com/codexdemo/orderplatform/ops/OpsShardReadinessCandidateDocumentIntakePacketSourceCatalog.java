package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentIntakePacketSourceCatalog {

    private OpsShardReadinessCandidateDocumentIntakePacketSourceCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage> sourceLineage(
            OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse sourcePrecheck
    ) {
        return List.of(
                source("node-intake-packet-plan", "Node v1421",
                        "D:/nodeproj/orderops-node/docs/plans3/v1421-controlled-read-only-shard-preview-candidate-document-intake-packet-closeout-roadmap.md",
                        "defines ten intake slots, ten guards, and no-material stop condition"),
                source("node-submission-precheck", "Node v1411",
                        "controlled read-only shard preview candidate document submission precheck",
                        "freezes the source checkpoint and validator counts"),
                source("java-submission-precheck", sourcePrecheck.version(), sourcePrecheck.endpoint(),
                        "provides twenty-five checkpoints and validators for slot grouping"),
                source("java-submission-precheck-profile", sourcePrecheck.profile(), sourcePrecheck.endpoint(),
                        "pins the route response consumed by this intake packet"),
                source("future-reviewed-real-material", "blocked", "not-supplied",
                        "keeps actual reviewed material intake out of scope")
        );
    }

    private static OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage source(
            String code,
            String version,
            String source,
            String role
    ) {
        return new OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage(
                code,
                version,
                source,
                role,
                "passed"
        );
    }
}
