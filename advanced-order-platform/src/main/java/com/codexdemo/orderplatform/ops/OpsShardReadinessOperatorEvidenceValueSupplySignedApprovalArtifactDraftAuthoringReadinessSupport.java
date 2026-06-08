package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1186";
    static final String SOURCE_NODE_REVIEW_PACKAGE_PREFLIGHT_VERSION = "Node v1161";
    static final String SOURCE_JAVA_REVIEW_PACKAGE_PREFLIGHT_VERSION = "Java v859";
    static final String AUTHORING_READINESS_STATE = "requirement-map-only";
    static final String AUTHORING_ARTIFACT_STATE = "not-created";
    static final String SIGNED_DRAFT_STATE = "not-created";
    static final String SIGNATURE_CAPTURE_STATE = "not-captured";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String VALUE_IMPORT_STATE = "locked";
    static final String RUNTIME_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport() {
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                    .AuthoringRequirement> requirements,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                    .AuthoringBlocker> blockers,
            List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                    .AuthoringGate> gates,
            List<String> additionalChecks
    ) {
        var requirementCopy = List.copyOf(requirements);
        var blockerCopy = List.copyOf(blockers);
        var gateCopy = List.copyOf(gates);
        int passedRequirementCount = (int) requirementCopy.stream()
                .filter(requirement -> "passed".equals(requirement.status()))
                .count();
        int passedBlockerCount = (int) blockerCopy.stream()
                .filter(blocker -> "passed".equals(blocker.status()))
                .count();
        List<String> checks = new ArrayList<>();
        checks.add("signed-approval-artifact-draft-authoring-readiness-requirement-count-"
                + requirementCopy.size());
        checks.add("signed-approval-artifact-draft-authoring-readiness-passed-requirement-count-"
                + passedRequirementCount);
        checks.add("signed-approval-artifact-draft-authoring-readiness-blocker-count-" + blockerCopy.size());
        checks.add("signed-approval-artifact-draft-authoring-readiness-passed-blocker-count-"
                + passedBlockerCount);
        checks.add("signed-approval-artifact-draft-authoring-readiness-gate-count-" + gateCopy.size());
        checks.add("signed-approval-artifact-draft-authoring-readiness-source-plan-" + SOURCE_PLAN);
        checks.add("signed-approval-artifact-draft-authoring-readiness-source-node-review-package-preflight-"
                + SOURCE_NODE_REVIEW_PACKAGE_PREFLIGHT_VERSION);
        checks.add("signed-approval-artifact-draft-authoring-readiness-source-java-review-package-preflight-"
                + SOURCE_JAVA_REVIEW_PACKAGE_PREFLIGHT_VERSION);
        checks.add("signed-approval-artifact-draft-authoring-readiness-no-authoring-artifact-creation");
        checks.add("signed-approval-artifact-draft-authoring-readiness-no-signed-draft-text");
        checks.add("signed-approval-artifact-draft-authoring-readiness-no-signature-capture");
        checks.add("signed-approval-artifact-draft-authoring-readiness-no-approval-grant");
        checks.add("signed-approval-artifact-draft-authoring-readiness-no-value-import");
        checks.add("signed-approval-artifact-draft-authoring-readiness-no-runtime-or-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_REVIEW_PACKAGE_PREFLIGHT_VERSION,
                SOURCE_JAVA_REVIEW_PACKAGE_PREFLIGHT_VERSION,
                AUTHORING_READINESS_STATE,
                AUTHORING_ARTIFACT_STATE,
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
                requirementCopy.size(),
                passedRequirementCount,
                blockerCopy.size(),
                passedBlockerCount,
                gateCopy.size(),
                requirementCopy,
                blockerCopy,
                gateCopy,
                List.copyOf(checks),
                passedRequirementCount == requirementCopy.size() && passedBlockerCount == blockerCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringRequirement requirement(
                    String code,
                    String sourceReviewPackageSlot,
                    String sourceField,
                    String authoringPurpose,
                    String authoringBlocker,
                    String blockerCode,
                    String sourceEndpoint
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                .AuthoringRequirement(
                code,
                sourceReviewPackageSlot,
                sourceField,
                authoringPurpose,
                authoringBlocker,
                blockerCode,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringBlocker blocker(
                    String code,
                    String category,
                    String blocker,
                    String rejectionCode,
                    String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                .AuthoringBlocker(
                code,
                category,
                blocker,
                rejectionCode,
                enforcement,
                "passed"
        );
    }

    static OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
            .AuthoringGate gate(
                    String code,
                    String category,
                    String gate,
                    String enforcement
    ) {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
                .AuthoringGate(code, category, gate, enforcement);
    }
}
