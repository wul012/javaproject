package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagereviewpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackageintake.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupportTests {

  @Test
  void buildsReviewPreflightResponseWithoutParsingPackageMaterial() {
    var response =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
            .response(
                "Java v935",
                "/ops/shard-readiness/draft-text-package-review-preflight-sample",
                "sample.signed-approval-artifact-draft-text-package-review-preflight.v1",
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                        .criterion(
                            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REQUEST_MANIFEST_CRITERION",
                            "Node v1237-v1240",
                            "request manifest id matches intake",
                            "is request manifest stable?",
                            "reject missing request manifest id",
                            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeIdentityCorrelationService
                                .ENDPOINT)),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                        .control(
                            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_REQUEST_MANIFEST_CONTROL",
                            "identity",
                            "reject missing request manifest id",
                            "REJECT_DRAFT_TEXT_PACKAGE_REVIEW_REQUEST_MANIFEST",
                            "fail-closed")),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSupport
                        .gate(
                            "DRAFT_TEXT_PACKAGE_REVIEW_PREFLIGHT_GATE_01",
                            "review",
                            "review preflight remains criteria-only",
                            "fail-closed")),
                List.of("sample-review-preflight-check"));

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v935");
    assertThat(response.sourcePlan()).isEqualTo("Node v1261");
    assertThat(response.sourceNodeTextPackageIntakeVersion()).isEqualTo("Node v1236");
    assertThat(response.sourceJavaTextPackageIntakeVersion()).isEqualTo("Java v934");
    assertThat(response.reviewPreflightState()).isEqualTo("criteria-only");
    assertThat(response.draftTextParseState()).isEqualTo("not-parsed");
    assertThat(response.detachedSignatureParseState()).isEqualTo("not-parsed");
    assertThat(response.readyForDraftTextPackageReviewPreflight()).isTrue();
    assertThat(response.readyForDraftTextPackageAcceptance()).isFalse();
    assertThat(response.readyForSignedDraftTextParsing()).isFalse();
    assertThat(response.readyForDetachedSignatureParsing()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.criterionCount()).isEqualTo(1);
    assertThat(response.rejectionControlCount()).isEqualTo(1);
    assertThat(response.gateCount()).isEqualTo(1);
    assertThat(response.checks())
        .contains(
            "signed-approval-artifact-draft-text-package-review-preflight-no-draft-text-parsing",
            "signed-approval-artifact-draft-text-package-review-preflight-no-detached-signature-parsing",
            "sample-review-preflight-check");
    assertThat(response.status()).isEqualTo("passed");
  }
}
