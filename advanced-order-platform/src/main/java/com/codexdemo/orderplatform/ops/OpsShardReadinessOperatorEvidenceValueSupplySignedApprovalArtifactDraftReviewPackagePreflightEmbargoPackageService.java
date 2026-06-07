package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EMBARGO_PACKAGE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-embargo.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    embargoPackage() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport.response(
                "Java v852",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                        .slots(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                        .guards(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGateCatalog
                        .gates(9, 19),
                List.of(
                        "signed-approval-artifact-draft-review-package-preflight-raw-secret-embargo-ready",
                        "signed-approval-artifact-draft-review-package-preflight-approval-grant-embargo-ready",
                        "signed-approval-artifact-draft-review-package-preflight-zero-value-import-embargo-ready",
                        "signed-approval-artifact-draft-review-package-preflight-write-route-embargo-ready",
                        "signed-approval-artifact-draft-review-package-preflight-sibling-non-mutation-ready"
                )
        );
    }
}
