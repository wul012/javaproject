package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalogTests {

    @Test
    void combinesFoundationAndAssuranceFieldsWithoutManualDraft() {
        var fields = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                .allFields();

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationFieldCatalog
                .foundationFields()).hasSize(13);
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceFieldCatalog
                .assuranceFields()).hasSize(12);
        assertThat(fields).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                        .FIELD_COUNT);
        assertThat(fields.stream().map(field -> field.code()).collect(Collectors.toSet())).hasSize(25);
        assertThat(fields.stream().map(field -> field.guardCode()).collect(Collectors.toSet())).hasSize(25);
        assertThat(fields).allSatisfy(field -> {
            assertThat(field.status()).isEqualTo("passed");
            assertThat(field.materializationBlocker()).isNotBlank();
            assertThat(field.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
    }
}
