package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewPolicyArchiveService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_POLICY_ARCHIVE;
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
