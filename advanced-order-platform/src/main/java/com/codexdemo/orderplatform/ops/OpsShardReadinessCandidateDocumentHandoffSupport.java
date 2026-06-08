package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentHandoffSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1386";
    static final String SOURCE_NODE_CANDIDATE_INTAKE_VERSION = "Node v1371";
    static final String SOURCE_JAVA_CANDIDATE_INTAKE_VERSION = "Java v1079";
    static final String HANDOFF_STATE = "waiting-for-reviewed-real-compared-package-evidence-candidate-document";
    static final int EXPECTED_SOURCE_LINEAGE_COUNT = 6;
    static final int EXPECTED_MODULE_COUNT = 5;
    static final int EXPECTED_ARTIFACT_HANDLE_COUNT = 15;
    static final int EXPECTED_POLICY_LOCK_COUNT = 15;
    static final int EXPECTED_ARCHIVE_ENTRY_COUNT = 8;
    static final int EXPECTED_CONSUMER_RULE_COUNT = 10;
    static final int EXPECTED_GATE_COUNT = 25;

    private OpsShardReadinessCandidateDocumentHandoffSupport() {
    }

    static OpsShardReadinessCandidateDocumentHandoffResponse response(
            String version,
            String endpoint,
            String profile,
            OpsShardReadinessCandidateDocumentRequestPackageResponse sourcePackage,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.SourceLineage> sourceLineage,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ArtifactHandle> artifactHandles,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.PolicyLock> policyLocks,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ArchiveEntry> archiveEntries,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ConsumerRule> consumerRules,
            List<String> gates,
            List<String> additionalChecks
    ) {
        var lineageCopy = List.copyOf(sourceLineage);
        var moduleCopy = List.copyOf(modules);
        var artifactCopy = List.copyOf(artifactHandles);
        var policyCopy = List.copyOf(policyLocks);
        var archiveCopy = List.copyOf(archiveEntries);
        var consumerCopy = List.copyOf(consumerRules);
        var gateCopy = List.copyOf(gates);
        List<String> checks = new ArrayList<>();
        checks.add("candidate-document-handoff-source-plan-" + SOURCE_PLAN);
        checks.add("candidate-document-handoff-source-node-" + SOURCE_NODE_CANDIDATE_INTAKE_VERSION);
        checks.add("candidate-document-handoff-source-java-" + SOURCE_JAVA_CANDIDATE_INTAKE_VERSION);
        checks.add("candidate-document-handoff-source-package-version-" + sourcePackage.version());
        checks.add("candidate-document-handoff-source-lineage-count-" + lineageCopy.size());
        checks.add("candidate-document-handoff-module-count-" + moduleCopy.size());
        checks.add("candidate-document-handoff-artifact-handle-count-" + artifactCopy.size());
        checks.add("candidate-document-handoff-policy-lock-count-" + policyCopy.size());
        checks.add("candidate-document-handoff-archive-entry-count-" + archiveCopy.size());
        checks.add("candidate-document-handoff-consumer-rule-count-" + consumerCopy.size());
        checks.add("candidate-document-handoff-gate-count-" + gateCopy.size());
        checks.add("candidate-document-handoff-zero-real-documents");
        checks.add("candidate-document-handoff-zero-payloads");
        checks.add("candidate-document-handoff-import-disabled");
        checks.add("candidate-document-handoff-evaluation-disabled");
        checks.add("candidate-document-handoff-approval-disabled");
        checks.add("candidate-document-handoff-runtime-disabled");
        checks.add("candidate-document-handoff-write-disabled");
        checks.add("candidate-document-handoff-sibling-mutation-disabled");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessCandidateDocumentHandoffResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_CANDIDATE_INTAKE_VERSION,
                SOURCE_JAVA_CANDIDATE_INTAKE_VERSION,
                sourcePackage.version(),
                sourcePackage.endpoint(),
                HANDOFF_STATE,
                endpoint,
                profile,
                lineageCopy.size(),
                moduleCopy.size(),
                artifactCopy.size(),
                policyCopy.size(),
                archiveCopy.size(),
                consumerCopy.size(),
                gateCopy.size(),
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                lineageCopy,
                moduleCopy,
                artifactCopy,
                policyCopy,
                archiveCopy,
                consumerCopy,
                gateCopy,
                List.copyOf(checks),
                isComplete(lineageCopy, moduleCopy, artifactCopy, policyCopy, archiveCopy, consumerCopy, gateCopy)
                        ? "passed"
                        : "blocked"
        );
    }

    private static boolean isComplete(
            List<OpsShardReadinessCandidateDocumentHandoffResponse.SourceLineage> sourceLineage,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ArtifactHandle> artifactHandles,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.PolicyLock> policyLocks,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ArchiveEntry> archiveEntries,
            List<OpsShardReadinessCandidateDocumentHandoffResponse.ConsumerRule> consumerRules,
            List<String> gates
    ) {
        return sourceLineage.size() == EXPECTED_SOURCE_LINEAGE_COUNT
                && modules.size() == EXPECTED_MODULE_COUNT
                && artifactHandles.size() == EXPECTED_ARTIFACT_HANDLE_COUNT
                && policyLocks.size() == EXPECTED_POLICY_LOCK_COUNT
                && archiveEntries.size() == EXPECTED_ARCHIVE_ENTRY_COUNT
                && consumerRules.size() == EXPECTED_CONSUMER_RULE_COUNT
                && gates.size() == EXPECTED_GATE_COUNT;
    }
}
