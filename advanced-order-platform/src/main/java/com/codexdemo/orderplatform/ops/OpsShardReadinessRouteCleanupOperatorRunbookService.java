package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupOperatorRunbookService {

    static final String PROFILE = "java-shard-readiness-route-cleanup-operator-runbook.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupOperatorRunbookResponse runbook() {
        List<OpsShardReadinessRouteCleanupOperatorRunbookResponse.RunbookStep> steps = List.of(
                step(1, "read-catalog", "GET " + OpsShardReadinessRouteCleanupEvidenceService.ENDPOINT,
                        OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()),
                step(2, "review-phase-summary", "GET " + OpsShardReadinessRouteCleanupPhaseSummaryService.ENDPOINT,
                        OpsShardReadinessRouteCleanupPhaseSummaryService.PROFILE),
                step(3, "review-boundary-matrix", "GET " + OpsShardReadinessRouteCleanupBoundaryMatrixService.ENDPOINT,
                        OpsShardReadinessRouteCleanupBoundaryMatrixService.PROFILE),
                step(4, "review-release-handoff", "GET " + OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT,
                        OpsShardReadinessRouteCleanupReleaseHandoffService.PROFILE),
                step(5, "export-archive-plan", "GET " + OpsShardReadinessRouteCleanupArchivePlanService.ENDPOINT,
                        OpsShardReadinessRouteCleanupArchivePlanService.PROFILE)
        );
        return new OpsShardReadinessRouteCleanupOperatorRunbookResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                PROFILE,
                steps.size(),
                steps,
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.forbiddenOperations(),
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()
        );
    }

    private OpsShardReadinessRouteCleanupOperatorRunbookResponse.RunbookStep step(
            int order,
            String name,
            String action,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupOperatorRunbookResponse.RunbookStep(
                order,
                name,
                action,
                true,
                evidence,
                "passed"
        );
    }
}
