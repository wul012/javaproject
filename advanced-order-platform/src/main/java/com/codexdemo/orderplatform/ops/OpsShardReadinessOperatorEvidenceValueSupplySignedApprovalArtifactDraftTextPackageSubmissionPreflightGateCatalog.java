package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightGateCatalog {

    static final int GATE_COUNT = 10;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightGateCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
            .SubmissionGate> allGates() {
        return List.of(
                gate("SUBMISSION_PREFLIGHT_GATE_01", "submission", "Submission preflight remains slots-only."),
                gate("SUBMISSION_PREFLIGHT_GATE_02", "acceptance", "Submitted package is not accepted."),
                gate("SUBMISSION_PREFLIGHT_GATE_03", "draft-text", "Signed draft text is not parsed."),
                gate("SUBMISSION_PREFLIGHT_GATE_04", "signature", "Detached signature payload is not parsed."),
                gate("SUBMISSION_PREFLIGHT_GATE_05", "approval", "Approval grants remain disabled."),
                gate("SUBMISSION_PREFLIGHT_GATE_06", "value", "Operator value import remains locked."),
                gate("SUBMISSION_PREFLIGHT_GATE_07", "runtime", "Runtime and startup remain locked."),
                gate("SUBMISSION_PREFLIGHT_GATE_08", "sibling", "Sibling mutation remains blocked."),
                gate("SUBMISSION_PREFLIGHT_GATE_09", "catalog", "Twenty-five slots and controls are present."),
                gate("SUBMISSION_PREFLIGHT_GATE_10", "closeout", "Closeout stops before manual package acceptance.")
        );
    }

    private static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
            .SubmissionGate gate(String code, String category, String gate) {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
                .gate(code, category, gate);
    }
}
