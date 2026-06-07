package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceServiceTests {

    @Test
    void exposesEvidenceReviewWithoutImport() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService()
                        .evidenceReview();

        assertThat(response.version()).isEqualTo("Java v825");
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.laneCount()).isEqualTo(3);
        assertThat(response.blockerCount()).isEqualTo(3);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-readiness-lane-no-evidence-import");
    }

    @Test
    void exposesValueRedactionWithoutValueBody() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneValueRedactionService()
                        .valueRedaction();

        assertThat(response.version()).isEqualTo("Java v826");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.laneCount()).isEqualTo(4);
        assertThat(response.blockerCount()).isEqualTo(4);
        assertThat(response.gateCount()).isEqualTo(2);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-readiness-lane-no-value-body");
    }

    @Test
    void exposesEmbargoLocksWithoutRuntimeOrSiblingMutation() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEmbargoLockService()
                        .embargoLocks();

        assertThat(response.version()).isEqualTo("Java v827");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.laneCount()).isEqualTo(5);
        assertThat(response.blockerCount()).isEqualTo(5);
        assertThat(response.gateCount()).isEqualTo(10);
    }

    @Test
    void exposesManualPackageGateWithoutAuthoringPackage() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneManualPackageGateService()
                        .manualPackageGate();

        assertThat(response.version()).isEqualTo("Java v828");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.manualPackageState()).isEqualTo("not-authored");
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-no-draft-authoring",
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-no-file-write",
                "signed-approval-artifact-draft-readiness-lane-manual-package-gate-no-process-start");
    }

    @Test
    void closesOutReadinessLaneWithExplicitManualPackageBoundary() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService()
                        .closeout();

        assertThat(response.version()).isEqualTo("Java v829");
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.laneCount()).isEqualTo(25);
        assertThat(response.blockerCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-readiness-lane-closeout-versions-v810-v834",
                "signed-approval-artifact-draft-readiness-lane-closeout-source-node-v1136",
                "signed-approval-artifact-draft-readiness-lane-closeout-source-node-preflight-v1111",
                "signed-approval-artifact-draft-readiness-lane-closeout-source-java-preflight-v809",
                "signed-approval-artifact-draft-readiness-lane-closeout-no-manual-package-authoring",
                "signed-approval-artifact-draft-readiness-lane-closeout-next-step-explicit-manual-package-plan"
        );
    }
}
