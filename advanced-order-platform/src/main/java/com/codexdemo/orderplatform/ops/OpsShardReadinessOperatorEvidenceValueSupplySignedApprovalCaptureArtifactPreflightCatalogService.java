package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse catalog() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport.response(
                "Java v744",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                        .allFragments(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                        .allSeals(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                        .allGates(),
                List.of(
                        "signed-approval-capture-artifact-preflight-catalog-fragment-count-25",
                        "signed-approval-capture-artifact-preflight-catalog-seal-count-25",
                        "signed-approval-capture-artifact-preflight-catalog-gate-count-20",
                        "signed-approval-capture-artifact-preflight-catalog-derived-from-node-v1086",
                        "signed-approval-capture-artifact-preflight-catalog-no-artifact-draft"
                )
        );
    }
}
