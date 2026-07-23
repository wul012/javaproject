package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.ArtifactVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.BoundaryVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.CiBatchVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.OperatorLaneVerification;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.SourceHandoffSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class ArchiveRenderer {

  private ArchiveRenderer() {}

  static List<MarkdownSection> render(ArchiveCatalog.Evidence evidence) {
    return List.of(
        sources(evidence.sourceHandoffSnapshots()),
        artifacts(evidence.artifactVerifications()),
        lanes(evidence.operatorLaneVerifications()),
        batches(evidence.ciBatchVerifications()),
        boundaries(evidence.boundaryVerifications()),
        scorecard(evidence.scorecard()));
  }

  private static MarkdownSection sources(List<SourceHandoffSnapshot> entries) {
    return MarkdownSections.counted(
        "Source Handoff",
        "source-handoff-count",
        entries,
        entry ->
            entry.version()
                + " | "
                + entry.endpoint()
                + " | "
                + entry.handoffState()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection artifacts(List<ArtifactVerification> entries) {
    return MarkdownSections.counted(
        "Artifact Verifications",
        "artifact-verification-count",
        entries,
        entry ->
            entry.artifact()
                + " | "
                + entry.producer()
                + " | archived="
                + entry.archived()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection lanes(List<OperatorLaneVerification> entries) {
    return MarkdownSections.counted(
        "Operator Lane Verifications",
        "operator-lane-verification-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.lane()
                + " | "
                + entry.owner()
                + " | archived="
                + entry.archived()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection batches(List<CiBatchVerification> entries) {
    return MarkdownSections.counted(
        "CI Batch Verifications",
        "ci-batch-verification-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | "
                + entry.commandFamily()
                + " | archived="
                + entry.archived()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaries(List<BoundaryVerification> entries) {
    return MarkdownSections.counted(
        "Boundary Verifications",
        "boundary-verification-count",
        entries,
        entry ->
            entry.code()
                + " | locked="
                + entry.locked()
                + " | archived="
                + entry.archived()
                + " | status="
                + entry.status(),
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
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }
}
