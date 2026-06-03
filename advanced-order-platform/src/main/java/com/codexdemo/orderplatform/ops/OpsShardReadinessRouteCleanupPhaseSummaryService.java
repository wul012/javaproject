package com.codexdemo.orderplatform.ops;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupPhaseSummaryService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_PHASE_SUMMARY;

    static final String PROFILE = "java-shard-readiness-route-cleanup-phase-summary.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupPhaseSummaryResponse summary() {
        List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries();
        List<OpsShardReadinessRouteCleanupPhaseSummaryResponse.PhaseSummary> phases =
                phaseSummaries(entries);
        return new OpsShardReadinessRouteCleanupPhaseSummaryResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                entries.size(),
                phases.size(),
                phases,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }

    private List<OpsShardReadinessRouteCleanupPhaseSummaryResponse.PhaseSummary> phaseSummaries(
            List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries
    ) {
        Map<String, List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry>> bySegment =
                new LinkedHashMap<>();
        for (OpsShardReadinessRouteCleanupEvidenceResponse.Entry entry : entries) {
            bySegment.computeIfAbsent(
                    OpsShardReadinessRouteCleanupEvidenceAnalyzer.segmentFor(entry),
                    ignored -> new ArrayList<>()
            ).add(entry);
        }
        return bySegment.entrySet().stream()
                .map(entry -> phaseSummary(entry.getKey(), entry.getValue()))
                .toList();
    }

    private OpsShardReadinessRouteCleanupPhaseSummaryResponse.PhaseSummary phaseSummary(
            String segment,
            List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries
    ) {
        return new OpsShardReadinessRouteCleanupPhaseSummaryResponse.PhaseSummary(
                segment,
                entries.getFirst().javaVersion(),
                entries.getLast().javaVersion(),
                entries.size(),
                entries.stream()
                        .map(OpsShardReadinessRouteCleanupEvidenceResponse.Entry::sourceNodePlan)
                        .distinct()
                        .toList(),
                true,
                false,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }
}
