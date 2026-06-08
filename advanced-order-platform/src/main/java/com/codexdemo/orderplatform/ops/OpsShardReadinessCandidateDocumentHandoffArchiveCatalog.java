package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffArchiveCatalog {

    private OpsShardReadinessCandidateDocumentHandoffArchiveCatalog() {
    }

    static List<OpsShardReadinessCandidateDocumentHandoffResponse.ArchiveEntry> archiveEntries() {
        return List.of(
                entry("source-plan", "e/1107/source/node-v1386-request-package-plan.md",
                        "retained-with-version-tag", "records the Node plan consumed by this Java handoff"),
                entry("source-request-package", "e/1107/source/java-v1081-request-package.json",
                        "retained-with-version-tag", "pins the Java request package response consumed by this handoff"),
                entry("source-lineage", "e/1107/lineage/candidate-document-handoff-source-lineage.json",
                        "retained-with-version-tag", "lists upstream plan, intake, and request package sources"),
                entry("artifact-handles", "e/1107/artifacts/candidate-document-handoff-artifact-handles.json",
                        "retained-with-version-tag", "lists evidence, digest, and archive handles for each request item"),
                entry("policy-locks", "e/1107/policy/candidate-document-handoff-policy-locks.json",
                        "retained-with-version-tag", "lists fail-closed acceptance locks"),
                entry("consumer-rules", "e/1107/handoff/candidate-document-handoff-consumer-rules.json",
                        "retained-with-version-tag", "states consumer read rules and forbidden transitions"),
                entry("route-evidence", "e/1107/routes/candidate-document-request-package-handoff-route.json",
                        "retained-with-version-tag", "captures read-only route and endpoint profile"),
                entry("closeout", "e/1107/closeout/candidate-document-handoff-closeout.md",
                        "retained-with-version-tag", "summarizes the handoff stop condition")
        );
    }

    private static OpsShardReadinessCandidateDocumentHandoffResponse.ArchiveEntry entry(
            String code,
            String path,
            String retention,
            String purpose
    ) {
        return new OpsShardReadinessCandidateDocumentHandoffResponse.ArchiveEntry(
                code,
                path,
                retention,
                purpose,
                "passed"
        );
    }
}
