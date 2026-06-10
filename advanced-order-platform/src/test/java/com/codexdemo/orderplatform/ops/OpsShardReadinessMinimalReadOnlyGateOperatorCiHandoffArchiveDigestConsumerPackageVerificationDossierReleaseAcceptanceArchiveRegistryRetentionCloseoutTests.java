package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryRetentionCloseoutTests {

    @Test
    void carriesRetentionWindowsForArchiveEvidence() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
                        .registry();

        assertThat(response.retentionWindowCount()).isEqualTo(5);
        assertThat(response.readyRetentionWindowCount()).isEqualTo(5);
        assertThat(response.retentionWindows())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .RetentionWindowEntry::name)
                .containsExactly(
                        "source-dossier-snapshot",
                        "provenance-chain",
                        "section-digests",
                        "ci-replay-lanes",
                        "boundary-controls"
                );
    }

    @Test
    void carriesCloseoutLedgerAndArchiveScorecard() {
        var response =
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryTestSupport
                        .registry();

        assertThat(response.closeoutLedgerCount()).isEqualTo(6);
        assertThat(response.readyCloseoutLedgerCount()).isEqualTo(6);
        assertThat(response.closeoutLedger())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .CloseoutLedgerEntry::item)
                .containsExactly(
                        "read-verification-dossier",
                        "verify-readiness-gates",
                        "confirm-boundary-controls",
                        "record-ci-replay-lanes",
                        "archive-release-evidence",
                        "handoff-release-acceptance"
                );
        assertThat(response.scorecardEntryCount()).isEqualTo(8);
        assertThat(response.passedScorecardEntryCount()).isEqualTo(8);
        assertThat(response.scorecard())
                .extracting(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistryResponse
                        .ScorecardEntry::name)
                .containsExactly(
                        "source-release-acceptance-status",
                        "artifact-manifest",
                        "route-packages",
                        "operator-packs",
                        "ci-attestations",
                        "boundary-seals",
                        "retention-windows",
                        "closeout-ledger"
                );
    }
}
