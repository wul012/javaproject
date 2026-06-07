package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_CLOSEOUT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse closeout() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.response(
                "Java v779",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog.allItems(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                        .allOwnershipRules(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog.allGates(),
                List.of(
                        "signed-approval-artifact-draft-readiness-closeout-versions-v760-v784",
                        "signed-approval-artifact-draft-readiness-closeout-item-count-25",
                        "signed-approval-artifact-draft-readiness-closeout-ownership-count-20",
                        "signed-approval-artifact-draft-readiness-closeout-gate-count-20",
                        "signed-approval-artifact-draft-readiness-closeout-source-node-v1086",
                        "signed-approval-artifact-draft-readiness-closeout-source-java-v759",
                        "signed-approval-artifact-draft-readiness-closeout-no-manual-artifact-draft",
                        "signed-approval-artifact-draft-readiness-closeout-no-capture-grant-value-import-runtime",
                        "signed-approval-artifact-draft-readiness-closeout-no-sibling-mutation",
                        "signed-approval-artifact-draft-readiness-closeout-next-step-needs-explicit-plan"
                )
        );
    }
}
