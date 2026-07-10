package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewComparisonOutcomeService {

  static final String ENDPOINT =
      OpsShardReadinessComparedPackageReviewRoutePaths.COMPARISON_OUTCOME;
  static final String PROFILE =
      "java-shard-readiness-compared-package-review-comparison-outcome.v1";

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
