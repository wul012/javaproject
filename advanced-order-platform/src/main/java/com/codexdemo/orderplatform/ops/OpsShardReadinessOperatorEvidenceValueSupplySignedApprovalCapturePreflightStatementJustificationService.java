package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightStatementJustificationService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_STATEMENT_JUSTIFICATION;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-statement.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse statement() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport.response(
                "Java v726",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog.inputs(10, 12),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                        .attestations(10, 12),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog.policies(9, 11),
                List.of(
                        "signed-approval-capture-preflight-approval-statement-placeholder",
                        "signed-approval-capture-preflight-operator-justification-mirrored",
                        "signed-approval-capture-preflight-statement-is-not-signed-approval"
                )
        );
    }
}
