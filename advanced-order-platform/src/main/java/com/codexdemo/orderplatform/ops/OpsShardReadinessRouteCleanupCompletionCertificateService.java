package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupCompletionCertificateService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_COMPLETION_CERTIFICATE;

    static final String PROFILE = "java-shard-readiness-route-cleanup-completion-certificate.v1";

    private final OpsShardReadinessRouteCleanupCompletionIndexService completionIndexService;

    private final OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService;

    private final OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService;

    public OpsShardReadinessRouteCleanupCompletionCertificateService(
            OpsShardReadinessRouteCleanupCompletionIndexService completionIndexService,
            OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService,
            OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService
    ) {
        this.completionIndexService = completionIndexService;
        this.thirdRunCloseoutService = thirdRunCloseoutService;
        this.finalArchivePlanService = finalArchivePlanService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupCompletionCertificateResponse certificate() {
        OpsShardReadinessRouteCleanupCompletionIndexResponse index = completionIndexService.index();
        OpsShardReadinessRouteCleanupThirdRunCloseoutResponse closeout = thirdRunCloseoutService.closeout();
        OpsShardReadinessRouteCleanupFinalArchivePlanResponse archivePlan = finalArchivePlanService.plan();
        List<OpsShardReadinessRouteCleanupCompletionCertificateResponse.CertificateClaim> claims = List.of(
                claim("completion-index", index.status()),
                claim("third-run-closeout", closeout.decision()),
                claim("final-archive-plan", archivePlan.decision()),
                claim("read-only-boundary", OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus()),
                claim("version-continuity",
                        String.valueOf(OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous())),
                claim("execution-disabled", String.valueOf(!index.executionAllowed()
                        && !closeout.executionAllowed()
                        && !archivePlan.executionAllowed()))
        );
        boolean passed = index.status().equals("passed")
                && closeout.status().equals("passed")
                && archivePlan.status().equals("passed")
                && OpsShardReadinessRouteCleanupEvidenceAnalyzer.boundaryStatus().equals("passed")
                && OpsShardReadinessRouteCleanupEvidenceAnalyzer.versionsAreContinuous();
        return new OpsShardReadinessRouteCleanupCompletionCertificateResponse(
                "advanced-order-platform",
                OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel(),
                true,
                false,
                ENDPOINT,
                PROFILE,
                OpsShardReadinessRouteCleanupCompletionIndexService.ENDPOINT,
                OpsShardReadinessRouteCleanupThirdRunCloseoutService.ENDPOINT,
                OpsShardReadinessRouteCleanupFinalArchivePlanService.ENDPOINT,
                claims.size(),
                claims,
                "java-route-cleanup-completion-v"
                        + OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion(),
                passed ? "completion-certificate-ready" : "blocked",
                passed ? "passed" : "blocked"
        );
    }

    private OpsShardReadinessRouteCleanupCompletionCertificateResponse.CertificateClaim claim(
            String name,
            String evidence
    ) {
        return new OpsShardReadinessRouteCleanupCompletionCertificateResponse.CertificateClaim(
                name,
                evidence,
                "passed"
        );
    }
}
