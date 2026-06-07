package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1161";
    static final String SOURCE_NODE_READINESS_LANE_VERSION = "Node v1136";
    static final String SOURCE_JAVA_READINESS_LANE_VERSION = "Java v834";
    static final String REVIEW_PACKAGE_STATE = "slot-map-only";
    static final String REVIEW_ARTIFACT_STATE = "not-created";
    static final String SIGNED_DRAFT_STATE = "not-created";
    static final String SIGNATURE_CAPTURE_STATE = "not-captured";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String VALUE_IMPORT_STATE = "locked";
    static final String RUNTIME_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
                    .PackageSlot> slots,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
                    .PackageGuard> guards,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
                    .ReviewPackageGate> gates,
            List<String> additionalChecks
    ) {
        var slotCopy = List.copyOf(slots);
        var guardCopy = List.copyOf(guards);
        var gateCopy = List.copyOf(gates);
        int passedSlotCount = (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
        int passedGuardCount = (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-artifact-draft-review-package-preflight-slot-count-" + slotCopy.size());
        checks.add("signed-approval-artifact-draft-review-package-preflight-passed-slot-count-" + passedSlotCount);
        checks.add("signed-approval-artifact-draft-review-package-preflight-guard-count-" + guardCopy.size());
        checks.add("signed-approval-artifact-draft-review-package-preflight-passed-guard-count-" + passedGuardCount);
        checks.add("signed-approval-artifact-draft-review-package-preflight-gate-count-" + gateCopy.size());
        checks.add("signed-approval-artifact-draft-review-package-preflight-source-plan-" + SOURCE_PLAN);
        checks.add("signed-approval-artifact-draft-review-package-preflight-source-node-readiness-lane-"
                + SOURCE_NODE_READINESS_LANE_VERSION);
        checks.add("signed-approval-artifact-draft-review-package-preflight-source-java-readiness-lane-"
                + SOURCE_JAVA_READINESS_LANE_VERSION);
        checks.add("signed-approval-artifact-draft-review-package-preflight-no-review-artifact-creation");
        checks.add("signed-approval-artifact-draft-review-package-preflight-no-signed-draft-text");
        checks.add("signed-approval-artifact-draft-review-package-preflight-no-signature-capture");
        checks.add("signed-approval-artifact-draft-review-package-preflight-no-approval-grant");
        checks.add("signed-approval-artifact-draft-review-package-preflight-no-value-import");
        checks.add("signed-approval-artifact-draft-review-package-preflight-no-runtime-or-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_READINESS_LANE_VERSION,
                SOURCE_JAVA_READINESS_LANE_VERSION,
                REVIEW_PACKAGE_STATE,
                REVIEW_ARTIFACT_STATE,
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

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .PackageSlot slot(
                    String code,
                    String sourceLane,
                    String sourceField,
                    String packagePurpose,
                    String materializationBlocker,
                    String guardCode,
                    String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
                .PackageSlot(
                code,
                sourceLane,
                sourceField,
                packagePurpose,
                materializationBlocker,
                guardCode,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .PackageGuard guard(
                    String code,
                    String category,
                    String guard,
                    String rejectionCode,
                    String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
                .PackageGuard(
                code,
                category,
                guard,
                rejectionCode,
                enforcement,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
            .ReviewPackageGate gate(
                    String code,
                    String category,
                    String gate,
                    String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
                .ReviewPackageGate(code, category, gate, enforcement);
    }
}
