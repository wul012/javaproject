package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessCandidateDocumentIntakePacketSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1421";
    static final String SOURCE_NODE_SUBMISSION_PRECHECK_VERSION = "Node v1411";
    static final String INTAKE_PACKET_STATE =
            "waiting-for-reviewed-real-compared-package-evidence-candidate-document";
    static final int EXPECTED_SOURCE_LINEAGE_COUNT = 5;
    static final int EXPECTED_MODULE_COUNT = 5;
    static final int EXPECTED_SLOT_COUNT = 10;
    static final int EXPECTED_GUARD_COUNT = 10;
    static final int EXPECTED_COVERED_CHECKPOINT_COUNT = 25;
    static final int EXPECTED_COVERED_VALIDATOR_COUNT = 25;
    static final int EXPECTED_CARRIED_FIELD_COUNT = 20;
    static final int EXPECTED_ARTIFACT_COUNT = 8;
    static final int EXPECTED_GATE_COUNT = 35;

    private OpsShardReadinessCandidateDocumentIntakePacketSupport() {
    }

    static OpsShardReadinessCandidateDocumentIntakePacketResponse response(
            String version,
            String endpoint,
            String profile,
            OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse sourcePrecheck,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage> sourceLineage,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot> slots,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard> guards,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact> artifacts,
            List<String> gates,
            List<String> additionalChecks
    ) {
        var sourceCopy = List.copyOf(sourceLineage);
        var moduleCopy = List.copyOf(modules);
        var slotCopy = List.copyOf(slots);
        var guardCopy = List.copyOf(guards);
        var artifactCopy = List.copyOf(artifacts);
        var gateCopy = List.copyOf(gates);
        int passedSlotCount = (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
        int passedGuardCount = (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
        int coveredCheckpointCount = slotCopy.stream()
                .mapToInt(OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot::coveredCheckpointCount)
                .sum();
        int carriedFieldCount = slotCopy.stream()
                .mapToInt(OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot::carriedFieldCount)
                .sum();
        List<String> checks = new ArrayList<>();
        checks.add("candidate-document-intake-packet-source-plan-" + SOURCE_PLAN);
        checks.add("candidate-document-intake-packet-source-node-" + SOURCE_NODE_SUBMISSION_PRECHECK_VERSION);
        checks.add("candidate-document-intake-packet-source-java-precheck-" + sourcePrecheck.version());
        checks.add("candidate-document-intake-packet-source-lineage-count-" + sourceCopy.size());
        checks.add("candidate-document-intake-packet-module-count-" + moduleCopy.size());
        checks.add("candidate-document-intake-packet-slot-count-" + slotCopy.size());
        checks.add("candidate-document-intake-packet-guard-count-" + guardCopy.size());
        checks.add("candidate-document-intake-packet-covered-checkpoint-count-" + coveredCheckpointCount);
        checks.add("candidate-document-intake-packet-covered-validator-count-" + sourcePrecheck.validatorCount());
        checks.add("candidate-document-intake-packet-carried-field-count-" + carriedFieldCount);
        checks.add("candidate-document-intake-packet-artifact-count-" + artifactCopy.size());
        checks.add("candidate-document-intake-packet-gate-count-" + gateCopy.size());
        checks.add("candidate-document-intake-packet-zero-documents");
        checks.add("candidate-document-intake-packet-zero-payloads");
        checks.add("candidate-document-intake-packet-no-material-accepted");
        checks.add("candidate-document-intake-packet-import-disabled");
        checks.add("candidate-document-intake-packet-evaluation-disabled");
        checks.add("candidate-document-intake-packet-approval-disabled");
        checks.add("candidate-document-intake-packet-signature-capture-disabled");
        checks.add("candidate-document-intake-packet-runtime-disabled");
        checks.add("candidate-document-intake-packet-write-disabled");
        checks.add("candidate-document-intake-packet-sibling-mutation-disabled");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessCandidateDocumentIntakePacketResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_SUBMISSION_PRECHECK_VERSION,
                sourcePrecheck.version(),
                sourcePrecheck.endpoint(),
                INTAKE_PACKET_STATE,
                endpoint,
                profile,
                sourceCopy.size(),
                moduleCopy.size(),
                slotCopy.size(),
                passedSlotCount,
                guardCopy.size(),
                passedGuardCount,
                coveredCheckpointCount,
                sourcePrecheck.validatorCount(),
                carriedFieldCount,
                artifactCopy.size(),
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
                sourceCopy,
                moduleCopy,
                slotCopy,
                guardCopy,
                artifactCopy,
                gateCopy,
                List.copyOf(checks),
                isComplete(sourceCopy, moduleCopy, slotCopy, guardCopy, artifactCopy, gateCopy,
                        coveredCheckpointCount, sourcePrecheck.validatorCount(), carriedFieldCount)
                        && passedSlotCount == slotCopy.size()
                        && passedGuardCount == guardCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    private static boolean isComplete(
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.SourceLineage> sourceLineage,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.ModuleEntry> modules,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeSlot> slots,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.IntakeGuard> guards,
            List<OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact> artifacts,
            List<String> gates,
            int coveredCheckpointCount,
            int coveredValidatorCount,
            int carriedFieldCount
    ) {
        return sourceLineage.size() == EXPECTED_SOURCE_LINEAGE_COUNT
                && modules.size() == EXPECTED_MODULE_COUNT
                && slots.size() == EXPECTED_SLOT_COUNT
                && guards.size() == EXPECTED_GUARD_COUNT
                && artifacts.size() == EXPECTED_ARTIFACT_COUNT
                && gates.size() == EXPECTED_GATE_COUNT
                && coveredCheckpointCount == EXPECTED_COVERED_CHECKPOINT_COUNT
                && coveredValidatorCount == EXPECTED_COVERED_VALIDATOR_COUNT
                && carriedFieldCount == EXPECTED_CARRIED_FIELD_COUNT;
    }
}
