package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSourceEvidenceService {

    static final String ENDPOINT = OpsShardReadinessRoutePaths.BASE_PATH + OpsShardReadinessRoutePaths
            .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_SOURCE_EVIDENCE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-review-preflight-source-evidence.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightResponse
    sourceEvidence() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                .response(
                        "Java v950",
                        ENDPOINT,
                        PROFILE,
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCriteriaCatalog
                                .criteria(11, 14),
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightRejectionControlCatalog
                                .controls(11, 14),
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightGateCatalog
                                .gates(11, 18),
                        List.of(
                                "signed-approval-artifact-draft-text-package-review-preflight-source-evidence-by-reference",
                                "signed-approval-artifact-draft-text-package-review-preflight-no-evidence-import"
                        )
                );
    }
}
