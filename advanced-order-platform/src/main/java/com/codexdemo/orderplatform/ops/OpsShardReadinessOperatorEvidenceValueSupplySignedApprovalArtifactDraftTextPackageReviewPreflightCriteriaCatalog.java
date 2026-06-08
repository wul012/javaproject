package com.codexdemo.orderplatform.ops;

import java.util.List;
import java.util.stream.Stream;

final class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog {

    static final int CRITERION_COUNT = 25;

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog() {
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewCriterion> allCriteria() {
        return Stream.concat(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightFoundationCriteriaCatalog
                        .foundationCriteria().stream(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceCriteriaCatalog
                        .assuranceCriteria().stream()
        ).toList();
    }

    static List<OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
            .ReviewCriterion> criteria(int fromInclusive, int toExclusive) {
        return List.copyOf(allCriteria().subList(fromInclusive, toExclusive));
    }
}
