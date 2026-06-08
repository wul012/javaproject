package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewComparisonOutcomeService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_COMPARISON_OUTCOME;
    static final String PROFILE = "java-shard-readiness-compared-package-review-comparison-outcome.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedPackageReviewResponse comparisonOutcome() {
        return OpsShardReadinessComparedPackageReviewCatalogService.response(
                "Java v1036",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedPackageReviewComparisonOutcomeSlotCatalog.comparisonOutcomeSlots(),
                OpsShardReadinessComparedPackageReviewGuardCatalog.comparisonGuards(),
                OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.comparisonGroups(),
                List.of("compared-package-review-comparison-outcome-only"));
    }
}
