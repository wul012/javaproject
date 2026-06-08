package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeServiceTests {

    @Test
    void comparedPackageEvidenceIntakeServicesExposeReadOnlySlotSlices() {
        var catalog = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService()
                .catalog();
        var source = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceAcceptanceService()
                .sourceAcceptance();
        var submission = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSubmissionComparisonService()
                .submissionComparison();
        var identityDigestSignature = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeIdentityDigestSignatureService()
                .identityDigestSignature();
        var assurance = new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceCloseoutService()
                .assuranceCloseout();

        assertThat(catalog.version()).isEqualTo("Java v1020");
        assertThat(catalog.evidenceSlotCount()).isEqualTo(10);
        assertThat(source.evidenceSlotCount()).isEqualTo(1);
        assertThat(submission.evidenceSlotCount()).isEqualTo(2);
        assertThat(identityDigestSignature.evidenceSlotCount()).isEqualTo(3);
        assertThat(assurance.evidenceSlotCount()).isEqualTo(4);
        assertThat(assurance.readyForComparedEvidenceAcceptance()).isFalse();
        assertThat(assurance.readyForRuntimePayload()).isFalse();
    }
}

