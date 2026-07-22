package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ArtifactVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.BoundaryVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.CiBatchVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.GateCheckVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.OperatorHandoffVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ReadTargetVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution.OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.SourceRegistrySnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.ArrayList;
import java.util.List;

final class ArchiveRenderer {

  private ArchiveRenderer() {}

  static List<MarkdownSection> render(ArchiveCatalog.Evidence evidence) {
    return List.of(
        MarkdownSections.counted(
            "Source Registry",
            "source-registry-count",
            evidence.sourceRegistrySnapshots(),
            ArchiveRenderer::sourceLine,
            MarkdownSection::new),
        MarkdownSections.counted(
            "Archive Artifacts",
            "artifact-verification-count",
            evidence.artifactVerifications(),
            ArchiveRenderer::artifactLine,
            MarkdownSection::new),
        MarkdownSections.counted(
            "Read Target Verification",
            "read-target-verification-count",
            evidence.readTargetVerifications(),
            ArchiveRenderer::readTargetLine,
            MarkdownSection::new),
        MarkdownSections.groupedCounted(
            "Gate Check Verification",
            "gate-check-verification-count",
            evidence.gateCheckVerifications(),
            GateCheckVerification::group,
            check -> check.code() + "=" + check.status(),
            MarkdownSection::new),
        MarkdownSections.counted(
            "Boundary Verification",
            "boundary-verification-count",
            evidence.boundaryVerifications(),
            ArchiveRenderer::boundaryLine,
            MarkdownSection::new),
        scorecardSection(
            evidence.ciBatchVerifications(),
            evidence.operatorHandoffVerifications(),
            evidence.scorecard()));
  }

  private static String sourceLine(SourceRegistrySnapshot snapshot) {
    return String.join(
        " | ",
        snapshot.version(),
        snapshot.endpoint(),
        snapshot.sourcePlan(),
        "status=" + snapshot.status());
  }

  private static String artifactLine(ArtifactVerification artifact) {
    return String.join(
        " | ",
        artifact.artifact(),
        artifact.producer(),
        artifact.evidence(),
        "status=" + artifact.status());
  }

  private static String readTargetLine(ReadTargetVerification target) {
    return String.join(
        " | ", target.target(), target.commandOrRoute(), "status=" + target.status());
  }

  private static String boundaryLine(BoundaryVerification boundary) {
    return String.join(
        " | ",
        boundary.code(),
        boundary.forbiddenAction(),
        "denied=" + boundary.denied(),
        "status=" + boundary.status());
  }

  private static MarkdownSection scorecardSection(
      List<CiBatchVerification> ciBatches,
      List<OperatorHandoffVerification> handoffs,
      List<ScorecardEntry> scorecard) {
    List<String> lines = new ArrayList<>();
    lines.add("ci-batch-verification-count=" + ciBatches.size());
    lines.add("operator-handoff-verification-count=" + handoffs.size());
    lines.add("scorecard-entry-count=" + scorecard.size());
    scorecard.forEach(
        entry -> lines.add(entry.name() + "=" + entry.actual() + "/" + entry.expected()));
    return new MarkdownSection("CI Handoff Scorecard", List.copyOf(lines));
  }
}
