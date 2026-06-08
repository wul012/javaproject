package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicySlotCatalog {

    private OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicySlotCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot> policySlots() {
        return List.of(
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot("policy-execution-lock-document",
                        "policy-execution-lock", "policy assertion, execution lock",
                        "real compared candidate policy execution lock document",
                        "reject-missing-policy-execution-lock-document",
                        OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs.POLICY),
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot("approval-archive-document",
                        "approval-archive-separation", "approval grant separation, archive reference",
                        "real compared candidate approval archive separation document",
                        "reject-missing-approval-archive-document",
                        OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs.POLICY)
        );
    }
}
