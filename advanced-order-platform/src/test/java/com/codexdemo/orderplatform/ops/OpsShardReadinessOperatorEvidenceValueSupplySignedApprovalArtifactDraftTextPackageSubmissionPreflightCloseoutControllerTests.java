package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutControllerTests {

    @Test
    void exposesCloseoutRoutesThroughControllerWithoutOpeningExecution() {
        var controller = controller();

        assertThat(controller.catalog().version()).isEqualTo("Java v979");
        assertThat(controller.handoffLedger().handoffItemCount()).isEqualTo(25);
        assertThat(controller.routeEvidence().routeEvidenceCount()).isEqualTo(11);
        assertThat(controller.archiveManifest().checks())
                .contains("draft-text-package-submission-preflight-closeout-archive-manifest-no-file-write");
        assertThat(controller.runtimeBoundary().readyForRuntimePayload()).isFalse();
        assertThat(controller.integritySummary().siblingMutationAllowed()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffLedgerService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRouteEvidenceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutArchiveManifestService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutRuntimeBoundaryService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutIntegritySummaryService()
        );
    }
}

