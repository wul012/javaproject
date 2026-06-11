package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourcePlan,
        String nodeParallelPlan,
        String sourceReceiptVersion,
        String sourceReceiptEndpoint,
        String sourceAcceptancePackageVersion,
        String endpoint,
        String profile,
        int sourceSnapshotCount,
        int criteriaEchoCount,
        int archiveItemCount,
        int verificationGateCount,
        int handoffNoteCount,
        int markdownSectionCount,
        List<SourceSnapshot> sourceSnapshots,
        List<CriteriaEcho> criteriaEchoes,
        List<ArchiveIndexItem> archiveItems,
        List<VerificationGate> verificationGates,
        List<HandoffNote> handoffNotes,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceSnapshot(
            String source,
            String version,
            String endpoint,
            String status
    ) {
    }

    public record CriteriaEcho(
            String name,
            String evidence,
            String status
    ) {
    }

    public record ArchiveIndexItem(
            String item,
            String location,
            String retention,
            boolean ready
    ) {
    }

    public record VerificationGate(
            String gate,
            String evidence,
            boolean passed
    ) {
    }

    public record HandoffNote(
            String audience,
            String note,
            boolean ready
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
