package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffRetentionCloseoutScorecardTests {

    @Test
    void carriesRetentionGuardsAndCloseoutHandoffs() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.retentionGuardCount()).isEqualTo(5);
        assertThat(response.readyRetentionGuardCount()).isEqualTo(5);
        assertThat(response.retentionGuards())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .RetentionGuard::name)
                .containsExactly(
                        "source-dossier-snapshot",
                        "provenance-chain",
                        "section-digests",
                        "ci-replay-lanes",
                        "boundary-controls"
                );

        assertThat(response.closeoutHandoffCount()).isEqualTo(6);
        assertThat(response.readyCloseoutHandoffCount()).isEqualTo(6);
        assertThat(response.closeoutHandoffs())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .CloseoutHandoff::item)
                .containsExactly(
                        "read-verification-dossier",
                        "verify-readiness-gates",
                        "confirm-boundary-controls",
                        "record-ci-replay-lanes",
                        "archive-release-evidence",
                        "handoff-release-acceptance"
                );
    }

    @Test
    void scorecardRequiresEveryArchiveVerificationHandoffPartToPass() {
        var response =
                OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffTestSupport.registry();

        assertThat(response.scorecardEntryCount()).isEqualTo(9);
        assertThat(response.passedScorecardEntryCount()).isEqualTo(9);
        assertThat(response.scorecard())
                .extracting(OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse
                        .ScorecardEntry::name)
                .containsExactly(
                        "source-archive",
                        "verification-requirements",
                        "artifact-cross-checks",
                        "route-handoffs",
                        "operator-instructions",
                        "ci-proofs",
                        "boundary-guards",
                        "retention-guards",
                        "closeout-handoffs"
                );
    }
}
