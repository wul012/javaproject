package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupReadOnlyGateService {

    static final String PROFILE = "java-shard-readiness-route-cleanup-read-only-gate.v1";

    private final OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService;

    private final OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService;

    public OpsShardReadinessRouteCleanupReadOnlyGateService(
            OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService,
            OpsShardReadinessRouteCleanupOperatorRunbookService operatorRunbookService
    ) {
        this.releaseHandoffService = releaseHandoffService;
        this.operatorRunbookService = operatorRunbookService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupReadOnlyGateResponse gate() {
        OpsShardReadinessRouteCleanupReleaseHandoffResponse releaseHandoff = releaseHandoffService.handoff();
        OpsShardReadinessRouteCleanupOperatorRunbookResponse runbook = operatorRunbookService.runbook();
        List<OpsShardReadinessRouteCleanupReadOnlyGateResponse.GateCheck> checks = List.of(
                check("catalog-continuity", OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous(),
                        "catalog versions are continuous"),
                check("release-handoff-passed", releaseHandoff.status().equals("passed"),
                        OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT),
                check("operator-runbook-passed", runbook.status().equals("passed"),
                        OpsShardReadinessRouteCleanupOperatorRunbookService.ENDPOINT),
                check("runtime-execution-disabled", !releaseHandoff.executionAllowed() && !runbook.executionAllowed(),
                        "executionAllowed=false across handoff and runbook")
        );
        boolean passed = checks.stream().allMatch(OpsShardReadinessRouteCleanupReadOnlyGateResponse.GateCheck::passed);
        return new OpsShardReadinessRouteCleanupReadOnlyGateResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                PROFILE,
                OpsShardReadinessRouteCleanupReleaseHandoffService.ENDPOINT,
                OpsShardReadinessRouteCleanupOperatorRunbookService.ENDPOINT,
                checks.size(),
                checks,
                passed ? "ready-for-read-only-consumer-handoff" : "blocked",
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupReadOnlyGateResponse.GateCheck check(
            String name,
            boolean passed,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupReadOnlyGateResponse.GateCheck(
                name,
                passed,
                evidence,
                passed ? "passed" : "blocked"
        );
    }
}
