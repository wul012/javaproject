package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessOperatorRequirementService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_OPERATOR_REQUIREMENTS;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-operator-requirements.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
    operatorRequirements() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport.response(
                "Java v873",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                        .requirements(4, 8),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                        .blockers(4, 8),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessGateCatalog
                        .gates(2, 4),
                List.of(
                        "signed-approval-artifact-draft-authoring-readiness-operator-identity-role-window-channel",
                        "signed-approval-artifact-draft-authoring-readiness-operator-no-credential-capture"
                )
        );
    }
}
