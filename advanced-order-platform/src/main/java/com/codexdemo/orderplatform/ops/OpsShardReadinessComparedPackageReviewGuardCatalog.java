package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedPackageReviewGuardCatalog {

    private OpsShardReadinessComparedPackageReviewGuardCatalog() {
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> allGuards() {
        return List.of(
                OpsShardReadinessComparedPackageReviewSupport.guard("source-catalog-present", "source evidence",
                        "Block review handoff when the intake catalog cannot be traced.",
                        "reject-review-source-catalog-missing"),
                OpsShardReadinessComparedPackageReviewSupport.guard("source-acceptance-pointer-only", "source evidence",
                        "Reject any packet that treats source acceptance as accepted evidence.",
                        "reject-review-source-acceptance-mutating"),
                OpsShardReadinessComparedPackageReviewSupport.guard("manual-reference-required", "source evidence",
                        "Block review when the manual submission reference is absent.",
                        "reject-review-manual-reference-missing"),
                OpsShardReadinessComparedPackageReviewSupport.guard("comparison-result-reference-only",
                        "comparison outcome",
                        "Reject comparison material that requires parsing compared package payloads.",
                        "reject-review-comparison-payload-parse"),
                OpsShardReadinessComparedPackageReviewSupport.guard("mismatch-summary-non-approving",
                        "comparison outcome",
                        "Block any mismatch summary that implies approval or rejection authority.",
                        "reject-review-mismatch-decision-coupling"),
                OpsShardReadinessComparedPackageReviewSupport.guard("reviewer-note-handle-only",
                        "comparison outcome",
                        "Reject reviewer notes that require raw endpoint exposure.",
                        "reject-reviewer-note-raw-endpoint"),
                OpsShardReadinessComparedPackageReviewSupport.guard("identity-binding-non-switching",
                        "identity digest",
                        "Block identity review that changes runtime principal or tenant context.",
                        "reject-review-identity-switch"),
                OpsShardReadinessComparedPackageReviewSupport.guard("digest-summary-no-body-storage",
                        "identity digest",
                        "Reject digest review that stores compared package body content.",
                        "reject-review-digest-body-storage"),
                OpsShardReadinessComparedPackageReviewSupport.guard("detached-signature-unparsed",
                        "identity digest",
                        "Block detached signature parsing during review readiness.",
                        "reject-review-signature-parsing"),
                OpsShardReadinessComparedPackageReviewSupport.guard("execution-lock-preserved",
                        "policy archive",
                        "Reject any review path that unlocks runtime execution.",
                        "reject-review-execution-unlock"),
                OpsShardReadinessComparedPackageReviewSupport.guard("approval-grant-separated",
                        "policy archive",
                        "Block approval grants from this review handoff surface.",
                        "reject-review-approval-grant"),
                OpsShardReadinessComparedPackageReviewSupport.guard("archive-write-disabled",
                        "policy archive",
                        "Reject archive material creation from review readiness endpoints.",
                        "reject-review-archive-write")
        );
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> sourceGuards() {
        return allGuards().subList(0, 3);
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> comparisonGuards() {
        return allGuards().subList(3, 6);
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> identityDigestGuards() {
        return allGuards().subList(6, 9);
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> policyArchiveGuards() {
        return allGuards().subList(9, 12);
    }
}
