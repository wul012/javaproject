package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionGate> allGates() {
        return List.of(
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_01", "preflight",
                        "Instruction preflight remains slot-map-only.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_02", "instruction",
                        "Instruction artifact remains uncreated.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_03", "draft-text",
                        "Signed draft text remains absent.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_04", "signature",
                        "Detached signature payload remains absent.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_05", "approval",
                        "Approval capture and grants remain disabled.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_06", "value",
                        "Operator value import remains locked.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_07", "runtime",
                        "Runtime payload creation remains locked.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_08", "runtime",
                        "Java service startup remains out of scope.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_09", "runtime",
                        "mini-kv service startup remains out of scope.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_10", "sibling",
                        "Sibling state mutation remains blocked.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_11", "catalog",
                        "Twenty-five instruction slots must be present.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_12", "catalog",
                        "Twenty-five instruction guards must be present.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_13", "source",
                        "Node v1186 authoring readiness remains pinned.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_14", "source",
                        "Java v884 authoring readiness remains pinned.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_15", "secret",
                        "Raw secret values remain absent.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_16", "materialization",
                        "Future instructions are reviewable but not materialized.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_17", "handoff",
                        "Future draft text package must be separate.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_18", "handoff",
                        "Future text package must name owner and evidence requirements.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_19", "handoff",
                        "Future text package must name startup and cleanup commands.", "fail-closed"),
                gate("DRAFT_INSTRUCTION_PREFLIGHT_GATE_20", "closeout",
                        "Closeout stops before real draft text.", "fail-closed")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
            .InstructionGate gate(String code, String category, String gate, String enforcement) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
                .gate(code, category, gate, enforcement);
    }
}
