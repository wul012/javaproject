package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalogTests {

    @Test
    void combinesFoundationAndAssuranceRequirementsWithoutMaterializingDraft() {
        var requirements = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                .allRequirements();

        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessFoundationRequirementCatalog
                .foundationRequirements()).hasSize(13);
        assertThat(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessAssuranceRequirementCatalog
                .assuranceRequirements()).hasSize(12);
        assertThat(requirements).hasSize(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                        .REQUIREMENT_COUNT);
        assertThat(requirements.stream().map(requirement -> requirement.code()).collect(Collectors.toSet()))
                .hasSize(25);
        assertThat(requirements.stream().map(requirement -> requirement.blockerCode()).collect(Collectors.toSet()))
                .hasSize(25);
        assertThat(requirements).allSatisfy(requirement -> {
            assertThat(requirement.status()).isEqualTo("passed");
            assertThat(requirement.sourceReviewPackageSlot()).contains("REVIEW_PACKAGE_PREFLIGHT");
            assertThat(requirement.authoringBlocker()).isNotBlank();
            assertThat(requirement.sourceEndpoint()).startsWith(OpsShardReadinessRoutePaths.BASE_PATH);
        });
    }
}
