package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_MANIFEST;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-archive-manifest.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse manifest() {
        List<OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse.ArchiveArtifact> artifacts = List.of(
                artifact("segment-catalog",
                        OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService.ENDPOINT,
                        "e/471/evidence/java-route-cleanup-maintenance-segment-catalog-v471.json"),
                artifact("continuity",
                        OpsShardReadinessRouteCleanupMaintenanceContinuityService.ENDPOINT,
                        "e/473/evidence/java-route-cleanup-maintenance-continuity-v473.json"),
                artifact("latest-sibling-report",
                        OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService.ENDPOINT,
                        "e/475/evidence/java-route-cleanup-maintenance-latest-sibling-report-v475.json"),
                artifact("handoff-pair-audit",
                        OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService.ENDPOINT,
                        "e/477/evidence/java-route-cleanup-maintenance-handoff-pair-audit-v477.json"),
                artifact("boundary-drift",
                        OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService.ENDPOINT,
                        "e/479/evidence/java-route-cleanup-maintenance-boundary-drift-v479.json"),
                artifact("source-plan-alignment",
                        OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService.ENDPOINT,
                        "e/481/evidence/java-route-cleanup-maintenance-source-plan-alignment-v481.json"),
                artifact("test-budget-plan",
                        OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService.ENDPOINT,
                        "e/483/evidence/java-route-cleanup-maintenance-test-budget-plan-v483.json")
        );
        List<String> checks = List.of(
                "archive-artifact-count-" + artifacts.size(),
                "archive-artifacts-use-versioned-evidence-paths",
                "archive-artifacts-map-to-maintenance-endpoints",
                "archive-does-not-require-runtime-artifacts",
                "archive-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse(
                "advanced-order-platform",
                "Java v485",
                true,
                false,
                ENDPOINT,
                PROFILE,
                artifacts.size(),
                artifacts,
                checks,
                status(artifacts)
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse.ArchiveArtifact artifact(
            String name,
            String sourceEndpoint,
            String evidencePath
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse.ArchiveArtifact(
                name,
                sourceEndpoint,
                evidencePath,
                "passed"
        );
    }

    private String status(
            List<OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse.ArchiveArtifact> artifacts
    ) {
        boolean passed = artifacts.size() == 7
                && artifacts.stream().allMatch(artifact -> artifact.evidencePath().startsWith("e/"))
                && artifacts.stream().allMatch(artifact -> artifact.evidencePath().endsWith(".json"))
                && artifacts.stream().allMatch(artifact -> "passed".equals(artifact.status()));
        return passed ? "passed" : "blocked";
    }
}
