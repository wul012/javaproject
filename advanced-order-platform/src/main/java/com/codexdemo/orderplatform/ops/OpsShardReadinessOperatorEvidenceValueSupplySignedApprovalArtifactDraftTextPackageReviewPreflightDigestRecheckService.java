package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightDigestRecheckService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_DIGEST_RECHECK;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-review-preflight-digest-recheck.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
    digestRecheck() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                .response(
                        "Java v948",
                        ENDPOINT,
                        PROFILE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog
                                .criteria(4, 8),
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
                                .controls(4, 8),
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightGateCatalog
                                .gates(9, 16),
                        List.of(
                                "signed-approval-artifact-draft-text-package-review-preflight-digest-recheck-controls",
                                "signed-approval-artifact-draft-text-package-review-preflight-digest-no-parsing"
                        )
                );
    }
}
