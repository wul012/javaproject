package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckControllerTests {

    @Test
    void exposesAcceptancePrecheckRoutesThroughControllerWithoutAcceptingPackage() {
        var controller = controller();

        assertThat(controller.catalog().checkpointCount()).isEqualTo(10);
        assertThat(controller.sourceIdentityDigest().version()).isEqualTo("Java v1010");
        assertThat(controller.signatureEvidenceValue().readyForDetachedSignatureParsing()).isFalse();
        assertThat(controller.policyExecutionArchive().readyForApprovalGrant()).isFalse();
        assertThat(controller.policyExecutionArchive().siblingMutationAllowed()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSourceIdentityDigestService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSignatureEvidenceValueService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService()
        );
    }
}

