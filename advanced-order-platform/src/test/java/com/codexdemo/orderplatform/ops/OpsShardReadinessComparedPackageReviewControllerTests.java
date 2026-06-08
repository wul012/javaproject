package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedPackageReviewControllerTests {

    @Test
    void exposesComparedPackageReviewRoutesWithoutDecisionOrRuntime() {
        var controller = controller();

        assertThat(controller.catalog().reviewSlotCount()).isEqualTo(12);
        assertThat(controller.sourceEvidence().version()).isEqualTo("Java v1035");
        assertThat(controller.comparisonOutcome().readyForReviewDecision()).isFalse();
        assertThat(controller.identityDigest().readyForRuntimePayload()).isFalse();
        assertThat(controller.policyArchive().readyForApprovalGrant()).isFalse();
        assertThat(controller.handoffCloseout().siblingMutationAllowed()).isFalse();
    }

    private OpsShardReadinessComparedPackageReviewController controller() {
        return new OpsShardReadinessComparedPackageReviewController(
                new OpsShardReadinessComparedPackageReviewCatalogService(),
                new OpsShardReadinessComparedPackageReviewSourceEvidenceService(),
                new OpsShardReadinessComparedPackageReviewComparisonOutcomeService(),
                new OpsShardReadinessComparedPackageReviewIdentityDigestService(),
                new OpsShardReadinessComparedPackageReviewPolicyArchiveService(),
                new OpsShardReadinessComparedPackageReviewHandoffCloseoutService()
        );
    }
}
