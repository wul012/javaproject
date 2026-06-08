package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupportTests {

    @Test
    void buildsAcceptancePrecheckResponseWithoutAcceptingComparedPackage() {
        var checkpoint = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport
                .checkpoint("checkpoint", "v1312", "checkpoint", "question", "guard", "source");
        var guard = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport
                .guard("guard", "source", "guard text", "reject");

        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport
                .response("Java v1005", "/ops/shard-readiness/example", "acceptance-precheck.example",
                        List.of(checkpoint), List.of(guard), List.of("extra-check"));

        assertThat(response.sourcePlan()).isEqualTo("Node v1321");
        assertThat(response.sourceJavaComparisonPreflightVersion()).isEqualTo("Java v1004");
        assertThat(response.readyForComparedPackageAcceptance()).isFalse();
        assertThat(response.readyForSignedDraftTextParsing()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.status()).isEqualTo("passed");
    }
}

