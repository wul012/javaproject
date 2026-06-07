package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightStatementEvidenceService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_STATEMENT_EVIDENCE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-statement-evidence.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse evidence() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport.response(
                "Java v750",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                        .fragments(11, 15),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                        .seals(11, 15),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                        .gates(10, 12),
                List.of(
                        "signed-approval-capture-artifact-preflight-statement-digest-placeholder-sealed",
                        "signed-approval-capture-artifact-preflight-source-version-sealed",
                        "signed-approval-capture-artifact-preflight-source-file-sealed",
                        "signed-approval-capture-artifact-preflight-source-snippet-sealed",
                        "signed-approval-capture-artifact-preflight-evidence-not-imported"
                )
        );
    }
}
