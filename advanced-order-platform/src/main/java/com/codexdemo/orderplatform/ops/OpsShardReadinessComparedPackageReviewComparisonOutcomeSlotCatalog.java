package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedPackageReviewComparisonOutcomeSlotCatalog {

    private OpsShardReadinessComparedPackageReviewComparisonOutcomeSlotCatalog() {
    }

    static List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> comparisonOutcomeSlots() {
        return List.of(
                OpsShardReadinessComparedPackageReviewSupport.slot(
                        "offline-comparison-result",
                        "Java v1024",
                        "comparison outcome",
                        "Offline compared package result is expected as an operator-provided reference only.",
                        "Is the comparison result named without parsing its payload?",
                        "reject-missing-offline-comparison-result",
                        OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.SUBMISSION_COMPARISON
                ),
                OpsShardReadinessComparedPackageReviewSupport.slot(
                        "mismatch-exception-summary",
                        "Java v1024",
                        "comparison outcome",
                        "Mismatch summary must be separated from approval and runtime decisions.",
                        "Are comparison exceptions described without granting approval?",
                        "reject-missing-mismatch-exception-summary",
                        OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.SUBMISSION_COMPARISON
                ),
                OpsShardReadinessComparedPackageReviewSupport.slot(
                        "reviewer-note-trace",
                        "Java v1024",
                        "comparison outcome",
                        "Reviewer notes require a trace handle instead of free-form material ingestion.",
                        "Can reviewer notes be traced without opening a raw endpoint?",
                        "reject-missing-reviewer-note-trace",
                        OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.SUBMISSION_COMPARISON
                )
        );
    }
}
