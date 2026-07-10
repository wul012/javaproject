package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewCatalogService {

  static final String ENDPOINT = OpsShardReadinessComparedPackageReviewRoutePaths.CATALOG;
  static final String PROFILE = "java-shard-readiness-compared-package-review-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedPackageReviewResponse catalog() {
    return response(
        "Java v1034",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedPackageReviewSlotCatalog.allSlots(),
        OpsShardReadinessComparedPackageReviewGuardCatalog.allGuards(),
        OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.allGroups(),
        List.of("compared-package-review-catalog-full"));
  }

  static OpsShardReadinessComparedPackageReviewResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessComparedPackageReviewResponse.ReviewSlot> slots,
      List<OpsShardReadinessComparedPackageReviewResponse.ReviewGuard> guards,
      List<OpsShardReadinessComparedPackageReviewResponse.ReviewerGroup> reviewerGroups,
      List<String> checks) {
    return OpsShardReadinessComparedPackageReviewSupport.response(
        version, endpoint, profile, slots, guards, reviewerGroups, checks);
  }
}
