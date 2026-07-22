package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.BoundaryLock;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.CiBatchPlan;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.OperatorLane;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff.OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.SourceArchiveSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownSections;
import java.util.List;

final class HandoffRenderer {

  private HandoffRenderer() {}

  static List<MarkdownSection> render(HandoffCatalog.Evidence evidence) {
    return List.of(
        source(evidence.sourceArchiveSnapshots()),
        lanes(evidence.operatorLanes()),
        batches(evidence.ciBatches()),
        boundaries(evidence.boundaryLocks()),
        scorecard(evidence.scorecard()));
  }

  private static MarkdownSection source(List<SourceArchiveSnapshot> entries) {
    return MarkdownSections.counted(
        "Source Archive",
        "source-archive-count",
        entries,
        entry ->
            entry.version()
                + " | "
                + entry.endpoint()
                + " | "
                + entry.archiveState()
                + " | status="
                + entry.status(),
        MarkdownSection::new);
  }

  private static MarkdownSection lanes(List<OperatorLane> entries) {
    return MarkdownSections.counted(
        "Operator Lanes",
        "operator-lane-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.lane()
                + " | "
                + entry.owner()
                + " | ready="
                + entry.ready()
                + " | "
                + entry.instruction(),
        MarkdownSection::new);
  }

  private static MarkdownSection batches(List<CiBatchPlan> entries) {
    return MarkdownSections.counted(
        "CI Batches",
        "ci-batch-count",
        entries,
        entry ->
            entry.order()
                + ". "
                + entry.batch()
                + " | "
                + entry.commandFamily()
                + " | passed="
                + entry.passed(),
        MarkdownSection::new);
  }

  private static MarkdownSection boundaries(List<BoundaryLock> entries) {
    return MarkdownSections.counted(
        "Boundary Locks",
        "boundary-lock-count",
        entries,
        entry -> entry.code() + " | locked=" + entry.locked(),
        MarkdownSection::new);
  }

  private static MarkdownSection scorecard(List<ScorecardEntry> entries) {
    return MarkdownSections.counted(
        "Scorecard",
        "scorecard-entry-count",
        entries,
        entry -> entry.name() + "=" + entry.actual() + "/" + entry.expected(),
        MarkdownSection::new);
  }
}
