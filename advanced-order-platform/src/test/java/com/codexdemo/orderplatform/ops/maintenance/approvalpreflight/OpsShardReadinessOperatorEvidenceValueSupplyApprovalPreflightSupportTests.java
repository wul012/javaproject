package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupportTests {

  @Test
  void buildsDraftApprovalPreflightResponseWithoutCapturingApprovalOrValues() {
    OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse response =
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
            "Java v685",
            "/api/v1/ops/shard-readiness/operator-evidence-value-supply-approval-preflight-example",
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-example.v1",
            List.of(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.item(
                    "VALUE_SUPPLY_APPROVAL_PACKET_EXAMPLE",
                    "VALUE_SUPPLY_01_ENVELOPE_ID",
                    "identity",
                    "Example approval packet metadata is shaped without capture.",
                    "approval capture remains locked",
                    "java-v658-value-supply-closeout",
                    "adapter-preflight-closeout-locks",
                    OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService
                        .ENDPOINT)),
            List.of(
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.policy(
                    "APPROVAL_POLICY_EXAMPLE",
                    "approval",
                    "Signed approval is required later, but this preflight cannot capture it.",
                    "fail-closed")),
            List.of("approval-preflight-example-check"));

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v685");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForApprovalPreflight()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v986");
    assertThat(response.sourceEnvelopeVersion()).isEqualTo("Node v961");
    assertThat(response.sourceValueSupplyVersion()).isEqualTo("Java v658");
    assertThat(response.sourceAdapterPreflightVersion()).isEqualTo("Java v684");
    assertThat(response.approvalPacketState()).isEqualTo("draft-preflight");
    assertThat(response.approvalCaptureState()).isEqualTo("not-captured");
    assertThat(response.acceptedValueState()).isEqualTo("not-accepted");
    assertThat(response.importState()).isEqualTo("locked");
    assertThat(response.redactionDigestState()).isEqualTo("required-before-capture");
    assertThat(response.provenanceState()).isEqualTo("required-before-import");
    assertThat(response.malformedValueState()).isEqualTo("rejected");
    assertThat(response.receiptState()).isEqualTo("required-before-import");
    assertThat(response.readyForSignedApprovalCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForOperatorValueSubmission()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.readyForLiveExecution()).isFalse();
    assertThat(response.readyForProductionExecution()).isFalse();
    assertThat(response.itemCount()).isEqualTo(1);
    assertThat(response.passedItemCount()).isEqualTo(1);
    assertThat(response.policyCount()).isEqualTo(1);
    assertThat(response.items().get(0).evidenceFileId())
        .isEqualTo("java-v658-value-supply-closeout");
    assertThat(response.policies().get(0).enforcement()).isEqualTo("fail-closed");
    assertThat(response.checks())
        .contains(
            "value-supply-approval-preflight-source-plan-Node v986",
            "value-supply-approval-preflight-source-envelope-Node v961",
            "value-supply-approval-preflight-source-adapter-preflight-Java v684",
            "value-supply-approval-preflight-approval-not-captured",
            "value-supply-approval-preflight-approval-grant-locked",
            "value-supply-approval-preflight-values-not-accepted",
            "value-supply-approval-preflight-import-locked",
            "approval-preflight-example-check");
    assertThat(response.status()).isEqualTo("passed");
  }
}
