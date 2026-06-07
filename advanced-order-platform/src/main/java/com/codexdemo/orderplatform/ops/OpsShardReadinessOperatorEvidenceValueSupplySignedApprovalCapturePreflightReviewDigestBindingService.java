package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightReviewDigestBindingService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REVIEW_DIGEST;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-review-digest.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse binding() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport.response(
                "Java v718",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog.inputs(2, 3),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                        .attestations(2, 3),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog.policies(2, 3),
                List.of(
                        "signed-approval-capture-preflight-review-digest-bound",
                        "signed-approval-capture-preflight-review-version-node-v1011",
                        "signed-approval-capture-preflight-review-digest-no-approval-grant"
                )
        );
    }
}
