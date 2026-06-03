package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupBoundaryMatrixService {

    static final String PROFILE = "java-shard-readiness-route-cleanup-boundary-matrix.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupBoundaryMatrixResponse matrix() {
        List<OpsShardReadinessRouteCleanupBoundaryMatrixResponse.BoundaryRule> rules =
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations().stream()
                        .map(operation -> new OpsShardReadinessRouteCleanupBoundaryMatrixResponse.BoundaryRule(
                                operation,
                                false,
                                "route cleanup handoff suite keeps " + operation + " disabled",
                                "passed"
                        ))
                        .toList();
        return new OpsShardReadinessRouteCleanupBoundaryMatrixResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                PROFILE,
                rules.size(),
                rules,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }
}
