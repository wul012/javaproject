package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog {

    static final int GATE_COUNT = 20;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
            .IntakeGate> allGates() {
        return List.of(
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_01", "intake",
                        "Draft text package intake remains expected-fields-only."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_02", "draft-text",
                        "Draft text artifact is not accepted by this contract."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_03", "signature",
                        "Detached signature payload is not accepted by this contract."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_04", "approval",
                        "Approval capture and grants remain disabled."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_05", "value",
                        "Operator value import remains locked."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_06", "runtime",
                        "Runtime payload creation remains locked."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_07", "runtime",
                        "Java service startup remains out of scope."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_08", "runtime",
                        "mini-kv service startup remains out of scope."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_09", "sibling",
                        "Sibling mutation remains blocked."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_10", "catalog",
                        "Twenty-five intake fields must be present."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_11", "catalog",
                        "Twenty-five intake guards must be present."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_12", "source",
                        "Node v1236 intake roadmap remains pinned."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_13", "source",
                        "Node v1211 instruction preflight remains pinned."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_14", "source",
                        "Java v909 instruction preflight remains pinned."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_15", "secret",
                        "Raw secret values remain absent."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_16", "field-map",
                        "Expected field map is typed but not ingested."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_17", "digest",
                        "Digest pins are required before later review."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_18", "review",
                        "Later package review must be a separate step."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_19", "archive",
                        "Archive closeout manifest must be present."),
                gate("DRAFT_TEXT_PACKAGE_INTAKE_GATE_20", "closeout",
                        "Closeout stops before signed approval consideration.")
        );
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
            .IntakeGate> gates(int fromInclusive, int toExclusive) {
        return List.copyOf(allGates().subList(fromInclusive, toExclusive));
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
            .IntakeGate gate(String code, String category, String gate) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport
                .gate(code, category, gate, "fail-closed");
    }
}
