package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringGate> allGates() {
        return List.of(
                gate("DRAFT_AUTHORING_READINESS_GATE_01", "readiness",
                        "Authoring readiness remains metadata-only.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_02", "draft-text",
                        "Signed draft text remains absent.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_03", "signature",
                        "Detached signature payload remains absent.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_04", "approval",
                        "Approval capture remains disabled.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_05", "approval",
                        "Approval grant emission remains disabled.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_06", "value",
                        "Operator value import remains locked.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_07", "runtime",
                        "Runtime payload creation remains locked.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_08", "runtime",
                        "Java service startup remains out of scope.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_09", "runtime",
                        "mini-kv service startup remains out of scope.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_10", "sibling",
                        "Sibling state mutation remains blocked.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_11", "catalog",
                        "Twenty-five authoring requirements must be present.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_12", "catalog",
                        "Twenty-five authoring blockers must be present.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_13", "source",
                        "Node v1161 review package preflight remains pinned.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_14", "source",
                        "Java v859 review package preflight remains pinned.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_15", "secret",
                        "Raw secret values remain absent.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_16", "artifact",
                        "Authoring artifact remains uncreated.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_17", "handoff",
                        "Any human draft authoring must happen in a separate package.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_18", "handoff",
                        "Future execution package must name owner and route boundary.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_19", "handoff",
                        "Future execution package must name startup and cleanup commands.", "fail-closed"),
                gate("DRAFT_AUTHORING_READINESS_GATE_20", "closeout",
                        "Closeout stops before signed draft materialization.", "fail-closed")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringGate gate(
                    String code,
                    String category,
                    String gate,
                    String enforcement
    ) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
                .gate(code, category, gate, enforcement);
    }
}
