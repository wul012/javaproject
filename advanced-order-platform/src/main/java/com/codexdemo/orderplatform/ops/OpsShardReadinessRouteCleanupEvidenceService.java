package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupEvidenceService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_EVIDENCE_CATALOG;

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupEvidenceResponse catalog() {
        List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries =
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.entries();
        return new OpsShardReadinessRouteCleanupEvidenceResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                "java-shard-readiness-route-cleanup-evidence-catalog.v1",
                entries.size(),
                entries,
                forbiddenOperations(),
                catalogStatus()
        );
    }

    private List<String> forbiddenOperations() {
        return OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations();
    }

    private String catalogStatus() {
        return OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus();
    }
}
