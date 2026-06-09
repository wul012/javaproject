package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffBoundaryDecisionTests {

    @Test
    void boundaryDecisionsBlockMutableAndRuntimeSurfaces() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.boundaryDecisions())
                .extracting(OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse
                        .BoundaryDecision::code)
                .containsExactly(
                        "draft-artifact-materialization",
                        "signed-approval-capture",
                        "approval-grant",
                        "value-import",
                        "runtime-payload",
                        "write-routing",
                        "sibling-mutation");
        assertThat(response.boundaryDecisions())
                .allSatisfy(decision -> {
                    assertThat(decision.decision()).isEqualTo("blocked");
                    assertThat(decision.status()).isEqualTo("passed");
                });
    }

    @Test
    void boundaryDecisionCountMatchesSupportGate() {
        var response = OpsShardReadinessSignedApprovalDraftProfileSectionHandoffTestSupport.handoff();

        assertThat(response.boundaryDecisionCount()).isEqualTo(7);
        assertThat(response.checks())
                .contains("signed-approval-draft-profile-section-handoff-boundary-decision-count-7");
    }
}
