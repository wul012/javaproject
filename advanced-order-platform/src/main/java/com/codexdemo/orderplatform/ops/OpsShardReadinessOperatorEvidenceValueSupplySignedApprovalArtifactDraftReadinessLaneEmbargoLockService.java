package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EMBARGO_LOCKS;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-lane-locks.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse embargoLocks() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport.response(
                "Java v827",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog
                        .lanes(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                        .blockers(19, 24),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog
                        .gates(9, 19),
                List.of(
                        "signed-approval-artifact-draft-readiness-lane-raw-secret-embargo-ready",
                        "signed-approval-artifact-draft-readiness-lane-approval-grant-embargo-ready",
                        "signed-approval-artifact-draft-readiness-lane-zero-value-import-embargo-ready",
                        "signed-approval-artifact-draft-readiness-lane-write-route-embargo-ready",
                        "signed-approval-artifact-draft-readiness-lane-sibling-non-mutation-ready"
                )
        );
    }
}
