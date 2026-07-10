package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewPolicyArchiveService {

  static final String ENDPOINT = OpsShardReadinessComparedPackageReviewRoutePaths.POLICY_ARCHIVE;
  static final String PROFILE = "java-shard-readiness-compared-package-review-policy-archive.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedPackageReviewResponse policyArchive() {
    return OpsShardReadinessComparedPackageReviewCatalogService.response(
        "Java v1038",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedPackageReviewPolicyArchiveSlotCatalog.policyArchiveSlots(),
        OpsShardReadinessComparedPackageReviewGuardCatalog.policyArchiveGuards(),
        OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.policyArchiveGroups(),
        List.of("compared-package-review-policy-archive-only"));
  }
}
