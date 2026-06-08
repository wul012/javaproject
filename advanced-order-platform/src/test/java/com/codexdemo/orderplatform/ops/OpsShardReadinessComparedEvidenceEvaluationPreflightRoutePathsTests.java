package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceEvaluationPreflightRoutePathsTests {

    @Test
    void evaluationPreflightRoutesRemainReadOnlyPreflightSurfaces() {
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_CATALOG)
                .endsWith("compared-evidence-evaluation-preflight-catalog");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_SOURCE_ARTIFACT)
                .endsWith("compared-evidence-evaluation-preflight-source-artifact");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_IDENTITY_DIGEST)
                .endsWith("compared-evidence-evaluation-preflight-identity-digest");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_POLICY_RUNTIME)
                .endsWith("compared-evidence-evaluation-preflight-policy-runtime");
        assertThat(OpsShardReadinessRoutePaths
                .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_EVIDENCE_EVALUATION_PREFLIGHT_EXCLUSION_CLOSEOUT)
                .endsWith("compared-evidence-evaluation-preflight-exclusion-closeout");
    }
}
