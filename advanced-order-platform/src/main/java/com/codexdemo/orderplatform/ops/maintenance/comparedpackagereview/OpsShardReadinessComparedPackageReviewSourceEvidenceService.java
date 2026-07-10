package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewSourceEvidenceService {

  static final String ENDPOINT = OpsShardReadinessComparedPackageReviewRoutePaths.SOURCE_EVIDENCE;
  static final String PROFILE = "java-shard-readiness-compared-package-review-source-evidence.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedPackageReviewResponse sourceEvidence() {
    return OpsShardReadinessComparedPackageReviewCatalogService.response(
        "Java v1035",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedPackageReviewSourceEvidenceSlotCatalog.sourceEvidenceSlots(),
        OpsShardReadinessComparedPackageReviewGuardCatalog.sourceGuards(),
        OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.sourceGroups(),
        List.of("compared-package-review-source-evidence-only"));
  }
}
