package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupportTests {

  @Test
  void buildsInstructionPreflightResponseWithoutCreatingDraftText() {
    var response =
        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
            .response(
                "Java v885",
                "/ops/shard-readiness/draft-instruction-preflight-sample",
                "sample.signed-approval-artifact-draft-instruction-preflight.v1",
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
                        .slot(
                            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REQUEST_MANIFEST_SLOT",
                            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_AUTHORING_READINESS_REQUEST_MANIFEST",
                            "future draft must cite request manifest",
                            "prepare request manifest instruction slot",
                            "slot cannot materialize draft text",
                            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REQUEST_MANIFEST_SLOT_GUARD",
                            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCatalogService
                                .ENDPOINT)),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
                        .guard(
                            "SIGNED_APPROVAL_CAPTURE_ARTIFACT_DRAFT_INSTRUCTION_PREFLIGHT_REQUEST_MANIFEST_SLOT_GUARD",
                            "request",
                            "reject missing request manifest instruction slot",
                            "REJECT_DRAFT_INSTRUCTION_PREFLIGHT_REQUEST_MANIFEST_MISSING",
                            "fail-closed")),
                List.of(
                    OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSupport
                        .gate(
                            "DRAFT_INSTRUCTION_PREFLIGHT_GATE_01",
                            "preflight",
                            "instruction preflight remains slot-map-only",
                            "fail-closed")),
                List.of("sample-instruction-preflight-check"));

    assertThat(response.project()).isEqualTo("advanced-order-platform");
    assertThat(response.version()).isEqualTo("Java v885");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.readyForInstructionPreflight()).isTrue();
    assertThat(response.sourcePlan()).isEqualTo("Node v1211");
    assertThat(response.sourceNodeAuthoringReadinessVersion()).isEqualTo("Node v1186");
    assertThat(response.sourceJavaAuthoringReadinessVersion()).isEqualTo("Java v884");
    assertThat(response.instructionPreflightState()).isEqualTo("slot-map-only");
    assertThat(response.instructionArtifactState()).isEqualTo("not-created");
    assertThat(response.readyForDraftTextPackage()).isFalse();
    assertThat(response.readyForSignedDraftText()).isFalse();
    assertThat(response.readyForSignatureCapture()).isFalse();
    assertThat(response.readyForApprovalGrant()).isFalse();
    assertThat(response.readyForRuntimePayload()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
    assertThat(response.slotCount()).isEqualTo(1);
    assertThat(response.guardCount()).isEqualTo(1);
    assertThat(response.gateCount()).isEqualTo(1);
    assertThat(response.checks())
        .contains(
            "sample-instruction-preflight-check",
            "signed-approval-artifact-draft-instruction-preflight-no-signed-draft-text",
            "signed-approval-artifact-draft-instruction-preflight-no-runtime-or-sibling-mutation");
    assertThat(response.status()).isEqualTo("passed");
  }
}
