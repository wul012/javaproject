package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SEGMENT_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-segment-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse catalog() {
        List<OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse.SegmentSummary> segments =
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.segments().stream()
                        .map(this::summary)
                        .toList();
        int entryCount = segments.stream()
                .mapToInt(OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse
                        .SegmentSummary::entryCount)
                .sum();
        return new OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse(
                "advanced-order-platform",
                "Java v471",
                true,
                false,
                ENDPOINT,
                PROFILE,
                segments.size(),
                entryCount,
                segments,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations(),
                status(segments, entryCount)
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse.SegmentSummary summary(
            OpsShardReadinessRouteCleanupEvidenceAnalyzer.Segment segment
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse.SegmentSummary(
                segment.name(),
                segment.firstJavaVersion(),
                segment.lastJavaVersion(),
                segment.entryCount(),
                segment.firstPhase(),
                segment.lastPhase(),
                segment.sourceNodePlans(),
                segment.status()
        );
    }

    private String status(
            List<OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse.SegmentSummary> segments,
            int entryCount
    ) {
        boolean passed = segments.size() == 6
                && entryCount == OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries().size()
                && segments.stream().allMatch(segment -> "passed".equals(segment.status()))
                && "passed".equals(OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus());
        return passed ? "passed" : "blocked";
    }
}
