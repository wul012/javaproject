package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EVIDENCE_PACKAGE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-evidence.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
    evidencePackage() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport.response(
                "Java v850",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                        .slots(12, 15),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                        .guards(12, 15),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGateCatalog
                        .gates(6, 7),
                List.of(
                        "signed-approval-artifact-draft-review-package-preflight-evidence-version-slot-ready",
                        "signed-approval-artifact-draft-review-package-preflight-evidence-file-snippet-slots-ready",
                        "signed-approval-artifact-draft-review-package-preflight-no-evidence-import"
                )
        );
    }
}
