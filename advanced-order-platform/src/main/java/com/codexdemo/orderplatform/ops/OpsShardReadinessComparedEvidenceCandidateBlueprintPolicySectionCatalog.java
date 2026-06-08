package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessComparedEvidenceCandidateBlueprintPolicySectionCatalog {

    private OpsShardReadinessComparedEvidenceCandidateBlueprintPolicySectionCatalog() {
    }

    static List<OpsShardReadinessComparedEvidenceCandidateBlueprintResponse.CandidateSection> policySections() {
        return List.of(
                OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section("policy-execution-lock",
                        "Node v1358", "policy",
                        "policy assertion, execution lock",
                        "policy lock reviewer",
                        OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs.POLICY_RUNTIME,
                        "block-missing-policy-execution-lock"),
                OpsShardReadinessComparedEvidenceCandidateBlueprintSupport.section("approval-archive-separation",
                        "Node v1359", "policy",
                        "approval grant separation, archive reference",
                        "policy lock reviewer",
                        OpsShardReadinessComparedEvidenceEvaluationPreflightEndpointRefs.POLICY_RUNTIME,
                        "block-missing-approval-archive-separation")
        );
    }
}
