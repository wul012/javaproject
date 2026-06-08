package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutSlotCatalog {

    private OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutSlotCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceCandidateIntakePreflightResponse.IntakeSlot> closeoutSlots() {
        return List.of(
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot("exclusion-boundary-document",
                        "exclusion-boundary", "secret, synthetic, runtime, write, and sibling mutation exclusions",
                        "real compared candidate exclusion boundary document",
                        "reject-missing-exclusion-boundary-document",
                        OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs.CLOSEOUT),
                OpsShardReadinessComparedEvidenceCandidateIntakePreflightSupport.slot("candidate-closeout-document",
                        "candidate-blueprint-closeout", "reviewer traceability, candidate blueprint closeout",
                        "real compared candidate closeout document",
                        "reject-missing-candidate-closeout-document",
                        OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs.CLOSEOUT)
        );
    }
}
