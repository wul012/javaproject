package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceServiceTests {

    @Test
    void exposesEvidenceRequirementsWithoutEvidenceImport() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEvidenceRequirementService()
                        .evidenceRequirements();

        assertThat(response.version()).isEqualTo("Java v875");
        assertThat(response.readyForEvidenceImport()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.requirementCount()).isEqualTo(3);
        assertThat(response.blockerCount()).isEqualTo(3);
    }

    @Test
    void exposesValuePolicyRequirementsWithoutRawValues() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService()
                        .valuePolicyRequirements();

        assertThat(response.version()).isEqualTo("Java v876");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
        assertThat(response.valueImportState()).isEqualTo("locked");
        assertThat(response.requirementCount()).isEqualTo(4);
        assertThat(response.blockerCount()).isEqualTo(4);
    }

    @Test
    void exposesEmbargoRequirementsWithoutSiblingMutation() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessEmbargoRequirementService()
                        .embargoRequirements();

        assertThat(response.version()).isEqualTo("Java v877");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.requirementCount()).isEqualTo(5);
        assertThat(response.blockerCount()).isEqualTo(5);
    }

    @Test
    void exposesDraftTextAbsenceWithoutMaterializingSignedDraft() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDraftTextAbsenceService()
                        .draftTextAbsence();

        assertThat(response.version()).isEqualTo("Java v878");
        assertThat(response.signedDraftState()).isEqualTo("not-created");
        assertThat(response.readyForSignedDraftText()).isFalse();
        assertThat(response.gateCount()).isEqualTo(20);
    }

    @Test
    void exposesCloseoutWithAllAuthoringLocksHeld() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService()
                        .closeout();

        assertThat(response.version()).isEqualTo("Java v879");
        assertThat(response.readyForHumanDraftAuthoring()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.requirementCount()).isEqualTo(25);
        assertThat(response.blockerCount()).isEqualTo(25);
        assertThat(response.status()).isEqualTo("passed");
    }
}
