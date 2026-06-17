package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightIdentitySignatureService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IDENTITY_SIGNATURE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-identity-signature.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse signature() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v690",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(0, 5),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(0, 5),
                List.of(
                        "value-supply-approval-preflight-identity-signature-slice-1-5",
                        "value-supply-approval-preflight-identity-operator-alias-only",
                        "value-supply-approval-preflight-identity-reviewer-role-required",
                        "value-supply-approval-preflight-signature-human-policy-required",
                        "value-supply-approval-preflight-signature-capture-locked",
                        "value-supply-approval-preflight-signature-grant-locked"
                )
        );
    }
}
