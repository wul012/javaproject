package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_FAIL_CLOSED_LOCKS;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-locks.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse locks() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.response(
                "Java v777",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog.items(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                        .ownershipRules(7, 18),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog.gates(9, 19),
                List.of(
                        "signed-approval-artifact-draft-readiness-no-raw-secret-ready",
                        "signed-approval-artifact-draft-readiness-no-grant-ready",
                        "signed-approval-artifact-draft-readiness-zero-value-import-ready",
                        "signed-approval-artifact-draft-readiness-no-write-route-ready",
                        "signed-approval-artifact-draft-readiness-no-sibling-mutation-ready"
                )
        );
    }
}
