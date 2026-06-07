package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupportTests {

    @Test
    void buildsDraftReadinessResponseWithoutCreatingManualArtifactDraft() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.response(
                "Java v760",
                "/ops/shard-readiness/draft-readiness-sample",
                "sample.signed-approval-artifact-draft-readiness.v1",
                List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.item(
                        "READINESS_01",
                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REQUEST_ID",
                        "request",
                        "confirm request fragment ownership before draft planning",
                        "request readiness cannot create artifact draft",
                        "OWNERSHIP_01",
                        "java-v759",
                        "request-fragment",
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService
                                .ENDPOINT
                )),
                List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport
                        .ownership("OWNERSHIP_01", "request", "operator-review",
                                "owns request readiness metadata", "metadata-only")),
                List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport.gate(
                        "GATE_01", "draft", "manual artifact draft remains unavailable", "fail-closed")),
                List.of("sample-draft-readiness-check")
        );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.sourcePlan()).isEqualTo("Node v1086");
        assertThat(response.sourceArtifactPreflightVersion()).isEqualTo("Java v759");
        assertThat(response.readyForDraftReadiness()).isTrue();
        assertThat(response.readyForManualArtifactDraft()).isFalse();
        assertThat(response.readyForSignedApprovalCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.readinessItemCount()).isEqualTo(1);
        assertThat(response.passedReadinessItemCount()).isEqualTo(1);
        assertThat(response.ownershipRuleCount()).isEqualTo(1);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-readiness-source-artifact-preflight-Java v759",
                "signed-approval-artifact-draft-readiness-no-manual-artifact-draft",
                "sample-draft-readiness-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
