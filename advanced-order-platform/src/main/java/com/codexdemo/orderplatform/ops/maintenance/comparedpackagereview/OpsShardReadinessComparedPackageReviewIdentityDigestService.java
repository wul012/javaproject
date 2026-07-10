package com.codexdemo.orderplatform.ops.maintenance.comparedpackagereview;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewIdentityDigestService {

  static final String ENDPOINT = OpsShardReadinessComparedPackageReviewRoutePaths.IDENTITY_DIGEST;
  static final String PROFILE = "java-shard-readiness-compared-package-review-identity-digest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessComparedPackageReviewResponse identityDigest() {
    return OpsShardReadinessComparedPackageReviewCatalogService.response(
        "Java v1037",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessComparedPackageReviewIdentityDigestSlotCatalog.identityDigestSlots(),
        OpsShardReadinessComparedPackageReviewGuardCatalog.identityDigestGuards(),
        OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.identityDigestGroups(),
        List.of("compared-package-review-identity-digest-only"));
  }
}
