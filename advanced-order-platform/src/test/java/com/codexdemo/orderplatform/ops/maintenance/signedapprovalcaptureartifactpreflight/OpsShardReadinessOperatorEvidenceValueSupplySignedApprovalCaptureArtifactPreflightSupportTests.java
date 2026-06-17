package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupportTests {

  @Test
  void buildsArtifactPreflightResponseWithoutMaterializingArtifactOrGrant() {
    var response =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
            .response(
                "Java v735",
                "/ops/shard-readiness/artifact-sample",
                "sample.artifact-preflight.v1",
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
                        .fragment(
                            "FRAGMENT_01",
                            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_REQUEST_ID",
                            "request",
                            "require artifact preflight request id fragment",
                            "request fragment cannot materialize artifact",
                            "ARTIFACT_PREFLIGHT_REQUEST_ID_SEAL",
                            "node-v1086",
                            "request-id-fragment",
                            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService
                                .ENDPOINT)),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
                        .seal(
                            "ARTIFACT_PREFLIGHT_REQUEST_ID_SEAL",
                            "request",
                            "seal request id as metadata only",
                            "reject-artifact-materialization",
                            "fail-closed")),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
                        .gate(
                            "ARTIFACT_GATE_01",
                            "materialization",
                            "artifact draft remains unavailable",
                            "fail-closed")),
                List.of("sample-artifact-check"));

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.sourcePlan()).isEqualTo("Node v1086");
    assertThat(response.sourceCapturePreflightVersion()).isEqualTo("Node v1061");
    assertThat(response.sourceJavaCapturePreflightVersion()).isEqualTo("Java v734");
    assertThat(response.readyForArtifactPreflight()).isTrue();
    assertThat(response.readyForArtifactDraft()).isFalse();
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.fragmentCount()).isEqualTo(1);
    assertThat(response.passedFragmentCount()).isEqualTo(1);
    assertThat(response.sealCount()).isEqualTo(1);
    assertThat(response.passedSealCount()).isEqualTo(1);
    assertThat(response.gateCount()).isEqualTo(1);
    assertThat(response.checks())
        .contains(
            "signed-approval-capture-artifact-preflight-source-plan-Node v1086",
            "signed-approval-capture-artifact-preflight-no-artifact-materialization",
            "sample-artifact-check");
    assertThat(response.status()).isEqualTo("passed");
  }
}
