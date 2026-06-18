package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadiness.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupportTests {

  @Test
  void buildsDraftPreflightResponseWithoutCreatingManualDraft() {
    var response =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
            .response(
                "Java v785",
                "/ops/shard-readiness/draft-preflight-sample",
                "sample.signed-approval-artifact-draft-preflight.v1",
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
                        .field(
                            "FIELD_01",
                            "ARTIFACT_DRAFT_READINESS_01_REQUEST_ID",
                            "request",
                            "require request id draft field",
                            "request field cannot create manual draft",
                            "ARTIFACT_DRAFT_PREFLIGHT_REQUEST_ID_GUARD",
                            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService
                                .ENDPOINT)),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
                        .guard(
                            "ARTIFACT_DRAFT_PREFLIGHT_REQUEST_ID_GUARD",
                            "request",
                            "guard request id as metadata only",
                            "reject-manual-draft-materialization",
                            "fail-closed")),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
                        .gate(
                            "DRAFT_PREFLIGHT_GATE_01",
                            "draft",
                            "manual draft remains unavailable",
                            "fail-closed")),
                List.of("sample-draft-preflight-check"));

    assertThat(response.sourcePlan()).isEqualTo("Node v1111");
    assertThat(response.sourceArtifactPreflightVersion()).isEqualTo("Node v1086");
    assertThat(response.sourceJavaDraftReadinessVersion()).isEqualTo("Java v784");
    assertThat(response.readyForDraftPreflight()).isTrue();
    assertThat(response.readyForManualDraft()).isFalse();
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForEvidenceImport()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.fieldCount()).isEqualTo(1);
    assertThat(response.guardCount()).isEqualTo(1);
    assertThat(response.gateCount()).isEqualTo(1);
    assertThat(response.checks())
        .contains(
            "signed-approval-artifact-draft-preflight-source-plan-Node v1111",
            "signed-approval-artifact-draft-preflight-no-real-manual-draft",
            "sample-draft-preflight-check");
    assertThat(response.status()).isEqualTo("passed");
  }
}
