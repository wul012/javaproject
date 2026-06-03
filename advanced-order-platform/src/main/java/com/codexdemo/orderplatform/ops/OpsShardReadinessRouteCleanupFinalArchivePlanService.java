package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupFinalArchivePlanService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_ARCHIVE_PLAN;

    static final String PROFILE = "java-shard-readiness-route-cleanup-final-archive-plan.v1";

    private final OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService;

    private final OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService;

    public OpsShardReadinessRouteCleanupFinalArchivePlanService(
            OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService,
            OpsShardReadinessRouteCleanupEndpointManifestService endpointManifestService
    ) {
        this.finalVerificationService = finalVerificationService;
        this.endpointManifestService = endpointManifestService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupFinalArchivePlanResponse plan() {
        OpsShardReadinessRouteCleanupFinalVerificationResponse verification =
                finalVerificationService.verification();
        OpsShardReadinessRouteCleanupEndpointManifestResponse manifest =
                endpointManifestService.manifest();
        List<OpsShardReadinessRouteCleanupFinalArchivePlanResponse.ArchiveStep> steps = List.of(
                step("capture-final-verification", OpsShardReadinessRouteCleanupFinalVerificationService.ENDPOINT),
                step("freeze-endpoint-manifest", OpsShardReadinessRouteCleanupEndpointManifestService.ENDPOINT),
                step("archive-evidence-catalog", OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT),
                step("record-ci-result-after-push", "GitHub Actions master run"),
                step("cleanup-generated-target", "advanced-order-platform/target"),
                step("keep-node-workspace-untouched", "Node historical fixtures are out of Java cleanup scope")
        );
        boolean passed = verification.status().equals("passed")
                && manifest.status().equals("passed")
                && steps.stream().allMatch(item -> item.status().equals("planned"));
        return new OpsShardReadinessRouteCleanupFinalArchivePlanResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                OpsShardReadinessRouteCleanupFinalVerificationService.ENDPOINT,
                manifest.endpointCount(),
                steps.size(),
                steps,
                passed ? "archive-plan-ready-after-ci" : "blocked",
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupFinalArchivePlanResponse.ArchiveStep step(
            String name,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupFinalArchivePlanResponse.ArchiveStep(
                name,
                "java",
                evidence,
                "planned"
        );
    }
}
