package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupportTests {

    @Test
    void buildsAuthoringReadinessResponseWithoutCreatingDraftText() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
                .response(
                        "Java v860",
                        "/ops/shard-readiness/draft-authoring-readiness-sample",
                        "sample.signed-approval-artifact-draft-authoring-readiness.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
                                .requirement(
                                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST",
                                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_SLOT",
                                        "draftArtifactRequestId",
                                        "bind reviewed request manifest into authoring readiness",
                                        "readiness cannot materialize instructions",
                                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST_BLOCKER",
                                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCatalogService
                                                .ENDPOINT)),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
                                .blocker(
                                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST_BLOCKER",
                                        "request",
                                        "reject missing reviewed request manifest",
                                        "REJECT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST_MISSING",
                                        "fail-closed")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
                                .gate(
                                        "DRAFT_AUTHORING_READINESS_GATE_01",
                                        "readiness",
                                        "authoring readiness remains metadata-only",
                                        "fail-closed")),
                        List.of("sample-authoring-readiness-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v860");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForAuthoringReadiness()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1186");
        assertThat(response.sourceNodeReviewPackagePreflightVersion()).isEqualTo("Node v1161");
        assertThat(response.sourceJavaReviewPackagePreflightVersion()).isEqualTo("Java v859");
        assertThat(response.authoringReadinessState()).isEqualTo("requirement-map-only");
        assertThat(response.authoringArtifactState()).isEqualTo("not-created");
        assertThat(response.readyForHumanDraftAuthoring()).isFalse();
        assertThat(response.readyForSignedDraftText()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.requirementCount()).isEqualTo(1);
        assertThat(response.blockerCount()).isEqualTo(1);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "sample-authoring-readiness-check",
                "signed-approval-artifact-draft-authoring-readiness-no-signed-draft-text",
                "signed-approval-artifact-draft-authoring-readiness-no-runtime-or-sibling-mutation"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
