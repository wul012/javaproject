package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffSourceCatalog {

    private OpsShardReadinessCandidateDocumentHandoffSourceCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentHandoffResponse.SourceLineage> sourceLineage(
            OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage
    ) {
        return List.of(
                lineage("node-request-plan", OpsShardReadinessCandidateDocumentHandoffSupport.SOURCE_PLAN,
                        "D:/nodeproj/orderops-node/docs/plans3/v1386-controlled-read-only-shard-preview-candidate-document-request-package-closeout-roadmap.md",
                        "defines request package counts and stop condition"),
                lineage("node-candidate-intake", sourcePackage.sourceNodeCandidateIntakeVersion(),
                        "controlled read-only shard preview compared evidence candidate intake preflight",
                        "freezes the original fifteen request item inputs"),
                lineage("java-candidate-intake", sourcePackage.sourceJavaCandidateIntakeVersion(),
                        OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
                                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_CANDIDATE_INTAKE_PREFLIGHT_CATALOG,
                        "preserves Java-side intake slots and guards"),
                lineage("java-request-package", sourcePackage.version(), sourcePackage.endpoint(),
                        "converts intake slots into request items and acceptance checks"),
                lineage("java-request-package-profile", sourcePackage.profile(), sourcePackage.endpoint(),
                        "pins the response contract consumed by this handoff"),
                lineage("future-real-document-intake", "blocked", "not-opened",
                        "keeps real document intake closed until a reviewed artifact exists")
        );
    }

    private static OpsShardReadinessCandidateDocumentHandoffResponse.SourceLineage lineage(
            String code,
            String version,
            String source,
            String role
    ) {
        return new OpsShardReadinessCandidateDocumentHandoffResponse.SourceLineage(
                code,
                version,
                source,
                source.startsWith("/") ? source : "",
                role,
                "passed"
        );
    }
}
