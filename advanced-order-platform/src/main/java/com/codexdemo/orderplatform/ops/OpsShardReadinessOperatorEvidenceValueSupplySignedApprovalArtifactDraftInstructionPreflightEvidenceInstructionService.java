package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths
                    .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_EVIDENCE_INSTRUCTIONS;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-instruction-preflight-evidence-instructions.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightResponse
    evidenceInstructions() {
        return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport.response(
                "Java v900",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSlotCatalog
                        .slots(12, 15),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGuardCatalog
                        .guards(12, 15),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightGateCatalog
                        .gates(6, 8),
                List.of("signed-approval-artifact-draft-instruction-preflight-evidence-instructions-no-import")
        );
    }
}
