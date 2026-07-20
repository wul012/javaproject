package com.codexdemo.orderplatform.ops.maintenance.ciaccept;

import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.BoundaryControl;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CiReplayLane;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.CloseoutCheckpoint;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.EvidenceChainEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReleaseReadinessGate;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ReplayDecision;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.RetentionPolicy;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.SignoffLane;
import com.codexdemo.orderplatform.ops.maintenance.ciaccept.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryResponse.SourceDossierSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<SourceDossierSnapshot> sourceDossiers,
      List<ReleaseReadinessGate> readinessGates,
      List<EvidenceChainEntry> evidenceChain,
      List<SignoffLane> signoffLanes,
      List<CiReplayLane> ciReplayLanes,
      List<BoundaryControl> boundaryControls,
      List<RetentionPolicy> retentionPolicies,
      List<ReplayDecision> replayDecisions,
      List<CloseoutCheckpoint> closeoutCheckpoints,
      List<ScorecardEntry> scorecard) {
    return List.of(
        sourceDossiers(sourceDossiers),
        readinessGates(readinessGates),
        evidenceChain(evidenceChain),
        signoffLanes(signoffLanes),
        ciReplayLanes(ciReplayLanes),
        boundaryControls(boundaryControls),
        retentionPolicies(retentionPolicies),
        replayDecisions(replayDecisions),
        closeoutCheckpoints(closeoutCheckpoints),
        scorecard(scorecard));
  }

  private static MarkdownSection sourceDossiers(List<SourceDossierSnapshot> entries) {
    return MarkdownSections.mapped(
        "Source Dossier",
        entries,
        entry ->
            entry.version()
                + " | state="
                + entry.dossierState()
                + " | sections="
                + entry.sectionDigestCount()
                + " | ci="
                + entry.ciLaneCount()
                + " | boundaries="
                + entry.boundaryAuditCount()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection readinessGates(List<ReleaseReadinessGate> entries) {
    return MarkdownSections.mapped(
        "Readiness Gates",
        entries,
        entry ->
            entry.code()
                + " | expected="
                + entry.expected()
                + " | actual="
                + entry.actual()
                + " | evidence="
                + entry.evidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection evidenceChain(List<EvidenceChainEntry> entries) {
    return MarkdownSections.mapped(
        "Evidence Chain",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.artifact()
                + " | target="
                + entry.releaseTarget()
                + " | source="
                + entry.sourceEvidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection signoffLanes(List<SignoffLane> entries) {
    return MarkdownSections.mapped(
        "Signoff Lanes",
        entries,
        entry ->
            entry.receiver()
                + " | owner="
                + entry.owner()
                + " | evidence="
                + entry.evidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection ciReplayLanes(List<CiReplayLane> entries) {
    return MarkdownSections.mapped(
        "CI Replay Lanes",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | command="
                + entry.commandFamily()
                + " | replay="
                + entry.replayGroup()
                + " | readOnly="
                + entry.readOnly()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaryControls(List<BoundaryControl> entries) {
    return MarkdownSections.mapped(
        "Boundary Controls",
        entries,
        entry ->
            entry.code()
                + " | lockedBehavior="
                + entry.lockedBehavior()
                + " | audit="
                + entry.auditEvidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection retentionPolicies(List<RetentionPolicy> entries) {
    return MarkdownSections.mapped(
        "Retention Policies",
        entries,
        entry ->
            entry.name()
                + " | window="
                + entry.retentionWindow()
                + " | evidence="
                + entry.sourceEvidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection replayDecisions(List<ReplayDecision> entries) {
    return MarkdownSections.mapped(
        "Replay Decisions",
        entries,
        entry ->
            entry.code()
                + " | decision="
                + entry.decision()
                + " | evidence="
                + entry.evidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection closeoutCheckpoints(List<CloseoutCheckpoint> entries) {
    return MarkdownSections.mapped(
        "Closeout Checkpoints",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.item()
                + " | owner="
                + entry.owner()
                + " | evidence="
                + entry.evidence()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.mapped(
        "Scorecard",
        entries,
        entry ->
            entry.name()
                + " | expected="
                + entry.expected()
                + " | actual="
                + entry.actual()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }
}
