package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupAuditTrailService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_AUDIT_TRAIL;

    static final String PROFILE = "java-shard-readiness-route-cleanup-audit-trail.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupAuditTrailResponse auditTrail() {
        List<OpsShardReadinessRouteCleanupAuditTrailResponse.AuditCheckpoint> checkpoints = List.of(
                checkpoint(
                        "node-plan-source",
                        "Node v549 route archive verification plan remains the source of truth",
                        "java"
                ),
                checkpoint(
                        "catalog-continuity",
                        OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel()
                                + " keeps the route cleanup catalog continuous",
                        "java"
                ),
                checkpoint(
                        "read-only-boundary",
                        "no credential values, raw endpoints, runtime execution, schema, SQL, deployment, or rollback",
                        "java"
                ),
                checkpoint(
                        "controller-split",
                        "route cleanup evidence stays separated from v1 contract and legacy readiness controllers",
                        "java"
                ),
                checkpoint(
                        "handoff-evidence",
                        "service response is deterministic and derived from existing read-only evidence",
                        "java"
                )
        );
        return new OpsShardReadinessRouteCleanupAuditTrailResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                checkpoints.size(),
                checkpoints,
                "Node v549",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }

    private OpsShardReadinessRouteCleanupAuditTrailResponse.AuditCheckpoint checkpoint(
            String name,
            String evidence,
            String owner
    ) {
        return new OpsShardReadinessRouteCleanupAuditTrailResponse.AuditCheckpoint(
                name,
                evidence,
                owner,
                true,
                false,
                "passed"
        );
    }
}
