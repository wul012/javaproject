package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceScorecardCatalog {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceScorecardCatalog() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
            .ScorecardEntry> scorecard(
                    OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse source,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReleaseReadinessGate> readinessGates,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.EvidenceChainEntry> evidenceChain,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.SignoffLane> signoffLanes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CiReplayLane> ciReplayLanes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.BoundaryControl> boundaryControls,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.RetentionPolicy> retentionPolicies,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReplayDecision> replayDecisions,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CloseoutCheckpoint> closeoutCheckpoints
            ) {
        return List.of(
                score("source-dossier-status", 1, "passed".equals(source.status()) ? 1 : 0),
                score("readiness-gates",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_READINESS_GATE_COUNT,
                        passedReadiness(readinessGates)),
                score("evidence-chain",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_EVIDENCE_CHAIN_ENTRY_COUNT,
                        passedEvidenceChain(evidenceChain)),
                score("signoff-lanes",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_SIGNOFF_LANE_COUNT,
                        readySignoffLanes(signoffLanes)),
                score("ci-replay-lanes",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_CI_REPLAY_LANE_COUNT,
                        readOnlyCiReplayLanes(ciReplayLanes)),
                score("boundary-controls",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_BOUNDARY_CONTROL_COUNT,
                        lockedBoundaryControls(boundaryControls)),
                score("retention-policies",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_RETENTION_POLICY_COUNT,
                        readyRetentionPolicies(retentionPolicies)),
                score("replay-decisions",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_REPLAY_DECISION_COUNT,
                        passedReplayDecisions(replayDecisions)),
                score("closeout-checkpoints",
                        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport
                                .EXPECTED_CLOSEOUT_CHECKPOINT_COUNT,
                        readyCloseoutCheckpoints(closeoutCheckpoints)),
                score("source-dossier-scorecard", source.scorecardEntryCount(), source.passedScorecardEntryCount())
        );
    }

    private static int passedReadiness(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReleaseReadinessGate> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReleaseReadinessGate::passed).count();
    }

    private static int passedEvidenceChain(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.EvidenceChainEntry> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.EvidenceChainEntry::passed).count();
    }

    private static int readySignoffLanes(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.SignoffLane> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.SignoffLane::ready).count();
    }

    private static int readOnlyCiReplayLanes(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CiReplayLane> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CiReplayLane::readOnly).count();
    }

    private static int lockedBoundaryControls(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.BoundaryControl> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.BoundaryControl::locked).count();
    }

    private static int readyRetentionPolicies(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.RetentionPolicy> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.RetentionPolicy::ready).count();
    }

    private static int passedReplayDecisions(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReplayDecision> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReplayDecision::passed).count();
    }

    private static int readyCloseoutCheckpoints(List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CloseoutCheckpoint> entries) {
        return (int) entries.stream().filter(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CloseoutCheckpoint::ready).count();
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
            .ScorecardEntry score(String name, int expected, int actual) {
        return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                .ScorecardEntry(name, expected, actual, expected == actual ? "passed" : "blocked");
    }
}
