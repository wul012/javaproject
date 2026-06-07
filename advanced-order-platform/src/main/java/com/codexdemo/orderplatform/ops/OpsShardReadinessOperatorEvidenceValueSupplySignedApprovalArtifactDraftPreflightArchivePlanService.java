package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_ARCHIVE_PLAN;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-archive.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse plan() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport.response(
                "Java v803",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                        .fields(20, 25),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                        .guards(20, 25),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog.allGates(),
                List.of(
                        "signed-approval-artifact-draft-preflight-archive-plan-metadata-only",
                        "signed-approval-artifact-draft-preflight-archive-plan-no-file-write",
                        "signed-approval-artifact-draft-preflight-archive-plan-no-process-start",
                        "signed-approval-artifact-draft-preflight-archive-plan-no-manual-draft"
                )
        );
    }
}
