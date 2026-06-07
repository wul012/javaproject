package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupportTests {

    @Test
    void buildsReadinessLaneResponseWithoutAuthoringManualPackage() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
                .response(
                        "Java v810",
                        "/ops/shard-readiness/readiness-lane-sample",
                        "sample.signed-approval-artifact-draft-readiness-lane.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
                                .lane(
                                        "LANE_01",
                                        "draftArtifactRequestId",
                                        "review request manifest before manual package authoring",
                                        "manual package cannot be authored from readiness lane",
                                        "DRAFT_READINESS_LANE_REQUEST_MANIFEST_BLOCKER",
                                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService
                                                .ENDPOINT)),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
                                .blocker(
                                        "DRAFT_READINESS_LANE_REQUEST_MANIFEST_BLOCKER",
                                        "request",
                                        "block missing request manifest",
                                        "REJECT_DRAFT_READINESS_REQUEST_MANIFEST_MISSING",
                                        "fail-closed")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
                                .gate(
                                        "DRAFT_READINESS_LANE_GATE_01",
                                        "manual-package",
                                        "manual draft package remains unavailable",
                                        "fail-closed")),
                        List.of("sample-readiness-lane-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v810");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForReadinessLaneCloseout()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1136");
        assertThat(response.sourceNodeDraftPreflightVersion()).isEqualTo("Node v1111");
        assertThat(response.sourceJavaDraftPreflightVersion()).isEqualTo("Java v809");
        assertThat(response.manualPackageState()).isEqualTo("not-authored");
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.laneCount()).isEqualTo(1);
        assertThat(response.blockerCount()).isEqualTo(1);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "sample-readiness-lane-check",
                "signed-approval-artifact-draft-readiness-lane-no-manual-package-authoring",
                "signed-approval-artifact-draft-readiness-lane-no-runtime-or-sibling-mutation"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
