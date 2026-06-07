package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_REDACTION_PROVENANCE;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-redaction.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
    redactionProvenance() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport.response(
                "Java v801",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                        .fields(15, 19),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                        .guards(15, 19),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                        .gates(7, 9),
                List.of(
                        "signed-approval-artifact-draft-preflight-redacted-value-digest-ready",
                        "signed-approval-artifact-draft-preflight-value-shape-no-value-body",
                        "signed-approval-artifact-draft-preflight-redaction-provenance-no-import"
                )
        );
    }
}
