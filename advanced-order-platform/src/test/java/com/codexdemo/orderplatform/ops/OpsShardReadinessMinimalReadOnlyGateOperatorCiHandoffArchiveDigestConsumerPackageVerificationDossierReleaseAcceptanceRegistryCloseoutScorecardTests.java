package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryCloseoutScorecardTests {

    @Test
    void carriesCloseoutCheckpointsForOperatorCiRelease() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                        .registry();

        assertThat(response.closeoutCheckpointCount()).isEqualTo(6);
        assertThat(response.readyCloseoutCheckpointCount()).isEqualTo(6);
        assertThat(response.closeoutCheckpoints())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .CloseoutCheckpoint::item)
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
    void scorecardRequiresEveryReleaseAcceptancePartToPass() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryTestSupport
                        .registry();

        assertThat(response.scorecardEntryCount()).isEqualTo(10);
        assertThat(response.passedScorecardEntryCount()).isEqualTo(10);
        assertThat(response.scorecard())
                .allSatisfy(score -> assertThat(score.status()).isEqualTo("passed"));
    }
}
