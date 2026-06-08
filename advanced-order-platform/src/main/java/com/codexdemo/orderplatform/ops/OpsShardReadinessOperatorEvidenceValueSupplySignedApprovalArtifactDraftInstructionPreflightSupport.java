package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1211";
    static final String SOURCE_NODE_AUTHORING_READINESS_VERSION = "Node v1186";
    static final String SOURCE_JAVA_AUTHORING_READINESS_VERSION = "Java v884";
    static final String INSTRUCTION_PREFLIGHT_STATE = "slot-map-only";
    static final String INSTRUCTION_ARTIFACT_STATE = "not-created";
    static final String SIGNED_DRAFT_STATE = "not-created";
    static final String SIGNATURE_CAPTURE_STATE = "not-captured";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String VALUE_IMPORT_STATE = "locked";
    static final String RUNTIME_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                    .InstructionSlot> slots,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                    .InstructionGuard> guards,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                    .InstructionGate> gates,
            List<String> additionalChecks
    ) {
        var slotCopy = List.copyOf(slots);
        var guardCopy = List.copyOf(guards);
        var gateCopy = List.copyOf(gates);
        int passedSlotCount = (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
        int passedGuardCount = (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-artifact-draft-instruction-preflight-slot-count-" + slotCopy.size());
        checks.add("signed-approval-artifact-draft-instruction-preflight-passed-slot-count-" + passedSlotCount);
        checks.add("signed-approval-artifact-draft-instruction-preflight-guard-count-" + guardCopy.size());
        checks.add("signed-approval-artifact-draft-instruction-preflight-passed-guard-count-" + passedGuardCount);
        checks.add("signed-approval-artifact-draft-instruction-preflight-gate-count-" + gateCopy.size());
        checks.add("signed-approval-artifact-draft-instruction-preflight-source-plan-" + SOURCE_PLAN);
        checks.add("signed-approval-artifact-draft-instruction-preflight-source-node-authoring-readiness-"
                + SOURCE_NODE_AUTHORING_READINESS_VERSION);
        checks.add("signed-approval-artifact-draft-instruction-preflight-source-java-authoring-readiness-"
                + SOURCE_JAVA_AUTHORING_READINESS_VERSION);
        checks.add("signed-approval-artifact-draft-instruction-preflight-no-instruction-artifact-creation");
        checks.add("signed-approval-artifact-draft-instruction-preflight-no-signed-draft-text");
        checks.add("signed-approval-artifact-draft-instruction-preflight-no-signature-capture");
        checks.add("signed-approval-artifact-draft-instruction-preflight-no-approval-grant");
        checks.add("signed-approval-artifact-draft-instruction-preflight-no-value-import");
        checks.add("signed-approval-artifact-draft-instruction-preflight-no-runtime-or-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_AUTHORING_READINESS_VERSION,
                SOURCE_JAVA_AUTHORING_READINESS_VERSION,
                INSTRUCTION_PREFLIGHT_STATE,
                INSTRUCTION_ARTIFACT_STATE,
                SIGNED_DRAFT_STATE,
                SIGNATURE_CAPTURE_STATE,
                APPROVAL_GRANT_STATE,
                VALUE_IMPORT_STATE,
                RUNTIME_STATE,
                SIBLING_MUTATION_STATE,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                endpoint,
                profile,
                slotCopy.size(),
                passedSlotCount,
                guardCopy.size(),
                passedGuardCount,
                gateCopy.size(),
                slotCopy,
                guardCopy,
                gateCopy,
                List.copyOf(checks),
                passedSlotCount == slotCopy.size() && passedGuardCount == guardCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionSlot slot(
                    String code,
                    String sourceAuthoringRequirement,
                    String futureInstruction,
                    String instructionPurpose,
                    String materializationBlocker,
                    String guardCode,
                    String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                .InstructionSlot(
                code,
                sourceAuthoringRequirement,
                futureInstruction,
                instructionPurpose,
                materializationBlocker,
                guardCode,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionGuard guard(
                    String code,
                    String category,
                    String guard,
                    String rejectionCode,
                    String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                .InstructionGuard(code, category, guard, rejectionCode, enforcement, "passed");
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionGate gate(
                    String code,
                    String category,
                    String gate,
                    String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
                .InstructionGate(code, category, gate, enforcement);
    }
}
