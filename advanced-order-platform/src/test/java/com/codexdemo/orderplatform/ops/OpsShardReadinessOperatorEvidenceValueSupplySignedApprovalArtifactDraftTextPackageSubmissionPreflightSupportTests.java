package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupportTests {

    @Test
    void buildsSubmissionPreflightResponseWithoutAcceptingSubmittedMaterial() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
                .response(
                        "Java v960",
                        "/ops/shard-readiness/submission-preflight-sample",
                        "sample.submission-preflight.v1",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
                                .slot(
                                        "DRAFT_TEXT_PACKAGE_SUBMISSION_REQUEST_MANIFEST_SLOT",
                                        "Node v1262-v1265",
                                        "requestManifestId",
                                        "compare request manifest id",
                                        "compare only",
                                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightCatalogService
                                                .ENDPOINT)),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
                                .control(
                                        "SUBMISSION_REQUEST_MANIFEST_CONTROL",
                                        "identity",
                                        "reject missing request manifest",
                                        "REJECT_DRAFT_TEXT_PACKAGE_SUBMISSION_REQUEST_MANIFEST_CONTROL")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSupport
                                .gate("SUBMISSION_PREFLIGHT_GATE_01", "submission",
                                        "submission remains slots-only")),
                        List.of("sample-submission-preflight-check")
                );

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v960");
        assertThat(response.sourcePlan()).isEqualTo("Node v1286");
        assertThat(response.sourceNodeReviewPreflightVersion()).isEqualTo("Node v1261");
        assertThat(response.sourceJavaReviewPreflightVersion()).isEqualTo("Java v959");
        assertThat(response.submissionPreflightState()).isEqualTo("slots-only");
        assertThat(response.submittedPackageState()).isEqualTo("not-accepted");
        assertThat(response.draftTextParseState()).isEqualTo("not-parsed");
        assertThat(response.detachedSignatureParseState()).isEqualTo("not-parsed");
        assertThat(response.readyForDraftTextPackageSubmissionPreflight()).isTrue();
        assertThat(response.readyForSubmittedPackageAcceptance()).isFalse();
        assertThat(response.readyForSignedDraftTextParsing()).isFalse();
        assertThat(response.readyForDetachedSignatureParsing()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.slotCount()).isEqualTo(1);
        assertThat(response.comparisonControlCount()).isEqualTo(1);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-text-package-submission-preflight-no-package-acceptance",
                "signed-approval-artifact-draft-text-package-submission-preflight-no-draft-text-parsing",
                "sample-submission-preflight-check"
        );
        assertThat(response.status()).isEqualTo("passed");
    }
}
