package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptCriteriaCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptCriteriaCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
            .AcceptedCriterion> criteria(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse source
    ) {
        return List.of(
                criterion("acceptance-package-passed", source.endpoint() + ":" + source.status(),
                        "passed".equals(source.status())),
                criterion("lineage-complete", "lineage-count=" + source.lineageEntryCount(),
                        source.lineageEntryCount() == 3),
                criterion("decisions-accepted", "decision-count=" + source.decisionRecordCount(),
                        source.decisions().stream()
                                .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                        .DecisionRecord::accepted)),
                criterion("archive-ready", "archive-items=" + source.archiveItemCount(),
                        source.archiveItems().stream()
                                .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                        .ArchiveItem::ready)),
                criterion("ci-evidence-passed", "ci-evidence=" + source.ciEvidenceCount(),
                        source.ciEvidence().stream()
                                .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                        .CiEvidence::passed)),
                criterion("runtime-boundaries-locked", "runtime-boundaries=" + source.runtimeBoundaryCount(),
                        source.runtimeBoundaries().stream()
                                .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                        .RuntimeBoundary::locked)),
                criterion("next-change-rules-ready", "next-change-rules=" + source.nextChangeRuleCount(),
                        source.nextChangeRules().stream()
                                .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                        .NextChangeRule::ready))
        );
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
            .AcceptedCriterion criterion(
            String name,
            String evidence,
            boolean accepted
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
                .AcceptedCriterion(name, evidence, true, accepted ? "accepted" : "blocked");
    }
}
