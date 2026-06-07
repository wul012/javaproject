package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_SIGNATURE_STATEMENT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-signature.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse signatureStatement() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.response(
                "Java v774",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessItemCatalog.items(8, 13),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOwnershipCatalog
                        .ownershipRules(4, 6),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog.gates(4, 7),
                List.of(
                        "signed-approval-artifact-draft-readiness-signature-policy-ready",
                        "signed-approval-artifact-draft-readiness-statement-placeholder-ready",
                        "signed-approval-artifact-draft-readiness-no-signature-material-or-signed-text"
                )
        );
    }
}
