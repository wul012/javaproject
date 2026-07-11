package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryRenderer {

  private OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryRenderer() {}

  static List<
          OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
              .MarkdownSection>
      render(
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .SourceRegistrySnapshot>
              sourceRegistrySnapshots,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .ArtifactVerification>
              artifactVerifications,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .ReadTargetVerification>
              readTargetVerifications,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .GateCheckVerification>
              gateCheckVerifications,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .BoundaryVerification>
              boundaryVerifications,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .CiBatchVerification>
              ciBatchVerifications,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .OperatorHandoffVerification>
              operatorHandoffVerifications,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .ScorecardEntry>
              scorecard) {
    List<
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                .MarkdownSection>
        sections = new ArrayList<>();
    sections.add(sourceRegistrySection(sourceRegistrySnapshots));
    sections.add(
        OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationRenderer.render(
            artifactVerifications));
    sections.add(readTargetSection(readTargetVerifications));
    sections.add(
        OpsShardReadinessMinimalReadOnlyGateExecutionGateVerificationRenderer.render(
            gateCheckVerifications));
    sections.add(
        OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationRenderer.render(
            boundaryVerifications));
    sections.add(
        ciAndHandoffSection(ciBatchVerifications, operatorHandoffVerifications, scorecard));
    return List.copyOf(sections);
  }

  private static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
          .MarkdownSection
      sourceRegistrySection(
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .SourceRegistrySnapshot>
              snapshots) {
    List<String> lines = new ArrayList<>();
    lines.add("source-registry-count=" + snapshots.size());
    snapshots.forEach(
        snapshot ->
            lines.add(
                String.join(
                    " | ",
                    snapshot.version(),
                    snapshot.endpoint(),
                    snapshot.sourcePlan(),
                    OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport
                        .statusLine("status", snapshot.status()))));
    return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.section(
        "Source Registry", lines);
  }

  private static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
          .MarkdownSection
      readTargetSection(
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .ReadTargetVerification>
              readTargets) {
    List<String> lines = new ArrayList<>();
    lines.add("read-target-verification-count=" + readTargets.size());
    readTargets.forEach(
        target ->
            lines.add(
                String.join(
                    " | ",
                    target.target(),
                    target.commandOrRoute(),
                    OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport
                        .statusLine("status", target.status()))));
    return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.section(
        "Read Target Verification", lines);
  }

  private static OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
          .MarkdownSection
      ciAndHandoffSection(
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .CiBatchVerification>
              ciBatches,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .OperatorHandoffVerification>
              handoffs,
          List<
                  OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                      .ScorecardEntry>
              scorecard) {
    List<String> lines = new ArrayList<>();
    lines.add("ci-batch-verification-count=" + ciBatches.size());
    lines.add("operator-handoff-verification-count=" + handoffs.size());
    lines.add("scorecard-entry-count=" + scorecard.size());
    scorecard.forEach(
        score -> lines.add(score.name() + "=" + score.actual() + "/" + score.expected()));
    return OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.section(
        "CI Handoff Scorecard", lines);
  }
}
