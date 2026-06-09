package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1456";
    static final String SOURCE_NODE_MATERIAL_SUBMISSION_PRECHECK_VERSION = "Node v1456";
    static final String HANDOFF_STATE =
            "archived-read-only-material-submission-precheck-waiting-for-reviewed-real-material";
    static final int EXPECTED_SOURCE_LINEAGE_COUNT = 6;
    static final int EXPECTED_MODULE_COUNT = 5;
    static final int EXPECTED_ARCHIVE_HANDLE_COUNT = 10;
    static final int EXPECTED_POLICY_LOCK_COUNT = 10;
    static final int EXPECTED_ARTIFACT_REFERENCE_COUNT = 8;
    static final int EXPECTED_CONSUMER_RULE_COUNT = 10;
    static final int EXPECTED_SOURCE_CHECKPOINT_COUNT = 10;
    static final int EXPECTED_SOURCE_VALIDATOR_COUNT = 10;
    static final int EXPECTED_SOURCE_ARTIFACT_COUNT = 8;
    static final int EXPECTED_SOURCE_GATE_COUNT = 41;
    static final int EXPECTED_GATE_COUNT = 42;

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffSupport() {
    }

    static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse response(
            String version,
            String endpoint,
            String profile,
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.SourceLineage> sourceLineage,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArchiveHandle> archiveHandles,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock> policyLocks,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArtifactReference> artifactReferences,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule> consumerRules,
            List<String> gates,
            List<String> additionalChecks
    ) {
        var lineageCopy = List.copyOf(sourceLineage);
        var moduleCopy = List.copyOf(modules);
        var archiveCopy = List.copyOf(archiveHandles);
        var policyCopy = List.copyOf(policyLocks);
        var artifactCopy = List.copyOf(artifactReferences);
        var consumerCopy = List.copyOf(consumerRules);
        var gateCopy = List.copyOf(gates);
        List<String> checks = new ArrayList<>();
        checks.add("candidate-document-material-submission-precheck-handoff-source-plan-" + SOURCE_PLAN);
        checks.add("candidate-document-material-submission-precheck-handoff-source-node-"
                + SOURCE_NODE_MATERIAL_SUBMISSION_PRECHECK_VERSION);
        checks.add("candidate-document-material-submission-precheck-handoff-source-java-precheck-"
                + sourcePrecheck.version());
        checks.add("candidate-document-material-submission-precheck-handoff-source-route-"
                + sourcePrecheck.endpoint());
        checks.add("candidate-document-material-submission-precheck-handoff-lineage-count-"
                + lineageCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-module-count-"
                + moduleCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-archive-handle-count-"
                + archiveCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-policy-lock-count-"
                + policyCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-artifact-reference-count-"
                + artifactCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-consumer-rule-count-"
                + consumerCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-source-checkpoint-count-"
                + sourcePrecheck.checkpointCount());
        checks.add("candidate-document-material-submission-precheck-handoff-source-validator-count-"
                + sourcePrecheck.validatorCount());
        checks.add("candidate-document-material-submission-precheck-handoff-source-artifact-count-"
                + sourcePrecheck.artifactCount());
        checks.add("candidate-document-material-submission-precheck-handoff-source-gate-count-"
                + sourcePrecheck.gateCount());
        checks.add("candidate-document-material-submission-precheck-handoff-gate-count-" + gateCopy.size());
        checks.add("candidate-document-material-submission-precheck-handoff-zero-documents");
        checks.add("candidate-document-material-submission-precheck-handoff-zero-payloads");
        checks.add("candidate-document-material-submission-precheck-handoff-material-submission-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-import-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-evaluation-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-approval-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-signature-capture-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-runtime-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-write-disabled");
        checks.add("candidate-document-material-submission-precheck-handoff-sibling-mutation-disabled");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_MATERIAL_SUBMISSION_PRECHECK_VERSION,
                sourcePrecheck.version(),
                sourcePrecheck.endpoint(),
                HANDOFF_STATE,
                endpoint,
                profile,
                lineageCopy.size(),
                moduleCopy.size(),
                archiveCopy.size(),
                policyCopy.size(),
                artifactCopy.size(),
                consumerCopy.size(),
                sourcePrecheck.checkpointCount(),
                sourcePrecheck.validatorCount(),
                sourcePrecheck.artifactCount(),
                sourcePrecheck.gateCount(),
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
                false,
                lineageCopy,
                moduleCopy,
                archiveCopy,
                policyCopy,
                artifactCopy,
                consumerCopy,
                gateCopy,
                List.copyOf(checks),
                isComplete(sourcePrecheck, lineageCopy, moduleCopy, archiveCopy, policyCopy,
                        artifactCopy, consumerCopy, gateCopy)
                        ? "passed"
                        : "blocked"
        );
    }

    private static boolean isComplete(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse sourcePrecheck,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.SourceLineage> sourceLineage,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArchiveHandle> archiveHandles,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.PolicyLock> policyLocks,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ArtifactReference> artifactReferences,
            List<OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse.ConsumerRule> consumerRules,
            List<String> gates
    ) {
        return sourceLineage.size() == EXPECTED_SOURCE_LINEAGE_COUNT
                && modules.size() == EXPECTED_MODULE_COUNT
                && archiveHandles.size() == EXPECTED_ARCHIVE_HANDLE_COUNT
                && policyLocks.size() == EXPECTED_POLICY_LOCK_COUNT
                && artifactReferences.size() == EXPECTED_ARTIFACT_REFERENCE_COUNT
                && consumerRules.size() == EXPECTED_CONSUMER_RULE_COUNT
                && sourcePrecheck.checkpointCount() == EXPECTED_SOURCE_CHECKPOINT_COUNT
                && sourcePrecheck.validatorCount() == EXPECTED_SOURCE_VALIDATOR_COUNT
                && sourcePrecheck.artifactCount() == EXPECTED_SOURCE_ARTIFACT_COUNT
                && sourcePrecheck.gateCount() == EXPECTED_SOURCE_GATE_COUNT
                && gates.size() == EXPECTED_GATE_COUNT;
    }
}
