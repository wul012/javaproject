package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1136";
    static final String SOURCE_NODE_DRAFT_PREFLIGHT_VERSION = "Node v1111";
    static final String SOURCE_JAVA_DRAFT_PREFLIGHT_VERSION = "Java v809";
    static final String READINESS_LANE_STATE = "review-lane-only";
    static final String MANUAL_PACKAGE_STATE = "not-authored";
    static final String MANUAL_DRAFT_STATE = "not-created";
    static final String DRAFT_MATERIALIZATION_STATE = "not-materialized";
    static final String SIGNATURE_CAPTURE_STATE = "not-captured";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String VALUE_IMPORT_STATE = "locked";
    static final String RUNTIME_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                    .ReadinessLane> lanes,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                    .ControlBlocker> blockers,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                    .ReadinessLaneGate> gates,
            List<String> additionalChecks
    ) {
        var laneCopy = List.copyOf(lanes);
        var blockerCopy = List.copyOf(blockers);
        var gateCopy = List.copyOf(gates);
        int passedLaneCount = (int) laneCopy.stream().filter(lane -> "passed".equals(lane.status())).count();
        int passedBlockerCount = (int) blockerCopy.stream()
                .filter(blocker -> "passed".equals(blocker.status()))
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-artifact-draft-readiness-lane-count-" + laneCopy.size());
        checks.add("signed-approval-artifact-draft-readiness-lane-passed-count-" + passedLaneCount);
        checks.add("signed-approval-artifact-draft-readiness-lane-blocker-count-" + blockerCopy.size());
        checks.add("signed-approval-artifact-draft-readiness-lane-passed-blocker-count-" + passedBlockerCount);
        checks.add("signed-approval-artifact-draft-readiness-lane-gate-count-" + gateCopy.size());
        checks.add("signed-approval-artifact-draft-readiness-lane-source-plan-" + SOURCE_PLAN);
        checks.add("signed-approval-artifact-draft-readiness-lane-source-node-preflight-"
                + SOURCE_NODE_DRAFT_PREFLIGHT_VERSION);
        checks.add("signed-approval-artifact-draft-readiness-lane-source-java-preflight-"
                + SOURCE_JAVA_DRAFT_PREFLIGHT_VERSION);
        checks.add("signed-approval-artifact-draft-readiness-lane-no-real-manual-draft");
        checks.add("signed-approval-artifact-draft-readiness-lane-no-manual-package-authoring");
        checks.add("signed-approval-artifact-draft-readiness-lane-no-signature-capture");
        checks.add("signed-approval-artifact-draft-readiness-lane-no-approval-grant");
        checks.add("signed-approval-artifact-draft-readiness-lane-no-value-import");
        checks.add("signed-approval-artifact-draft-readiness-lane-no-runtime-or-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_DRAFT_PREFLIGHT_VERSION,
                SOURCE_JAVA_DRAFT_PREFLIGHT_VERSION,
                READINESS_LANE_STATE,
                MANUAL_PACKAGE_STATE,
                MANUAL_DRAFT_STATE,
                DRAFT_MATERIALIZATION_STATE,
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
                endpoint,
                profile,
                laneCopy.size(),
                passedLaneCount,
                blockerCopy.size(),
                passedBlockerCount,
                gateCopy.size(),
                laneCopy,
                blockerCopy,
                gateCopy,
                List.copyOf(checks),
                passedLaneCount == laneCopy.size() && passedBlockerCount == blockerCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse.ReadinessLane
    lane(
            String code,
            String sourceField,
            String reviewPurpose,
            String manualReviewBlocker,
            String blockerCode,
            String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                .ReadinessLane(
                code,
                sourceField,
                reviewPurpose,
                manualReviewBlocker,
                blockerCode,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse.ControlBlocker
    blocker(
            String code,
            String category,
            String blocker,
            String rejectionCode,
            String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                .ControlBlocker(
                code,
                category,
                blocker,
                rejectionCode,
                enforcement,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse.ReadinessLaneGate
    gate(String code, String category, String gate, String enforcement) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
                .ReadinessLaneGate(code, category, gate, enforcement);
    }
}
