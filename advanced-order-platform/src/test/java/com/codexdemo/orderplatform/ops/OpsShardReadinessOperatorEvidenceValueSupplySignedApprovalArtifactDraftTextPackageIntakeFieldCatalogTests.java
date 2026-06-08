package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalogTests {

    @Test
    void combinesFoundationAndAssuranceFieldsIntoTwentyFiveTypedExpectedFields() {
        var foundationFields =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationFieldCatalog
                        .foundationFields();
        var assuranceFields =
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceFieldCatalog
                        .assuranceFields();
        var fields = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                .allFields();

        assertThat(foundationFields)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationFieldCatalog
                        .FOUNDATION_FIELD_COUNT);
        assertThat(assuranceFields)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceFieldCatalog
                        .ASSURANCE_FIELD_COUNT);
        assertThat(fields)
                .hasSize(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                        .FIELD_COUNT);
        assertThat(fields).extracting(
                        OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                                .IntakeField::expectedField)
                .contains(
                        "requestManifestId",
                        "instructionPreflightDigest",
                        "signatureEnvelopeId",
                        "sourcePlanVersion",
                        "operatorValueHandle",
                        "reviewState",
                        "archiveCloseoutManifest"
                );
        assertThat(fields).allSatisfy(field -> {
            assertThat(field.status()).isEqualTo("passed");
            assertThat(field.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
    }

    @Test
    void fieldSlicesPreserveVersionMapOrder() {
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                .fields(0, 4))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                        .IntakeField::versionRange)
                .containsOnly("Node v1212-v1215");
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFieldCatalog
                .fields(19, 24))
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse
                        .IntakeField::versionRange)
                .containsOnly("Node v1231-v1235");
    }
}
