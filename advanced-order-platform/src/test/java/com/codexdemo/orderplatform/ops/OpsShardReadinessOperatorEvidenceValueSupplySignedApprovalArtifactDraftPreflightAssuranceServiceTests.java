package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceServiceTests {

    @Test
    void exposesEvidenceSourceWithoutImport() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService()
                        .evidenceSource();

        assertThat(response.version()).isEqualTo("Java v800");
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(3);
        assertThat(response.guardCount()).isEqualTo(3);
        assertThat(response.gateCount()).isEqualTo(1);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-preflight-evidence-source-no-evidence-import");
    }

    @Test
    void exposesRedactionProvenanceWithoutValueBody() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService()
                        .redactionProvenance();

        assertThat(response.version()).isEqualTo("Java v801");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(4);
        assertThat(response.guardCount()).isEqualTo(4);
        assertThat(response.gateCount()).isEqualTo(2);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-preflight-value-shape-no-value-body",
                "signed-approval-artifact-draft-preflight-redaction-provenance-no-import");
    }

    @Test
    void exposesFailClosedLocksWithoutRuntimeOrSiblingMutation() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService()
                        .locks();

        assertThat(response.version()).isEqualTo("Java v802");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(5);
        assertThat(response.guardCount()).isEqualTo(5);
        assertThat(response.gateCount()).isEqualTo(10);
    }

    @Test
    void exposesArchivePlanWithoutFileWrites() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService()
                        .plan();

        assertThat(response.version()).isEqualTo("Java v803");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-preflight-archive-plan-metadata-only",
                "signed-approval-artifact-draft-preflight-archive-plan-no-file-write",
                "signed-approval-artifact-draft-preflight-archive-plan-no-process-start");
    }

    @Test
    void closesOutPreflightWithNextStepExplicitPlanBoundary() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService()
                        .closeout();

        assertThat(response.version()).isEqualTo("Java v804");
        assertThat(response.readyForManualDraft()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.fieldCount()).isEqualTo(25);
        assertThat(response.guardCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.checks()).contains(
                "signed-approval-artifact-draft-preflight-closeout-versions-v785-v809",
                "signed-approval-artifact-draft-preflight-closeout-source-node-v1111",
                "signed-approval-artifact-draft-preflight-closeout-source-artifact-preflight-node-v1086",
                "signed-approval-artifact-draft-preflight-closeout-source-java-readiness-v784",
                "signed-approval-artifact-draft-preflight-closeout-no-real-manual-draft",
                "signed-approval-artifact-draft-preflight-closeout-next-step-requires-explicit-manual-draft-plan"
        );
    }
}
