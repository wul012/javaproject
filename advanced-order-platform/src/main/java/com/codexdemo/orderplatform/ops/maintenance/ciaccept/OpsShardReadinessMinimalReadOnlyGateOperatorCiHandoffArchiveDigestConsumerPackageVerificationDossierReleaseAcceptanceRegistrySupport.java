package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import com.codexdemo.orderplatform.ops.maintenance.operatorcidossier.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse;
import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v367";
  static final String REQUIRED_ARCHIVE_VERIFICATION_PLAN = "Node v368";
  static final String OPERATOR_HANDOFF_PLAN = "Node v369";
  static final String RELEASE_ACCEPTANCE_STATE =
      "minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-ready";
  static final int EXPECTED_SOURCE_DOSSIER_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_READINESS_GATE_COUNT = 6;
  static final int EXPECTED_EVIDENCE_CHAIN_ENTRY_COUNT = 6;
  static final int EXPECTED_SIGNOFF_LANE_COUNT = 4;
  static final int EXPECTED_CI_REPLAY_LANE_COUNT = 5;
  static final int EXPECTED_BOUNDARY_CONTROL_COUNT = 8;
  static final int EXPECTED_RETENTION_POLICY_COUNT = 5;
  static final int EXPECTED_REPLAY_DECISION_COUNT = 5;
  static final int EXPECTED_CLOSEOUT_CHECKPOINT_COUNT = 6;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 10;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 10;

  private
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport() {}

  static
  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
      response(
          String version,
          String endpoint,
          String profile,
          OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse
              source,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .SourceDossierSnapshot>
              sourceDossiers,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .ReleaseReadinessGate>
              readinessGates,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .EvidenceChainEntry>
              evidenceChain,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .SignoffLane>
              signoffLanes,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .CiReplayLane>
              ciReplayLanes,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .BoundaryControl>
              boundaryControls,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .RetentionPolicy>
              retentionPolicies,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .ReplayDecision>
              replayDecisions,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .CloseoutCheckpoint>
              closeoutCheckpoints,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .ScorecardEntry>
              scorecard,
          List<
                  OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                      .MarkdownSection>
              markdownSections) {
    var sourceDossierCopy = List.copyOf(sourceDossiers);
    var readinessGateCopy = List.copyOf(readinessGates);
    var evidenceChainCopy = List.copyOf(evidenceChain);
    var signoffLaneCopy = List.copyOf(signoffLanes);
    var ciReplayLaneCopy = List.copyOf(ciReplayLanes);
    var boundaryControlCopy = List.copyOf(boundaryControls);
    var retentionPolicyCopy = List.copyOf(retentionPolicies);
    var replayDecisionCopy = List.copyOf(replayDecisions);
    var closeoutCheckpointCopy = List.copyOf(closeoutCheckpoints);
    var scorecardCopy = List.copyOf(scorecard);
    var markdownSectionCopy = List.copyOf(markdownSections);
    int passedReadinessGateCount = countPassedReadiness(readinessGateCopy);
    int passedEvidenceChainCount = countPassedEvidenceChain(evidenceChainCopy);
    int readySignoffLaneCount = countReadySignoffLanes(signoffLaneCopy);
    int readOnlyCiReplayLaneCount = countReadOnlyCiReplayLanes(ciReplayLaneCopy);
    int lockedBoundaryControlCount = countLockedBoundaryControls(boundaryControlCopy);
    int readyRetentionPolicyCount = countReadyRetentionPolicies(retentionPolicyCopy);
    int passedReplayDecisionCount = countPassedReplayDecisions(replayDecisionCopy);
    int readyCloseoutCheckpointCount = countReadyCloseout(closeoutCheckpointCopy);
    int passedScorecardCount = countScorecard(scorecardCopy);

    List<String> checks = new ArrayList<>();
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-source-plan-" + SOURCE_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-required-archive-"
            + REQUIRED_ARCHIVE_VERIFICATION_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-operator-plan-"
            + OPERATOR_HANDOFF_PLAN);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-source-dossier-version-"
            + source.version());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-source-dossier-status-"
            + source.status());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-source-dossier-count-"
            + sourceDossierCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-readiness-gate-count-"
            + readinessGateCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-passed-readiness-gate-count-"
            + passedReadinessGateCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-evidence-chain-count-"
            + evidenceChainCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-passed-evidence-chain-count-"
            + passedEvidenceChainCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-signoff-lane-count-"
            + signoffLaneCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-ready-signoff-lane-count-"
            + readySignoffLaneCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-ci-replay-lane-count-"
            + ciReplayLaneCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-read-only-ci-replay-lane-count-"
            + readOnlyCiReplayLaneCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-boundary-control-count-"
            + boundaryControlCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-locked-boundary-control-count-"
            + lockedBoundaryControlCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-retention-policy-count-"
            + retentionPolicyCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-ready-retention-policy-count-"
            + readyRetentionPolicyCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-replay-decision-count-"
            + replayDecisionCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-passed-replay-decision-count-"
            + passedReplayDecisionCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-closeout-checkpoint-count-"
            + closeoutCheckpointCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-ready-closeout-checkpoint-count-"
            + readyCloseoutCheckpointCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-scorecard-count-"
            + scorecardCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-passed-scorecard-count-"
            + passedScorecardCount);
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-markdown-section-count-"
            + markdownSectionCopy.size());
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-consumes-verification-dossier");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-upstream-autostart");
    checks.add("minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-write-routing");
    checks.add("minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-secret-value");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-raw-endpoint-resolution");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-managed-audit-http");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-runtime-execution");
    checks.add(
        "minimal-read-only-gate-operator-ci-handoff-release-acceptance-no-deployment-rollback");

    String status =
        "passed".equals(source.status())
                && source.readOnly()
                && !source.executionAllowed()
                && !source.startsJavaService()
                && !source.startsMiniKvService()
                && !source.readsCredentialValue()
                && !source.resolvesRawEndpointUrl()
                && !source.managedAuditHttpAllowed()
                && sourceDossierCopy.size() == EXPECTED_SOURCE_DOSSIER_SNAPSHOT_COUNT
                && readinessGateCopy.size() == EXPECTED_READINESS_GATE_COUNT
                && passedReadinessGateCount == readinessGateCopy.size()
                && evidenceChainCopy.size() == EXPECTED_EVIDENCE_CHAIN_ENTRY_COUNT
                && passedEvidenceChainCount == evidenceChainCopy.size()
                && signoffLaneCopy.size() == EXPECTED_SIGNOFF_LANE_COUNT
                && readySignoffLaneCount == signoffLaneCopy.size()
                && ciReplayLaneCopy.size() == EXPECTED_CI_REPLAY_LANE_COUNT
                && readOnlyCiReplayLaneCount == ciReplayLaneCopy.size()
                && boundaryControlCopy.size() == EXPECTED_BOUNDARY_CONTROL_COUNT
                && lockedBoundaryControlCount == boundaryControlCopy.size()
                && retentionPolicyCopy.size() == EXPECTED_RETENTION_POLICY_COUNT
                && readyRetentionPolicyCount == retentionPolicyCopy.size()
                && replayDecisionCopy.size() == EXPECTED_REPLAY_DECISION_COUNT
                && passedReplayDecisionCount == replayDecisionCopy.size()
                && closeoutCheckpointCopy.size() == EXPECTED_CLOSEOUT_CHECKPOINT_COUNT
                && readyCloseoutCheckpointCount == closeoutCheckpointCopy.size()
                && scorecardCopy.size() == EXPECTED_SCORECARD_ENTRY_COUNT
                && passedScorecardCount == scorecardCopy.size()
                && markdownSectionCopy.size() == EXPECTED_MARKDOWN_SECTION_COUNT
            ? "passed"
            : "blocked";

    return new OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse(
        PROJECT,
        version,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        endpoint,
        profile,
        SOURCE_PLAN,
        REQUIRED_ARCHIVE_VERIFICATION_PLAN,
        OPERATOR_HANDOFF_PLAN,
        source.version(),
        source.endpoint(),
        source.verificationDossierState(),
        RELEASE_ACCEPTANCE_STATE,
        sourceDossierCopy.size(),
        readinessGateCopy.size(),
        passedReadinessGateCount,
        evidenceChainCopy.size(),
        passedEvidenceChainCount,
        signoffLaneCopy.size(),
        readySignoffLaneCount,
        ciReplayLaneCopy.size(),
        readOnlyCiReplayLaneCount,
        boundaryControlCopy.size(),
        lockedBoundaryControlCount,
        retentionPolicyCopy.size(),
        readyRetentionPolicyCount,
        replayDecisionCopy.size(),
        passedReplayDecisionCount,
        closeoutCheckpointCopy.size(),
        readyCloseoutCheckpointCount,
        scorecardCopy.size(),
        passedScorecardCount,
        markdownSectionCopy.size(),
        sourceDossierCopy,
        readinessGateCopy,
        evidenceChainCopy,
        signoffLaneCopy,
        ciReplayLaneCopy,
        boundaryControlCopy,
        retentionPolicyCopy,
        replayDecisionCopy,
        closeoutCheckpointCopy,
        scorecardCopy,
        markdownSectionCopy,
        List.copyOf(checks),
        status);
  }

  private static int countPassedReadiness(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .ReleaseReadinessGate>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .ReleaseReadinessGate
                    ::passed)
            .count();
  }

  private static int countPassedEvidenceChain(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .EvidenceChainEntry>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .EvidenceChainEntry
                    ::passed)
            .count();
  }

  private static int countReadySignoffLanes(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .SignoffLane>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .SignoffLane
                    ::ready)
            .count();
  }

  private static int countReadOnlyCiReplayLanes(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .CiReplayLane>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .CiReplayLane
                    ::readOnly)
            .count();
  }

  private static int countLockedBoundaryControls(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .BoundaryControl>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .BoundaryControl
                    ::locked)
            .count();
  }

  private static int countReadyRetentionPolicies(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .RetentionPolicy>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .RetentionPolicy
                    ::ready)
            .count();
  }

  private static int countPassedReplayDecisions(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .ReplayDecision>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .ReplayDecision
                    ::passed)
            .count();
  }

  private static int countReadyCloseout(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .CloseoutCheckpoint>
          entries) {
    return (int)
        entries.stream()
            .filter(
                OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                        .CloseoutCheckpoint
                    ::ready)
            .count();
  }

  private static int countScorecard(
      List<
              OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse
                  .ScorecardEntry>
          entries) {
    return (int) entries.stream().filter(entry -> "passed".equals(entry.status())).count();
  }
}
