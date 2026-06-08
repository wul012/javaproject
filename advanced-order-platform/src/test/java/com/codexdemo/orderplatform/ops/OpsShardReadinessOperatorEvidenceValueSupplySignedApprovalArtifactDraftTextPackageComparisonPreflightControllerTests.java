package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightControllerTests {

    @Test
    void exposesComparisonPreflightRoutesThroughControllerWithoutAcceptingMaterial() {
        var controller = controller();

        assertThat(controller.catalog().version()).isEqualTo("Java v1001");
        assertThat(controller.identityRequest().comparisonLaneCount()).isEqualTo(4);
        assertThat(controller.digestSignature().readyForDetachedSignatureParsing()).isFalse();
        assertThat(controller.evidenceValuePolicy().readyForEvidenceImport()).isFalse();
        assertThat(controller.executionCloseout().siblingMutationAllowed()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService()
        );
    }
}
