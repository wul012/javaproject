package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryRenderer {

    private OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryRenderer() {
    }

    static List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .MarkdownSection> render(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .SourceArchiveSnapshot> sourceArchives,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .OperatorLane> operatorLanes,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .CiBatchPlan> ciBatches,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .BoundaryLock> boundaryLocks,
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .ScorecardEntry> scorecard
            ) {
        List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                .MarkdownSection> sections = new ArrayList<>();
        sections.add(sourceArchiveSection(sourceArchives));
        sections.add(OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneRenderer.render(
                operatorLanes));
        sections.add(ciBatchSection(ciBatches));
        sections.add(boundarySection(boundaryLocks));
        sections.add(scorecardSection(scorecard));
        return List.copyOf(sections);
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .MarkdownSection sourceArchiveSection(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .SourceArchiveSnapshot> archives
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("source-archive-count=" + archives.size());
        archives.forEach(archive -> lines.add(archive.version()
                + " | "
                + archive.endpoint()
                + " | "
                + archive.archiveState()
                + " | status="
                + archive.status()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport.section(
                "Source Archive",
                lines
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .MarkdownSection ciBatchSection(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .CiBatchPlan> batches
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("ci-batch-count=" + batches.size());
        batches.forEach(batch -> lines.add(batch.order()
                + ". "
                + batch.batch()
                + " | "
                + batch.commandFamily()
                + " | passed="
                + batch.passed()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport.section(
                "CI Batches",
                lines
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .MarkdownSection boundarySection(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .BoundaryLock> locks
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("boundary-lock-count=" + locks.size());
        locks.forEach(lock -> lines.add(lock.code() + " | locked=" + lock.locked()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport.section(
                "Boundary Locks",
                lines
        );
    }

    private static OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
            .MarkdownSection scorecardSection(
                    List<OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse
                            .ScorecardEntry> scorecard
            ) {
        List<String> lines = new ArrayList<>();
        lines.add("scorecard-entry-count=" + scorecard.size());
        scorecard.forEach(score -> lines.add(score.name() + "=" + score.actual() + "/" + score.expected()));
        return OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport.section(
                "Scorecard",
                lines
        );
    }
}
