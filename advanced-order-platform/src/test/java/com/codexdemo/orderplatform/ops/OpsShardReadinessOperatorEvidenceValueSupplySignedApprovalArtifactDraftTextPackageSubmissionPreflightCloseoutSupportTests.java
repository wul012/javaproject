package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupportTests {

    @Test
    void buildsCloseoutResponseWithoutAcceptingSubmittedMaterial() {
        var response = OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
                .response("Java v970", "/ops/shard-readiness/example", "submission-preflight-closeout.example",
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
                                .handoff("handoff", "identity", "item", "evidence", "source")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
                                .guardrail("guard", "runtime", "runtime remains locked")),
                        List.of(OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutSupport
                                .route("route", "/route", "route stays read-only")),
                        List.of("extra-check"));

        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForSubmittedPackageAcceptance()).isFalse();
        assertThat(response.readyForSignedDraftTextParsing()).isFalse();
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
        assertThat(response.status()).isEqualTo("passed");
        assertThat(response.checks()).contains("extra-check");
    }
}

