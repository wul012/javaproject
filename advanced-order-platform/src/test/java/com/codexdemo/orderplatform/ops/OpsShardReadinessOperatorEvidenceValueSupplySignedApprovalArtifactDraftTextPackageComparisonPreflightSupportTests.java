package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupportTests {

    @Test
    void buildsComparisonPreflightResponseWithoutAcceptingPackageMaterial() {
        var lane = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                .lane("lane", "v1287", "compare identity", "question", "reject", "source");
        var control = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                .control("control", "acceptance", "reject uncompared material", "reject");
        var gate = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                .gate("gate", "runtime", "runtime locked");

        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightSupport
                .response("Java v995", "/ops/shard-readiness/example", "comparison-preflight.example",
                        List.of(lane), List.of(control), List.of(gate), List.of("extra-check"));

        assertThat(response.sourcePlan()).isEqualTo("Node v1311");
        assertThat(response.sourceJavaSubmissionCloseoutVersion()).isEqualTo("Java v994");
        assertThat(response.readyForSubmittedPackageAcceptance()).isFalse();
        assertThat(response.readyForSignedDraftTextParsing()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.status()).isEqualTo("passed");
    }
}

