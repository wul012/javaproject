package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupportTests {

    @Test
    void buildsReviewPackagePreflightResponseWithoutCreatingDraftArtifact() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
                .response(
                        "Java v835",
                        "/ops/shard-readiness/review-package-preflight-sample",
                        "sample.signed-approval-artifact-draft-review-package-preflight.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
                                .slot(
                                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_SLOT",
                                        "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_READINESS_REQUEST_MANIFEST",
                                        "draftArtifactRequestId",
                                        "bind request manifest into review package preflight",
                                        "slot cannot create review artifact",
                                        "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_GUARD",
                                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogService
                                                .ENDPOINT)),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
                                .guard(
                                        "ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_GUARD",
                                        "request",
                                        "guard request manifest package slot",
                                        "REJECT_REVIEW_PACKAGE_PREFLIGHT_REQUEST_MANIFEST_MISSING",
                                        "fail-closed")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
                                .gate(
                                        "REVIEW_PACKAGE_PREFLIGHT_GATE_01",
                                        "package",
                                        "review package remains unmaterialized",
                                        "fail-closed")),
                        List.of("sample-review-package-preflight-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v835");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForReviewPackagePreflight()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1161");
        assertThat(response.sourceNodeReadinessLaneVersion()).isEqualTo("Node v1136");
        assertThat(response.sourceJavaReadinessLaneVersion()).isEqualTo("Java v834");
        assertThat(response.reviewPackageState()).isEqualTo("slot-map-only");
        assertThat(response.reviewArtifactState()).isEqualTo("not-created");
        assertThat(response.readyForHumanDraftAuthoring()).isFalse();
        assertThat(response.readyForSignedDraftText()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.slotCount()).isEqualTo(1);
        assertThat(response.guardCount()).isEqualTo(1);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "sample-review-package-preflight-check",
                "signed-approval-artifact-draft-review-package-preflight-no-review-artifact-creation",
                "signed-approval-artifact-draft-review-package-preflight-no-runtime-or-sibling-mutation"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
