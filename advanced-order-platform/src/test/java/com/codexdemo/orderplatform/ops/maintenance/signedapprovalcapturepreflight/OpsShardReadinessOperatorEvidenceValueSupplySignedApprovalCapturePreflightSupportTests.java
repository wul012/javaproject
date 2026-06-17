package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupportTests {

  @Test
  void buildsDerivedCapturePreflightResponseWithoutCapturingApprovalOrGrantingValues() {
    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse response =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport.response(
            "Java v710",
            "/ops/shard-readiness/sample",
            "sample.capture-preflight.v1",
            List.of(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
                    .input(
                        "INPUT_01",
                        "request-id",
                        "identity",
                        "require request id metadata",
                        "request id cannot create capture",
                        "node-v1061",
                        "request-id",
                        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService
                            .ENDPOINT)),
            List.of(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
                    .attestation(
                        "ATTEST_01",
                        "identity",
                        "request id remains metadata only",
                        "fail-closed")),
            List.of(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
                    .policy(
                        "POLICY_01",
                        "identity",
                        "request id cannot grant approval",
                        "fail-closed")),
            List.of("sample-check"));

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.sourcePlan()).isEqualTo("Node v1061");
    assertThat(response.sourceTemplateVersion()).isEqualTo("Node v1036");
    assertThat(response.sourceApprovalPacketReviewVersion()).isEqualTo("Node v1011");
    assertThat(response.sourceApprovalPreflightVersion()).isEqualTo("Java v709");
    assertThat(response.readyForCapturePreflight()).isTrue();
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.inputCount()).isEqualTo(1);
    assertThat(response.passedInputCount()).isEqualTo(1);
    assertThat(response.attestationCount()).isEqualTo(1);
    assertThat(response.passedAttestationCount()).isEqualTo(1);
    assertThat(response.policyCount()).isEqualTo(1);
    assertThat(response.checks())
        .contains(
            "signed-approval-capture-preflight-source-plan-Node v1061",
            "signed-approval-capture-preflight-no-signed-approval-capture",
            "sample-check");
    assertThat(response.status()).isEqualTo("passed");
  }
}
