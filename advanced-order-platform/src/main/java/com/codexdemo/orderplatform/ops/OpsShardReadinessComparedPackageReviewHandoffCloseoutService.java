package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessComparedPackageReviewHandoffCloseoutService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_REVIEW_HANDOFF_CLOSEOUT;
    static final String PROFILE = "java-shard-readiness-compared-package-review-handoff-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessComparedPackageReviewResponse handoffCloseout() {
        return OpsShardReadinessComparedPackageReviewCatalogService.response(
                "Java v1039",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessComparedPackageReviewSlotCatalog.allSlots(),
                OpsShardReadinessComparedPackageReviewGuardCatalog.allGuards(),
                OpsShardReadinessComparedPackageReviewReviewerGroupCatalog.allGroups(),
                List.of("compared-package-review-handoff-closeout",
                        "compared-package-review-handoff-ready-for-human-review-only"));
    }
}
