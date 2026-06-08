package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedPackageReviewReviewerGroupCatalog {

    private OpsShardReadinessComparedPackageReviewReviewerGroupCatalog() {
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> allGroups() {
        return List.of(
                OpsShardReadinessComparedPackageReviewSupport.reviewerGroup(
                        "source-evidence-reviewer",
                        "operator evidence reviewer",
                        "Trace source intake references and missing-evidence boundaries.",
                        "evidence acceptance"
                ),
                OpsShardReadinessComparedPackageReviewSupport.reviewerGroup(
                        "comparison-outcome-reviewer",
                        "manual comparison reviewer",
                        "Confirm comparison result handles and mismatch summaries stay reference-only.",
                        "review decision"
                ),
                OpsShardReadinessComparedPackageReviewSupport.reviewerGroup(
                        "identity-digest-reviewer",
                        "identity digest reviewer",
                        "Check identity, digest, and detached signature observations without parsing.",
                        "principal switch"
                ),
                OpsShardReadinessComparedPackageReviewSupport.reviewerGroup(
                        "policy-lock-reviewer",
                        "policy lock reviewer",
                        "Confirm approval grant and runtime execution stay locked.",
                        "approval grant"
                ),
                OpsShardReadinessComparedPackageReviewSupport.reviewerGroup(
                        "archive-closeout-owner",
                        "archive closeout owner",
                        "Record the closeout boundary without writing archive material.",
                        "archive write"
                )
        );
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> sourceGroups() {
        return List.of(allGroups().get(0));
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> comparisonGroups() {
        return List.of(allGroups().get(1));
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> identityDigestGroups() {
        return List.of(allGroups().get(2));
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> policyArchiveGroups() {
        return List.of(allGroups().get(3), allGroups().get(4));
    }
}
