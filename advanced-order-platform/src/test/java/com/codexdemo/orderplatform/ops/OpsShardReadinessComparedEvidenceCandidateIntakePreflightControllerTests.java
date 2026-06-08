package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessComparedEvidenceCandidateIntakePreflightControllerTests {

    @Test
    void exposesCandidateIntakePreflightRoutesWithoutAcceptingDocuments() {
        var controller = controller();

        assertThat(controller.catalog().intakeSlotCount()).isEqualTo(10);
        assertThat(controller.source().version()).isEqualTo("Java v1076");
        assertThat(controller.comparison().payloadImportAllowed()).isFalse();
        assertThat(controller.policy().approvalGrantAllowed()).isFalse();
        assertThat(controller.closeout().siblingMutationAllowed()).isFalse();
    }

    private OpsShardReadinessComparedEvidenceCandidateIntakePreflightController controller() {
        return new OpsShardReadinessComparedEvidenceCandidateIntakePreflightController(
                new OpsShardReadinessComparedEvidenceCandidateIntakePreflightCatalogService(),
                new OpsShardReadinessComparedEvidenceCandidateIntakePreflightSourceService(),
                new OpsShardReadinessComparedEvidenceCandidateIntakePreflightComparisonService(),
                new OpsShardReadinessComparedEvidenceCandidateIntakePreflightPolicyService(),
                new OpsShardReadinessComparedEvidenceCandidateIntakePreflightCloseoutService()
        );
    }
}
