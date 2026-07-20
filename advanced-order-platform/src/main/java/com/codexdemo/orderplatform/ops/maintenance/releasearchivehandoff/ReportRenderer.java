package com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff;

import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ArtifactCrossCheck;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CiProof;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.CloseoutHandoff;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.OperatorInstruction;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RetentionGuard;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.RouteHandoff;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.SourceArchiveSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse.VerificationRequirement;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ReportRenderer {

  private ReportRenderer() {}

  static List<MarkdownSection> render(
      List<SourceArchiveSnapshot> snapshots,
      List<VerificationRequirement> requirements,
      List<ArtifactCrossCheck> artifacts,
      List<RouteHandoff> routes,
      List<OperatorInstruction> instructions,
      List<CiProof> proofs,
      List<BoundaryGuard> boundaries,
      List<RetentionGuard> retentions,
      List<CloseoutHandoff> closeouts,
      List<ScorecardEntry> scorecard) {
    return List.of(
        sourceArchive(snapshots),
        verificationRequirements(requirements),
        artifactCrossChecks(artifacts),
        routeHandoffs(routes),
        operatorInstructions(instructions),
        ciProofs(proofs),
        boundaryGuards(boundaries),
        retentionGuards(retentions),
        closeoutHandoffs(closeouts),
        scorecard(scorecard));
  }

  private static MarkdownSection sourceArchive(List<SourceArchiveSnapshot> entries) {
    return MarkdownSections.counted(
        "Source Archive",
        "source-archive-snapshot-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.version(),
                entry.endpoint(),
                entry.profile(),
                entry.archiveRegistryState(),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection verificationRequirements(List<VerificationRequirement> entries) {
    return MarkdownSections.counted(
        "Verification Requirements",
        "verification-requirement-count",
        entries,
        entry ->
            entry.code()
                + "="
                + entry.actual()
                + "/"
                + entry.expected()
                + " | "
                + entry.evidence()
                + " | "
                + flag("passed", entry.passed())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection artifactCrossChecks(List<ArtifactCrossCheck> entries) {
    return MarkdownSections.counted(
        "Artifact Cross Checks",
        "artifact-cross-check-count",
        entries,
        entry ->
            entry.name()
                + "="
                + entry.sourceValue()
                + " | "
                + entry.expectedEvidence()
                + " | "
                + flag("matched", entry.matched())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection routeHandoffs(List<RouteHandoff> entries) {
    return MarkdownSections.counted(
        "Route Handoffs",
        "route-handoff-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.receiver(),
                entry.owner(),
                entry.packet(),
                flag("ready", entry.ready()),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection operatorInstructions(List<OperatorInstruction> entries) {
    return MarkdownSections.counted(
        "Operator Instructions",
        "operator-instruction-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.owner()
                + " | "
                + entry.sourceEvidence()
                + " | "
                + entry.instruction()
                + " | "
                + flag("ready", entry.ready())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection ciProofs(List<CiProof> entries) {
    return MarkdownSections.counted(
        "CI Proofs",
        "ci-proof-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | "
                + entry.commandFamily()
                + " | "
                + flag("readOnly", entry.readOnly())
                + " | "
                + flag("sourcePassed", entry.sourcePassed())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaryGuards(List<BoundaryGuard> entries) {
    return MarkdownSections.counted(
        "Boundary Guards",
        "boundary-guard-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.code(),
                entry.lockedBehavior(),
                entry.auditEvidence(),
                flag("locked", entry.locked()),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection retentionGuards(List<RetentionGuard> entries) {
    return MarkdownSections.counted(
        "Retention Guards",
        "retention-guard-count",
        entries,
        entry ->
            String.join(
                " | ",
                entry.name(),
                entry.sourceEvidence(),
                entry.retentionWindow(),
                flag("ready", entry.ready()),
                status(entry.status())),
        MarkdownSection::new);
  }

  private static MarkdownSection closeoutHandoffs(List<CloseoutHandoff> entries) {
    return MarkdownSections.counted(
        "Closeout Handoffs",
        "closeout-handoff-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.item()
                + " | "
                + entry.owner()
                + " | "
                + entry.evidence()
                + " | "
                + flag("ready", entry.ready())
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.counted(
        "Scorecard",
        "scorecard-entry-count",
        entries,
        entry ->
            entry.name()
                + "="
                + entry.actual()
                + "/"
                + entry.expected()
                + " | "
                + status(entry.status()),
        MarkdownSection::new);
  }

  private static String flag(String name, boolean value) {
    return name + "=" + value;
  }

  private static String status(String value) {
    return "status=" + value;
  }
}
