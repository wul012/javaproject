package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessComparedPackageReviewSupport {

    static final String PROJECT = "advanced-order-platform";
    static final String SOURCE_PLAN = "Node v1331";
    static final String SOURCE_NODE_EVIDENCE_INTAKE_VERSION = "Node v1331";
    static final String SOURCE_JAVA_EVIDENCE_INTAKE_VERSION = "Java v1024";
    static final String REVIEW_CONTRACT_STATE = "manual-review-readiness-only";
    static final String EVIDENCE_ACCEPTANCE_STATE = "not-accepted";
    static final String REVIEW_DECISION_STATE = "not-decided";
    static final String APPROVAL_GRANT_STATE = "not-emitted";
    static final String RUNTIME_PAYLOAD_STATE = "locked";
    static final String SIBLING_MUTATION_STATE = "locked";

    private OpsShardReadinessComparedPackageReviewSupport() {
    }

    static OpsShardReadinessComparedPackageReviewResponse response(
            String version,
            String endpoint,
            String profile,
            List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> slots,
            List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> guards,
            List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> reviewerGroups,
            List<String> additionalChecks
    ) {
        var slotCopy = List.copyOf(slots);
        var guardCopy = List.copyOf(guards);
        var reviewerGroupCopy = List.copyOf(reviewerGroups);
        int passedSlotCount = (int) slotCopy.stream().filter(slot -> "passed".equals(slot.status())).count();
        int passedGuardCount = (int) guardCopy.stream().filter(guard -> "passed".equals(guard.status())).count();
        List<String> checks = new ArrayList<>();
        checks.add("compared-package-review-slot-count-" + slotCopy.size());
        checks.add("compared-package-review-guard-count-" + guardCopy.size());
        checks.add("compared-package-review-reviewer-group-count-" + reviewerGroupCopy.size());
        checks.add("compared-package-review-source-plan-" + SOURCE_PLAN);
        checks.add("compared-package-review-source-node-" + SOURCE_NODE_EVIDENCE_INTAKE_VERSION);
        checks.add("compared-package-review-source-java-" + SOURCE_JAVA_EVIDENCE_INTAKE_VERSION);
        checks.add("compared-package-review-no-evidence-acceptance");
        checks.add("compared-package-review-no-review-decision");
        checks.add("compared-package-review-no-approval-grant");
        checks.add("compared-package-review-no-runtime-payload");
        checks.add("compared-package-review-no-sibling-mutation");
        checks.addAll(additionalChecks);

        return new OpsShardReadinessComparedPackageReviewResponse(
                PROJECT,
                version,
                true,
                false,
                true,
                SOURCE_PLAN,
                SOURCE_NODE_EVIDENCE_INTAKE_VERSION,
                SOURCE_JAVA_EVIDENCE_INTAKE_VERSION,
                REVIEW_CONTRACT_STATE,
                EVIDENCE_ACCEPTANCE_STATE,
                REVIEW_DECISION_STATE,
                APPROVAL_GRANT_STATE,
                RUNTIME_PAYLOAD_STATE,
                SIBLING_MUTATION_STATE,
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
                reviewerGroupCopy.size(),
                slotCopy,
                guardCopy,
                reviewerGroupCopy,
                List.copyOf(checks),
                passedSlotCount == slotCopy.size() && passedGuardCount == guardCopy.size()
                        ? "passed"
                        : "blocked"
        );
    }

    static OpsShardReadinessComparedPackageReviewResponse.ReviewSlot slot(
            String code,
            String sourceVersion,
            String reviewArea,
            String expectedEvidence,
            String reviewerQuestion,
            String missingEvidenceGuard,
            String sourceEndpoint
    ) {
        return new OpsShardReadinessComparedPackageReviewResponse.ReviewSlot(
                code,
                sourceVersion,
                reviewArea,
                expectedEvidence,
                reviewerQuestion,
                missingEvidenceGuard,
                sourceEndpoint,
                "passed"
        );
    }

    static OpsShardReadinessComparedPackageReviewResponse.ReviewGuard guard(
            String code,
            String category,
            String guard,
            String rejectionCode
    ) {
        return new OpsShardReadinessComparedPackageReviewResponse.ReviewGuard(
                code,
                category,
                guard,
                rejectionCode,
                "fail-closed",
                "passed"
        );
    }

    static OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup reviewerGroup(
            String code,
            String owner,
            String responsibility,
            String blockedAction
    ) {
        return new OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup(
                code,
                owner,
                responsibility,
                blockedAction,
                "passed"
        );
    }
}
